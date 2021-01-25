package com.hoanglam0869.toeic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnChontu;
    ListView lvChuDe;
    ArrayList<String> mangChuDe;
    ArrayAdapter adapter;

    String chude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBarMain);
        btnChontu = findViewById(R.id.buttonChontu);
        lvChuDe = findViewById(R.id.listViewChuDe);
        setSupportActionBar(toolbar);

        MangChuDe();
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mangChuDe);
        lvChuDe.setAdapter(adapter);

        btnChontu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chude == null){
                    Toast.makeText(MainActivity.this, "Bạn chưa chọn chủ đề", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(MainActivity.this, ChonTuActivity.class);
                    intent.putExtra("chude", chude);
                    startActivity(intent);
                }
            }
        });

        lvChuDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chude = mangChuDe.get(position);
            }
        });
    }

    private void MangChuDe(){
        mangChuDe = new ArrayList<>();
        mangChuDe.add("1. Contracts - Hợp Đồng");                      // 0
        mangChuDe.add("2. Marketing - Nghiên Cứu Thị Trường");         // 1
        mangChuDe.add("3. Warrranties - Sự Bảo Hành");
        mangChuDe.add("4. Business Planning - Kế Hoạch Kinh Doanh");
    }
}