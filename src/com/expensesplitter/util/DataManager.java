package com.expensesplitter.util;

import com.expensesplitter.model.Group;

import java.io.*;

/**
 * Simple serialization persistence.
 * Saves / loads `expense_splitter_data.ser` in working directory.
 */
public class DataManager {
    private static final String FILE = "expense_splitter_data.ser";

    public static void saveGroup(Group g) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Group loadGroup() {
        File f = new File(FILE);
        if (!f.exists()) return new Group();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object o = ois.readObject();
            if (o instanceof Group) return (Group) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Group();
    }

    public static void deleteSavedFile() {
        File f = new File(FILE);
        if (f.exists()) f.delete();
    }
}
