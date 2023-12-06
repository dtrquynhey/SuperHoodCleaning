package models;

public class Customer {
    private String customerId;
    private String name;
    private String manager;
    private Address address;
    private String phone;

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Customer(String customerName, String managerName, Address address, String phone) {
        this.name = customerName;
        this.manager = managerName;
        this.address = address;
        this.phone = phone;
    }
}
