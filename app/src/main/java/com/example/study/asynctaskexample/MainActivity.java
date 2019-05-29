package com.example.study.asynctaskexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText book_name;
    TextView results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        book_name=findViewById(R.id.book_name);
        results=findViewById(R.id.result);
    }

    public void search(View view) {
        String s=book_name.getText().toString();
        new BooksAsyncTask(this,s).execute();
    }
}
