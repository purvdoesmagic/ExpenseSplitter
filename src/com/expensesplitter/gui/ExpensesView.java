package com.expensesplitter.gui;

import javax.swing.*;
import java.awt.*;
import com.expensesplitter.model.*;
import com.expensesplitter.service.SimpleSplitService;
import com.expensesplitter.util.DataManager;
import com.expensesplitter.ui.UITheme;

/**
 * Expenses view: title, expense list center, inputs + buttons at bottom.
 * View Summary button placed at bottom-right (keeps layout consistent).
 */
public class ExpensesView extends JPanel {
    private final MainFrame owner;
    private final Group group;
    private final DefaultListModel<String> expenseListModel;
    private final JList<String> expenseList;
    private final JTextField descField;
    private final JTextField amountField;
    private final JComboBox<String> paidByBox;

    public ExpensesView(MainFrame owner, Group group) {
        this.owner = owner;
        this.group = group;

        setLayout(new BorderLayout(10, 10));
        setBackground(UITheme.getWindowBackground());

        JLabel title = new JLabel("Add Expenses", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(UITheme.getTextColor());
        add(title, BorderLayout.NORTH);

        expenseListModel = new DefaultListModel<>();
        expenseList = new JList<>(expenseListModel);
        expenseList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(new JScrollPane(expenseList), BorderLayout.CENTER);

        // bottom area
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setPreferredSize(new Dimension(0, 120));
        bottom.setBackground(UITheme.getWindowBackground());

        JPanel inputs = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputs.setBackground(UITheme.getWindowBackground());

        inputs.add(new JLabel("Description:"));
        descField = new JTextField(24);
        inputs.add(descField);

        inputs.add(new JLabel("Amount:"));
        amountField = new JTextField(8);
        inputs.add(amountField);

        inputs.add(new JLabel("Paid by:"));
        paidByBox = new JComboBox<>();
        paidByBox.setPreferredSize(new Dimension(160, 24));
        refreshPaidBy();
        inputs.add(paidByBox);

        bottom.add(inputs, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        buttons.setBackground(UITheme.getWindowBackground());

        JButton addBtn = new JButton("Add Expense");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton clearBtn = new JButton("Clear All");
        JButton backBtn = new JButton("Back");
        JButton viewSummaryBtn = new JButton("View Summary");

        Dimension btnSize = new Dimension(130, 30);
        addBtn.setPreferredSize(btnSize);
        deleteBtn.setPreferredSize(btnSize);
        clearBtn.setPreferredSize(btnSize);
        backBtn.setPreferredSize(btnSize);
        viewSummaryBtn.setPreferredSize(btnSize);

        addBtn.addActionListener(e -> addExpense());
        deleteBtn.addActionListener(e -> deleteExpense());
        clearBtn.addActionListener(e -> clearAllExpenses());
        backBtn.addActionListener(e -> owner.showMembersView());
        viewSummaryBtn.addActionListener(e -> owner.showSummaryView());

        UITheme.styleButton(addBtn);
        UITheme.styleButton(deleteBtn);
        UITheme.styleButton(clearBtn);
        UITheme.styleButton(backBtn);
        UITheme.styleButton(viewSummaryBtn);

        buttons.add(addBtn);
        buttons.add(deleteBtn);
        buttons.add(clearBtn);
        buttons.add(backBtn);
        buttons.add(viewSummaryBtn);

        bottom.add(buttons, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);

        refreshExpenseList();

        descField.setCaretColor(UITheme.getTextColor());
        amountField.setCaretColor(UITheme.getTextColor());
    }

    public void refreshPaidBy() {
        paidByBox.removeAllItems();
        paidByBox.addItem("Select payer");
        for (Person p : group.getMembers()) paidByBox.addItem(p.getName());
    }

    private String formatExpense(Expense e) {
        return String.format("%s - Rs. %.2f (Paid by %s)", e.getDescription(), e.getAmount(), e.getPayer().getName());
    }

    public void refreshExpenseList() {
        expenseListModel.clear();
        for (Expense e : group.getExpenses()) expenseListModel.addElement(formatExpense(e));
    }

    private void addExpense() {
        String desc = descField.getText().trim();
        String amtText = amountField.getText().trim();
        int sel = paidByBox.getSelectedIndex();

        if (desc.isEmpty() || amtText.isEmpty() || sel <= 0) {
            JOptionPane.showMessageDialog(this, "Enter description, amount and select payer.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amtText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid amount.");
            return;
        }

        String payerName = (String) paidByBox.getSelectedItem();
        Person payer = group.findMemberByName(payerName);
        if (payer == null) {
            JOptionPane.showMessageDialog(this, "Selected payer not found.");
            return;
        }

        Expense e = new Expense(desc, amount, payer);
        group.addExpense(e);
        refreshExpenseList();

        descField.setText("");
        amountField.setText("");
        paidByBox.setSelectedIndex(0);

        new SimpleSplitService().calculateBalances(group);
        DataManager.saveGroup(group);
    }

    private void deleteExpense() {
        int idx = expenseList.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Select an expense to delete.");
            return;
        }
        int conf = JOptionPane.showConfirmDialog(this, "Delete selected expense?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            group.removeExpense(idx);
            refreshExpenseList();
            new SimpleSplitService().calculateBalances(group);
            DataManager.saveGroup(group);
        }
    }

    private void clearAllExpenses() {
        int conf = JOptionPane.showConfirmDialog(this, "This will remove all expenses. Continue?", "Confirm Clear All", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            group.getExpenses().clear();
            refreshExpenseList();
            new SimpleSplitService().calculateBalances(group);
            DataManager.saveGroup(group);
        }
    }
}
