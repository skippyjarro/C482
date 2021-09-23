package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

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

    public static Product lookupProduct(int productId) {
        Product tempProduct = null;
        ObservableList<Product> tempAllProducts = Inventory.getAllProducts();
        for (Product product : tempAllProducts) {
            if (product.getId() == productId) {
                tempProduct = product;
            }
        }
        return tempProduct;    }

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

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> tempList = FXCollections.observableArrayList();
        ObservableList<Product> tempAllProducts = Inventory.getAllProducts();
        for (Product product : tempAllProducts) {
            if (product.getName().contains(productName)) {
                tempList.add(product);
            }
        }
        return tempList;    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
