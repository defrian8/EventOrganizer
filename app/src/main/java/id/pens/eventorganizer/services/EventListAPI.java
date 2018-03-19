package id.pens.eventorganizer.services;

import id.pens.eventorganizer.model.EventResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MONKEY on 04/01/2018.
 */

public interface EventListAPI {
    @GET("event/getevent")
    Call<EventResponse> getEvents();
}
