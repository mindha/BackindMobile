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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;

import backind.backind.Constant;
import backind.backind.Model.Data;
import backind.backind.Model.User;
import backind.backind.R;
import backind.backind.Response.ProfileResponse;
import backind.backind.Response.RegisterResponse;
import backind.backind.Service.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private TextView name,email;
    private EditText nama, mail, telp;
    private ImageView photo;
    private User user=null;
    private Button update;
    private Data data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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

        Spinner spinner = findViewById(R.id.jenis_kelamin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.jenis_kelamin_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        name = findViewById(R.id.name);
        email = findViewById(R.id.emailadd);
        nama = findViewById(R.id.nama_lengkap);
        mail = findViewById(R.id.mail);
        telp = findViewById(R.id.nohp);
        photo = findViewById(R.id.img_user);
        update = findViewById(R.id.btn_update);

        setDataUser();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Api.getService().updateUser(nama.getText().toString(),mail.getText().toString(),telp.getText().toString()).enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                        if (response.isSuccessful()) {
                            setNewDataUser(response.body().getName().toString(),
                                    response.body().getEmail().toString(),
                                    response.body().getPhoneNumber().toString());
                            //Hawk.put(Constant.DataLocal.dataUser,response.body());
                            Toast.makeText(EditProfileActivity.this, "Data anda berhasil disimpan", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(EditProfileActivity.this, EditProfileActivity.class));
                            setDataUser();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {
                        Toast.makeText(EditProfileActivity.this, "Data anda gagal disimpan", Toast.LENGTH_LONG).show();
                    }
                });

            }
            });
        }

        public void setNewDataUser(String nama, String email, String notelp){
            User newUser = new User();
            newUser.setName(nama);
            newUser.setEmail(email);
            newUser.setPhoneNumber(notelp);
            newUser.setAvatar(user.getAvatar());
            Hawk.put(Constant.DataLocal.dataUser,newUser);
        }

        public void setDataUser(){
            try{

                user = Hawk.get(Constant.DataLocal.dataUser);
                name.setText(user.getName().toString());
                email.setText(user.getEmail().toString());
                Glide.with(this).load(Constant.BASE_URL_PHOTO + user.getAvatar()).into(photo);

                nama.setText(user.getName().toString());
                mail.setText(user.getEmail().toString());
                telp.setText(user.getPhoneNumber().toString());


            }catch (Exception e){
                Log.d("Backindbug","error edit profil :",e);
            }
        }

    }



