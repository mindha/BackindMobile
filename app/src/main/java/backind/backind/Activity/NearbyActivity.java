package backind.backind.Activity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import backind.backind.Adapter.BisnisAdapter;
import backind.backind.R;


public class NearbyActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private RecyclerView recList;
    private Menu collapseMenu;

    private boolean appBarExpanded = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        toolbar = findViewById(R.id.anim_toolbar);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        appBarLayout = findViewById(R.id.appbar);
        recList = findViewById(R.id.scrollableview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar.setTitle("Kampung Gajah");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        BisnisAdapter adapter = new BisnisAdapter();
        recList.setAdapter(adapter);


//        try {
//            URL url = new URL("http://developer.android.com/assets/images/dac_logo.png");
//            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
//            wallpaperManager.setBitmap(bitmap);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kampung_gajah);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {
                int vibrantColor = palette.getVibrantColor(R.color.colorPrimary);
                collapsingToolbar.setContentScrimColor(vibrantColor);
                collapsingToolbar.setStatusBarScrimColor(R.color.colorPrimary);
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) > 200){
                    appBarExpanded = false;
                }else{
                    appBarExpanded = true;
                }
                invalidateOptionsMenu();
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(collapseMenu != null && (!appBarExpanded || collapseMenu.size() != 1)){
            //collapsed
            collapseMenu.add("Maps")
                    .setIcon(R.drawable.ic_near_me)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }else{

        }
        return super.onPrepareOptionsMenu(collapseMenu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.collapseMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Setting menu clicked!", Toast.LENGTH_SHORT).show();
                break;
        }

        if(item.getTitle() == "Maps"){
            Toast.makeText(this, "Maps menu clicked!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
