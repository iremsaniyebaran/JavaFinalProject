import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ItemForm {

    private ComboBox<String> categoryBox;
    private TextField colorField;
    private TextField brandField;
    private TextField dynamicField; // Acts as 'Size' or 'Accessory Type'
    private Label dynamicLabel;

    private WardrobeItem resultItem = null; // Stores the object we create/edit to pass back

    private final String LABEL_STYLE = "-fx-font-weight: bold; -fx-font-family: 'Segoe UI', sans-serif; -fx-text-fill: #2c3e50; -fx-font-size: 13px;";
    private final String INPUT_STYLE = "-fx-background-color: #ffffff; -fx-border-color: #dcdde1; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 8;";
    private final String SAVE_BUTTON_STYLE = "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 30; -fx-background-radius: 5; -fx-cursor: hand;";
    private final String CANCEL_BUTTON_STYLE = "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 30; -fx-background-radius: 5; -fx-cursor: hand;";
    private final String FORM_BG_COLOR = "-fx-background-color: #f5f6fa;";

    /**
     * Displays the form modal.
     * @param title Title of the window.
     * @param itemToEdit Null if adding, or an existing item if editing.
     * @return The created or modified WardrobeItem, or null if cancelled.
     */
    public WardrobeItem display(String title, WardrobeItem itemToEdit) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        // Header
        Label headerLabel = new Label(title);
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2f3640;");

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 0, 20, 0));
        grid.setVgap(15);
        grid.setHgap(15);
        grid.setAlignment(Pos.CENTER);

        // 1. Category Field
        Label categoryLabel = new Label("Classification");
        categoryLabel.setStyle(LABEL_STYLE);
        categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Clothing", "Accessories");
        categoryBox.setStyle(INPUT_STYLE);
        categoryBox.setMaxWidth(Double.MAX_VALUE);

        // 2. Brand Field
        Label brandLabel = new Label("Brand");
        brandLabel.setStyle(LABEL_STYLE);
        brandField = new TextField();
        brandField.setStyle(INPUT_STYLE);

        // 3. Color Field
        Label colorLabel = new Label("Color");
        colorLabel.setStyle(LABEL_STYLE);
        colorField = new TextField();
        colorField.setStyle(INPUT_STYLE);

        // 4. Dynamic Field (Changes based on selection)
        dynamicLabel = new Label("Size");
        dynamicLabel.setStyle(LABEL_STYLE);
        dynamicField = new TextField();
        dynamicField.setStyle(INPUT_STYLE);

        // Listener to change label text dynamically depending on the dropdown
        categoryBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if ("Accessories".equals(newVal)) {
                dynamicLabel.setText("Accessory Type");
                dynamicField.setPromptText("e.g. Watch, Sunglasses, Belt");
            } else {
                dynamicLabel.setText("Size");
                dynamicField.setPromptText("e.g. M, L, 32x30");
            }
        });

        // Default setup
        categoryBox.getSelectionModel().select("Clothing");
        dynamicField.setPromptText("e.g. M, L, 32x30");

        // Add components to layout grid
        grid.add(categoryLabel, 0, 0);
        grid.add(categoryBox, 1, 0);
        grid.add(brandLabel, 0, 1);
        grid.add(brandField, 1, 1);
        grid.add(colorLabel, 0, 2);
        grid.add(colorField, 1, 2);
        grid.add(dynamicLabel, 0, 3);
        grid.add(dynamicField, 1, 3);

        // Pre-fill values if we are editing an existing item
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
        }

        // Action Buttons
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        saveButton.setStyle(SAVE_BUTTON_STYLE);
        cancelButton.setStyle(CANCEL_BUTTON_STYLE);

        saveButton.setOnAction(e -> handleSave(window, itemToEdit));
        cancelButton.setOnAction(e -> window.close());

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(cancelButton, saveButton);

        VBox container = new VBox(10);
        container.setPadding(new Insets(30));
        container.setAlignment(Pos.TOP_CENTER);
        container.setStyle(FORM_BG_COLOR);
        container.getChildren().addAll(headerLabel, new Separator(), grid, buttonBox);

        Scene scene = new Scene(container, 400, 480);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();

        return resultItem; // This returns the object created/modified or null
    }

    private void handleSave(Stage window, WardrobeItem originalItem) {
        String category = categoryBox.getValue();
        String brand = brandField.getText();
        String color = colorField.getText();
        String extraDetail = dynamicField.getText();

        if (brand.trim().isEmpty() || color.trim().isEmpty() || extraDetail.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields are required before saving!");
            alert.showAndWait();
            return;
        }

        // Save Mode Logic: are we editing an old item or adding a new one?
        if (category.equals("Clothing")) {
            if (originalItem != null) {
                // Modify existing instance properties
                originalItem.setBrand(brand);
                originalItem.setColor(color);
                ((Clothing) originalItem).setSize(extraDetail);
                resultItem = originalItem;
            } else {
                // Create a completely brand new Clothing instance
                resultItem = new Clothing(color, brand, extraDetail);
            }
        } else {
            if (originalItem != null) {
                // Modify existing instance properties
                originalItem.setBrand(brand);
                originalItem.setColor(color);
                ((Accessory) originalItem).setAccessoryType(extraDetail);
                resultItem = originalItem;
            } else {
                // Create a completely brand new Accessory instance
                resultItem = new Accessory(color, brand, extraDetail);
            }
        }

        window.close();
    }
}