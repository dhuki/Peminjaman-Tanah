package com.example.ddr.peminjamantanah.Model;

public class MenuUtama {

    private int image;
    private String keterangan;
    private String subKeterangan;

    public MenuUtama(int image, String keterangan, String subKeterangan) {
        this.image = image;
        this.keterangan = keterangan;
        this.subKeterangan = subKeterangan;
    }

    public int getImage() {
        return image;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getSubKeterangan() {
        return subKeterangan;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setSubKeterangan(String subKeterangan) {
        this.subKeterangan = subKeterangan;
    }

}
