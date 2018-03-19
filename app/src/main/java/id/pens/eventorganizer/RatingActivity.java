package id.pens.eventorganizer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import id.pens.eventorganizer.lib.SharedPreferenceHelper;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.model.UserResponse;
import id.pens.eventorganizer.services.APIService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RatingActivity extends AppCompatActivity {

    TextView tvNama, tvTanggal;
    EditText comment;
    RatingBar ratingBar;
    Button btnDone;
    String ID_EVENT,ID_USER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Intent i = getIntent();
        ID_USER = SharedPreferenceHelper.getSharedPreferenceString(this, "user_id","1");
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_rating);
        mToolbar.setTitle("Give Rating");
        mToolbar.setNavigationIcon(R.drawable.ic_backk);
        mToolbar.setBackgroundColor(Color.parseColor("#1E88E5"));
        setSupportActionBar(mToolbar);
        tvNama =(TextView) findViewById(R.id.tv_ratingNama_event) ;
        tvTanggal = (TextView) findViewById(R.id.tv_rating_tanggal);
        btnDone = (Button) findViewById(R.id.btnDone);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tvNama.setText(i.getStringExtra("NAMA_EVENT"));
        tvTanggal.setText(i.getStringExtra("TANGGAL"));
        ID_EVENT = i.getStringExtra("ID_EVENT");
        comment = (EditText) findViewById(R.id.comment);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#FDD835"), PorterDuff.Mode.SRC_ATOP);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Float rating = ratingBar.getRating();
                Integer ratingKU = Math.round(rating);
                APIService apiService;
                Retrofit retrofit = Utils.getRetrofit();
                apiService = retrofit.create(APIService.class);
                Call<ResponseBody> call = apiService.giveRating(ID_USER,
                        ID_EVENT,
                        ratingKU.toString(),
                        comment.getText().toString());

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){

                            try{
                                JSONObject jObject  = new JSONObject(response.body().string());
                                if(jObject.getString("success").equals("true")) {
                                    Toast.makeText(getApplicationContext(), jObject.getString("message").toString(), Toast.LENGTH_SHORT).show();
                                }
                            }catch (JSONException jE) {
                                Toast.makeText(getApplicationContext(), "Failed!! " + jE.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }catch (IOException iE) {
                                Toast.makeText(getApplicationContext(), "Failed!! " + iE.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Failed!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }
}
