package model;

/**
 * This class is the model for part created in-house. It extends the Part abstract class.
 */
public class InHouse extends Part{
    private int machineId;

    /**
     * This is the constructor for InHouse parts
     * @param id ID of part
     * @param name Name of part
     * @param price Price of part
     * @param stock Stock level of part
     * @param min Minimum stock level of part
     * @param max Maximum stock level of part
     * @param machineId Machine ID of part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * This method is called to set the Machine ID of a part.
     * @param machineId Machine ID of part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * This method is called to get Machine ID of a part.
     * @return Returns machine ID of part
     */
    public int getMachineId() {
        return machineId;
    }
}

