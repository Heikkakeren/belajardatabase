package com.example.belajardatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_laptop";
    private static final int DB_VERSION = 2;

    private static final String TABLE_LAPTOP = "laptop";
    private static final String COL_ID = "id";
    private static final String COL_MERK = "merk";
    private static final String COL_MODEL = "model";
    private static final String COL_PROCESSOR = "processor";
    private static final String COL_RAM = "ram";
    private static final String COL_STORAGE = "storage";
    private static final String COL_HARGA = "harga";
    private static final String COL_GAMBAR1 = "gambar1";
    private static final String COL_GAMBAR2 = "gambar2";
    private static final String COL_GAMBAR3 = "gambar3";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_LAPTOP + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MERK + " TEXT, " +
                COL_MODEL + " TEXT, " +
                COL_PROCESSOR + " TEXT, " +
                COL_RAM + " TEXT, " +
                COL_STORAGE + " TEXT, " +
                COL_HARGA + " REAL, " +
                COL_GAMBAR1 + " BLOB, " +
                COL_GAMBAR2 + " BLOB, " +
                COL_GAMBAR3 + " BLOB)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAPTOP);
        onCreate(db);
    }

    public long insertLaptop(Laptop laptop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_MERK, laptop.getMerk());
        cv.put(COL_MODEL, laptop.getModel());
        cv.put(COL_PROCESSOR, laptop.getProcessor());
        cv.put(COL_RAM, laptop.getRam());
        cv.put(COL_STORAGE, laptop.getStorage());
        cv.put(COL_HARGA, laptop.getHarga());
        cv.put(COL_GAMBAR1, laptop.getGambar1());
        cv.put(COL_GAMBAR2, laptop.getGambar2());
        cv.put(COL_GAMBAR3, laptop.getGambar3());
        long id = db.insert(TABLE_LAPTOP, null, cv);
        db.close();
        return id;
    }

    public List<Laptop> getAllLaptops() {
        List<Laptop> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LAPTOP, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Laptop l = new Laptop(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getDouble(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getBlob(9)
                );
                list.add(l);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public int updateLaptop(Laptop laptop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_MERK, laptop.getMerk());
        cv.put(COL_MODEL, laptop.getModel());
        cv.put(COL_PROCESSOR, laptop.getProcessor());
        cv.put(COL_RAM, laptop.getRam());
        cv.put(COL_STORAGE, laptop.getStorage());
        cv.put(COL_HARGA, laptop.getHarga());
        cv.put(COL_GAMBAR1, laptop.getGambar1());
        cv.put(COL_GAMBAR2, laptop.getGambar2());
        cv.put(COL_GAMBAR3, laptop.getGambar3());
        int rows = db.update(TABLE_LAPTOP, cv, COL_ID + "=?", new String[]{String.valueOf(laptop.getId())});
        db.close();
        return rows;
    }

    public void deleteLaptop(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LAPTOP, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}