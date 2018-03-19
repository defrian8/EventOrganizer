package id.pens.eventorganizer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.EnumMap;
import java.util.Map;
import com.google.zxing.Result;

import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.model.DetailEvent;
import id.pens.eventorganizer.model.QrResponse;
import id.pens.eventorganizer.model.QrTiket;
import id.pens.eventorganizer.services.APIService;
import id.pens.eventorganizer.services.DetailEventAPI;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewticketActivity extends AppCompatActivity {


    TextView  tvNama, tvJenisTiket;
    ImageView imageEvent, imageTiket;
    MyTextView tvNamaEvent;
    Button btnDone;
    String ID_ORDER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewticket);
        Intent intent = getIntent();


        ID_ORDER = intent.getStringExtra("ID_ORDER");
        imageTiket = (ImageView) findViewById(R.id.imageview_ticket);
        imageEvent = (ImageView) findViewById(R.id.imageview_ticket);
        tvNamaEvent = (MyTextView) findViewById(R.id.tv_nama_event);
        tvNama = (TextView) findViewById(R.id.tv_tiket_nama_attender);
        tvJenisTiket = (TextView) findViewById(R.id.tv_tiket_jenis_tiket);
        btnDone = (Button) findViewById(R.id.btn_view_ticket_done);


        String text2Qr = "ini untuk qrcode";
        Retrofit retrofit = Utils.getRetrofit();
        //DetailEventAPI service = retrofit.create(DetailEventAPI.class);
        APIService service = retrofit.create(APIService.class);
        Call<QrResponse> call = service.getQrTiket(ID_ORDER);
//        Call<DetailEvent> call = service.getDetails(ID_EVENT);
        call.enqueue(new Callback<QrResponse>() {
            @Override
            public void onResponse(Call<QrResponse> call, Response<QrResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getSuccess().equals("true")){
                        QrTiket tiket = response.body().getData();
                        tvNamaEvent.setText(tiket.getNamaEvent());
                        tvNama.setText(tiket.getNamaPeserta());
                        tvJenisTiket.setText(tiket.getJenisTiket());
                        BuatQR(tiket.getKodeQr());
                    }else{
                        Toast.makeText(getApplicationContext(),
                                response.body().getMessage().toString(),
                                Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QrResponse> call, Throwable t) {

            }
        });
        //BuatQR(text2Qr);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void BuatQR(String var) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 2); /* default = 4 */
            BitMatrix bitMatrix = multiFormatWriter.encode(var, BarcodeFormat.QR_CODE,500,500, hints);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageTiket.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}
