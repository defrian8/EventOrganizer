package id.pens.eventorganizer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.pens.eventorganizer.lib.SharedPreferenceHelper;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.services.APIService;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScanActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener,ActivityCompat.OnRequestPermissionsResultCallback  {

    Context ctx;
    QRCodeReaderView qrCodeReaderView;
    private TextView tvNama, tvJenis, tvStatus;
    private RelativeLayout mainLayout;
    private PointsOverlayView pointsOverlayView;
    private CheckBox flashlightCheckBox;
    private CheckBox enableDecodingCheckBox;
    private static final int MY_PERMISSION_REQUEST_CAMERA = 0;
    String ID_USER,ID_EVENT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_scan);
        Intent i = getIntent();
        ID_EVENT = i.getStringExtra("ID_EVENT");
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        tvNama = (TextView) findViewById(R.id.tv_scan_nama);
        tvJenis = (TextView) findViewById(R.id.tv_scan_jenis_tiket);
        tvStatus = (TextView) findViewById(R.id.tv_checkin_status);
        mainLayout = (RelativeLayout) findViewById(R.id.main_rel);
        pointsOverlayView = (PointsOverlayView) findViewById(R.id.points_overlay_view);
        flashlightCheckBox = (CheckBox) findViewById(R.id.flashlight_checkbox);
        enableDecodingCheckBox = (CheckBox) findViewById(R.id.enable_decoding_checkbox);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            //initQRCodeReaderView();
            qrCodeReaderView.setOnQRCodeReadListener(this);
            qrCodeReaderView.setQRDecodingEnabled(true);
            qrCodeReaderView.setAutofocusInterval(2000L);

            qrCodeReaderView.setBackCamera();
            flashlightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    qrCodeReaderView.setTorchEnabled(isChecked);
                }
            });
            enableDecodingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    qrCodeReaderView.setQRDecodingEnabled(isChecked);
                }
            });
        } else {
            requestCameraPermission();
        }
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        // add here your source code with scanning code
        pointsOverlayView.setPoints(points);
        TextView tv = (TextView) findViewById(R.id.tv_checkin_kode) ;
        tv.setText(text);
        getCheckin(text);
    }
    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(mainLayout, "Camera permission was granted.", Snackbar.LENGTH_SHORT).show();
            qrCodeReaderView.setOnQRCodeReadListener(this);
            qrCodeReaderView.setQRDecodingEnabled(true);
            qrCodeReaderView.setAutofocusInterval(2000L);
            qrCodeReaderView.setBackCamera();
            flashlightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    qrCodeReaderView.setTorchEnabled(isChecked);
                }
            });
            enableDecodingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    qrCodeReaderView.setQRDecodingEnabled(isChecked);
                }
            });
        } else {
            Snackbar.make(mainLayout, "Camera permission request was denied.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Snackbar.make(mainLayout, "Camera access is required to display the camera preview.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityCompat.requestPermissions(ScanActivity.this, new String[] {
                            Manifest.permission.CAMERA
                    }, MY_PERMISSION_REQUEST_CAMERA);
                }
            }).show();
        } else {
            Snackbar.make(mainLayout, "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.CAMERA
            }, MY_PERMISSION_REQUEST_CAMERA);
        }
    }

    private void getCheckin(String kode_tiket) {
        Retrofit retrofit = Utils.getRetrofit();
        APIService apiService = retrofit.create(APIService.class);
        ID_USER = SharedPreferenceHelper.getSharedPreferenceString(this, "user_id","1");
        apiService.checkIn(kode_tiket, ID_USER,ID_EVENT).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {

                    try{
                        JSONObject jObject  = new JSONObject(response.body().string());
                        if(jObject.getString("success").equals("true")) {
                            JSONObject data = jObject.getJSONObject("data");
                            tvNama.setText(data.getString("nama_peserta"));
                            tvJenis.setText(data.getString("jenis_tiket"));
                            tvStatus.setText(data.getString("message"));
                        }else{
                           tvStatus.setText(jObject.getString("message").toString());
                        }
                    }catch (JSONException jE) {

                    }catch (IOException iE) {

                    }
                }else {
                    Log.e("Scan", "error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
