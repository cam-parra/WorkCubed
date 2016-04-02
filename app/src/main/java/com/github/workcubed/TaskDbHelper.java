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
public class TaskDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "WorkCubed.db";
    public static final String TASK_TABLE_NAME = "Tasks";
    public static final String TASK_COLUMN_ID = "id";
    public static final String TASK_COLUMN_TASKNAME = "name";
    public static final String TASK_COLUMN_PROJECTNAME = "projectname";
    public static final String TASK_COLUMN_DESCRIPTION = "description";
    public static final String TASK_COLUMN_HOURSEXPECTED = "hours_expected";
    public static final String TASK_COLUMN_HOURSACTUAL = "hours_actual";
    public static final String TASK_COLUMN_DATEDEADLINE = "datedeadline";
    public static final String TASK_COLUMN_COMPLETED = "completed";


    private HashMap hp;

    public TaskDbHelper(Context context){
        //super(context, DATABASE_NAME, null, 1);
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Tasks " + "(id INTEGER PRIMARY KEY, name TEXT, " +
                "description TEXT, hours_expected REAL, hours_actual REAL, projectname TEXT," +
                " datedeadline TEXT, completed INTEGER,);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Tasks");
        System.out.println("Made to the upgrade");
        onCreate(db);
    }

    public boolean insertTask (String name,
                               String description,
                               String hours_actual,
                               String hours_expected,
                               String projectname,
                               String datedeadline,
                               Integer completed){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("hours_expected", hours_expected);
        contentValues.put("hours_actual", hours_actual);
        contentValues.put("projectname", projectname);
        contentValues.put("datedeadline", datedeadline);
        contentValues.put("completed", completed);

        db.insert("Tasks", null, contentValues);

        return true;
    }

    public boolean updateTask (Integer id, String name, String description,
                               Float hours_actual, Float hours_expected,
                               Integer projectname, String datedeadline, Integer completed){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("hours_expected", hours_expected);
        contentValues.put("hours_actual", hours_actual);
        contentValues.put("projectname", projectname);
        contentValues.put("datedeadline", datedeadline);
        contentValues.put("completed", completed);

        db.update("Tasks", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Tasks where id="+id+"", null );
        return res;
    }


    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TASK_TABLE_NAME);
        return numRows;
    }



    public Integer deleteTasks (Integer id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Tasks",  "id = ? ",
                new String[] { Integer.toString(id) });

    }


    public ArrayList<String> getAllTasks()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * Tasks", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(TASK_COLUMN_PROJECTNAME)));
            res.moveToNext();
        }
        return array_list;
    }


}
