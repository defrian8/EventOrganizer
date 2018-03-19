package id.pens.eventorganizer.lib;

import id.pens.eventorganizer.services.APIService;
import android.text.format.DateUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by MONKEY on 09/01/2018.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.43.223/event/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
    public String getTimeAgo(String dateInput) {
        String timeformat = dateInput;
        try {
            long now = System.currentTimeMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date convertedDate = dateFormat.parse(dateInput);
            CharSequence relavetime1 = DateUtils.getRelativeTimeSpanString(
                    convertedDate.getTime(),
                    now,
                    DateUtils.SECOND_IN_MILLIS);
            timeformat = String.valueOf(relavetime1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timeformat;
    }
}
