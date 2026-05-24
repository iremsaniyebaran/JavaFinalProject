# Personal Wardrobe Management System

A desktop application built with Java and JavaFX to help users organize, track, and manage their closet items in a clean, modern dashboard.

## Week 3 Progress: OOP & GUI Finalization
* **Object-Oriented Programming (OOP):** Implemented an abstract base class `WardrobeItem` and two specialized subclasses: `Clothing` and `Accessory`, demonstrating inheritance and encapsulation.
* **Polymorphism:** The main `TableView` dynamically displays specific details depending on the instance type (e.g., Size for Clothing, Type for Accessories).
* **In-Memory Storage:** The GUI is fully wired to create real objects and store them in an `ObservableList` during runtime.
* **Modern UI:** Applied a professional, flat-design styling to the main dashboard using JavaFX inline CSS, featuring a hero banner and card-layout table.

**Next Steps:** Connect to an SQLite database for persistent storage (Week 4).