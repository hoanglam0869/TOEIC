package com.hoanglam0869.toeic;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hoanglam0869.toeic.KetQua.KetQuaActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ChonTuFragment extends Fragment {

    ImageView imgHinh, imgGoiYDe;
    TextView txtGoiYDe, txtExp, txtVang, txtNghia;
    ProgressBar progressBar;
    Button btnTu1, btnTu2;

    ArrayList<DuLieu> mangDuLieu;
    ArrayList<DuLieu> mangTron;
    ArrayList<String> mangTraLoi;
    int stt = 0;
    int GoiYDe, Exp, Vang;
    boolean isGoiY;
    String chude;
    ViewPager mPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ChonTuActivity chonTuActivity = (ChonTuActivity) getActivity();
        chude = chonTuActivity.chude;
        mangDuLieu = chonTuActivity.mangDuLieu;
        mangTron = chonTuActivity.mangTron;
        txtGoiYDe = chonTuActivity.txtGoiYDe;
        txtExp = chonTuActivity.txtExp;
        txtVang = chonTuActivity.txtVang;
        progressBar = chonTuActivity.progressBar;
        stt = getArguments().getInt("stt");
        chonTuActivity.stt = stt;
        mPager = chonTuActivity.mPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chon_tu, container, false);

        imgHinh = rootView.findViewById(R.id.imageViewHinh);
        txtNghia = rootView.findViewById(R.id.textViewNghia);
        imgGoiYDe = rootView.findViewById(R.id.imageViewGoiYDe);
        btnTu1 = rootView.findViewById(R.id.buttonTu1);
        btnTu2 = rootView.findViewById(R.id.buttonTu2);
        DoiCauHoi();
        ThanhTienDo();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imgGoiYDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGoiY = true;
                GoiYDe = Integer.parseInt(txtGoiYDe.getText().toString());
                txtGoiYDe.setText(GoiYDe - 1 + "");
                if (mangTron.get(stt).getTu().equals(btnTu1.getText().toString())){
                    btnTu1.setBackgroundResource(R.drawable.duongvien_goctron_dung);
                } else {
                    btnTu2.setBackgroundResource(R.drawable.duongvien_goctron_dung);
                }
                ChonDung();
            }
        });

        btnTu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangTron.get(stt).getTu().equals(btnTu1.getText().toString())){
                    btnTu1.setBackgroundResource(R.drawable.duongvien_goctron_dung);
                    ChonDung();
                } else {
                    btnTu1.setBackgroundResource(R.drawable.duongvien_goctron_sai);
                    AmThanh(R.raw.sai);
                    GhiDapAn("S");
                }
            }
        });

        btnTu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangTron.get(stt).getTu().equals(btnTu2.getText().toString())){
                    btnTu2.setBackgroundResource(R.drawable.duongvien_goctron_dung);
                    ChonDung();
                } else {
                    btnTu2.setBackgroundResource(R.drawable.duongvien_goctron_sai);
                    AmThanh(R.raw.sai);
                    GhiDapAn("S");
                }
            }
        });
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
        isGoiY = false;
    }

    private void ChonDung(){
        btnTu1.setClickable(false);
        btnTu2.setClickable(false);
        AmThanh(R.raw.dung);
        GhiDapAn("D");
        Chung.PlayNhacMp3(mangTron.get(stt).getAmThanh());
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                if (stt == mangDuLieu.size() - 1){
                    Intent intent = new Intent(getActivity(), KetQuaActivity.class);
                    intent.putExtra("exp", txtExp.getText().toString());
                    intent.putExtra("vang", txtVang.getText().toString());
                    intent.putExtra("sai", ChonTuActivity.sai);
                    intent.putExtra("dung", mangDuLieu.size() - ChonTuActivity.sai);
                    intent.putExtra("dulieu", mangTron);
                    intent.putExtra("chude", chude);
                    startActivity(intent);
                } else {
                    mPager.setCurrentItem(stt + 1);
                }
            }
        }.start();
    }

    private void AmThanh(int id){
        MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), id);
        mediaPlayer.start();
        mediaPlayer.setVolume(0.3f, 0.3f);
    }

    private void GhiDapAn(String kq){
        if (kq.equals("S") & mangTron.get(stt).getKetQua().equals("")){
            ChonTuActivity.sai++;
            Exp = 0;
            Vang = -(Vang + ChonTuActivity.sai);
        }
        if (mangTron.get(stt).getKetQua().equals("")){
            mangTron.get(stt).setKetQua(kq);
        }
        if (isGoiY & !mangTron.get(stt).getKetQua().equals("S")){
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

    private void ThanhTienDo() {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress",
                ChonTuActivity.tiendo * progressBar.getMax() / mangDuLieu.size(),
                (ChonTuActivity.tiendo + 1) * progressBar.getMax() / mangDuLieu.size());
        animation.setDuration(1000);
        animation.start();
        ChonTuActivity.tiendo++;
    }

    public static ChonTuFragment TruyenDuLieu(int position){
        ChonTuFragment fragment = new ChonTuFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("stt", position);
        fragment.setArguments(bundle);
        return fragment;
    }
}