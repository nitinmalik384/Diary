package com.example.hp.diary;


import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragments extends Fragment {
    ListView listView;
    DBManager dbManager;


    public NotesFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         dbManager=new DBManager(getContext());
        // Inflate the layout for this fragment

                View v =inflater.inflate(R.layout.fragment_notes, container, false);
         listView=(ListView)v.findViewById(R.id.list);
        listView.setItemChecked(0, true);
        refreshList();

      //  CircleButton circleButton=(CircleButton)v.findViewById(R.id.addwriting);



       /* circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),WritingNotes.class);
                intent.putExtra("create_update",0);//0 for create & 1 for update

                startActivity(intent);
            }
        });
circleButton.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {
        get();
        return true;
    }
});*/

/*
     listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

             final Dialog dialog=new Dialog(getContext());
             dialog.setContentView(R.layout.custom_dialog);
             Button yesbtn=(Button)dialog.findViewById(R.id.confirm);
             Button nobtn=(Button)dialog.findViewById(R.id.cancel);
             ImageView alert_Img=(ImageView)dialog.findViewById(R.id.alert);
             alert_Img.animate().rotation(360).setDuration(1000).start();

             yesbtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     String data= (String) listView.getItemAtPosition(position);
                     String[] parts=data.split("@");


                     String notesContent=parts[0];
                     String notesDate=parts[1];




                     String[] SelectionArgs={notesDate};
                     int count=dbManager.Delete(DBManager.COL3_DATE_TIME+"=?",SelectionArgs);
                     if (count>0){
                      //   Toast.makeText(getContext(),"Deleted",Toast.LENGTH_SHORT).show();
                         refreshList();

                     }
                     else {
                         Toast.makeText(getContext(),"not deleted",Toast.LENGTH_SHORT).show();

                     }
                     dialog.dismiss();


                 }
             });
             nobtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     dialog.dismiss();
                 }
             });
             dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id

             dialog.show();




             return true;
         }
     });*/

listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        String data= (String) listView.getItemAtPosition(i);
        String[] parts=data.split("@");


        String notesContent=parts[0];
        String notesDate=parts[1];

        View view1=getLayoutInflater().inflate(R.layout.bottomsheet,null);

        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(view1);
        TextView textView=(TextView) view1.findViewById(R.id.bottomnote);
       textView.setText(notesContent  );
        bottomSheetDialog.show();





        return true;
    }
});
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                String data= (String) listView.getItemAtPosition(position);
                String[] parts=data.split("@");
                String  notesContent=parts[0];

                String notesDate=parts[1];

              /*  String[] projection={DBManager.COL1_ID,DBManager.COL2_CONTENT,DBManager.COL3_DATE_TIME};
                String selection=DBManager.COL3_DATE_TIME+"=?";
                String[] selectionargs={notesDate};

                Cursor cursor=dbManager.getdata(projection,selection,selectionargs,DBManager.COL2_CONTENT);
                String content=cursor.getString(cursor.getColumnIndex(DBManager.COL2_CONTENT));
*/
                Intent intent=new Intent(getContext(),WritingNotes.class);
                intent.putExtra("notecontent",notesContent);
                intent.putExtra("create_update",1);//0 for create & 1 for update
                intent.putExtra("notesdate",notesDate);


                startActivity(intent);

            }
        });



        return v;








    }
    public  void  refreshList(){



        ArrayList<String> arrayList=new ArrayList<String>();

        Cursor cursor=dbManager.getdata(null,null,null,DBManager.COL3_DATE_TIME +" DESC");

        if (cursor.moveToFirst()) {
            String tabledata;
            do {
                // tabledata = tabledata + cursor.getString(cursor.getColumnIndex(DBManager.ColUserName)) + "," +
                //    cursor.getString(   cursor.getColumnIndex(DBManager.ColUserPassword)) + " \n";

                tabledata=cursor.getString(cursor.getColumnIndex(DBManager.COL2_CONTENT))+"@"+
                        cursor.getString(cursor.getColumnIndex(DBManager.COL3_DATE_TIME));
                arrayList.add(tabledata);


            }
            while (cursor.moveToNext());

            //  Toast.makeText(MainActivity.this, tabledata, Toast.LENGTH_SHORT).show();
        }
        else{
          //  Toast.makeText(getContext(),"Data not found",Toast.LENGTH_SHORT).show();

        }
        ListAdapter listAdapter=new CustomAdapter(getContext(),arrayList);


        listView.setAdapter(listAdapter);



    }


    @Override
    public void onResume() {

        super.onResume();
        refreshList();
    }
}


