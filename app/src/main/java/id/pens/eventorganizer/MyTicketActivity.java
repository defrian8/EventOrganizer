package id.pens.eventorganizer;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import id.pens.eventorganizer.adapter.MyOrderAdapter;
import id.pens.eventorganizer.adapter.TiketAdapter;
import id.pens.eventorganizer.lib.SharedPreferenceHelper;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.model.MyOrder;
import id.pens.eventorganizer.model.Tiket;
import id.pens.eventorganizer.model.TiketResponse;
import id.pens.eventorganizer.services.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyTicketActivity extends AppCompatActivity {

    private Retrofit retrofit;
    APIService service;
    RecyclerView recyclerView;
    TiketAdapter adapter;
    String ID_USER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_my_ticket);
        mToolbar.setTitle("My Ticket");
        mToolbar.setNavigationIcon(R.drawable.ic_backk);
        mToolbar.setBackgroundColor(Color.parseColor("#1E88E5"));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        retrofit = Utils.getRetrofit();
        service = retrofit.create(APIService.class);
        recyclerView = (RecyclerView) findViewById(R.id.rv_tiket);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TiketAdapter(this, new ArrayList<Tiket>());
        recyclerView.setAdapter(adapter);
        loadTiket();
    }

    private void loadTiket() {
        ID_USER = SharedPreferenceHelper.getSharedPreferenceString(this, "user_id","1");
        Call<TiketResponse> call = service.getTiket(ID_USER);
        call.enqueue(new Callback<TiketResponse>() {
            @Override
            public void onResponse(Call<TiketResponse> call, Response<TiketResponse> response) {
                TiketResponse data = response.body();
                if(data != null) {
                    if(data.getData() != null) {
                        if(data.getData().size() > 0) {
                            adapter.refresh(data.getData());
                        }else {
                            Log.e("id.pens.ev", "Kosong 0");
                        }
                    }else {
                        Log.e("id.pens.ev", "Kosong Null");
                    }

                }else {
                    Log.e("id.pens.ev", "Kosong null");
                }
            }

            @Override
            public void onFailure(Call<TiketResponse> call, Throwable t) {

            }
        });
    }
}
