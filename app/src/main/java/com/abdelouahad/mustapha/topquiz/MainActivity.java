package com.abdelouahad.mustapha.topquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
        //Connection aux widgets
        mGreetingText = findViewById(R.id.activity_main_greeting_txt);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButon = findViewById(R.id.activity_main_play_btn);
        //Desactive le bouton
        mPlayButon.setEnabled(false);

        //Fonctions de traitement pour le champs EditText
        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButon.setEnabled(s.toString().length()>0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Action lors du click sur le bouton
        mPlayButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivityIntent = new Intent(MainActivity.this,GameActivity.class);
                startActivity(gameActivityIntent);
            }
        });
    }
}
