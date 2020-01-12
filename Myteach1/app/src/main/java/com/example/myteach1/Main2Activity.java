package com.example.myteach1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView textViewname, txtAge, textViewNrc,textViewFname,textViewFnrc,textViewMname,textViewMnrc;
    int age = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textViewname = findViewById(R.id.txt_name);
        txtAge = findViewById(R.id.txt_age);
        textViewNrc = findViewById(R.id.txt_nrc);
        textViewFname =findViewById(R.id.txt_Fname);
        textViewMname = findViewById(R.id.txt_Mname);
        textViewFnrc = findViewById(R.id.txt_Fnrc);
        textViewMnrc = findViewById(R.id.txt_MNRC);

        age = getIntent().getIntExtra("age",0);
        textViewname.setText(getIntent().getStringExtra("name"));//Mg Mg
        txtAge.setText(String.valueOf(age));
        textViewNrc.setText(getIntent().getStringExtra("nrc"));
        textViewFname.setText(getIntent().getStringExtra("fname"));
        textViewMname.setText(getIntent().getStringExtra("mname"));
        textViewFnrc.setText(getIntent().getStringExtra("fnrc"));
        textViewMnrc.setText(getIntent().getStringExtra("mnrc"));
    }
}
