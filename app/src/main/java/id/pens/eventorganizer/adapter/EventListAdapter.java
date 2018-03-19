package id.pens.eventorganizer.adapter;

/**
 * Created by MONKEY on 01/01/2018.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import id.pens.eventorganizer.DetailActivity;
import id.pens.eventorganizer.R;
import id.pens.eventorganizer.model.Gambar;
import id.pens.eventorganizer.model.ListEvent;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder> {
    private List<ListEvent> eventList;
    private Context mContext;

    public EventListAdapter(Context context, List<ListEvent> events) {
        this.eventList = events;
        this.mContext = context;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //final Gambar gambar = gambars.get(position);
        ListEvent event = eventList.get(position);

        if (!TextUtils.isEmpty(event.getImageUrl())) {
            Picasso.with(mContext).load(event.getImageUrl())
//                        .error(R.drawable.image_error)
//                        .placeholder(R.drawable.placeholder)
                    .into(holder.imageView);
        }
        Log.e("EventList", event.getImageUrl());
        final String id_event = event.getIdEvent().toString();
        final String harga =event.getHarga().toString();
        final String LAT = event.getLatitude();
        final String LONG = event.getLongitude();

        holder.textView.setText(Html.fromHtml(event.getTitle()));
        holder.txtTgl.setText(Html.fromHtml(event.getTanggal()));
        holder.txtBulan.setText(Html.fromHtml(event.getBulan()));
        holder.txtDesc.setText(Html.fromHtml(event.getDesc()));

        holder.Cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(view.getContext(), DetailActivity.class);

                //String judul = (String) gambar.title;
                i.putExtra("id_event", id_event);

                i.putExtra("LAT", LAT);
                i.putExtra("LANG", LONG);
                i.putExtra("HARGA", harga);
               // LAT = "-7.2922042163278995";
               // LANG = "112.72558450698853";
                view.getContext().startActivity(i);
                //Toast.makeText(view.getContext(), "Test Click " + judul, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_event_home, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        return (eventList != null ? eventList.size() : 0);
    }

    public void refresh(List<ListEvent> events) {
        this.eventList =events;
        this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected CardView Cv;
        protected TextView txtTgl, txtBulan, txtDesc;

        public MyViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.textView = (TextView) view.findViewById(R.id.title);
            this.Cv = (CardView) view.findViewById(R.id.cv_list_event_home);
            this.txtTgl  = (TextView) view.findViewById(R.id.txt_date);
            this.txtBulan  = (TextView) view.findViewById(R.id.txt_month);
            this.txtDesc  = (TextView) view.findViewById(R.id.txt_desc);
        }
    }
}
