package id.pens.eventorganizer.model;

/**
 * Created by MONKEY on 04/01/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListEvent {
    @SerializedName("id_event")
    @Expose
    private Integer idEvent;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("lokasi")
    @Expose
    private String lokasi;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("bulan")
    @Expose
    private String bulan;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("harga")
    @Expose
    private String harga;
    //latitude
      //      longitude


    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String nama) {
        this.title = nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String image) {
        this.imageUrl = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
