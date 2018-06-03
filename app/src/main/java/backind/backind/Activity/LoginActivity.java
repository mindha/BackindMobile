package backind.backind.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import backind.backind.Constant;
import backind.backind.Model.Data;
import backind.backind.R;
import backind.backind.Response.LoginResponse;
import backind.backind.Service.Api;
import backind.backind.Utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private TextView txtDaftar;
    private Button btnLogin;
    private EditText edtUser;
    private EditText edtPass;
    private Data data = null;
//    private Dialog dialog;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        Hawk.init(this).build();

        try {
            data = Hawk.get(Constant.TOKEN);
            if(data != null){
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        }catch (Exception e){

        }
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
                    dialog = new SpotsDialog(LoginActivity.this);
                    dialog.show();
                    Api.getService().login(edtUser.getText().toString(), edtPass.getText().toString()).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            Log.d("Backindbug","status = " + response.body().getError());
                            Log.d("Backindbug","response = " + Utils.getJsonfromUrl(response.body()));
                            if (response.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this,"Log In", Toast.LENGTH_LONG).show();
                                Log.d("ini tokennya", response.body().getData().getToken());
                                Hawk.put(Constant.TOKEN, "Bearer " + response.body().getData().getToken());
//                                Hawk.put(Constant.USER, response.body());
                                Log.d("ini tokennya", Hawk.get(Constant.TOKEN, ""));
                                Log.d("sama kan", response.body().getData().getToken());
                                Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                Hawk.put(Constant.DataLocal.dataUser,response.body().getData().getUser());
                                Hawk.put("statusLogin", true);
                                finish();
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this,"ERROR LOGIN",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.d("Backindbug",t.getMessage());
                        }
                    });
            }
        });

    }

//    private void ErrormessageDialog() {
//        dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.error_message);
//
//        Window window = dialog.getWindow();
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        dialog.show();
//    }
//
//    private void SuccessmessageDialog() {
//        dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.success_message);
//
//        Window window = dialog.getWindow();
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        dialog.show();
//    }

}
