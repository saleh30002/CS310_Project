package com.example.istview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class WriteCommentActivity extends AppCompatActivity {

    TextView txtname;
    EditText edttxtName;
    EditText edttxtComment;
    RadioGroup rdbtn;
    Button postbtn;
    int rating;
    Locations location;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            finish();
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
        setContentView(R.layout.activity_write_comment);

        location = (Locations) getIntent().getExtras().getSerializable("location");

        txtname = findViewById(R.id.textViewWriteName);
        txtname.setText("Write Comment on: " + location.getName());
        edttxtName = findViewById(R.id.editTextUser);
        edttxtComment = findViewById(R.id.editTextComment);
        rdbtn = findViewById(R.id.radioGroup);
        postbtn = findViewById(R.id.buttonPost);

        postbtn.setOnClickListener(v->{
            RadioButton rd = findViewById(rdbtn.getCheckedRadioButtonId());
            rating = Integer.parseInt(rd.getText().toString());
            CommentsRepository repo = new CommentsRepository();
            repo.postComment(((ISTViewApplication)getApplication()).srv, handler, edttxtName.getText().toString(),edttxtComment.getText().toString(),(double) rating, location);

        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }
}