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

import backind.backind.R;
import backind.backind.Response.UpdateCostResponse;
import backind.backind.Service.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBayarHomestayActivity extends AppCompatActivity {

    private Button btnBayar, btnCariTiket, btnNope, btnYes;
    private Dialog dialog;
    private ImageView close;
    String nama_homestay, checkin, checkout;
    TextView nama, masuk, keluar, n, price;
    int jumlah, harga,id_booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bayar_homestay);

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
                updateCost();
                Intent i = new Intent(DetailBayarHomestayActivity.this,PaymentDeadlineActivity.class);
                i.putExtra("id_booking",id_booking);
                startActivity(i);
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
                startActivity(new Intent(DetailBayarHomestayActivity.this, NearbyActivity.class));
            }
        });

        dialog.show();
    }

    public void updateCost(){
        Api.getService().getUpdateCost("postUpdateCost/"+id_booking,harga).enqueue(new Callback<UpdateCostResponse>() {
            @Override
            public void onResponse(Call<UpdateCostResponse> call, Response<UpdateCostResponse> response) {
                if(response.isSuccessful()){
                    Log.d("Backindbug","id booking="+id_booking);
                    Log.d("Backindbug","harga="+harga);
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
