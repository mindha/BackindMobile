package backind.backind.Activity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
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
import backind.backind.Response.BusinessResponse;
import backind.backind.R;
import backind.backind.Service.Api;
import backind.backind.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListHomestayFragment extends Fragment implements SearchView.OnQueryTextListener {
    public static final String ROOT_URL = "http://backind.id/";
    private static final String getIdCity = "id_city";

    private LinearLayout mLinearLayout;


    private RecyclerView recyclerView;
    public TourismAdapter adapter;
    private List<BusinessData> bisnisList;
    public int id_city;

    public static ListHomestayFragment newListHomestay (int id_city){
        ListHomestayFragment ListHomestayFragment = new ListHomestayFragment();
        Bundle args = new Bundle();
        args.putInt(getIdCity,id_city);
        ListHomestayFragment.setArguments(args);

        return ListHomestayFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!= null){
            this.id_city = getArguments().getInt(getIdCity);
            Log.d("Backindbug","id citynya = " + id_city);
        }

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_tourism, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        bisnisList = new ArrayList<>();
        adapter = new TourismAdapter(getActivity());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ListHomestayFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

//        getData();
        getListHomestay();
        Log.d("Backindbug", "Cobain = " + Utils.getJsonfromUrl(bisnisList));
        return rootView;
    }

    // 123
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.abuDark));
        searchEditText.setHintTextColor(getResources().getColor(R.color.abuLight));
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        });

    }

    private void getListHomestay() {
        Api.getService().getDataHomestay("getHomestayInThatCities/"+id_city).enqueue(new Callback<BusinessResponse>() {
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
    // 123
    public void searchView(String newText) {
        Log.d("Backindbug","ini harganya = "+newText);
        if(newText == null || newText.equals("")){
            newText="5000000";
        }

        List<BusinessData> listTourism1 = filter(bisnisList, newText);
        adapter.setFilter(Integer.parseInt(newText),listTourism1);
        adapter.notifyDataSetChanged();
        /*if (mViewPager.getCurrentItem()==0){

            //List<BusinessData> listTourism1 = filter(listTourism, newText);
            //tab1.adapter.setFilter(listTourism1);
        }else if(mViewPager.getCurrentItem()==1){

        }*/
    }
    // 123
    private List<BusinessData> filter(List<BusinessData> models, String query) {
        query = query.toLowerCase();
        List<BusinessData> filtermodel = new ArrayList<BusinessData>();

        try {
            if (query.isEmpty()) {
                filtermodel = models;
            } else {
                long q = Long.valueOf(query);
                long harga;
                for (BusinessData model : models) {
                    harga = Long.valueOf(model.getBusinessDetails().getBusinessPrice());
                    long n = Long.valueOf(harga);
                    if (harga <= q) {
                        filtermodel.add(model);
                    }
                }
            }
        } catch (NumberFormatException e) {

        }

        return filtermodel;
    }

    // 123
    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView(query);
        return true;
    }
    // 123
    @Override
    public boolean onQueryTextChange(String newText) {
        searchView(newText);
        return true;
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

