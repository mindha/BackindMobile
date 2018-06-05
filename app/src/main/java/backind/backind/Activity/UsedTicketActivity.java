package backind.backind.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import backind.backind.R;
import backind.backind.Response.ReviewResponse;
import backind.backind.Service.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsedTicketActivity extends AppCompatActivity {
    TextView name_user,name_tourism,name_homestay,totals,jumlah_orang,date_tourism,date_homestay,id_booking, txt_homestay,txt_tourism,txt_status;
    Button review_homestay,review_tourism;
    String duedate, tagihan, code, homestay, tourism, check_in, check_out, date_tiket,user, jumlah;
    int status_pay, id_transaksi;
    int id_detail_bisnis, id_user;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_used_ticket);

        try{
            id_transaksi = getIntent().getIntExtra("id_transaksi", 0);
            duedate = getIntent().getStringExtra("duedate");
            tagihan = getIntent().getStringExtra("jumlah_tagihan");
            code = getIntent().getStringExtra("eticket");
            status_pay = getIntent().getIntExtra("status",0);
            id_detail_bisnis = getIntent().getIntExtra("id_business",0);
            id_user = getIntent().getIntExtra("id_user",0);
            jumlah = getIntent().getStringExtra("jumlah_orang");
            homestay = getIntent().getStringExtra("name_homestay");
            user = getIntent().getStringExtra("name_user");
            tourism = getIntent().getStringExtra("name_tourism");
            check_in = getIntent().getStringExtra("check_in");
            check_out = getIntent().getStringExtra("check_out");
            date_tiket = getIntent().getStringExtra("date_tiket");

        }catch (Exception e){

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        id_booking = findViewById(R.id.id_booking);
        name_user = findViewById(R.id.name_user);
        date_tourism = findViewById(R.id.date_tourism);
        date_homestay = findViewById(R.id.date_homestay);
        name_homestay = findViewById(R.id.name_homestay);
        name_tourism = findViewById(R.id.name_tourism);
        name_homestay = findViewById(R.id.name_homestay);
        totals = findViewById(R.id.totals);
        jumlah_orang = findViewById(R.id.jumlah_orang);
        txt_homestay = findViewById(R.id.txt_homestay);
        txt_tourism = findViewById(R.id.txt_tourism);
        txt_status = findViewById(R.id.status);
        review_homestay = findViewById(R.id.review_homestay);
        review_tourism = findViewById(R.id.review_tourism);

        String n = String.valueOf(jumlah);
        txt_status.setText("Used");
        txt_status.setTextColor(ContextCompat.getColor(this,R.color.colorPink));
        name_user.setText(user);
        id_booking.setText(code);
        jumlah_orang.setText(jumlah);
//        Toast.makeText(this, "homestay "+homestay, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "tourism "+tourism, Toast.LENGTH_SHORT).show();

        jumlah_orang.setText(n);
        totals.setText("Rp "+tagihan+",-");

        if (tourism != null){
            name_tourism.setText(tourism);
            date_tourism.setText(date_tiket);
            review_tourism.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    messageDialog();
                }
            });
        } else {
            name_tourism.setVisibility(View.GONE);
            txt_tourism.setVisibility(View.GONE);
            date_tourism.setVisibility(View.GONE);
            review_tourism.setVisibility(View.GONE);
        }

        if (homestay != null){
            name_homestay.setText(homestay);
            date_homestay.setText(check_in+" sampai "+check_out);
            review_homestay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    messageDialog();
                }
            });
        } else {
            name_homestay.setVisibility(View.GONE);
            txt_homestay.setVisibility(View.GONE);
            date_homestay.setVisibility(View.GONE);
            review_homestay.setVisibility(View.GONE);
        }


    }

    private void messageDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_review);
        final RatingBar valueRating = dialog.findViewById(R.id.value_ratting);
        final EditText valueReview = dialog.findViewById(R.id.value_review);
        Button btnReview = dialog.findViewById(R.id.btn_review);

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intValueRating = (int) valueRating.getRating();
                String review = valueReview.getText().toString();
                addReview(intValueRating, review);

            }
        });

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void addReview(int ratting, String review) {
        Api.getService().postReview("postReview", id_detail_bisnis, review, " ", ratting, id_user).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(UsedTicketActivity.this, "Berhasil add review", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(UsedTicketActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
