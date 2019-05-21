
package Model;

public class Customer {
    private String customerName;
    private short addressID;
    private short active;
    private String address;
    

    public Customer(String customerName, short addressID, short active) {
        this.customerName = customerName;
        this.addressID = addressID;
        this.active = active;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public short getAddressID() {
        return addressID;
    }

    public void setAddressID(short addressID) {
        this.addressID = addressID;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }
    public String getAddress(){
        return address;
    }
    
    
}
