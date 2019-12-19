package com.example.jb.demoreg;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jb.demoreg.nrc.NRCValidator;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    final int REQUEST_CODE_GALLERY =999;
    //Integer PICK_IMAGE = 0;
    private Button Register;
    EditText name,nrc,fname,email,phone;
    TextView age;
    TextView dob;
    RadioButton male,female;
    String s ;
    Uri imageUri;
    private Button choose;
    DatabaseHandler databaseHandler;
    ImageView imageView;
    User user;
    String state = null;
    String district = null;
    String naing = null;
    String registerNo = null;
    int seY;
    NRCValidator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.editText);
        age= findViewById(R.id.editText3);
        nrc  = findViewById(R.id.editText2);
        imageView = findViewById(R.id.imageView);
        dob = findViewById(R.id.editText4);
       fname = findViewById(R.id.editText5);
       email = findViewById(R.id.editText6);
       phone = findViewById(R.id.editText7);
       male = findViewById(R.id.male);
       female = findViewById(R.id.female);
       Register = findViewById(R.id.reg);
       choose = findViewById(R.id.cImg);


       choose.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
               ,REQUEST_CODE_GALLERY);
           }
       });
       Register.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.O)
           @Override
           public void onClick(View v) {

               //age.setText(Year.now().getValue());
               String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

               String NRCpattern = "^([\\d]{1,2})\\/([\\w]{3}|[\\w]{6})[(](?:N|NAING)[)]([\\d]{6})$";

               if(email.getText().toString().isEmpty()) {
                   Toast.makeText(getApplicationContext(),"enter email address",Toast.LENGTH_SHORT).show();
               }else {
                   if (email.getText().toString().trim().matches(emailPattern)) {
                       if (nrc.getText().toString().trim().matches(NRCpattern)) {
                           String fullNRc = nrc.getText().toString();
                           String[] nrcArr = fullNRc.split( "[/()]", 4);
                           if(nrcArr.length == 4){
                               state = nrcArr[0];
                               district = nrcArr[1];
                               naing = nrcArr[2];
                               registerNo = nrcArr[3];
                           }
                           else {
                               Toast.makeText(RegisterActivity.this, "NRC format is not invalid!!",Toast.LENGTH_SHORT).show();
                           }
                           if (Integer.parseInt(state)>0 && Integer.parseInt(state)<15){
                               byte[] newEntryImg = imageTobyte(imageView);
                               databaseHandler = new DatabaseHandler(RegisterActivity.this);
                               user = new User(name.getText().toString(), nrc.getText().toString(), age.getText().toString(), dob.getText().toString(), s, fname.getText().toString(), email.getText().toString(), phone.getText().toString(), newEntryImg);
                               //user = new User(newEntryImg);
                               databaseHandler.addData(user);
                               Toast.makeText(getApplicationContext(), "File database created!", Toast.LENGTH_SHORT).show();
                           }
                           else {
                               Toast.makeText(RegisterActivity.this, "NRC format is not invalid!!",Toast.LENGTH_SHORT).show();
                           }

                       }
                       else {
                           nrc.setError("Enter Correct NRC Number");
                           Toast.makeText(getApplicationContext(),"Invalid NRC address", Toast.LENGTH_SHORT).show();
                       }
                   }else {
                       email.setError("Enter Correct Email number");
                       Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                   }
               }

           }
       });
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else
            {
                Toast.makeText(RegisterActivity.this, "You don't have the permission to access file", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        imageUri = data.getData();
        try{
            InputStream inputStream= getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        super.onActivityResult(requestCode,resultCode,data);
       // imageView.setImageURI(imageUri);
    }
    private byte[] imageTobyte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    public void onDateClicked(View view){
        Calendar c = Calendar.getInstance();
        final int mY=c.get(Calendar.YEAR);
        int mM=c.get(Calendar.MONTH);
        int mD=c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd=new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dob.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                seY = mY-year;
                age.setText(String.valueOf(seY));


            }
        },mY,mM,mD);
        dpd.show();
    }
    public void onRadioButtonClicked(View v){
        boolean check=((RadioButton)v).isChecked();
        switch (v.getId()){
            case R.id.male:if(check)s="M";break;
            case R.id.female:if(check)s="F";break;
        }
    }
}
