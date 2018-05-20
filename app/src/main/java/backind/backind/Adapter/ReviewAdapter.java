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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import backind.backind.Activity.BusinessDetailActivity;
import backind.backind.Model.Near;
import backind.backind.Model.Review;
import backind.backind.Model.User;
import backind.backind.R;

/**
 * Created by root on 28/05/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Review> reviewList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView labelName, labelDesc;
        public ImageView labelPic;
        public LinearLayout listItem;

        public MyViewHolder(View view) {
            super(view);
            labelPic = view.findViewById(R.id.pic_user);
            labelName   =  view.findViewById(R.id.name);
            labelDesc   = view.findViewById(R.id.comment);
            listItem = view.findViewById(R.id.item);

        }
    }

    public ReviewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setItems(List<Review> review){
        this.reviewList.addAll(review);
        notifyDataSetChanged();

    }

    public void setFilter(List<Review> review){
        this.reviewList = new ArrayList<Review>();
        this.reviewList.addAll(review);
        super.notifyDataSetChanged();

    }

    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);

        return new ReviewAdapter.MyViewHolder(itemView);
    }


    int n;
    @Override
    public void onBindViewHolder(final ReviewAdapter.MyViewHolder holder, int position) {
        Review album = reviewList.get(position);
        User dataUser = album.getUser();
        holder.labelName.setText(dataUser.getName().toString());
        holder.labelDesc.setText(album.getReview());

//        i.putExtra("id_bisnis", Integer.parseInt(kotaList.get(mPosition).getIdCity().toString()));

        // loading album cover using Glide library
//        Glide.with(mContext).load("http://backind.id/storage/"+album.get).into(holder.labelPic);

    }


    @Override
    public int getItemCount() {
        return reviewList.size();
    }



}
