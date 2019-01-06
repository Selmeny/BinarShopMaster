package com.dicoding.paul.binarshop;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dicoding.paul.binarshop.Adapter.ShopAdapter;
import com.dicoding.paul.binarshop.Database.ShopHelper;
import com.dicoding.paul.binarshop.Model.ShopModel;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dicoding.paul.binarshop.AddAndEditActivity.REQUEST_UPDATE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.rv_binar_shop) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private LinkedList<ShopModel> linkedList;
    private ShopAdapter adapter;
    private ShopHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        floatingActionButton.setOnClickListener(this);

        helper = new ShopHelper(this);
        helper.open();

        linkedList = new LinkedList<>();

        new LoadShopAsync().execute();

        showRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //override this method to close database when activity is being destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper != null) {
            helper.close();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            Intent intent = new Intent(MainActivity.this, AddAndEditActivity.class);
            startActivityForResult(intent, AddAndEditActivity.REQUEST_ADD);
        }
    }

    //Use asycntask to load data from database to model
    private class LoadShopAsync extends AsyncTask<Void, Void, ArrayList<ShopModel>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (linkedList.size() > 0) {
                linkedList.clear();
            }
        }

        @Override
        protected ArrayList<ShopModel> doInBackground(Void... voids) {
            return helper.query();
        }

        @Override
        protected void onPostExecute(ArrayList<ShopModel> models) {
            super.onPostExecute(models);

            linkedList.addAll(models);
            adapter.setShopModelLinkedList(linkedList);
            adapter.notifyDataSetChanged();

            if (linkedList.size() == 0) {
                showSnackBarMessage("Tidak ada data saat ini");
            }
        }
    }

    //override this method to get data back from other activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddAndEditActivity.REQUEST_ADD) {
            if (resultCode == AddAndEditActivity.RESULT_ADD) {
                new LoadShopAsync().execute();
                showSnackBarMessage("Satu barang berhasil ditambahkan");
            }
        } else if (requestCode == REQUEST_UPDATE) {
            if (resultCode == AddAndEditActivity.RESULT_UPDATE) {
                new LoadShopAsync().execute();
                showSnackBarMessage("Satu barang berhasil diperbarui");
            } else if (resultCode == AddAndEditActivity.RESULT_DELETE) {
                int position = data.getIntExtra(AddAndEditActivity.EXTRA_POSITION, 0);
                linkedList.remove(position);
                adapter.setShopModelLinkedList(linkedList);
                adapter.notifyDataSetChanged();
                showSnackBarMessage("Satu barang berhasil dihapus");
            }
        }
    }

    //snackbar to diplay message, we can also use toast message
    private void showSnackBarMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_LONG).show();
    }

    //use this method to set adapter into recyclerview and display it
    private void showRecyclerView () {
        ButterKnife.bind(this);
        adapter = new ShopAdapter(this);
        adapter.setShopModelLinkedList(linkedList);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }
}
