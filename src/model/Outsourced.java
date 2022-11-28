package model;

/**
 * This Class is the model for Outsourced parts. It extends the Part abstract class.
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * This method is the constructor for Outsourced parts
     * @param id ID of part
     * @param name Name of part
     * @param price Price of part
     * @param stock Stock of part
     * @param min Minimum stock of part
     * @param max Maximum stock of part
     * @param companyName Company name of part source
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * This method sets the company name of the part source
     * @param companyName Company name of part source
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * This method is called to get company name of part source
     * @return Returns company name of part source
     */
    public String getCompanyName() {
        return companyName;
    }
}
