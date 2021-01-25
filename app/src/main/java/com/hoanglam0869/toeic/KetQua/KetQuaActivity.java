package com.hoanglam0869.toeic.KetQua;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hoanglam0869.toeic.ChonTuActivity;
import com.hoanglam0869.toeic.DuLieu;
import com.hoanglam0869.toeic.MainActivity;
import com.hoanglam0869.toeic.R;

import java.util.ArrayList;

public class KetQuaActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtExp, txtVang, txtDung, txtSai;
    ListView lvKetQua;
    ArrayList<DuLieu> listDuLieu;
    KetQuaAdapter adapter;
    ImageView imgLamLai;
    Button btnTiepTuc;

    int chude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua);

        AnhXa();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        txtExp.setText(intent.getStringExtra("exp"));
        txtVang.setText(intent.getStringExtra("vang"));
        txtDung.setText(String.valueOf(intent.getIntExtra("dung", 123)));
        txtSai.setText(String.valueOf(intent.getIntExtra("sai", 123)));
        chude = intent.getIntExtra("chude", 123);

        listDuLieu = (ArrayList<DuLieu>) intent.getSerializableExtra("dulieu");
        adapter = new KetQuaAdapter(this, R.layout.dong_ket_qua, listDuLieu);
        lvKetQua.setAdapter(adapter);

        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuayLai();
            }
        });

        imgLamLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KetQuaActivity.this, ChonTuActivity.class);
                intent.putExtra("chude", chude);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolBarKetQua);
        txtExp = findViewById(R.id.textViewExp);
        txtVang = findViewById(R.id.textViewVang);
        txtDung = findViewById(R.id.textViewDung);
        txtSai = findViewById(R.id.textViewSai);
        lvKetQua = findViewById(R.id.listViewKetQua);
        imgLamLai = findViewById(R.id.imageViewLamLai);
        btnTiepTuc = findViewById(R.id.buttonTiepTuc);
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
                    QuayLai();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        } else {
            QuayLai();
        }
    }

    private void QuayLai(){
        Intent intent = new Intent(KetQuaActivity.this, MainActivity.class);
        startActivity(intent);
    }
}