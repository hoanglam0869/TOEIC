package com.hoanglam0869.toeic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hoanglam0869.toeic.KetQua.KetQuaActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class ChonTuActivity extends AppCompatActivity {

    String DATABASE_NAME = "toeic.sqlite";
    SQLiteDatabase database;

    Toolbar toolbar;
    ProgressBar progressBar;
    ImageView imgHinh, imgGoiYDe;
    TextView txtGoiYDe, txtExp, txtVang, txtNghia;
    Button btnTu1, btnTu2;
    ArrayList<DuLieu> mangDuLieu, mangTron;
    ArrayList<String> mangTraLoi;

    int chude = -1;
    int stt = 0;
    int GoiYDe, Exp, Vang;
    int sai = 0;
    boolean GoiY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_tu);

        AnhXa();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        chude = intent.getIntExtra("chude", 123);

        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TOEIC WHERE Chude=" + chude, null);
        cursor.moveToFirst();

        mangDuLieu = new ArrayList<>();
        do {
            mangDuLieu.add(new DuLieu(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), ""));
        } while (cursor.moveToNext());

        Collections.shuffle(mangDuLieu);
        mangTron = new ArrayList<>();
        mangTron.addAll(mangDuLieu);
        DoiCauHoi();
        Chung.ThanhTienDo(progressBar, stt, mangDuLieu);

        imgGoiYDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoiY = true;
                GoiYDe = Integer.parseInt(txtGoiYDe.getText().toString());
                txtGoiYDe.setText(GoiYDe - 1 + "");
                btnTu1.setClickable(false);
                btnTu2.setClickable(false);
                if (mangTron.get(stt).getTu().equals(btnTu1.getText().toString())){
                    btnTu1.setBackgroundResource(R.drawable.duongvien_goctron_dung);
                } else {
                    btnTu2.setBackgroundResource(R.drawable.duongvien_goctron_dung);
                }
                Chung.AmThanh(ChonTuActivity.this, R.raw.dung);
                GhiDapAn("D");
                Chung.PlayNhacMp3(mangTron.get(stt).getAmThanh());
                new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        if (stt == mangDuLieu.size() - 1){
                            Intent intent = new Intent(ChonTuActivity.this, KetQuaActivity.class);
                            intent.putExtra("exp", txtExp.getText().toString());
                            intent.putExtra("vang", txtVang.getText().toString());
                            intent.putExtra("sai", sai);
                            intent.putExtra("dung", mangDuLieu.size() - sai);
                            intent.putExtra("dulieu", mangTron);
                            intent.putExtra("chude", chude);
                            startActivity(intent);
                        } else {
                            stt++;
                            DoiCauHoi();
                            Chung.ThanhTienDo(progressBar, stt, mangDuLieu);
                        }
                    }
                }.start();
            }
        });
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolBarChonTu);
        txtGoiYDe = findViewById(R.id.textViewGoiYDe);
        txtExp = findViewById(R.id.textViewExp);
        txtVang = findViewById(R.id.textViewVang);
        progressBar = findViewById(R.id.progressBarChonTu);
        imgHinh = findViewById(R.id.imageViewHinh);
        txtNghia = findViewById(R.id.textViewNghia);
        imgGoiYDe = findViewById(R.id.imageViewGoiYDe);
        btnTu1 = findViewById(R.id.buttonTu1);
        btnTu2 = findViewById(R.id.buttonTu2);
    }

    @Override
    public void onBackPressed() {
        if (!txtExp.getText().toString().equals("0") | !txtVang.getText().toString().equals("0")){
            AlertDialog.Builder builder = new  AlertDialog.Builder(this);
            builder.setTitle("Bạn có chắc chắn muốn quay lại?");
            builder.setMessage("Kinh nghiệm và vàng sẽ bị mất.");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ChonTuActivity.super.onBackPressed();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        } else {
            super.onBackPressed();
        }
    }

    private void DoiCauHoi(){
        Collections.shuffle(mangDuLieu);
        mangTraLoi = new ArrayList<>();
        mangTraLoi.add(mangTron.get(stt).getTu());

        if (mangTron.get(stt).getTu().equals(mangDuLieu.get(0).getTu())){
            mangTraLoi.add(mangDuLieu.get(1).getTu());
        } else {
            mangTraLoi.add(mangDuLieu.get(0).getTu());
        }

        Picasso.get().load(mangTron.get(stt).getHinhAnh()).into(imgHinh);
        txtNghia.setText(mangTron.get(stt).getNghia());
        Collections.shuffle(mangTraLoi);
        btnTu1.setText(mangTraLoi.get(0));
        btnTu2.setText(mangTraLoi.get(1));

        btnTu1.setBackgroundResource(R.drawable.duongvien_goctron);
        btnTu2.setBackgroundResource(R.drawable.duongvien_goctron);
        btnTu1.setClickable(true);
        btnTu2.setClickable(true);

        Exp = 6;
        Vang = 3;
        GoiY = false;
    }

    public void ChonDapAn(View view) {
        Button btn = (Button) view;
        if (mangTron.get(stt).getTu().equals(btn.getText().toString())){
            btnTu1.setClickable(false);
            btnTu2.setClickable(false);
            btn.setBackgroundResource(R.drawable.duongvien_goctron_dung);
            Chung.AmThanh(this, R.raw.dung);
            GhiDapAn("D");
            Chung.PlayNhacMp3(mangTron.get(stt).getAmThanh());
            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    if (stt == mangDuLieu.size() - 1){
                        Intent intent = new Intent(ChonTuActivity.this, KetQuaActivity.class);
                        intent.putExtra("exp", txtExp.getText().toString());
                        intent.putExtra("vang", txtVang.getText().toString());
                        intent.putExtra("sai", sai);
                        intent.putExtra("dung", mangDuLieu.size() - sai);
                        intent.putExtra("dulieu", mangTron);
                        intent.putExtra("chude", chude);
                        startActivity(intent);
                    } else {
                        stt++;
                        DoiCauHoi();
                        Chung.ThanhTienDo(progressBar, stt, mangDuLieu);
                    }
                }
            }.start();
        } else {
            btn.setBackgroundResource(R.drawable.duongvien_goctron_sai);
            Chung.AmThanh(this, R.raw.sai);
            GhiDapAn("S");
        }
    }

    private void GhiDapAn(String kq){
        if (kq.equals("S") & mangTron.get(stt).getKetQua().equals("")){
            sai++;
            Exp = 0;
            Vang = -(Vang + sai);
        }
        if (mangTron.get(stt).getKetQua().equals("")){
            mangTron.get(stt).setKetQua(kq);
        }
        if (GoiY & !mangTron.get(stt).getKetQua().equals("S")){
            Exp = 0;
            Vang = 0;
        }
        if (kq.equals("D")){
            Thuong(txtExp, Exp);
            Thuong(txtVang, Vang);
        }
    }

    private void Thuong(TextView tv, int thuong){
        int knv = Integer.parseInt(tv.getText().toString());
        if (knv + thuong < 0){
            thuong = -knv;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(knv, knv + thuong);
        valueAnimator.setDuration(1000);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                tv.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Menu){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.main_menu);

            TextView txtTienTrinh = dialog.findViewById(R.id.textViewTienTrinh);
            ProgressBar pbTienTrinh = dialog.findViewById(R.id.pbTienTrinh);
            TextView txtExpdialog = dialog.findViewById(R.id.textViewExp);
            TextView txtVangdialog = dialog.findViewById(R.id.textViewVang);
            int TienTrinh = (stt + 1) * progressBar.getMax() / mangTron.size();
            txtTienTrinh.setText(TienTrinh + "%");
            pbTienTrinh.setProgress(TienTrinh);
            txtExpdialog.setText(txtExp.getText().toString());
            txtVangdialog.setText(txtVang.getText().toString());

            dialog.show();
        }
        return true;
    }
}