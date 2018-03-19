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
import id.pens.eventorganizer.RatingActivity;
import id.pens.eventorganizer.ViewticketActivity;
import id.pens.eventorganizer.model.EventDone;
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

public class MyEventDoneAdapter  extends RecyclerView.Adapter<MyEventDoneAdapter.MyViewHolder> {

    private List<EventDone> eventDoneList;
    private Context mContext;

    public MyEventDoneAdapter(Context context, List<EventDone> eventDones) {
        this.eventDoneList = eventDones;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final EventDone  eventDone = eventDoneList.get(position);
        if (!TextUtils.isEmpty(eventDone.getFoto())) {
            Picasso.with(mContext).load(eventDone.getFoto())
                    .into(holder.imageView);
        }
        holder.tvNamaEvent.setText(eventDone.getNamaEvent());
        holder.tvTanggal.setText(eventDone.getTanggal());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), RatingActivity.class);
                i.putExtra("ID_EVENT", eventDone.getIdEvent().toString());
                i.putExtra("NAMA_EVENT", eventDone.getNamaEvent().toString());
                i.putExtra("TANGGAL", eventDone.getTanggal());
                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_event_done, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        return (eventDoneList != null ? eventDoneList.size() : 0);
    }

    public void refresh(List<EventDone> eventDones) {
        this.eventDoneList = eventDones;
        this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView tvNamaEvent, tvTanggal;
        RelativeLayout relativeLayout;
        public MyViewHolder(View view) {
            super(view);
            this.relativeLayout = (RelativeLayout) view.findViewById(R.id.rel_layout_my_event_done);
            this.imageView = (ImageView) view.findViewById(R.id.img_my_event_done);
            this.tvTanggal = (TextView) view.findViewById(R.id.tv_my_event_done_tanggal);
            this.tvNamaEvent = (TextView) view.findViewById(R.id.tv_my_event_done_nama_event);
        }
    }
}
