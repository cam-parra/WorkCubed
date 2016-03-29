package com.github.workcubed;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.InputMismatchException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by cam on 3/20/16.
 */
public class Dbhelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "WorkCubed.db";
    public static final String CONTACTS_TABLE_NAME = "Project";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_PROJECTNAME = "name";
    public static final String CONTACTS_COLUMN_DESCRIPTION = "description";
    public static final String CONTACTS_COLUMN_DATECREATED = "datecreated";
    public static final String CONTACTS_COLUMN_DATEDEADLINE = "datedeadline";
    public static final String CONTACTS_COLUMN_COMPLETED = "completed";


    private HashMap hp;

    public Dbhelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Projects " + "id integer primary key," +
                "description text, datecreated string, datedeadline string, completed int");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Projects");
        onCreate(db);
    }

    public boolean insertProject (Integer id, String name, String description,
                                  String datecreated, String datecompleted, int completed){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("datecreated", datecreated);
        contentValues.put("datedeadline", datecompleted);
        contentValues.put("completed", completed);

        db.update("contacts", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteProject (Integer id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Project",  "id = ? ",
                new String[] { Integer.toString(id) });

    }


    public ArrayList<String> getAllProjects()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * Project", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PROJECTNAME)));
            res.moveToNext();
        }
        return array_list;
    }


}
