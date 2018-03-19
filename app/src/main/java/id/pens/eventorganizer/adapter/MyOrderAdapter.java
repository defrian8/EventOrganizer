package id.pens.eventorganizer.adapter;

/**
 * Created by MONKEY on 31/01/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import org.w3c.dom.Text;
import java.util.List;

import id.pens.eventorganizer.DetailActivity;
import id.pens.eventorganizer.DetailOrder;
import id.pens.eventorganizer.R;
import id.pens.eventorganizer.model.JenisTiket;
import id.pens.eventorganizer.model.ListEvent;
import id.pens.eventorganizer.model.MyOrder;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> {

    private Context mContext;
    private List<MyOrder> myOrders;

    public MyOrderAdapter(Context context, List<MyOrder> orders) {
        this.mContext = context;
        this.myOrders = orders;
    }
    @Override
    public int getItemCount() {
        return (myOrders != null ? myOrders.size() : 0);
    }

    public void refresh(List<MyOrder> orders) {
        this.myOrders = orders;
        this.notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(MyOrderAdapter.MyViewHolder holder, final int position) {

        final MyOrder order = myOrders.get(position);
        holder.tvNama_event.setText(order.getEvent().toString());
        holder.tv_tanggal.setText(order.getTanggalDaftar().toString());
        final String id_order = order.getIdPendaftaran().toString();
        if (!TextUtils.isEmpty(order.getImageUrl())) {
            Picasso.with(mContext).load(order.getImageUrl())
                    .into(holder.imageView);
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DetailOrder.class);
                i.putExtra("ID_ORDER", id_order);
                view.getContext().startActivity(i);
            }
        });



    }
    @Override
    public MyOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       // View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_jenis_tiket, null);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_order, null);
        MyOrderAdapter.MyViewHolder viewHolder = new MyOrderAdapter.MyViewHolder(view);
       // JenisTiketAdapter.MyViewHolder viewHolder = new JenisTiketAdapter.MyViewHolder(view);

        return viewHolder;
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvNama_event, tv_tanggal;
        protected RelativeLayout relativeLayout;
        ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            tvNama_event = (TextView) view.findViewById(R.id.tv_my_order_nama_event);
            tv_tanggal = (TextView) view.findViewById(R.id.tv_my_order_tanggal);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.rel_layout_my_order);
            imageView = (ImageView) view.findViewById(R.id.img_my_order);
        }
    }
}
