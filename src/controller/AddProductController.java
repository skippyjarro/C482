package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This Class controls the Add Product Screen
 */
public class AddProductController implements Initializable {

    ObservableList<Part> newAssociatedParts = FXCollections.observableArrayList();
    Product product;

    @FXML
    public TextField productIDField;

    @FXML
    public TextField productNameField;

    @FXML
    public TextField productStockField;

    @FXML
    public TextField productCostField;

    @FXML
    public TextField productMaxField;

    @FXML
    public TextField productMinField;

    @FXML
    public Label partSearchResults;

    @FXML
    public TextField searchAvailablePartsField;

    @FXML
    public TableView availablePartsTable;

    @FXML
    public TableColumn availablePartIdCol;

    @FXML
    public TableColumn availablePartNameCol;

    @FXML
    public TableColumn availablePartStockCol;

    @FXML
    public TableColumn availablePartCostCol;

    @FXML
    public Button addAssociatedPartsButton;

    @FXML
    public TableView associatedPartsTable;

    @FXML
    public TableColumn associatedPartIdCol;

    @FXML
    public TableColumn associatedPartNameCol;

    @FXML
    public TableColumn associatedPartStockCol;

    @FXML
    public TableColumn associatedPartCostCol;

    @FXML
    public Button removeAssociatedPartsButton;

    @FXML
    public Button saveAddProductButton;

    @FXML
    public Button cancelAddProductButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        availablePartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        availablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availablePartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availablePartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        availablePartsTable.setItems(Inventory.getAllParts());

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartsTable.setItems(newAssociatedParts);
    }

    /**
     * This method nvaigates the user back to the Inventory Manager screen
     *
     * @param actionEvent Button click event
     * @throws IOException Incorrect input
     */
    public void backToInventoryManager(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/InventoryManager.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1024, 400);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    /**
     * This method is called when the user clicks the Save button. It performs field validations then saves the new product to the inventory.
     *
     * @param actionEvent Save button click event
     * @throws IOException Incorrect input
     */
    public void saveNewProduct(ActionEvent actionEvent) throws IOException {
        int newProductId = Inventory.getNextProductId();
        String newProductName = productNameField.getText();
        int newProductStock;
        double newProductPrice;
        int newProductMax;
        int newProductMin;

        //Check if name has valid text
        if (newProductName.equals("") || newProductName == null) {
            Alert companyNameAlert = new Alert(Alert.AlertType.ERROR);
            companyNameAlert.setHeaderText("Part Name is a required field");
            companyNameAlert.showAndWait();
            return;
        }

        //Check if stock fields have valid integers
        try {
            newProductStock = Integer.parseInt(productStockField.getText());
            newProductMin = Integer.parseInt(productMinField.getText());
            newProductMax = Integer.parseInt(productMaxField.getText());
        } catch (NumberFormatException e) {
            Alert stockAlert = new Alert(Alert.AlertType.ERROR);
            stockAlert.setHeaderText("Stock fields should be whole numbers");
            stockAlert.showAndWait();
            return;
        }

        //Check if price field has valid double
        try {
            newProductPrice = Double.parseDouble(productCostField.getText());
        } catch (NumberFormatException e) {
            Alert stockAlert = new Alert(Alert.AlertType.ERROR);
            stockAlert.setHeaderText("Price field should be number");
            stockAlert.showAndWait();
            return;
        }

        //Allow only positive numbers in stock and price fields
        if (newProductStock < 0 || newProductPrice < 0 || newProductMax < 0 || newProductMin < 0) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Only non-negative numbers are allowed");
            errorAlert.showAndWait();
            return;
        }

        //Check if min stock is less than max stock
        if (newProductMin >= newProductMax) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Minimum stock must be less than Maximum stock");
            errorAlert.showAndWait();
            return;
        }

        //Add new product to inventory
        product = new Product(newProductId, newProductName, newProductPrice, newProductStock, newProductMin, newProductMax);
        Inventory.addProduct(product);
        for (Part p : newAssociatedParts) {
            product.addAssociatedPart(p);
        }

        //Navigate back to Inventory Manager screen
        backToInventoryManager(actionEvent);
    }

    /**
     * This method is called when the user clicks the Associate part button.
     * @param actionEvent Associate part button click event
     */
    public void associatePart(ActionEvent actionEvent) {
        Part selectedPart = (Part) availablePartsTable.getSelectionModel().getSelectedItem();
        if (Inventory.getAllParts().size() == 0) {
            Alert selectPartAlert = new Alert(Alert.AlertType.ERROR);
            selectPartAlert.setHeaderText("No parts to associate");
            selectPartAlert.showAndWait();
            return;
        } else if (selectedPart == null) {
            Alert selectPartAlert = new Alert(Alert.AlertType.ERROR);
            selectPartAlert.setHeaderText("Must select a part");
            selectPartAlert.showAndWait();
            return;
        } else {
            newAssociatedParts.add(selectedPart);
        }
    }

    /**
     * This method is called with the user clicks the Remove associated part button. It validates that there are parts to remove and the user has selected a part before asking for confirmation and removing the part.
     * @param actionEvent Remove associated part button click event
     */
    public void removeAssociatedPart(ActionEvent actionEvent) {
        Part selectedPart = (Part) associatedPartsTable.getSelectionModel().getSelectedItem();
        if (newAssociatedParts.size() == 0) {
            Alert selectPartAlert = new Alert(Alert.AlertType.ERROR);
            selectPartAlert.setHeaderText("No parts to remove");
            selectPartAlert.showAndWait();
            return;
        } else if (selectedPart == null) {
            Alert selectPartAlert = new Alert(Alert.AlertType.ERROR);
            selectPartAlert.setHeaderText("Must select a part");
            selectPartAlert.showAndWait();
            return;
        } else {
            Alert confirmRemove = new Alert(Alert.AlertType.CONFIRMATION);
            confirmRemove.setHeaderText("This will remove the selected part from this product. \nDo you wish to continue?");
            confirmRemove.setContentText("Click OK to proceed.");
            confirmRemove.showAndWait();
            if (confirmRemove.getResult() == ButtonType.CANCEL) {
                return;
            }
            newAssociatedParts.remove(selectedPart);
        }
    }

    /**
     * This method is called when the user types in the Search parts text field. It filters the table to show parts matching the search text and displays a message showing the number of results found.
      * @param keyEvent
     */
    public void searchParts(KeyEvent keyEvent) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        String searchText = searchAvailablePartsField.getText();
        int searchID;
        try {
            searchID = Integer.parseInt(searchText);
            parts.add(Inventory.lookupPart(searchID));
        } catch (Exception e) {
            parts = Inventory.lookupPart(searchText);
        }
        if (parts.size() > 1 || parts.size() < 1) {
            partSearchResults.setText(parts.size() + " Results Found");
        } else if (parts.size() == 1) {
            partSearchResults.setText(parts.size() + " Result Found");
        }
        availablePartsTable.setItems(parts);
    }
}
