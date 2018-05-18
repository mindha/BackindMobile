package backind.backind.Activity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import backind.backind.Adapter.TourismAdapter;
import backind.backind.Model.BusinessData;
import backind.backind.Model.BusinessDetails;
import backind.backind.Model.BusinessResponse;
import backind.backind.R;
import backind.backind.Service.Api;
import backind.backind.Utils.Utils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListTourismFragment extends Fragment{
    public static final String ROOT_URL = "http://backind.id/";
    private static final String LIST_Tourism = "List_Tourism";

    private LinearLayout mLinearLayout;


    private RecyclerView recyclerView;
    public TourismAdapter adapter;
    public List<BusinessData> bisnisList;

    public static ListTourismFragment newListTourism (List<BusinessData> bisnis){
        ListTourismFragment listTourismFragment = new ListTourismFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(LIST_Tourism, (ArrayList<? extends Parcelable>) bisnis);
        listTourismFragment.setArguments(args);

        return listTourismFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!= null){
            this.bisnisList = getArguments().getParcelableArrayList(LIST_Tourism);
            Log.d("Backindbug","Ga null = " + Utils.getJsonfromUrl(bisnisList));
        }

    }

    public void searchView(String newText){
        List<BusinessData> listTourism1 = filter(bisnisList, newText);
        adapter.setFilter(listTourism1);
        adapter.notifyDataSetChanged();
        /*if (mViewPager.getCurrentItem()==0){

            //List<BusinessData> listTourism1 = filter(listTourism, newText);
            //tab1.adapter.setFilter(listTourism1);
        }else if(mViewPager.getCurrentItem()==1){

        }*/
    }

    private List<BusinessData> filter(List<BusinessData> models, String query){
        query = query.toLowerCase();
        int q = Integer.valueOf(query);
        final List <BusinessData> filtermodel = new ArrayList<BusinessData>();
        String harga;
        for(BusinessData model : models){
            harga = model.getBusinessDetails().getBusinessPrice().toLowerCase();
            int n = Integer.valueOf(harga);
            if(harga.contains(query)) {
                filtermodel.add(model);
            }
        }
        return filtermodel;
    }

    private void getListTourism(){
        Api.getService().getDataTourism().enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> response) {
                if(response.isSuccessful()){
                    bisnisList = response.body().getData();
                    adapter.setItems(bisnisList);
                }
            }

            @Override
            public void onFailure(Call<BusinessResponse> call, Throwable t) {

            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_tourism,container,false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        bisnisList = new ArrayList<>();
        adapter = new TourismAdapter(getActivity());
        //adapter.setItems(bisnisList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ListTourismFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        getListTourism();

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) item.getActionView();
        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.abuDark));
        searchEditText.setHintTextColor(getResources().getColor(R.color.abuLight));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Backindbug","Tes = " + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Backindbug","diklik searchnya");
            }
        });
    }

    /*@Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Backindbug","query = " + query);
        searchView(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("Backindbug","newText = " + newText);
        searchView(newText);
        return true;
    }*/

    //    private void getData() {
//        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Log.d("Backind", message);
//            }
//        });
//        loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(loggin)
//                .build();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ROOT_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//
//        RestApi service = retrofit.create(RestApi.class);
//        Call<List<BusinessResponse>> call = service.getDataTourism();
//        call.enqueue(new Callback<List<BusinessResponse>>() {
//            @Override
//            public void onResponse(Call<List<BusinessResponse>> call, Response<List<BusinessResponse>> response) {
//                List<BusinessResponse> bisnis = response.body();
//                bisnisList.clear();
//                for(BusinessResponse homestay : bisnis){
//                    bisnisList.add(homestay.getBusinessDetails());
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<List<BusinessResponse>> call, Throwable t) {
//                System.out.println("Mencoba");
//            }
//
//        });
//
//    }

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

