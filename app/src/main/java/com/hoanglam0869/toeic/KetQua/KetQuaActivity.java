package com.hoanglam0869.toeic.KetQua;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

    String chude;
    SharedPreferences sp;

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
        chude = intent.getStringExtra("chude");

        listDuLieu = (ArrayList<DuLieu>) intent.getSerializableExtra("dulieu");
        adapter = new KetQuaAdapter(this, R.layout.dong_ket_qua, listDuLieu);
        lvKetQua.setAdapter(adapter);

        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuExpVang();
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
            builder.setTitle(R.string.TitleAlertDialog);
            builder.setMessage(R.string.MessageAlertDialog);
            builder.setPositiveButton(R.string.PositiveButtonAlertDialog, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    QuayLai();
                }
            });
            builder.setNegativeButton(R.string.NegativeButtonAlertDialog, new DialogInterface.OnClickListener() {
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

    private void LuuExpVang(){
        sp = getSharedPreferences("ExpVang", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int Exp = Integer.parseInt(sp.getString("Exp", "0")) + Integer.parseInt(txtExp.getText().toString());
        int Vang = Integer.parseInt(sp.getString("Vang", "0")) + Integer.parseInt(txtVang.getText().toString());
        editor.putString("Exp", String.valueOf(Exp));
        editor.putString("Vang", String.valueOf(Vang));
        editor.commit();
    }
}