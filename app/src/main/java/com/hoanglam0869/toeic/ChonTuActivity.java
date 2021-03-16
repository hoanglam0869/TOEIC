package com.hoanglam0869.toeic;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;

public class ChonTuActivity extends AppCompatActivity {

    String DATABASE_NAME = "toeic.sqlite";
    SQLiteDatabase database;

    Toolbar toolbar;
    ProgressBar progressBar;
    TextView txtGoiYDe, txtExp, txtVang;
    ArrayList<DuLieu> mangDuLieu, mangTron;

    String chuDe;
    int stt = 0, viTri = 0;
    static int sai;
    static int tiendo;
    String url = "https://600tuvungtoeic.com/index.php?mod=lesson&id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_tu);

        AnhXa();
        ActionBar();


        Intent intent = getIntent();
        chuDe = intent.getStringExtra("chude");
        viTri = intent.getIntExtra("vitri", 0);

        /*database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TOEIC WHERE Chude='" + chuDe + "'", null);
        cursor.moveToFirst();

        mangDuLieu = new ArrayList<>();
        do {
            mangDuLieu.add(new DuLieu(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), ""));
        } while (cursor.moveToNext());*/
        mangDuLieu = Chung.GetDuLieu(this, viTri, chuDe);

        Collections.shuffle(mangDuLieu);
        mangTron = new ArrayList<>();
        mangTron.addAll(mangDuLieu);

        sai = 0;
        tiendo = 0;
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolBarChonTu);
        txtGoiYDe = findViewById(R.id.textViewGoiYDe);
        txtExp = findViewById(R.id.textViewExp);
        txtVang = findViewById(R.id.textViewVang);
        progressBar = findViewById(R.id.progressBarChonTu);
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

    public void GetDuLieu(int viTri, String chuDe) {
        mangDuLieu = new ArrayList<>();
        String duongDan = url + viTri;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String hinh = "", tuVung = "", phienAm = "", tuLoai = "null", amThanh = "";
                Document document = Jsoup.parse(response);
                if (document != null) {
                    Elements elements = document.select("div.tuvung");
                    int i = 0;
                    for (Element element : elements) {
                        Element elementSTT = element.select("div.stt").first();
                        Element elementHinh = element.getElementsByTag("img").first();
                        Element elementTuVung = element.select("span").first();
                        Element elementPhienAm = element.select("span").next().first();

                        Element elementTuLoai = element.select("span.bold").next().next().first();
                        Element elementAmThanh = element.getElementsByTag("source").first();

                        if (elementHinh != null) {
                            hinh = elementHinh.attr("src");
                        }
                        if (elementTuVung != null) {
                            tuVung = elementTuVung.text();
                        }
                        if (elementPhienAm != null) {
                            phienAm = elementPhienAm.text();
                        }
                        if (elementTuLoai != null) {
                            tuLoai = elementTuLoai.nextSibling().toString();
                        }
                        if (elementAmThanh != null) {
                            amThanh = "https://600tuvungtoeic.com/" + elementAmThanh.attr("src");
                        }
                        if (elementSTT != null) {
                            mangDuLieu.add(new DuLieu(++i, chuDe, tuVung, phienAm, tuLoai, tuLoai, hinh, amThanh, ""));
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}