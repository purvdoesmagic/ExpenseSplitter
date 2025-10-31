# 💸 Expense-Splitter — Modern Java Swing Desktop App

A sleek, modern **desktop application for splitting group expenses**, built entirely with **Java Swing**.  
This project demonstrates how Swing can still deliver **clean, modern, and efficient UIs** when structured well.

> 🎯 Perfect for trips, roommates, college events, or any group activity where expenses need to be divided fairly.

---

## 🚀 Key Features

### 🧭 1. Group Creation
- Create a **new group** when launching the app.  
- Each group acts as a workspace with its own members and expense list.

### 👥 2. Member Management
- Add or remove members dynamically.  
- Member list updates instantly across all views (Members, Expenses, Summary).  
- Each member is stored as a serialized object for persistence.

### 💰 3. Expense Tracking
- Record detailed expenses with:
  - Expense name  
  - Payer  
  - Amount  
  - People sharing that expense  
- Expenses are linked to their group and saved persistently.

### ⚖️ 4. Intelligent Splitting Logic
- Uses `SimpleSplitService` for precise and optimized settlement calculations.  
- Automatically computes **who owes whom**, minimizing total transactions.

### 📊 5. Detailed Summary View
- Displays a full table of:
  - All members and their net balances  
  - Each expense contribution  
  - Simplified list of settlement transactions  
- Designed for clarity and presentation — perfect for showing in a demo.

### 💾 6. Persistent Data Storage
- Uses `DataManager` (Java Serialization) to save and load `.esg` files.  
- Data remains intact even after closing the app.  
- You can manually save, load, or clear all data.

### 🔁 7. Full Reset Option
- “Clear All Data” instantly deletes **everything** — groups, members, and expenses.  
- Reloads the app with a clean state for testing or new groups.

### 🌗 8. Dual Theme Support
- **Light Theme:** Classic white panels, gray background (your chosen GUI reference).  
- **Dark Theme:** Elegant dark interface with green accent and smooth transitions.  
- Toggle instantly without restarting.

### 🎨 9. Modern Swing Interface
- Polished, consistent layout using `BorderLayout`, `FlowLayout`, and `CardLayout`.  
- Consistent component sizing (equal-length buttons).  
- Custom UI utilities like rounded text fields, scrollbars, and panels.  
- Styled dialogs for light/dark theme consistency.

---

## 🖥️ User Interface Highlights

| View | Description |
|------|--------------|
| **Dashboard View** | Central navigation for all features |
| **Members View** | Manage group members with live updates |
| **Expenses View** | Add, edit, and review expenses |
| **Summary View** | View balances and optimized settlements |

The **“View Summary”** button is available from both the Dashboard and the Expenses panel (bottom-aligned).

---

## 🛠️ Technical Overview

| Layer | Description |
|--------|--------------|
| **Model** | Data classes for Group, Person, and Expense |
| **View (GUI)** | Swing panels organized via `MainFrame` |
| **Service** | Logic for splitting, settlements, and validation |
| **Util** | File handling, saving/loading group data |
| **UI** | Theme management, icons, and custom UI components |

---

## 📁 Project Structure

```plaintext
Expense-Splitter/
└── src/
    └── com/
        └── expensesplitter/
            ├── gui/                 # All GUI Panels
            │   ├── DashboardView.java
            │   ├── MembersView.java
            │   ├── ExpensesView.java
            │   ├── SummaryView.java
            │   └── MainFrame.java
            │
            ├── main/                # Application Entry Point
            │   └── ExpenseSplitter.java
            │
            ├── model/               # Core Data Models
            │   ├── Person.java
            │   ├── Group.java
            │   └── Expense.java
            │
            ├── service/             # Splitting and Calculation Logic
            │   ├── SplitService.java
            │   └── SimpleSplitService.java
            │
            ├── ui/                  # UI Theme & Components
            │   ├── UITheme.java
            │   ├── IconFactory.java
            │   └── components/
            │       ├── RoundedPanel.java
            │       ├── RoundedTextField.java
            │       └── CustomScrollBarUI.java
            │
            └── util/                # File Handling
                └── DataManager.java
