package id.pens.eventorganizer.lib;

/**
 * Created by MONKEY on 01/01/2018.
 */

import android.content.Context;
import android.content.SharedPreferences;

import java.io.InputStream;
import java.io.OutputStream;

import retrofit2.Retrofit;

public class Utils {


    private static final String PREFERENCES_FILE = "pens_eo_setting";

    public static final String BASE_URL_API = "http://192.168.43.223/event/";

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);

    }

    public static void sessionInt(Context ctx, String key, int value) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

   public static void saveSPBoolean(Context ctx, String key, Boolean val) {
       SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPref.edit();
       editor.putBoolean(key, val);
       editor.commit();
   }

   public  static Boolean getSPBoolean(Context ctx, String key, Boolean defaultVal) {
       SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
       return sharedPref.getBoolean(key, defaultVal);
   }
    // Mendeklarasikan Interface BaseApiService
    public static Retrofit getRetrofit(){
        return RetrofitClient.getClient(BASE_URL_API);
    }



}