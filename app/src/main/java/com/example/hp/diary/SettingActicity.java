package com.example.hp.diary;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActicity extends AppCompatActivity {
    Typeface cursive,casual;
    TextView fontview;
     SharedPreferences sharedPreferences;
    int font_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_acticity);

       Toolbar toolbar = (Toolbar) findViewById(R.id.settingtoolbar);
        toolbar.setTitle("Setting");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fontview =(TextView)findViewById(R.id.textfont);
        casual=Typeface.createFromAsset(getAssets(),"font1.ttf");
        cursive=Typeface.createFromAsset(getAssets(),"font2.ttf");

      sharedPreferences  =this.getSharedPreferences("com.example.hp.diary", Context.MODE_PRIVATE);
        font_id= sharedPreferences.getInt("font",1);


        font(font_id);
        RadioButton normal=(RadioButton)findViewById(R.id.normalfont);
        RadioButton casual=(RadioButton)findViewById(R.id.casualfont);
        RadioButton cursive=(RadioButton)findViewById(R.id.cursivefont);
        if (font_id==1){
            normal.setChecked(true);
        }
        else  if (font_id==2){
            casual.setChecked(true);        }
        else if (font_id==3){
            cursive.setChecked(true);
        }




        final RadioGroup fontgroup=(RadioGroup)findViewById(R.id.fontgroup);
        fontgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {

                switch (id){
                    case R.id.normalfont :
                        sharedPreferences.edit().putInt("font",1).apply();
                        break;

                    case R.id.casualfont : sharedPreferences.edit().putInt("font",2).apply();
                        break;
                    case R.id.cursivefont : sharedPreferences.edit().putInt("font",3).apply();
                        break;
                  }
                  font(font_id);





            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public  void font(int font){
        if (font==1){
            fontview.setTypeface(Typeface.DEFAULT);

        }
        else if (font==2){
            fontview.setTypeface(casual);


        }
        else if (font==3){
            fontview.setTypeface(cursive);

        }
    }
}
