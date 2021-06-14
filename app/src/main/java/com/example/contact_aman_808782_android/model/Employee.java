package com.example.contact_aman_808782_android.model;

public class Employee {

    int id;
    String firstName, lastName, email, address;
    Integer number;

    public Employee(int id, String firstName, String lastName, String email, String address, Integer number) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Integer getNumber() {
        return number;
    }
}
