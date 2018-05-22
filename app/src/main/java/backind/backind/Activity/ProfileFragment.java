package backind.backind.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;

import backind.backind.Constant;
import backind.backind.Model.User;
import backind.backind.R;


public class ProfileFragment extends Fragment {
    private TextView logout, name, email, changeProfile, changePass;
    private ImageView photo;
    private User user=null;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_profile, container, false);
        name = rootView.findViewById(R.id.name);
        email = rootView.findViewById(R.id.emailadd);
        photo = rootView.findViewById(R.id.img_user);
        changeProfile = rootView.findViewById(R.id.ubah_profil);
        changePass = rootView.findViewById(R.id.ubah_sandi);

        try{
            user = Hawk.get(Constant.DataLocal.dataUser);
            name.setText(user.getName().toString());
            email.setText(user.getEmail().toString());
            Glide.with(getActivity()).load(Constant.BASE_URL_PHOTO + user.getAvatar()).into(photo);

        }catch (Exception e){

        }

        logout = (TextView)rootView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hawk.deleteAll();
                Hawk.put("statusLogin", false);
                startActivity(new Intent(getActivity(),MenuActivity.class));
            }
        });

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),EditProfileActivity.class));
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ChangePasswordActivity.class));
            }
        });

        return rootView;

    }


}
