package com.example.jb.demoreg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
 TextView name,age,nrc,gender,dob,fname,email,phone;
 DatabaseHandler databaseHandler;
 TextInputEditText uName,uPhone;
 Button uUpdate;
 MainActivity m;
 //List<User> lst ;
    String n;
    User user;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name = findViewById(R.id.namesh);
        age = findViewById(R.id.agesh);
        dob = findViewById(R.id.dobsh);
        imageView = findViewById(R.id.imgsh);
        nrc = findViewById(R.id.nrcsh);
        gender =findViewById(R.id.gendersh);
        fname = findViewById(R.id.fnamesh);
        email = findViewById(R.id.emailsh);
        phone = findViewById(R.id.phonesh);
       databaseHandler = new DatabaseHandler(this);


               //lst = new ArrayList<>();
       //user = (User) getIntent().getSerializableExtra("Name");
        //Log.i("MAin2Ac", user.getName());
        //String s = getIntent().getStringExtra("Name");
       //User a = (User)getIntent().getExtras().getSerializable("Title");
        n = getIntent().getStringExtra("Name");
        //User userdata = (User) getIntent().getExtras().getSerializable("Title");
            Log.e("KAKA LApi", n);
            user = databaseHandler.getAllContact(n);

            //lst =databaseHandler.getAllContact();
            name.setText(user.getName());
            age.setText(user.getAge());
            nrc.setText(user.getNrc());
            byte[] image = user.getImg();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            imageView.setImageBitmap(bitmap);
            //imageView.setImageResource(R.drawable.ic_launcher_background);
            dob.setText(user.getDob());
            gender.setText(user.getGender());
            fname.setText(user.getFname());
            email.setText(user.getEmail());
            phone.setText(user.getPhone());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.app_bar_delete:
                databaseHandler.deleteData(name.getText().toString());
                Intent intent = new Intent(Main2Activity.this,ListDataActivity.class);
                startActivity(intent);
                break;
            case R.id.app_bar_update:
                updateName();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.updatename,null);
        uName = v.findViewById(R.id.updateName);
        uPhone = v.findViewById(R.id.updatePhone);
        uUpdate = v.findViewById(R.id.onUpdateBtn);
        builder.setView(v);
        builder.setTitle("Update Name");
       final AlertDialog dialog = builder.create();
       dialog.show();
       uUpdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String namedata = uName.getText().toString().trim();
               String phonedata = uPhone.getText().toString().trim();
               if(namedata.isEmpty()){
                   uName.setError("Fill New Name");
                   return;
               }
               if(phonedata.isEmpty()){
                   uPhone.setError("Fill Ph number");
                   return;
               }
               databaseHandler.updateData(namedata,phonedata,nrc.getText().toString().trim());

               if(true){
                   Toast.makeText(Main2Activity.this,"Success", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(Main2Activity.this,ListDataActivity.class);
                   startActivity(intent);
                   dialog.dismiss();
               }
               else {
                   Toast.makeText(Main2Activity.this,"Fail", Toast.LENGTH_SHORT).show();
               }


           }
       });

    }
}
