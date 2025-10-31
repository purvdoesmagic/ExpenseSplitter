# 💸 Expense-Splitter — Modern Java Swing Desktop App

A sleek, modern **desktop application for splitting group expenses**, built entirely with **Java Swing** from the ground up.

This project challenges the outdated perception of Java GUIs by showcasing a **beautiful, minimalist, dark-themed interface** packed with real-world functionality.

> 🎯 Perfect for roommates, trips, events, or any group activity where expenses need splitting.

---

## 🚀 Features at a Glance

- ✅ **Dynamic Group Creation**  
  Name your group on startup and begin tracking expenses instantly.

- ➕ **Effortless Member Management**  
  Add, edit, or remove members seamlessly with real-time updates.

- 💰 **Intuitive Expense Logging**  
  Record who paid, for what, how much, and who should share the cost.

- ⚖️ **Flexible & Fair Splitting**  
  Split costs equally or selectively between specific members.

- 🔁 **Smart Settlement Suggestions**  
  Automatically generate the **minimum number of transactions** needed to settle debts.

- 💾 **Persistent Data Storage**  
  Save and load your entire group using `.esg` files (via Java Serialization).

- 🧹 **Full Reset Option**  
  Start fresh anytime with the “Clear All Data” feature — wipes all groups, members, and expenses.

- 🌗 **Dual Theme Support**  
  Toggle between light and dark mode instantly with smooth theme transitions.

---

## 🖌️ UI & UX Design

- 🌚 **Minimalist Dark Theme** — Professional dark interface with vibrant green accents.  
- 🌞 **Light Theme** — Clean white panels with gray controls for a classic Swing look.  
- 🧱 **Custom Components** — Rounded panels, text fields, and scrollbars built for a polished modern UI.  
- 🎯 **Interactive Feedback** — Subtle hover effects and focus transitions for responsiveness.  
- 🧑‍🎨 **Vector Icons** — Flat, scalable icons matching the interface tone.  
- 🅰️ **Typography** — Uses *Inter* and *JetBrains Mono* for readability and style.  
- 💬 **Styled Dialogs** — All dialogs adapt to the current theme automatically.

---

## 🛠️ Tech Stack

| Layer         | Technology                  |
|---------------|-----------------------------|
| Language      | Java (JDK 8+)               |
| UI Framework  | Java Swing                  |
| Architecture  | MVC (Model-View-Controller) |
| Persistence   | Java Serialization          |
| Fonts         | Inter, JetBrains Mono       |

---

## 📁 Project Structure

```plaintext
Expense-Splitter/
└── src/
    └── com/
        └── expensesplitter/
            ├── gui/                 # GUI panels for all app views
            │   ├── DashboardView.java
            │   ├── MembersView.java
            │   ├── ExpensesView.java
            │   ├── SummaryView.java
            │   └── MainFrame.java
            │
            ├── main/                # Application entry point
            │   └── ExpenseSplitter.java
            │
            ├── model/               # Core data models
            │   ├── Person.java
            │   ├── Group.java
            │   └── Expense.java
            │
            ├── service/             # Logic for splitting expenses
            │   ├── SplitService.java
            │   └── SimpleSplitService.java
            │
            ├── ui/                  # Custom UI components & theme
            │   ├── UITheme.java
            │   ├── IconFactory.java
            │   └── components/
            │       ├── RoundedPanel.java
            │       ├── RoundedTextField.java
            │       └── CustomScrollBarUI.java
            │
            └── util/                # File handling & data persistence
                └── DataManager.java
