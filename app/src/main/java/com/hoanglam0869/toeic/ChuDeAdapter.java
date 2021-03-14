package com.hoanglam0869.toeic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChuDeAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<DuLieu> duLieuArrayList;

    public ChuDeAdapter(Context context, int layout, ArrayList<DuLieu> duLieuArrayList) {
        this.context = context;
        this.layout = layout;
        this.duLieuArrayList = duLieuArrayList;
    }

    @Override
    public int getCount() {
        return duLieuArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        ImageView imgHinh;
        TextView txtChuDe;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.imgHinh = convertView.findViewById(R.id.imageViewHinh);
            holder.txtChuDe = convertView.findViewById(R.id.textViewChuDe);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DuLieu duLieu = duLieuArrayList.get(position);
        Picasso.get().load(duLieu.getHinhAnh()).into(holder.imgHinh);
        holder.txtChuDe.setText(duLieu.getChuDe());
        return convertView;
    }
}
