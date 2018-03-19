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

public class GambarAdapter extends RecyclerView.Adapter<GambarAdapter.MyViewHolder> {
    private List<Gambar> gambars;
    private Context mContext;

    public GambarAdapter(Context context, List<Gambar> gambars) {
        this.gambars = gambars;
        this.mContext = context;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Gambar gambar = gambars.get(position);
        if (!TextUtils.isEmpty(gambar.imageUrl)) {
            Picasso.with(mContext).load(gambar.imageUrl)
//                        .error(R.drawable.image_error)
//                        .placeholder(R.drawable.placeholder)
                    .into(holder.imageView);
        }
        holder.textView.setText(Html.fromHtml(gambar.title));
        holder.Cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(view.getContext(), DetailActivity.class);

                String judul = (String) gambar.title;
               // i.putExtra("judul", judul);
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
        return (gambars != null ? gambars.size() : 0);
    }

    public void refresh(List<Gambar> gambars) {
        this.gambars=gambars;
        this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected CardView Cv;

        public MyViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.textView = (TextView) view.findViewById(R.id.title);
            this.Cv = (CardView) view.findViewById(R.id.cv_list_event_home);
        }
    }
}
