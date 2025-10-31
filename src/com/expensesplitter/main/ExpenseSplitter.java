package com.expensesplitter.main;

import javax.swing.SwingUtilities;
import com.expensesplitter.gui.MainFrame;

public class ExpenseSplitter {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
            frame.showMembersView();
        });
    }
}
