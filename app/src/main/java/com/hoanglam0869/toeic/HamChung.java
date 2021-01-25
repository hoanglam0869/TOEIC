package com.hoanglam0869.toeic;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.util.ArrayList;

public class HamChung {
    public static void AmThanh(Context context, int id){
        MediaPlayer mediaPlayer = MediaPlayer.create(context, id);
        mediaPlayer.start();
        mediaPlayer.setVolume(0.3f, 0.3f);
    }

    public static void PlayNhacMp3(String url){
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

    public static void ThanhTienDo(ProgressBar progressBar, int stt, ArrayList<DuLieu> mangDuLieu) {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress",
                stt * progressBar.getMax() / mangDuLieu.size(), (stt + 1) * progressBar.getMax() / mangDuLieu.size());
        animation.setDuration(1000);
        animation.start();
    }
}
