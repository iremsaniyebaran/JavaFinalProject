# Personal Wardrobe Management System

### Description
The Personal Wardrobe Management System is a JavaFX-based desktop application designed to help users digitize and organize their closets. It allows users to seamlessly categorize their belongings into clothing or accessories, track details like brand and size, and view everything in a clean, interactive dashboard.

### Features
* **Persistent SQLite Database:** All wardrobe items are securely saved locally using a Singleton Database Manager and DAO architecture.
* **Full CRUD Functionality:** Users can easily add new items, edit existing details, and permanently delete items from their digital closet.
* **Polymorphic Data Handling:** The system dynamically adjusts input fields and table displays based on whether an item is classified as Clothing (Size) or an Accessory (Type).
* **Robust Input Validation:** The application prevents crashes and database errors by strictly enforcing required fields and highlighting missing data with visual cues.
* **Dynamic Status Bar:** Real-time tracking of the total number of items currently stored in the wardrobe.

### Known Limitations & Future Improvements
* **No Image Support:** Currently, the application only stores text-based data. With more time, I would add the ability to upload and display thumbnail images for each wardrobe item.
* **Local Storage Only:** The database is stored locally on the user's machine. A future update could involve cloud synchronization for accessing the wardrobe across multiple devices.
* **Advanced Filtering:** Adding search and filter bars (e.g., "Show only Nike items" or "Show only blue clothes") would greatly improve navigation for larger wardrobes.