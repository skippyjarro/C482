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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryManagerController implements Initializable {
    @FXML
    public Button addPart;

    @FXML
    public Button modifyPart;

    @FXML
    public Button deletePart;

    @FXML
    public Button modifyProduct;

    @FXML
    public Button deleteProduct;

    @FXML
    public Button addProduct;

    @FXML
    public Button exit;
    public TableView partsTable;
    public TableColumn partIdCol;
    public TableColumn partStockCol;
    public TableColumn partPriceCol;
    public TableView productsTable;
    public TableColumn productIdCol;
    public TableColumn productNameCol;
    public TableColumn productStockCol;
    public TableColumn productPriceCol;
    public TableColumn partNameCol;
    public TextField searchPartsField;
    public TextField searchProductsField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Part part1 = new InHouse(1, "Test1", 4.99, 5, 1, 10, 10);
        Inventory.addPart(part1);
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.getAllParts());
    }


    public void toAddPart(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 600, 600);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    public void toModifyPart(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 600, 600);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    public void toAddProduct(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1024, 600);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    public void toModifyProduct(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1024, 600);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    public void deleteProduct(ActionEvent actionEvent) {
    }

    public void deletePart(ActionEvent actionEvent) {
    }

    public void searchParts(ActionEvent actionEvent) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        String searchText = searchPartsField.getText();
        int searchID;
        try {
            searchID = Integer.parseInt(searchText);
            parts = (ObservableList<Part>) Inventory.lookupPart(searchID);
        } catch (Exception e) {
            parts = Inventory.lookupPart(searchText);
        }
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(parts);
    }

    public void searchProducts(KeyEvent keyEvent) {
    }
}
