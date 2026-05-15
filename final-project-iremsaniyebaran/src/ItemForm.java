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
    private TextField typeField;

    // --- Modern Style Constants ---
    private final String LABEL_STYLE = "-fx-font-weight: bold; -fx-font-family: 'Segoe UI', sans-serif; -fx-text-fill: #2c3e50; -fx-font-size: 13px;";
    private final String INPUT_STYLE = "-fx-background-color: #ffffff; -fx-border-color: #dcdde1; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 8;";
    private final String SAVE_BUTTON_STYLE = "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 30; -fx-background-radius: 5; -fx-cursor: hand;";
    private final String CANCEL_BUTTON_STYLE = "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 30; -fx-background-radius: 5; -fx-cursor: hand;";
    private final String FORM_BG_COLOR = "-fx-background-color: #f5f6fa;";

    public void display(String title) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        // --- Header Section ---
        Label headerLabel = new Label(title);
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2f3640;");
        
        // --- Form Layout (GridPane) ---
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 0, 20, 0));
        grid.setVgap(15);
        grid.setHgap(15);
        grid.setAlignment(Pos.CENTER);

        // Category
        Label categoryLabel = new Label("Category");
        categoryLabel.setStyle(LABEL_STYLE);
        categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Tops", "Bottoms", "Outerwear", "Shoes", "Accessories");
        categoryBox.setPromptText("Select Category");
        categoryBox.setStyle(INPUT_STYLE);
        categoryBox.setMaxWidth(Double.MAX_VALUE);

        // Color
        Label colorLabel = new Label("Primary Color");
        colorLabel.setStyle(LABEL_STYLE);
        colorField = new TextField();
        colorField.setPromptText("e.g. Midnight Blue");
        colorField.setStyle(INPUT_STYLE);

        // Type
        Label typeLabel = new Label("Type or Brand");
        typeLabel.setStyle(LABEL_STYLE);
        typeField = new TextField();
        typeField.setPromptText("e.g. Cotton Hoodie");
        typeField.setStyle(INPUT_STYLE);

        // Adding to grid
        grid.add(categoryLabel, 0, 0);
        grid.add(categoryBox, 1, 0);
        grid.add(colorLabel, 0, 1);
        grid.add(colorField, 1, 1);
        grid.add(typeLabel, 0, 2);
        grid.add(typeField, 1, 2);

        // --- Buttons ---
        Button saveButton = new Button("Save Item");
        Button cancelButton = new Button("Cancel");

        saveButton.setStyle(SAVE_BUTTON_STYLE);
        cancelButton.setStyle(CANCEL_BUTTON_STYLE);

        saveButton.setOnAction(e -> handleSave(window));
        cancelButton.setOnAction(e -> window.close());

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(cancelButton, saveButton);

        // --- Main Container ---
        VBox container = new VBox(10);
        container.setPadding(new Insets(30));
        container.setAlignment(Pos.TOP_CENTER);
        container.setStyle(FORM_BG_COLOR);
        container.getChildren().addAll(headerLabel, new Separator(), grid, buttonBox);

        Scene scene = new Scene(container, 400, 450);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }

    private void handleSave(Stage window) {
        String category = categoryBox.getValue();
        String color = colorField.getText();
        String type = typeField.getText();

        if (category == null || color.trim().isEmpty() || type.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all fields before saving.");
            alert.showAndWait();
        } else {
            System.out.println("Form Data Validated: Ready to save " + type);
            window.close();
        }
    }
}