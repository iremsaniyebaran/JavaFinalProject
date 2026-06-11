import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class WardrobeMain extends Application {

    private TableView<WardrobeItem> table;
    private ObservableList<WardrobeItem> wardrobeList;
    private WardrobeDAO dao;

    // Status bar label — declared as a field so all methods can update it
    private Label statusLabel;

    // --- Styling Constants ---
    private final String HEADER_BG_STYLE    = "-fx-background-color: #2c3e50; -fx-padding: 20 25 20 25;";
    private final String MAIN_BG_COLOR      = "-fx-background-color: #f5f6fa;";
    private final String BUTTON_ADD_STYLE   = "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Segoe UI', sans-serif; -fx-font-size: 13px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;";
    private final String BUTTON_EDIT_STYLE  = "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Segoe UI', sans-serif; -fx-font-size: 13px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;";
    private final String BUTTON_DELETE_STYLE= "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Segoe UI', sans-serif; -fx-font-size: 13px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;";
    private final String TABLE_CARD_STYLE   = "-fx-background-color: #ffffff; -fx-background-radius: 8; -fx-border-color: #dcdde1; -fx-border-width: 1; -fx-border-radius: 8; -fx-padding: 5;";
    private final String STATUS_BAR_STYLE   = "-fx-background-color: #dfe6e9; -fx-padding: 6 15; -fx-border-color: #b2bec3; -fx-border-width: 1 0 0 0;";
    private final String STATUS_LABEL_STYLE = "-fx-font-size: 12px; -fx-text-fill: #636e72; -fx-font-family: 'Segoe UI', sans-serif; -fx-font-weight: bold;";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Personal Wardrobe Management System");

        // Initialize DAO and load data
        dao = new WardrobeDAO();
        wardrobeList = FXCollections.observableArrayList();
        loadItemsFromDatabase();

        // --- TOP: Menu Bar ---
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #dcdde1; -fx-border-width: 0 0 1 0;");
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> handleExit(primaryStage));
        fileMenu.getItems().add(exitItem);
        menuBar.getMenus().add(fileMenu);

        // --- Hero Header Banner ---
        Label titleLabel = new Label("My Digital Wardrobe");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-font-family: 'Segoe UI', sans-serif;");

        Label subtitleLabel = new Label("Organize, track, and manage your closet items in one clean dashboard.");
        subtitleLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #bdc3c7; -fx-font-family: 'Segoe UI', sans-serif;");

        VBox headerTextContainer = new VBox(4, titleLabel, subtitleLabel);
        VBox topPane = new VBox(headerTextContainer);
        topPane.setStyle(HEADER_BG_STYLE);

        VBox menuAndHeader = new VBox(menuBar, topPane);

        // --- CENTER: TableView ---
        table = new TableView<>();
        table.setItems(wardrobeList);
        table.setStyle("-fx-background-color: transparent; -fx-table-cell-border-color: #f1f2f6;");

        TableColumn<WardrobeItem, String> typeCol = new TableColumn<>("Classification");
        typeCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue() instanceof Clothing ? "Clothing" : "Accessory"));
        typeCol.setMinWidth(120);

        TableColumn<WardrobeItem, String> brandCol = new TableColumn<>("Brand / Label");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        brandCol.setMinWidth(150);

        TableColumn<WardrobeItem, String> colorCol = new TableColumn<>("Primary Color");
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        colorCol.setMinWidth(120);

        TableColumn<WardrobeItem, String> detailCol = new TableColumn<>("Item Details");
        detailCol.setCellValueFactory(cellData -> {
            WardrobeItem item = cellData.getValue();
            if (item instanceof Clothing)
                return new SimpleStringProperty("Size: " + ((Clothing) item).getSize());
            else if (item instanceof Accessory)
                return new SimpleStringProperty("Type: " + ((Accessory) item).getAccessoryType());
            return new SimpleStringProperty("");
        });
        detailCol.setMinWidth(200);

        table.getColumns().addAll(typeCol, brandCol, colorCol, detailCol);
        table.setPlaceholder(new Label("Your wardrobe is empty. Click '+ Add New Item' to get started!"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox tableContainer = new VBox(table);
        tableContainer.setStyle(TABLE_CARD_STYLE);
        VBox.setVgrow(table, Priority.ALWAYS);

        VBox centerPane = new VBox(tableContainer);
        centerPane.setPadding(new Insets(20));
        centerPane.setStyle(MAIN_BG_COLOR);
        VBox.setVgrow(tableContainer, Priority.ALWAYS);

        // --- BOTTOM: Action Buttons Row ---
        Button addButton    = new Button("+ Add New Item");
        Button editButton   = new Button("✎ Edit Item");
        Button deleteButton = new Button("🗑 Delete Item");

        addButton.setTooltip(new Tooltip("Add a new piece of clothing or accessory"));
        editButton.setTooltip(new Tooltip("Edit the selected item's details"));
        deleteButton.setTooltip(new Tooltip("Permanently remove the selected item"));

        addButton.setStyle(BUTTON_ADD_STYLE);
        editButton.setStyle(BUTTON_EDIT_STYLE);
        deleteButton.setStyle(BUTTON_DELETE_STYLE);

        addButton.setOnAction(e -> handleAddItem());
        editButton.setOnAction(e -> handleEditItem());
        deleteButton.setOnAction(e -> handleDeleteItem());

        HBox actionBox = new HBox(15, addButton, editButton, deleteButton);
        actionBox.setPadding(new Insets(15, 25, 15, 25)); // Adjusted padding for status bar room
        actionBox.setAlignment(Pos.CENTER_RIGHT);
        actionBox.setStyle(MAIN_BG_COLOR);

        // --- STATUS BAR ---
        statusLabel = new Label();
        statusLabel.setStyle(STATUS_LABEL_STYLE);
        updateStatusBar(); // Initialize the count

        HBox statusBar = new HBox(statusLabel);
        statusBar.setStyle(STATUS_BAR_STYLE);
        statusBar.setAlignment(Pos.CENTER_LEFT);

        VBox bottomPane = new VBox(actionBox, statusBar);

        BorderPane root = new BorderPane();
        root.setTop(menuAndHeader);
        root.setCenter(centerPane);
        root.setBottom(bottomPane);

        Scene scene = new Scene(root, 850, 680); // Made slightly taller for the status bar
        primaryStage.setScene(scene);
        
        primaryStage.setOnCloseRequest(e -> handleExit(primaryStage));
        primaryStage.show();
    }

    // --- CRUD DAO OPERATIONS ---

    private void loadItemsFromDatabase() {
        List<WardrobeItem> items = dao.getAllItems();
        wardrobeList.setAll(items);
        if (statusLabel != null) updateStatusBar();
    }

    private void handleAddItem() {
        ItemForm form = new ItemForm();
        WardrobeItem newItem = form.display("Add New Item", null);
        
        if (newItem != null) {
            boolean success = dao.insertItem(newItem);
            if (success) {
                wardrobeList.add(newItem);
                updateStatusBar(); // Update count on add
            } else {
                showErrorAlert("Could not add item to database.");
            }
        }
    }

    private void handleEditItem() {
        WardrobeItem selected = table.getSelectionModel().getSelectedItem();

        if (selected != null) {
            ItemForm form = new ItemForm();
            WardrobeItem updatedItem = form.display("Edit Item", selected);
            
            if (updatedItem != null) {
                updatedItem.setId(selected.getId());
                boolean success = dao.updateItem(updatedItem);
                
                if (success) {
                    int index = wardrobeList.indexOf(selected);
                    wardrobeList.set(index, updatedItem);
                } else {
                    showErrorAlert("Could not update item in database.");
                }
            }
        } else {
            showNoSelectionAlert("Edit");
        }
    }

    private void handleDeleteItem() {
        WardrobeItem selected = table.getSelectionModel().getSelectedItem();

        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Item");
            alert.setContentText("Are you sure you want to permanently delete this " + selected.getColor() + " " + selected.getBrand() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean success = dao.deleteItem(selected.getId());
                if (success) {
                    wardrobeList.remove(selected);
                    updateStatusBar(); // Update count on delete
                } else {
                    showErrorAlert("Could not delete item from database.");
                }
            }
        } else {
            showNoSelectionAlert("Delete");
        }
    }
    
    private void handleExit(Stage stage) {
        DatabaseManager.getInstance().closeConnection();
        stage.close();
    }

    // --- UTILITIES ---

    /**
     * Updates the text in the status bar based on the current list size.
     */
    private void updateStatusBar() {
        if (wardrobeList != null && statusLabel != null) {
            int total = wardrobeList.size();
            statusLabel.setText("Total Items in Wardrobe: " + total);
        }
    }

    private void showNoSelectionAlert(String action) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText(null);
        alert.setContentText("Please select an item in the table to " + action.toLowerCase() + ".");
        alert.showAndWait();
    }
    
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}