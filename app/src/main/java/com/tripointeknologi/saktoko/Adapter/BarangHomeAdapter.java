package com.tripointeknologi.saktoko.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.joooonho.SelectableRoundedImageView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tripointeknologi.saktoko.Activity.DetailActivity;
import com.tripointeknologi.saktoko.Models.MBarang;
import com.tripointeknologi.saktoko.R;
import com.tripointeknologi.saktoko.Utils.Database;
import com.tripointeknologi.saktoko.Utils.Libku;

import java.util.List;

public class BarangHomeAdapter extends RecyclerView.Adapter<BarangHomeAdapter.Holder> {
    Context ctx;
    List<MBarang> list;
    int row_index = -1;
    Libku libku;
    Database db;
    public BarangHomeAdapter(List<MBarang> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public BarangHomeAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        libku = new Libku(ctx);
        db = new Database(ctx);
        View v = LayoutInflater.from(ctx).inflate(R.layout.list_produk_home,parent,false);
        return new Holder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BarangHomeAdapter.Holder h, int i) {
        String id = list.get(i).getId_barang();
        int harga = list.get(i).getHarga();

        String asli = libku.convertRibuan(harga);
        int finalHarga = harga;


        Glide.with(ctx)
                .load(list.get(i).getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.ic_no_foto).error(R.drawable.ic_no_foto))
                .into(h.imFoto);

        h.tvNamaBarang.setText(list.get(i).getNama_barang());
        h.tvKategori.setText(list.get(i).getKategori());
        h.tvHarga.setText("Rp."+libku.convertRibuan(harga));
        h.tvStock.setText("Tersedia : 1000");

        h.btnDetail.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, DetailActivity.class);
            intent.putExtra( "id_barang",list.get(i).getId_barang()) ;
            intent.putExtra( "foto",list.get(i).getFoto()) ;

            intent.putExtra( "nama_barang",list.get(i).getNama_barang()) ;
            intent.putExtra( "kategori",list.get(i).getKategori()) ;
            intent.putExtra( "harga",list.get(i).getHarga());
            intent.putExtra( "satuan",list.get(i).getNama_barang());
            intent.putExtra( "berat",list.get(i).getBerat());


            ctx.startActivity(intent);
        });

        int finalHarga1 = finalHarga;
        h.btn_beli.setOnClickListener(view -> {
            int berat = list.get(i).getBerat();
            int qty = db.getQTY(list.get(i).getId_barang());
//            row_index = i;
//            if(qty == 0) {
//                db.insert(list.get(i).getId_product(), list.get(i).getNama_product(), list.get(i).getKategori(),
//                        list.get(i).getSub_kategori(), list.get(i).getFoto(), list.get(i).getFoto_thumb()
//                        , list.get(i).getHarga(),list.get(i).getStock(),berat,list.get(i).getSatuan(),1, finalHarga1
//                        ,list.get(i).getDiskon(),list.get(i).getDiskon_ongkir());
//            }else{
//                int fqty = qty +1;
//                int ftotal = fqty * finalHarga1;
//                int fberat = fqty * berat;
//                db.update(id,fberat,fqty,ftotal);
//            }
//            activity.setBadge();
            notifyItemChanged(i);
        });
        h.btn_tambah.setOnClickListener(view -> {
//            int berat = list.get(i).getBerat();
//            int qty = db.getQTY(list.get(i).getId_product());
//            int fqty = qty +1;
//            int ftotal = fqty * finalHarga1;
//            int fberat = fqty * berat;
//            db.update(id,fberat,fqty,ftotal);
//            activity.setBadge();
            notifyItemChanged(i);
        });
        h.btn_kurang.setOnClickListener(view -> {
//            int x = Integer.parseInt(h.jumlah.getText().toString());
//            int y = x - 1;
//            if (y <= 0) {
//                db.deleteById(id);
//            } else {
//                int berat = list.get(i).getBerat();
//                int ftotal = y * finalHarga1;
//                int fberat = y * berat;
//                db.update(id,fberat,y,ftotal);
//            }
//            activity.setBadge();
            notifyItemChanged(i);
        });

//        int x = db.getQTY(id);
//        if (x > 0) {
//            h.llbtnBeli.setVisibility(View.GONE);
//            h.llbtn.setVisibility(View.VISIBLE);
//            h.rljumlah.setVisibility(View.VISIBLE);
//            h.tvJumlah.setText(String.valueOf(x));
//            h.cvmenu.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.kuning));
//            h.fotoProduct.setBackgroundColor(ContextCompat.getColor(ctx, R.color.kuning));
//        } else {
//            h.llbtnBeli.setVisibility(View.VISIBLE);
//            h.llbtn.setVisibility(View.GONE);
//            h.rljumlah.setVisibility(View.GONE);
//            h.jumlah.setText(String.valueOf(0));
//            h.cvmenu.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.putih));
//            h.fotoProduct.setBackgroundColor(ContextCompat.getColor(ctx, R.color.putih));
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        SelectableRoundedImageView imFoto;
        TextView tvNamaBarang,tvHarga,tvKategori,tvStock,tvJumlah,tvHargaAsli;
        LinearLayout llbtn, llbtnBeli;
        CardView cvmenu;
        RelativeLayout rljumlah;
        MaterialRippleLayout btn_beli;
        MaterialRippleLayout btnDetail,btn_tambah, btn_kurang;
        public Holder(@NonNull View v) {
            super(v);
            imFoto = v.findViewById(R.id.imFoto);
            tvNamaBarang = v.findViewById(R.id.tvNamaBarang);
            tvHarga = v.findViewById(R.id.tvHarga);
            tvKategori = v.findViewById(R.id.tvKategori);
            tvStock = v.findViewById(R.id.tvStock);
            tvJumlah = v.findViewById(R.id.tvJumlah);
            tvHargaAsli = v.findViewById(R.id.tvHargaAsli);
            llbtn = v.findViewById(R.id.llbtn);
            llbtnBeli = v.findViewById(R.id.llbtnBeli);
            cvmenu = v.findViewById(R.id.cvMenu);
            rljumlah = v.findViewById(R.id.rlJumlah);
            btn_beli = v.findViewById(R.id.btn_beli);
            btn_kurang = v.findViewById(R.id.btn_kurang);
            btn_tambah = v.findViewById(R.id.btn_tambah);
            btnDetail = v.findViewById(R.id.btnDetail);

        }
    }
}
