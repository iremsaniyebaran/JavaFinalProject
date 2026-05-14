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

    // These fields are accessible to the class methods
    private ComboBox<String> categoryBox;
    private TextField colorField;
    private TextField typeField;

    /**
     * Displays the form window.
     * @param title The title of the window (e.g., "Add New Item")
     */
    public void display(String title) {
        Stage window = new Stage();

        // Modality.APPLICATION_MODAL makes it so the user must close this window 
        // before interacting with the main window again.
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);

        // --- FORM LAYOUT (GridPane) ---
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25));
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        // Category Label & ComboBox
        Label categoryLabel = new Label("Category:");
        categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Tops", "Bottoms", "Outerwear", "Shoes", "Accessories");
        categoryBox.setPromptText("Select Category");
        categoryBox.setMaxWidth(Double.MAX_VALUE); // Make it fill the column

        // Color Label & TextField
        Label colorLabel = new Label("Color:");
        colorField = new TextField();
        colorField.setPromptText("e.g. Navy Blue");

        // Type/Brand Label & TextField
        Label typeLabel = new Label("Type/Brand:");
        typeField = new TextField();
        typeField.setPromptText("e.g. Denim Jacket");

        // Adding components to grid (Column, Row)
        grid.add(categoryLabel, 0, 0);
        grid.add(categoryBox, 1, 0);
        grid.add(colorLabel, 0, 1);
        grid.add(colorField, 1, 1);
        grid.add(typeLabel, 0, 2);
        grid.add(typeField, 1, 2);

        // --- BUTTONS (HBox) ---
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        // Modern styling to match WardrobeMain
        String primaryStyle = "-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20;";
        String cancelStyle = "-fx-background-color: #bdc3c7; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 8 20;";
        
        saveButton.setStyle(primaryStyle);
        cancelButton.setStyle(cancelStyle);

        // Button Actions
        saveButton.setOnAction(e -> handleSave(window));
        cancelButton.setOnAction(e -> window.close());

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        buttonBox.getChildren().addAll(cancelButton, saveButton);

        // Combining everything into a VBox
        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(grid, buttonBox);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setStyle("-fx-background-color: #ffffff;"); // Clean white background

        Scene scene = new Scene(mainLayout);
        window.setScene(scene);
        window.showAndWait(); // Waits for this window to close before returning to WardrobeMain
    }

    private void handleSave(Stage window) {
        // Placeholder for data validation and database logic
        String category = categoryBox.getValue();
        String color = colorField.getText();
        String type = typeField.getText();

        if (category == null || color.isEmpty() || type.isEmpty()) {
            System.out.println("Validation Error: All fields are required.");
        } else {
            System.out.println("Saving: " + category + ", " + color + ", " + type);
            // Later, we will add the code here to insert into SQLite
            window.close();
        }
    }
}
