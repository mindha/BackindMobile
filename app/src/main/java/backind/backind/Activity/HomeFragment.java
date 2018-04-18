package backind.backind.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import backind.backind.Adapter.HomestayAdapter;
import backind.backind.Adapter.KotaAdapter;
import backind.backind.Model.Kota;
import backind.backind.R;
import backind.backind._sliders.FragmentSlider;
import backind.backind._sliders.SliderIndicator;
import backind.backind._sliders.SliderPagerAdapter;
import backind.backind._sliders.SliderView;


public class HomeFragment extends Fragment {

    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

    private SliderView sliderView;
    private LinearLayout mLinearLayout;


    private RecyclerView recyclerView;
    private KotaAdapter adapter;
    private List<Kota> kotaList;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        sliderView = (SliderView) rootView.findViewById(R.id.sliderView);
        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.pagesContainer);
        setupSlider();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        kotaList = new ArrayList<>();
        adapter = new KotaAdapter(getActivity(), kotaList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) rootView.findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    Window window = getActivity().getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    Window window = getActivity().getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    collapsingToolbar.setTitle("Home");
                    isShow = true;
                } else if (isShow) {
                    //status bar
                    Window window = getActivity().getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });

        prepareKota();


        return rootView;
    }

    private void setupSlider() {
        sliderView.setDurationScroll(800);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentSlider.newInstance("http://4.bp.blogspot.com/-wBLaglBgvHI/Wsx1SJFpzII/AAAAAAAADOY/ycrraRu1hJEpNKfZ57Ngu0K5DOueZVvJACK4BGAYYCw/s1600/img_slider_1.png"));
        fragments.add(FragmentSlider.newInstance("http://1.bp.blogspot.com/-qTuGbLKylHw/Wsx1SHhquXI/AAAAAAAADOU/gs4k4cTBjuIvdzOAVCRQUG1zIHEcK-SjACK4BGAYYCw/s1600/img_slider_2.png"));
        fragments.add(FragmentSlider.newInstance("http://4.bp.blogspot.com/-WMIGm511kz4/Wsx1Rz1GryI/AAAAAAAADOQ/pz9gkghxtCMJNu8rWQmRBOor3vpKqLQOACK4BGAYYCw/s1600/img_slider_3.png"));

        mAdapter = new SliderPagerAdapter(getFragmentManager(), fragments);
        sliderView.setAdapter(mAdapter);
        mIndicator = new SliderIndicator(getActivity(), mLinearLayout, sliderView, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();
    }



    private void prepareKota() {
        String[] covers = new String[]{
                "https://demajesticbandung.com/wp-content/uploads/2017/03/1a-bandung-dari-masa-ke-masa.jpg",
                "https://cdn-image.hipwee.com/wp-content/uploads/2017/11/hipwee-wisata-jogja-1080x630.jpg",
                "https://cdns.klimg.com/resized/670x335/p/headline/5-destinasi-wisata-kekinian-wajib-kunju-fc3462.jpg",
                "http://www.nationsonline.org/gallery/Indonesia/Jakarta-Panorama.jpg"};

        Kota a = new Kota("Bandung", 20000, covers[0]);
        kotaList.add(a);

        a = new Kota("Jogjakarta", 10000, covers[1]);
        kotaList.add(a);

        a = new Kota("Malang", 30000, covers[2]);
        kotaList.add(a);

        a = new Kota("Jakarta", 50000, covers[3]);
        kotaList.add(a);

        adapter.notifyDataSetChanged();
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
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
