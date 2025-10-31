package com.expensesplitter.gui;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import com.expensesplitter.model.*;
import com.expensesplitter.service.SimpleSplitService;
import com.expensesplitter.util.DataManager;
import com.expensesplitter.ui.UITheme;

/**
 * SummaryView: detailed summary with totals, per-person contributions, balances, and suggested settlements.
 * Summary renders in the same look as the app; theme is applied globally but summary area remains readable.
 */
public class SummaryView extends JPanel {
    private final MainFrame owner;
    private final Group group;
    private final JTextArea summaryArea;

    public SummaryView(MainFrame owner, Group group) {
        this.owner = owner;
        this.group = group;

        setLayout(new BorderLayout(10, 10));
        setBackground(UITheme.getWindowBackground());

        JLabel title = new JLabel("Summary", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(UITheme.getTextColor());
        add(title, BorderLayout.NORTH);

        summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        summaryArea.setBackground(UITheme.getPanelBackground());
        summaryArea.setForeground(UITheme.getTextColor());
        JScrollPane sp = new JScrollPane(summaryArea);
        sp.setBorder(BorderFactory.createTitledBorder("Detailed Summary"));
        add(sp, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        bottom.setBackground(UITheme.getWindowBackground());

        JButton saveBtn = new JButton("Save Now");
        JButton clearAllBtn = new JButton("Clear All Data");
        JButton backBtn = new JButton("Back");

        Dimension btnSize = new Dimension(140, 30);
        saveBtn.setPreferredSize(btnSize);
        clearAllBtn.setPreferredSize(btnSize);
        backBtn.setPreferredSize(btnSize);

        saveBtn.addActionListener(e -> {
            DataManager.saveGroup(group);
            JOptionPane.showMessageDialog(this, "Saved.");
        });

        clearAllBtn.addActionListener(e -> {
            int conf = JOptionPane.showConfirmDialog(this,
                    "This will clear ALL members and expenses. Continue?",
                    "Confirm Full Reset", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (conf == JOptionPane.YES_OPTION) {
                group.clearAll();
                DataManager.deleteSavedFile();
                DataManager.saveGroup(group);
                refresh();
                owner.showMembersView();
                JOptionPane.showMessageDialog(this, "All data cleared.");
            }
        });

        backBtn.addActionListener(e -> owner.showMembersView());

        UITheme.styleButton(saveBtn);
        UITheme.styleButton(clearAllBtn);
        UITheme.styleButton(backBtn);

        bottom.add(saveBtn);
        bottom.add(clearAllBtn);
        bottom.add(backBtn);
        add(bottom, BorderLayout.SOUTH);

        // Force summary area to readable background (but still respects text color)
        summaryArea.setBackground(UITheme.getPanelBackground());
        summaryArea.setForeground(UITheme.getTextColor());

        refresh();
    }

    public void refresh() {
        // Recalculate balances
        new SimpleSplitService().calculateBalances(group);

        StringBuilder sb = new StringBuilder();
        sb.append("========== GROUP SUMMARY ==========\n\n");
        sb.append("Total Members: ").append(group.getMembers().size()).append("\n");
        sb.append("Total Expenses: ").append(group.getExpenses().size()).append("\n");
        double total = group.getTotalExpense();
        sb.append(String.format("Total Spent: Rs. %.2f%n%n", total));

        // Per-person paid totals
        sb.append("----- Individual Contributions -----\n");
        Map<String, Double> paidMap = new LinkedHashMap<>();
        for (Person p : group.getMembers()) paidMap.put(p.getName(), 0.0);
        for (Expense e : group.getExpenses()) {
            String payer = e.getPaidBy().getName();
            paidMap.put(payer, paidMap.getOrDefault(payer, 0.0) + e.getAmount());
        }
        for (Map.Entry<String, Double> entry : paidMap.entrySet()) {
            sb.append(String.format("%-18s Spent: Rs. %8.2f%n", entry.getKey(), entry.getValue()));
        }
        sb.append("\n");

        // Balances
        sb.append("----- Balances (positive = should receive) -----\n");
        for (Person p : group.getMembers()) {
            sb.append(String.format("%-18s Balance: Rs. %8.2f%n", p.getName(), p.getBalance()));
        }
        sb.append("\n");

        // Suggested settlements
        sb.append("----- Suggested Settlements -----\n");
        List<String> settlements = new SimpleSplitService().simplifyTransactions(group.getBalancesMap());
        if (settlements.isEmpty()) {
            sb.append("All settled.\n");
        } else {
            for (String s : settlements) sb.append(s).append("\n");
        }
        sb.append("\n");

        // All transactions
        sb.append("----- All Transactions -----\n");
        for (Expense e : group.getExpenses()) {
            sb.append(String.format("• %-20s Rs. %8.2f (Paid by %s)%n", e.getDescription(), e.getAmount(), e.getPayer().getName()));
        }

        summaryArea.setText(sb.toString());
    }
}
