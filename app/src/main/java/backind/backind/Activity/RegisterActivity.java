package backind.backind.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import backind.backind.R;
import backind.backind.Response.RegisterResponse;
import backind.backind.Service.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    private TextView txtLogin;
    private Button btnDaftar;
    private EditText Edtname, Edtemail, Edtnohp, Edtpass;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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


        Spinner spinner = (Spinner) findViewById(R.id.jenis_kelamin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.jenis_kelamin_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnDaftar = (Button)findViewById(R.id.daftar);
        txtLogin = (TextView)findViewById(R.id.login);
        Edtname = findViewById(R.id.nama_lengkap);
        Edtemail = findViewById(R.id.email);
        Edtnohp = findViewById(R.id.nohp);
        Edtpass = findViewById(R.id.password);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Edtname.getText().toString().isEmpty())
                    Edtname.setError("Harus diisi ");
                else if (Edtemail.getText().toString().isEmpty())
                    Edtname.setError("Harus diisi ");
                else if (Edtnohp.getText().toString().isEmpty())
                    Edtnohp.setError("Harus diisi ");
                else if (Edtpass.getText().toString().isEmpty())
                    Edtpass.setError("Harus diisi ");
                else
                    Api.getService().register(Edtname.getText().toString(), Edtemail.getText().toString(), Edtnohp.getText().toString(), Edtpass.getText().toString()).
                            enqueue(new Callback<RegisterResponse>() {
                                @Override
                                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                                    if (response.isSuccessful()) {
                                        Edtname.setText(null);
                                        Edtemail.setText(null);
                                        Edtnohp.setText(null);
                                        Edtpass.setText(null);
                                        Toast.makeText(RegisterActivity.this, "Data anda berhasil disimpan", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    }
                                }

                                @Override
                                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                                    Toast.makeText(RegisterActivity.this, "Data anda gagal disimpan", Toast.LENGTH_LONG).show();
                                }
                            });

            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}
