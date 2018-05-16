package backind.backind.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;

import backind.backind.R;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment fragment1 = new HomeFragment();
                    FragmentTransaction fT1 = getSupportFragmentManager().beginTransaction();
                    fT1.replace(R.id.content, fragment1,"FragmentName");
                    fT1.commit();
                    return true;
                case R.id.navigation_history:
                    HistoryFragment fragment2 = new HistoryFragment();
                    FragmentTransaction fT2 = getSupportFragmentManager().beginTransaction();
                    fT2.replace(R.id.content, fragment2,"FragmentName");
                    fT2.commit();
                    return true;
                case R.id.navigation_profile:
                    ProfileFragment fragment3 = new ProfileFragment();
                    FragmentTransaction fT3 = getSupportFragmentManager().beginTransaction();
                    fT3.replace(R.id.content, fragment3,"FragmentName");
                    fT3.commit();
                    return true;
            }
            return false;
        }
    };
    public BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);
        Hawk.init(this).build();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setTitle("Home");
        HomeFragment fragment1 = new HomeFragment();
        FragmentTransaction fT1 = getSupportFragmentManager().beginTransaction();
        fT1.replace(R.id.content, fragment1,"FragmentName");
        fT1.commit();

    }

}
