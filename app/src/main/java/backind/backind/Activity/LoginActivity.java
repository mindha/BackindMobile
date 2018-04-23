package backind.backind.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import backind.backind.Constant;
import backind.backind.Model.Data;
import backind.backind.R;
import backind.backind.Response.LoginResponse;
import backind.backind.Service.Api;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private TextView txtDaftar;
    private Button btnLogin;
    private EditText edtUser;
    private EditText edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        Hawk.init(this).build();

        //status bar
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorWhite));

        txtDaftar = (TextView)findViewById(R.id.daftar);
        btnLogin = (Button) findViewById(R.id.login);
        edtUser = findViewById(R.id.username);
        edtPass = findViewById(R.id.password);
        txtDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                if (edtUser.getText().toString().isEmpty())
                    edtUser.setError("Harus diisi ");
                else if (edtPass.getText().toString().isEmpty())
                    edtPass.setError("Harus diisi ");
                else
                    Api.getService().login(edtUser.getText().toString(), edtPass.getText().toString()).enqueue(new Callback<LoginResponse<Data>>() {
                        @Override
                        public void onResponse(Call<LoginResponse<Data>> call, Response<LoginResponse<Data>> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Log In", Toast.LENGTH_LONG).show();
                                Log.d("ini tokennya", response.body().data.token);
                                Hawk.put(Constant.TOKEN, "Bearer " + response.body().data.token);
//                                Hawk.put(Constant.USER, response.body());
                                Log.d("ini tokennya", Hawk.get(Constant.TOKEN, ""));
                                Log.d("sama kan", response.body().data.token);
                                if (!Hawk.get(Constant.TOKEN,"ERROR").equals("ERROR")){
                                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    finish();
                                    startActivity(intent);
                                }else{
                                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    finish();
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse<Data>> call, Throwable t) {

                        }
                    });
            }
        });

    }

}
