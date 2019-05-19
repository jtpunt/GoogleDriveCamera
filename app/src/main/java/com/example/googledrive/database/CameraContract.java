package com.example.googledrive.database;

import android.provider.BaseColumns;

public final class CameraContract {
    private CameraContract(){}; // To prevent someone from accidentally instantiating the contract class, make the constructor private

    // Inner class that defines the table contents
    public static class CameraEntry implements BaseColumns {
        public static final String TABLE_NAME = "camera";
        public static final String COLUMN_FILENAME = "FileName";
    }
}
