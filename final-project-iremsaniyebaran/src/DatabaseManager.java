import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    // The database file will be created in your project's root directory
    private static final String DB_URL = "jdbc:sqlite:wardrobe.db";

    // The single shared instance (Singleton Pattern)
    private static DatabaseManager instance;

    // The single shared connection object
    private Connection connection;

    /**
     * Private constructor — prevents other classes from calling 'new DatabaseManager()'.
     * It immediately establishes the connection and sets up the schema.
     */
    private DatabaseManager() {
        try {
            // Step 1: Establish the connection to the SQLite file.
            // SQLite will automatically CREATE the .db file if it doesn't exist yet.
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Database connection established successfully.");

            // Step 2: Immediately create the table if it doesn't already exist.
            initializeDatabase();

        } catch (SQLException e) {
            System.err.println("CRITICAL ERROR: Could not connect to the database.");
            e.printStackTrace();
        }
    }

    /**
     * The global access point for the DatabaseManager instance.
     * This is called from any class that needs database access.
     * @return The single shared DatabaseManager instance.
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Returns the active connection object.
     * Used by other methods (like in a future WardrobeDAO) to run SQL queries.
     * @return The active SQLite Connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Creates the wardrobe_items table if it does not already exist.
     * This is safe to call every time the app starts because of 'IF NOT EXISTS'.
     */
    private void initializeDatabase() {
        // SQL statement to create our table
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS wardrobe_items (
                    id          INTEGER PRIMARY KEY AUTOINCREMENT,
                    item_type   TEXT    NOT NULL,
                    brand       TEXT    NOT NULL,
                    color       TEXT    NOT NULL,
                    extra_detail TEXT   NOT NULL
                );
                """;

        // A 'Statement' is the standard JDBC object for running SQL commands
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Database schema verified. Table 'wardrobe_items' is ready.");
        } catch (SQLException e) {
            System.err.println("ERROR: Could not create the wardrobe_items table.");
            e.printStackTrace();
        }
    }

    /**
     * Safely closes the database connection.
     * This should be called when the application is shutting down.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed cleanly.");
            } catch (SQLException e) {
                System.err.println("ERROR: Could not close the database connection.");
                e.printStackTrace();
            }
        }
    }
}