package com.example.api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Models.NewsApiResponse;
import Models.NewsHeadlines;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity implements SelectListener , View.OnClickListener{
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1,b2,b3,b4,b5,b6,b7;
    SearchView searchView ;
    Button previousButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("News App");
        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Searching...");
                dialog.show();
                RequestManager manager = new RequestManager(getApplicationContext());
                manager.getNewsHeadlines(listener,"general",query);
                dialog.dismiss();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        RequestManager manager = new RequestManager(getApplicationContext());
        manager.getNewsHeadlines(listener,"general",null);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching news articles");
        dialog.show();

        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn_7);
        b7.setOnClickListener(this);


    }
    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list) {
            if(list.isEmpty()){
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_LONG).show();
            }
            else {
                showNews(list);
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(getApplicationContext(),"An Error Occured",Toast.LENGTH_LONG).show();
        }
    };
    private void showNews(List<NewsHeadlines>list){
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new CustomAdapter(this,list,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(MainActivity.this,DetailsActivity.class)
                .putExtra("data",headlines));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        if(previousButton != null){
            previousButton.setBackgroundColor(Color.TRANSPARENT);
        }
        Button button = (Button) v;
        int color_id = R.color.btn_bg;
        int color = getResources().getColor(color_id);
        button.setBackgroundColor(color);
        previousButton = button;
        String category = button.getText().toString();
        dialog.setTitle("Fetching news articles of " +  category);
        dialog.show();
        RequestManager manager = new RequestManager(getApplicationContext());
        manager.getNewsHeadlines(listener,category,null);
        dialog.dismiss();
    }
}
