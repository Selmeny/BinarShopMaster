package com.dicoding.paul.binarshop.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.dicoding.paul.binarshop.Database.DatabaseContract.*;

//this is the only database i am capable to work with for now
//i will continue to learn about others
//use this class to create database using DDL

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "binarshop";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_SHOP = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s INTEGER,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL)",
            TABLE_SHOP,
            ShopColumns._ID,
            ShopColumns.TANGGAL_MASUK,
            ShopColumns.PETUGAS_PENCATAT,
            ShopColumns.NAMA_BARANG,
            ShopColumns.JUMLAH_BARANG,
            ShopColumns.NAMA_PEMASOK,
            ShopColumns.KETERANGAN_LAIN);

    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_SHOP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP );
        onCreate(db);
    }
}
