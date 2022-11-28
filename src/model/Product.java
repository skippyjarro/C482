package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This Class is the model for Products
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * This method is the constructor for Products
     * @param id ID of product
     * @param name Name of product
     * @param price Price of product
     * @param stock Stock of product
     * @param min Minimum stock of product
     * @param max Maximum stock of product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This method is called to set the product ID
     * @param id ID of product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method sets the product name
     * @param name Name of product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method sets the price of a product
     * @param price Price of product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method sets the stock of the product
     * @param stock Stock of product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * This method sets minimum stock of the product
     * @param min Minimum stock of product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method sets maximum stock of the product
     * @param max Maximum stock of product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method gets the product ID
     * @return Returns product ID
     */
    public int getId() {
        return id;
    }

    /**
     * This method gets the product name
     * @return Returns product name
     */
    public String getName() {
        return name;
    }

    /**
     * This method gets the product price
     * @return Returns product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * This method gets the product stock
     * @return Returns product stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * This method gets the minimum stock of the product
     * @return Returns minimum stock
     */
    public int getMin() {
        return min;
    }

    /**
     * This method gets the maximum stock of the product
     * @return Returns maximum stock
     */
    public int getMax() {
        return max;
    }

    /**
     * This method is called when the user associates parts to the product.
     * @param part Part to be associated
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * This method is called when the user removes associated parts from the product.
     * @param selectedAssociatedPart Part selected to be removed
     * @return Returns status of removal
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
            return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * This method is called to populate associated parts table and is used to ensure products are not deleted if parts are associated.
     * @return Returns ObservableList of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
