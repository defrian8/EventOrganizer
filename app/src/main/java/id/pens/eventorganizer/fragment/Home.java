package id.pens.eventorganizer.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.pens.eventorganizer.R;


import android.content.Intent;

import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.pens.eventorganizer.adapter.EventListAdapter;
import id.pens.eventorganizer.adapter.GambarAdapter;
import id.pens.eventorganizer.lib.Utils;
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
/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefresh;
    //private ProgressBar progressBar;
    EventListAdapter eventAdapter;


    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance() {

        return new Home();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar_home);
        mToolbar.setTitle("List Event");

        mToolbar.setBackgroundColor(Color.parseColor("#1E88E5"));
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh_fragment);
      //  progressBar = (ProgressBar) view.findViewById(R.id.progressBar_fragment);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_fragment);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //adapter = new GambarAdapter(this, new ArrayList<Gambar>());
        eventAdapter = new EventListAdapter(getActivity(), new ArrayList<ListEvent>());
        //recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(eventAdapter);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
                eventAdapter.notifyDataSetChanged();
                loadEvents();
            }
        });
        //loadEvents();

        return  view;
    }


    @Override
    public void onRefresh() {
       loadEvents();
    }
    private void loadEvents() {
        swipeRefresh.setRefreshing(true);

        String URL = "http://192.168.43.103/event/";

        //progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = Utils.getRetrofit();

        //GambarServices service = retrofit.create(GambarServices.class);
        EventListAPI service = retrofit.create(EventListAPI.class);
//        Call<GambarResponse> call = service.getGambar();
        Call<EventResponse> call = service.getEvents();

        Log.d("Call", "----------------------------------------");

        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {

                EventResponse dataResponse = response.body();
                Log.d("MainActivity", "Status Code = " + response.code());
                if(dataResponse != null) {
                    if(dataResponse.getData().size() > 0) {
                        eventAdapter.refresh(dataResponse.getData());
                    }
                }

                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.d("onFailure", "");
                swipeRefresh.setRefreshing(false);
            }
        });


    }
}
