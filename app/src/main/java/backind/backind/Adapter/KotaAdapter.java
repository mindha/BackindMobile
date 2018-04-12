package backind.backind.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
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

import backind.backind.Activity.ListBisnisActivity;
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

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mPosition = getLayoutPosition();
                    String element = kotaList.get(mPosition).toString();
                    Toast.makeText(view.getContext(), kota.getText(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(view.getContext(), ListBisnisActivity.class);
//                    i.putExtra("nama", kota.getText());
                    view.getContext().startActivity(i);
                }
            });
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
        holder.startFrom.setText("Mulai dari Rp " +album.getStartFrom() +",-");

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumnail()).into(holder.thumbnail);

    }


    @Override
    public int getItemCount() {
        return kotaList.size();
    }




}

