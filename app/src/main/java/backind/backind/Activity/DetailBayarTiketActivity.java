package backind.backind.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import backind.backind.R;
import backind.backind.Response.UpdateCostResponse;
import backind.backind.Service.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailBayarTiketActivity extends AppCompatActivity {

    private Button btnBayar, btnFindHomestay, btnNope, btnYes;
    private Dialog dialog;
    private ImageView close;
    TextView nama, tanggal, jumlah, harga;
    String name, date;
    int value, price, id_booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bayar_tiket);

        try{
            name = getIntent().getStringExtra("name_tourism");
            date = getIntent().getStringExtra("date");
            value = getIntent().getIntExtra("jumlah",0);
            price = getIntent().getIntExtra("harga",0);
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
        btnFindHomestay = findViewById(R.id.cari_homestay);
        nama = findViewById(R.id.namatiket);
        tanggal = findViewById(R.id.tanggal);
        jumlah = findViewById(R.id.tiket);
        harga = findViewById(R.id.harga);

        try{
            nama.setText(name);
            tanggal.setText(date);
            jumlah.setText(""+value);
            harga.setText("Rp "+price+",-");
        }catch (Exception e){
            e.printStackTrace();
        }

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCost();
                Intent i = new Intent(DetailBayarTiketActivity.this,PaymentDeadlineActivity.class);
                i.putExtra("id_booking",id_booking);
                startActivity(i);
//                startActivity(new Intent(DetailBayarTiketActivity.this, PaymentDeadlineActivity.class));
            }
        });

        btnFindHomestay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageDialog();
            }
        });
    }

    private void messageDialog(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm_find_homestay);
        btnNope = dialog.findViewById(R.id.btnNo);
        btnYes = dialog.findViewById(R.id.btnYes);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        btnNope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailBayarTiketActivity.this, NearbyActivity.class));
            }
        });

        dialog.show();
    }

    public void updateCost(){
        Api.getService().getUpdateCost("postUpdateCost/"+id_booking,price).enqueue(new Callback<UpdateCostResponse>() {
            @Override
            public void onResponse(Call<UpdateCostResponse> call, Response<UpdateCostResponse> response) {
                if(response.isSuccessful()){
                    Log.d("Backindbug","id booking="+id_booking);
                    Log.d("Backindbug","harga="+price);
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
