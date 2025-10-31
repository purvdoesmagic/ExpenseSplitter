# 💸 Expense-Splitter — Java Swing Desktop Application  

## 📘 Abstract  

**Expense-Splitter** is a desktop application developed using **Java Swing** that efficiently manages and splits group expenses among multiple members.  
It automates the process of calculating individual contributions, settlements, and balances for shared expenses in events such as trips, parties, or household expenses.  
The project demonstrates the integration of **object-oriented design**, **GUI programming**, and **data persistence** within a modular **MVC (Model–View–Controller)** architecture.  

---

## 🎯 Objective  

The main objective of this project is to provide a user-friendly platform that simplifies the task of splitting shared expenses.  
It eliminates the need for manual calculations by maintaining organized records of who paid how much, how the costs are distributed, and who owes whom.  

---

## 🧩 Problem Statement  

In group activities, manually tracking expenses and ensuring fair contribution among members can become complex and error-prone.  
There is a need for a desktop-based application that:  
- Records each member’s contributions and shares  
- Automatically computes balances  
- Suggests settlement transactions  
- Stores and retrieves data persistently for later use  

---

## 🚀 Features  

- **Group Creation** – Create a new expense group by entering a name on startup  
- **Member Management** – Add or remove members dynamically  
- **Expense Management** – Record details of each expense (payer, amount, and participants)  
- **Automatic Splitting** – Compute individual shares and overall balances instantly  
- **Summary View** – Display a detailed overview of total expenses, paid amounts, owed amounts, and net balances  
- **Data Persistence** – Save and load entire groups with their data using serialized `.esg` files  
- **Full Reset** – A “Clear All Data” option to delete all saved information  
- **Theme Switching** – Toggle between **Light Mode** and **Dark Mode** for improved user experience  
- **Confirmation Dialogs** – Prevent accidental deletions or resets  

---

## ⚙️ System Design and Architecture  

The project follows the **MVC (Model–View–Controller)** pattern to ensure modularity and maintainability.  

### 🧠 Model Layer  
Handles all data representation through classes like **Person**, **Expense**, and **Group**.  
Each model is serializable and interacts with the file system via the **DataManager** class.  

### 🖥️ View Layer  
Comprises all user interface panels (**DashboardView**, **ExpensesView**, **MembersView**, and **SummaryView**) within `MainFrame.java`.  
Each view operates independently and communicates through event listeners.  

### ⚙️ Controller / Service Layer  
Contains the business logic (**SplitService** and **SimpleSplitService**) responsible for calculating individual shares and settlements.  

---

## 🧱 Module Descriptions  

### 1. GUI Module (`com.expensesplitter.gui`)  
Manages all user-interface components using **Java Swing**.  
Each screen (Dashboard, Members, Expenses, and Summary) is represented by a separate class.  
Layout managers and event listeners handle navigation and interaction.  

### 2. Model Module (`com.expensesplitter.model`)  
Defines data entities:  
- `Person` – Represents a participant in the group.  
- `Expense` – Represents each recorded transaction.  
- `Group` – Acts as a container for all members and expenses.  

### 3. Service Module (`com.expensesplitter.service`)  
Contains the **expense-splitting logic** implemented through interfaces and concrete service classes.  
`SimpleSplitService` determines each member’s net balance based on total contributions and shares.  

### 4. UI Module (`com.expensesplitter.ui`)  
Provides reusable UI utilities such as themes, icons, and custom components.  
- `UITheme` – Enables theme switching.  
- `IconFactory` – Handles icon management and loading.  

### 5. Utility Module (`com.expensesplitter.util`)  
Handles **data persistence** through the `DataManager` class.  
Performs serialization and deserialization for saving and loading sessions.  

---

## ⚖️ Expense Splitting Logic  

1. Each expense is linked to a payer and a list of beneficiaries.  
2. The total amount is divided equally among the selected participants.  
3. The service layer updates each member’s balance based on:  
   - **Amount Paid**  
   - **Amount Owed**  
