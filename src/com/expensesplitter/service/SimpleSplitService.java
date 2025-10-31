package com.expensesplitter.service;

import com.expensesplitter.model.*;
import java.util.*;

/**
 * Simple splitting logic:
 * - calculateBalances: returns Person -> (paid - share)
 * - simplifyTransactions: returns list of human-readable settlements
 */
public class SimpleSplitService implements SplitService {

    @Override
    public Map<Person, Double> calculateBalances(List<Expense> expenses, List<Person> members) {
        Map<Person, Double> balances = new LinkedHashMap<>();
        for (Person p : members) balances.put(p, 0.0);

        int n = members.size();
        if (n == 0) return balances;

        double total = 0.0;
        for (Expense e : expenses) total += e.getAmount();
        double share = total / n;

        for (Expense e : expenses) {
            Person payer = e.getPaidBy();
            balances.put(payer, balances.get(payer) + e.getAmount());
        }
        for (Person p : members) balances.put(p, balances.get(p) - share);

        return balances;
    }

    @Override
    public List<String> simplifyTransactions(Map<Person, Double> balances) {
        List<String> settlements = new ArrayList<>();

        // list of debtors (negative) and creditors (positive)
        List<Map.Entry<Person, Double>> debtors = new ArrayList<>();
        List<Map.Entry<Person, Double>> creditors = new ArrayList<>();

        for (Map.Entry<Person, Double> entry : balances.entrySet()) {
            if (entry.getValue() < -0.01) debtors.add(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()));
            else if (entry.getValue() > 0.01) creditors.add(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()));
        }

        // Sort for deterministic output
        debtors.sort(Comparator.comparing(e -> e.getKey().getName().toLowerCase()));
        creditors.sort(Comparator.comparing(e -> e.getKey().getName().toLowerCase()));

        int i = 0, j = 0;
        while (i < debtors.size() && j < creditors.size()) {
            Map.Entry<Person, Double> d = debtors.get(i);
            Map.Entry<Person, Double> c = creditors.get(j);

            double owe = -d.getValue();
            double receive = c.getValue();
            double pay = Math.min(owe, receive);

            if (pay > 0.01) {
                settlements.add(String.format("%s pays Rs. %.2f to %s", d.getKey().getName(), pay, c.getKey().getName()));
            }

            // update amounts
            d.setValue(d.getValue() + pay); // less negative
            c.setValue(c.getValue() - pay);

            if (Math.abs(d.getValue()) < 0.01) i++;
            if (Math.abs(c.getValue()) < 0.01) j++;
        }

        return settlements;
    }

    // convenience used by some GUI parts
    public void calculateBalances(Group group) {
        if (group == null) return;
        Map<Person, Double> map = calculateBalances(group.getExpenses(), group.getMembers());
        // apply into Person objects
        for (Person p : group.getMembers()) {
            Double b = map.get(p);
            p.setBalance(b == null ? 0.0 : b);
        }
    }
}
