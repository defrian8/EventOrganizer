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
import id.pens.eventorganizer.adapter.EventListAdapter;
import id.pens.eventorganizer.adapter.MyOrderAdapter;
import id.pens.eventorganizer.lib.SharedPreferenceHelper;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.model.ListEvent;
import id.pens.eventorganizer.model.MyOrder;
import id.pens.eventorganizer.model.MyOrderResponse;
import id.pens.eventorganizer.services.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Order_waiting extends Fragment {

    private Retrofit retrofit;
    APIService service;
    String ID_USER;
    MyOrderAdapter adapter;

    public Order_waiting() {
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
        View view = inflater.inflate(R.layout.fragment_order_waiting, container, false);
        retrofit = Utils.getRetrofit();
        service = retrofit.create(APIService.class);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_order_waiting);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyOrderAdapter(getActivity(), new ArrayList<MyOrder>());
        recyclerView.setAdapter(adapter);
        loadOrder();
        return view;
    }

    private void loadOrder() {
        ID_USER = SharedPreferenceHelper.getSharedPreferenceString(getContext(), "user_id","1");
        Call<MyOrderResponse> call_order = service.getOrder(ID_USER, "1");
        call_order.enqueue(new Callback<MyOrderResponse>() {
            @Override
            public void onResponse(Call<MyOrderResponse> call, Response<MyOrderResponse> response) {
                MyOrderResponse data = response.body();
                Log.e("id.pens", data.toString());
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
            public void onFailure(Call<MyOrderResponse> call, Throwable t) {

            }
        });

    }
}
