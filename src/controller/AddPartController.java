package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This Class controls the Add Part screen
 */
public class AddPartController implements Initializable {

    @FXML
    public RadioButton inhouseRadio;

    @FXML
    public RadioButton outsourcedRadio;

    @FXML
    public Label partSourceLabel;

    @FXML
    public TextField partIDField;

    @FXML
    public TextField partNameField;

    @FXML
    public TextField partStockField;

    @FXML
    public TextField partCostField;

    @FXML
    public TextField partMaxField;

    @FXML
    public TextField partSourceField;

    @FXML
    public TextField partMinField;

    @FXML
    public Button savePartButton;

    @FXML
    public Button cancelAddPartButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * This method navigates the user back to the Inventory Manager screen without saving any changes.
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
     * This method sets the part source label to InHouse based on radio button selection
     *
     * @param actionEvent Radio button selection event
     */
    public void selectInhouse(ActionEvent actionEvent) {
        partSourceLabel.setText("Machine ID");
    }

    /**
     * This method sets the part source label to Outsourced based on radio button selection
     *
     * @param actionEvent Radio button selection event
     */
    public void selectOutsourced(ActionEvent actionEvent) {
        partSourceLabel.setText("Company Name");
    }

    /**
     * This method creates a new part and adds it to the Inventory.
     * <p>
     * RUNTIME ERROR NumberFormatException if entering non-integer values in stock fields or non-double values in price field. Corrected by adding try/catch blocks.
     * RUNTIME ERROR NumberFormatException if entering a non-integer value in Machine ID field. Corrected by adding if/then field validation.
     *
     * @param actionEvent This takes in a button click.
     * @throws IOException
     */
    public void saveNewPart(ActionEvent actionEvent) throws IOException {
        int newPartId = Inventory.getNextPartId();
        String newPartName = partNameField.getText();
        int newPartMaxStock;
        int newPartMinStock;
        int newPartStock;
        double newPartPrice;
        int newPartMachineId;
        String newPartCompanyName;

        //Check if name has valid text
        if (newPartName.equals("") || newPartName == null) {
            Alert companyNameAlert = new Alert(Alert.AlertType.ERROR);
            companyNameAlert.setHeaderText("Part Name is a required field");
            companyNameAlert.showAndWait();
            return;
        }

        //Check if stock fields have valid integers
        try {
            newPartStock = Integer.parseInt(partStockField.getText());
            newPartMinStock = Integer.parseInt(partMinField.getText());
            newPartMaxStock = Integer.parseInt(partMaxField.getText());
        } catch (NumberFormatException e) {
            Alert stockAlert = new Alert(Alert.AlertType.ERROR);
            stockAlert.setHeaderText("Stock fields should be whole numbers");
            stockAlert.showAndWait();
            return;
        }

        //Check if price field has valid double
        try {
            newPartPrice = Double.parseDouble(partCostField.getText());
        } catch (NumberFormatException e) {
            Alert stockAlert = new Alert(Alert.AlertType.ERROR);
            stockAlert.setHeaderText("Price field should be number");
            stockAlert.showAndWait();
            return;
        }

        //Allow only positive numbers in stock and price fields
        if (newPartStock < 0 || newPartPrice < 0 || newPartMaxStock < 0 || newPartMinStock < 0) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Only non-negative numbers are allowed");
            errorAlert.showAndWait();
            return;
        }

        //Check if min stock is less than max stock
        if (newPartMinStock >= newPartMaxStock) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Minimum stock must be less than Maximum stock");
            errorAlert.showAndWait();
            return;
        }

        //Create InHouse part or Outsourced part based on radio button selection
        if (inhouseRadio.isSelected()) {
            try {
                newPartMachineId = Integer.parseInt(partSourceField.getText());
            } catch (NumberFormatException e) {
                Alert machineIdAlert = new Alert(Alert.AlertType.ERROR);
                machineIdAlert.setHeaderText("Machine ID field should be number");
                machineIdAlert.showAndWait();
                return;
            }
            Inventory.addPart(new InHouse(newPartId, newPartName, newPartPrice, newPartStock, newPartMinStock, newPartMaxStock, newPartMachineId));
        } else if (outsourcedRadio.isSelected()) {
            newPartCompanyName = partSourceField.getText();
            if (newPartCompanyName.equals("") || newPartCompanyName == null) {
                Alert companyNameAlert = new Alert(Alert.AlertType.ERROR);
                companyNameAlert.setHeaderText("Company Name is a required field");
                companyNameAlert.showAndWait();
                return;
            }
            Inventory.addPart(new Outsourced(newPartId, newPartName, newPartPrice, newPartStock, newPartMinStock, newPartMaxStock, newPartCompanyName));
        }

        //Return to Inventory Manager screen
        backToInventoryManager(actionEvent);
    }

}
