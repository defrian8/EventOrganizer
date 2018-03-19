package id.pens.eventorganizer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.pens.eventorganizer.lib.ImageLoader;
import id.pens.eventorganizer.model.DetailEvent;
import id.pens.eventorganizer.services.DetailEventAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import  id.pens.eventorganizer.lib.Utils;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private RecyclerView recyclerView;
    private Menu collapsedMenu;
    private TextView txtDetail, txtJudul, txtTanggal, txtKapasitas, txtLokasi, txtHarga;
    private ImageView imgHeader, imgTest;
    private Button btnAttend;
    private DessertAdapter dessertAdapter;
    private boolean appBarExpanded = true;
    private GoogleMap mMap;
    String ID_EVENT;
    String LANG;
    String LAT;
    String HARGA_TIKET;
    ArrayList<HashMap<String, String>> contactList;
    private SupportMapFragment mMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        ID_EVENT = intent.getStringExtra("id_event");
        LAT = intent.getStringExtra("LAT");
        LANG = intent.getStringExtra("LANG");
        HARGA_TIKET = intent.getStringExtra("HARGA");
        txtDetail = (TextView) findViewById(R.id.txt_desc);
        imgHeader =(ImageView) findViewById(R.id.header);
        imgTest = (ImageView) findViewById(R.id.imgTest);
        txtJudul = (TextView) findViewById(R.id.detail_event_title);
        btnAttend = (Button) findViewById(R.id.detail_btnAttend);
        txtTanggal = (TextView) findViewById(R.id.detail_event_tanggal);
        txtKapasitas =(TextView) findViewById(R.id.detail_event_kapasitas);
        txtLokasi = (TextView) findViewById(R.id.detail_event_lokasi);
        txtHarga=(TextView) findViewById(R.id.detail_event_tiket);
        if(LANG.equals("") || LANG.equals("0")) {
            LANG = "112.8086256980896";
        }
        if(LAT.equals("") ||  LAT.equals("0")) {
            LAT = "-7.273440544555624";
        }
        final String[] IMG_HEADER_URL = new String[1];
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.header);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);



       // Log.e("ssssss", "judulnya " + judul[0]);

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);

