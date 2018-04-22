package backind.backind.Activity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import backind.backind.Adapter.TourismAdapter;
import backind.backind.Model.Business;
import backind.backind.Model.BusinessDetails;
import backind.backind.R;
import backind.backind.Rest.RestApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListHomestayFragment extends Fragment {
    public static final String ROOT_URL = "http://192.168.0.121:8000/";

    private LinearLayout mLinearLayout;


    private RecyclerView recyclerView;
    private TourismAdapter adapter;
    private List<BusinessDetails> bisnisList;

    public ListHomestayFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_tourism,container,false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        bisnisList = new ArrayList<>();
        adapter = new TourismAdapter(getActivity(), bisnisList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ListHomestayFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        getData();

        return rootView;
    }

    private void getData() {
        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("Backind", message);
            }
        });
        loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggin)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        RestApi service = retrofit.create(RestApi.class);
        Call<List<Business>> call = service.getDataHomestay();
        call.enqueue(new Callback<List<Business>>() {
            @Override
            public void onResponse(Call<List<Business>> call, Response<List<Business>> response) {
                List<Business> bisnis = response.body();
                bisnisList.clear();
                for(Business homestay : bisnis){
                    bisnisList.add(homestay.getBusinessDetails());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Business>> call, Throwable t) {
                System.out.println("Mencoba");
            }

        });

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}

