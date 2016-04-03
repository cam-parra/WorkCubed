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
    public static final String PROJECT_TABLE_NAME = "Projects";
    public static final String PROJECT_COLUMN_ID = "id";
    public static final String PROJECT_COLUMN_PROJECTNAME = "names";
    public static final String PROJECT_COLUMN_DESCRIPTION = "description";
    public static final String PROJECT_COLUMN_DATECREATED = "datecreated";
    public static final String PROJECT_COLUMN_DATEDEADLINE = "datedeadline";
    public static final String PROJECT_COLUMN_COMPLETED = "completed";

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

    public Dbhelper(Context context){
        //super(context, DATABASE_NAME, null, 1);
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Creating tables");
        db.execSQL("CREATE TABLE Projects (id INTEGER PRIMARY KEY, names TEXT, description TEXT, datecreated TEXT, datedeadline TEXT, completed INTEGER, unique (names));");
        db.execSQL("CREATE TABLE Tasks (id INTEGER PRIMARY KEY, name TEXT, description TEXT, hours_expected INTEGER, hours_actual INTEGER, projectname TEXT, datedeadline TEXT, completed INTEGER, unique(name));");
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

    public boolean updateTask (Integer id, String name, String description,
                               Float hours_actual, Float hours_expected, Integer completed, String projectname){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("hours_expected", hours_expected);
        contentValues.put("hours_actual", hours_actual);
        contentValues.put("projectname", projectname);
//        contentValues.put("datedeadline", datedeadline);
        contentValues.put("completed", completed);

        db.update("Tasks", contentValues, "id = ? ", new String[]{Integer.toString(id)});
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
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJECT_TABLE_NAME);
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
            array_list.add(res.getString(res.getColumnIndex(PROJECT_COLUMN_PROJECTNAME)));
            res.moveToNext();
        }

        return array_list;
    }

    public int numberOfRowsInTask(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TASK_TABLE_NAME);
        return numRows;
    }



    public Integer deleteTasks (Integer id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Tasks",  "id = ? ",
                new String[] { Integer.toString(id) });

    }


    public ArrayList<String> getAllTasks(String name)
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Tasks where projectname=?", new String[]{name + ""});
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(TASK_COLUMN_TASKNAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public String getTaskDescByName (String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String desc = "";
        Cursor cursor = db.rawQuery("select description from Tasks where name=?", new String[]{name + ""});
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();
            desc = cursor.getString(cursor.getColumnIndex("description"));
        }

        return desc;
    }

    public Integer getTaskStatusByName (String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Integer status = 0;
        Cursor cursor = db.rawQuery("select completed from Tasks where name=?", new String[]{name + ""});
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();
            status = cursor.getInt(cursor.getColumnIndex("completed"));
        }

        return status;
    }

    public Integer getTaskHoursEstimatedByName (String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Integer hours = 0;
        Cursor cursor = db.rawQuery("select hours_expected from Tasks where name=?", new String[]{name + ""});
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();
            hours = cursor.getInt(cursor.getColumnIndex("hours_expected"));
        }
        return hours;
    }

    public Integer getTaskHoursActualByName (String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Integer hours = 0;
        Cursor cursor = db.rawQuery("select hours_actual from Tasks where name=?", new String[]{name + ""});
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();
            hours = cursor.getInt(cursor.getColumnIndex("hours_actual"));
        }
        return hours;
    }

    public String getTaskColumnProjectname (String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String project = "";
        Cursor cursor = db.rawQuery("select projectname from Tasks where name=?", new String[]{name + ""});
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();
            project = cursor.getString(cursor.getColumnIndex("projectname"));
        }

        return project;
    }

    public Integer getTaskIDByName (String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Integer id = null;

        Cursor cursor = db.rawQuery("select id from Tasks where name=?", new String[]{name + ""});
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }

        return id;
    }
}
