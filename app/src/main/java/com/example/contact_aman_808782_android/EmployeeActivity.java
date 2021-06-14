package com.example.contact_aman_808782_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.contact_aman_808782_android.room.Employee;
import com.example.contact_aman_808782_android.room.EmployeeRoomDb;
import com.example.contact_aman_808782_android.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    DatabaseHelper sqLiteDatabase;
    Button sendMessageBtn;

    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private TelephonyManager mTelephonyManager;

    // Room db instance
    private EmployeeRoomDb employeeRoomDb;

    List<Employee> employeeList;
    ListView employeesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);


        Button dial = findViewById(R.id.btn_dial);
        final EditText mPhoneNoEt = findViewById(R.id.et_phone_no);

        // initializing the Telephony manager instance
        mTelephonyManager = (TelephonyManager) getSystemService(getApplicationContext().TELEPHONY_SERVICE);

        employeesListView = findViewById(R.id.lv_employees);
        employeeList = new ArrayList<>();


        // Room
        employeeRoomDb = EmployeeRoomDb.getInstance(this);
        loadEmployees();




    }

    private void loadEmployees() {


        employeeList = employeeRoomDb.employeeDao().getAllEmployees();


        EmployeeAdapter employeeAdapter = new EmployeeAdapter(this, R.layout.list_layout_employee, employeeList);
        employeesListView.setAdapter(employeeAdapter);
    }

//
    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return (checkPermission == PackageManager.PERMISSION_GRANTED);
    }
//
    PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);

            switch(state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    Toast.makeText(EmployeeActivity.this, "CALL_STATE_IDLE", Toast.LENGTH_SHORT).show();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Toast.makeText(EmployeeActivity.this, "CALL_STATE_RINGING", Toast.LENGTH_SHORT).show();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Toast.makeText(EmployeeActivity.this, "CALL_STATE_OFFHOOK", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case SEND_SMS_PERMISSION_REQUEST_CODE: {
//                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    sendMessageBtn.setEnabled(true);
//                }
//                return;
//            }
//        }
//    }
}

