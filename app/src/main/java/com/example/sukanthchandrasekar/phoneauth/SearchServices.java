package com.example.sukanthchandrasekar.phoneauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SearchServices extends AppCompatActivity {
    RadioGroup radioGroup;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_services);
        radioGroup = (RadioGroup)findViewById(R.id.serachservicesradiogroup);
        button = (Button)findViewById(R.id.reachsomeone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                String service = radioButton.getText().toString();
                if (service.equals("Blood Bank")){
                    service = getString(R.string.bloodbank);
                }else if (service.equals("Alumni")){
                    service = getString(R.string.alumni);
                }
                Intent intent = new Intent(SearchServices.this,MapsActivity.class);
                intent.putExtra("service",service);
                startActivity(intent);
                finish();
            }
        });
    }
}
