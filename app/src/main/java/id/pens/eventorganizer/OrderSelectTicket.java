package id.pens.eventorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import id.pens.eventorganizer.adapter.JenisTiketAdapter;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.model.DetailEvent;
import id.pens.eventorganizer.model.JenisTiket;
import id.pens.eventorganizer.model.JenisTiketResponse;
import id.pens.eventorganizer.services.APIService;
import id.pens.eventorganizer.services.DetailEventAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderSelectTicket extends AppCompatActivity {

    String ID_EVENT;
    JenisTiketAdapter adapter;
    Button btnOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ID_EVENT = intent.getStringExtra("id_event");
        setContentView(R.layout.activity_order_select_ticket);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_select_jenis_tiket);
        btnOrder = (Button) findViewById(R.id.btn_jenis_tiket_pilih);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JenisTiketAdapter(this, new ArrayList<JenisTiket>());
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = Utils.getRetrofit();
        APIService service = retrofit.create(APIService.class);
        Call<JenisTiketResponse> call = service.getJenisTicket(ID_EVENT);
        call.enqueue(new Callback<JenisTiketResponse>() {
            @Override
            public void onResponse(Call<JenisTiketResponse> call, Response<JenisTiketResponse> response) {
                JenisTiketResponse dataResponse = response.body();
                if(dataResponse != null) {
                    if(dataResponse.getData().size() > 0) {
                        adapter.refresh(dataResponse.getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<JenisTiketResponse> call, Throwable t) {

            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(adapter.selectedPosition() < 0) {
                    Toast.makeText(getApplicationContext(), "Anda belum memelilih jenis tiket", Toast.LENGTH_SHORT).show();
                }else {
                    JenisTiket model = adapter.getSelectedItem();
//             Toast.makeText(view.getContext(), model.getNamaTiket().toString(), Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(OrderSelectTicket.this, OrderCreate.class);
                    myIntent.putExtra("id_jenis_tiket", model.getIdJenisTiket().toString());
                    myIntent.putExtra("id_event", ID_EVENT);
                    myIntent.putExtra("nama_tiket", model.getNamaTiket().toString());
                    myIntent.putExtra("harga_tiket", model.getHarga().toString());
                    Log.e("EventOrg", model.getHarga().toString());
                    Log.e("EventOrg", model.getNamaTiket().toString());
                    OrderSelectTicket.this.startActivity(myIntent);
                }

            }
        });
    }
}
