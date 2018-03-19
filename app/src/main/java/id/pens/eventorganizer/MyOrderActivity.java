package id.pens.eventorganizer;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import id.pens.eventorganizer.fragment.Order_done;
import id.pens.eventorganizer.fragment.Order_onProccess;
import id.pens.eventorganizer.fragment.Order_waiting;
import id.pens.eventorganizer.fragment.Profile;
import id.pens.eventorganizer.fragment.Profile_event;
import id.pens.eventorganizer.fragment.Profile_organizer;

public class MyOrderActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentActivity myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_my_order);
        mToolbar.setTitle("My Orders");
        mToolbar.setNavigationIcon(R.drawable.ic_backk);
        mToolbar.setBackgroundColor(Color.parseColor("#1E88E5"));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager_my_order);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_my_order);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentManager fragManager = getSupportFragmentManager();

        ViewPagerAdapter adapter = new ViewPagerAdapter(fragManager);
        adapter.addFragment(new Order_waiting(), "Waiting Payment");
        adapter.addFragment(new Order_onProccess(), "On Process");
        adapter.addFragment(new Order_done(), "Done");

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
}
