package id.pens.eventorganizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.pens.eventorganizer.lib.SharedPreferenceHelper;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.services.APIService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    Context ctx;
    APIService apiService;
    EditText etName, etUsername, etPassword, etEmail, etPhone;
    Button btnRegister;
    RelativeLayout container;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ctx = this;
        etName =(EditText) findViewById(R.id.et_register_nama);
        etEmail = (EditText) findViewById(R.id.et_register_email);
        etPassword = (EditText) findViewById(R.id.et_register_password);
        etPhone = (EditText) findViewById(R.id.et_register_phone);
        etUsername = (EditText) findViewById(R.id.et_register_username);
        btnRegister = (Button) findViewById(R.id.btn_register_Register);
        container = (RelativeLayout) findViewById(R.id.lay_register);

        Retrofit retrofit = Utils.getRetrofit();
        apiService = retrofit.create(APIService.class);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(ctx,null, "Loading...", true, false);
                requestRegister();
            }
        });
    }

    private void requestRegister() {

        apiService.registerRequest(etUsername.getText().toString(),
                etName.getText().toString(), etEmail.getText().toString(), etPhone.getText().toString(), etPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            loading.dismiss();
                            try{
                                JSONObject jObject  = new JSONObject(response.body().string());
                                if(jObject.getString("success").equals("true")) {
                                    JSONObject data = jObject.getJSONObject("data");
                                    //String nama = data.getString("name");
                                    //Toast.makeText(ctx, "Haii " + nama, Toast.LENGTH_SHORT).show();
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
                                }else {
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
}
