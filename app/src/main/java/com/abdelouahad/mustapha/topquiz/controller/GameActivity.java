package com.abdelouahad.mustapha.topquiz.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelouahad.mustapha.topquiz.R;
import com.abdelouahad.mustapha.topquiz.model.Question;
import com.abdelouahad.mustapha.topquiz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mQuestionTextView;
    private Button mAnswerButton1 ;
    private Button mAnswerButton2;
    private Button mAnswerButton3 ;
    private Button mAnswerButton4 ;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    public static final String TAG = GameActivity.class.getSimpleName();

    public static final String BUNDLE_EXTRA_SCORE="BUNDLE_EXTRA_SCORE";
    private boolean mEnableTouchEvents;

    public static final String BUNDLE_STATE_SCORE="BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTION="BUNDLE_STATE_QUESTION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Log.i(TAG,"::onCreate()");


        //Connection aux Widgets (element graphique de l'interface)
        mQuestionTextView =findViewById(R.id.activity_game_question_text);
        mAnswerButton1 = findViewById(R.id.activity_game_answer1_btn);
        mAnswerButton2 = findViewById(R.id.activity_game_answer2_btn);
        mAnswerButton3 = findViewById(R.id.activity_game_answer3_btn);
        mAnswerButton4 = findViewById(R.id.activity_game_answer4_btn);




        //Boolean pour activer la détection au touché de l'écran
        mEnableTouchEvents= true;
        //Attribution d'un tag à chaque boutons
        mAnswerButton1.setTag(0);
        mAnswerButton2.setTag(1);
        mAnswerButton3.setTag(2);
        mAnswerButton4.setTag(3);


        //Relis chaque bouton à la methode OnClick de la GameActivity via mot clé "this"
        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);


        //Objet mQuestionBank stock la banque de question généré par la méthode generateQuestions
        mQuestionBank = this.generateQuestions();

        if(savedInstanceState != null){
            mScore=savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions= savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        }else{
            //Initialise le score et le nombre de question
            mScore =0;
            mNumberOfQuestions=4;
        }
        //Objet mCurrentQuestion récupère la quesiton actuelle provenant de mQuestionBank
        mCurrentQuestion = mQuestionBank.getQuestion();
        //Affichage de la question et de ses choix de réponse
        this.displayQuestion(mCurrentQuestion);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {//lorsque l'activité est détruite
        //Sauvegarde le score et la question dans le Bundle grace aux key BUNDLE_STATE_*****
        outState.putInt(BUNDLE_STATE_SCORE,mScore);
        outState.putInt(BUNDLE_STATE_QUESTION,mNumberOfQuestions);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) { //Lors d'un clic sur un bouton
        int responseIndex = (int) v.getTag();//Récupère le tag généré par le bouton pour l'identifier
        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {//Si le tag correspond a la position de la bonne réponse
            //Good Answer
            //Affiche un message de félicitation
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            //Incrémente (Augmente) le score de l'utilisateur
            mScore++;
        } else {
            //Wrong Answer
            //Sinon signal a l'utilisateur qu'il s'est trompé
            Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;
        new Handler().postDelayed(new Runnable() { //Execute le code après 2 secondes
            @Override
            public void run() {
                mEnableTouchEvents=true;
                //Décrémentation du nombre de question restante
                if (--mNumberOfQuestions == 0){
                    //Si 0 alors find e jeu
                    endGame();
                }else{
                    //Sinon objet mCurrentQuestion est égal à la question suivante
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    //Actualise l'affiche de la question
                    displayQuestion(mCurrentQuestion);
                }
            }
        },2000);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {//active ou non le toucher sur l'écran
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame(){
        //Création d'un builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Well done!") //Titre de la popup (fenêtre)
                .setMessage("Your score is "+mScore) //Message avec le score de l'utilisateur
                .setPositiveButton("OK", new DialogInterface.OnClickListener() { //Creation d'un bouton positif
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(); //Création de l'intent
                        intent.putExtra(BUNDLE_EXTRA_SCORE,mScore); //Associe le score à BUNDLE_EXTRA_SCORE
                        setResult(RESULT_OK,intent);//Tout c'est bien passé
                        finish(); //Termine l'activité courante
                    }
                })
                .create() //creer la fenêtre
                .show();//affiche la fenêtre
    }


    private void displayQuestion(final Question question){
        //Affiche la question et ses réponses possible
        mQuestionTextView.setText(question.getQuestion());
        mAnswerButton1.setText(question.getChoiceList().get(0));
        mAnswerButton2.setText(question.getChoiceList().get(1));
        mAnswerButton3.setText(question.getChoiceList().get(2));
        mAnswerButton4.setText(question.getChoiceList().get(3));
    }

    //Fonction qui renvois un Objet QuestionBank
    private QuestionBank generateQuestions(){

        //NEW Question (Question en String, [réponse1 en String,réponse2 en String,
        // réponse3 en String,réponse4 en String], bonne réponse en Int);

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
        //Retourne la Banque de questions
        return new QuestionBank(Arrays.asList(
                question1,
                question2,
                question3,
                question4,
                question5
        ));

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
