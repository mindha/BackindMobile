package backind.backind.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import backind.backind.Model.BusinessData;
import backind.backind.R;
import backind.backind.Utils.Utils;

public class ListBisnisActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    //private MaterialSearchView materialSearchView;
    private ViewPager mViewPager;
    TabLayout tabLayout;

    private List<BusinessData> listTourism;
    private List<BusinessData> listHomestay = new ArrayList<>();

    ListTourismFragment tab1 = null;
    ListHomestayFragment tab2 = null;
    int id_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bisnis);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try{
            id_city = getIntent().getIntExtra("id_kota",0);
        }catch (Exception e){

        }

        /*materialSearchView = findViewById(R.id.searchbudget);
        materialSearchView.closeSearch();
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/

        //getListBisnisTorism();
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //resultSearch();

    }

    private void setupViewPager(ViewPager viewPager) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void resultSearch() {
        Log.d("Backindbug", "cek length = " + tab1.bisnisList.size());
        Log.d("Backindbug", "cek result = " + Utils.getJsonfromUrl(tab1.bisnisList));
        /*if (mViewPager.getCurrentItem()==0){
            tab1 = ListTourismFragment.newListTourism();
            //List<BusinessData> listTourism1 = filter(listTourism, newText);
            //tab1.adapter.setFilter(listTourism1);
        }else if(mViewPager.getCurrentItem()==1){

        }*/
    }

    private List<BusinessData> filter(List<BusinessData> models, String query) {
        query = query.toLowerCase();
        int q = Integer.valueOf(query);
        final List<BusinessData> filtermodel = new ArrayList<BusinessData>();
        String harga;
        for (BusinessData model : models) {
            harga = model.getBusinessDetails().getBusinessPrice().toLowerCase();
            int n = Integer.valueOf(harga);
            if (harga.contains(query)) {
                filtermodel.add(model);
            }
        }
        return filtermodel;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) item.getActionView();
        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setText("Masukkan Budget Anda");
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Backindbug", "Disearch = " + query);
        //resultSearch();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("Backindbug", "Disearch coba = " + newText);
        tab1.searchView(newText);
        tab2.searchView(newText);
        return true;
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list_all, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    tab1 = ListTourismFragment.newListTourism(id_city);
                    //tab1 = new ListTourismFragment();
                    return tab1;
                case 1:
                    tab2 = ListHomestayFragment.newListHomestay(id_city);
                    return tab2;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Tourism";
                case 1:
                    return "Homestay";
            }
            return null;
        }
    }
}
