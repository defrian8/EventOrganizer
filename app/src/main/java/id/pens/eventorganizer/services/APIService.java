package id.pens.eventorganizer.services;

/**
 * Created by MONKEY on 09/01/2018.
 */
import id.pens.eventorganizer.model.AttendCheckResponse;
import id.pens.eventorganizer.model.AttendListResponse;
import id.pens.eventorganizer.model.DetailEvent;
import id.pens.eventorganizer.model.EventDone;
import id.pens.eventorganizer.model.EventDoneResponse;
import id.pens.eventorganizer.model.EventResponse;
import id.pens.eventorganizer.model.JenisTiketResponse;
import id.pens.eventorganizer.model.MyOrderResponse;
import id.pens.eventorganizer.model.Notif;
import id.pens.eventorganizer.model.NotifResponse;
import id.pens.eventorganizer.model.OrderDetailResponse;
import id.pens.eventorganizer.model.OrganizerResponse;
import id.pens.eventorganizer.model.QrResponse;
import id.pens.eventorganizer.model.Rumah;
import id.pens.eventorganizer.model.TiketResponse;
import id.pens.eventorganizer.model.UserResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import okhttp3.ResponseBody;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {
    @GET("order/getjenisticket")
    Call<JenisTiketResponse> getJenisTicket(@Query("id_event") String id_event);

    @GET("user/info")
    Call<UserResponse> getUserInfo(@Query("id_user") String id_user);
    @GET("event/getqr")
    Call<QrResponse> getQrTiket(@Query("id_order") String id_order);
    @GET("order/userorder")
    Call<MyOrderResponse> getOrder(@Query("id_user") String id_user, @Query("status") String status);
    @GET("order/orderdetail")
    Call<OrderDetailResponse> getOrderDetail(@Query("id_order") String id_order);
    @GET("order/userticket")
    Call<TiketResponse> getTiket(@Query("id_user") String id_user);
    @GET("event/checkin")
    Call<ResponseBody> checkIn(@Query("kode_tiket") String kode_tiket,
                               @Query("panitia_id") String id_panitia,
                               @Query("id_event") String id_event);
    @GET("organizer/orglist")
    Call<OrganizerResponse> getOrgList(@Query("id_user") String id_user);
    @GET("user/getnotif")
    Call<NotifResponse> getUserNotif(@Query("id_user") String id_user);
    @GET("user/notifdetail")
    Call<ResponseBody> getNotifDetail (@Query("id_notification") String id_notif);
    @GET("event/geteventdone")
    Call<EventDoneResponse> getEventDone(@Query("id_user") String id_user);
    @GET("event/giverating")
    Call<ResponseBody> giveRating(@Query("id_user") String id_user,
                                  @Query("id_event") String id_event,
                                  @Query("rating") String rating,
                                  @Query("isi_feedback") String isi_feedback
                                  );
    @GET("organizer/attendlist")
    Call<AttendListResponse> getAttender (@Query("id_event") String id_event);
    @GET("organizer/attendcheck")
    Call<AttendCheckResponse> getAttenderCheck (@Query("id_event") String id_event);

    @GET("event/carievent")
    Call<EventResponse> cariEvent(@Query("query") String query);
     //--------------------------------------------------------------------------------------
    // POST METHOD--------------------------------------------------------------------------
    @POST("event/postevent")
    @FormUrlEncoded
    Call<Rumah> saveRumah(@Field("nama") String nama,
                        @Field("type") String type,
                        @Field("harga") Integer harga);
    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);
    @FormUrlEncoded
    @POST("user/sendid")
    Call<ResponseBody> sendRegID(@Field("id_user") String id_user,
                                 @Field("reg_id") String reg_id);

    @FormUrlEncoded
    @POST("api/register")
    Call<ResponseBody> registerRequest(@Field("username") String username,
                                       @Field("name") String name,
                                       @Field("email") String email,
                                       @Field("phone") String phone,
                                    @Field("password") String password);

    @Multipart
    @POST("/event/order/kirimbukti")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part foto, @Part("id_pendaftaran") RequestBody id_pendaftaran);

    @FormUrlEncoded
    @POST("order/attend")
    Call<ResponseBody> createOrder(@Field("id_user") String id_user,
                                   @Field("id_event") String id_event,
                                   @Field("id_jenis_tiket")String id_jenis_tiket );
}
