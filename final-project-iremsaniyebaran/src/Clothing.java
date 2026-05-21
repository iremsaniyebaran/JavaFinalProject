public class Clothing extends WardrobeItem {
    private String size;

    // Constructor with ID
    public Clothing(int id, String color, String brand, String size) {
        super(id, color, brand); // Calls the parent constructor
        this.size = size;
    }

    // Constructor without ID
    public Clothing(String color, String brand, String size) {
        super(color, brand); // Calls the parent constructor
        this.size = size;
    }

    // Getter and Setter
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    // Polymorphic method override
    @Override
    public String toString() {
        return super.toString() + " [Size: " + size + "]";
    }
}