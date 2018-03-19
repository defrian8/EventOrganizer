package id.pens.eventorganizer.adapter;

/**
 * Created by MONKEY on 07/02/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.pens.eventorganizer.R;
import id.pens.eventorganizer.ViewticketActivity;
import id.pens.eventorganizer.model.AttendCheck;
import id.pens.eventorganizer.model.AttendList;
import id.pens.eventorganizer.model.Tiket;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.pens.eventorganizer.R;
import id.pens.eventorganizer.ViewticketActivity;
import id.pens.eventorganizer.model.Tiket;

/**
 * Created by MONKEY on 01/02/2018.
 */

public class AttenderListCheck  extends RecyclerView.Adapter<AttenderListCheck.MyViewHolder> {

    private List<AttendCheck> attendLists;
    private Context mContext;

    public AttenderListCheck(Context context, List<AttendCheck> attendLists) {
        this.attendLists = attendLists;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //final Gambar gambar = gambars.get(position);

        final AttendCheck attend = attendLists.get(position);

        holder.tvNama.setText(attend.getNama());
        holder.tvTanggal.setText(attend.getWaktu());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_attender_check, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        return (attendLists != null ? attendLists.size() : 0);
    }

    public void refresh(List<AttendCheck> attendLists) {
        this.attendLists = attendLists;
        this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView tvNama, tvTanggal;
        RelativeLayout relativeLayout;
        public MyViewHolder(View view) {
            super(view);
            // this.relativeLayout = (RelativeLayout) view.findViewById(R.id.rel_layout_my_tiket);
            //this.imageView = (ImageView) view.findViewById(R.id.img_my_tiket);
            this.tvNama = (TextView) view.findViewById(R.id.tv_attender_nama);
            this.tvTanggal = (TextView) view.findViewById(R.id.tv_attender_tanggal);
        }
    }
}
