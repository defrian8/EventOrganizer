package id.pens.eventorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.services.APIService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WelcomeEventActivity extends AppCompatActivity {

    TextView tvNamaEvent;

    APIService apiService;
    String ID_NOTIF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_event);
        Retrofit retrofit= Utils.getRetrofit();
        tvNamaEvent = (TextView) findViewById(R.id.tv_nama_event);
        apiService = retrofit.create(APIService.class);
        Intent intent = getIntent();
        ID_NOTIF = intent.getStringExtra("ID_NOTIF");
        Log.e(NotifDetailActivity.class.getSimpleName(), ID_NOTIF);
        loadNotif();
    }

    private void loadNotif() {
        apiService.getNotifDetail(ID_NOTIF).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {

                    try {
                        JSONObject jObject = new JSONObject(response.body().string());
                        Log.e(NotifDetailActivity.class.getSimpleName(), jObject.toString());
                        if(jObject.getString("success").equals("true")) {
                            JSONObject data = jObject.getJSONObject("data");
                            tvNamaEvent.setText(data.getString("nama_event"));
                            Log.e(NotifDetailActivity.class.getSimpleName(), data.getString("title"));
                        }else {
                            Log.e(NotifDetailActivity.class.getSimpleName(),"error");
                        }

                    }catch (JSONException jE) {

                    }catch (IOException iE) {

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
