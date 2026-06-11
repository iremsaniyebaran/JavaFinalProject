# Personal Wardrobe Management System

A desktop application built with Java and JavaFX to help users organize, track, and manage their closet items in a clean, modern dashboard.

## Week 3 Progress: OOP & GUI Finalization
* **Object-Oriented Programming (OOP):** Implemented an abstract base class `WardrobeItem` and two specialized subclasses: `Clothing` and `Accessory`, demonstrating inheritance and encapsulation.
* **Polymorphism:** The main `TableView` dynamically displays specific details depending on the instance type (e.g., Size for Clothing, Type for Accessories).
* **In-Memory Storage:** The GUI is fully wired to create real objects and store them in an `ObservableList` during runtime.
* **Modern UI:** Applied a professional, flat-design styling to the main dashboard using JavaFX inline CSS, featuring a hero banner and card-layout table.

## Week 5 Progress: Database Connection and CRUD
* **SQLite Integration:** Added `sqlite-jdbc` dependency to seamlessly connect the Java application to a local database.
* **DatabaseManager (Singleton):** Implemented a thread-safe Singleton class to ensure only one active database connection is open during runtime. The database file (`wardrobe.db`) and `wardrobe_items` table are created automatically on the first run.
* **Data Access Object (DAO):** Created `WardrobeDAO.java` to handle all SQL operations (INSERT, SELECT, UPDATE, DELETE) using `PreparedStatement` to prevent SQL injection.
* **GUI & Database Wiring:** Fully connected the JavaFX frontend (`WardrobeMain`) to the backend database. The TableView now loads real, persisted data upon startup, and all add, edit, and delete actions directly modify the SQLite database.

**Next Steps:** Polish the UI, add data validation, and finalize the application for the final project submission.

## Week 6 Progress: Finalization and Submission
* **Input Validation:** Added strict, field-by-field validation to `ItemForm.java` to prevent empty submissions, ensure data integrity, and provide specific error feedback to the user.
* **Edge Cases & UX:** Prevented application crashes from bad inputs and disabled the classification dropdown during item edits to avoid relational database conflicts.
* **UI Polish:** Integrated a dynamic Status Bar at the bottom of the main dashboard to continuously track and display the total number of wardrobe items.