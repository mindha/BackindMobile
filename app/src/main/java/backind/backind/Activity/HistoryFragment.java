package backind.backind.Activity;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import backind.backind.Adapter.EticketAdapter;
import backind.backind.Adapter.KotaAdapter;
import backind.backind.Model.City;
import backind.backind.Model.PaymentReceipt;
import backind.backind.R;
import backind.backind.Response.CityResponse;
import backind.backind.Response.EticketResponse;
import backind.backind.Service.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private EticketAdapter adapter;
    private Button eticket;
    private List<PaymentReceipt> listEticket;



    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history,container,false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        eticket = rootView.findViewById(R.id.eticket);
        adapter = new EticketAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        getListEticket();

        return rootView;

    }

    private void getListEticket(){
        Api.getService().getTicketList().enqueue(new Callback<EticketResponse>() {
            @Override
            public void onResponse(Call<EticketResponse> call, Response<EticketResponse> response) {
                if(response.isSuccessful()){
                    listEticket = new ArrayList<PaymentReceipt>();
                    for (List<PaymentReceipt> data : response.body().getData()){
                        for (PaymentReceipt paymentReceipt : data){
                            listEticket.add(paymentReceipt);
                        }
                    }
                    adapter.setItems(listEticket);
                }
            }

            @Override
            public void onFailure(Call<EticketResponse> call, Throwable t) {

            }
        });
    }


}
