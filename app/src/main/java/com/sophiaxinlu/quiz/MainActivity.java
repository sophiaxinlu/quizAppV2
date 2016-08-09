package com.sophiaxinlu.quiz;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.CheckBox;
=======
>>>>>>> c08ad89815d4a2051088976c4a4f15b67efac318
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] mQuestions;
    private static final String TAG_QUESTION = "question";
    private static final String TAG_ANSWER = "answer";
    private static final String TAG_QUESTION_TYPE = "type";
    private static final String TAG_OPTIONS = "options";
    private static final String TYPE_RADIO = "radio";
    private static final String TYPE_TEXT = "text";
    private static final String TYPE_MULTI_CHOICE = "multi";
    private static final String TAG_OPTION1 = "1";
    private static final String TAG_OPTION2 = "2";
    private static final String TAG_OPTION3 = "3";
    private static final String TAG_OPTION4 = "4";
    private static final String SCORE = "score";
    private int i = 1;
    private RadioGroup radioGroup;
    private String answer;
    private String question;
    private String type;
    private String selectedAnswer;
    private String answerText;
    private JSONArray answerArr;

    private int totalScore = 0;

    private String[] mBackgroundColor ={
            "#3079ab", // dark blue
            "#838cc7", // lavender
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#637a91" // dark gray
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String jsonString = loadJSONFromAsset("questions.json");

        if(jsonString != null) {

            try {
                JSONObject obj = new JSONObject(jsonString);
                final JSONArray jsonArray = obj.optJSONArray("Olympics");

                final TextView QuestionTextView = (TextView) findViewById(R.id.QuestionID);
                final TextView AnswerTextView  = (TextView) findViewById(R.id.AnswerID);
                final TextView SequenceTextView  = (TextView) findViewById(R.id.seq);
                final RadioButton Radio1View  = (RadioButton) findViewById(R.id.radio_1);
                final RadioButton Radio2View  = (RadioButton) findViewById(R.id.radio_2);
                final RadioButton Radio3View  = (RadioButton) findViewById(R.id.radio_3);
                final RadioButton Radio4View  = (RadioButton) findViewById(R.id.radio_4);

                final CheckBox Checkbox1View  = (CheckBox) findViewById(R.id.checkbox_1);
                final CheckBox Checkbox2View  = (CheckBox) findViewById(R.id.checkbox_2);
                final CheckBox Checkbox3View  = (CheckBox) findViewById(R.id.checkbox_3);
                final CheckBox Checkbox4View  = (CheckBox) findViewById(R.id.checkbox_4);

                final ScrollView questionBg = (ScrollView) findViewById(R.id.background);
                final Button nextButton = (Button) findViewById(R.id.next);
                final Button submitButton = (Button) findViewById(R.id.submit);
                final RadioGroup radioGroupView = (RadioGroup) findViewById(R.id.options);

                final LinearLayout multiChoiceView = (LinearLayout) findViewById(R.id.multiChoice);
                final EditText editTextView = (EditText) findViewById(R.id.text_answer);



                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(0);

                answer = jsonObject.getString(TAG_ANSWER);
                type = jsonObject.getString(TAG_QUESTION_TYPE);

                AnswerTextView.setText("");
                View.OnClickListener nextButtonListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JSONObject jsonObject = null;
                        try {
                            int qLen = jsonArray.length();

                            if (type.equals(TYPE_RADIO)) {
                                if ((selectedAnswer != null && !selectedAnswer.isEmpty()) && selectedAnswer.equals(answer)){
                                    totalScore += 10;
                                }
                            } else if (type.equals(TYPE_TEXT)) {
                                answerText = editTextView.getText().toString();
                                if ((answerText != null && !answerText.isEmpty()) && answerText.equals(answer)){
                                    totalScore += 10;
                                }
                            } else if (type.equals(TYPE_MULTI_CHOICE)) {
                                boolean isAnswerSelected = false;
                                for (int i = 0; i <answerArr.length(); i++ ) {
                                    try {
                                        String choice = answerArr.getString(i);

                                        if ((choice != null && !choice.isEmpty()) &&  choice.equals(TAG_OPTION1) ) {
                                            if (Checkbox1View.isChecked()) {
                                                isAnswerSelected = true;
                                            } else {
                                                isAnswerSelected = false;
                                            }
                                        } else if((choice != null && !choice.isEmpty()) && choice.equals(TAG_OPTION2)) {
                                            if (Checkbox2View.isChecked()) {
                                                isAnswerSelected = true;
                                            }else {
                                                isAnswerSelected = false;
                                            }
                                        } else if((choice != null && !choice.isEmpty()) && choice.equals(TAG_OPTION3)) {
                                            if (Checkbox3View.isChecked()) {
                                                isAnswerSelected = true;
                                            }else {
                                                isAnswerSelected = false;
                                            }
                                        }else if((choice != null && !choice.isEmpty()) && choice.equals(TAG_OPTION4)) {
                                            if (Checkbox4View.isChecked()) {
                                                isAnswerSelected = true;
                                            }else {
                                                isAnswerSelected = false;
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if (isAnswerSelected) {
                                    totalScore += 10;
                                }
                            }

                            if (i < qLen) {
                                jsonObject = jsonArray.getJSONObject(i);

                                question = jsonObject.getString(TAG_QUESTION);
                                type = jsonObject.getString(TAG_QUESTION_TYPE);

                                //load next question
                                QuestionTextView.setText(question);

                                if (type.equals(TYPE_RADIO)) {
                                    answer = jsonObject.getString(TAG_ANSWER);
                                    JSONObject options = jsonObject.getJSONObject(TAG_OPTIONS);
                                    //dynamically generate radio button content
                                    Radio1View.setText(options.getString(TAG_OPTION1));
                                    Radio2View.setText(options.getString(TAG_OPTION2));
                                    Radio3View.setText(options.getString(TAG_OPTION3));
                                    Radio4View.setText(options.getString(TAG_OPTION4));

                                    editTextView.setVisibility(View.GONE);
                                    radioGroupView.setVisibility(View.VISIBLE);
                                    multiChoiceView.setVisibility(View.GONE);

                                } else if (type.equals(TYPE_TEXT)) {
                                    answer = jsonObject.getString(TAG_ANSWER);
                                    editTextView.setVisibility(View.VISIBLE);
                                    radioGroupView.setVisibility(View.GONE);
                                    multiChoiceView.setVisibility(View.GONE);
                                } else if (type.equals(TYPE_MULTI_CHOICE)) {
                                    answerArr = jsonObject.getJSONArray(TAG_ANSWER);
                                    answer = answerArr.toString();
                                    JSONObject options = jsonObject.getJSONObject(TAG_OPTIONS);

                                    //dynamically generate MultiChoice button content
                                    Checkbox1View.setText(options.getString(TAG_OPTION1));
                                    Checkbox2View.setText(options.getString(TAG_OPTION2));
                                    Checkbox3View.setText(options.getString(TAG_OPTION3));
                                    Checkbox4View.setText(options.getString(TAG_OPTION4));

                                    multiChoiceView.setVisibility(View.VISIBLE);
                                    editTextView.setVisibility(View.GONE);
                                    radioGroupView.setVisibility(View.GONE);
                                }

                                //rerender background and button color
                                int bgColor = Color.parseColor(getColor());
                                questionBg.setBackgroundColor(bgColor);
                                nextButton.setTextColor(bgColor);
                                submitButton.setTextColor(bgColor);

                                i++;
                                SequenceTextView.setText(i + "/10");
                                AnswerTextView.setText("");
                            } else {
                                i = 0;
                                Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
                                intent.putExtra(SCORE, totalScore);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //when user click submit button, calculate the score, show answer and current score
                View.OnClickListener submitButtonListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isAnswerRight = false;
                        //check if answer is correct
                        if (type.equals(TYPE_RADIO)) {
                            if ((selectedAnswer != null && !selectedAnswer.isEmpty()) && selectedAnswer.equals(answer)){
                                isAnswerRight = true;
                            }
                        } else if (type.equals(TYPE_TEXT)) {
                            answerText = editTextView.getText().toString();
                            if ((answerText != null && !answerText.isEmpty()) && answerText.equals(answer)){
                                isAnswerRight = true;
                            }
                        } else if (type.equals(TYPE_MULTI_CHOICE)) {
                            //check if multi choice answers are correct
                           for (int i = 0; i <answerArr.length(); i++ ) {
                               try {
                                   String choice = answerArr.getString(i);

                                   if ((choice != null && !choice.isEmpty()) &&  choice.equals(TAG_OPTION1) ) {
                                       if (Checkbox1View.isChecked()) {
                                           isAnswerRight = true;
                                       }else {
                                           isAnswerRight = false;
                                       }
                                   } else if((choice != null && !choice.isEmpty()) && choice.equals(TAG_OPTION2)) {
                                       if (Checkbox2View.isChecked()) {
                                           isAnswerRight = true;
                                       }else {
                                           isAnswerRight = false;
                                       }
                                   } else if((choice != null && !choice.isEmpty()) && choice.equals(TAG_OPTION3)) {
                                       if (Checkbox3View.isChecked()) {
                                           isAnswerRight = true;
                                       }else {
                                           isAnswerRight = false;
                                       }
                                   }else if((choice != null && !choice.isEmpty()) && choice.equals(TAG_OPTION4)) {
                                       if (Checkbox4View.isChecked()) {
                                           isAnswerRight = true;
                                       }else {
                                           isAnswerRight = false;
                                       }
                                   }
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }

                        }

                        if (isAnswerRight) {
                            AnswerTextView.setText(R.string.correct);
                            totalScore += 10;
                        } else {
                            String message = getString(R.string.notCorrect) + " " + answer;
                            AnswerTextView.setText(message);
                        }

                        String scoreMsg = getString(R.string.scoreToast) + " " + totalScore;
                        Toast toast = Toast.makeText(getApplicationContext(), scoreMsg, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                };


                nextButton.setOnClickListener(nextButtonListener);
                submitButton.setOnClickListener(submitButtonListener);

                radioGroupView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb = (RadioButton) group.findViewById(group.getCheckedRadioButtonId());
                        selectedAnswer = rb.getText().toString();
                        AnswerTextView.setText("");
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    * Load json file from Assets
    * Param: String fileName
    *
    * */
    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    /*
    * Randomly select a background color
    *
    * */
    public String getColor() {

        String color = "";

        Random randomGenerator  = new Random();
        int randomNumber = randomGenerator.nextInt(mBackgroundColor.length);

        color = mBackgroundColor[randomNumber];
        return color;

    }

}
