package com.dicoding.paul.binarshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.dicoding.paul.binarshop.Database.ShopHelper;
import com.dicoding.paul.binarshop.Model.ShopModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAndEditActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.edt_petugas_pencatat) EditText edtPetugasPencatat;
    @BindView(R.id.edt_nama_barang) EditText edtNamaBarang;
    @BindView(R.id.edt_jumlah_barang) EditText edtJumlahBarang;
    @BindView(R.id.edt_nama_pemasok) EditText edtNamaPemasok;
    @BindView(R.id.edt_keterangan_lain) EditText edtKeteranganLain;
    @BindView(R.id.btn_add) Button btnTambah;
    @BindView(R.id.toolbar) Toolbar toolbar;


    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_SHOP = "extra_shop";

    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int RESULT_DELETE = 300;

    final int ALERT_DIALOG_DELETE = 10;
    final int ALERT_DIALOG_CLOSE = 11;

    private boolean isEdit = false;

    private ShopModel model;
    private int position;
    private ShopHelper shopHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        btnTambah.setOnClickListener(this);

        shopHelper = new ShopHelper(this);
        shopHelper.open();

        model = getIntent().getParcelableExtra(EXTRA_SHOP);

        if (model != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        }

        String actionBarTitle;
        String btnTitle;

        if (isEdit) {
            actionBarTitle = "Ubah";
            btnTitle = "Perbarui";
            edtPetugasPencatat.setText(model.getPetugas_pencatat());
            edtNamaBarang.setText(model.getNama_barang());
            edtJumlahBarang.setText(String.valueOf(model.getJumlah_barang()));
            edtNamaPemasok.setText(model.getNama_pemasok());
            edtKeteranganLain.setText(model.getKeterangan_lain());
        } else {
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnTambah.setText(btnTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (shopHelper != null) {
            shopHelper.close();
        }
    }

    @Override
    public void onClick(View v) {
        ButterKnife.bind(this);
        if (v.getId() == R.id.btn_add) {
            String petugasPencatat = edtPetugasPencatat.getText().toString().trim();
            String namaBarang = edtNamaBarang.getText().toString().trim();
            int jumlahBarang = Integer.parseInt(edtJumlahBarang.getText().toString().trim());
            String namaPemasok = edtNamaPemasok.getText().toString().trim();
            String keteranganLain = edtKeteranganLain.getText().toString().trim();

            boolean isEmpty = false;

            if (TextUtils.isEmpty (petugasPencatat)) {
                isEmpty = true;
                edtPetugasPencatat.setError("Tidak boleh kosong");
            }

            if (TextUtils.isEmpty (namaBarang)) {
                isEmpty = true;
                edtPetugasPencatat.setError("Tidak boleh kosong");
            }

            if (TextUtils.isEmpty (String.valueOf(jumlahBarang))) {
                isEmpty = true;
                edtPetugasPencatat.setError("Tidak boleh kosong");
            }

            if (TextUtils.isEmpty (namaPemasok)) {
                isEmpty = true;
                edtPetugasPencatat.setError("Tidak boleh kosong");
            }

            if (TextUtils.isEmpty (keteranganLain)) {
                isEmpty = true;
                edtPetugasPencatat.setError("Tidak boleh kosong");
            }

            if (!isEmpty) {
                ShopModel shopModel = new ShopModel();
                shopModel.setPetugas_pencatat(petugasPencatat);
                shopModel.setNama_barang(namaBarang);
                shopModel.setJumlah_barang(jumlahBarang);
                shopModel.setNama_pemasok(namaPemasok);
                shopModel.setKeterangan_lain(keteranganLain);

                Intent resultIntent = new Intent();

                if (isEdit) {
                    shopModel.setTanggal_masuk(model.getTanggal_masuk());
                    shopModel.setId(model.getId());
                    shopHelper.update(shopModel);

                    resultIntent.putExtra(EXTRA_POSITION, position);
                    setResult(RESULT_UPDATE, resultIntent);
                    finish();
                } else {
                    shopModel.setTanggal_masuk(getCurrentDate());
                    shopHelper.insert(shopModel);

                    setResult(RESULT_ADD);
                    finish();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_delete, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle;
        String dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan pembaruan?";
        } else {
            dialogTitle = "Hapus";
            dialogMessage = "Apakah anda yakin ingin menghapus catatan ini?";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (isDialogClose) {
                            finish();
                        } else {
                            shopHelper.delete(model.getId());
                            Intent intent = new Intent();
                            intent.putExtra(EXTRA_POSITION, position);
                            setResult(RESULT_DELETE, intent);
                            finish();
                        }

                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }
}
