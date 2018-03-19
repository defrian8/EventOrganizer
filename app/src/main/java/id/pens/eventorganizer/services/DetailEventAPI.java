package id.pens.eventorganizer.services;

import id.pens.eventorganizer.model.DetailEvent;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MONKEY on 02/01/2018.
 */

public interface DetailEventAPI {
    @GET("event/getdetail")
    Call<DetailEvent> getDetails(@Query("id_event") String id_event);
}
