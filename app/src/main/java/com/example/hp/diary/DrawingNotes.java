package com.example.hp.diary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DrawingNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_notes);
        TextView textView=(TextView)findViewById(R.id.marquee);
        textView.setSelected(true);
    }
}
