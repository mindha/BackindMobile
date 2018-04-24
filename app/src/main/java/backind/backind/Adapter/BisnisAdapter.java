package backind.backind.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import backind.backind.Model.BisnisModel;
import backind.backind.R;

/**
 * Created by root on 28/05/17.
 */

public class BisnisAdapter extends RecyclerView.Adapter<BisnisAdapter.ViewHolder> {

    private ArrayList<BisnisModel> items;

    public BisnisAdapter(){
        items = new ArrayList<>();

        String[] names = {"Mindha Ningrum", "Amrizal Nurrahman", "Saka Gelantara", "Paijo Sukirman", "Gege Kala", "Kalki Muhammad", "Muhammad Iqbal"};
        String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque";
        for(int i = 0; i < names.length; i++){
            BisnisModel item = new BisnisModel(names[i], desc);
            items.add(item);
        }
    }

    public BisnisAdapter(ArrayList<BisnisModel> items){
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BisnisModel item = items.get(position);
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
            labelPic = (ImageView) view.findViewById(R.id.user);
            labelName   = (TextView) view.findViewById(R.id.name);
            labelDesc   = (TextView) view.findViewById(R.id.comment);
        }
    }
}
