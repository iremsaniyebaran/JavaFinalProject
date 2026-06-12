import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton utility class responsible for managing the SQLite database connection lifecycle.
 * Enforces a single active database connection across the entire JavaFX application
 * to ensure thread safety and prevent database file locks.
 *
 * <p><strong>Design Pattern:</strong> Thread-safe Singleton Pattern</p>
 *
 * @author      İrem Saniye Baran
 * @version     1.0
 * @since       1.0
 */
public class DatabaseManager {

    private static DatabaseManager instance;
    private Connection connection;
    private final String URL = "jdbc:sqlite:wardrobe.db";

    /**
     * Private constructor to prevent direct instantiation from external classes.
     * Automatically registers the SQLite JDBC driver, establishes the connection,
     * and triggers the default table creation layout if it does not exist.
     */
    private DatabaseManager() {
        try {
            // Explicitly load the SQLite JDBC Driver
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            System.out.println("SQLite Database connection established successfully.");
            
            // Generate necessary schema on first launch
            createTable();
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: SQLite JDBC Driver not found. Add the JAR to your lib folder.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("ERROR: Failed to connect to the local SQLite database file.");
            e.printStackTrace();
        }
    }

    /**
     * Provides global access to the single, shared DatabaseManager instance.
     * Uses synchronized block implementation to ensure thread safety during initialization.
     *
     * @return the active Singleton instance of DatabaseManager
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Retrieves the active database connection session.
     * Used by Data Access Objects (DAOs) to execute database queries.
     *
     * @return the active {@link Connection} object
     */
    public Connection getConnection() {
        return connection;
        
    }

    /**
     * Creates the core database schema required for the system during initial setup.
     * Executes an structural DDL query to generate the 'wardrobe_items' table.
     */
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS wardrobe_items ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "item_type TEXT NOT NULL, "
                   + "brand TEXT NOT NULL, "
                   + "color TEXT NOT NULL, "
                   + "extra_detail TEXT"
                   + ");";
                   
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database schema verified: 'wardrobe_items' table is ready.");
        } catch (SQLException e) {
            System.err.println("ERROR: Failed to construct database schema tables.");
            e.printStackTrace();
        }
    }

    /**
     * Safely closes the active database connection when the application terminates.
     * Invoked automatically by the system shutdown hooks to prevent memory leaks.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed cleanly. No resources leaked.");
            } catch (SQLException e) {
                System.err.println("ERROR: Problem occurred while closing database connection.");
                e.printStackTrace();
            }
        }
    }
}