//        Picasso.with(this).load("https://baperdev.github.io/images/nature/2.jpg")
////                        .error(R.drawable.image_error)
////                        .placeholder(R.drawable.placeholder)
//                .into(imgHeader);



        String isi = "<p><strong>Tabir surya merupakan salah satu pelindung kulit dari dampak sinar matahari. </strong><strong>Sinar matahari memang bagus untuk kesehatan karena bisa merangsang tubuh memproduksi vitamin D. Namun di lain sisi, jika terkena sinar matahari (sinar ultraviolet A atau B) terlalu lama dan tanpa perlindungan, akibatnya bisa berdampak buruk bagi </strong><strong>warna dan kesehatan</strong><strong> kulit Anda.</strong></p>\n" +
                "\n" +
                "\t\t<p>Kulit akan terbakar dan menjadi hitam jika sinar ultraviolet terlalu lama dan tanpa perlindungan. Tidak hanya masalah kecantikan, sinar matahari juga bisa meningkatkan risiko mengalami . Salah satu cara agar terhindar dari efek buruk sinar matahari adalah dengan memakai tabir surya yang tepat.</p>\n" +
                "\t\t<p>Maka dari itu, penting untuk menggunakan tabir surya yang tepat. Dengan tabir surya, kulit akan terlindungi dari bahaya  yang bisa memicu produksi melanin, yaitu pigmen penentu warna kulit, yang membuat kulit menjadi gelap.</p>\n" +
                "\n" +
                "\t\t<h3><strong>Panduan Memilih Tabir Surya yang Tepat </strong></h3>\n" +
                "\t\t<p>Tabir surya melindungi Anda dari sinar ultraviolet yang berbahaya dengan dua cara. Ada tabir surya yang bekerja dengan cara memantulkan sinar UV agar tidak terkena kulit. Ada pula yang bekerja dengan menyerap sinar UV sebelum mengenai kulit Anda.</p>\n" +
                "\t\t<p>Dahulu kita hanya fokus mencari kandungan <em>sun protection factor (SPF)</em> yang tinggi saat mencari tabir surya. Lantaran SPF berfungsi melindungi kulit dari bahaya sinar ultraviolet B (UVB), yaitu kulit terbakar, menghitam, dan kanker kulit. Namun penelitian terbaru mengungkapkan bahwa sinar ultraviolet A (UVA) juga bisa meningkatkan risiko kanker kulit dan menyebabkan keriput, meski tidak bisa membuat kulit terbakar.</p>\n" +
                "\n" +
                "\t\t<p>Agar Anda mendapat perlindungan maksimal, disarankan memilih tabir surya yang memiliki kandungan berikut ini:</p>\n" +
                "\n" +
                "\t\t<ul>\n" +
                "<li>Pilih tabir surya dengan kadar SPF 15 atau lebih. Kadar tersebut menunjukkan seberapa efektif tabir surya mencegah kulit Anda terbakar akibat sinar UVB. Contohnya, jika kulit Anda akan terbakar dalam waktu 10 menit. Maka tabir surya SPF 15 bisa melindungi kulit Anda selama 150 menit sebelum terbakar dan akhirnya menjadi hitam. Bagi sebagian orang, memakai tabir surya SPF 15 sudah cukup. Kadar SPF yang lebih tinggi hanya diperuntukkan bagi mereka yang memiliki kulit sangat putih, atau memiliki kondisi seperti riwayat kanker kulit atau lupus.</li>\n" +
                "</ul>\n" +
                "\t\\";


        
        //txtDetail.setText(Html.fromHtml(isi));

       // recyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        //  Use when your list size is constant for better performance
        //recyclerView.setHasFixedSize(true);

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
       // recyclerView.setLayoutManager(linearLayoutManager);

        //dessertAdapter = new DessertAdapter(this);
        //recyclerView.setAdapter(dessertAdapter);



        Retrofit retrofit = Utils.getRetrofit();
        DetailEventAPI service = retrofit.create(DetailEventAPI.class);
        Call<DetailEvent> call = service.getDetails(ID_EVENT);

        call.enqueue(new Callback<DetailEvent>() {
            @Override
            public void onResponse(Call<DetailEvent> call, retrofit2.Response<DetailEvent> response) {
                if(response.body().getData().getTitle() != null) {

                    collapsingToolbar.setTitle(response.body().getData().getTitle());
                    final String url_ = response.body().getData().getImageUrl();
                    Picasso.with(DetailActivity.this).load(url_)
////                        .error(R.drawable.image_error)
////                        .placeholder(R.drawable.placeholder)
                            .into(imgHeader);
                    LAT = "-7.2922042163278995";
                            LANG = "112.72558450698853";
//                    DetailActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Picasso.with(DetailActivity.this).load(url_)
//////                        .error(R.drawable.image_error)
//////                        .placeholder(R.drawable.placeholder)
//                           .into(imgHeader);
//                        }
//                    });
                    txtJudul.setText(Html.fromHtml(response.body().getData().getTitle()));
                    txtDetail.setText(Html.fromHtml(response.body().getData().getDesc()));
                    txtTanggal.setText(response.body().getData().getTanggal());
                    txtLokasi.setText(response.body().getData().getLokasi());
                    txtKapasitas.setText(response.body().getData().getKapasitas());
                    txtHarga.setText(response.body().getData().getHarga_tiket().toString());

                   // HARGA_TIKET = response.body().getData().getTiket();

                }
            }

            @Override
            public void onFailure(Call<DetailEvent> call, Throwable t) {

            }
        });
        btnAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), OrderSelectTicket.class);
                i.putExtra("id_event", ID_EVENT);

                if(HARGA_TIKET == null || HARGA_TIKET.equals("")) {
                    Toast.makeText(getApplicationContext(), "Tidak ada tiket yang tersedia", Toast.LENGTH_LONG).show();
                }else {
                    view.getContext().startActivity(i);
                }
            }
        });
       // Log.e("IMG---------------- : ", IMG_HEADER_URL[0]);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(MainActivity.class.getSimpleName(), "onOffsetChanged: verticalOffset: " + verticalOffset);

                //  Vertical offset == 0 indicates appBar is fully expanded.
                if (Math.abs(verticalOffset) > 200) {
                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    appBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collapsedMenu != null
                && (!appBarExpanded || collapsedMenu.size() != 1)) {
            //collapsed
            collapsedMenu.add("share")
                    .setIcon(R.drawable.ic_share)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else {
            //expanded
        }
        return super.onPrepareOptionsMenu(collapsedMenu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        collapsedMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
        }
        if (item.getTitle() == "share") {
            Toast.makeText(this, "clicked add", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(LAT), Double.parseDouble(LANG));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Event Location"));
        //mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney), 12.0f);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 19.0f));
    }

}
