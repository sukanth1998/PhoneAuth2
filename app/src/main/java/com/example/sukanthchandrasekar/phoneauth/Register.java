package com.example.sukanthchandrasekar.phoneauth;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Register extends AppCompatActivity {
    TextView display;
    SharedPreferences sharedPreferences;
    String userKey = "Not registered...", phoneNumber;
    EditText registername, registerdob, registeremailid;
    Button updatedetails;
    RadioGroup gender;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener eventdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPreferences = getSharedPreferences("ReachMe", Context.MODE_PRIVATE);
        display=(TextView)findViewById(R.id.display);
        registerdob = (EditText) findViewById(R.id.dob);
        registeremailid = (EditText)findViewById(R.id.registeremailid);
        registername = (EditText)findViewById(R.id.registername);
        updatedetails = (Button) findViewById(R.id.updatedetailsbutton);
        gender = (RadioGroup) findViewById(R.id.genderradiogroup);
        if (sharedPreferences.contains("userKey")){
            userKey = sharedPreferences.getString("userKey","Default");
            phoneNumber = sharedPreferences.getString("userPhoneNumber","1234567890");
        }
        registerdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Register.this,eventdate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        eventdate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
            private void updateLabel() {
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                registerdob.setText(sdf.format(myCalendar.getTime()));
            }
        };
        display.setText("Registered Number - "+phoneNumber);
        updatedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = registername.getText().toString();
                String d = registerdob.getText().toString();
                String e = registeremailid.getText().toString();
                RadioButton radioButton = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
                String g = radioButton.getText().toString();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child(getString(R.string.firebase_path)).child(getString(R.string.userDetails)).child(userKey).child("name").setValue(n);
                databaseReference.child(getString(R.string.firebase_path)).child(getString(R.string.userDetails)).child(userKey).child("dob").setValue(d);
                databaseReference.child(getString(R.string.firebase_path)).child(getString(R.string.userDetails)).child(userKey).child("gender").setValue(g);
                databaseReference.child(getString(R.string.firebase_path)).child(getString(R.string.userDetails)).child(userKey).child("phone").setValue(phoneNumber);
                databaseReference.child(getString(R.string.firebase_path)).child(getString(R.string.userDetails)).child(userKey).child("emailid").setValue(e);
                Toast.makeText(getApplicationContext(),"Added...",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this,SelectServices.class));
                finish();
            }
        });
    }
}
