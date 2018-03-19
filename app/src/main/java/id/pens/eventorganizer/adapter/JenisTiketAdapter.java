package id.pens.eventorganizer.adapter;

/**
 * Created by MONKEY on 17/01/2018.
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
import id.pens.eventorganizer.R;
import id.pens.eventorganizer.model.JenisTiket;
import id.pens.eventorganizer.model.ListEvent;

public class JenisTiketAdapter extends RecyclerView.Adapter<JenisTiketAdapter.MyViewHolder> {

    private Context mContext;
    private List<JenisTiket> jenisTikets;
    private int lastCheckedPosition = -1;
    public  int id_jenis_tiket;

    public JenisTiketAdapter(Context context, List<JenisTiket> tikets) {
        this.mContext = context;
        this.jenisTikets = tikets;
    }
    @Override
    public int getItemCount() {
        return (jenisTikets != null ? jenisTikets.size() : 0);
    }

    public void refresh(List<JenisTiket> tikets) {
        this.jenisTikets = tikets;
        this.notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final JenisTiket tiket = jenisTikets.get(position);
        holder.tvNama.setText(tiket.getNamaTiket().toString());
        final String id_jenis_ticket = tiket.getIdJenisTiket().toString();
        holder.id_jenis_ticket_holder = Integer.parseInt(id_jenis_ticket);
        holder.tvHarga.setText(tiket.getHarga().toString());
        holder.tvStok.setText(tiket.getAvailable().toString());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastCheckedPosition = position;
                notifyDataSetChanged();
            }
        });
        if(lastCheckedPosition == position) {
            holder.lin_selected.setBackgroundColor(Color.parseColor("#FC4B6C"));
        }else{
            holder.lin_selected.setBackgroundColor(Color.parseColor("#FFB22B"));
        }
        // holder.radioButton.setChecked(lastCheckedPosition == position);

    }
    public  JenisTiket getSelectedItem() {
        JenisTiket model = jenisTikets.get(lastCheckedPosition);
        return  model;
    }
    public int selectedPosition(){
        return lastCheckedPosition;
    }
    public int getId_jenis_tiket()
    {
        return id_jenis_tiket;
    }
    @Override
    public JenisTiketAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_jenis_tiket, null);
        JenisTiketAdapter.MyViewHolder viewHolder = new JenisTiketAdapter.MyViewHolder(view);
        return viewHolder;
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvNama, tvHarga, tvStok;
        protected RadioButton radioButton;
        protected int id_jenis_ticket_holder;
        protected RelativeLayout relativeLayout;
        protected LinearLayout lin_selected;
        public MyViewHolder(View view) {
            super(view);
            this.tvNama = (TextView) view.findViewById(R.id.tv_nama_jenis_tiket);
          //  this.radioButton = (RadioButton) view.findViewById(R.id.radio_jenis_ticket);
            tvHarga = (TextView) view.findViewById(R.id.tv_jenis_tiket_harga);
            tvStok = (TextView) view.findViewById(R.id.tv_jenis_tiket_stok);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.rel_layout_jenis_tiket);
            lin_selected = (LinearLayout) view.findViewById(R.id.linear_pilih_jenis_tiket);


        }
    }
}
