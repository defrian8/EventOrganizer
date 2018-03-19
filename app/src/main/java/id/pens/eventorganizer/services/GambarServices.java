package id.pens.eventorganizer.services;

/**
 * Created by MONKEY on 01/01/2018.
 */

import id.pens.eventorganizer.model.GambarResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GambarServices {
    @GET("event/getevent")
    Call<GambarResponse> getGambar();
}
