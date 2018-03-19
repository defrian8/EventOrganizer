package id.pens.eventorganizer.adapter;

/**
 * Created by MONKEY on 02/02/2018.
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

import id.pens.eventorganizer.DashboarOrganizer;
import id.pens.eventorganizer.R;
import id.pens.eventorganizer.ViewticketActivity;
import id.pens.eventorganizer.model.Organizer;
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

public class MyOrganizerAdapter  extends RecyclerView.Adapter<MyOrganizerAdapter.MyViewHolder> {

    private List<Organizer> organizerList;
    private Context mContext;

    public MyOrganizerAdapter(Context context, List<Organizer> organizers) {
        this.organizerList = organizers;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Organizer organizer = organizerList.get(position);

        if (!TextUtils.isEmpty(organizer.getImgUrl())) {
            Picasso.with(mContext).load(organizer.getImgUrl())
                    .into(holder.imageView);
        }
        holder.tvNamaEvent.setText(organizer.getNamaEvent());
        holder.tvTanggal.setText(organizer.getTanggal());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DashboarOrganizer.class);
                i.putExtra("ID_EVENT", organizer.getIdEvent().toString());
                i.putExtra("NAMA_EVENT", organizer.getNamaEvent().toString());
                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_organizer, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        return (organizerList != null ? organizerList.size() : 0);
    }

    public void refresh(List<Organizer> organizers) {
        this.organizerList = organizers;
        this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView tvNamaEvent, tvTanggal;
        RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            this.relativeLayout = (RelativeLayout) view.findViewById(R.id.rel_layout_my_organizer);
            this.imageView = (ImageView) view.findViewById(R.id.img_my_organizer);
            this.tvTanggal = (TextView) view.findViewById(R.id.tv_my_org_tanggal);
            this.tvNamaEvent = (TextView) view.findViewById(R.id.tv_my_org_nama_event);
        }
    }
}
