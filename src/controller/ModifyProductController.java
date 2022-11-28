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
 * This Class controls the Modify Product screen
 */
public class ModifyProductController implements Initializable {
    Product selectedProduct = null;

    @FXML
    public Label partSearchResults;

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
    public TextField searchAssociatedPartsField;

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
    public Button saveModifyProductButton;

    @FXML
    public Button cancelModifyProductButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = Inventory.getSelectedProduct();

        productIDField.setText(String.valueOf(selectedProduct.getId()));
        productNameField.setText(selectedProduct.getName());
        productStockField.setText(String.valueOf(selectedProduct.getStock()));
        productCostField.setText(String.valueOf(selectedProduct.getPrice()));
        productMaxField.setText(String.valueOf(selectedProduct.getMax()));
        productMinField.setText(String.valueOf(selectedProduct.getMin()));

        //Populate available parts table
        availablePartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        availablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availablePartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availablePartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        availablePartsTable.setItems(Inventory.getAllParts());

        //Populate associated parts table
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartsTable.setItems(selectedProduct.getAllAssociatedParts());
    }

    /**
     * The method navigates the user back to the Inventory Manager screen after a successful save or if the user clicks the Cancel button.
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
     * This method is called when the user clicks the Associate part button. It validates that there are parts to associate and the user has selected a part prior to associating.
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
            Inventory.getSelectedProduct().addAssociatedPart(selectedPart);
        }
    }

    /**
     * This method is called with the user clicks the Remove associated part button. It performs validation that there are parts to remove and the user has selected a part prior to asking for confirmation.
     *
     * @param actionEvent Remove button click event
     */
    public void removeAssociatedPart(ActionEvent actionEvent) {
        Part selectedPart = (Part) associatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct.getAllAssociatedParts().size() == 0) {
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
            Inventory.getSelectedProduct().deleteAssociatedPart(selectedPart);
        }
    }

    /**
     * This method is called when the user clicks the Save product button. It validates that all fields contain required information prior to adding a new product to the Inventory.
     * @param actionEvent Save product button click event
     * @throws IOException Incorrect input
     */
    public void saveModifyProduct(ActionEvent actionEvent) throws IOException {
        int productId = Integer.parseInt(productIDField.getText());
        String productName = productNameField.getText();
        int productStock;
        double productPrice;
        int productMax;
        int productMin;
        ObservableList<Part> parts;
        parts = Inventory.getSelectedProduct().getAllAssociatedParts();

        //Check if name has valid text
        if (productName.equals("") || productName == null) {
            Alert companyNameAlert = new Alert(Alert.AlertType.ERROR);
            companyNameAlert.setHeaderText("Part Name is a required field");
            companyNameAlert.showAndWait();
            return;
        }

        //Check if stock fields have valid integers
        try {
            productStock = Integer.parseInt(productStockField.getText());
            productMin = Integer.parseInt(productMinField.getText());
            productMax = Integer.parseInt(productMaxField.getText());
        } catch (NumberFormatException e) {
            Alert stockAlert = new Alert(Alert.AlertType.ERROR);
            stockAlert.setHeaderText("Stock fields should be whole numbers");
            stockAlert.showAndWait();
            return;
        }

        //Check if price field has valid double
        try {
            productPrice = Double.parseDouble(productCostField.getText());
        } catch (NumberFormatException e) {
            Alert stockAlert = new Alert(Alert.AlertType.ERROR);
            stockAlert.setHeaderText("Price field should be number");
            stockAlert.showAndWait();
            return;
        }

        //Allow only positive numbers in stock and price fields
        if (productStock < 0 || productPrice < 0 || productMax < 0 || productMin < 0) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Only non-negative numbers are allowed");
            errorAlert.showAndWait();
            return;
        }

        //Min stock must be less than max stock
        if (productMin >= productMax) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Minimum stock must be less than Maximum stock");
            errorAlert.showAndWait();
            return;
        }

        //Update product
        Product updatedProduct = new Product(productId, productName, productPrice, productStock, productMin, productMax);
        Inventory.updateProduct(Inventory.getAllProducts().indexOf(selectedProduct), updatedProduct);
        for (Part p : parts) {
            updatedProduct.addAssociatedPart(p);
        }

        //Navigate back to Inventory Manager screen
        backToInventoryManager(actionEvent);
    }

    /**
     * This method is called when the user types in the Search Parts field. It filters the table to the matching parts and displays the number of results found.
      * @param keyEvent User typing event
     */
    public void searchParts(KeyEvent keyEvent) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        String searchText = searchAssociatedPartsField.getText();
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
