package com.example.sahithi_rvs.studentsworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class EvaluateActivity extends Activity {

    TextView myres;
    TextView correct;
    TextView ques;
    TextView explanation;
    TextView Score;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate);
        System.out.println("evaluateInstanstiated");
        Intent intent = getIntent();
        try {
            JSONArray correctans = new JSONArray(intent.getStringExtra("correctans"));
            System.out.println(correctans);
            HashMap<String,String > response = (HashMap<String,String>)intent.getSerializableExtra("response");
            System.out.println("printing in evaluate");
            System.out.println(response);
            String score = intent.getStringExtra("score");
            Score =(TextView)findViewById(R.id.scor);
            Score.setText(score);
            evaluate(correctans, response, 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void evaluate(final JSONArray correctans, final HashMap<String,String> response, final int quesno) {

        if(quesno >= correctans.length()){
            return;
        }
        else if(quesno < 0){
            return;
        }

        try {
            JSONObject temp = correctans.getJSONObject(quesno);
            String questionid = temp.getString("question_id");
            String question = temp.getString("question");
            String explanationofquestion = temp.getString("explanation");
            String answer = temp.getString("answer");
            String myresponse;
            try {
                myresponse = response.get(questionid);
            }
            catch (Exception e){
                myresponse = "NA";
            }
            ques = (TextView) findViewById(R.id.ques);
            ques.setText(question);
            explanation = (TextView) findViewById(R.id.explanation);
            explanation.setText(explanationofquestion);
            myres = (TextView) findViewById(R.id.myres);
            myres.setText(myresponse);
            correct = (TextView) findViewById(R.id.correct);
            correct.setText(answer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btnnext = (Button) findViewById(R.id.next);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluate(correctans, response, quesno+1);
            }
        });

        Button btnprev = (Button) findViewById(R.id.prev);
        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluate(correctans, response, quesno - 1);
            }
        });
    }
}
