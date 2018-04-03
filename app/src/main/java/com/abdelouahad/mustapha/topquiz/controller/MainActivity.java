package com.abdelouahad.mustapha.topquiz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abdelouahad.mustapha.topquiz.R;
import com.abdelouahad.mustapha.topquiz.model.Question;
import com.abdelouahad.mustapha.topquiz.model.QuestionBank;
import com.abdelouahad.mustapha.topquiz.model.User;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private  TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButon;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //Instance d'un nouvel objet utilisateur
        mUser = new User();

        //Connection aux widgets
        mGreetingText = findViewById(R.id.activity_main_greeting_txt);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButon = findViewById(R.id.activity_main_play_btn);
        //Désactive le bouton
        mPlayButon.setEnabled(false);



        //Fonctions de traitement pour le champs EditText
        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Active ou désactive le bouton Play en fonction de la longueur du nom
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
                //Récupère le nom du EditText
                String firstname = mNameInput.getText().toString();
                //Initialise nom de l'utilisateur
                mUser.setFirstname(firstname);
                //Creer un intent pour passer de MainActivity -> GameActivity
                Intent gameActivityIntent = new Intent(MainActivity.this,GameActivity.class);
                startActivity(gameActivityIntent);
            }
        });
    }


}
