package com.example.googledrive;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.googledrive.database.CameraDBUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "GoogleDrive";
    Button sign_out_btn = null;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign_out_btn = (Button) findViewById(R.id.sign_in_btn);
        sign_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = buildDialog();
                dialog.show();
                Log.d(TAG, "You have successfully been logged out of google");
            }
        });
        final Button google_drive_btn = (Button) findViewById(R.id.google_drive_btn);
        google_drive_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        Button google_browse_btn = (Button) findViewById(R.id.google_browse_btn);
        google_browse_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) { // google sign in was successful
                sign_out_btn.setVisibility(View.VISIBLE);
            }
        }
    }
    public AlertDialog buildDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to signout of google?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CameraActivity.signOut();
                        sign_out_btn.setVisibility(View.GONE);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        return alert11;
    }
}
