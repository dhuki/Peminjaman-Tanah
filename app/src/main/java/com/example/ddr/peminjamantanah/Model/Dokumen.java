package com.example.ddr.peminjamantanah.Model;

public class Dokumen {
    private String urlKTP;
    private String urlKK;
    private String urlBPS;
    private String urlSBP;

    public Dokumen(String urlKTP, String urlKK, String urlBPS, String urlSBP) {
        this.urlKTP = urlKTP;
        this.urlKK = urlKK;
        this.urlBPS = urlBPS;
        this.urlSBP = urlSBP;
    }


    public String getUrlKTP() {
        return urlKTP;
    }

    public void setUrlKTP(String urlKTP) {
        this.urlKTP = urlKTP;
    }

    public String getUrlKK() {
        return urlKK;
    }

    public void setUrlKK(String urlKK) {
        this.urlKK = urlKK;
    }

    public String getUrlBPS() {
        return urlBPS;
    }

    public void setUrlBPS(String urlBPS) {
        this.urlBPS = urlBPS;
    }

    public String getUrlSBP() {
        return urlSBP;
    }

    public void setUrlSBP(String urlSBP) {
        this.urlSBP = urlSBP;
    }

}
