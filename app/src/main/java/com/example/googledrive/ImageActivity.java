package com.example.googledrive;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.googledrive.database.CameraContract;
import com.example.googledrive.database.CameraDBUtils;
import com.example.googledrive.CameraCursorAdapter;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends Activity {

    private static final String TAG = "GoogleDrive";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);
        if(!hasPermissions()){ // do we not have all the required permissions?
            ActivityCompat.requestPermissions(this, Global.PERMISSIONS, Global.SDCARD_PERMISSION_RESULT);
        }else{ // we have all required permissions, query the database, and then show the data
            showData();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == Global.SDCARD_PERMISSION_RESULT){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){ // user has granted permissions
                // Lots of debugging was involved until this solution was found. I was getting errors every time this app initially ran
                // and tried to use the camera or view the list of files that were uploaded to Google Drive. But, after closing the app
                // and opening it, no errors involving permissions occured. Apparently, with emulated devices, this is a known bug and
                // the 'killProcess' code below is a quick work-around.
                // https://stackoverflow.com/questions/32699129/android-6-0-needs-restart-after-granting-user-permission-at-runtime
                android.os.Process.killProcess(android.os.Process.myPid());
                showData();
            }
            else{ // user has not granted permissions
                ActivityCompat.requestPermissions(this, Global.PERMISSIONS, Global.SDCARD_PERMISSION_RESULT);
            }
        }
        else{
            ActivityCompat.requestPermissions(this, Global.PERMISSIONS, Global.SDCARD_PERMISSION_RESULT);
        }
    }
    // Returns true if the user has granted all required permissions, false otherwise
    public boolean hasPermissions(){
        if (Build.VERSION.SDK_INT >= 23) {
            for (String permission : Global.PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false; // permission has not been granted
                }
            }
            return true; // all permissions have been granted
        }
        return false;
    }
    private void showData(){
        if(CameraDBUtils.validDB()) { // do we have a valid database?
            CameraCursorAdapter cursorAdapter = new CameraCursorAdapter(this,  CameraDBUtils.getDatabaseData());
            ListView SQLListView = (ListView) findViewById(R.id.google_list_view);
            SQLListView.setAdapter(cursorAdapter);

        }else{ // set up the database and call this function again
            CameraDBUtils.setupSQLite(this);
            showData();
        }
    }
}
