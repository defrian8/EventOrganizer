package id.pens.eventorganizer.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.pens.eventorganizer.R;
import id.pens.eventorganizer.adapter.AttenderList;
import id.pens.eventorganizer.adapter.AttenderListCheck;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.model.AttendCheck;
import id.pens.eventorganizer.model.AttendCheckResponse;
import id.pens.eventorganizer.model.AttendList;
import id.pens.eventorganizer.model.AttendListResponse;
import id.pens.eventorganizer.services.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AttenderCheck extends Fragment {

    private Retrofit retrofit;
    APIService service;
    String ID_USER;
    AttenderListCheck adapter;
    public AttenderCheck() {
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
        View view = inflater.inflate(R.layout.fragment_attender_check, container, false);
        retrofit = Utils.getRetrofit();
        service = retrofit.create(APIService.class);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_attender_list_check);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AttenderListCheck(getActivity(), new ArrayList<AttendCheck>());
        recyclerView.setAdapter(adapter);
        loadData();
        return view;
    }

    private void loadData() {
        String ID_EVENT = getActivity().getIntent().getExtras().getString("ID_EVENT");
        Log.e("id.pens.ev", "id event" + ID_EVENT);
        Call<AttendCheckResponse> call = service.getAttenderCheck(ID_EVENT);
        call.enqueue(new Callback<AttendCheckResponse>() {
            @Override
            public void onResponse(Call<AttendCheckResponse> call, Response<AttendCheckResponse> response) {
                AttendCheckResponse data = response.body();
                Log.e("id.pens.ev", data.toString());
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
            public void onFailure(Call<AttendCheckResponse> call, Throwable t) {

            }
        });

    }
}
