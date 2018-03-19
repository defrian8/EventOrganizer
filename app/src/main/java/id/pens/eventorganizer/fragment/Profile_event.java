package id.pens.eventorganizer.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import id.pens.eventorganizer.MyEventDone;
import id.pens.eventorganizer.MyOrderActivity;
import id.pens.eventorganizer.MyTicketActivity;
import id.pens.eventorganizer.R;


public class Profile_event extends Fragment {

    ListView listView;
    String[] menu = {
            "My Orders",
            "My Ticket",
            "Give Rating"
    };
    public Profile_event() {
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
        View view = inflater.inflate(R.layout.fragment_profile_event, container, false);

        listView =(ListView) view.findViewById(R.id.listViewEvent);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list, menu);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, menu);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(getActivity(), MyOrderActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(getActivity(), MyTicketActivity.class);
                        startActivity(intent2);
                        break;
                    case  2:
                        Intent intent3 = new Intent(getActivity(), MyEventDone.class);
                        startActivity(intent3);
                }
            }
        });
        return view;
       // return inflater.inflate(R.layout.fragment_profile_event, container, false);
    }

}
