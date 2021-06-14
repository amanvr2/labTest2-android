package com.example.contact_aman_808782_android.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Insert
    void insertEmployee(Employee employee);

    @Query("DELETE FROM employee")
    void deleteAllEmployees();

    @Query("DELETE FROM employee WHERE id = :id" )
    int deleteEmployee(int id);

    @Query("UPDATE employee SET firstName = :firstName, lastName = :lastName, email = :email,address = :address, number= :number WHERE id = :id")
    int updateEmployee(int id, String firstName, String lastName,String email,String address, int number);

    @Query("SELECT * FROM employee ORDER BY firstName")
    List<Employee> getAllEmployees();

    @Query("SELECT * from employee WHERE firstName = :firstName")
    int searchEmployee(String firstName);

}