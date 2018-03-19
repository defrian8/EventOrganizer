package id.pens.eventorganizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import id.pens.eventorganizer.lib.ApiUtils;
import id.pens.eventorganizer.model.Rumah;
import id.pens.eventorganizer.services.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KirimActivity extends AppCompatActivity {

    private TextView mResponseTv;
    private APIService mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim);

        final EditText edNama = (EditText) findViewById(R.id.et_nama);
        final EditText edType = (EditText) findViewById(R.id.et_type);
        final EditText edHarga = (EditText) findViewById(R.id.et_harga);

        Button submitBtn = (Button) findViewById(R.id.btn_submit);
        //mResponseTv = (TextView) findViewById(R.id.tv_response);

        mAPIService = ApiUtils.getAPIService();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = edNama.getText().toString().trim();
                String type = edType.getText().toString().trim();
                String harga = edHarga.getText().toString().trim();

                if(!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(type)) {
                    sendPost(nama, type, Integer.parseInt(harga));
                }
            }
        });
    }
    public void sendPost(String nama, String type, Integer harga) {
        mAPIService.saveRumah(nama, type, harga).enqueue(new Callback<Rumah>() {
            @Override
            public void onResponse(Call<Rumah> call, Response<Rumah> response) {

                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i("Kirim", "post submitted to API." + response.body().toString());

                }
            }

            @Override
            public void onFailure(Call<Rumah> call, Throwable t) {
                Log.e("Kirim", "Unable to submit post to API.");
            }
        });
    }

    public void showResponse(String response) {

    }
}
