package id.pens.eventorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import id.pens.eventorganizer.adapter.EventListAdapter;
import id.pens.eventorganizer.adapter.GambarAdapter;
import id.pens.eventorganizer.model.EventResponse;
import id.pens.eventorganizer.model.Gambar;
import id.pens.eventorganizer.model.GambarResponse;
import id.pens.eventorganizer.model.ListEvent;
import id.pens.eventorganizer.services.EventListAPI;
import id.pens.eventorganizer.services.GambarServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private ProgressBar progressBar;
    //private GambarAdapter adapter;
    EventListAdapter eventAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new GambarAdapter(this, new ArrayList<Gambar>());
        eventAdapter = new EventListAdapter(this, new ArrayList<ListEvent>());
        //recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(eventAdapter);
        loadEvents();

        swipeRefresh.setOnRefreshListener(onSwipeRefresh);
    }

    private SwipeRefreshLayout.OnRefreshListener onSwipeRefresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadEvents();

        }
    };

    private void loadEvents() {
        String URL = "http://192.168.43.102/event/";

        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //GambarServices service = retrofit.create(GambarServices.class);
         EventListAPI service = retrofit.create(EventListAPI.class);
//        Call<GambarResponse> call = service.getGambar();
        Call<EventResponse> call = service.getEvents();

        Log.d("Call", "----------------------------------------");

        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                //GambarResponse dataResponse = response.body();
                EventResponse dataResponse = response.body();
                Log.d("MainActivity", "Status Code = " + response.code());
//                if (dataResponse != null && dataResponse.gambars != null) {
//
//                    if (dataResponse.gambars.size() > 0) {
//                        adapter.refresh(dataResponse.gambars);
//                        //Toast.makeText(MainActivity.class, dataResponse.gambars.toString(), Toast.LENGTH_SHORT);
//                       // String isisi = (String) dataResponse.gambars.toString();
//                        //Toast.makeText(this, "jsjsjs ", Toast.LENGTH_SHORT);
//                        //Toast.makeText(this, "jsjsjs " , Toast.LENGTH_SHORT);
//
//                    }
//                }
                if(dataResponse != null) {
                    if(dataResponse.getData().size() > 0) {
                        eventAdapter.refresh(dataResponse.getData());
                    }
                }
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.d("onFailure", "");
                progressBar.setVisibility(View.GONE);
            }
        });


    }
}
