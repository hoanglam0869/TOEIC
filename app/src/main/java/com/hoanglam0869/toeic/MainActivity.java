package com.hoanglam0869.toeic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtExp, txtVang;
    ListView lvChuDe;
    ArrayList<DuLieu> mangChuDe;
    ChuDeAdapter adapter;

    String chude;
    SharedPreferences sp;

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

        MangChuDe();
        adapter = new ChuDeAdapter(MainActivity.this, R.layout.dong_chu_de, mangChuDe);
        lvChuDe.setAdapter(adapter);

        lvChuDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chude = mangChuDe.get(position).getChuDe();
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_tro_choi);
                Button btnChonTu = dialog.findViewById(R.id.buttonChonTu);

                btnChonTu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ChonTuActivity.class);
                        intent.putExtra("chude", chude);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
    }

    private void MangChuDe(){
        mangChuDe = new ArrayList<>();
        mangChuDe.add(new DuLieu(1,"1. Contracts - Hợp Đồng", "", "", "", "", "https://600tuvungtoeic.com/template/english/images/lesson/contracts.jpg", "", ""));
        mangChuDe.add(new DuLieu(2,"2. Marketing - Nghiên Cứu Thị Trường", "", "", "", "", "https://600tuvungtoeic.com/template/english/images/lesson/marketing.jpg", "", ""));
        mangChuDe.add(new DuLieu(3,"3. Warrranties - Sự Bảo Hành", "", "", "", "", "https://600tuvungtoeic.com/template/english/images/lesson/warranties.jpg", "", ""));
        mangChuDe.add(new DuLieu(4,"4. Business Planning - Kế Hoạch Kinh Doanh", "", "", "", "", "https://600tuvungtoeic.com/template/english/images/lesson/business_planning.jpg", "", ""));
    }
}