/**
 * Abstract base class representing a generic item in the wardrobe.
 * This class demonstrates encapsulation and serves as the foundation 
 * for polymorphism in the Wardrobe Management System.
 */
public abstract class WardrobeItem {
    
    private int id; // Database primary key
    private String color;
    private String brand;

    /**
     * Default constructor for creating a new item before saving to the database.
     */
    public WardrobeItem(String color, String brand) {
        this.color = color;
        this.brand = brand;
    }

    /**
     * Overloaded constructor for reconstructing an item loaded from the database.
     */
    public WardrobeItem(int id, String color, String brand) {
        this.id = id;
        this.color = color;
        this.brand = brand;
    }

    // --- Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}