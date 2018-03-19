package id.pens.eventorganizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.pens.eventorganizer.lib.SharedPreferenceHelper;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.model.DetailEvent;
import id.pens.eventorganizer.model.JenisTiketResponse;
import id.pens.eventorganizer.model.User;
import id.pens.eventorganizer.model.UserResponse;
import id.pens.eventorganizer.services.APIService;
import id.pens.eventorganizer.services.DetailEventAPI;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderCreate extends AppCompatActivity {

    TextView tvNama, tvAlamat, tvEmail, tvTelp, tvNamaTiket, tvHargaTiket, tvTotal;
    Button btnCreate;
    String ID_EVENT,ID_JENIS_TIKET, ID_USER;
    RelativeLayout layout;
    ProgressDialog loading;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ctx = this;
        ID_EVENT = intent.getStringExtra("id_event");
        ID_JENIS_TIKET = intent.getStringExtra("id_jenis_tiket");
        String Nama_Tiket = intent.getStringExtra("nama_tiket");
        String Harga_Tiket = intent.getStringExtra("harga_tiket");
        Integer harga_ = intent.getIntExtra("harga_tiket",0);
        //Log.e("EventOrg harg", harga_.toString());
        Log.e("EventOrg har" , Harga_Tiket);
        setContentView(R.layout.activity_order_create);
        tvNama = (TextView) findViewById(R.id.tv_order_create_nama_peserta);
        tvAlamat = (TextView) findViewById(R.id.tv_order_create_alamat);
        tvEmail = (TextView) findViewById(R.id.tv_order_create_email);
        tvTelp = (TextView) findViewById(R.id.tv_order_create_telp);
        tvNamaTiket = (TextView) findViewById(R.id.tv_order_create_nama_tiket);
        tvHargaTiket = (TextView) findViewById(R.id.tv_order_create_harga);
        tvTotal = (TextView) findViewById(R.id.tv_order_create_total);
        btnCreate = (Button) findViewById(R.id.btn_order_create);

        tvHargaTiket.setText(Harga_Tiket.toString());
        tvNamaTiket.setText(Nama_Tiket);
        tvTotal.setText(Harga_Tiket.toString());
        layout = (RelativeLayout) findViewById(R.id.rel_create_order);

        Retrofit retrofit = Utils.getRetrofit();
        APIService service = retrofit.create(APIService.class);
        ID_USER = SharedPreferenceHelper.getSharedPreferenceString(this, "user_id","1");
        Call<UserResponse> call_user = service.getUserInfo(ID_USER);
        call_user.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.body().getData().getId() != null) {
                    User user = response.body().getData();
                    tvNama.setText(user.getNama());
                    tvEmail.setText(user.getEmail());
                    tvTelp.setText(user.getPhone());
                    tvAlamat.setText(user.getAddress());

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
//        Log.e("EventOrg", intent.getStringExtra("id_jenis_tiket"));
//        tvHargaTiket.setText(intent.getStringExtra("harga_tiket"));
  //     tvNamaTiket.setText(intent.getStringExtra("nama_tiket"));

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(ctx,null, "Loading...", true, false);
                createOrder();
            }
        });
    }

    private  void createOrder() {
        APIService apiService;
        Retrofit retrofit = Utils.getRetrofit();
        apiService = retrofit.create(APIService.class);
        apiService.createOrder(ID_USER, ID_EVENT, ID_JENIS_TIKET).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    loading.dismiss();
                    try{
                        JSONObject jObject  = new JSONObject(response.body().string());
                        if(jObject.getString("success").equals("true")) {
                            startActivity(new Intent(ctx, OrderDoneActivity.class));
                        }else {
                            Snackbar snackbar = Snackbar.make(layout, jObject.getString("message"), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }catch (JSONException jE) {

                    }catch (IOException iE) {

                    }
                }else{
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
