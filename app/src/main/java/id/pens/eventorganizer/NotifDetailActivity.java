package id.pens.eventorganizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
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

public class NotifDetailActivity extends AppCompatActivity {

    Context ctx;
    TextView tvMessage, tvJudul, tvTgl;

    APIService apiService;
    String ID_NOTIF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_detail);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_my_notif);
        mToolbar.setTitle("Notification");
        mToolbar.setNavigationIcon(R.drawable.ic_backk);
        mToolbar.setBackgroundColor(Color.parseColor("#1E88E5"));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Retrofit retrofit= Utils.getRetrofit();
        tvJudul = (TextView) findViewById(R.id.tv_notif_judul);
        tvTgl = (TextView) findViewById(R.id.tv_notif_tanggal);
        tvMessage = (TextView) findViewById(R.id.tv_notif_message);
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
                            tvJudul.setText(data.getString("title"));
                            tvMessage.setText(Html.fromHtml(data.getString("message")));
                            tvTgl.setText(data.getString("time"));
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
