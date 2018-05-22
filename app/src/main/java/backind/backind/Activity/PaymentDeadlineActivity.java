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
import android.widget.Toast;

import org.w3c.dom.Text;

import backind.backind.R;
import backind.backind.Response.InvoiceResponse;
import backind.backind.Response.TransaksiResponse;
import backind.backind.Service.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDeadlineActivity extends AppCompatActivity {

    private Button bayar, batal;
    private TextView due_date, total_tagihan, order_code;
    private TextView username, email_user, no_telp;
    private TextView tourism_name, tourism_price, date_ticket, number_ticket;
    private TextView homestay_name, homestay_price, date_booking, number_people;
    private TextView pay_code, total_harga;
    int id_booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_deadline);

        try{
            id_booking = getIntent().getIntExtra("id_booking",0);

        }catch (Exception e){
            e.printStackTrace();
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

        due_date = findViewById(R.id.due_date);
        total_tagihan = findViewById(R.id.total_tagihan);
        order_code = findViewById(R.id.order_code);

        username = findViewById(R.id.username);
        email_user = findViewById(R.id.email_user);
        no_telp = findViewById(R.id.no_telp);

        tourism_name = findViewById(R.id.tourism_name);
        tourism_price = findViewById(R.id.tourism_price);
        date_ticket = findViewById(R.id.date_ticket);
        number_ticket = findViewById(R.id.number_ticket);

        homestay_name = findViewById(R.id.homestay_name);
        homestay_price = findViewById(R.id.homestay_price);
        date_booking = findViewById(R.id.date_booking);
        number_people = findViewById(R.id.number_people);

        pay_code = findViewById(R.id.pay_code);
        total_harga = findViewById(R.id.total_harga);


        Log.d("Backindbug","id booking = "+ id_booking);

        bayar = findViewById(R.id.pay);
        batal = findViewById(R.id.batal);

        getDataTransaction();



    }

    public void getDataTransaction(){
        Api.getService().getTransaction("getInvoice/"+id_booking).enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if(response.isSuccessful()){
                    Log.d("Backindbug","SUKSES TAMPILIN");
                    Log.d("Backindbug","id booking = "+ id_booking);
                    int kode = 100;
                    final String batas_tanggal = response.body().getData().get(0).getDuedate();
                    int code= kode+id_booking;
                    final String total_code = String.valueOf(response.body().getData().get(0).getTotalCost());
                    final String code_order = "#BACKIND"+code;
                    String nama = response.body().getData().get(0).getUser().getName();
                    String email = response.body().getData().get(0).getUser().getEmail();
                    String telp = response.body().getData().get(0).getUser().getPhoneNumber();

                    String tourism="", value="-", price_tourism="", total_all_tourism="", date_tourism="";
                    int jumlah = 0, harga_tourism=0, total_tourism=0;
                    //Tourism
                    value = String.valueOf(response.body().getData().get(0).getTotalTicket());
                    jumlah= Integer.valueOf(value);

                    try{
                        Log.d("Backindbug","try Tourism");
                        tourism = response.body().getData().get(0).getTourism().getBusinessName();
                        price_tourism = response.body().getData().get(0).getTourism().getBusinessPrice();
                        harga_tourism = Integer.valueOf(price_tourism);
                        total_tourism = harga_tourism*jumlah;
                        total_all_tourism = String.valueOf(total_tourism);
                        date_tourism = String.valueOf(response.body().getData().get(0).getCheckinTourism());
                    }catch (Exception e){
                        Log.d("Backindbug","catch Tourism");
                    }

                    //homestay
                    String name_homestay="", price_homestay="", total_all_homestay="", checkin="", checkout="";
                    int harga_homestay=0, total_homestay=0;
                    try{
                        Log.d("Backindbug","try Homestay");
                        name_homestay = response.body().getData().get(0).getHomestay().getBusinessName();
                        Log.d("Backindbug","ini nama homestay"+name_homestay);
                        price_homestay = String.valueOf(response.body().getData().get(0).getHomestay().getBusinessPrice());
                        harga_homestay = Integer.valueOf(price_homestay);
                        Log.d("Backindbug","ini harga homestay"+harga_homestay);
                        total_homestay = jumlah *harga_homestay;
                        total_all_homestay = String.valueOf(total_homestay);
                        Log.d("Backindbug","ini total homestay"+total_all_homestay);
                        checkin = String.valueOf(response.body().getData().get(0).getCheckin());
                        checkout = String.valueOf(response.body().getData().get(0).getCheckout());

                    }catch (Exception e){
                        Log.d("Backindbug","catch Homestay");

                    }

                    String day_homestay = checkin+ " sampai " + checkout;
                    String code_pay = String.valueOf(code);
                    int total_semua = total_homestay + total_tourism +code;
                    String total_all = String.valueOf(total_semua);

                    due_date.setText(batas_tanggal);
                    total_tagihan.setText(total_code);
                    order_code.setText(code_order);

                    username.setText(nama);
                    email_user.setText(email);
                    no_telp.setText(telp);

                    try{
                        if(response.body().getData().get(0).getTourism() == null){
                            tourism_name.setVisibility(View.GONE);
                            tourism_price.setVisibility(View.GONE);
                            date_ticket.setVisibility(View.GONE);
                            number_ticket.setVisibility(View.GONE);

                            homestay_name.setVisibility(View.VISIBLE);
                            homestay_price.setVisibility(View.VISIBLE);
                            date_booking.setVisibility(View.VISIBLE);
                            number_people.setVisibility(View.VISIBLE);

                        }else if(response.body().getData().get(0).getHomestay() == null){
                            tourism_name.setVisibility(View.VISIBLE);
                            tourism_price.setVisibility(View.VISIBLE);
                            date_ticket.setVisibility(View.VISIBLE);
                            number_ticket.setVisibility(View.VISIBLE);

                            homestay_name.setVisibility(View.GONE);
                            homestay_price.setVisibility(View.GONE);
                            date_booking.setVisibility(View.GONE);
                            number_people.setVisibility(View.GONE);
                        }
                    }catch (Exception e){

                    }

                    try{
                        tourism_name.setText(tourism);
                        tourism_price.setText(total_all_tourism);
                        date_ticket.setText(date_tourism);
                        number_ticket.setText(value);
                    }catch (Exception e){

                    }

                    try{
                        homestay_name.setText(name_homestay);
                        homestay_price.setText(total_all_homestay);
                        date_booking.setText(day_homestay);
                        number_people.setText(value);
                    }catch (Exception e){

                    }

                    pay_code.setText(code_pay);
                    total_harga.setText(total_code);

                    bayar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(PaymentDeadlineActivity.this, BuktiTransferActivity.class);
                            i.putExtra("eticket",code_order);
                            i.putExtra("jumlah_tagihan",total_code);
                            i.putExtra("duedate",batas_tanggal);
                            i.putExtra("status",2);
                            startActivity(i);
                        }
                    });

                }else{
                    Log.d("Backindbug","TIDAK TAMPIL BRE");
                    Toast.makeText(PaymentDeadlineActivity.this,"Data tidak tampil",Toast.LENGTH_LONG).show();
                }




            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                    Log.d("Backindbug","TIDAK TAMPIL = " + t.getMessage());

            }
        });
    }



}
