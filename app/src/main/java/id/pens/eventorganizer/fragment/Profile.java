package id.pens.eventorganizer.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.pens.eventorganizer.R;
import id.pens.eventorganizer.lib.SharedPreferenceHelper;
import id.pens.eventorganizer.model.User;
import id.pens.eventorganizer.model.UserResponse;
import id.pens.eventorganizer.services.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import id.pens.eventorganizer.lib.Utils;

public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Retrofit retrofit;
    APIService service;
    String ID_USER;
    TextView tvNama, tvUsername;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentActivity myContext;
    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile, container, false);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        retrofit = Utils.getRetrofit();
        service = retrofit.create(APIService.class);
        tvNama = (TextView) view.findViewById(R.id.tv_profile_name);
        tvUsername = (TextView) view.findViewById(R.id.tv_profile_username);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        loadProfile();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    private void setupViewPager(ViewPager viewPager) {
        FragmentManager fragManager = getChildFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragManager);
        adapter.addFragment(new Profile_event(), "MY ORDER");
        adapter.addFragment(new Profile_organizer(), "MY ORGANIZER");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private void loadProfile() {
        ID_USER = SharedPreferenceHelper.getSharedPreferenceString(getContext(), "user_id","1");
        Call<UserResponse> call_user = service.getUserInfo(ID_USER);
        call_user.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.body().getData().getId() != null) {
                    User user = response.body().getData();
                    tvNama.setText(user.getNama().toString());

                    tvUsername.setText(user.getUserName().toString());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }

    private void OrganizerLoadEvent() {

    }
}
