package com.example.hp.diary;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

/**
 * Created by HP on 8/10/2017.
 */

public class CustomAdapter extends ArrayAdapter {
    public CustomAdapter(@NonNull Context context, ArrayList resource) {
        super(context,R.layout.row, resource);
    }

    SharedPreferences sharedPreferences;
    Typeface simple,casual,cursive;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View view=inflater.inflate(R.layout.row,parent,false);

        String singleitem= (String) getItem(position);
        String[] parts=singleitem.split("@");

        String notesContent=parts[0];
        final String notesDate=parts[1];

        ImageView imageView=(ImageView) view.findViewById(R.id.img);
        TextView notes_content=(TextView) view.findViewById(R.id.note_content);
        TextView date=(TextView) view.findViewById(R.id.date);
      //  CircleButton delete_btn=(CircleButton)view.findViewById(R.id.delete) ;
        casual=Typeface.createFromAsset(getContext().getAssets(),"font1.ttf");
        cursive=Typeface.createFromAsset(getContext().getAssets(),"font2.ttf");
       // notes_content.setTypeface(casual);


        sharedPreferences=getContext().getSharedPreferences("com.example.hp.diary", Context.MODE_PRIVATE);
        int font_id=sharedPreferences.getInt("font",0);
        if(font_id==2){
            notes_content.setTypeface(casual);
            notes_content.setTextSize(24);
           // Toast.makeText(getContext(),font_id+"",Toast.LENGTH_SHORT).show();
        }
        else if (font_id==3){
            notes_content.setTypeface(cursive);
        }

        if (notesContent.contains("\n")){
          String [] multiplelinesNotesContent=notesContent.split("\n");
            String beforeNextLine=multiplelinesNotesContent[0];
            notes_content.setText(beforeNextLine);
        }
        else {
        notes_content.setText(notesContent);}
        notes_content.setSelected(true);
        Typeface datefont=Typeface.createFromAsset(getContext().getAssets(),"date.ttf");
        //date.setTypeface(datefont);
        date.setText(notesDate);
        date.setSelected(true);

        imageView.setImageResource(R.drawable.docblue);
        final DBManager dbManager=new DBManager(getContext());

      /*  delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] SelectionArgs={notesDate};
                int count=dbManager.Delete(DBManager.COL3_DATE_TIME+"=?",SelectionArgs);
                if (count>0){
                    Toast.makeText(getContext(),"Deleted",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getContext(),"not deleted",Toast.LENGTH_SHORT).show();

                }
            }
        });
*/


                return view;
    }
}
