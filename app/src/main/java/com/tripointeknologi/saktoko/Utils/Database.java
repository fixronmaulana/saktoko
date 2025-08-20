package com.tripointeknologi.saktoko.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tripointeknologi.saktoko.Models.MBarang;

import java.util.ArrayList;
import java.util.List;


public class Database extends SQLiteOpenHelper {
    public static final String nama_db = "saktoko_pelanggan.db";
    public static final String table_belanja = "belanja";
    public static final String C_1 = "id_barang";
    public static final String C_2 = "nama_barang";
    public static final String C_3 = "kategori";
    public static final String C_4 = "satuan";
    public static final String C_5 = "foto";
    public static final String C_6 = "harga";
    public static final String C_7 = "berat";
    public static final String C_8 = "stok";
    public static final String C_9 = "qty";
    public static final String C_10 = "total";
    public static final String C_11 = "diskon";
    public Database(Context context) {
        super(context, nama_db, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + nama_db + " (" + C_1 + " TEXT," + C_2 + " TEXT," + C_3
                + " TEXT," + C_4 + " TEXT," + C_5 + " TEXT," + C_6 + " INTEGER," + C_7 + " INTEGER," + C_8 + " INTEGER,"
                + C_9 + " INTEGER," + C_10 + " INTEGER," + C_11 + " INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + nama_db);
    }


    public void insert(String id_barang,String nama_barang,String kategori,
                       String satuan,String foto,int harga, int berat,int stok, int qty, int total,int diskon){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_1, id_barang);
        contentValues.put(C_2, nama_barang);
        contentValues.put(C_3, kategori);
        contentValues.put(C_4, satuan);
        contentValues.put(C_5, foto);
        contentValues.put(C_6, harga);
        contentValues.put(C_7, berat);
        contentValues.put(C_8, stok);
        contentValues.put(C_9, qty);
        contentValues.put(C_10, total);
        contentValues.put(C_11, diskon);
        try {
            db.insert(nama_barang, null, contentValues);

        } catch (Exception e) {
            Log.d("ERROR_INSERT", e.toString());
        }
    }

    public void update(String id_barang,int berat, int qty,int total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_7, berat);
        contentValues.put(C_9, qty);
        contentValues.put(C_10,total);
        db.update(nama_db,contentValues, C_1 + " = ?", new String[]{id_barang});
    }

    public void deleteById(String id_barang) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(nama_db, C_1 + " = ?", new String[]{id_barang});
    }

    public Integer cekProduk(String id_barang) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT COUNT(*) FROM " + nama_db + " WHERE " + C_1 + " = '" + id_barang+"'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public Integer badge() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT COUNT(*) FROM " + nama_db;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }
    @SuppressLint({"Recycle", "Range"})
    public Integer getQTY(String id_barang) {
        String query;
        query = "SELECT " + C_9 + " FROM " + nama_db + " WHERE " + C_1 + " = '" + id_barang+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int qty = 0;
        if (cursor.moveToFirst()) {
            do {
                qty = cursor.getInt(cursor.getColumnIndex(C_9));
            } while (cursor.moveToNext());
        }
        return qty;
    }

    public Integer sumBerat() {
        String query  = String.format("SELECT SUM(%s) FROM %s", C_7, nama_db);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int ttl = 0;
        if (cursor.moveToFirst()) {
            do {
                ttl  = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ttl;
    }

    public Integer sumTotal() {
        String query = String.format("SELECT SUM (%s) FROM %s", C_10, nama_db);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int qty = 0;
        if (cursor.moveToFirst()) {
            do {
                qty = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return qty;
    }

    @SuppressLint({"Recycle", "Range"})
    public List<MBarang> getAll() {
        String query;
        query = String.format("SELECT * FROM %s ORDER BY %s ASC", nama_db, C_1);
        List<MBarang> isiList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MBarang data;
        if (cursor.moveToFirst()) {
            do {
                data = new MBarang();
                data.setId_barang(cursor.getString(cursor.getColumnIndex(C_1)));
                data.setNama_barang(cursor.getString(cursor.getColumnIndex(C_2)));
                data.setKategori(cursor.getString(cursor.getColumnIndex(C_3)));
                data.setSatuan(cursor.getString(cursor.getColumnIndex(C_4)));
                data.setFoto(cursor.getString(cursor.getColumnIndex(C_5)));
                data.setHarga(cursor.getInt(cursor.getColumnIndex(C_6)));
                data.setBerat(cursor.getInt(cursor.getColumnIndex(C_7)));
                data.setStok(cursor.getInt(cursor.getColumnIndex(C_8)));
                data.setQty(cursor.getInt(cursor.getColumnIndex(C_9)));
                data.setTotal(cursor.getInt(cursor.getColumnIndex(C_10)));
//                data.setDiskon(cursor.getInt(cursor.getColumnIndex(C_11)));

                isiList.add(data);

            } while (cursor.moveToNext());
        }
        return isiList;
    }



    public Integer deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(nama_db, null, null);
    }
}
