package com.hoanglam0869.toeic;

import java.io.Serializable;

public class DuLieu implements Serializable {
    private int ID;
    private String ChuDe;
    private String Tu;
    private String PhienAm;
    private String Nghia;
    private String TuLoai;
    private String HinhAnh;
    private String AmThanh;
    private String KetQua;

    public DuLieu(int id, String chuDe, String tu, String phienAm, String nghia, String tuLoai, String hinhAnh, String amThanh, String ketQua) {
        ID = id;
        ChuDe = chuDe;
        Tu = tu;
        PhienAm = phienAm;
        Nghia = nghia;
        TuLoai = tuLoai;
        HinhAnh = hinhAnh;
        AmThanh = amThanh;
        KetQua = ketQua;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getChuDe() {
        return ChuDe;
    }

    public void setChuDe(String chuDe) {
        ChuDe = chuDe;
    }

    public String getTu() {
        return Tu;
    }

    public void setTu(String tu) {
        Tu = tu;
    }

    public String getPhienAm() {
        return PhienAm;
    }

    public void setPhienAm(String phienAm) {
        PhienAm = phienAm;
    }

    public String getNghia() {
        return Nghia;
    }

    public void setNghia(String nghia) {
        Nghia = nghia;
    }

    public String getTuLoai() {
        return TuLoai;
    }

    public void setTuLoai(String tuLoai) {
        TuLoai = tuLoai;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getAmThanh() {
        return AmThanh;
    }

    public void setAmThanh(String amThanh) {
        AmThanh = amThanh;
    }

    public String getKetQua() {
        return KetQua;
    }

    public void setKetQua(String ketQua) {
        KetQua = ketQua;
    }
}
