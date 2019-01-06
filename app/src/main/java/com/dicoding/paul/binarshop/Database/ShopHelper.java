package com.dicoding.paul.binarshop.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dicoding.paul.binarshop.Model.ShopModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.paul.binarshop.Database.DatabaseContract.ShopColumns.JUMLAH_BARANG;
import static com.dicoding.paul.binarshop.Database.DatabaseContract.ShopColumns.KETERANGAN_LAIN;
import static com.dicoding.paul.binarshop.Database.DatabaseContract.ShopColumns.NAMA_BARANG;
import static com.dicoding.paul.binarshop.Database.DatabaseContract.ShopColumns.NAMA_PEMASOK;
import static com.dicoding.paul.binarshop.Database.DatabaseContract.ShopColumns.PETUGAS_PENCATAT;
import static com.dicoding.paul.binarshop.Database.DatabaseContract.ShopColumns.TANGGAL_MASUK;
import static com.dicoding.paul.binarshop.Database.DatabaseContract.TABLE_SHOP;

//use this class to manipulate databse using DML
public class ShopHelper {
    private static String DATABASE_TABLE = TABLE_SHOP;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public ShopHelper(Context context) {
        this.context = context;
    }

    public ShopHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<ShopModel> query() {
        ArrayList<ShopModel> arrayList = new ArrayList<ShopModel>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null,
                null, null, _ID + " DESC", null );
        cursor.moveToFirst();
        ShopModel model;
        if (cursor.getCount() > 0) {

            do {
                model = new ShopModel();
                model.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                model.setTanggal_masuk(cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL_MASUK)));
                model.setPetugas_pencatat(cursor.getString(cursor.getColumnIndexOrThrow(PETUGAS_PENCATAT)));
                model.setNama_barang(cursor.getString(cursor.getColumnIndexOrThrow(NAMA_BARANG)));
                model.setJumlah_barang(cursor.getInt(cursor.getColumnIndexOrThrow(JUMLAH_BARANG)));
                model.setNama_pemasok(cursor.getString(cursor.getColumnIndexOrThrow(NAMA_PEMASOK)));
                model.setKeterangan_lain(cursor.getString(cursor.getColumnIndexOrThrow(KETERANGAN_LAIN)));

                arrayList.add(model);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(ShopModel model) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TANGGAL_MASUK, model.getTanggal_masuk());
        initialValues.put(PETUGAS_PENCATAT, model.getPetugas_pencatat());
        initialValues.put(NAMA_BARANG, model.getNama_barang());
        initialValues.put(JUMLAH_BARANG, model.getJumlah_barang());
        initialValues.put(NAMA_PEMASOK, model.getNama_pemasok());
        initialValues.put(KETERANGAN_LAIN, model.getKeterangan_lain());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(ShopModel model) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(TANGGAL_MASUK, model.getTanggal_masuk());
        updateValues.put(PETUGAS_PENCATAT, model.getPetugas_pencatat());
        updateValues.put(NAMA_BARANG, model.getNama_barang());
        updateValues.put(JUMLAH_BARANG, model.getJumlah_barang());
        updateValues.put(NAMA_PEMASOK, model.getNama_pemasok());
        updateValues.put(KETERANGAN_LAIN, model.getKeterangan_lain());
        return database.update(DATABASE_TABLE, updateValues, _ID + "= '" + model.getId()
                + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_SHOP, _ID +"= '" + id + "'", null);
    }
}
