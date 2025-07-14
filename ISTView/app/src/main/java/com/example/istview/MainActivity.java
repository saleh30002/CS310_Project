package com.example.istview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recView;
    ProgressBar prgBar;
    TabLayout tabLayout;
    TabLayout.OnTabSelectedListener tabchanger = new TabLayout.OnTabSelectedListener(){
        @Override
        public void onTabSelected(@NonNull TabLayout.Tab tab) {
            repo.locationByCategory(((ISTViewApplication)getApplication()).srv, handler, tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText().toString());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
    LocationsRepository repo;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            List<Locations> data = (List<Locations>) msg.obj;
            LocationAdapter adp =
                    new LocationAdapter(MainActivity.this, data);
            recView.setAdapter(adp);

            prgBar.setVisibility(View.INVISIBLE);
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout_main);

        prgBar = findViewById(R.id.progressBarList);
        recView = findViewById(R.id.recyclerViewList);
        recView.setLayoutManager(new LinearLayoutManager(this));

        prgBar.setVisibility(View.VISIBLE);

        repo = new LocationsRepository();
        //repo.getAllLocations(((ISTViewApplication)getApplication()).srv, handler);
        repo.locationByCategory(((ISTViewApplication)getApplication()).srv, handler, tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText().toString());

        tabLayout.addOnTabSelectedListener(tabchanger);
    }
}