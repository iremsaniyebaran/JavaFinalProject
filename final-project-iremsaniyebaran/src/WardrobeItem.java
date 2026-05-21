public abstract class WardrobeItem {
    private int id; // Useful for SQLite primary keys
    private String color;
    private String brand;

    // Constructor for creating an item with an ID (e.g., loaded from database)
    public WardrobeItem(int id, String color, String brand) {
        this.id = id;
        this.color = color;
        this.brand = brand;
    }

    // Constructor without ID (useful for creating new items before saving to database)
    public WardrobeItem(String color, String brand) {
        this.id = -1; // -1 represents an unsaved item
        this.color = color;
        this.brand = brand;
    }

    // Getters and Setters
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

    // A helper method for debugging
    @Override
    public String toString() {
        return String.format("%s (Color: %s, Brand: %s)", getClass().getSimpleName(), color, brand);
    }
}