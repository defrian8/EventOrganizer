package id.pens.eventorganizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import id.pens.eventorganizer.lib.RetrofitClient;
import id.pens.eventorganizer.lib.SharedPreferenceHelper;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.services.APIService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btnLogin, btnRegister;
    Context ctx;
    APIService apiService;
    RelativeLayout container;
    AnimationDrawable anim;
    TextView tvSignup;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().hide();
        ctx = this;

        ///

        //
        container = (RelativeLayout) findViewById(R.id.container);
        anim = (AnimationDrawable) container.getBackground();
        //anim.setEnterFadeDuration(10000);
        //anim.setExitFadeDuration(5000);
        etUsername = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin   = (Button) findViewById(R.id.btnLogin);
        btnRegister   = (Button) findViewById(R.id.btnRegister);
        tvSignup = (TextView) findViewById(R.id.tv_login_sign_up);
        Retrofit retrofit = Utils.getRetrofit();
        apiService = retrofit.create(APIService.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(ctx,null, "Loading...", true, false);
                requestLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctx, RegisterActivity.class));
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctx, RegisterActivity.class));
            }
        });
        Boolean is_loggin = SharedPreferenceHelper.getSharedPreferenceBoolean(ctx, "is_loggin", false);

       if(is_loggin) {
           startActivity(new Intent(LoginActivity.this, BottomActivity.class)
                   .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
           finish();
       }


    }

    private void requestLogin() {
        apiService.loginRequest(etUsername.getText().toString(), etPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            loading.dismiss();
                            try{
                                JSONObject jObject  = new JSONObject(response.body().string());
                                if(jObject.getString("success").equals("true")) {
                                    JSONObject data = jObject.getJSONObject("data");
                                    String nama = data.getString("name");
                                    String uid  = data.getString("uid");
                                    String username = data.getString("username");
                                    String auth_key = data.getString("auth");
                                    SharedPreferenceHelper.setSharedPreferenceString(ctx, "user_nama", nama);
                                    SharedPreferenceHelper.setSharedPreferenceString(ctx, "user_auth", auth_key);
                                    SharedPreferenceHelper.setSharedPreferenceString(ctx, "user_username", username);
                                    SharedPreferenceHelper.setSharedPreferenceString(ctx, "user_id", uid);
                                    SharedPreferenceHelper.setSharedPreferenceBoolean(ctx, "is_loggin", true);
                                   // Toast.makeText(ctx, "Haii " + nama, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ctx, BottomActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                }else{
                                    Snackbar snackbar = Snackbar.make(container, jObject.getString("message"), Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }catch (JSONException jE) {

                            }catch (IOException iE) {

                            }
                        }else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }



}
