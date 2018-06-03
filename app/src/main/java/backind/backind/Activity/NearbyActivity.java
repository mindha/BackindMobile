package backind.backind.Activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import backind.backind.Adapter.BusinessAdapter;
import backind.backind.Model.Near;
import backind.backind.R;
import backind.backind.Response.NearbyResponse;
import backind.backind.Service.Api;
import backind.backind.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NearbyActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private RecyclerView recList;
    private TextView price,status;
    private Button detail;
    private ImageView header;
    private Menu collapseMenu;
    private List<Near> nearList = null;
    int id_bisnis, hargaSearch, id_menu;
    public BusinessAdapter adapter = null;

    private boolean appBarExpanded = true;
    int id_detail_bisnis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try{
            id_menu = getIntent().getIntExtra("id_menu",0);
            id_bisnis = getIntent().getIntExtra("id_bisnis",0);
            hargaSearch = getIntent().getIntExtra("harga_search",0);
        }catch (Exception e){

        }

        try{
            id_detail_bisnis = getIntent().getIntExtra("id_detail_bisnis",0);
        }catch (Exception e){

        }
        setContentView(R.layout.activity_nearby);
        toolbar = findViewById(R.id.anim_toolbar);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        appBarLayout = findViewById(R.id.appbar);
        recList = findViewById(R.id.scrollableview);
        status = findViewById(R.id.status);

        header = findViewById(R.id.header);
        price = findViewById(R.id.harga);
        detail = findViewById(R.id.detail);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getListBisnis();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        adapter = new BusinessAdapter(NearbyActivity.this);
        recList.setAdapter(adapter);


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

    private void getListBisnis() {
        Api.getService().getNearby("getNearby/"+id_bisnis, hargaSearch).enqueue(new Callback<NearbyResponse>() {
            @Override
            public void onResponse(Call<NearbyResponse> call, final Response<NearbyResponse> response) {
                if(response.isSuccessful()){
                    collapsingToolbar.setTitle(response.body().getData().getLoc().getBusinessName());
                    Glide.with(NearbyActivity.this).load("http://backind.id/storage/"+response.body().getData().getLoc().getBusinessProfilePict()).into(header);
                    String nilai = response.body().getData().getLoc().getBusinessPrice();
                    price.setText("Rp "+nilai);
                    nearList = response.body().getData().getNear();
                    final int idDetailBisnis = response.body().getData().getLoc().getIdBusinessDetails();
                    if (id_menu == 1){
                        status.setText("Homestay Terdekat");
                    }else{
                        status.setText("Tempat Wisata Terdekat");
                    }
                    Log.d("Backindbug","idDetilBisnis="+idDetailBisnis);
                    Log.d("Backindbug","TESTES="+ Utils.getJsonfromUrl(response.body().getData().getLoc()));
                    adapter.setItems(nearList);



                    detail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("Backindbug","WKWKW = " + idDetailBisnis);
                            //startActivity(new Intent(NearbyActivity.this, BusinessDetailActivity.class));
//                Toast.makeText(NearbyActivity.this,"Diklik, id bisnis detail = " + id_bisnis+"\n"+"harga search = " + hargaSearch,Toast.LENGTH_LONG).show();
                            Intent i = new Intent(NearbyActivity.this, BusinessDetailActivity.class);
                            i.putExtra("id_detail_bisnis", idDetailBisnis);
                            i.putExtra("hargaSearch", hargaSearch);
                            startActivity(i);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<NearbyResponse> call, Throwable t) {

            }
        });
    }



}

//        try {
//            URL url = new URL("http://developer.android.com/assets/images/dac_logo.png");
//            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
//            wallpaperManager.setBitmap(bitmap);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
