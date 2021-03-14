package com.hoanglam0869.toeic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtExp, txtVang;
    ListView lvChuDe;
    ArrayList<DuLieu> mangChuDe, mangDuLieu;
    ChuDeAdapter adapter;

    SharedPreferences sp;
    String hinh = "", chuDe = "";
    String url = "https://600tuvungtoeic.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBarMain);
        txtExp = findViewById(R.id.textViewExp);
        txtVang = findViewById(R.id.textViewVang);
        lvChuDe = findViewById(R.id.listViewChuDe);
        setSupportActionBar(toolbar);

        sp = getSharedPreferences("ExpVang", MODE_PRIVATE);
        txtExp.setText(sp.getString("Exp", "0"));
        txtVang.setText(sp.getString("Vang", "0"));

        GetDuLieu();
        //MangChuDe();

        lvChuDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_tro_choi);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button btnChonTu = dialog.findViewById(R.id.buttonChonTu);
                btnChonTu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ChonTuActivity.class);
                        chuDe = mangChuDe.get(position).getChuDe();
                        intent.putExtra("chude", chuDe);
                        intent.putExtra("vitri", position + 1);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
    }

    private void GetDuLieu() {
        mangChuDe = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document document = Jsoup.parse(response);
                Elements elements = document.select("div.gallery-item");
                int i = 0;
                for (Element element : elements) {
                    Element elementHinh = element.getElementsByTag("img").first();
                    Element elementChuDe = element.getElementsByTag("h3").first();
                    if (elementHinh != null) {
                        hinh = elementHinh.attr("src");
                    }
                    if (elementChuDe != null) {
                        chuDe = elementChuDe.text();
                    }
                    mangChuDe.add(new DuLieu(++i, chuDe, "", "", "", "", hinh, "", ""));
                }
                adapter = new ChuDeAdapter(MainActivity.this, R.layout.dong_chu_de, mangChuDe);
                lvChuDe.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void MangChuDe(){
        mangChuDe = new ArrayList<>();
        mangChuDe.add(new DuLieu(1,"1. Contracts - Hợp Đồng", "", "", "", "", "https://600tuvungtoeic.com/template/english/images/lesson/contracts.jpg", "", ""));
        mangChuDe.add(new DuLieu(2,"2. Marketing - Nghiên Cứu Thị Trường", "", "", "", "", "https://600tuvungtoeic.com/template/english/images/lesson/marketing.jpg", "", ""));
        mangChuDe.add(new DuLieu(3,"3. Warrranties - Sự Bảo Hành", "", "", "", "", "https://600tuvungtoeic.com/template/english/images/lesson/warranties.jpg", "", ""));
        mangChuDe.add(new DuLieu(4,"4. Business Planning - Kế Hoạch Kinh Doanh", "", "", "", "", "https://600tuvungtoeic.com/template/english/images/lesson/business_planning.jpg", "", ""));
        adapter = new ChuDeAdapter(MainActivity.this, R.layout.dong_chu_de, mangChuDe);
        lvChuDe.setAdapter(adapter);
    }
}