package com.tripointeknologi.saktoko.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SessionApp {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public static final String KEY_ID = "id_pelanggan";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NO_HP = "no_hp";
    public static final String KEY_FOTO = "foto";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_STATUS="status";
    public static final String KEY_TIPE = "tipe";
    final String SHARE_NAMA = "com.tripointeknologi.saktoko.log";

    Context _context;

    @SuppressLint("CommitPrefEdits")
    public SessionApp(Context context) {
        this._context = context;
        sp = _context.getSharedPreferences(SHARE_NAMA, Context.MODE_PRIVATE);
        editor = sp.edit();
    }




    public void storelogin(String id, String nama,String email,String password,String no_hp,String tipe,String foto,boolean status) {
        editor.putString(KEY_ID, id);
        editor.putString(KEY_NAMA, nama);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_NO_HP, no_hp);
        editor.putString(KEY_TIPE,tipe);
        editor.putString(KEY_FOTO, foto);
        editor.putBoolean(KEY_STATUS,status);
        editor.commit();
    }

    public void update(String nama,String email,String password,String foto_profil) {
        editor.putString(KEY_NAMA, nama);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_FOTO,foto_profil);
        editor.commit();
    }

    public void updateTipe(String tipe){
        editor.putString(KEY_TIPE,tipe);
        editor.commit();
    }

    public String getTipe(){
        return sp.getString(KEY_TIPE,"1");
    }

    public void updateFoto(String foto) {
        editor.putString(KEY_FOTO, foto);
        editor.commit();
    }


    public String getIdUser(){
        return sp.getString(KEY_ID, "");
    }
    public String getNama() {
        return sp.getString(KEY_NAMA, "");
    }
    public String getEmail() {
        return sp.getString(KEY_EMAIL, "");
    }
    public String getFoto(){
        return sp.getString(KEY_FOTO,null);
    }

    public String getNoHP(){
        return sp.getString(KEY_NO_HP,"");
    }
    public void clearSession(){
        editor.clear();
        editor.commit();
    }
    public boolean getLogin(){
        return sp.getBoolean(KEY_STATUS,false);
    }

    public void storeToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        return sp.getString(KEY_TOKEN, "");
    }


}