package com.example.googledrive.database;
import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.googledrive.CameraActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class CameraDBUtils {
    private static SQLiteDatabase cameraDB;
    private static CameraDBHelper cameraDbHelper;
    private static void deleteData(){

    }
    public static void setupSQLite(Context myContext){
        cameraDbHelper = new CameraDBHelper(myContext); // instantiate the subclass of SQLiteOpenHelper to get access to the db
        cameraDB = cameraDbHelper.getWritableDatabase(); // gets the data repository in write mode
        Log.d("SQL", "Setting up SQL Database");
    }
    public static void insertData(String fileName){
        ContentValues values = new ContentValues();
        values.put(CameraContract.CameraEntry.COLUMN_FILENAME, fileName);
        cameraDB.insert(CameraContract.CameraEntry.TABLE_NAME, null, values);
    }
    public static  void deleteData(int id){
        cameraDB.delete(CameraContract.CameraEntry.TABLE_NAME, CameraContract.CameraEntry._ID+"=?", new String[]{String.valueOf(id)});
    }
    public static Cursor getDatabaseData(){
        // Specify the array of columns to return
        String[] projection = {
                CameraContract.CameraEntry._ID,
                CameraContract.CameraEntry.COLUMN_FILENAME
        };
        // Read from the database and return the Cursor object
        return cameraDB.query(
                CameraContract.CameraEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }
    public static boolean validDB(){
        return cameraDB != null;
    }
}
