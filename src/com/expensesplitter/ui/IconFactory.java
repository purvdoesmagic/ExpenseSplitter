package com.expensesplitter.ui;

import javax.swing.*;
import java.net.URL;

public class IconFactory {
    public static ImageIcon getIcon(String path) {
        URL r = IconFactory.class.getResource(path);
        return r == null ? null : new ImageIcon(r);
    }
}
