package id.pens.eventorganizer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import id.pens.eventorganizer.lib.SharedPreferenceHelper;
import id.pens.eventorganizer.lib.Utils;
import id.pens.eventorganizer.services.APIService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class KirimBuktiActivity extends AppCompatActivity {

    Button btnUpload, btnMulUpload, btnPickImage, btnPickVideo;
    String mediaPath, mediaPath1;
    TextView TVtotal;
    //ImageView imgView;
    String[] mediaColumns = {MediaStore.Video.Media._ID};
    ProgressDialog progressDialog;
    TextView str1, str2;
    ImageView kirim;
    String ID_ORDER, Harga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim_bukti);
        Intent intent = getIntent();
        ID_ORDER = intent.getStringExtra("ID_ORDER");
        Harga = intent.getStringExtra("harga");
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_kirim_bukti);
        TVtotal = (TextView) findViewById(R.id.tv_order_create_total);
        mToolbar.setTitle("Kirim Bukti Pembayaran");
        mToolbar.setNavigationIcon(R.drawable.ic_backk);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        TVtotal.setText(Harga);
        btnUpload = (Button) findViewById(R.id.kirim_btnKirim);

        kirim = (ImageView) findViewById(R.id.preview_upload);



        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                //str1.setText(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                kirim.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } // When an Video is picked
            else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

                // Get the Video from data
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                mediaPath1 = cursor.getString(columnIndex);
                //str2.setText(mediaPath1);
                // Set the Video Thumb in ImageView Previewing the Media
                kirim.setImageBitmap(getThumbnailPathForLocalFile(KirimBuktiActivity.this, selectedVideo));
                cursor.close();

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    // Providing Thumbnail For Selected Image
    public Bitmap getThumbnailPathForLocalFile(Activity context, Uri fileUri) {
        long fileId = getFileId(context, fileUri);
        return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
    }

    // Getting Selected File ID
    public long getFileId(Activity context, Uri fileUri) {
        Cursor cursor = context.managedQuery(fileUri, mediaColumns, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            return cursor.getInt(columnIndex);
        }
        return 0;
    }

    // Uploading Image/Video
    private void uploadFile() {
        progressDialog.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("foto", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), ID_ORDER);

        Retrofit retrofit = Utils.getRetrofit();
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> call = service.uploadFile(fileToUpload, filename);

       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if(response.isSuccessful())
               {
                   try{
                       JSONObject jObject  = new JSONObject(response.body().string());
                      //JSONObject data = jObject.getJSONObject("data");
                       if(jObject.getString("success").equals("true")) {
                           Toast.makeText(getApplicationContext(), jObject.getString("message"),
                                   Toast.LENGTH_SHORT).show();
                           //progressDialog.dismiss();
                       }else{
                            Toast.makeText(getApplicationContext(), jObject.getString("message"),
                                    Toast.LENGTH_SHORT).show();
                           //progressDialog.dismiss();
                       }
                   }catch (JSONException jE) {

                   }catch (IOException iE) {

                   }
                   progressDialog.dismiss();
               }else {
                   Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                   progressDialog.dismiss();
               }

           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
           }
       });

    }
}
