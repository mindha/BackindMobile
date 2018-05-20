package backind.backind.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import backind.backind.Activity.NearbyActivity;
import backind.backind.Model.BusinessData;
import backind.backind.Model.BusinessDetails;
import backind.backind.R;

public class TourismAdapter extends RecyclerView.Adapter<TourismAdapter.MyViewHolder> {

    private int hargaSearch = 5000000;
    private Context mContext;
    private List<BusinessData> tourismList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tourism, startFrom;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            tourism =  view.findViewById(R.id.bisnis);
            startFrom =  view.findViewById(R.id.harga);
            thumbnail =  view.findViewById(R.id.img);

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mPosition = getLayoutPosition();
                    Toast.makeText(mContext,"Di klik id = " + tourismList.get(mPosition).getIdBusinessDetails().toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(mContext,"Di klik harga search = " + hargaSearch,Toast.LENGTH_LONG).show();
                    String element = tourismList.get(mPosition).toString();
                    Toast.makeText(view.getContext(), tourism.getText(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(view.getContext(), NearbyActivity.class);
                    i.putExtra("id_bisnis", Integer.parseInt(tourismList.get(mPosition).getIdBusinessDetails().toString()));
                    i.putExtra("harga_search", hargaSearch);
                    view.getContext().startActivity(i);
                }
            });
        }
    }


    public TourismAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setItems(List<BusinessData> business){
        this.tourismList.addAll(business);
        notifyDataSetChanged();

    }

    public void setFilter(int hargaSearch, List<BusinessData> business){
        this.hargaSearch = hargaSearch;
        this.tourismList = new ArrayList<BusinessData>();
        this.tourismList.addAll(business);
        super.notifyDataSetChanged();

    }

    @Override
    public TourismAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bisnis_card, parent, false);

        return new TourismAdapter.MyViewHolder(itemView);
    }

    int n;
    @Override
    public void onBindViewHolder(final TourismAdapter.MyViewHolder holder, int position) {
        BusinessDetails album = tourismList.get(position).getBusinessDetails();
        n = Integer.valueOf(album.getIdBusinessDetails());
        holder.tourism.setText(album.getBusinessName());
        holder.startFrom.setText("Rp " + album.getBusinessPrice() + ",-");

        // loading album cover using Glide library
        Glide.with(mContext).load("http://backind.id/storage/"+album.getBusinessProfilePict()).into(holder.thumbnail);

    }


    @Override
    public int getItemCount() {
        return tourismList.size();
    }

}
