package com.example.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TaskOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Task";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "taskmanager";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CONTENT = "content";

    public TaskOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql_query = String.format("CREATE TABLE %s(%s TEXT PRIMARY KEY, %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, KEY_NAME, KEY_CONTENT);
        sqLiteDatabase.execSQL(sql_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        sqLiteDatabase.execSQL(drop_students_table);

        onCreate(sqLiteDatabase);
    }

    public void onInsert(TaskModel task) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            ContentValues values = new ContentValues();
            values.put(KEY_ID, task.getId());
            values.put(KEY_NAME, task.getName());
            values.put(KEY_CONTENT, task.getContent());

            db.insert(TABLE_NAME, null, values);
        }catch(SQLiteException e){
            throw e;
        }finally {
            db.close();
        }
    }

    public void onDelete(TaskModel task){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(task.getId()) });
        db.close();
    }

    public int onUpdate(TaskModel task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_CONTENT, task.getContent());

        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    public List<TaskModel> onGetAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<TaskModel> listTask = new ArrayList<TaskModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TaskModel task = new TaskModel();
                task.setId(cursor.getString(0));
                task.setName(cursor.getString(1));
                task.setContent(cursor.getString(2));

                listTask.add(task);
            } while (cursor.moveToNext());
        }

        return listTask;
    }
}
