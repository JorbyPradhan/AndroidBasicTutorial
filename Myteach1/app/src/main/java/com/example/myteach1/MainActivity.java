package com.example.myteach1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private EditText name,age,nrc,fname,mname,fnrc,mnrc;
 Button ok,Cancel;
 String Sname = null;
 int Sage =0;
 TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       name = findViewById(R.id.edt_name);
       age = findViewById(R.id.edt_age);
       nrc = findViewById(R.id.edt_nrc);
       fname = findViewById(R.id.edt_fname);
       mname = findViewById(R.id.edt_mname);
       fnrc = findViewById(R.id.edt_fnrc);
       mnrc = findViewById(R.id.edt_mnrc);
       ok = findViewById(R.id.btn_ok);
       textView = findViewById(R.id.txt_text);
       Cancel = findViewById(R.id.btn_cancel);
       ok.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Sname = name.getText().toString();//Mg Mg
               Sage = Integer.valueOf(age.getText().toString());
               textView.setText(Sname);
               Toast.makeText(MainActivity.this, Sname + " Clicked Ok Button" , Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(MainActivity.this, Main2Activity.class);
               intent.putExtra("name", Sname);
               intent.putExtra("age",Sage);
               intent.putExtra("nrc",nrc.getText().toString());
               intent.putExtra("fnrc",fnrc.getText().toString());
               intent.putExtra("fname",fname.getText().toString());
               intent.putExtra("mname",mname.getText().toString());
               intent.putExtra("mnrc",mnrc.getText().toString());
               startActivity(intent);
           }
       });
    }
}
