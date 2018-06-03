package backind.backind.Activity;

import android.app.ActionBar;
//import android.app.DatePickerDialog;
import android.app.Activity;
import android.app.Dialog;
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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

public class BeliTiketActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView txtJumlahTiket,tourismname;
    ImageButton btnMinus, btnPlus;
    Button btnBeli;
    EditText buyDate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    int n;
    int id_tourism, id_menu, hargaSearch, harga_tourism;
    String name;

    private int myear;
    private int mmonth;
    private int mday;

    static final int DATE_DIALOG_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli_tiket);

        try{
            id_menu = getIntent().getIntExtra("id_menu",1);
            hargaSearch = getIntent().getIntExtra("harga_search",5000000);
            String harga = getIntent().getStringExtra("harga_tourism");
            harga_tourism = Integer.parseInt(harga);
        }catch (Exception e){

        }

        try{
            id_tourism = getIntent().getIntExtra("id_tourism",0);
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
        txtJumlahTiket = findViewById(R.id.jumlah);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnBeli = findViewById(R.id.belitiket);
        buyDate = findViewById(R.id.tanggal);
        tourismname = findViewById(R.id.ticketname);

        tourismname.setText(name);
        showDialog();



        buyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
                datePickerDialog.show((BeliTiketActivity.this).getFragmentManager(), "Datepickerdialog");

            }
        });

//        setCurrentDateOnView();
//        addListenerOnButton();

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (n > 1) {
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

        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingTourism();
            }
        });
    }

    public void showDialog(){

        Calendar now = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(
                BeliTiketActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setMinDate(now);
//        Calendar newCalendar = Calendar.getInstance();
//
//        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
////                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
////                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (1000 * 60 * 60));
//                buyDate.setText(dateFormatter.format(newDate.getTime()));
//            }
//
//        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//
//        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        buyDate.setText(date);
    }

    // display current date
    public void setCurrentDateOnView() {

        buyDate = findViewById(R.id.tanggal);

        final Calendar c = Calendar.getInstance();
        myear = c.get(Calendar.YEAR);
        mmonth = c.get(Calendar.MONTH);
        mday = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        buyDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(myear).append("-").append(mmonth + 1).append("-").append(mday));
    }







    public void bookingTourism(){
        int id_homestay = 0;
        final String dateTicket = buyDate.getText().toString();
        final int jumlah = Integer.parseInt(txtJumlahTiket.getText().toString());
        Log.d("Backindbug","Tes Parameter = " + "id_homestay = " + id_homestay+",\n " +
                "dateticket = "+ dateTicket+", \n " +
                "jumlah = " + jumlah+", \n" +
                "id_tourism = " + id_tourism);

        BusinessData tourism = new BusinessData();

        BusinessDetails tourism_detail = new BusinessDetails();
        Transaksi pesanTourism = new Transaksi();
        pesanTourism.setIdTourism(""+id_tourism);
        pesanTourism.setCheckinTourism(dateTicket);
        pesanTourism.setTotalTicket(""+jumlah);
        tourism_detail.setBusinessPrice(""+harga_tourism);
        tourism.setBusinessDetails(tourism_detail);
        pesanTourism.setTourism(tourism);

        Hawk.put("PesananTourism",pesanTourism);
        Intent i = new Intent(BeliTiketActivity.this,DetailBayarTiketActivity.class);
        i.putExtra("name_tourism",name);
        i.putExtra("date",dateTicket);
        i.putExtra("jumlah",jumlah);
        i.putExtra("id_bisnis",id_tourism);
        i.putExtra("id_menu",id_menu);
        i.putExtra("harga_search",hargaSearch);
        int harga = harga_tourism * jumlah;
        int id_booking = 0;
        i.putExtra("harga",harga);
        i.putExtra("id_booking",id_booking);
        Log.d("Backindbug","TESSSS TOURISM = " + Utils.getJsonfromUrl(pesanTourism));
        startActivity(i);
        /*Api.getService().booking(id_tourism,id_homestay,"", "",dateTicket,jumlah).
                enqueue(new Callback<TransaksiResponse>() {
                    @Override
                    public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("Backindbug","BERHASIL");
                            Toast.makeText(BeliTiketActivity.this, "Data anda berhasil disimpan", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(BeliTiketActivity.this,DetailBayarTiketActivity.class);
                            i.putExtra("name_tourism",name);
                            i.putExtra("date",dateTicket);
                            i.putExtra("jumlah",jumlah);
                            i.putExtra("id_bisnis",id_tourism);
                            i.putExtra("id_menu",id_menu);
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


}
