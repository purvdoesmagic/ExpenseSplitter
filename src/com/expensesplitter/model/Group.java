package com.expensesplitter.model;

import java.io.Serializable;
import java.util.*;

/**
 * Group: members and expenses container with helper calculations.
 */
public class Group implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Person> members = new ArrayList<>();
    private final List<Expense> expenses = new ArrayList<>();

    public List<Person> getMembers() { return members; }
    public List<Expense> getExpenses() { return expenses; }

    public void addMember(Person p) { members.add(p); }
    public void removeMember(Person p) {
        if (p == null) return;
        members.removeIf(m -> m.getName().equalsIgnoreCase(p.getName()));
        expenses.removeIf(e -> e.getPayer().getName().equalsIgnoreCase(p.getName()));
    }
    public void removeMember(String name) {
        if (name == null) return;
        members.removeIf(m -> m.getName().equalsIgnoreCase(name));
        expenses.removeIf(e -> e.getPayer().getName().equalsIgnoreCase(name));
    }

    public void addExpense(Expense e) { expenses.add(e); }
    public void removeExpense(int index) {
        if (index >= 0 && index < expenses.size()) expenses.remove(index);
    }

    public void clearAll() {
        members.clear();
        expenses.clear();
    }

    public Person findMemberByName(String name) {
        if (name == null) return null;
        for (Person p : members) if (p.getName().equalsIgnoreCase(name)) return p;
        return null;
    }

    public double getTotalExpense() {
        double sum = 0.0;
        for (Expense e : expenses) sum += e.getAmount();
        return sum;
    }

    public double getSharePerPerson() {
        int n = members.size();
        if (n == 0) return 0.0;
        return getTotalExpense() / n;
    }

    public double getTotalPaidBy(Person p) {
        if (p == null) return 0.0;
        double sum = 0.0;
        for (Expense e : expenses) if (e.getPayer().getName().equalsIgnoreCase(p.getName())) sum += e.getAmount();
        return sum;
    }

    /**
     * Returns a map Person -> balance (paid - share).
     * Also updates Person.balance fields.
     */
    public Map<Person, Double> getBalancesMap() {
        Map<Person, Double> map = new LinkedHashMap<>();
        int n = members.size();
        if (n == 0) return map;

        double total = getTotalExpense();
        double share = total / n;

        for (Person p : members) map.put(p, 0.0);

        for (Expense e : expenses) {
            Person payer = findMemberByName(e.getPayer().getName());
            if (payer != null) {
                map.put(payer, map.getOrDefault(payer, 0.0) + e.getAmount());
            }
        }

        for (Person p : members) map.put(p, map.get(p) - share);

        // update Person.balance
        for (Person p : members) {
            Double b = map.get(p);
            p.setBalance(b == null ? 0.0 : b);
        }

        return map;
    }

    // convenience: recalc & apply to persons
    public void applyBalances() {
        getBalancesMap();
    }
}
