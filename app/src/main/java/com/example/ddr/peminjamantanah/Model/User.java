package com.example.ddr.peminjamantanah.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable { //We can get more easier by using library from Parceler
    private String key_id;
    private String id;
    private String nomor_sewa;
    private String nama_pemakai;
    private String email;
    private String password;
    private Dokumen dokumen;

    public User(String key_id, String id, String nomor_sewa, String nama_pemakai, String KTP, String KK, String BPS, String SBP) {
        this.key_id = key_id;
        this.id = id;
        this.nomor_sewa = nomor_sewa;
        this.nama_pemakai = nama_pemakai;
        this.email = "";
        this.password = "";
        dokumen = new Dokumen(KTP, KK, BPS, SBP);
    }

    protected User(Parcel in) { //for return User in Parcelable
        key_id = in.readString();
        id = in.readString();
        nomor_sewa = in.readString();
        nama_pemakai = in.readString();
        email = in.readString();
        password = in.readString();
        dokumen = in.readParcelable(Dokumen.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) { //write to Parcel
        dest.writeString(key_id);
        dest.writeString(id);
        dest.writeString(nomor_sewa);
        dest.writeString(nama_pemakai);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeParcelable(dokumen,flags);
    }

    @Override
    public int describeContents() { // Describe special object what is special object go to stackOverflow //Zero means there is no specia object
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() { //create Parcel Object
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getNomor_sewa() {
        return nomor_sewa;
    }

    public void setNomor_sewa(String nomor_sewa) {
        this.nomor_sewa = nomor_sewa;
    }

    public String getNama_pemakai() {
        return nama_pemakai;
    }

    public void setNama_pemakai(String nama_pemakai) {
        this.nama_pemakai = nama_pemakai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public Dokumen getDokumen() {
        return dokumen;
    }
}
