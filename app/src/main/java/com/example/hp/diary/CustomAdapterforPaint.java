package com.example.hp.diary;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 8/10/2017.
 */

public class CustomAdapterforPaint extends ArrayAdapter {
    public CustomAdapterforPaint(@NonNull Context context, String[] resource) {
        super(context,R.layout.rowforpaint, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View view=inflater.inflate(R.layout.rowforpaint,parent,false);

        String singleitem= (String) getItem(position);


        ImageView imageView=(ImageView) view.findViewById(R.id.img_piant);
        TextView paintTitle=(TextView) view.findViewById(R.id.paint_Title);
       TextView date=(TextView) view.findViewById(R.id.paint_date);

        paintTitle.setText(singleitem);
        imageView.setImageResource(R.drawable.painticonforlistblue);



        return view;
    }
}


