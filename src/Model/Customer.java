
package Model;

public class Customer {
    private String customerName;
    private int addressID;
    private int active;
    private int customerID;
    private String address;
    private String address2;
    private int postCode;
    private String city;
    private String country;
    private String email;
    private String customerPhone;



    public Customer(String customerName, String email, String customerPhone, int addressID, int active, int customerID, String address, String address2, int postCode, String city, String country) {
        this.customerName = customerName; //customer table
        this.email = email;
        this.customerPhone = customerPhone;
        this.addressID = addressID; //address table
        this.active = active; // customer table
        this.customerID = customerID; // customer table
        this.address = address; //address table
        this.address2 = address2; //address table
        this.postCode = postCode; //address table
        this.city = city; //city table
        this.country = country; //country table
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    public int getCustomerID(){
        return customerID;
    }
    
    public void setCustomerID(int customerID){
        this.customerID = customerID;
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
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
