# 💸 Expense-Splitter — Modern Java Swing Desktop App

A sleek, modern **desktop application for splitting group expenses**, built entirely with **Java Swing** from the ground up.

This project challenges the outdated perception of Java GUIs by showcasing a **beautiful, minimalist, dark-themed interface** packed with real-world functionality.

> 🎯 Perfect for roommates, trips, events, or any group activity where expenses need splitting.

---

## 🚀 Features at a Glance

- ✅ **Dynamic Group Creation**  
  Name your group on startup and begin tracking expenses instantly.

- ➕ **Effortless Member Management**  
  Add members seamlessly with real-time UI updates.

- 💰 **Intuitive Expense Logging**  
  Log who paid, for what, how much, and who should share the cost.

- ⚖️ **Flexible & Fair Splitting**  
  Split costs equally or selectively between specific members.

- 🔁 **Smart Settlement Suggestions**  
  Get the **most efficient set of transactions** to settle debts.

- 💾 **Save & Load Sessions**  
  Persist your group data using custom `.esg` files (via Java Serialization).

- 🎨 **Modern UX in Java Swing**  
  Fast, fluid, and clean user experience — unlike typical Swing apps.

---

## 🖌️ UI & UX Design

- 🌚 **Minimalist Dark Theme** — Professional dark interface with a vibrant green accent.  
- 🧱 **Custom Components** — Rounded `JPanel`, `JTextField`, and more, all hand-painted.  
- 🎯 **Interactive Feedback** — Subtle hover effects and transitions for responsiveness.  
- 🧑‍🎨 **Vector Icons** — Clean and modern icons to match the dark aesthetic.  
- 🅰️ **Typography** — Uses **Inter** and **JetBrains Mono** for clarity and readability.  
- 💬 **Styled Dialogs** — Consistent dark-mode `JOptionPane` dialogs.

---

## 🛠️ Tech Stack

| Layer         | Technology             |
|---------------|------------------------|
| Language      | Java (JDK 8+)          |
| UI Framework  | Java Swing             |
| Architecture  | MVC (Model-View-Controller) |
| Persistence   | Java Serialization     |
| Fonts         | Inter, JetBrains Mono  |

---

## 📁 Project Structure

```plaintext
Expense-Splitter/
└── src/
    └── com/
        └── expensesplitter/
            ├── gui/                 # UI panels for main app views
            │   ├── ExpensesView.java
            │   ├── DashboardView.java
            │   ├── MembersView.java
            │   ├── SummaryView.java
            │   └── MainFrame.java
            │
            ├── main/                # Application entry point
            │   └── ExpenseSplitter.java
            │
            ├── model/               # Core data models
            │   ├── Expense.java
            │   ├── Group.java
            │   └── Person.java
            │
            ├── service/             # Business logic & calculations
            │   ├── SplitService.java
            │   └── SimpleSplitService.java
            │
            ├── ui/                  # UI utilities and custom styles
            │   ├── IconFactory.java
            │   ├── UITheme.java
            │   └── components/
            │       ├── RoundedPanel.java
            │       ├── RoundedTextField.java
            │       └── CustomScrollBarUI.java
            │
            └── util/                # Utilities for file I/O, etc.
                └── DataManager.java
