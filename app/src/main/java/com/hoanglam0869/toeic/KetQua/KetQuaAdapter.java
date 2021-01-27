package com.hoanglam0869.toeic.KetQua;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoanglam0869.toeic.DuLieu;
import com.hoanglam0869.toeic.R;

import java.io.IOException;
import java.util.ArrayList;

public class KetQuaAdapter extends BaseAdapter {
    private final Context context;
    private final int layout;
    private final ArrayList<DuLieu> listDuLieu;

    public KetQuaAdapter(Context context, int layout, ArrayList<DuLieu> listDuLieu) {
        this.context = context;
        this.layout = layout;
        this.listDuLieu = listDuLieu;
    }

    @Override
    public int getCount() {
        return listDuLieu.size();
    }

    @Override
    public Object getItem(int position) {
        return listDuLieu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        TextView txtTu, txtNghia;
        ImageView imgDapAn, imgAmThanh;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtTu = convertView.findViewById(R.id.textViewTu);
            holder.txtNghia = convertView.findViewById(R.id.textViewNghia);
            holder.imgDapAn = convertView.findViewById(R.id.imageViewDapAn);
            holder.imgAmThanh = convertView.findViewById(R.id.imageViewAmThanh);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DuLieu duLieu = listDuLieu.get(position);
        holder.txtTu.setText(duLieu.getTu());
        holder.txtNghia.setText(duLieu.getNghia());
        if (duLieu.getKetQua().equals("D")){
            holder.imgDapAn.setImageResource(R.drawable.dung);
        } else {
            holder.imgDapAn.setImageResource(R.drawable.sai);
        }
        holder.imgAmThanh.setImageResource(R.drawable.loa);

        holder.imgAmThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayNhacMp3(duLieu.getAmThanh());
            }
        });

        return convertView;
    }

    private void PlayNhacMp3(String url){
        //url = "http://khoapham.vn/download/vietnamoi.mp3";
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
