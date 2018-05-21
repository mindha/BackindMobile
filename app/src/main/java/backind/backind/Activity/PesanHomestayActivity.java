package backind.backind.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import backind.backind.R;
import backind.backind.Response.TransaksiResponse;
import backind.backind.Service.Api;
import backind.backind.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanHomestayActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText dateCheckIn, dateCheckOut;
    private Button orderHomestay;
    ImageButton btnMinus, btnPlus;
    TextView txtJumlahTiket, homestayname;
    int n;
    int id_homestay;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_homestay);

        try{
            id_homestay = getIntent().getIntExtra("id_homestay",0);
            name = getIntent().getStringExtra("name");

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

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        dateCheckIn = findViewById(R.id.checkin);
        dateCheckOut =  findViewById(R.id.checkout);
        orderHomestay = findViewById(R.id.pesanHomestay);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        homestayname = findViewById(R.id.homestayname);
        txtJumlahTiket = findViewById(R.id.jumlah);

        homestayname.setText(name);

        dateCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkIn();
            }
        });
        dateCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOut();
            }
        });

        orderHomestay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingHomestay();
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (n > 0) {
                    n--;
                    txtJumlahTiket.setText(String.valueOf(n));
                } else  {
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Isi Jumlah Tiket", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (n < 10) {
                    n++;
                    txtJumlahTiket.setText(String.valueOf(n));
                } else {
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Maksimal pembelian tiket hanya 10 Tiket", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }

    private void checkIn(){

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (1000 * 60 * 60));
                dateCheckIn.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void checkOut(){

        final Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateCheckOut.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public void bookingHomestay(){
        int id_tourism = 0;
        final String checkIn = dateCheckIn.getText().toString();
        final String checkOut = dateCheckOut.getText().toString();
        final int jumlah = Integer.parseInt(txtJumlahTiket.getText().toString());
        Log.d("Backindbug","Tes Parameter = " + "id_tourism = " + id_tourism+",\n " +
                "chekcin = "+ checkIn+", \n " +
                "checkout = " + checkOut+", \n " +
                "jumlah = " + jumlah+", \n" +
                "id_homestay = " + id_homestay);
        Api.getService().booking(id_tourism,id_homestay,checkIn, checkOut,"",jumlah).
                enqueue(new Callback<TransaksiResponse>() {
                    @Override
                    public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("Backindbug","BERHASIL");
                            Toast.makeText(PesanHomestayActivity.this, "Data anda berhasil disimpan", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(PesanHomestayActivity.this,DetailBayarHomestayActivity.class);
                            i.putExtra("nama_homestay",name);
                            i.putExtra("checkin",checkIn);
                            i.putExtra("checkout",checkOut);
                            i.putExtra("jumlah",jumlah);
                            int harga = response.body().getData().getTotalCost();
                            int id_booking = response.body().getData().getIdBooking();
                            i.putExtra("harga",harga);
                            i.putExtra("id_booking",id_booking);
                            startActivity(i);
                        }else{
                            Log.d("Backindbug","GA BERHASIL = " + Utils.getJsonfromUrl(response.errorBody()));
                            Log.d("Backindbug","GA BERHASIL = " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                            Log.d("Backindbug","GAGAL = " + t.getMessage());

                    }
                });
    }
}

