package com.example.jb.networkjson;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {
    TextView title,overview,date,rating;
    ImageView imageView;
    String t,o;
    FloatingActionButton floatingActionButton,fav;
    private Boolean isFabOpen = false;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.txt_title);
        overview = findViewById(R.id.txt_overview);
        date = findViewById(R.id.txt_release);
        rating = findViewById(R.id.txt_voting);
        floatingActionButton =  findViewById(R.id.floatingActionButton);
        fav = findViewById(R.id.fav);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fav_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fav_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        floatingActionButton.setOnClickListener(this);
        MovieList movieList = (MovieList)getIntent().getExtras().getSerializable("Title");
        if(movieList != null){
            Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+movieList.getPoster_path()).into(imageView);
            title.setText(movieList.getTitle());
            overview.setText(movieList.getOverview());
            date.setText(movieList.getRelease_date());
            rating.setText(Double.toString(movieList.getVote_average()));
            //TranslateAPI translateAPI = new TranslateAPI();
        }
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MovieDetailActivity.this, "You click favourate button",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id1 = v.getId();
        switch (id1){
            case R.id.floatingActionButton:
                animateFAB();
                break;

            case R.id.fav:
                break;

        }
    }

    public void animateFAB(){

        if(isFabOpen){
            floatingActionButton.startAnimation(rotate_backward);
            fav.startAnimation(fab_close);
            //read.startAnimation(fab_close);
            fav.setClickable(false);
            //read.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            floatingActionButton.startAnimation(rotate_forward);
            fav.startAnimation(fab_open);
            //read.startAnimation(fab_open);
            fav.setClickable(true);
            //read.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }
}
