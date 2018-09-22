package com.example.sukanthchandrasekar.phoneauth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelectServices extends AppCompatActivity {
    Button button;
    CheckBox bb,al;
    SharedPreferences sharedPreferences;
    String userKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_services);
        button = (Button) findViewById(R.id.addservices);
        bb = (CheckBox) findViewById(R.id.bb);
        al = (CheckBox) findViewById(R.id.al);
        sharedPreferences = getSharedPreferences("ReachMe", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("userKey")){
            userKey = sharedPreferences.getString("userKey","default");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                if (bb.isChecked()||al.isChecked()){
                    if (bb.isChecked()){
                        databaseReference.child(getString(R.string.firebase_path)).child(getString(R.string.services)).child(getString(R.string.bloodbank)).child(userKey).setValue("false");
                    }
                    if (al.isChecked()){
                        databaseReference.child(getString(R.string.firebase_path)).child(getString(R.string.services)).child(getString(R.string.alumni)).child(userKey).setValue("false");
                    }
                    Toast.makeText(getApplicationContext(),"Services added...",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SelectServices.this,HomeActivity.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Click any service...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
