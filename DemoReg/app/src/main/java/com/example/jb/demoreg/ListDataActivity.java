package com.example.jb.demoreg;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class ListDataActivity extends AppCompatActivity {
  public ListView listView;
    DatabaseHandler db;
    List<User> listData ;
    ListAdapter adapter;
    private ProgressBar progressBar;
    private int nProgressStatus = 0;
    SwipeRefreshLayout layout;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        listView = findViewById(R.id.lst);
        layout = findViewById(R.id.SwipeRefresh);
        String arr1[] = {"James", "Jobby", "Htut","Bimgo","Myogyi","Waigyi"};

        listData = new ArrayList<>();
        db = new DatabaseHandler(ListDataActivity.this);
        populateView();
        adapter = new ListAdapter(ListDataActivity.this,R.layout.item,listData);
        listView.setAdapter(adapter);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter = new ListAdapter(ListDataActivity.this,R.layout.item,listData);
                listView.setAdapter(adapter);
                layout.setRefreshing(false);
            }
        });
        //progressBar = findViewById(R.id.progree_bar);
        //listView.setOnItemClickListener(this);
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                while (nProgressStatus <100){
                    nProgressStatus++;
                    SystemClock.sleep(10);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(nProgressStatus);
                        }
                    });
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listData = new ArrayList<>();
                        db = new DatabaseHandler(ListDataActivity.this);
                        populateView();
                        //listData = db.getAllContact();
                        progressBar.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        adapter = new ListAdapter(ListDataActivity.this,R.layout.item,listData);
                        listView.setAdapter(adapter);
                    }
                });
            }
        }).start();*/
      // new RefreshClass().execute();



    }
   /* class  RefreshClass extends AsyncTask<String ,Void,String >{

        @Override
        protected String doInBackground(String... strings) {
            while (nProgressStatus <100) {
                nProgressStatus++;
                SystemClock.sleep(10);
                progressBar.setProgress(nProgressStatus);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            listData = new ArrayList<>();
            db = new DatabaseHandler(ListDataActivity.this);
            populateView();
            //listData = db.getAllContact();
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adapter = new ListAdapter(ListDataActivity.this,R.layout.item,listData);
            listView.setAdapter(adapter);

        }
    }*/

    private void populateView() {
        Cursor data = db.getData();
        while (data.moveToNext()){
            String name = data.getString(0);
            byte[] img = data.getBlob(1);
            listData.add(new User(name,img));
        }
    }
    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("onItemClick:", "This method is invoked");
        Intent intent = new Intent(ListDataActivity.this, Main2Activity.class);
        intent.putExtra("Title", (User)parent.getItemAtPosition(position));
        startActivity(intent);
    }*/
}
