package com.dicoding.paul.binarshop.Database;

import android.provider.BaseColumns;

//use this class to create those "columns" needed by database
public class DatabaseContract {
    static String TABLE_SHOP = "shop";

    static final class ShopColumns implements BaseColumns {
        static String TANGGAL_MASUK = "tanggal_masuk";
        static String PETUGAS_PENCATAT = "petugas_pencatat";
        static String NAMA_BARANG = "nama_barang";
        static String JUMLAH_BARANG = "jumlah_barang";
        static String NAMA_PEMASOK = "nama_pemasok";
        static String KETERANGAN_LAIN = "keterangan_lain";
    }
}
