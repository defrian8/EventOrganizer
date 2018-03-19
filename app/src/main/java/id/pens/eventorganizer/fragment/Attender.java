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
import id.pens.eventorganizer.adapter.MyOrderAdapter;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.model.AttendList;
import id.pens.eventorganizer.model.AttendListResponse;
import id.pens.eventorganizer.model.MyOrder;
import id.pens.eventorganizer.model.MyOrderResponse;
import id.pens.eventorganizer.services.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Attender extends Fragment {

    private Retrofit retrofit;
    APIService service;
    String ID_USER;
    AttenderList adapter;
    public Attender() {
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
        View view = inflater.inflate(R.layout.fragment_attender, container, false);
        retrofit = Utils.getRetrofit();
        service = retrofit.create(APIService.class);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_attender_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AttenderList(getActivity(), new ArrayList<AttendList>());
        recyclerView.setAdapter(adapter);
        loadData();
        return view;
    }

    private void loadData() {
        String ID_EVENT = getActivity().getIntent().getExtras().getString("ID_EVENT");
        Call<AttendListResponse> call = service.getAttender(ID_EVENT);
        call.enqueue(new Callback<AttendListResponse>() {
            @Override
            public void onResponse(Call<AttendListResponse> call, Response<AttendListResponse> response) {
                AttendListResponse data = response.body();

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
            public void onFailure(Call<AttendListResponse> call, Throwable t) {

            }
        });
    }
}
