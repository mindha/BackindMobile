package backind.backind.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import backind.backind.R;

public class PaidTicketActivity extends AppCompatActivity {

    TextView name_user,name_tourism,name_homestay,totals,jumlah_orang,date_tourism,date_homestay,id_booking, txt_homestay,txt_tourism;
    String duedate, tagihan, code, homestay, tourism, check_in, check_out, date_tiket,user, jumlah;
    int status_pay, id_transaksi;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_ticket);

        try{
            id_transaksi = getIntent().getIntExtra("id_transaksi", 0);
            duedate = getIntent().getStringExtra("duedate");
            tagihan = getIntent().getStringExtra("jumlah_tagihan");
            code = getIntent().getStringExtra("eticket");
            status_pay = getIntent().getIntExtra("status",0);
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

        String n = String.valueOf(jumlah);
        name_user.setText(user);
        id_booking.setText(code);
        jumlah_orang.setText(jumlah);
        Toast.makeText(this, "homestay "+homestay, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "tourism "+tourism, Toast.LENGTH_SHORT).show();

        jumlah_orang.setText(n);
        totals.setText("Rp "+tagihan+",-");

        if (tourism != null){
            name_tourism.setText(tourism);
            date_tourism.setText(date_tiket);
        } else {
            name_tourism.setVisibility(View.GONE);
            txt_tourism.setVisibility(View.GONE);
            date_tourism.setVisibility(View.GONE);
        }

        if (homestay != null){
            name_homestay.setText(homestay);
            date_homestay.setText(check_in+" sampai "+check_out);
        } else {
            name_homestay.setVisibility(View.GONE);
            txt_homestay.setVisibility(View.GONE);
            date_homestay.setVisibility(View.GONE);
        }


    }
}
