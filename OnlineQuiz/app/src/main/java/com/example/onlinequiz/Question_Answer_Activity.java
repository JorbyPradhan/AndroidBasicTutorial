package com.example.onlinequiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinequiz.databaseConnection.DBConnection;
import com.example.onlinequiz.model.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class Question_Answer_Activity extends AppCompatActivity {
    private String diff,lang;
    public static final String EXTRA_SCORE = "extraScore";
    public static final long COUNTDOWN_IN_MILLITS = 30000;
    private TextView textViewQuestion;
    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST ="keyQuestionList";
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private RadioGroup rdG;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private TextView difficulty;
    private TextView textViewCategory;
    private Button Confirm;
    private ArrayList<Question> questionList;
    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private int score;
    private boolean answered;
    Question question;
    private long backPressedTime;
    DBConnection dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_answer);

        textViewQuestion = (TextView)findViewById(R.id.question1);
        textViewScore = (TextView)findViewById(R.id.score);
        textViewQuestionCount = (TextView)findViewById(R.id.question_count);
        textViewCountDown = (TextView)findViewById(R.id.quiztime);
        textViewCategory = (TextView)findViewById(R.id.text_view_cate);
        rdG = (RadioGroup)findViewById(R.id.group);
        rb1=(RadioButton)findViewById(R.id.op1);
        rb2=(RadioButton)findViewById(R.id.op2);
        rb3=(RadioButton)findViewById(R.id.op3);
        rb4=(RadioButton)findViewById(R.id.op4);
        difficulty=(TextView)findViewById(R.id.text_view_difficulty);
        Confirm=(Button)findViewById(R.id.confrim);
        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd = textViewCountDown.getTextColors();
       // int cateId =getIntent().getIntExtra("cate_id", 0);
        diff = getIntent().getStringExtra("Diff");
        lang = getIntent().getStringExtra("Lang");
        textViewCategory.setText("Category: " + lang);
        difficulty.setText(" Difficulty: " + diff);
        questionList = new ArrayList<>();

        if (savedInstanceState == null){
            try {
            dbHelper=DBConnection.getInstance();
            String query = " select * from dbo.quiz_questions where category_id = '" + lang + "' and difficulty = '" + diff + "' ;";
            //questionList = dbHelper.getQuestions(cateId,diff);
            ResultSet rs = dbHelper.getData(query);
            if (rs != null) {
                while (rs.next()) {
                    try {
                        //Toast.makeText(getContext(), "aaaa", Toast.LENGTH_LONG).show();
                        //Diffculty.add(rs.getString("difficulty"));
                        questionList.add(new Question(rs.getString("question"),rs.getString("option1"),
                                rs.getString("option2"),rs.getString("option3"),rs.getString("option4"),
                                rs.getInt("answer_nr"),rs.getString("difficulty"),
                                rs.getString("category_id")));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);
            showNextQuestion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            questionList =savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            if(questionList == null){
                finish();
            }
            questionCountTotal =questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCounter -1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if(!answered){
                startCountDown();
            }
            else {
                updateCountDownText();
                showSolution();
            }
        }
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(Question_Answer_Activity.this, "please select answer", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    showNextQuestion();
                }
            }
        });

    }
    private void updateCountDownText() {
        int min =(int) (timeLeftInMillis/1000)/60;
        int sec = (int) (timeLeftInMillis/1000)%60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
        textViewCountDown.setText(timeFormatted);
        if(timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        }
        else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }
    private void showSolution(){
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 is Correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is Correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is Correct");
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 4 is Correct");
                break;
        }
        if(questionCounter < questionCountTotal) {
            Confirm.setText("Next");
        }
        else {
            Confirm.setText("Finish");
        }
    }
    private void checkAnswer(){
       /* dbHelper = DBConnection.getInstance();
        String query = "select answer_nr from dbo.quiz_questions where question = '" + textViewQuestion.getText().toString() + "'";
        ResultSet rs = dbHelper.getData(query);
        try {
            if(rs.next()){
                currentQuestion = rs.getInt("answer_nr");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        answered=true;
        countDownTimer.cancel();
        RadioButton rbSelected = (RadioButton)findViewById(rdG.getCheckedRadioButtonId());
        int answerNr = rdG.indexOfChild(rbSelected)+1;
        if(answerNr == currentQuestion.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }
        showSolution();
    }
    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rdG.clearCheck();
        if(questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOp1());
            rb2.setText(currentQuestion.getOp2());
            rb3.setText(currentQuestion.getOp3());
            rb4.setText(currentQuestion.getOp4());
            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            Confirm.setText("Confirm");
            timeLeftInMillis = COUNTDOWN_IN_MILLITS;
            startCountDown();
        }
        else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SCORE, score);
        Log.i("SETdfsdfdas", String.valueOf(score));
        setResult(2, intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        if(backPressedTime+2000 > System.currentTimeMillis()){
            finishQuiz();
        }
        else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_LONG).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionCounter);
        outState.putLong(KEY_MILLIS_LEFT, timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
    }
}

