package com.example.contact_aman_808782_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contact_aman_808782_android.room.Employee;
import com.example.contact_aman_808782_android.room.EmployeeRoomDb;
import com.example.contact_aman_808782_android.util.DatabaseHelper;

import java.util.Arrays;
import java.util.List;

public class EmployeeAdapter extends ArrayAdapter {

    private static final String TAG = "EmployeeAdapter";

    Context context;
    int layoutRes;
    List<com.example.contact_aman_808782_android.room.Employee> employeeList;

    DatabaseHelper sqLiteDatabase;
    EmployeeRoomDb employeeRoomDb;





    public EmployeeAdapter(@NonNull Context context, int resource, List<com.example.contact_aman_808782_android.room.Employee> employeeList) {
        super(context, resource, employeeList);
        this.employeeList = employeeList;
        this.context = context;
        this.layoutRes = resource;
        employeeRoomDb = EmployeeRoomDb.getInstance(context);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = convertView;
        if (v == null) v = inflater.inflate(layoutRes, null);
        TextView firstnameTV = v.findViewById(R.id.tv_firstName);
        TextView lastnameTV = v.findViewById(R.id.tv_lastName);
        TextView email = v.findViewById(R.id.tv_email);
        TextView address = v.findViewById(R.id.tv_address);
        TextView number = v.findViewById(R.id.tv_number);

        EditText e = v.findViewById(R.id.etSearchName);


        final com.example.contact_aman_808782_android.room.Employee employee = employeeList.get(position);
        firstnameTV.setText(employee.getFirstName());
        lastnameTV.setText(employee.getLastName());
        email.setText(employee.getEmail());
        address.setText(employee.getAddress());
        number.setText(String.valueOf(employee.getNumber()));


        v.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateEmployee(employee);
            }

            private void updateEmployee(final com.example.contact_aman_808782_android.room.Employee employee) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.dialog_update_employee, null);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final EditText etfirstName = view.findViewById(R.id.et_firstName);
                final EditText etLastName = view.findViewById(R.id.et_lastName);
                final EditText etEmail = view.findViewById(R.id.et_email);
                final EditText etAddress = view.findViewById(R.id.et_address);
                final EditText etNumber = view.findViewById(R.id.et_number);

                etfirstName.setText(employee.getFirstName());
                etLastName.setText(employee.getLastName());
                etEmail.setText(employee.getEmail());
                etAddress.setText(employee.getAddress());
                etNumber.setText(String.valueOf(employee.getNumber()));


                view.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String firstName = etfirstName.getText().toString().trim();
                        String lastName = etLastName.getText().toString().trim();
                        String email = etEmail.getText().toString().trim();
                        String address = etAddress.getText().toString().trim();
                        String number = etNumber.getText().toString().trim();


                        if (firstName.isEmpty()) {
                            etfirstName.setError("name field cannot be empty");
                            etfirstName.requestFocus();
                            return;
                        }

                        if (lastName.isEmpty()) {
                            etLastName.setError("salary cannot be empty");
                            etLastName.requestFocus();
                            return;
                        }


                        // Room
                        employeeRoomDb.employeeDao().updateEmployee(employee.getId(),
                                firstName, lastName, email,address,Integer.parseInt(number));
                        loadEmployees();
                        alertDialog.dismiss();
                    }
                });
            }
        });


//        v.findViewById(R.id.btn_dial).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dial(employee);
//            }
//
//            private void dial(final Employee employee) {
//
//
//
//                String phoneNo = String.valueOf(employee.getNumber());
//                if(!TextUtils.isEmpty(phoneNo)) {
//                    String dial = "tel:" + phoneNo;
//                    // an intent to dial a number
//                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
//
//                }
//            }
//
//        });


        v.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String firstName = e.getText().toString().trim();
                searchEmployee(firstName);
            }

            private void searchEmployee(String firstName) {



                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Please confirm");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Room

                        Employee searchData = employeeRoomDb.employeeDao().searchEmployee(firstName);
                        sEmployees(searchData);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee(employee);
            }

            private void deleteEmployee(final Employee employee) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Room

                        employeeRoomDb.employeeDao().deleteEmployee(employee.getId());
                        loadEmployees();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "The employee (" + employee.getFirstName() + ") is not deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        Log.d(TAG, "getView: " + getCount());
        return v;





    }




    @Override
    public int getCount() {
        return employeeList.size();
    }

    private void loadEmployees() {

        employeeList = employeeRoomDb.employeeDao().getAllEmployees();
        notifyDataSetChanged();

    }

    private void sEmployees(Employee searchData) {

        employeeList = List.of(searchData);
        notifyDataSetChanged();

    }
}










