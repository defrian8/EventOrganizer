package id.pens.eventorganizer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.model.EventResponse;
import id.pens.eventorganizer.model.OrderDetail;
import id.pens.eventorganizer.model.OrderDetailResponse;
import id.pens.eventorganizer.model.User;
import id.pens.eventorganizer.services.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailOrder extends AppCompatActivity {


    TextView tvNama,tvEmail,tvPhone,tvTiket,tvHarga, tvTotal, tvStatus, tvBatas;
    Button btnDone, btnUpload;
    String harga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        Intent intent = getIntent();
        final String ID_ORDER = intent.getStringExtra("ID_ORDER");
        //final String harga = "";
        tvNama = (TextView)findViewById(R.id.detail_nama);
        tvEmail = (TextView) findViewById(R.id.detail_email);
        tvPhone = (TextView) findViewById(R.id.detail_phone);
        tvTiket = (TextView) findViewById(R.id.detail_nama_tiket);
        tvHarga = (TextView) findViewById(R.id.detail_harga);
        tvTotal = (TextView) findViewById(R.id.detail_total);
        tvStatus = (TextView) findViewById(R.id.order_detail_status);
        tvBatas = (TextView) findViewById(R.id.order_batas_pembayaran);
        btnDone = (Button) findViewById(R.id.btn_order_detail_done) ;
        btnUpload = (Button) findViewById(R.id.btn_kirim_bukti);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_my_order_detail);
        mToolbar.setTitle("Order Detail");
        mToolbar.setNavigationIcon(R.drawable.ic_backk);
        mToolbar.setBackgroundColor(Color.parseColor("#1E88E5"));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Retrofit retrofit = Utils.getRetrofit();
        APIService service = retrofit.create(APIService.class);
        Call<OrderDetailResponse> call = service.getOrderDetail(ID_ORDER);
        call.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<OrderDetailResponse> call, Response<OrderDetailResponse> response) {
                OrderDetailResponse dataResponse = response.body();
                Log.d("MainActivity", "Status Code = " + response.code());
                if(response.body().getData().getIdOrder() != null) {
                    //User user = response.body().getData();
                    OrderDetail orderDetail  = response.body().getData();
                    tvNama.setText(orderDetail.getNamaPeserta());
                    tvEmail.setText(orderDetail.getEmail());
                    tvPhone.setText(orderDetail.getPhone());
                    if(orderDetail.getStatusOrder() == 1){
                        tvStatus.setText("Belum Membayar");
                        tvBatas.setText(orderDetail.getExpired_time());
                        btnUpload.setVisibility(View.VISIBLE);
                    }
                    if(orderDetail.getStatusOrder() == 2)
                    {
                        tvStatus.setText("Sedang Diproses");
                        btnUpload.setVisibility(View.INVISIBLE);
                    }
                    if(orderDetail.getStatusOrder() == 3) {
                        tvStatus.setText("Selesai");
                        btnUpload.setVisibility(View.INVISIBLE);
                    }
                    tvTiket.setText(orderDetail.getJenisTiket());
                    tvHarga.setText(orderDetail.getHarga().toString());
                    tvTotal.setText(orderDetail.getHarga().toString());
                    harga = orderDetail.getHarga().toString();
                    //tvNama.setText(user.getNama());

                }
            }

            @Override
            public void onFailure(Call<OrderDetailResponse> call, Throwable t) {

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), KirimBuktiActivity.class);
                i.putExtra("ID_ORDER" , ID_ORDER);
                i.putExtra("harga", harga);
                startActivity(i);
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
