package backind.backind.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import backind.backind.Model.PaymentReceipt;
import backind.backind.R;
import backind.backind.Service.Api;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuktiTransferActivity extends AppCompatActivity {

    String duedate, tagihan, code, id_booking;
    int status_pay, id_transaksi;
    String statusbayar;
    TextView order_code, total_tagihan, due_date, status_pembayaran;
    Button done, choose;
    ImageView imgPreview;

    private static final int REQUEST_CAMERA = 0, SELECT_FILE = 1;    private Uri imgPath;
    private String mediaPath = null;
    private File imgFile = null;
    private Bitmap imgBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukti_transfer);

        try{
            id_transaksi = getIntent().getIntExtra("id_transaksi", 0);
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
        imgPreview = findViewById(R.id.img_prev);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadBukti();
            }
        });


        try{
            order_code.setText(code);
            String tagih = "Rp "+tagihan+",-";
            total_tagihan.setText(tagih);
            due_date.setText(duedate);
            if(status_pay == 1){
                statusbayar = "Paid";
                status_pembayaran.setTextColor(ContextCompat.getColor(this,R.color.colorGreen));
            }else if (status_pay == 2){
                statusbayar = "Waiting Payment";
                status_pembayaran.setTextColor(ContextCompat.getColor(this,R.color.colorOrange));
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
    private void selectImage() {
        final CharSequence[] items = {"Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(BuktiTransferActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Choose from Library")) {
                    galeryPicker();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void uploadBukti(){
        String fileName = "";
        RequestBody requestBody = RequestBody.create(MediaType.parse("/*"), "");
        MultipartBody.Part body = MultipartBody.Part.createFormData("bukti_transfer", null, requestBody);

        if (imgFile != null){
            fileName = imgFile.getName();
            requestBody = RequestBody.create(MediaType.parse("/*"), imgFile);
            body = MultipartBody.Part.createFormData("bukti_transfer", fileName, requestBody);
        }

        Api.getService().paymentReceipt("postuploadBukti", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_transaksi)), body )
                .enqueue(new Callback<PaymentReceipt>() {
                    @Override
                    public void onResponse(Call<PaymentReceipt> call, Response<PaymentReceipt> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(BuktiTransferActivity.this, "Berhasil upload", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BuktiTransferActivity.this, MenuActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("action", "history");
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onFailure(Call<PaymentReceipt> call, Throwable t) {
                        Toast.makeText(BuktiTransferActivity.this, "Gagal upload bukti", Toast.LENGTH_SHORT).show();
                    }
                });

        try {

        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    private void galeryPicker() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar "), SELECT_FILE);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mediaPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private File persistImage(Bitmap bitmap, String name) {
        File filesDir = getApplicationContext().getFilesDir();
        File outputFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("error", "Error writing bitmap", e);
        }

        return outputFile;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        if (requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Toast.makeText(this, "Masuk Galery", Toast.LENGTH_SHORT).show();
            try {
                imgPath = data.getData();
                imgBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgPath);

                imgFile = persistImage(imgBitmap, name);
//                imgFile = compressFile(imgFile);

                imgPreview.setImageBitmap(imgBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
