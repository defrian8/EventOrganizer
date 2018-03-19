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
import id.pens.eventorganizer.adapter.MyOrderAdapter;
import id.pens.eventorganizer.adapter.MyOrganizerAdapter;
import id.pens.eventorganizer.lib.SharedPreferenceHelper;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.model.MyOrder;
import id.pens.eventorganizer.model.MyOrderResponse;
import id.pens.eventorganizer.model.Organizer;
import id.pens.eventorganizer.model.OrganizerResponse;
import id.pens.eventorganizer.services.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Profile_organizer extends Fragment {

    private Retrofit retrofit;
    APIService service;
    String ID_USER;
    MyOrganizerAdapter adapter;

    public Profile_organizer() {
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
        View view = inflater.inflate(R.layout.fragment_profile_organizer, container, false);
        retrofit = Utils.getRetrofit();
        service = retrofit.create(APIService.class);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_my_organizer);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyOrganizerAdapter(getActivity(), new ArrayList<Organizer>());
        recyclerView.setAdapter(adapter);
        loadOrg();
        return view;
    }

    private void loadOrg() {
        ID_USER = SharedPreferenceHelper.getSharedPreferenceString(getContext(), "user_id","1");
        Call<OrganizerResponse> call = service.getOrgList(ID_USER);
        call.enqueue(new Callback<OrganizerResponse>() {
            @Override
            public void onResponse(Call<OrganizerResponse> call, Response<OrganizerResponse> response) {
                OrganizerResponse data = response.body();
//                Log.e("id.pens", data.toString());
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
            public void onFailure(Call<OrganizerResponse> call, Throwable t) {

            }
        });
    }
}
