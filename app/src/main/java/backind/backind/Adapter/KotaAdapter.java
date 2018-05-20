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

import backind.backind.Activity.ListBisnisActivity;
import backind.backind.R;
import backind.backind.Model.City;

public class KotaAdapter extends RecyclerView.Adapter<KotaAdapter.MyViewHolder> {

    private Context mContext;
    private List<City> kotaList = new ArrayList<>();

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
                    //Toast.makeText(mContext,"Di klik id = " + kotaList.get(mPosition).getIdCity().toString(),Toast.LENGTH_LONG).show();
                    String element = kotaList.get(mPosition).toString();
                    Toast.makeText(view.getContext(), kota.getText(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(view.getContext(), ListBisnisActivity.class);
                    i.putExtra("id_kota", Integer.parseInt(kotaList.get(mPosition).getIdCity().toString()));
                    view.getContext().startActivity(i);
                }
            });
        }
    }



    public KotaAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kota_card, parent, false);

        return new MyViewHolder(itemView);
    }

    public void setItems(List<City> city){
        this.kotaList.addAll(city);
        notifyDataSetChanged();

    }
    private void prepareKota() {
        String[] covers = new String[]{
                "https://demajesticbandung.com/wp-content/uploads/2017/03/1a-bandung-dari-masa-ke-masa.jpg",
                "https://cdn-image.hipwee.com/wp-content/uploads/2017/11/hipwee-wisata-jogja-1080x630.jpg",
                "https://cdns.klimg.com/resized/670x335/p/headline/5-destinasi-wisata-kekinian-wajib-kunju-fc3462.jpg",
                "http://www.nationsonline.org/gallery/Indonesia/Jakarta-Panorama.jpg"};
    }
    int n;
        @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        City album = kotaList.get(position);
        n = Integer.valueOf(album.getIdCity());
        holder.kota.setText(album.getIdCity().toString());
        holder.kota.setText(album.getCity().toString());
        holder.startFrom.setText("Mulai dari Rp "+ album.getMinCBusinessPrice().toString()+",-");

            String[] covers = new String[]{
                    "https://demajesticbandung.com/wp-content/uploads/2017/03/1a-bandung-dari-masa-ke-masa.jpg",
                    "https://demajesticbandung.com/wp-content/uploads/2017/03/1a-bandung-dari-masa-ke-masa.jpg",
                    "https://3.bp.blogspot.com/-PmpppQWw-MA/WAH7UzbRZbI/AAAAAAAAEYo/endQ1av9OrYuS_I9XXn2U3IXMOhS4GjHwCEw/s1600/grafika_cikole_2.jpg",
                    "http://2.bp.blogspot.com/-aDf1Ynp2gCw/UnsehJmPsmI/AAAAAAAABGU/Galsz6EnV7M/s1600/IMG_0406.jpg",
            };

        // loading album cover using Glide library
                Glide.with(mContext).load(covers[n]).into(holder.thumbnail);


//        holder.kota.setText(album.getName());
//        holder.startFrom.setText("Mulai dari Rp " +album.getStartFrom() +",-");

        // loading album cover using Glide library
//        Glide.with(mContext).load(album.getThumnail()).into(holder.thumbnail);

    }


    @Override
    public int getItemCount() {
        return kotaList.size();
    }




}

