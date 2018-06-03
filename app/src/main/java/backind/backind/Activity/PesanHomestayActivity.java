package backind.backind.Activity;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import backind.backind.Model.BusinessData;
import backind.backind.Model.BusinessDetails;
import backind.backind.Model.Transaksi;
import backind.backind.R;
import backind.backind.Response.TransaksiResponse;
import backind.backind.Service.Api;
import backind.backind.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanHomestayActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText dateCheckIn, dateCheckOut;
    private Button orderHomestay;
    ImageButton btnMinus, btnPlus;
    TextView txtJumlahTiket, homestayname;
    int n;
    int id_homestay, hargaSearch, id_menu, harga_homestay;
    String name;
    int dptipyChoose = 0;
    int days =0;
    private Calendar checkinChoosen, checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_homestay);

        try {
            hargaSearch = getIntent().getIntExtra("harga_search", 5000000);
            id_menu = getIntent().getIntExtra("id_menu", 2);
            String harga = getIntent().getStringExtra("harga_homestay");
            harga_homestay = Integer.parseInt(harga);
        } catch (Exception e) {

        }

        try {
            id_homestay = getIntent().getIntExtra("id_homestay", 0);
            name = getIntent().getStringExtra("name");

        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //status bar
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorWhite));

        //action bar
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffffff"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setElevation(0);

        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorHitam), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);


        dateCheckIn = findViewById(R.id.checkin);
        dateCheckOut = findViewById(R.id.checkout);
        orderHomestay = findViewById(R.id.pesanHomestay);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        homestayname = findViewById(R.id.homestayname);
        txtJumlahTiket = findViewById(R.id.jumlah);

        homestayname.setText(name);

        checkinChoosen = Calendar.getInstance();
        showDialogcheckin();
        showDialogcheckout();

        dateCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dptipyChoose = 1;
                showDialogcheckin();
                datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
                datePickerDialog.show((PesanHomestayActivity.this).getFragmentManager(), "Datepickerdialog");

            }
        });
        dateCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dptipyChoose = 2;
                showDialogcheckout();
                datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
                datePickerDialog.show((PesanHomestayActivity.this).getFragmentManager(), "Datepickerdialog");

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
                if (n > 1) {
                    n--;
                    txtJumlahTiket.setText(String.valueOf(n));
                } else {
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

    private void showDialogcheckin() {

        Calendar checkin = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(
                PesanHomestayActivity.this,
                checkin.get(Calendar.YEAR),
                checkin.get(Calendar.MONTH),
                checkin.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setMinDate(checkin);
    }

    private void showDialogcheckout() {

        checkout = Calendar.getInstance();
        checkout.set(Calendar.DAY_OF_MONTH, checkinChoosen.get(Calendar.DAY_OF_MONTH) + 1);
//        Toast.makeText(this, "Checkin" + checkinChoosen.get(Calendar.DAY_OF_MONTH) + 1, Toast.LENGTH_SHORT).show();
        datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                PesanHomestayActivity.this,
                checkout.get(Calendar.YEAR),
                checkout.get(Calendar.MONTH),
                checkout.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setMinDate(checkout);
    }

    public void bookingHomestay() {
        int id_tourism = 0;
        final String checkIn = dateCheckIn.getText().toString();
        final String checkOut = dateCheckOut.getText().toString();
        final int jumlah = Integer.parseInt(txtJumlahTiket.getText().toString());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date d1 = format.parse(checkIn);
            Date d2 = format.parse(checkOut);
            Log.d("Backindbug", "Days "+getDifferenceDays(d1,d2));
            days = getDifferenceDays(d1,d2);
            Toast.makeText(this, "ini harinya"+days, Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        Log.d("Backindbug", "Tes Parameter = " + "id_tourism = " + id_tourism + ",\n " +
                "chekcin = " + checkIn + ", \n " +
                "checkout = " + checkOut + ", \n " +
                "jumlah = " + jumlah + ", \n" +
                "id_homestay = " + id_homestay);

        BusinessData homestay = new BusinessData();

        BusinessDetails homestay_detail = new BusinessDetails();
        Transaksi pesanHomestay = new Transaksi();
        pesanHomestay.setIdHomestay("" + id_homestay);
        pesanHomestay.setCheckin(checkIn);
        pesanHomestay.setCheckout(checkOut);
        pesanHomestay.setTotalTicket("" + jumlah);
        homestay_detail.setBusinessPrice("" + harga_homestay);
        homestay.setBusinessDetails(homestay_detail);
        pesanHomestay.setHomestay(homestay);


        Hawk.put("PesananHomestay", pesanHomestay);

        Intent i = new Intent(PesanHomestayActivity.this, DetailBayarHomestayActivity.class);
        i.putExtra("nama_homestay", name);
        i.putExtra("checkin", checkIn);
        i.putExtra("checkout", checkOut);
        i.putExtra("jumlah", jumlah);
        i.putExtra("id_menu", id_menu);
        i.putExtra("id_bisnis", id_homestay);
        i.putExtra("harga_search", hargaSearch);
        int harga = harga_homestay * (days-1);
        int id_booking = 0;
        i.putExtra("harga", harga);
        i.putExtra("id_booking", id_booking);

        Log.d("Backindbug", "TESSSS HOMESTAY = " + Utils.getJsonfromUrl(pesanHomestay));
        startActivity(i);
        /*Api.getService().booking(id_tourism,id_homestay,checkIn, checkOut,"",jumlah).
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
                            i.putExtra("id_menu",id_menu);
                            i.putExtra("id_bisnis",id_homestay);
                            i.putExtra("harga_search",hargaSearch);
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
                });*/
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        if (dptipyChoose == 1) {
            dateCheckIn.setText(date);
            checkinChoosen.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            checkinChoosen.set(Calendar.MONTH, monthOfYear + 1);
            checkinChoosen.set(Calendar.YEAR, year);
            showDialogcheckout();
        } else if (dptipyChoose == 2) {
            dateCheckOut.setText(date);
        }


    }

    private int getDifferenceDays(Date d1, Date d2) {
        int daysdiff=0;
        long diff = d2.getTime() - d1.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000)+1;
        daysdiff = (int) diffDays;
        return daysdiff;
    }


}
