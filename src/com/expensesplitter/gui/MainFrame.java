package com.expensesplitter.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.expensesplitter.model.Group;
import com.expensesplitter.util.DataManager;
import com.expensesplitter.ui.UITheme;

/**
 * MainFrame: holds CardLayout views and menu (Toggle Theme, Save, Exit).
 * Default theme: light.
 */
public class MainFrame extends JFrame {
    private final Group group;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    private final MembersView membersView;
    private final ExpensesView expensesView;
    private final SummaryView summaryView;

    public MainFrame() {
        super("Expense Splitter");

        // load persisted group or new
        group = DataManager.loadGroup();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        // menu bar
        setJMenuBar(buildMenuBar());

        // content
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        membersView = new MembersView(this, group);
        expensesView = new ExpensesView(this, group);
        summaryView = new SummaryView(this, group);

        contentPanel.add(membersView, "Members");
        contentPanel.add(expensesView, "Expenses");
        contentPanel.add(summaryView, "Summary");

        add(contentPanel, BorderLayout.CENTER);

        // default theme: light
        UITheme.applyTheme(this);

        // auto-save on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataManager.saveGroup(group);
            }
        });
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem toggleTheme = new JMenuItem("Toggle Theme");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        toggleTheme.addActionListener(e -> {
            UITheme.toggleTheme(this);
        });

        saveItem.addActionListener(e -> {
            DataManager.saveGroup(group);
            JOptionPane.showMessageDialog(this, "Saved successfully.");
        });

        exitItem.addActionListener(e -> {
            DataManager.saveGroup(group);
            dispose();
            System.exit(0);
        });

        menu.add(toggleTheme);
        menu.add(saveItem);
        menu.add(exitItem);
        menuBar.add(menu);
        return menuBar;
    }

    public void showMembersView() {
        membersView.refreshList();
        cardLayout.show(contentPanel, "Members");
        UITheme.applyTheme(this);
    }

    public void showExpensesView() {
        expensesView.refreshPaidBy();
        expensesView.refreshExpenseList();
        cardLayout.show(contentPanel, "Expenses");
        UITheme.applyTheme(this);
    }

    public void showSummaryView() {
        summaryView.refresh();
        cardLayout.show(contentPanel, "Summary");
        // Summary stays readable; reapply theme for whole window
        UITheme.applyTheme(this);
    }

    public Group getGroup() { return group; }
}
