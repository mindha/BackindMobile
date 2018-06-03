package backind.backind.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
import backind.backind.Activity.PaidTicketActivity;
import backind.backind.Activity.PaymentDeadlineActivity;
import backind.backind.Model.PaymentReceipt;
import backind.backind.R;

import static com.thefinestartist.utils.content.ResourcesUtil.getColor;

public class EticketAdapter extends RecyclerView.Adapter<EticketAdapter.MyViewHolder> {

    private Context mContext;
    private List<PaymentReceipt> eticketList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout list;
        public TextView kode, homestay, tourism, status,txt_homestay, txt_tourism;



        public MyViewHolder(View view) {
            super(view);
            list =view.findViewById(R.id.list_item);
            kode =  view.findViewById(R.id.code_backind);
            status =  view.findViewById(R.id.status);
            homestay =  view.findViewById(R.id.name_homestay);
            tourism =  view.findViewById(R.id.name_tourism);
            txt_homestay = view.findViewById(R.id.txt_homestay);
            txt_tourism = view.findViewById(R.id.txt_tourism);

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
        Drawable myDrawable;
//        String nama_homestay;

        final PaymentReceipt album = eticketList.get(position);
        n = Integer.valueOf(album.getIdTransaksi());
        final int kode = 100+ Integer.valueOf(album.getIdBooking());
        final String kode_backind = "#BACKIND2018"+kode;
        holder.kode.setText(kode_backind);
        final int id_status = Integer.valueOf(album.getStatusTransfer());
        if(id_status == 1){
            holder.status.setText("Paid");
            holder.status.setTextColor(ContextCompat.getColor(mContext,R.color.colorGreen));

        }else if(id_status ==2){
            holder.status.setText("Waiting Payment");
            holder.status.setTextColor(ContextCompat.getColor(mContext,R.color.colorOrange));
        }

        final String duedate = album.getTransaksi().getDuedate();
        final int tagihan = album.getTransaksi().getTotalCost();
        final String tagih = String.valueOf(tagihan);
        String nama_homestay = null;
        String nama_tourism = null;
         String in_homestay = null;
         String out_homestay = null;
        String date_tourism = null;


        if (album.getTransaksi().getTourism() != null){

            holder.tourism.setText(album.getTransaksi().getTourism().getBusinessDetails().getBusinessName().toString());
            nama_tourism = album.getTransaksi().getTourism().getBusinessDetails().getBusinessName();
            date_tourism = String.valueOf(album.getTransaksi().getCheckinTourism());

        } else {
            holder.tourism.setVisibility(View.GONE);
            holder.txt_tourism.setVisibility(View.GONE);
        }

        if (album.getTransaksi().getHomestay() != null){
            holder.homestay.setText(album.getTransaksi().getHomestay().getBusinessDetails().getBusinessName().toString());
            nama_homestay = album.getTransaksi().getHomestay().getBusinessDetails().getBusinessName();
            in_homestay = album.getTransaksi().getCheckin();
            out_homestay = album.getTransaksi().getCheckout();
        } else {
            holder.homestay.setVisibility(View.GONE);
            holder.txt_homestay.setVisibility(View.GONE);
        }

        final String finalNama_homestay = nama_homestay;
        final String finalNama_tourism = nama_tourism;
        final String finalDate_tourism = date_tourism;
        final String finalIn_homestay = in_homestay;
        final String finalOut_homestay = out_homestay;
        holder.list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = holder.getLayoutPosition();
                String element = eticketList.get(mPosition).toString();
                if(id_status==1){
                    Intent i = new Intent(view.getContext(), PaidTicketActivity.class);
                    i.putExtra("id_transaksi", Integer.parseInt(eticketList.get(mPosition).getIdTransaksi().toString()));
                    i.putExtra("duedate",duedate);
                    i.putExtra("jumlah_tagihan",tagih);
                    i.putExtra("eticket",kode_backind);
                    i.putExtra("name_user",String.valueOf(album.getTransaksi().getUser().getName()));
                    i.putExtra("name_homestay", finalNama_homestay);
                    i.putExtra("name_tourism", finalNama_tourism);
                    i.putExtra("check_in", finalIn_homestay);
                    i.putExtra("check_out", finalOut_homestay);
                    i.putExtra("date_tiket", finalDate_tourism);
                    i.putExtra("jumlah_orang",album.getTransaksi().getTotalTicket());
                    view.getContext().startActivity(i);

                }else if(id_status==2){
                    Intent i = new Intent(view.getContext(), BuktiTransferActivity.class);
                    i.putExtra("id_transaksi", Integer.parseInt(eticketList.get(mPosition).getIdTransaksi().toString()));
                    i.putExtra("duedate",duedate);
                    i.putExtra("jumlah_tagihan",tagih);
                    i.putExtra("eticket",kode_backind);
                    i.putExtra("status",id_status);
                    view.getContext().startActivity(i);
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return eticketList.size();
    }




}

