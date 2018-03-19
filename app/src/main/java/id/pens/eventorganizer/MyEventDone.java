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

import id.pens.eventorganizer.adapter.MyEventDoneAdapter;
import id.pens.eventorganizer.adapter.MyOrderAdapter;
import id.pens.eventorganizer.lib.SharedPreferenceHelper;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.model.EventDone;
import id.pens.eventorganizer.model.EventDoneResponse;
import id.pens.eventorganizer.model.MyOrder;
import id.pens.eventorganizer.services.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyEventDone extends AppCompatActivity {

    private Retrofit retrofit;
    APIService service;
    String ID_USER;
    MyEventDoneAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_done);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_my_event_done);
        mToolbar.setTitle("Past Event");
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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_event_done);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyEventDoneAdapter(this, new ArrayList<EventDone>());
        recyclerView.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        ID_USER = SharedPreferenceHelper.getSharedPreferenceString(this, "user_id","1");
        Call<EventDoneResponse> call = service.getEventDone(ID_USER);
        call.enqueue(new Callback<EventDoneResponse>() {
            @Override
            public void onResponse(Call<EventDoneResponse> call, Response<EventDoneResponse> response) {
                EventDoneResponse data = response.body();
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
            public void onFailure(Call<EventDoneResponse> call, Throwable t) {

            }
        });
    }
}