4. The system automatically computes who should pay whom to settle balances with minimal transactions.  

---

## 💾 Data Management  

- **File Format:** `.esg` – A serialized Java object storing the entire group’s state.  
- **Persistence Management:** Implemented via `DataManager` using object streams.  
- **Scope:** Each group (members + expenses) is stored independently.  
- **Full Reset:** Invoking `DataManager.clearAllData()` removes all stored data.  

---

## 🖌️ UI and UX Design  

- **Clean Interface:** Consistent button sizing, centralized panels, and balanced layouts.  
- **Modern Swing Look:** White/light-gray backgrounds with accent highlights.  
- **Dark Mode Support:** Provides professional aesthetics and reduced eye strain.  
- **Custom Components:** Rounded text fields, panels, and scrollbars enhance the UI.  
- **User Feedback:** Hover highlights and confirmation prompts improve usability.  

---

## 🛠️ Technologies Used  

| Layer | Technology | Description |
|-------|-------------|-------------|
| Programming Language | **Java (JDK 8+)** | Core development |
| GUI Framework | **Java Swing** | Front-end design |
| Architecture | **MVC Pattern** | Separation of concerns |
| Data Handling | **Serialization** | Persistent storage |
| Design Utilities | **Custom UI Components** | Enhanced visual design |

---

## 📁 Project Structure

```plaintext
Expense-Splitter/
└── src/
    └── com/
        └── expensesplitter/
            ├── gui/                 # All GUI panels
            │   ├── MembersView.java
            │   ├── ExpensesView.java
            │   ├── SummaryView.java
            │   └── MainFrame.java
            │
            ├── main/                # Application entry point
            │   └── ExpenseSplitter.java
            │
            ├── model/               # Data models
            │   ├── Group.java
            │   ├── Person.java
            │   └── Expense.java
            │
            ├── service/             # Splitting and calculation logic
            │   ├── SplitService.java
            │   └── SimpleSplitService.java
            │
            ├── ui/                  # Themes and custom UI components
            │   ├── UITheme.java
            │   ├── IconFactory.java
            │   └── components/
            │       ├── RoundedPanel.java
            │       ├── RoundedTextField.java
            │       └── CustomScrollBarUI.java
            │
            └── util/                # Utilities
                └── DataManager.java

---

## 🧠 How the Application Works  

1. **Step 1:** Launch the application and create a new group.  
2. **Step 2:** Add members through the “Members” tab.  
3. **Step 3:** Record expenses under the “Expenses” tab.  
4. **Step 4:** The system calculates balances and displays them in “Summary.”  
5. **Step 5:** Save, load, or reset data as required.  

---

## 🪶 Future Enhancements  

- 📊 Integrate **visual charts and graphs** in the Summary view.  
- ☁️ Implement **cloud backup & sync** using Firebase or JSON APIs.  
- 💬 Add **notes or comments** to each expense.  
- 🧾 Enable **PDF or Excel export** of reports.  
- 📱 Develop a **JavaFX or Compose Desktop** version for modern UI.  

---

## 📷 Snapshots  

<img width="1919" height="1015" alt="Screenshot 2025-10-31 151323" src="https://github.com/user-attachments/assets/8386f2cd-dec6-4028-870a-b2ac4f4b1b0b" />
<img width="1918" height="1018" alt="Screenshot 2025-10-31 151346" src="https://github.com/user-attachments/assets/1a914986-87f4-41f1-9ceb-5fb1f5563e56" />
<img width="1919" height="1019" alt="Screenshot 2025-10-31 151356" src="https://github.com/user-attachments/assets/6dad3c24-067e-43a8-860d-88e3e428a8c3" />


---

## 🏁 Conclusion  

The **Expense-Splitter** project demonstrates the effective use of **Java Swing** in building an interactive, persistent, and visually polished desktop application.  
It emphasizes modular programming, data management, and clean user interface principles.  
Beyond academic evaluation, this project serves as a practical, real-world solution for fair and transparent expense management.  

---
