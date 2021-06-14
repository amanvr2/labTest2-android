package com.example.contact_aman_808782_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.contact_aman_808782_android.room.Employee;
import com.example.contact_aman_808782_android.room.EmployeeRoomDb;
import com.example.contact_aman_808782_android.util.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    // sqLite openHelper instance
    DatabaseHelper sqLiteDatabase;

    private EmployeeRoomDb employeeRoomDb;

    EditText etFirstName,etLastName,etEmail,etAddress, etNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFirstName = findViewById(R.id.et_firstName);
        etLastName = findViewById(R.id.et_lastName);
        etEmail = findViewById(R.id.et_email);
        etAddress = findViewById(R.id.et_address);
        etNumber = findViewById(R.id.et_number);

        findViewById(R.id.btn_add_employee).setOnClickListener(this);
        findViewById(R.id.tv_view_employees).setOnClickListener(this);

        // Room db
        employeeRoomDb = EmployeeRoomDb.getInstance(this);
    }


    @SuppressLint("NonConstantResourceId")
    //@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_employee:
                addEmployee();
                break;
            case R.id.tv_view_employees:
                startActivity(new Intent(this, EmployeeActivity.class));
                break;
        }

    }

    private void addEmployee() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String number = etNumber.getText().toString().trim();

        if (firstName.isEmpty()) {
            etFirstName.setError("name field cannot be empty");
            etFirstName.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            etLastName.setError("salary cannot be empty");
            etLastName.requestFocus();
            return;
        }
//
//        // Insert into room db
       // Employee employee = new Employee(firstName,lastName,email,Integer.parseInt(number),address);
        Employee employee = new Employee(firstName,lastName,email,Integer.parseInt(number),address);
        employeeRoomDb.employeeDao().insertEmployee(employee);
        Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        clearFields();
    }

    private void clearFields() {
        etFirstName.setText("");
        etLastName.setText("");
        etEmail.setText("");
        etAddress.setText("");
        etNumber.setText("");
        etFirstName.clearFocus();
        etLastName.clearFocus();
    }
}