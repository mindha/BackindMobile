package backind.backind.Activity;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.TimeKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import backind.backind.Adapter.ReviewAdapter;
import backind.backind.Constant;
import backind.backind.Model.Review;
import backind.backind.Model.User;
import backind.backind.R;
import backind.backind.Response.BusinessDetailsResponse;
import backind.backind.Response.ReviewResponse;
import backind.backind.Service.Api;
import backind.backind.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BusinessDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private RecyclerView recList;
    private Menu collapseMenu;
    private Button btnOrder;
    private ImageView review, header;
    private  FloatingActionButton fab;
    private Dialog dialog;
    private TextView price, bukatutup, desc, address, number_reviews;
    private RatingBar totalStar;
    private List<Review> reviewData = null;
    public ReviewAdapter adapter = null;
    private User user = null;
    String longitute,latitute;

    int id_detail_bisnis, hargaSearch, id_user;
    private boolean appBarExpanded = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_detail);

        try {
            if (Hawk.contains(Constant.DataLocal.dataUser)){
                user = Hawk.get(Constant.DataLocal.dataUser);
                id_user = user.getIdUser();
            }
            id_detail_bisnis = getIntent().getIntExtra("id_detail_bisnis", 8);
            hargaSearch = getIntent().getIntExtra("hargaSearch", 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        toolbar = findViewById(R.id.anim_toolbar);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        appBarLayout = findViewById(R.id.appbar);
        recList = findViewById(R.id.scrollableview);
        review = findViewById(R.id.review);

        header = findViewById(R.id.header);
        price = findViewById(R.id.price);
        bukatutup = findViewById(R.id.bukatutup);
        address = findViewById(R.id.address);
        desc = findViewById(R.id.desc);
        number_reviews = findViewById(R.id.number_reviews);
        btnOrder = findViewById(R.id.btnOrder);
        totalStar = findViewById(R.id.total_star);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getBusinessDetail();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        adapter = new ReviewAdapter(BusinessDetailActivity.this);
        recList.setAdapter(adapter);

        fab = findViewById(R.id.maps);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kampung_gajah);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {
                int vibrantColor = palette.getVibrantColor(R.color.colorAccent);
                collapsingToolbar.setContentScrimColor(vibrantColor);
                collapsingToolbar.setStatusBarScrimColor(R.color.colorAccent);
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 200) {
                    appBarExpanded = false;
                } else {
                    appBarExpanded = true;
                }
                invalidateOptionsMenu();
            }
        });

        btnOrder = findViewById(R.id.btnOrder);

