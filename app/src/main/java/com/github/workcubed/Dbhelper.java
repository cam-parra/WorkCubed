package com.github.workcubed;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.List;

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
    public static final String CONTACTS_TABLE_NAME = "Projects";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_PROJECTNAME = "names";
    public static final String CONTACTS_COLUMN_DESCRIPTION = "description";
    public static final String CONTACTS_COLUMN_DATECREATED = "datecreated";
    public static final String CONTACTS_COLUMN_DATEDEADLINE = "datedeadline";
    public static final String CONTACTS_COLUMN_COMPLETED = "completed";


    private HashMap hp;

    public Dbhelper(Context context){
        //super(context, DATABASE_NAME, null, 1);
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Projects " + "(id INTEGER PRIMARY KEY, names TEXT, description TEXT, datecreated TEXT, datedeadline TEXT, completed INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Projects");
        onCreate(db);
    }

    public boolean insertProject (String name, String description,
                                  String datecreated, String datecompleted, Integer completed){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("names", name);
        contentValues.put("description", description);
        contentValues.put("datecreated", datecreated);
        contentValues.put("datedeadline", datecompleted);
        contentValues.put("completed", completed);

        long result = db.insert("Projects", null, contentValues);
        System.out.println(result);

        return true;
    }

    public boolean updateProject (Integer id, String name, String description,
                                  String datecreated, String datecompleted, Integer completed){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("completed", completed);
        contentValues.put("datedeadline", datecompleted);
        contentValues.put("datecreated", datecreated);
        contentValues.put("description", description);
        contentValues.put("names", name);

        db.update("Projects", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Projects where id="+id+"", null );
        return res;
    }

    public String getProjectDescByName (String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String desc = "";
        Cursor cursor = db.rawQuery("select description from Projects where names=?", new String[]{name + ""});
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();
            desc = cursor.getString(cursor.getColumnIndex("description"));
        }

        return desc;
    }

    public String getProjectDeadlineByName (String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String deadline = "";
        Cursor cursor = db.rawQuery("select datedeadline from Projects where names=?", new String[]{name + ""});
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();
            deadline = cursor.getString(cursor.getColumnIndex("datedeadline"));
        }
        return deadline;
    }

    public String getProjectIDByName (String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String id = "";
        Cursor cursor = db.rawQuery("select id from Projects where names=?", new String[]{name + ""});
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();
            id = cursor.getString(cursor.getColumnIndex("id"));
        }
        return id;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }



    public Integer deleteProjects (Integer id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Projects",  "id = ? ",
                new String[] { Integer.toString(id) });

    }


    public ArrayList<String> getAllProjects()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Projects", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            System.out.println("Made it here!");
            String row_name = "1" + res.getString(res.getColumnIndex(CONTACTS_COLUMN_PROJECTNAME));
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PROJECTNAME)));
            System.out.println(row_name);
            res.moveToNext();
        }

        return array_list;
    }


}
