package com.expensesplitter.ui;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

/**
 * Theme manager. Light default. Toggle flips colors and reapplies recursively.
 * Use UITheme.applyTheme(window) after changing contents to refresh look.
 */
public class UITheme {
    public enum Theme { LIGHT, DARK }
    private static Theme current = Theme.LIGHT;

    private static final Color LIGHT_WINDOW_BG = new Color(238, 238, 238);
    private static final Color LIGHT_PANEL_BG = Color.WHITE;
    private static final Color LIGHT_TEXT = Color.BLACK;
    private static final Color LIGHT_CONTROL = new Color(230, 230, 230);

    private static final Color DARK_WINDOW_BG = new Color(40, 40, 40);
    private static final Color DARK_PANEL_BG = new Color(60, 63, 65);
    private static final Color DARK_TEXT = new Color(230, 230, 230);
    private static final Color DARK_CONTROL = new Color(80, 80, 80);

    public static void toggleTheme(Window window) {
        current = (current == Theme.LIGHT) ? Theme.DARK : Theme.LIGHT;
        applyTheme(window);
    }

    public static void applyTheme(Component comp) {
        applyThemeWithMode(comp, current);
    }

    public static void applyThemeWithMode(Component comp, Theme mode) {
        Color windowBg = (mode == Theme.DARK) ? DARK_WINDOW_BG : LIGHT_WINDOW_BG;
        Color panelBg = (mode == Theme.DARK) ? DARK_PANEL_BG : LIGHT_PANEL_BG;
        Color text = (mode == Theme.DARK) ? DARK_TEXT : LIGHT_TEXT;
        Color control = (mode == Theme.DARK) ? DARK_CONTROL : LIGHT_CONTROL;
        applyColorsRecursive(comp, windowBg, panelBg, text, control);
        if (comp instanceof Window) SwingUtilities.updateComponentTreeUI((Window) comp);
    }

    private static void applyColorsRecursive(Component comp, Color windowBg, Color panelBg, Color text, Color control) {
        if (comp == null) return;

        if (comp instanceof JMenuBar || comp instanceof JMenu || comp instanceof JMenuItem) {
            comp.setBackground(panelBg);
            comp.setForeground(text);
        } else if (comp instanceof JPanel) {
            comp.setBackground(panelBg);
            comp.setForeground(text);
        } else if (comp instanceof JScrollPane) {
            comp.setBackground(windowBg);
            JViewport vp = ((JScrollPane) comp).getViewport();
            if (vp != null) vp.setBackground(panelBg);
        } else if (comp instanceof JLabel) {
            comp.setForeground(text);
            comp.setBackground(panelBg);
        } else if (comp instanceof JButton) {
            JButton b = (JButton) comp;
            b.setBackground(control);
            b.setForeground(text);
            b.setBorder(BorderFactory.createLineBorder(text, 1));
        } else if (comp instanceof JTextComponent) {
            comp.setBackground(windowBg);
            comp.setForeground(text);
            ((JTextComponent) comp).setCaretColor(text);
            ((JTextComponent) comp).setBorder(BorderFactory.createLineBorder(text, 1));
        } else if (comp instanceof JComboBox) {
            comp.setBackground(control);
            comp.setForeground(text);
        } else if (comp instanceof JList) {
            comp.setBackground(panelBg);
            comp.setForeground(text);
        } else if (comp instanceof JTextArea) {
            comp.setBackground(panelBg);
            comp.setForeground(text);
        } else {
            comp.setBackground(windowBg);
            comp.setForeground(text);
        }

        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                applyColorsRecursive(child, windowBg, panelBg, text, control);
            }
        }
    }

    public static void styleButton(AbstractButton b) {
        Color control = (current == Theme.DARK) ? DARK_CONTROL : LIGHT_CONTROL;
        Color text = (current == Theme.DARK) ? DARK_TEXT : LIGHT_TEXT;
        b.setBackground(control);
        b.setForeground(text);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(text, 1));
    }

    public static Color getTextColor() {
        return (current == Theme.DARK) ? DARK_TEXT : LIGHT_TEXT;
    }

    public static Color getWindowBackground() {
        return (current == Theme.DARK) ? DARK_WINDOW_BG : LIGHT_WINDOW_BG;
    }

    public static Color getPanelBackground() {
        return (current == Theme.DARK) ? DARK_PANEL_BG : LIGHT_PANEL_BG;
    }

    public static Color getWindowColor() { return getWindowBackground(); }
}
