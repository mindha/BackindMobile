package backind.backind.Activity;

import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import backind.backind.Constant;
import backind.backind.Model.User;
import backind.backind.R;
import backind.backind.Response.BaseResponse;
import backind.backind.Service.Api;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText passOld, passNew;
    Button changePass;
    User user = null;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

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

//        passOld = findViewById(R.id.old_password);
        passNew = findViewById(R.id.new_password);
        changePass = findViewById(R.id.change_pass);

//        user = Hawk.get(Constant.DataLocal.dataUser);
//        String pass = user.get();
        final String new_password = passNew.getText().toString();

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new SpotsDialog(ChangePasswordActivity.this);
                dialog.show();
                Api.getService().updatePassword(passNew.getText().toString()).enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if(response.isSuccessful()){
                            dialog.dismiss();
                            passNew.setText(new_password);
                            Toast.makeText(ChangePasswordActivity.this, "Data anda berhasil disimpan", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toast.makeText(ChangePasswordActivity.this, "Data anda tidak berhasil disimpan", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });





    }
}
