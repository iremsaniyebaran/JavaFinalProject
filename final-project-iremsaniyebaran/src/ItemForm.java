import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ItemForm {

    private ComboBox<String> categoryBox;
    private TextField colorField;
    private TextField brandField;
    private TextField dynamicField;
    private Label dynamicLabel;

    private WardrobeItem resultItem = null;

    // --- Styling Constants ---
    private final String LABEL_STYLE         = "-fx-font-weight: bold; -fx-font-family: 'Segoe UI', sans-serif; -fx-text-fill: #2c3e50; -fx-font-size: 13px;";
    private final String INPUT_STYLE         = "-fx-background-color: #ffffff; -fx-border-color: #dcdde1; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 8; -fx-font-size: 13px;";
    private final String INPUT_ERROR_STYLE   = "-fx-background-color: #fff5f5; -fx-border-color: #e74c3c; -fx-border-width: 1.5; -fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 8; -fx-font-size: 13px;";
    private final String SAVE_BUTTON_STYLE   = "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Segoe UI', sans-serif; -fx-font-size: 13px; -fx-padding: 10 30; -fx-background-radius: 5; -fx-cursor: hand;";
    private final String CANCEL_BUTTON_STYLE = "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Segoe UI', sans-serif; -fx-font-size: 13px; -fx-padding: 10 30; -fx-background-radius: 5; -fx-cursor: hand;";
    private final String FORM_BG_STYLE       = "-fx-background-color: #f5f6fa;";
    private final String HEADER_BG_STYLE     = "-fx-background-color: #2c3e50; -fx-padding: 20 25;";

    /**
     * Displays the Item Form as a modal window.
     * @param title      The window title ("Add New Item" or "Edit Item").
     * @param itemToEdit Pass null when adding, or an existing item when editing.
     * @return The newly created or updated WardrobeItem, or null if cancelled.
     */
    public WardrobeItem display(String title, WardrobeItem itemToEdit) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setResizable(false);

        // -------------------------------------------------------
        // HEADER BLOCK
        // -------------------------------------------------------
        Label headerLabel = new Label(title);
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; " +
                             "-fx-text-fill: #ffffff; -fx-font-family: 'Segoe UI', sans-serif;");

        Label headerSub = new Label(itemToEdit == null
                ? "Fill in the details below to add a new item to your wardrobe."
                : "Update the details below and click Save to apply your changes.");
        headerSub.setStyle("-fx-font-size: 11px; -fx-text-fill: #bdc3c7; " +
                           "-fx-font-family: 'Segoe UI', sans-serif;");
        headerSub.setWrapText(true);

        VBox headerBox = new VBox(5, headerLabel, headerSub);
        headerBox.setStyle(HEADER_BG_STYLE);

        // -------------------------------------------------------
        // FORM GRID
        // -------------------------------------------------------
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25, 30, 10, 30));
        grid.setVgap(12);
        grid.setHgap(15);

        // Force the input column to grow and fill available width
        javafx.scene.layout.ColumnConstraints labelCol = new javafx.scene.layout.ColumnConstraints();
        labelCol.setMinWidth(110);
        javafx.scene.layout.ColumnConstraints inputCol = new javafx.scene.layout.ColumnConstraints();
        inputCol.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(labelCol, inputCol);

        // --- Classification (ComboBox) ---
        Label categoryLabel = new Label("Classification");
        categoryLabel.setStyle(LABEL_STYLE);
        categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Clothing", "Accessories");
        categoryBox.setStyle(INPUT_STYLE);
        categoryBox.setMaxWidth(Double.MAX_VALUE);

        // --- Brand Field ---
        Label brandLabel = new Label("Brand");
        brandLabel.setStyle(LABEL_STYLE);
        brandField = new TextField();
        brandField.setPromptText("e.g. Nike, Levi's, Fossil");
        brandField.setStyle(INPUT_STYLE);
        
        // Reset border to normal as soon as the user starts typing
        brandField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.trim().isEmpty()) brandField.setStyle(INPUT_STYLE);
        });

        // --- Color Field ---
        Label colorLabel = new Label("Primary Color");
        colorLabel.setStyle(LABEL_STYLE);
        colorField = new TextField();
        colorField.setPromptText("e.g. Midnight Blue, Crimson");
        colorField.setStyle(INPUT_STYLE);
        colorField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.trim().isEmpty()) colorField.setStyle(INPUT_STYLE);
        });

        // --- Dynamic Field (Size or Accessory Type) ---
        dynamicLabel = new Label("Size");
        dynamicLabel.setStyle(LABEL_STYLE);
        dynamicField = new TextField();
        dynamicField.setPromptText("e.g. M, L, XL, 32x30");
        dynamicField.setStyle(INPUT_STYLE);
        dynamicField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.trim().isEmpty()) dynamicField.setStyle(INPUT_STYLE);
        });

        // Listener: updates the dynamic label when classification changes
        categoryBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if ("Accessories".equals(newVal)) {
                dynamicLabel.setText("Accessory Type");
                dynamicField.setPromptText("e.g. Watch, Belt, Sunglasses");
            } else {
                dynamicLabel.setText("Size");
                dynamicField.setPromptText("e.g. M, L, XL, 32x30");
            }
            dynamicField.clear();
            dynamicField.setStyle(INPUT_STYLE);
        });

        // Default selection
        categoryBox.getSelectionModel().select("Clothing");

        // Add rows to the grid
        grid.add(categoryLabel, 0, 0);
        grid.add(categoryBox, 1, 0);
        grid.add(brandLabel, 0, 1);
        grid.add(brandField, 1, 1);
        grid.add(colorLabel, 0, 2);
        grid.add(colorField, 1, 2);
        grid.add(dynamicLabel, 0, 3);
        grid.add(dynamicField, 1, 3);

        // If editing an existing item, populate the fields
        if (itemToEdit != null) {
            brandField.setText(itemToEdit.getBrand());
            colorField.setText(itemToEdit.getColor());
            
            if (itemToEdit instanceof Clothing) {
                categoryBox.getSelectionModel().select("Clothing");
                dynamicField.setText(((Clothing) itemToEdit).getSize());
            } else if (itemToEdit instanceof Accessory) {
                categoryBox.getSelectionModel().select("Accessories");
                dynamicField.setText(((Accessory) itemToEdit).getAccessoryType());
            }
            // Prevent changing the type of an already existing item to avoid database conflicts
            categoryBox.setDisable(true);
        }

        // -------------------------------------------------------
        // ACTION BUTTONS & VALIDATION LOGIC
        // -------------------------------------------------------
        Button saveButton = new Button("Save Item");
        saveButton.setStyle(SAVE_BUTTON_STYLE);
        
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle(CANCEL_BUTTON_STYLE);
        cancelButton.setOnAction(e -> window.close());

        saveButton.setOnAction(e -> {
            boolean isValid = true;
            StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n\n");

            // Validate Brand
            if (brandField.getText() == null || brandField.getText().trim().isEmpty()) {
                brandField.setStyle(INPUT_ERROR_STYLE);
                errorMessage.append("- Brand cannot be empty.\n");
                isValid = false;
            }

            // Validate Color
            if (colorField.getText() == null || colorField.getText().trim().isEmpty()) {
                colorField.setStyle(INPUT_ERROR_STYLE);
                errorMessage.append("- Primary Color cannot be empty.\n");
                isValid = false;
            }

            // Validate Dynamic Field
            if (dynamicField.getText() == null || dynamicField.getText().trim().isEmpty()) {
                dynamicField.setStyle(INPUT_ERROR_STYLE);
                errorMessage.append("- ").append(dynamicLabel.getText()).append(" cannot be empty.\n");
                isValid = false;
            }

            // If validation fails, show error and stop
            if (!isValid) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation Error");
                alert.setHeaderText("Missing Information");
                alert.setContentText(errorMessage.toString());
                alert.showAndWait();
                return;
            }

            // If valid, create the object
            String selectedType = categoryBox.getValue();
            String brand = brandField.getText().trim();
            String color = colorField.getText().trim();
            String detail = dynamicField.getText().trim();

            if ("Clothing".equals(selectedType)) {
                resultItem = new Clothing(color, brand, detail);
            } else {
                resultItem = new Accessory(color, brand, detail);
            }
            
            window.close();
        });

        HBox actionBox = new HBox(15, cancelButton, saveButton);
        actionBox.setAlignment(Pos.CENTER_RIGHT);
        actionBox.setPadding(new Insets(15, 30, 25, 30));

        // Combine everything
        VBox root = new VBox(headerBox, grid, actionBox);
        root.setStyle(FORM_BG_STYLE);

        Scene scene = new Scene(root, 450, 400);
        window.setScene(scene);
        window.showAndWait();

        return resultItem;
    }
}