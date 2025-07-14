package com.example.istview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityDetails extends AppCompatActivity {

    ImageView imgLoc;
    TextView txtName;
    TextView txtRating;
    TextView txtFee;
    TextView txtAdd;
    TextView txtDesc;
    Button btnComments;



    Handler imgHandler= new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            imgLoc.setImageBitmap((Bitmap)msg.obj);

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
        setContentView(R.layout.activity_details);

        Locations location = (Locations) getIntent().getExtras().getSerializable("location");
        String avgRating = getIntent().getCharSequenceExtra("avgRating").toString();
        imgLoc = findViewById(R.id.imageViewLoc);
        txtName = findViewById(R.id.textViewName);
        txtRating = findViewById(R.id.textViewDetailRating);
        txtFee = findViewById(R.id.textViewFee);
        txtAdd = findViewById(R.id.textViewAddress);
        txtDesc = findViewById(R.id.textViewDescription);
        btnComments = findViewById(R.id.buttonComments);

        btnComments.setOnClickListener(v->{

            Intent i = new Intent(ActivityDetails.this, CommentsActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        });



        LocationsRepository repo = new LocationsRepository();
        repo.downloadImage(((ISTViewApplication)getApplication()).srv,imgHandler,location.getImage());
        txtName.setText(location.getName());
        txtDesc.setText(location.getDescription());
        txtAdd.setText(location.getAddress());
        txtFee.setText("Fee: " + Integer.toString(location.getFee()));
        txtRating.setText("Rating: " + avgRating);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}