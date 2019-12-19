package com.example.jb.networkjson;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    TextView textView;
    int i = 1;
    ListView movieDetailList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieDetailList = findViewById(R.id.list_movie);
        movieDetailList.setOnItemClickListener(this);
        new CheckConnectionStatus().execute("https://api.themoviedb.org/3/movie/popular?api_key=7142a40c54b2c690a1f53e697a1d51aa&language=en-US&page=1");
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    url = new URL("https://www.google.com");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try {
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    Log.i("Response :" , String.valueOf(httpsURLConnection.getResponseCode()));
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Error:", e.getMessage());
                }
            }
        }).start();*/



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("onItemClick:", "This method is invoked");
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra("Title", (MovieList)parent.getItemAtPosition(position));
        startActivity(intent);
    }

    class CheckConnectionStatus extends AsyncTask<String , Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            URL url= null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
               // httpsURLConnection.setDoOutput(true);
/*
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", "admin")
                        .appendQueryParameter("password", "James1997");*/
//Getting object of outputstream from httpUrlConnection to write some data to stream
                /*OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                bufferedWriter.write(builder.build().getEncodedQuery());
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();*/


                httpsURLConnection.connect();
//Getting inputsteam from connection, that is response which we got from server
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String s = bufferedReader.readLine();
                bufferedReader.close();

                return s;

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Error:", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                ArrayList<MovieList> arrayList = new ArrayList<>();
               //Map<String, Integer > cmap = new HashMap<>();
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i<jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    MovieList movieList = new MovieList();
                    movieList.setTitle(object.getString("title"));
                    movieList.setOverview(object.getString("overview"));
                    movieList.setRelease_date(object.getString("release_date"));
                    movieList.setVote_average(object.getDouble("vote_average"));
                    movieList.setPoster_path(object.getString("poster_path"));
                    arrayList.add(movieList);
                   // cmap.put(object.getString("name"),object.getInt("id"));
                    //textView.setText(String.valueOf(cmap.get("Regency Enterprises")));
                }
                Log.i("List:", arrayList.get(2).getOverview());
                MoviesAdapter moviesAdapter = new MoviesAdapter(MainActivity.this, R.layout.item, arrayList);
                movieDetailList.setAdapter(moviesAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Error Lar p:", e.getMessage());
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
            if (id == R.id.next) {
                ++i;
                new CheckConnectionStatus().execute("https://api.themoviedb.org/3/movie/popular?api_key=7142a40c54b2c690a1f53e697a1d51aa&language=en-US&page=" + i + "");
            }
        return super.onOptionsItemSelected(item);
    }
}
