package com.example.bo.mathtest;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    TextView time, question, a1, a2, a3, a4, totalScore, yOrN;
    CountDownTimer countDownTimer;
    CountDownTimer scorePanel;
    Button playAgain;

    Integer totalTime = 29, rightAnswer = 0, totalAnswer = 0, randomLeft, randomRight, total;
    Integer[] possibleAnswer = new Integer[4];
    Boolean pressed = false;
    public void eachQuestion(){
        randomLeft = new Random().nextInt(20);
        randomRight = new Random().nextInt(20);
        total = randomLeft+randomRight;
        int randomPosition = new Random().nextInt(4);
        for(int i = 0; i<=3; i++){
            if(i == randomPosition){
                possibleAnswer[i] = total;
                continue;
            }
            else{
                while ((possibleAnswer[i] = new Random().nextInt(39)) == total){
                    possibleAnswer[i] = new Random().nextInt(39);
                }
            }
        }
        question.setText(randomLeft + " + "+randomRight+" = ?");
        a1 = (TextView) findViewById(R.id.a1);
        a1.setText(possibleAnswer[0]+"");
        a2 = (TextView) findViewById(R.id.a2);
        a2.setText(possibleAnswer[1]+"");
        a3 = (TextView) findViewById(R.id.a3);
        a3.setText(possibleAnswer[2]+"");
        a4 = (TextView) findViewById(R.id.a4);
        a4.setText(possibleAnswer[3]+"");

    }
    public boolean onClick(View view){
        totalAnswer++;
        TextView textView = (TextView) view;
        pressed = true;
        if(Integer.parseInt(textView.getText().toString()) == total) {
            rightAnswer++;
            yOrN.setText("YES");
            return true;
        }
        else {
            yOrN.setText("Wrong");
        }
        return  false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time = (TextView) findViewById(R.id.time);
        question = (TextView) findViewById(R.id.question);
        totalScore = (TextView) findViewById(R.id.totalScore);
        yOrN = (TextView) findViewById(R.id.yOrN);

        eachQuestion();

        startThread();
        playAgain = (Button) findViewById(R.id.playAgain);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightAnswer = 0;
                totalAnswer = 0;
                totalTime = 29;
                playAgain.setAlpha(0f);
                yOrN.setText("");
                totalScore.setText("0 / 0");
                eachQuestion();
                startThread();
            }
        });

    }

    public void startThread(){
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                time.setText(""+totalTime);
                totalTime--;

            }

            @Override
            public void onFinish() {

                yOrN.setText("Your score is: "+rightAnswer + " / "+ totalAnswer);
                playAgain.setAlpha(1f);
            }
        }.start();

        scorePanel = new CountDownTimer(30000, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(pressed){
                    pressed = false;
                    totalScore.setText(rightAnswer + " / "+ totalAnswer);
                    eachQuestion();
                }
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

}
