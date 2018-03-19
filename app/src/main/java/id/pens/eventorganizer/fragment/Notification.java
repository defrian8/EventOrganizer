package id.pens.eventorganizer.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.pens.eventorganizer.R;
import id.pens.eventorganizer.adapter.EventListAdapter;
import id.pens.eventorganizer.adapter.NotifAdapter;
import id.pens.eventorganizer.lib.SharedPreferenceHelper;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.model.EventResponse;
import id.pens.eventorganizer.model.ListEvent;
import id.pens.eventorganizer.model.Notif;
import id.pens.eventorganizer.model.NotifResponse;
import id.pens.eventorganizer.services.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Notification extends Fragment {


    NotifAdapter adapter;
    String ID_USER;
    public Notification() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar_notification);
        mToolbar.setTitle("Notification");
        mToolbar.setBackgroundColor(Color.parseColor("#1E88E5"));
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_notification);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new NotifAdapter(getActivity(), new ArrayList<Notif>());
        recyclerView.setAdapter(adapter);
        loadData();
        return view;
    }

    private void loadData() {
        Retrofit retrofit = Utils.getRetrofit();
        APIService service = retrofit.create(APIService.class);
        ID_USER = SharedPreferenceHelper.getSharedPreferenceString(getContext(), "user_id","1");
        Call<NotifResponse> call = service.getUserNotif(ID_USER);
        call.enqueue(new Callback<NotifResponse>() {
            @Override
            public void onResponse(Call<NotifResponse> call, Response<NotifResponse> response) {
                NotifResponse dataResponse = response.body();
                Log.d("MainActivity", "Status Code = " + response.code());
                if(dataResponse != null) {
                    if(dataResponse.getData().size() > 0) {
                        adapter.refresh(dataResponse.getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<NotifResponse> call, Throwable t) {

            }
        });
    }
}
