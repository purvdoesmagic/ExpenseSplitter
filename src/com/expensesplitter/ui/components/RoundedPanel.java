package com.expensesplitter.ui.components;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private final int radius = 16;
    public RoundedPanel() { setOpaque(false); }
    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0,0,getWidth(),getHeight(),radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }
}
