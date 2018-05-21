package backind.backind.Activity;

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
import android.widget.TextView;

import backind.backind.R;

public class BuktiTransferActivity extends AppCompatActivity {

    String duedate, tagihan, code;
            int status_pay;
            String statusbayar;
    TextView order_code, total_tagihan, due_date, status_pembayaran;
    Button done, choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukti_transfer);

        try{
            duedate = getIntent().getStringExtra("duedate");
            tagihan = getIntent().getStringExtra("jumlah_tagihan");
            code = getIntent().getStringExtra("eticket");
            status_pay = getIntent().getIntExtra("status",0);
        }catch (Exception e){

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


        order_code = findViewById(R.id.order_code);
        total_tagihan = findViewById(R.id.total_tagihan);
        due_date = findViewById(R.id.due_date);
        status_pembayaran = findViewById(R.id.status);
        done = findViewById(R.id.done);
        choose = findViewById(R.id.choose);

        try{
            order_code.setText(code);
            total_tagihan.setText(tagihan);
            due_date.setText(duedate);
            if(status_pay == 1){
                statusbayar = "Paid";
            }else if (status_pay == 2){
                statusbayar = "Waiting Payment";
            }
            status_pembayaran.setText(statusbayar);

            Log.d("Backindbug","oderdercode"+code);
            Log.d("Backindbug","oderdercode"+tagihan);
            Log.d("Backindbug","oderdercode"+duedate);
            Log.d("Backindbug","Status"+statusbayar);
        }catch (Exception e){
            Log.d("Backindbug","TIDAK TAMPIL");
        }

    }
}
