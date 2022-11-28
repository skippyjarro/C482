package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * This is the Main Class
 * JAVADOCS in Javadocs folder
 * FUTURE ENHANCEMENT This application would be suitable for maintaining Inventory for the company if only one person were to do it on a single computer.
 * However, this is not realistic and is typically done by a team from sales to manufacturing to logistics teams.
 * This application should be turned into a cloud-based web app that can be used from any internet browser.
 * Having the data saved in a database makes the data non-volatile and can be access/updated by all users of the application.
 * The application also needs several reports so users can analyze part/product stock flows and costs.
 * 3rd-part integrations with shipping partners may be helpful to facilitate part shipment to customers.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/InventoryManager.fxml"));
        stage.setTitle("Inventory Manager");
        stage.setScene(new Scene(root, 1024, 400));
        stage.setResizable(false);
        stage.show();

        Part part1 = new InHouse(1, "Test1", 4.99, 5, 1, 10, 10);
        Inventory.addPart(part1);

        Product product1 = new Product(1, "Tester1", 19.99, 5, 1, 10);
        Inventory.addProduct(product1);
    }

    /**
     * This is the main method. It launches the application
     * @param args Argument of Strings
     */
    public static void main(String[] args) {
        launch(args);
    }
}
