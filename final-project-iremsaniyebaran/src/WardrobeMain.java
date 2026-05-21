import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Optional;

public class WardrobeMain extends Application {

    private TableView<WardrobeItem> table;
    private ObservableList<WardrobeItem> wardrobeList;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Personal Wardrobe Management System");

        // Initialize our memory-based data list
        wardrobeList = FXCollections.observableArrayList();

        // Add some dummy data to start with so the GUI isn't empty
        wardrobeList.add(new Clothing("Red", "Nike", "L"));
        wardrobeList.add(new Accessory("Black", "Fossil", "Watch"));
        wardrobeList.add(new Clothing("Blue", "Levi's", "32x30"));

        // --- TOP: Menu Bar ---
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> primaryStage.close());
        fileMenu.getItems().add(exitItem);
        menuBar.getMenus().add(fileMenu);

        // --- CENTER: TableView ---
        table = new TableView<>();
        table.setItems(wardrobeList);

        // Column 1: Item Classification (Polymorphic display)
        TableColumn<WardrobeItem, String> typeCol = new TableColumn<>("Item Type");
        typeCol.setCellValueFactory(cellData -> {
            WardrobeItem item = cellData.getValue();
            return new SimpleStringProperty(item instanceof Clothing ? "Clothing" : "Accessory");
        });
        typeCol.setMinWidth(120);

        // Column 2: Brand
        TableColumn<WardrobeItem, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        brandCol.setMinWidth(150);

        // Column 3: Color
        TableColumn<WardrobeItem, String> colorCol = new TableColumn<>("Color");
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        colorCol.setMinWidth(120);

        // Column 4: Specific Details (Polymorphic display of Size vs Accessory Type)
        TableColumn<WardrobeItem, String> detailCol = new TableColumn<>("Specific Details");
        detailCol.setCellValueFactory(cellData -> {
            WardrobeItem item = cellData.getValue();
            if (item instanceof Clothing) {
                return new SimpleStringProperty("Size: " + ((Clothing) item).getSize());
            } else if (item instanceof Accessory) {
                return new SimpleStringProperty("Type: " + ((Accessory) item).getAccessoryType());
            }
            return new SimpleStringProperty("");
        });
        detailCol.setMinWidth(200);

        table.getColumns().addAll(typeCol, brandCol, colorCol, detailCol);
        table.setPlaceholder(new Label("Your wardrobe is empty!"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // --- BOTTOM: Action Buttons ---
        Button addButton = new Button("Add New Item");
        Button editButton = new Button("Edit Item");
        Button deleteButton = new Button("Delete Item");

        String buttonStyle = "-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-cursor: hand;";
        addButton.setStyle(buttonStyle);
        editButton.setStyle(buttonStyle);
        deleteButton.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-cursor: hand;");

        addButton.setOnAction(e -> handleAddItem());
        editButton.setOnAction(e -> handleEditItem());
        deleteButton.setOnAction(e -> handleDeleteItem());

        HBox actionBox = new HBox(15);
        actionBox.setPadding(new Insets(20));
        actionBox.setAlignment(Pos.CENTER);
        actionBox.getChildren().addAll(addButton, editButton, deleteButton);
        actionBox.setStyle("-fx-background-color: #ecf0f1;");

        // --- Layout ---
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(table);
        root.setBottom(actionBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleAddItem() {
        ItemForm form = new ItemForm();
        // Open the form. It returns a new WardrobeItem if saved, or null if canceled.
        WardrobeItem newItem = form.display("Add New Item", null);
        
        if (newItem != null) {
            wardrobeList.add(newItem);
        }
    }

    private void handleEditItem() {
        WardrobeItem selected = table.getSelectionModel().getSelectedItem();

        if (selected != null) {
            ItemForm form = new ItemForm();
            WardrobeItem updatedItem = form.display("Edit Item", selected);
            
            if (updatedItem != null) {
                // To trigger a table refresh, we replace or update the item inside the list
                int index = wardrobeList.indexOf(selected);
                wardrobeList.set(index, updatedItem);
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
            alert.setContentText("Are you sure you want to delete this " + selected.getColor() + " " + selected.getBrand() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                wardrobeList.remove(selected);
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

    public static void main(String[] args) {
        launch(args);
    }
}