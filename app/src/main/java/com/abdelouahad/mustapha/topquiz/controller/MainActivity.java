package com.abdelouahad.mustapha.topquiz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abdelouahad.mustapha.topquiz.R;
import com.abdelouahad.mustapha.topquiz.model.User;

public class MainActivity extends AppCompatActivity {

    private  TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButon;
    private User mUser;
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int GAME_ACTIVITY_REQUEST_CODE = 30;

    private SharedPreferences mPreferences;

    public static final String PREF_KEY_SCORE="PREF_KEY_SCORE";
    public static final String  PREF_KEY_FIRSTNAME="PREF_KEY_FIRSTNAME";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(GAME_ACTIVITY_REQUEST_CODE==requestCode && RESULT_OK==resultCode){
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE,0);
            //sauvegarde le score de l'utilisateur
            mPreferences.edit().putInt(PREF_KEY_SCORE,score).apply();

            Log.e(TAG,"score:"+score);
            welcomeUser();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"::onCreate()");
        //Instance d'un nouvel objet utilisateur
        mUser = new User();
        //Preferences accessible seulement depuis cette activité
        mPreferences =getPreferences(MODE_PRIVATE);
        //Connection aux widgets
        mGreetingText = findViewById(R.id.activity_main_greeting_txt);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButon = findViewById(R.id.activity_main_play_btn);
        //Désactive le bouton
        mPlayButon.setEnabled(false);


        //Check si une partie existe
        welcomeUser();



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
                //sauvegarde le firstname dans les preferences
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME,mUser.getFirstname()).apply();
                //Creer un intent pour passer de MainActivity -> GameActivity
                Intent gameActivityIntent = new Intent(MainActivity.this,GameActivity.class);
                //Activity avec un identifiant (GAME_ACTIVITY_REQUEST_CODE)
                startActivityForResult(gameActivityIntent,GAME_ACTIVITY_REQUEST_CODE);
            }
        });
    }


    private void welcomeUser(){
        if(mPreferences.getString(PREF_KEY_FIRSTNAME,null)!=null){
            //Stock firstname et score dans des variables
            String firstname=mPreferences.getString(PREF_KEY_FIRSTNAME,null);
            int score = mPreferences.getInt(PREF_KEY_SCORE,0);

            //Variable contenant la phrase avec des balises HTML pour la mise en forme
            String salutation= "Welcome back, "+firstname+"!<br/> Your last score was "+score+", will you do better this time?";
            //Test la version d'ANDROID de l'application
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {//Si SDK >= Version Nougat
                //Affiche le TextView avec le HTML
                mGreetingText.setText(Html.fromHtml(salutation,Html.FROM_HTML_MODE_COMPACT));
            } else { // Si inférieur
                mGreetingText.setText(Html.fromHtml(salutation));
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"::onResume()");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"::onDestroy()");
    }
}
