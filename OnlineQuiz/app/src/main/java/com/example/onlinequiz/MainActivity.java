package com.example.onlinequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinequiz.databaseConnection.DBConnection;
import com.example.onlinequiz.model.Category;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView lang,diff;
    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";
    public static final String EXTRA_CATEGORY_ID = "extraCategoryID";
    public static final String EXTRA_CATEGORY_NAME = "extraCategoryName";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighScore";
    private TextView textViewhighScore;
    private int highScore;
    private ImageView drop1,drop2;
    DBConnection connection;
    private Button Start_quiz;
    private List<String> Language,Diffculty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lang = findViewById(R.id.language_choose);
        diff = findViewById(R.id.diff_choose);
        drop1 = findViewById(R.id.img_drop);
        drop2 = findViewById(R.id.img_drop1);
        textViewhighScore = findViewById(R.id.txt_Score);
        Start_quiz = findViewById(R.id.start_quiz);
        loadHighscore();

        connection = DBConnection.getInstance();
        Language = new ArrayList<String>();
            try {
                String query = " select category_id from dbo.quiz_questions Group By category_id having(count(*)>0)";
                ResultSet rs = connection.getData(query);
                if (rs != null) {
                    while (rs.next()) {
                        try {
                            //Toast.makeText(getContext(), "aaaa", Toast.LENGTH_LONG).show();
                            Language.add(rs.getString("category_id"));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, Language);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lang.setAdapter(dataAdapter);
                        lang.setCursorVisible(false);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Diffculty = new ArrayList<String>();
            try {
                String query = " select difficulty from dbo.quiz_questions Group By difficulty having(count(*)>0) ";
                ResultSet rs = connection.getData(query);
                if (rs != null) {
                    while (rs.next()) {
                        try {
                            //Toast.makeText(getContext(), "aaaa", Toast.LENGTH_LONG).show();
                            Diffculty.add(rs.getString("difficulty"));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, Diffculty);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        diff.setAdapter(dataAdapter);
                        diff.setCursorVisible(false);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
      /*  lang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lang.showDropDown();
            }
        });
        diff.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diff.showDropDown();
            }
        });*/
        lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang.showDropDown();
            }
        });
        diff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diff.showDropDown();
            }
        });

        drop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang.showDropDown();
            }
        });
        drop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diff.showDropDown();
            }
        });
        Start_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Question_Answer_Activity.class);
                intent.putExtra("Lang",lang.getText().toString());
                //  intent.putExtra("cate_id",categoryID);
                intent.putExtra("Diff",diff.getText().toString());
                startActivityForResult(intent,2);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2){
                int Score = data.getIntExtra(Question_Answer_Activity.EXTRA_SCORE, 0);
                if(Score > highScore) {
                    updateHighscore(Score);
                }
        }
    }

    private void loadHighscore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highScore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewhighScore.setText("HighScore:" + highScore);
    }
    private void updateHighscore(int score) {
        highScore = score;
        textViewhighScore.setText("HighScore:" + highScore);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highScore);
        editor.apply();
    }
  /*  public void Start_Quiz(View view){
       *//* Category selectedCategory = (Category) lang.getText();
        int categoryID = selectedCategory.getId();
        String categoryName = selectedCategory.getName();*//*
        Intent intent = new Intent(MainActivity.this,Question_Answer_Activity.class);
        intent.putExtra("Lang",lang.getText().toString());
        //  intent.putExtra("cate_id",categoryID);
        intent.putExtra("Diff",diff.getText().toString());
        startActivity(intent);

    }*/
}
