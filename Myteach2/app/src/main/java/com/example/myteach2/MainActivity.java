package com.example.myteach2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
 private Button Save;
 private RadioButton male,female;
 private EditText name,dob;
 private CheckBox Ios,Lin,Win,Android;
 private Spinner blood;
 private ImageView img;
 private String gender = null;
 private List<String> OsList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setId();
        OsList = new ArrayList<>();
        //For CheckBox
        Ios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Ios.isChecked()){
                    OsList.add("IOS");
                }
                else
                    OsList.remove("IOS");
            }
        });
        Android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Android.isChecked()){
                    OsList.add("Android");
                }
                else
                    OsList.remove("Android");
            }
        });
        Win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Win.isChecked()){
                    OsList.add("Window");
                }
                else
                    OsList.remove("Window");
            }
        });
        Lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Lin.isChecked()){
                    OsList.add("Linux");
                }
                else
                    OsList.remove("Linux");
            }
        });
        //For Date picker
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                final int mY=c.get(Calendar.YEAR);
                int mM=c.get(Calendar.MONTH);
                int mD=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd=new DatePickerDialog(MainActivity.this,new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        dob.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                        //seY = mY-year;
                       // age.setText(String.valueOf(seY));
                    }
                },mY,mM,mD);
                dpd.show();
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                for(String s: OsList){
                    stringBuilder.append(s).append("\n");
                }
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("dob",dob.getText().toString());
                intent.putExtra("gender",gender);
                intent.putExtra("blood",blood.getSelectedItem().toString());
                intent.putExtra("chk",stringBuilder.toString());
                startActivity(intent);
            }
        });
    }
    public void OnRadioButtonClicked(View view){
        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.male :
                if(check)gender = "M";
                break;
            case R.id.female:
                if(check)gender = "F";
                break;
        }
    }



    private void setId(){
        Save = findViewById(R.id.btn_Save);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        name = findViewById(R.id.edt_name);
        dob = findViewById(R.id.edt_dob);
        blood = findViewById(R.id.select_bloodtype);
        img = findViewById(R.id.img_view);
        Ios = findViewById(R.id.chkIos);
        Android =findViewById(R.id.chkAndroid);
        Lin = findViewById(R.id.chkLinux);
        Win = findViewById(R.id.chkWindows);
    }
}
