package id.pens.eventorganizer.fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import id.pens.eventorganizer.R;
import id.pens.eventorganizer.adapter.EventListAdapter;
import id.pens.eventorganizer.fragment.dummy.DummyContent;
import id.pens.eventorganizer.fragment.dummy.DummyContent.DummyItem;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.model.EventResponse;
import id.pens.eventorganizer.model.ListEvent;
import id.pens.eventorganizer.services.APIService;
import id.pens.eventorganizer.services.EventListAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;


public class CariFragment extends Fragment {

    EventListAdapter adapter;
    EditText etQuery;
    Button btnSubmit;
    ProgressDialog dialog;
    public CariFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CariFragment newInstance(int columnCount) {
        CariFragment fragment = new CariFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cari_list, container, false);
        setHasOptionsMenu(true);
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar_cari);
        mToolbar.setTitle("Search...");
        mToolbar.setBackgroundColor(Color.parseColor("#1E88E5"));
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_cari);
        etQuery = (EditText) view.findViewById(R.id.et_query);
        btnSubmit = (Button) view.findViewById(R.id.btnCari);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //adapter = new GambarAdapter(this, new ArrayList<Gambar>());
        adapter = new EventListAdapter(getActivity(), new ArrayList<ListEvent>());
        //recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.show();
                String query = etQuery.getText().toString();
                String URL = Utils.BASE_URL_API;

                //progressBar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                //GambarServices service = retrofit.create(GambarServices.class);

                APIService service = retrofit.create(APIService.class);
//        Call<GambarResponse> call = service.getGambar();
                Call<EventResponse> call = service.cariEvent(query);

                Log.d("Call", "----------------------------------------");

                call.enqueue(new Callback<EventResponse>() {
                    @Override
                    public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                        dialog.dismiss();
                        EventResponse dataResponse = response.body();
                        Log.d("MainActivity", "Status Code = " + response.code());
                        if(dataResponse != null) {
                            if(dataResponse.getData().size() > 0) {
                                adapter.refresh(dataResponse.getData());
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<EventResponse> call, Throwable t) {
                        Log.d("onFailure", "");
                        dialog.dismiss();
                    }
                });
            }
        });
        return view;
    }
}
