package backind.backind.Adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import backind.backind.Model.Kota;
import backind.backind.R;

public class KotaAdapter extends RecyclerView.Adapter<KotaAdapter.MyViewHolder> {

    private Context mContext;
    private List<Kota> kotaList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView kota, startFrom;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            kota = (TextView) view.findViewById(R.id.kota);
            startFrom = (TextView) view.findViewById(R.id.harga);
            thumbnail = (ImageView) view.findViewById(R.id.img);
        }
    }


    public KotaAdapter(Context mContext, List<Kota> albumList) {
        this.mContext = mContext;
        this.kotaList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kota_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Kota album = kotaList.get(position);
        holder.kota.setText(album.getName());
        holder.startFrom.setText("Mulai dari " +album.getStartFrom() );

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumnail()).into(holder.thumbnail);

    }



    @Override
    public int getItemCount() {
        return kotaList.size();
    }
}

