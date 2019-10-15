package com.example.acer.smartpecifier;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ParamsActivity extends AppCompatActivity {


    EditText numEdit;
    MaterialButton changer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_params);




        numEdit = findViewById(R.id.numEdit);
        changer = findViewById(R.id.changer);

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);

        String phone = prefs.getString("tel", "");//"No name defined" is the default value.
        numEdit.setText(phone);




        changer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numEdit.getText().toString().length()!=8 || numEdit.getText().toString().isEmpty()){
                    numEdit.setError("Veuillez entrer un numéro valide ! ");
                }else{
                    SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit();
                    editor.putString("tel", numEdit.getText().toString());
                    editor.apply();

                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                }
            }
        });












    }



}
