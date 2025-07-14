package com.example.istview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    RecyclerView recView;
    ProgressBar prgBar;

    CommentsRepository repo;

    Locations location;

    //Locations loc = new Locations("645708d9b1d1496b8debeff5", "Viaport AVM", "car", 200, "ds", "Asfdfs", "asdd");


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            List<Comments> data = (List<Comments>) msg.obj;
            CommentsAdapter adp =
                    new CommentsAdapter(CommentsActivity.this, data);
            recView.setAdapter(adp);

            prgBar.setVisibility(View.INVISIBLE);
            return true;
        }
    });


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        //tabLayout = findViewById(R.id.tabLayout_main);

        prgBar = findViewById(R.id.progressBarList2);
        recView = findViewById(R.id.recyclerViewListComment);
        recView.setLayoutManager(new LinearLayoutManager(this));

        location = (Locations) getIntent().getExtras().getSerializable("location");

        prgBar.setVisibility(View.VISIBLE);

        Button write_comment = findViewById(R.id.writeComment);

        write_comment.setOnClickListener(v-> {

            Intent i = new Intent(CommentsActivity.this, WriteCommentActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        });

        repo = new CommentsRepository();
        //repo.getAllLocations(((ISTViewApplication)getApplication()).srv, handler);
        repo.commentByLocation(((ISTViewApplication)getApplication()).srv, handler, location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        repo = new CommentsRepository();
        repo.commentByLocation(((ISTViewApplication)getApplication()).srv, handler, location);

    }
}
