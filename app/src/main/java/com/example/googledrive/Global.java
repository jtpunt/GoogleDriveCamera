package com.example.googledrive;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class Global {
    public static GoogleSignInClient mGoogleSignInClient;
    public static String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.GET_ACCOUNTS,
    };
    public static final int SDCARD_PERMISSION_RESULT = 1;
}
