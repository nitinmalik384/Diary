package com.example.hp.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.Selection;
import android.widget.Toast;

/**
 * Created by HP on 8/8/2017.
 */

public class DBManager  {

    private SQLiteDatabase sql;

    static final String DbName="NOTES";
    static final String TableName="WRITING";

    static final String COL2_CONTENT="NOTES_CONTENT";
    static final String COL1_ID="ID";

    static final String COL3_DATE_TIME="DATE_TIME";
    static final String CREATE_TABLE="create table "+TableName+"("+COL1_ID+" integer PRIMARY KEY AUTOINCREMENT ,"+
            COL2_CONTENT+" text ,"+COL3_DATE_TIME+" date)";
    static  final int VERSION=1;


    static  class DBHelper extends SQLiteOpenHelper{
        Context context;
        DBHelper(Context context){
            super(context,DbName,null,VERSION);
            this.context=context;

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL(CREATE_TABLE);
          //  Toast.makeText(context,"table create ",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TableName);
            onCreate(sqLiteDatabase);

        }
    }

    public  DBManager(Context context){
        DBHelper dbHelper=new DBHelper(context);
       sql= dbHelper.getWritableDatabase();
    }

    public long Insert(ContentValues values){
        long id=sql.insert(TableName,"",values);
        return id;
    }

    public Cursor getdata(String [] Projections,String Selection,String[] SelectionArgs,String SortOrder){
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        qb.setTables(TableName);
        Cursor cursor=qb.query(sql,Projections,Selection,SelectionArgs,null,null,SortOrder);
        return cursor;
    }

    public  int  Delete(String Selection,String[] SelectionArgs){
        int count=sql.delete(TableName,Selection,SelectionArgs);
        return  count;
    }
    public int Update(ContentValues values,String Selection,String[] SelectionArgs){

        int count =sql.update(TableName,values,Selection,SelectionArgs);
        return  count;
    }

}

