package com.dicoding.paul.binarshop.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dicoding.paul.binarshop.AddAndEditActivity;
import com.dicoding.paul.binarshop.Model.ShopModel;
import com.dicoding.paul.binarshop.R;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

//adapter used to display data from database into view object
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    private LinkedList<ShopModel> shopModelLinkedList;
    private Activity activity;

    public ShopAdapter(Activity activity) {
        this.activity = activity;
    }

    public LinkedList<ShopModel> getShopModelLinkedList() {
        return shopModelLinkedList;
    }

    public void setShopModelLinkedList(LinkedList<ShopModel> shopModelLinkedList) {
        this.shopModelLinkedList = shopModelLinkedList;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_item_cardview,
                viewGroup, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder shopViewHolder, int position) {
        ShopModel model = getShopModelLinkedList().get(position);
        shopViewHolder.tanggalMasuk.setText(model.getTanggal_masuk());
        shopViewHolder.petugasPencatat.setText(model.getPetugas_pencatat());
        shopViewHolder.namaBarang.setText(model.getNama_barang());
        shopViewHolder.jumlahBarang.setText(String.valueOf(model.getJumlah_barang()));
        shopViewHolder.namaPemasok.setText(model.getNama_pemasok());
        shopViewHolder.keteranganLain.setText(model.getKeterangan_lain());
    }

    @Override
    public int getItemCount() {
        return getShopModelLinkedList().size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.cv_shop_items) CardView shopItems;
        @BindView(R.id.tv_tanggal_masuk) TextView tanggalMasuk;
        @BindView(R.id.tv_petugas_pencatat) TextView petugasPencatat;
        @BindView(R.id.tv_nama_barang) TextView namaBarang;
        @BindView(R.id.tv_jumlah_barang) TextView jumlahBarang;
        @BindView(R.id.tv_nama_pemasok) TextView namaPemasok;
        @BindView(R.id.tv_keterangan_lain) TextView keteranganLain;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            ShopModel model = getShopModelLinkedList().get(getAdapterPosition());
            Intent intent = new Intent(activity, AddAndEditActivity.class);
            intent.putExtra(AddAndEditActivity.EXTRA_POSITION, getAdapterPosition());
            intent.putExtra(AddAndEditActivity.EXTRA_SHOP, model);
            activity.startActivityForResult(intent, AddAndEditActivity.REQUEST_UPDATE);
        }
    }
}
