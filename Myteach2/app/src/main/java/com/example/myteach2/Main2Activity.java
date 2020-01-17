package com.example.myteach2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
 private String name,dob,gender,blood,chk;
 private TextView txt_name,txt_dob,txt_gender,txt_blood,txt_Os;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txt_name = findViewById(R.id.txt_name);
        txt_dob = findViewById(R.id.txt_dob);
        txt_gender = findViewById(R.id.txt_gender);
        txt_blood = findViewById(R.id.txt_blood);
        txt_Os = findViewById(R.id.txt_os);
        name = getIntent().getStringExtra("name");
        dob = getIntent().getStringExtra("dob");
        gender =getIntent().getStringExtra("gender");
        blood = getIntent().getStringExtra("blood");
        chk = getIntent().getStringExtra("chk");
        txt_name.setText(": " + name );
        txt_dob.setText(": " + dob );
        txt_gender.setText(": " + gender );
        txt_blood.setText(": " + blood );
        txt_Os.setText(": " + chk );
    }
}
