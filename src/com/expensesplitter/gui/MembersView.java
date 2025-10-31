package com.expensesplitter.gui;

import javax.swing.*;
import java.awt.*;
import com.expensesplitter.model.Group;
import com.expensesplitter.model.Person;
import com.expensesplitter.util.DataManager;
import com.expensesplitter.ui.UITheme;

/**
 * Members view: centered title, list in center, bottom controls (Name input + buttons).
 * Buttons have identical preferred size.
 */
public class MembersView extends JPanel {
    private final MainFrame owner;
    private final Group group;
    private final DefaultListModel<String> listModel;
    private final JList<String> memberList;
    private final JTextField nameField;

    public MembersView(MainFrame owner, Group group) {
        this.owner = owner;
        this.group = group;

        setLayout(new BorderLayout(10, 10));
        setBackground(UITheme.getWindowBackground());

        JLabel title = new JLabel("Members", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(UITheme.getTextColor());
        add(title, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        memberList = new JList<>(listModel);
        memberList.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        add(new JScrollPane(memberList), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottom.setPreferredSize(new Dimension(0, 80));
        bottom.setBackground(UITheme.getWindowBackground());

        bottom.add(new JLabel("Name:"));
        nameField = new JTextField(22);
        bottom.add(nameField);

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton expensesBtn = new JButton("Add Expenses");
        JButton summaryBtn = new JButton("View Summary");

        Dimension btnSize = new Dimension(130, 30);
        addBtn.setPreferredSize(btnSize);
        deleteBtn.setPreferredSize(btnSize);
        expensesBtn.setPreferredSize(btnSize);
        summaryBtn.setPreferredSize(btnSize);

        addBtn.addActionListener(e -> addMember());
        deleteBtn.addActionListener(e -> deleteMember());
        expensesBtn.addActionListener(e -> owner.showExpensesView());
        summaryBtn.addActionListener(e -> owner.showSummaryView());

        UITheme.styleButton(addBtn);
        UITheme.styleButton(deleteBtn);
        UITheme.styleButton(expensesBtn);
        UITheme.styleButton(summaryBtn);

        bottom.add(addBtn);
        bottom.add(deleteBtn);
        bottom.add(expensesBtn);
        bottom.add(summaryBtn);

        add(bottom, BorderLayout.SOUTH);

        refreshList();
        UITheme.applyTheme(this);

        SwingUtilities.invokeLater(() -> nameField.requestFocusInWindow());
    }

    public void refreshList() {
        listModel.clear();
        for (Person p : group.getMembers()) listModel.addElement(p.getName());
    }

    private void addMember() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a name.");
            return;
        }
        boolean exists = group.getMembers().stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));
        if (exists) {
            JOptionPane.showMessageDialog(this, "This name already exists.");
            return;
        }
        group.addMember(new Person(name));
        DataManager.saveGroup(group);
        nameField.setText("");
        refreshList();
    }

    private void deleteMember() {
        int idx = memberList.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Select a member to delete.");
            return;
        }
        String name = listModel.get(idx);
        int conf = JOptionPane.showConfirmDialog(this, "Delete \"" + name + "\"?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            group.removeMember(name);
            DataManager.saveGroup(group);
            refreshList();
        }
    }
}
