
package Model;

public class Customer {
    private String customerName;
    private short addressID;
    private short active;
    private String address;
    private String address2;
    private int postCode;
    private String city;
    private String country;

    public Customer(String customerName, short addressID, short active, String address, String address2, int postCode, String city, String country) {
        this.customerName = customerName;
        this.addressID = addressID;
        this.active = active;
        this.address = address;
        this.address2 = address2;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    
    
}
