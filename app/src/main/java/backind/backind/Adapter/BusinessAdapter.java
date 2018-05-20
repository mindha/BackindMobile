package backind.backind.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import backind.backind.Activity.BusinessDetailActivity;
import backind.backind.Model.Near;
import backind.backind.R;

/**
 * Created by root on 28/05/17.
 */

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.MyViewHolder> {

    private Context mContext;
    private List<Near> bisnisList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView labelName, labelDesc;
        public ImageView labelPic;
        public LinearLayout listItem;

        public MyViewHolder(View view) {
            super(view);
            labelPic = view.findViewById(R.id.pic);
            labelName   =  view.findViewById(R.id.name);
            labelDesc   = view.findViewById(R.id.harga);
            listItem = view.findViewById(R.id.item);

            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mPosition = getLayoutPosition();
                    Toast.makeText(view.getContext(), labelName.getText(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(view.getContext(), BusinessDetailActivity.class);
                    i.putExtra("id_detail_bisnis", Integer.parseInt(bisnisList.get(mPosition).getIdBusinessDetails().toString()));
//                    i.putExtra("harga_search", hargaSearch);
                    view.getContext().startActivity(i);
                }
            });

        }
    }

    public BusinessAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setItems(List<Near> business){
        this.bisnisList.addAll(business);
        notifyDataSetChanged();

    }

    public void setFilter(List<Near> business){
        this.bisnisList = new ArrayList<Near>();
        this.bisnisList.addAll(business);
        super.notifyDataSetChanged();

    }

    @Override
    public BusinessAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_business, parent, false);

        return new BusinessAdapter.MyViewHolder(itemView);
    }


    int n;
    @Override
    public void onBindViewHolder(final BusinessAdapter.MyViewHolder holder, int position) {
        Near album = bisnisList.get(position);
        n = Integer.valueOf(album.getIdBusinessDetails());
        holder.labelName.setText(album.getBusinessName());
        holder.labelDesc.setText("Rp " + album.getBusinessPrice() + ",-");

//        i.putExtra("id_bisnis", Integer.parseInt(kotaList.get(mPosition).getIdCity().toString()));

        // loading album cover using Glide library
//        Glide.with(mContext).load("http://backind.id/storage/"+album.getfoto()).into(holder.labelPic);

    }


    @Override
    public int getItemCount() {
        return bisnisList.size();
    }



}
