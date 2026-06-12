/**
 * Abstract base class representing a generic item in the Personal Wardrobe
 * Management System.
 *
 * @author      İrem Saniye Baran
 * @version     1.2
 */
public abstract class WardrobeItem {

    private int id = -1; // -1 = henüz veritabanına kaydedilmedi
    private String color;
    private String brand;

    /**
     * Default constructor for creating a new item before saving to the database.
     */
    public WardrobeItem(String color, String brand) {
        setColor(color);
        setBrand(brand);
    }

    /**
     * Overloaded constructor for reconstructing an item loaded from the database.
     */
    public WardrobeItem(int id, String color, String brand) {
        this.id = id;
        setColor(color);
        setBrand(brand);
    }

    /**
     * Abstract method that concrete subclasses must implement to return their category type.
     */
    public abstract String getCategory();

    // --- Getters and Setters with Input Validation ---

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
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color cannot be empty");
        }
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand cannot be empty");
        }
        this.brand = brand;
    }

    @Override
    public String toString() {
        return brand + " (" + color + ")";
    }
}