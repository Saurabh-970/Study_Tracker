# 📚 Study Tracker Application (Java CLI + GUI)

## 🚀 Overview

The **Study Tracker Application** is a Java-based project designed to help users log, manage, and analyze their daily study activities.

It provides **two modes of interaction**:

* 🖥️ **CLI (Command Line Interface)**
* 🎨 **GUI (Java Swing Dashboard)**

Users can record study sessions, view logs, and export data into a CSV file for further analysis.

---

## 🛠️ Tech Stack

* **Language:** Java
* **UI Framework:** Swing (AWT)
* **Concepts Used:**

  * Object-Oriented Programming (OOP)
  * Collections (`ArrayList`)
  * File Handling (`FileWriter`)
  * Event-Driven Programming
  * Java Time API (`LocalDate`)

---

## 📂 Project Architecture

The project follows a **layered architecture design**:

```
StudyTrackerApplication
│
├── Study_Log              (Model Layer)
├── Study_Tracker          (Service Layer)
└── StudyTrackerGUI        (Presentation Layer)
```

---

## 🧠 Class Breakdown

### 🔹 1. Study_Log (Model Class)

Represents a single study entry.

**Attributes:**

* Date
* Subject
* Duration
* Description

**Responsibilities:**

* Store data
* Provide getters
* Format output using `toString()`

---

### 🔹 2. Study_Tracker (Service Class)

Handles all business logic.

**Functions:**

* Insert new study log
* Display logs (CLI)
* Export logs to CSV file

**Data Structure Used:**

```java
ArrayList<Study_Log> Database
```

---

### 🔹 3. StudyTrackerGUI (Presentation Layer)

Provides a user-friendly graphical interface.

**Features:**

* Input form for study logs
* Table to display logs
* Buttons for:

  * Add Log
  * View Logs
  * Export CSV

**Concepts Used:**

* Swing Components (`JFrame`, `JTable`, `JButton`)
* Layout Managers (`BorderLayout`, `GridLayout`)
* Event Handling (`ActionListener`)

---

### 🔹 4. StudyTrackerApplication (Main Class)

Acts as the entry point of the application.

**Responsibilities:**

* Allows user to choose:

  * CLI Mode
  * GUI Mode
* Controls program flow

---

## ⚙️ Features

### ✅ Add Study Log

* Captures subject, duration, and description
* Automatically records current date

### ✅ View Logs

* Displays logs in CLI or GUI table

### ✅ Export to CSV

* File: `StudyTracker.csv`
* Compatible with Excel / Google Sheets

### ✅ Dual Mode Support

* CLI for simplicity
* GUI for better user experience

---

## 📸 Workflow

```
Start Application
      ↓
Choose Mode (CLI / GUI)
      ↓
---------------------------
|        CLI Mode         |
---------------------------
      ↓
Menu → Input → Process → Repeat

---------------------------
|        GUI Mode         |
---------------------------
      ↓
Fill Form → Click Button → Display Data
```

---

## ▶️ How to Run

### 1. Compile

```bash
javac StudyTrackerApplication.java
```

### 2. Run

```bash
java StudyTrackerApplication
```

### 3. Select Mode

```
1 → CLI Mode  
2 → GUI Mode  
```

---

## 📊 Sample Output

### CLI Output

```
2026-04-01 | Java | 2.0 | OOP Concepts
2026-04-01 | Python | 1.5 | DSA Practice
```

---

### CSV Output

```
Date,Subject,Duration,Description
2026-04-01,Java,2.0,OOP Concepts
2026-04-01,Python,1.5,DSA Practice
```

---

## 🎨 GUI Highlights

* Clean and modern UI
* Color-coded buttons
* Table view for logs
* Easy-to-use input form

---

## 💡 Future Enhancements

* 💾 Persistent storage (Database / File system)
* 📊 Graphs & analytics dashboard
* 🌙 Dark mode UI
* 🔐 User login system
* 📅 Filter logs by date range

---

## 🧑‍💻 Author

**Saurabh Bhonsle**
📍 Pune, India

---

## ⭐ Contribution

Feel free to fork and improve this project!

---

## 📌 License

This project is open-source and free to use.

