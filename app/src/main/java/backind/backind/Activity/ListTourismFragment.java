package backind.backind.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import backind.backind.Adapter.KotaAdapter;
import backind.backind.Adapter.TourismAdapter;
import backind.backind.Model.TourismModel;
import backind.backind.Model.TourismModel;
import backind.backind.R;
import backind.backind._sliders.SliderView;


public class ListTourismFragment extends Fragment {

    private LinearLayout mLinearLayout;


    private RecyclerView recyclerView;
    private TourismAdapter adapter;
    private List<TourismModel> tourismList;

    public ListTourismFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_tourism,container,false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        tourismList = new ArrayList<>();
        adapter = new TourismAdapter(getActivity(), tourismList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ListTourismFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        dataTourism();

        return rootView;
    }

    private void dataTourism() {
        String[] covers = new String[]{
                "https://demajesticbandung.com/wp-content/uploads/2017/03/1a-bandung-dari-masa-ke-masa.jpg",
                "https://cdn-image.hipwee.com/wp-content/uploads/2017/11/hipwee-wisata-jogja-1080x630.jpg",
                "https://cdns.klimg.com/resized/670x335/p/headline/5-destinasi-wisata-kekinian-wajib-kunju-fc3462.jpg",
                "http://www.nationsonline.org/gallery/Indonesia/Jakarta-Panorama.jpg"};

        TourismModel a = new TourismModel("Kampung Gajah", 20000, covers[0]);
        tourismList.add(a);

        a = new TourismModel("Taman Kardus", 10000, covers[1]);
        tourismList.add(a);

        a = new TourismModel("Lorem Ipsum", 30000, covers[2]);
        tourismList.add(a);

        a = new TourismModel("Lorem Ipsum", 50000, covers[3]);
        tourismList.add(a);

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
