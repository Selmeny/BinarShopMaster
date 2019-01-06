package com.dicoding.paul.binarshop.Model;

import android.os.Parcel;
import android.os.Parcelable;

//POJO used as model for data storing
public class ShopModel implements Parcelable {
    private int id;
    private String tanggal_masuk;
    private String petugas_pencatat;
    private String nama_barang;
    private int jumlah_barang;
    private String nama_pemasok;
    private String keterangan_lain;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal_masuk() {
        return tanggal_masuk;
    }

    public void setTanggal_masuk(String tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
    }

    public String getPetugas_pencatat() {
        return petugas_pencatat;
    }

    public void setPetugas_pencatat(String petugas_pencatat) {
        this.petugas_pencatat = petugas_pencatat;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public int getJumlah_barang() {
        return jumlah_barang;
    }

    public void setJumlah_barang(int jumlah_barang) {
        this.jumlah_barang = jumlah_barang;
    }

    public String getNama_pemasok() {
        return nama_pemasok;
    }

    public void setNama_pemasok(String nama_pemasok) {
        this.nama_pemasok = nama_pemasok;
    }

    public String getKeterangan_lain() {
        return keterangan_lain;
    }

    public void setKeterangan_lain(String keterangan_lain) {
        this.keterangan_lain = keterangan_lain;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.tanggal_masuk);
        dest.writeString(this.petugas_pencatat);
        dest.writeString(this.nama_barang);
        dest.writeInt(this.jumlah_barang);
        dest.writeString(this.nama_pemasok);
        dest.writeString(this.keterangan_lain);
    }

    public ShopModel() {
    }

    protected ShopModel(Parcel in) {
        this.id = in.readInt();
        this.tanggal_masuk = in.readString();
        this.petugas_pencatat = in.readString();
        this.nama_barang = in.readString();
        this.jumlah_barang = in.readInt();
        this.nama_pemasok = in.readString();
        this.keterangan_lain = in.readString();
    }

    public static final Parcelable.Creator<ShopModel> CREATOR = new Parcelable.Creator<ShopModel>() {
        @Override
        public ShopModel createFromParcel(Parcel source) {
            return new ShopModel(source);
        }

        @Override
        public ShopModel[] newArray(int size) {
            return new ShopModel[size];
        }
    };
}
