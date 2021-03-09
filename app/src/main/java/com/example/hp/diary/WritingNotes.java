package com.example.hp.diary;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
//import android.support.v7.app.ActionBarActivity; // it doesn't support
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.MonthDay;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import at.markushi.ui.CircleButton;

public class WritingNotes extends AppCompatActivity {

    EditText create_updateET;
    DBManager dbManager;
    Toolbar toolbar;
    NotesFragments homefragments;
    TextView length,date;
    int create_update;
    String notesdate;
    int result;
    String note_content;
    TextToSpeech textToSpeech;
    CircleButton speakstop;
    int size=16;
    SharedPreferences Size_preferences;
    int custom_Font_Size;
    Typeface casual,cursive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_notes);
        create_updateET=(EditText)findViewById(R.id.create_edit);
        date=(TextView)findViewById(R.id.date);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();


        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String data=intent.getStringExtra(Intent.EXTRA_TEXT);
               create_updateET.setText(data);
            }
        }



        date.setText(currentdate()+"");

        casual=Typeface.createFromAsset(getAssets(),"font1.ttf");
        cursive =Typeface.createFromAsset(getAssets(),"font2.ttf");







        //  Size_preferences =this.getSharedPreferences("com.example.hp.diary",this.MODE_PRIVATE);
        Size_preferences =this.getSharedPreferences("com.example.hp.diary",Context.MODE_PRIVATE);
        custom_Font_Size=Size_preferences.getInt("font_size",17);
        int font_id=Size_preferences.getInt("font",1);
        if (font_id==2){
            create_updateET.setTypeface(casual);
            date.setTypeface(casual);
        }
        else if (font_id==3)
        {
            create_updateET.setTypeface(cursive);
            date.setTypeface(cursive);
        }

        // casual=Typeface.createFromAsset(getAssets(),"font1.ttf");






        dbManager=new DBManager(this);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Writing");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // SharedPreferences sharedPreferences=getSharedPreferences()
        int font=Size_preferences.getInt("font",1);


        length=(TextView) findViewById(R.id.words);

        speakstop=(CircleButton)findViewById(R.id.speakstop);


        Bundle bundle=getIntent().getExtras();

        note_content=bundle.getString("notecontent");
        notesdate=bundle.getString("notesdate");
        create_update=bundle.getInt("create_update");

        if (create_update==1){

            create_updateET.setText(note_content);

            create_updateET.setTextSize(custom_Font_Size);

            length.setText(note_content.length()+"");
            create_updateET.setSelection(note_content.length());
        }

        else {
            speakstop.setVisibility(View.INVISIBLE);
            length.setVisibility(View.INVISIBLE);

        }

        create_updateET.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)

                { //speakstop.animate().alpha(1f).alphaBy(0f).setDuration(200);
                    speakstop.setVisibility(View.VISIBLE);
                    length.setVisibility(View.VISIBLE);

                    int a=s.length();
                    if (a>99){
                        length.setTextSize(8);
                    }
                    else {
                        length.setTextSize(10);
                    }
                    length.setText(""+a);

                }
                else {
                    speakstop.setVisibility(View.INVISIBLE);
                    length.setVisibility(View.INVISIBLE);


                }


            }


        });

        textToSpeech =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {


                if (status==textToSpeech.SUCCESS){
                    result= textToSpeech.setLanguage(Locale.ENGLISH);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Feature not supported",Toast.LENGTH_SHORT).show();
                }

            }
        });


        speakstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (result==textToSpeech.LANG_NOT_SUPPORTED||result==textToSpeech.LANG_MISSING_DATA){

                    Toast.makeText(getApplicationContext(),"Feature Not Supported",Toast.LENGTH_SHORT).show();

                }
                else {
                    String data=  create_updateET.getText().toString();
                    if (textToSpeech.isSpeaking()){
                        speakstop.setImageResource(R.drawable.mute);
                        textToSpeech.stop();
                    }

                    else {
                        speakstop.setImageResource(R.drawable.speaker);
                        textToSpeech.speak(data,textToSpeech.QUEUE_FLUSH,null);
                    }}

            }
        });








        // String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        //reate_updateET.setText(currentDateTimeString);

    }

    @Override
    public void onBackPressed() {
        textToSpeech.stop();
        // String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());


        finish();
        if (create_update==0){
            if (create_updateET.length()!=0){
                ContentValues values=new ContentValues();
                values.put(DBManager.COL2_CONTENT,create_updateET.getText().toString());
                values.put(DBManager.COL3_DATE_TIME, currentdate());
                long id=dbManager.Insert(values);
            }}

        else if (create_update==1){

            //old notes_content is not equal to new content the update data otherwise don't update data
            if (!create_updateET.getText().toString().equals(note_content)){

                //if new data's length is not equal to 0 then update the data
                if (create_updateET.getText().toString().length()!=0){

                    ContentValues values=new ContentValues();
                    values.put(DBManager.COL2_CONTENT,create_updateET.getText().toString());
                    values.put(DBManager.COL3_DATE_TIME,currentdate());
                    String[] SelectionArgs={notesdate};
                    int count=dbManager.Update(values,DBManager.COL3_DATE_TIME+" =?",SelectionArgs);

                    if (count>0){
                        //  Toast.makeText(getApplicationContext(),"UPDATED",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //   Toast.makeText(getApplicationContext(),"FAILED",Toast.LENGTH_SHORT).show();

                    }}
                //if new data's length is  equal to 0 then delete that data

                else{
                    String[] SelectionArgs={notesdate};
                    dbManager.Delete(DBManager.COL3_DATE_TIME+"=?",SelectionArgs);

                }}

            else {}}


    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){



        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){


            int a=++custom_Font_Size;

            if (custom_Font_Size<=36){



                create_updateET.setTextSize(custom_Font_Size);}
            else {

                custom_Font_Size=36;
            }
            Size_preferences.edit().putInt("font_size",a).apply();

            // Log.i("font_size", String.valueOf(font));

            //  Toast.makeText(getApplicationContext(),String.valueOf(font),Toast.LENGTH_SHORT).show();





            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            int a= --custom_Font_Size;
            if (custom_Font_Size>=10){
                create_updateET.setTextSize(custom_Font_Size);}
            else {
                custom_Font_Size=10;
            }
            Size_preferences.edit().putInt("font_size",a).apply();
            // Toast.makeText(this, "Volume Down", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    String currentdate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date cdate = new Date();
        // System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        //  String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());


        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int Am_Pm=calendar.get(Calendar.AM_PM);
        String days = null;
        switch (day){
            case 1: days="Sunday";
                break;

            case 2: days="Monday";
                break;
            case 3: days="Tuesday";
                break;
            case 4: days="Wednesday";
                break;
            case 5: days="Thursday";
                break;
            case 6: days="Friday";
                break;
            case 7: days="Saturday";
                break;
        }
        String am_pm;
        switch (Am_Pm){
            case 0: am_pm="AM";
                break;
            default:am_pm="PM";
        }


        String currentdate=days+" , "+dateFormat.format(cdate)+" "+am_pm;
        return  currentdate;

    }

}
