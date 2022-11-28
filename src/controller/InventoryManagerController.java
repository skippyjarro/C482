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
 * This Class controls the main Inventory Manager screen
 */
public class InventoryManagerController implements Initializable {
    @FXML
    public Button addPart;

    @FXML
    public Button modifyPart;

    @FXML
    public Button deletePartButton;

    @FXML
    public Button modifyProduct;

    @FXML
    public Button deleteProductButton;

    @FXML
    public Button addProduct;

    @FXML
    public Button exitButton;

    @FXML
    public TableView partsTable;

    @FXML
    public TableColumn partIdCol;

    @FXML
    public TableColumn partStockCol;

    @FXML
    public TableColumn partPriceCol;

    @FXML
    public TableView productsTable;

    @FXML
    public TableColumn productIdCol;

    @FXML
    public TableColumn productNameCol;

    @FXML
    public TableColumn productStockCol;

    @FXML
    public TableColumn productPriceCol;

    @FXML
    public TableColumn partNameCol;

    @FXML
    public TextField searchPartsField;

    @FXML
    public TextField searchProductsField;

    @FXML
    public Label partSearchResults;

    @FXML
    public Label productSearchResults;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Populate Parts table
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.getAllParts());

        //Populate Products table
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
    }

    /**
     * This method is called when the user clicks the Add part button. It navigates the user to the Add Part screen.
      * @param actionEvent Add part button click event
     * @throws IOException Incorrect input
     */
    public void toAddPart(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 600, 600);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    /**
     * This method passes the selected part in the Part Table and navigates the user to the Modify Part screen.
     * RUNTIME ERROR NullPointerException if Modify button is clicked with no Part selected.  Added it/then check for null selection
     *
     * @param actionEvent Modify part button click event
     * @throws IOException Incorrect input
     */
    public void toModifyPart(javafx.event.ActionEvent actionEvent) throws IOException {
        Inventory.setSelectedPart((Part) partsTable.getSelectionModel().getSelectedItem());

        if (Inventory.getSelectedPart() == null) {
            Alert unselectedPartAlert = new Alert(Alert.AlertType.ERROR);
            unselectedPartAlert.setHeaderText("Must select a part");
            unselectedPartAlert.showAndWait();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 600, 600);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    /**
     * This method is called when a user clicks the Add product button. It navigates the user to the Add Product screen.
      * @param actionEvent Add product button click event
     * @throws IOException Incorrect input
     */
    public void toAddProduct(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1024, 600);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    /**
     * This method passes the selected part in the Product Table and navigates the user to the Modify Part screen.
     * RUNTIME ERROR NullPointerException if Modify button is clicked with no Product selected.  Added if/then check for null selection
     *
     * @param actionEvent Modify product button click event
     * @throws IOException Incorrect input
     */
    public void toModifyProduct(javafx.event.ActionEvent actionEvent) throws IOException {
        Inventory.setSelectedProduct((Product) productsTable.getSelectionModel().getSelectedItem());

        if (Inventory.getSelectedProduct() == null) {
            Alert unselectedProductAlert = new Alert(Alert.AlertType.ERROR);
            unselectedProductAlert.setHeaderText("Must select a product");
            unselectedProductAlert.showAndWait();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1024, 600);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    /**
     * This method is called when the user clicks the Delete part button. It performs validation that a product was selected before deleting and asks for confirmation. It returns a success/failure dialog.
     * @param actionEvent Delete part button click event
     */
    public void deletePart(ActionEvent actionEvent) {
        Part p = (Part) partsTable.getSelectionModel().getSelectedItem();

        //Check if a part is selected
        if (p == null) {
            Alert unselectedPartAlert = new Alert(Alert.AlertType.ERROR);
            unselectedPartAlert.setHeaderText("Must select a part");
            unselectedPartAlert.showAndWait();
            return;
        }

        //Confirm deletion
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setHeaderText("This cannot be undone.  \nWould you like to proceed anyway?");
        confirmAlert.setContentText("Click OK to Proceed");
        confirmAlert.showAndWait();
        if (confirmAlert.getResult() == ButtonType.CANCEL) {
            return;
        }

        //Display success/failure dialog
        if (Inventory.deletePart(p)) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText("Part Deleted Successfully");
            successAlert.showAndWait();
        } else if (!Inventory.deletePart(p)) {
            Alert failureAlert = new Alert(Alert.AlertType.ERROR);
            failureAlert.setTitle("Error");
            failureAlert.setHeaderText("There was a problem deleting the selected part");
            failureAlert.showAndWait();
        }
    }

    /**
     * This method is called when the user clicks the Delete product button. It performs validation that a product was selected and does not have associated parts before deleting. It returns a success/failure dialog.
     *
     * @param actionEvent Delete product button click event
     */
    public void deleteProduct(ActionEvent actionEvent) {
        Product p = (Product) productsTable.getSelectionModel().getSelectedItem();

        //Check if a product is selected
        if (p == null) {
            Alert unselectedProductAlert = new Alert(Alert.AlertType.ERROR);
            unselectedProductAlert.setHeaderText("Must select a product");
            unselectedProductAlert.showAndWait();
            return;
        }

        //Show error message if product has associated parts
        if (p.getAllAssociatedParts().size() != 0 && p.getAllAssociatedParts() != null) {
            Alert unableAlert = new Alert(Alert.AlertType.ERROR);
            unableAlert.setTitle("Error");
            unableAlert.setHeaderText("This product has associated parts and cannot be deleted");
            unableAlert.showAndWait();
            return;
        }

        //Confirm deletion
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setHeaderText("This cannot be undone.  \nWould you like to proceed anyway?");
        confirmAlert.setContentText("Click OK to Proceed");
        confirmAlert.showAndWait();
        if (confirmAlert.getResult() == ButtonType.CANCEL) {
            return;
        }

        //Show deletion success/failure dialog
        if (Inventory.deleteProduct(p)) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText("Product Deleted Successfully");
            successAlert.showAndWait();
        } else if (!Inventory.deleteProduct(p)) {
            Alert failureAlert = new Alert(Alert.AlertType.ERROR);
            failureAlert.setTitle("Error");
            failureAlert.setHeaderText("There was a problem deleting the selected product");
            failureAlert.showAndWait();
        }
    }

    /**
     * This method is called when the user types in the Search Parts text field. It filters the table to results matching the value typed and displays a message showing the number of results found.
     *
     * @param keyEvent User typing event
     */
    public void searchParts(KeyEvent keyEvent) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        String searchText = searchPartsField.getText();
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
        partsTable.setItems(parts);
    }

    /**
     * This method is called when user types in the Search Products text field. It filters the table to results matching the value typed and displays a message showing the number of results found.
     *
     * @param keyEvent User typing event
     */
    public void searchProducts(KeyEvent keyEvent) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        String searchText = searchProductsField.getText();
        int searchID;
        try {
            searchID = Integer.parseInt(searchText);
            products.add(Inventory.lookupProduct(searchID));
        } catch (Exception e) {
            products = Inventory.lookupProduct(searchText);
        }
        if (products.size() > 1 || products.size() < 1) {
            productSearchResults.setText(products.size() + " Results Found");
        } else if (products.size() == 1) {
            productSearchResults.setText(products.size() + " Result Found");
        }
        productsTable.setItems(products);
    }

    /**
     * This method is called when the user clicks the Exit button. It closes the program.
     *
     * @param actionEvent Exit button click event
     */
    public void exitProgram(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
