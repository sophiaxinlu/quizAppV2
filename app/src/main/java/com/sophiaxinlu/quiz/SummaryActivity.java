package com.sophiaxinlu.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


public class SummaryActivity extends AppCompatActivity {
    private static final String SCORE = "score";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent intent = getIntent();
        int finalScore = intent.getIntExtra(SCORE, 0);

        String scoreNum = finalScore + "";
        TextView textView = (TextView) findViewById(R.id.score);
        textView.setText(scoreNum);

    }

}