//        review.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean statusLogin = Hawk.get("statusLogin", false);
//                if (statusLogin) {
//                    messageDialog();
//                } else {
//                    Toast.makeText(BusinessDetailActivity.this, "Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(BusinessDetailActivity.this, LoginActivity.class));
//                    finish();
//                }
//            }
//        });

    }

    private void messageDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_review);
        final RatingBar valueRating = dialog.findViewById(R.id.value_ratting);
        final EditText valueReview = dialog.findViewById(R.id.value_review);
        Button btnReview = dialog.findViewById(R.id.btn_review);

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intValueRating = (int) valueRating.getRating();
                String review = valueReview.getText().toString();
                addReview(intValueRating, review);

            }
        });

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void addReview(int ratting, String review) {
        Api.getService().postReview("postReview", id_detail_bisnis, review, " ", ratting, id_user).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(BusinessDetailActivity.this, "Berhasil add review", Toast.LENGTH_SHORT).show();
                    getBusinessDetail();
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(BusinessDetailActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBusinessDetail() {
        Api.getService().getDetailPerBusiness("getDetailPerBisnis/" + id_detail_bisnis).enqueue(new Callback<BusinessDetailsResponse>() {
            @Override
            public void onResponse(Call<BusinessDetailsResponse> call, final Response<BusinessDetailsResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("Backindbug", "HMMM = " + Utils.getJsonfromUrl(response.body()));
                    final String title = response.body().getData().getBusinessDetails().get(0).getBusinessDetails().getBusinessName();
                    String description = response.body().getData().getBusinessDetails().get(0).getBusinessDetails().getBusinessDesc();
                    String harga = "Rp " + response.body().getData().getBusinessDetails().get(0).getBusinessDetails().getBusinessPrice();
                    String openclose = "Buka Jam " + response.body().getData().getBusinessDetails().get(0).getBusinessDetails().getBusinessOpenTime() + " - Tutup Jam " + response.body().getData().getBusinessDetails().get(0).getBusinessDetails().getBusinessCloseTime();
                    String alamat = response.body().getData().getBusinessDetails().get(0).getBusinessDetails().getBusinessAddress();
                    String n_review = String.valueOf(response.body().getData().getBusinessDetails().get(0).getReviews().size());
                    longitute = response.body().getData().getBusinessDetails().get(0).getBusinessDetails().getBusinessLang() ;
                    latitute = response.body().getData().getBusinessDetails().get(0).getBusinessDetails().getBusinessLat();
                    collapsingToolbar.setTitle(title);
                    Glide.with(BusinessDetailActivity.this).load("http://backind.id/storage/" + response.body().getData().getBusinessDetails().get(0).getBusinessDetails().getBusinessProfilePict()).into(header);
                    price.setText(harga);
                    desc.setText(description);
                    bukatutup.setText(openclose);
                    address.setText(alamat);
                    number_reviews.setText(n_review);
                    reviewData = new ArrayList<Review>();
                    reviewData = response.body().getData().getBusinessDetails().get(0).getReviews();
                    final int idDetailBisnis = response.body().getData().getBusinessDetails().get(0).getBusinessDetails().getIdBusinessDetails();
                    final int idmenu = Integer.valueOf(response.body().getData().getBusinessDetails().get(0).getIdMenu());

                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                            String uri = "http://maps.google.com/maps?daddr=" + latitute.trim() + "," + longitute.trim();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            intent.setPackage("com.google.android.apps.maps");
                            startActivity(intent);
                        }
                    });

                    btnOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean statusLogin = Hawk.get("statusLogin", false);
                            if (statusLogin) {
                                if (idmenu == 1) {
                                    Intent i = new Intent(BusinessDetailActivity.this, BeliTiketActivity.class);
                                    i.putExtra("id_tourism", idDetailBisnis);
                                    i.putExtra("name", title);
                                    i.putExtra("harga_tourism",response.body().getData().getBusinessDetails().get(0).getBusinessDetails().getBusinessPrice());
                                    startActivity(i);
                                } else {
                                    Intent i = new Intent(BusinessDetailActivity.this, PesanHomestayActivity.class);
                                    i.putExtra("id_homestay", idDetailBisnis);
                                    i.putExtra("name", title);
                                    i.putExtra("harga_homestay",response.body().getData().getBusinessDetails().get(0).getBusinessDetails().getBusinessPrice());
                                    startActivity(i);
                                }
                            } else {
                                Toast.makeText(BusinessDetailActivity.this, "Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(BusinessDetailActivity.this, LoginActivity.class));
                                finish();
                            }
                            BusinessDetailActivity.this.finish();
                        }
                    });
                    number_reviews.setText(reviewData.size() + " reviews");
                    adapter.setItems(reviewData);

                    float sumRate = 0;
                    for (Review review : reviewData) {
                        sumRate += Integer.valueOf(review.getRating());
                        Log.d("Backindbug", review.getRating());
                    }
                    float avgRate = sumRate / reviewData.size();
                    Log.d("Backindbug", avgRate + "");
                    //Toast.makeText(BusinessDetailActivity.this, avgRate + "", Toast.LENGTH_SHORT).show();
                    totalStar.setRating(avgRate);

                }
            }

            @Override
            public void onFailure(Call<BusinessDetailsResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collapseMenu != null && (!appBarExpanded || collapseMenu.size() != 1)) {
            //collapsed
            collapseMenu.add("Maps")
                    .setIcon(R.drawable.ic_near_me)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else {

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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }

        if (item.getTitle() == "Maps") {
            //Toast.makeText(this, "Maps menu clicked!", Toast.LENGTH_SHORT).show();
            String uri = "http://maps.google.com/maps?daddr=" + latitute.trim() + "," + longitute.trim();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
}
