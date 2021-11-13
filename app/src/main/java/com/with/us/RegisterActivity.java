package com.with.us;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //지역 스피너 연결
        spinner = (Spinner) findViewById(R.id.activity_register_spn_region);
        adapter = ArrayAdapter.createFromResource(this,R.array.region, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);




    }
}