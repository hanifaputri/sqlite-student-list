package com.example.tugas7ver2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Student.db2";
    private static final String TABLE_NAME = "student_table";
    private static final int DATABASE_VERSION = 1;

    private static final String COL_ID = "ID";
    private static final String COL_NAMA = "NAMA";
    private static final String COL_NIM = "NIM";
    private static final String COL_KELAS = "KELAS";
    
    SQLiteDatabase db;
    
    DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE " + TABLE_NAME +
                "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAMA + " TEXT," +
                COL_NIM + " TEXT," +
                COL_KELAS + " INTEGER" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "");
        onCreate(db);
    }

    public boolean insertData(String nama, String nim, String kelas){
        if (!checkDataExist(nim)){
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_NAMA, nama);
            contentValues.put(COL_NIM, nim);
            contentValues.put(COL_KELAS, kelas);
            long result = db.insert(TABLE_NAME, null, contentValues);
            if (result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }
    }

    public Cursor getAllData() {
        Cursor res = null;
        if (db != null){
            res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        }
        return res;
    }

    public boolean updateData(String id, String nama, String nim, String kelas) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, id);
        contentValues.put(COL_NAMA, nama);
        contentValues.put(COL_NIM, nim);
        contentValues.put(COL_KELAS, kelas);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public int deleteData(String id) {
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

    public boolean checkDataExist(String in_nim) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_NIM + " = " + in_nim;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
