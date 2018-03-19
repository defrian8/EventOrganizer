package id.pens.eventorganizer.adapter;

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

public class TiketAdapter  extends RecyclerView.Adapter<TiketAdapter.MyViewHolder> {

    private List<Tiket> tiketList;
    private Context mContext;

    public TiketAdapter(Context context, List<Tiket> tikets) {
            this.tiketList = tikets;
            this.mContext = context;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            //final Gambar gambar = gambars.get(position);

            final Tiket tiket = tiketList.get(position);

            if (!TextUtils.isEmpty(tiket.getImageUrl())) {
                    Picasso.with(mContext).load(tiket.getImageUrl())
                        .into(holder.imageView);
            }
            holder.tvNamaEvent.setText(tiket.getNamaEvent());
            holder.tvJenis.setText(tiket.getJenis_tiket());
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), ViewticketActivity.class);
                    i.putExtra("ID_ORDER", tiket.getId_order().toString());
                    view.getContext().startActivity(i);
                }
            });

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_tiket, null);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
    }


    @Override
    public int getItemCount() {
            return (tiketList != null ? tiketList.size() : 0);
    }

    public void refresh(List<Tiket> tikets) {
            this.tiketList = tikets;
            this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView tvNamaEvent, tvJenis;
        RelativeLayout relativeLayout;
        public MyViewHolder(View view) {
            super(view);
            this.relativeLayout = (RelativeLayout) view.findViewById(R.id.rel_layout_my_tiket);
            this.imageView = (ImageView) view.findViewById(R.id.img_my_tiket);
            this.tvJenis = (TextView) view.findViewById(R.id.tv_my_tiket_jenis_tiket);
            this.tvNamaEvent = (TextView) view.findViewById(R.id.tv_my_tiket_nama_event);
        }
    }
}
