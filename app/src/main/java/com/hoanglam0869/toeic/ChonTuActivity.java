package com.hoanglam0869.toeic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ChonTuActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 5;
    ViewPager mPager;
    private PagerAdapter pagerAdapter;

    String DATABASE_NAME = "toeic.sqlite";
    SQLiteDatabase database;

    Toolbar toolbar;
    ProgressBar progressBar;
    TextView txtGoiYDe, txtExp, txtVang;
    ArrayList<DuLieu> mangDuLieu, mangTron;

    String chude;
    int stt = 0;
    static int sai;
    static int tiendo;

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
        chude = intent.getStringExtra("chude");

        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TOEIC WHERE Chude='" + chude + "'", null);
        cursor.moveToFirst();

        mangDuLieu = new ArrayList<>();
        do {
            mangDuLieu.add(new DuLieu(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), ""));
        } while (cursor.moveToNext());

        Collections.shuffle(mangDuLieu);
        mangTron = new ArrayList<>();
        mangTron.addAll(mangDuLieu);

        mPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        sai = 0;
        tiendo = 0;
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolBarChonTu);
        txtGoiYDe = findViewById(R.id.textViewGoiYDe);
        txtExp = findViewById(R.id.textViewExp);
        txtVang = findViewById(R.id.textViewVang);
        progressBar = findViewById(R.id.progressBarChonTu);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ChonTuFragment.TruyenDuLieu(position);
        }

        @Override
        public int getCount() {
            return mangDuLieu.size();
        }
    }

    @Override
    public void onBackPressed() {
        if (!txtExp.getText().toString().equals("0") | !txtVang.getText().toString().equals("0")){
            AlertDialog.Builder builder = new  AlertDialog.Builder(this);
            builder.setTitle(R.string.TitleAlertDialog);
            builder.setMessage(R.string.MessageAlertDialog);
            builder.setPositiveButton(R.string.PositiveButtonAlertDialog, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ChonTuActivity.super.onBackPressed();
                }
            });
            builder.setNegativeButton(R.string.NegativeButtonAlertDialog, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        } else {
            super.onBackPressed();
        }
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
            TextView txtChuDe = dialog.findViewById(R.id.textViewChuDe);

            int TienTrinh = (tiendo + 1) * progressBar.getMax() / mangTron.size();
            txtTienTrinh.setText(TienTrinh + "%");
            pbTienTrinh.setProgress(TienTrinh);
            txtExpdialog.setText(txtExp.getText().toString());
            txtVangdialog.setText(txtVang.getText().toString());
            txtChuDe.setText(mangTron.get(stt).getChuDe());

            dialog.show();
        }
        return true;
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }
}