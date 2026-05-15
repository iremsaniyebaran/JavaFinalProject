import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

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

        // Button actions
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

    // --- Navigation Methods ---

    private void handleAddItem() {
        ItemForm form = new ItemForm();
        form.display("Add New Item");
    }

    private void handleEditItem() {
        ClothingItem selected = table.getSelectionModel().getSelectedItem();

        if (selected != null) {
            ItemForm form = new ItemForm();
            form.display("Edit Item");
        } else {
            showNoSelectionAlert("Edit");
        }
    }

    private void handleDeleteItem() {
        ClothingItem selected = table.getSelectionModel().getSelectedItem();

        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Item");
            alert.setContentText("Are you sure you want to delete this item?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.out.println("Deleting from database: " + selected.getBrand());
            }
        } else {
            showNoSelectionAlert("Delete");
        }
    }

    private void showNoSelectionAlert(String action) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText(null);
        alert.setContentText("Please select an item in the table to " + action.toLowerCase() + ".");
        alert.showAndWait();
    }

    /**
     * Inner class representing the Data Model.
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