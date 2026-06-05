import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WardrobeDAO {

    private final Connection connection;

    public WardrobeDAO() {
        this.connection = DatabaseManager.getInstance().getConnection();
    }

    // =========================================================
    //  CREATE — Insert a new WardrobeItem into the database
    // =========================================================

    public boolean insertItem(WardrobeItem item) {
        String sql = "INSERT INTO wardrobe_items (item_type, brand, color, extra_detail) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, getItemType(item));
            pstmt.setString(2, item.getBrand());
            pstmt.setString(3, item.getColor());
            pstmt.setString(4, getExtraDetail(item));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        item.setId(newId);
                        System.out.println("Item inserted successfully with ID: " + newId);
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("ERROR: Could not insert item into database.");
            e.printStackTrace();
        }
        return false;
    }

    // =========================================================
    //  READ — Retrieve all WardrobeItems from the database
    // =========================================================

    public List<WardrobeItem> getAllItems() {
        List<WardrobeItem> items = new ArrayList<>();
        String sql = "SELECT id, item_type, brand, color, extra_detail FROM wardrobe_items ORDER BY id ASC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String itemType = rs.getString("item_type");
                String brand = rs.getString("brand");
                String color = rs.getString("color");
                String extraDetail = rs.getString("extra_detail");

                WardrobeItem item = buildItem(id, itemType, brand, color, extraDetail);

                if (item != null) {
                    items.add(item);
                }
            }
            System.out.println("Loaded " + items.size() + " items from the database.");

        } catch (SQLException e) {
            System.err.println("ERROR: Could not retrieve items from the database.");
            e.printStackTrace();
        }
        return items;
    }

    // =========================================================
    //  UPDATE — Modify an existing WardrobeItem in the database
    // =========================================================

    public boolean updateItem(WardrobeItem item) {
        String sql = "UPDATE wardrobe_items SET item_type = ?, brand = ?, color = ?, extra_detail = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, getItemType(item));
            pstmt.setString(2, item.getBrand());
            pstmt.setString(3, item.getColor());
            pstmt.setString(4, getExtraDetail(item));
            pstmt.setInt(5, item.getId());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Item with ID " + item.getId() + " updated successfully.");
                return true;
            } else {
                System.out.println("WARNING: No item found with ID " + item.getId() + ". Nothing was updated.");
            }

        } catch (SQLException e) {
            System.err.println("ERROR: Could not update item with ID " + item.getId());
            e.printStackTrace();
        }
        return false;
    }

    // =========================================================
    //  DELETE — Remove a WardrobeItem from the database
    // =========================================================

    public boolean deleteItem(int itemId) {
        String sql = "DELETE FROM wardrobe_items WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Item with ID " + itemId + " deleted successfully.");
                return true;
            } else {
                System.out.println("WARNING: No item found with ID " + itemId + ". Nothing was deleted.");
            }

        } catch (SQLException e) {
            System.err.println("ERROR: Could not delete item with ID " + itemId);
            e.printStackTrace();
        }
        return false;
    }

    // =========================================================
    //  PRIVATE HELPER METHODS
    // =========================================================

    private String getItemType(WardrobeItem item) {
        if (item instanceof Clothing) {
            return "Clothing";
        } else if (item instanceof Accessory) {
            return "Accessory";
        }
        return "Unknown";
    }

    private String getExtraDetail(WardrobeItem item) {
        if (item instanceof Clothing) {
            return ((Clothing) item).getSize();
        } else if (item instanceof Accessory) {
            return ((Accessory) item).getAccessoryType();
        }
        return "";
    }

    private WardrobeItem buildItem(int id, String itemType, String brand,
                                   String color, String extraDetail) {
        WardrobeItem item = null;
        
        if ("Clothing".equalsIgnoreCase(itemType)) {
            item = new Clothing(color, brand, extraDetail);
        } else if ("Accessory".equalsIgnoreCase(itemType)) {
            item = new Accessory(color, brand, extraDetail);
        }
        
        if (item != null) {
            item.setId(id);
        }
        
        return item;
    }
}