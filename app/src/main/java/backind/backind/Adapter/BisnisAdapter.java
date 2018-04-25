package backind.backind.Adapter;

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

import backind.backind.Activity.BusinessDetailActivity;
import backind.backind.Activity.NearbyActivity;
import backind.backind.Model.BisnisModel;
import backind.backind.R;

import static com.thefinestartist.utils.service.ClipboardManagerUtil.getText;

/**
 * Created by root on 28/05/17.
 */

public class BisnisAdapter extends RecyclerView.Adapter<BisnisAdapter.ViewHolder> {

    private ArrayList<BisnisModel> items;

    public BisnisAdapter(){
        items = new ArrayList<>();

        String[] names = {"Taman Pintar", "Homestay Keren", "Homestay Bahagia", "Homestay Keluarga", "Homestay Indah", "Homestay Kalky", "Homestay Melati"};
        String desc = "Rp 50.000,-";
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
        LinearLayout listItem;
        ImageView labelPic;
        TextView labelName;
        TextView labelDesc;

        public ViewHolder(View view) {
            super(view);
            labelPic = view.findViewById(R.id.pic);
            labelName   =  view.findViewById(R.id.name);
            labelDesc   = view.findViewById(R.id.harga);
            listItem = view.findViewById(R.id.item);
            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mPosition = getLayoutPosition();
                    String element = items.get(mPosition).toString();
                    Toast.makeText(view.getContext(), labelName.getText(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(view.getContext(), BusinessDetailActivity.class);
//                    i.putExtra("nama", tourism.getText());
                    view.getContext().startActivity(i);
                }
            });

        }
    }
}
