package com.abdelouahad.mustapha.topquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private  TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGreetingText = findViewById(R.id.activity_main_greeting_txt);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButon = findViewById(R.id.activity_main_play_btn);
    }
}
