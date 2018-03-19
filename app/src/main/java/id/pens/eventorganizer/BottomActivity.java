package id.pens.eventorganizer;

import android.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.pens.eventorganizer.fragment.CariFragment;
import id.pens.eventorganizer.fragment.Home;

import id.pens.eventorganizer.fragment.Notification;
import id.pens.eventorganizer.fragment.Profile;
import id.pens.eventorganizer.lib.BottomNavigationViewHelper;
import id.pens.eventorganizer.lib.SharedPreferenceHelper;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.services.APIService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BottomActivity extends AppCompatActivity {

    APIService apiService;
    //private TextView mTextMessage;

   // private MenuItem mHome, mNotif, mSearch, mProfile;
    private Context ctx;

    boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationItemView mHome, mNotif, mSearch, mProfile;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                   // FragmentManager fm = getSupportFragmentManager();
                    Home h = Home.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_content,new Home())
                            .commit();


                    return true;
                case R.id.nav_search:
                   // mTextMessage.setText(R.string.title_dashboard);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_content, new CariFragment())
                            .commit();
                    return true;
                case R.id.nav_notif:
                    //mTextMessage.setText(R.string.title_notifications);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_content, new Notification())
                            .commit();
                    return true;
                case R.id.nav_profile:
                    //mTextMessage.setText("profile");
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_content, new Profile())
                            .commit();
                    return true;
            }
            return false;
        }

    };

    //private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.dispo
        setContentView(R.layout.activity_bottom);
        ctx = this;
        subscribeToPushService();
       // mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mHome = (BottomNavigationItemView) findViewById(R.id.nav_home);
        mSearch = (BottomNavigationItemView) findViewById(R.id.nav_search);
        mNotif = (BottomNavigationItemView) findViewById(R.id.nav_notif);
        mProfile = (BottomNavigationItemView) findViewById(R.id.nav_profile);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame_content, new Home());
        tx.commit();
        Retrofit retrofit = Utils.getRetrofit();
        apiService = retrofit.create(APIService.class);
        sendRegID();
    }

    private  void sendRegID() {
        String token = FirebaseInstanceId.getInstance().getToken();
        String user_id = SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(), "user_id", "");
        if( !user_id.equals("")) { //ada isinya
            apiService.sendRegID(user_id,token).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                   // Log.e("EventOrg" ,response.body().toString());
                    if(response.isSuccessful()) {

                        try{
                            JSONObject jObject  = new JSONObject(response.body().string());
                            if(jObject.getString("success").equals("true")) {
                                JSONObject data = jObject.getJSONObject("data");
                                String token = data.getString("token");
                                SharedPreferenceHelper.setSharedPreferenceString(ctx, "reg_id", token);
                                Log.d("EventOrg : ", token);

                            }else{

                            }
                        }catch (JSONException jE) {

                        }catch (IOException iE) {

                        }
                    }else {
                        Log.e("EventOrg", "Not sucees");
                       // loading.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

            //Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_LONG).show();
        }
        // apiService.sendRegID();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("eticket");

        Log.d("AndroidBash", "Subscribed");
        // Toast.makeText(LoginActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();

        //String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        //Log.d("AndroidBash", token);
        //Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
    }
}
