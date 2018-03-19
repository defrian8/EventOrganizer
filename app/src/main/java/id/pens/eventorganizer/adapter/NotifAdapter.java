package id.pens.eventorganizer.adapter;

/**
 * Created by MONKEY on 04/02/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.pens.eventorganizer.NotifDetailActivity;
import id.pens.eventorganizer.R;
import id.pens.eventorganizer.ViewticketActivity;
import id.pens.eventorganizer.WelcomeEventActivity;
import id.pens.eventorganizer.model.Notif;
import id.pens.eventorganizer.model.Organizer;
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

public class NotifAdapter  extends RecyclerView.Adapter<NotifAdapter.MyViewHolder> {

    private List<Notif> notifList;
    private Context mContext;

    public NotifAdapter(Context context, List<Notif> notifs) {
        this.notifList = notifs;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Notif notif = notifList.get(position);
        holder.tvJudul.setText(Html.fromHtml(notif.getTitle()));
        holder.tvMessage.setText(Html.fromHtml(notif.getMessage()));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notif.getType() == 1 || notif.getType() == 4) {
                    Intent i = new Intent(view.getContext(), NotifDetailActivity.class);
                    i.putExtra("ID_NOTIF", notif.getIdNotification().toString());
                    view.getContext().startActivity(i);
                }
                if(notif.getType() == 2) {
                    Intent i = new Intent(view.getContext(), WelcomeEventActivity.class);
                    i.putExtra("ID_NOTIF", notif.getIdNotification().toString());
                    view.getContext().startActivity(i);
                }
               // view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        return (notifList != null ? notifList.size() : 0);
    }

    public void refresh(List<Notif> notifs) {
        this.notifList = notifs;
        this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvJudul, tvMessage;
        RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            this.relativeLayout = (RelativeLayout) view.findViewById(R.id.rel_layout_notification);

            this.tvJudul = (TextView) view.findViewById(R.id.tv_notif_judul);
            this.tvMessage = (TextView) view.findViewById(R.id.tv_notif_message);
        }
    }
}
