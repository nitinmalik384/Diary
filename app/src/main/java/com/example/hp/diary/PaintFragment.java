package com.example.hp.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import at.markushi.ui.CircleButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaintFragment extends Fragment {

    CircleButton plus;

    public PaintFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_paint, container, false);
        ListView listView=(ListView)v.findViewById(R.id.list1);

        String[] items={"Hello Paint!",};

        ListAdapter adapter=new CustomAdapterforPaint(getContext(),items);
        listView.setAdapter(adapter);
//        plus=(CircleButton)v.findViewById(R.id.add_drawing);
      /*  plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),DrawingNotes.class);
                startActivity(intent);

            }
        });
*/
    return v;}

}
