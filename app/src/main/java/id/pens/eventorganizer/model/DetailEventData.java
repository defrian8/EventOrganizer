package id.pens.eventorganizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MONKEY on 02/01/2018.
 */

public class DetailEventData {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("desc")
    @Expose
    private String Desc;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("kapasitas")
    @Expose
    private String kapasitas;
    @SerializedName("lokasi")
    @Expose
    private String lokasi;
    @SerializedName("tiket")
    @Expose
    private String tiket;
    @SerializedName("harga_tiket")
    @Expose
    private String harga_tiket;


    public String getHarga_tiket() {
        return harga_tiket;
    }

    public void setHarga_tiket(String harga_tiket) {
        this.harga_tiket = harga_tiket;
    }
    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTiket() {
        return tiket;
    }

    public void setTiket(String tiket) {
        this.tiket = tiket;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
