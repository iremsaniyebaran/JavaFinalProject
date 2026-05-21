public class Accessory extends WardrobeItem {
    private String accessoryType;

    // Constructor with ID
    public Accessory(int id, String color, String brand, String accessoryType) {
        super(id, color, brand); // Calls the parent constructor
        this.accessoryType = accessoryType;
    }

    // Constructor without ID
    public Accessory(String color, String brand, String accessoryType) {
        super(color, brand); // Calls the parent constructor
        this.accessoryType = accessoryType;
    }

    // Getter and Setter
    public String getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) {
        this.accessoryType = accessoryType;
    }

    // Polymorphic method override
    @Override
    public String toString() {
        return super.toString() + " [Type: " + accessoryType + "]";
    }
}