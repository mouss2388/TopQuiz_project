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
    private QuestionBank mQuestionBank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUser = new User();

        //Connection aux widgets
        mGreetingText = findViewById(R.id.activity_main_greeting_txt);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButon = findViewById(R.id.activity_main_play_btn);
        //Desactive le bouton
        mPlayButon.setEnabled(false);

        mQuestionBank = this.generateQuestions();

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
                String firstname = mNameInput.getText().toString();
                mUser.setFirstname(firstname);
                Intent gameActivityIntent = new Intent(MainActivity.this,GameActivity.class);
                startActivity(gameActivityIntent);
            }
        });
    }

    public QuestionBank generateQuestions(){
        Question question1 = new Question("Who is the creator of Android?",

                Arrays.asList("Andy Rubin",

                        "Steve Wozniak",

                        "Jake Wharton",

                        "Paul Smith"),

                0);

        Question question2 = new Question("When did the first man land on the moon?",

                Arrays.asList("1958",

                        "1962",

                        "1967",

                        "1969"),

                3);

        Question question3 = new Question("What is the house number of The Simpsons?",

                Arrays.asList("42",

                        "101",

                        "666",

                        "742"),

                3);

        Question question4 = new Question("How many oscars did the Titanic movie got?",

                Arrays.asList("Seven",

                        "Ten",

                        "Nine",

                        "Eleven"),

                3);

        Question question5 = new Question("Which malformation did Marilyn Monroe have when she was born?",

                Arrays.asList("Six toes",

                        "Four fingers",

                        "None",

                        "Joker"),

                0);
        return new QuestionBank(Arrays.asList(
                question1,
                question2,
                question3,
                question4,
                question5
                ));

    }
}
