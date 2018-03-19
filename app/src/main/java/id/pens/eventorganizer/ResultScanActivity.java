package id.pens.eventorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultScanActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_scan);
        tv = (TextView) findViewById(R.id.tvResult);
        Intent intet = getIntent();
        String qr =intet.getStringExtra("qr");
        tv.setText(qr);
    }
}
