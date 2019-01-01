package com.example.ddr.peminjamantanah.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Dokumen implements Parcelable {
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

    protected Dokumen(Parcel in) {
        urlKTP = in.readString();
        urlKK = in.readString();
        urlBPS = in.readString();
        urlSBP = in.readString();
    }

    public static final Creator<Dokumen> CREATOR = new Creator<Dokumen>() {
        @Override
        public Dokumen createFromParcel(Parcel in) {
            return new Dokumen(in);
        }

        @Override
        public Dokumen[] newArray(int size) {
            return new Dokumen[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(urlKTP);
        parcel.writeString(urlKK);
        parcel.writeString(urlBPS);
        parcel.writeString(urlSBP);
    }
}
