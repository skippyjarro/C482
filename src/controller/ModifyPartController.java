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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This Class controls the Modify Part screen
 */
public class ModifyPartController implements Initializable {

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
    public Button cancelModifyPartButton;

    Part p;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Populate fields based on selected part passed in from Inventory Manager screen
        p = Inventory.getSelectedPart();
        if (p.getClass().getTypeName() == "model.InHouse") {
            InHouse part = (InHouse) Inventory.getSelectedPart();
            inhouseRadio.setSelected(true);
            partIDField.setText(String.valueOf(part.getId()));
            partNameField.setText(part.getName());
            partCostField.setText(String.valueOf(part.getPrice()));
            partStockField.setText(String.valueOf(part.getStock()));
            partMaxField.setText(String.valueOf(part.getMax()));
            partMinField.setText(String.valueOf(part.getMin()));
            partSourceField.setText(String.valueOf(part.getMachineId()));
            partSourceLabel.setText("Machine ID");
        } else if (p.getClass().getTypeName() == "model.Outsourced") {
            Outsourced part = (Outsourced) Inventory.getSelectedPart();
            outsourcedRadio.setSelected(true);
            partIDField.setText(String.valueOf(part.getId()));
            partNameField.setText(part.getName());
            partCostField.setText(String.valueOf(part.getPrice()));
            partStockField.setText(String.valueOf(part.getStock()));
            partMaxField.setText(String.valueOf(part.getMax()));
            partMinField.setText(String.valueOf(part.getMin()));
            partSourceField.setText(part.getCompanyName());
            partSourceLabel.setText("Company Name");
        }
    }

    /**
     * This method navigates the user back to the Inventory Manager screen.
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
     * This method is called upon clicking the Save button. The method performs form field validation and provides error messages as appropriate prior to saving then navigates user back to Inventory Manager scree.
     * RUNTIME ERROR Machine ID throws NumberFormatException if non-integer value entered into Source field
     *
     * @param actionEvent Save button click event
     * @throws IOException Incorrect input
     */
    public void saveModifyPart(ActionEvent actionEvent) throws IOException {
        int partId = Integer.parseInt(partIDField.getText());
        String partName = partNameField.getText();
        double partPrice;
        int partStock;
        int partMinStock;
        int partMaxStock;
        int partMachineId;

        //Check if name has valid text
        if (partName.equals("") || partName == null) {
            Alert companyNameAlert = new Alert(Alert.AlertType.ERROR);
            companyNameAlert.setHeaderText("Part Name is a required field");
            companyNameAlert.showAndWait();
            return;
        }

        //Check if stock fields have valid integers
        try {
            partStock = Integer.parseInt(partStockField.getText());
            partMinStock = Integer.parseInt(partMinField.getText());
            partMaxStock = Integer.parseInt(partMaxField.getText());
        } catch (NumberFormatException e) {
            Alert stockAlert = new Alert(Alert.AlertType.ERROR);
            stockAlert.setHeaderText("Stock fields should be whole numbers");
            stockAlert.showAndWait();
            return;
        }

        //Check if price field has valid double
        try {
            partPrice = Double.parseDouble(partCostField.getText());
        } catch (NumberFormatException e) {
            Alert stockAlert = new Alert(Alert.AlertType.ERROR);
            stockAlert.setHeaderText("Price field should be number");
            stockAlert.showAndWait();
            return;
        }

        //Allow only positive numbers in stock and price fields
        if (partStock < 0 || partPrice < 0 || partMaxStock < 0 || partMinStock < 0) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Only non-negative numbers are allowed");
            errorAlert.showAndWait();
            return;
        }

        //Check if min stock is less than max stock
        if (partMinStock >= partMaxStock) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Minimum stock must be less than Maximum stock");
            errorAlert.showAndWait();
            return;
        }

        //Update existing part based on InHouse or Outsourced radio selection
        if (inhouseRadio.isSelected()) {
            try {
                partMachineId = Integer.parseInt(partSourceField.getText());
            } catch (NumberFormatException e) {
                Alert machineIdAlert = new Alert(Alert.AlertType.ERROR);
                machineIdAlert.setHeaderText("Machine ID field should be number");
                machineIdAlert.showAndWait();
                return;
            }
            Inventory.updatePart(Inventory.getAllParts().indexOf(p), new InHouse(partId, partName, partPrice, partStock, partMinStock, partMaxStock, partMachineId));
        } else if (outsourcedRadio.isSelected()) {
            String partCompanyName = partSourceField.getText();
            if (partCompanyName.equals("") || partCompanyName == null) {
                Alert companyNameAlert = new Alert(Alert.AlertType.ERROR);
                companyNameAlert.setHeaderText("Company Name is a required field");
                companyNameAlert.showAndWait();
                return;
            }
            Inventory.updatePart(Inventory.getAllParts().indexOf(p), new Outsourced(partId, partName, partPrice, partStock, partMinStock, partMaxStock, partCompanyName));
        }

        //Navigate back to Inventory Manager screen
        backToInventoryManager(actionEvent);
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

}
