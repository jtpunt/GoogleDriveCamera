package com.example.googledrive;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.googledrive.database.CameraDBUtils;

import java.io.File;

public class CameraCursorAdapter extends CursorAdapter {
    TextView fileName;
    ImageView myImg;
    Button delete_btn;
    int id;

    public CameraCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        fileName = (TextView) view.findViewById(R.id.google_listview_string);
        myImg = (ImageView) view.findViewById(R.id.google_imageview);
        delete_btn = (Button) view.findViewById(R.id.delete_btn);
        final int position = cursor.getPosition();
        String file_path = cursor.getString(cursor.getColumnIndexOrThrow("FileName"));
        fileName.setText("File Path: " + file_path);
        myImg.setImageURI(Uri.parse(file_path));
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor.moveToPosition(position);
                id = cursor.getInt(cursor.getColumnIndex("_id"));
                String strFile = cursor.getString(cursor.getColumnIndexOrThrow("FileName"));
                AlertDialog myDialog = buildDialog(context, strFile, id, cursor);
                myDialog.show();
            }
        });
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.google_item, parent, false);
    }

    public AlertDialog buildDialog(Context context, final String filePath, final int myId, final Cursor myCursor){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Are you sure you want to delete this file?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        File myFile = new File(filePath);
                        boolean deleted = myFile.delete(); // delete the file from the sd card
                        CameraDBUtils.deleteData(myId); // delete the entry from the db
                        notifyDataSetChanged();
                        myCursor.requery();
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
