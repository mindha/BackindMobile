package backind.backind.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;

import backind.backind.Model.BusinessDetails;
import backind.backind.Model.Transaksi;
import backind.backind.R;
import backind.backind.Response.NearbyResponse;
import backind.backind.Response.TransaksiResponse;
import backind.backind.Response.UpdateCostResponse;
import backind.backind.Service.Api;
import backind.backind.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBayarHomestayActivity extends AppCompatActivity {

    private Button btnBayar, btnCariTiket, btnNope, btnYes;
    private Dialog dialog;
    private ImageView close;
    String nama_homestay, checkin, checkout;
    TextView nama, masuk, keluar, n, price;
    int jumlah, harga,id_booking, id_menu, hargaSearch, id_bisnis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bayar_homestay);

        try{
            id_bisnis = getIntent().getIntExtra("id_bisnis",0);
            id_menu = getIntent().getIntExtra("id_menu",2);
            hargaSearch = getIntent().getIntExtra("harga_search",5000000);
        }catch (Exception e){

        }

        try{
            nama_homestay = getIntent().getStringExtra("nama_homestay");
            checkin = getIntent().getStringExtra("checkin");
            checkout = getIntent().getStringExtra("checkout");
            jumlah = getIntent().getIntExtra("jumlah",0);
            harga = getIntent().getIntExtra("harga",0);
            id_booking = getIntent().getIntExtra("id_booking",0);

        }catch (Exception e){
            Log.d("Backindbug","ERRR = " + e.getMessage());
            e.printStackTrace();
        }

        //status bar
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorWhite));

        //action bar
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffffff"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setElevation(0);

        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorHitam), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        btnBayar = findViewById(R.id.bayar);
        btnCariTiket =findViewById(R.id.cari_tiket);
        nama = findViewById(R.id.namahomestay);
        masuk = findViewById(R.id.dateCheckIn);
        keluar = findViewById(R.id.dateCheckOut);
        n = findViewById(R.id.people);
        price = findViewById(R.id.harga);

        try{
            if(Hawk.get("PesananTourism") != null){
                btnCariTiket.setVisibility(View.GONE);
            }else{
                btnCariTiket.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){

        }

        try{
            nama.setText(nama_homestay);
            masuk.setText(checkin);
            keluar.setText(checkout);
            n.setText(""+jumlah);
            price.setText("Rp "+harga+",-");
        }catch (Exception e){
            e.printStackTrace();
        }

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updateCost();
                order();
                //startActivity(new Intent(DetailBayarHomestayActivity.this, PaymentDeadlineActivity.class));
            }
        });

        btnCariTiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageDialog();
            }
        });

    }
    private void messageDialog(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm_find_ticket);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        btnNope = dialog.findViewById(R.id.btnNo);
        btnYes = dialog.findViewById(R.id.btnYes);


        btnNope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBayarHomestayActivity.this, NearbyActivity.class);
                i.putExtra("id_menu",id_menu);
                i.putExtra("id_bisnis",id_bisnis);
                i.putExtra("harga_search", hargaSearch);
                startActivity(i);
            }
        });

        dialog.show();
    }

    public void order(){
        try{
            Log.d("Backindbug","Mau bayar di Homestay");
            Transaksi pesanan = null;
            int id_tourism = 0;
            String checkinTourism = "";
            int total_harga_semua = harga;
            if(Hawk.get("PesananTourism") != null){
                pesanan = Hawk.get("PesananTourism");
                id_tourism = Integer.parseInt(pesanan.getIdTourism());
                checkinTourism = pesanan.getCheckinTourism().toString();
                total_harga_semua = harga + (Integer.parseInt(pesanan.getTourism().getBusinessPrice()) * jumlah);
            }else {

            }
            final int finalTotal_harga_semua = total_harga_semua;
            Log.d("Backindbug","DAPET DARI HOMESTAY = " + Utils.getJsonfromUrl(pesanan));
        Api.getService().booking(id_tourism,id_bisnis,checkin, checkout,checkinTourism,jumlah).
                enqueue(new Callback<TransaksiResponse>() {
                    @Override
                    public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                        if (response.isSuccessful()){
                            updateCost(response.body().getData().getIdBooking(), finalTotal_harga_semua);
                        }
                    }

                    @Override
                    public void onFailure(Call<TransaksiResponse> call, Throwable t) {

                    }
                });
        }catch (Exception e){

        }

    }
    public void updateCost(final int id_booking, final int harga){
        Api.getService().getUpdateCost("postUpdateCost/"+id_booking,harga).enqueue(new Callback<UpdateCostResponse>() {
            @Override
            public void onResponse(Call<UpdateCostResponse> call, Response<UpdateCostResponse> response) {
                if(response.isSuccessful()){
                    Hawk.put("PesananTourism",null);
                    Hawk.put("PesananHomestay",null);
                    Log.d("Backindbug","id booking="+id_booking);
                    Log.d("Backindbug","harga="+harga);
                    Intent i = new Intent(DetailBayarHomestayActivity.this,PaymentDeadlineActivity.class);
                    i.putExtra("id_booking",id_booking);
                    startActivity(i);
                }else{
                    Log.d("Backindbug","TIDAK SUKSES");
                }
            }

            @Override
            public void onFailure(Call<UpdateCostResponse> call, Throwable t) {
                    Log.d("Backindbug","GAGAL");

            }
        });
    }

}
