package backind.backind.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import backind.backind.Activity.BuktiTransferActivity;
import backind.backind.Activity.ListBisnisActivity;
import backind.backind.Activity.PaymentDeadlineActivity;
import backind.backind.Model.PaymentReceipt;
import backind.backind.R;

public class EticketAdapter extends RecyclerView.Adapter<EticketAdapter.MyViewHolder> {

    private Context mContext;
    private List<PaymentReceipt> eticketList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout list;
        public TextView kode, homestay, tourism, status;


        public MyViewHolder(View view) {
            super(view);
            list =view.findViewById(R.id.list_item);
            kode =  view.findViewById(R.id.code_backind);
            status =  view.findViewById(R.id.status);
            homestay =  view.findViewById(R.id.name_homestay);
            tourism =  view.findViewById(R.id.name_tourism);


        }
    }



    public EticketAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_eticket, parent, false);

        return new MyViewHolder(itemView);
    }

    public void setItems(List<PaymentReceipt> eticket){
        this.eticketList.addAll(eticket);
        notifyDataSetChanged();

    }

    int n;
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PaymentReceipt album = eticketList.get(position);
        n = Integer.valueOf(album.getIdTransaksi());
        final int kode = 100+ Integer.valueOf(album.getIdBooking());
        final String kode_backind = "#BACKIND2018"+kode;
        holder.kode.setText(kode_backind);
        final int id_status = Integer.valueOf(album.getStatusTransfer());
        if(id_status == 1){
            holder.status.setText("PAID");
        }else if(id_status ==2){
            holder.status.setText("WAITING");
        }

        final String duedate = album.getTransaksi().getDuedate();
        final int tagihan = album.getTransaksi().getTotalCost();
        final String tagih = String.valueOf(tagihan);


        if (album.getTransaksi().getTourism() != null){

            holder.tourism.setText(album.getTransaksi().getTourism().getBusinessDetails().getBusinessName().toString());
        } else {
            holder.tourism.setVisibility(View.GONE);
        }

        if (album.getTransaksi().getHomestay() != null){
            holder.homestay.setText(album.getTransaksi().getHomestay().getBusinessDetails().getBusinessName().toString());
        } else {
            holder.homestay.setVisibility(View.GONE);
        }

        holder.list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = holder.getLayoutPosition();
                //Toast.makeText(mContext,"Di klik id = " + kotaList.get(mPosition).getIdCity().toString(),Toast.LENGTH_LONG).show();
                String element = eticketList.get(mPosition).toString();
                Intent i = new Intent(view.getContext(), BuktiTransferActivity.class);

                i.putExtra("id_transaksi", Integer.parseInt(eticketList.get(mPosition).getIdTransaksi().toString()));
                i.putExtra("duedate",duedate);
                i.putExtra("jumlah_tagihan",tagih);
                i.putExtra("eticket",kode_backind);
                i.putExtra("status",id_status);
                view.getContext().startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return eticketList.size();
    }




}

