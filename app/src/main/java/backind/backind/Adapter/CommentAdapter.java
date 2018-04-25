package backind.backind.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import backind.backind.Model.CommentModel;
import backind.backind.R;

/**
 * Created by root on 28/05/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private ArrayList<CommentModel> items;

    public CommentAdapter(){
        items = new ArrayList<>();

        String[] names = {"Mindha", "Amrizal", "Ikhsan", "Icha", "Saka", "Kalky", "Melati"};
        String desc = "Bagus Banget";
        for(int i = 0; i < names.length; i++){
            CommentModel item = new CommentModel(names[i], desc);
            items.add(item);
        }
    }

    public CommentAdapter(ArrayList<CommentModel> items){
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommentModel item = items.get(position);
        holder.labelName.setText(item.getName());
        holder.labelDesc.setText(item.getComment());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView labelPic;
        TextView labelName;
        TextView labelDesc;

        public ViewHolder(View view) {
            super(view);
            labelPic = view.findViewById(R.id.pic);
            labelName   =  view.findViewById(R.id.name);
            labelDesc   = view.findViewById(R.id.comment);
        }
    }
}
