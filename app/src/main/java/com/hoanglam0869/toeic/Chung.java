package com.hoanglam0869.toeic;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

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

public class Chung {
    public static ArrayList<DuLieu> GetDuLieu(Context context, int viTri, String chuDe) {
        ArrayList<DuLieu> mangDuLieu = new ArrayList<>();
        String duongDan = "https://600tuvungtoeic.com/index.php?mod=lesson&id=" + viTri;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String hinh = "", tuVung = "", phienAm = "", nghia = "", tuLoai = "", amThanh = "";
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
                            tuLoai = elementTuLoai.text() + " " + elementTuLoai.nextSibling().toString();
                            nghia = elementTuLoai.nextSibling().toString();
                        }
                        if (elementAmThanh != null) {
                            amThanh = "https://600tuvungtoeic.com/" + elementAmThanh.attr("src");
                        }
                        if (elementSTT != null) {
                            mangDuLieu.add(new DuLieu(++i, chuDe, tuVung, phienAm, nghia, tuLoai, hinh, amThanh, ""));
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
        return mangDuLieu;
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
}
