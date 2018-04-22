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

import java.util.List;

import backind.backind.Activity.ListBisnisActivity;
import backind.backind.Model.HomestayDetails;
import backind.backind.R;

public class TourismAdapter extends RecyclerView.Adapter<TourismAdapter.MyViewHolder> {

    private Context mContext;
    private List<HomestayDetails> tourismList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tourism, startFrom;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            tourism = (TextView) view.findViewById(R.id.bisnis);
            startFrom = (TextView) view.findViewById(R.id.harga);
            thumbnail = (ImageView) view.findViewById(R.id.img);

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mPosition = getLayoutPosition();
                    String element = tourismList.get(mPosition).toString();
                    Toast.makeText(view.getContext(), tourism.getText(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(view.getContext(), ListBisnisActivity.class);
//                    i.putExtra("nama", tourism.getText());
                    view.getContext().startActivity(i);
                }
            });
        }
    }


    public TourismAdapter(Context mContext, List<HomestayDetails> albumList) {
        this.mContext = mContext;
        this.tourismList = albumList;
    }

    @Override
    public TourismAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bisnis_card, parent, false);

        return new TourismAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TourismAdapter.MyViewHolder holder, int position) {
        HomestayDetails album = tourismList.get(position);
        holder.tourism.setText(album.getBusinessName());
        holder.startFrom.setText("Mulai dari Rp " + album.getBusinessPrice() + ",-");

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getBusinessProfilePict()).into(holder.thumbnail);

    }


    @Override
    public int getItemCount() {
        return tourismList.size();
    }

}
