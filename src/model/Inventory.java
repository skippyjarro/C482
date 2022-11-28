package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class maintains the parts and products inventory.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int nextPartId = 1;
    private static int nextProductId = 1;
    private static Part selectedPart = null;
    private static Product selectedProduct = null;

    /** This method is called by the Modify Part screen. It returns the currently selected Part in the parts table that is meant to be modified.
     * @return Returns part selected to be modified
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }

    /** This method sets the static variable selectedPart to the currently selected part in the parts table. This part will be passed to the Modify Part screen.
     * @param selectedPart Part selected in parts table
     */
    public static void setSelectedPart(Part selectedPart) {
        Inventory.selectedPart = selectedPart;
    }

    /** This method is called by the Modify Product screen. It returns the currently selected Product in the products table meant to be modified.
     * @return Returns product selected to be modified
     */
    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    /** This method sets the static variable selectedProduct to he currently selected product in the products table. This product will be passed to the Modify Product screen.
     * @param selectedProduct Product selected in the products table
     */
    public static void setSelectedProduct(Product selectedProduct) {
        Inventory.selectedProduct = selectedProduct;
    }

    /**
     * This method is called when a user saves a new part. The nextPartID tracks what the next Part ID should be.
     * @return Returns next Part ID
     */
    public static int getNextPartId() {
        for (Part p : allParts) {
            if (p.getId() == nextPartId) {
                nextPartId++;
            }
        }
        return nextPartId++;
    }

    /**
     * This method is called when the user saves a new product. The nextProductID tracks what the next product ID should be.
     * @return Returns next Product ID
     */
    public static int getNextProductId() {
        for (Product p : allProducts) {
            if (p.getId() == nextProductId) {
                nextProductId++;
            }
        }
        return nextProductId++;
    }

    /**
     * This method is called when the user saves a new part. It adds the part to the Inventory.
     * @param newPart Part to be added to Inventory
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method is called when the user saves a new product. It adds the product to the Inventory.
     * @param newProduct Product to be added to Inventory
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method is called when the user types in the search part text field. It looks for parts by Part ID.
     * @param partId Part ID used for search
     * @return Returns part found
     */
    public static Part lookupPart(int partId) {
        Part tempPart = null;
        ObservableList<Part> tempAllParts = Inventory.getAllParts();
        for (Part part : tempAllParts) {
            if (part.getId() == partId) {
                tempPart = part;
            }
        }
        return tempPart;
    }

    /**
     * This method is called when the user types in the search product text field. It looks for products by Part ID.
     * @param productId Product ID used for search
     * @return Returns product found
     */
    public static Product lookupProduct(int productId) {
        Product tempProduct = null;
        ObservableList<Product> tempAllProducts = Inventory.getAllProducts();
        for (Product product : tempAllProducts) {
            if (product.getId() == productId) {
                tempProduct = product;
            }
        }
        return tempProduct;
    }

    /**
     * This method is called when the user types in the search part text field. It looks for products by Part Name.
     * @param partName Part name used for search
     * @return Returns ObservableList of parts found
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> tempList = FXCollections.observableArrayList();
        ObservableList<Part> tempAllParts = Inventory.getAllParts();
        for (Part part : tempAllParts) {
            if (part.getName().contains(partName)) {
                tempList.add(part);
            }
        }
        return tempList;
    }

    /**
     * This method is called when the user types in the search product text field. It looks for products by Product Name.
     * @param productName Product name used for search
     * @return Returns ObservableList of products found
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> tempList = FXCollections.observableArrayList();
        ObservableList<Product> tempAllProducts = Inventory.getAllProducts();
        for (Product product : tempAllProducts) {
            if (product.getName().contains(productName)) {
                tempList.add(product);
            }
        }
        return tempList;
    }

    /**
     * This method is called with the user saves a modified part.
     * @param index Index of part to be modified
     * @param selectedPart Part selected to be modified
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * This method is called with the user saves a modified product.
     * @param index Index of product to be modified
     * @param selectedProduct Product selected to be modified
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * This method is called when the user deletes a part.
     * @param selectedPart Part selected to be deleted
     * @return Returns status of delete
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * This method is called when the user deletes a product
     * @param selectedProduct Product selected to be deleted
     * @return Returns status of delete
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * This method is called to populate parts tables
     * @return Returns ObservableList of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method is called to populate the product table
     * @return Returns ObservableList of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
