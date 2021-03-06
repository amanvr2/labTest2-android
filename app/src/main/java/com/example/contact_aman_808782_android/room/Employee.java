package com.example.contact_aman_808782_android.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee")
public class Employee {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "firstName")
    private String firstName;

    @NonNull
    @ColumnInfo(name = "lastName")
    private String lastName;

    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "number")
    private int number;

    @NonNull
    @ColumnInfo(name = "address")
    private String address;

    public Employee(@NonNull String firstName, @NonNull String lastName, @NonNull String email, int number, @NonNull String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public int getNumber() {
        return number;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }


}
