package id.pens.eventorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.pens.eventorganizer.fragment.Attender;
import id.pens.eventorganizer.fragment.AttenderCheck;
import id.pens.eventorganizer.fragment.Home;

public class DashboarOrganizer extends AppCompatActivity {

    ImageView imgList, imgScan, imgCheck;
    TextView txtNama_event;
    String ID_EVENT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboar_organizer);
        Intent i = getIntent();
        ID_EVENT = i.getStringExtra("ID_EVENT");

        imgList = (ImageView) findViewById(R.id.ListAttender);
        imgScan = (ImageView) findViewById(R.id.scanQR);
        imgCheck = (ImageView) findViewById(R.id.list_att_check);
        txtNama_event = (TextView) findViewById(R.id.tv_nama_event);
        txtNama_event.setText(i.getStringExtra("NAMA_EVENT"));
        imgList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_content,new Attender())
                        .commit();
            }
        });

        imgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_content,new AttenderCheck())
                        .commit();
            }
        });

        imgScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboarOrganizer.this, ScanActivity.class);
                i.putExtra("ID_EVENT", ID_EVENT);
                startActivity(i);
            }
        });

    }
}
