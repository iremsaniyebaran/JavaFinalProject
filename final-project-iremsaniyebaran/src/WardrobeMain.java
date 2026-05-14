import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WardrobeMain extends Application {

    private TableView<ClothingItem> table;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Personal Wardrobe Management System");

        // --- TOP: Menu Bar ---
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> primaryStage.close());
        fileMenu.getItems().add(exitItem);
        
        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        helpMenu.getItems().add(aboutItem);
        
        menuBar.getMenus().addAll(fileMenu, helpMenu);

        // --- CENTER: TableView ---
        table = new TableView<>();
        
        TableColumn<ClothingItem, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryCol.setMinWidth(150);

        TableColumn<ClothingItem, String> colorCol = new TableColumn<>("Color");
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        colorCol.setMinWidth(100);

        TableColumn<ClothingItem, String> brandCol = new TableColumn<>("Brand/Type");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        brandCol.setMinWidth(200);

        table.getColumns().addAll(categoryCol, colorCol, brandCol);
        table.setPlaceholder(new Label("No clothing items found."));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // --- BOTTOM: Action Buttons ---
        Button addButton = new Button("Add New Item");
        Button editButton = new Button("Edit Item");
        Button deleteButton = new Button("Delete Item");

        // Simple styling for a modern look
        String buttonStyle = "-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;";
        addButton.setStyle(buttonStyle);
        editButton.setStyle(buttonStyle);
        deleteButton.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");

        // Button actions (Placeholders)
        addButton.setOnAction(e -> handleAddItem());
        editButton.setOnAction(e -> handleEditItem());
        deleteButton.setOnAction(e -> handleDeleteItem());

        HBox actionBox = new HBox(15);
        actionBox.setPadding(new Insets(20));
        actionBox.setAlignment(Pos.CENTER);
        actionBox.getChildren().addAll(addButton, editButton, deleteButton);

        // --- Main Layout ---
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(table);
        root.setBottom(actionBox);
        
        // Background color for the bottom area
        actionBox.setStyle("-fx-background-color: #ecf0f1;");

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- Placeholder Methods ---

    private void handleAddItem() {
        System.out.println("Add New Item clicked - Opening Dialog...");
        // Logic to open a new window for input will go here
    }

    private void handleEditItem() {
        ClothingItem selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Editing: " + selected.getBrand());
        } else {
            System.out.println("No item selected to edit.");
        }
    }

    private void handleDeleteItem() {
        ClothingItem selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Deleting: " + selected.getBrand());
        } else {
            System.out.println("No item selected to delete.");
        }
    }

    /**
     * Inner class representing the Data Model.
     * This makes it easy for the TableView to display data.
     */
    public static class ClothingItem {
        private final SimpleStringProperty category;
        private final SimpleStringProperty color;
        private final SimpleStringProperty brand;

        public ClothingItem(String category, String color, String brand) {
            this.category = new SimpleStringProperty(category);
            this.color = new SimpleStringProperty(color);
            this.brand = new SimpleStringProperty(brand);
        }

        public String getCategory() { return category.get(); }
        public String getColor() { return color.get(); }
        public String getBrand() { return brand.get(); }
    }

    public static void main(String[] args) {
        launch(args);
    }
}