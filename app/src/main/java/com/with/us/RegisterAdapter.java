package com.with.us;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterAdapter extends AppCompatActivity {

    private ArrayAdapter region_adapter,interest_adapter;
    private Spinner region_spinner,interest_spinner;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fragment);

        //지역 스피너 연결
        region_spinner = (Spinner) findViewById(R.id.activity_register_spn_region);
        region_adapter = ArrayAdapter.createFromResource(this,R.array.region, android.R.layout.simple_spinner_dropdown_item);
        region_spinner.setAdapter(region_adapter);

        //interest
        interest_spinner = (Spinner) findViewById(R.id.activity_register_spn_interest);
        interest_adapter = ArrayAdapter.createFromResource(this,R.array.interest, android.R.layout.simple_spinner_dropdown_item);
        interest_spinner.setAdapter(interest_adapter);

    }

}
