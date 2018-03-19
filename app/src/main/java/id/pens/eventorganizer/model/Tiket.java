package id.pens.eventorganizer.model;

/**
 * Created by MONKEY on 01/02/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Tiket {
    @SerializedName("id_tiket")
    @Expose
    private Integer idTiket;
    @SerializedName("nama_event")
    @Expose
    private String namaEvent;
    @SerializedName("kode_tiket")
    @Expose
    private String kodeTiket;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("jenis_tiket")
    @Expose
    private String jenis_tiket;
    @SerializedName("id_order")
    @Expose
    private String id_order;

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getJenis_tiket() {
        return jenis_tiket;
    }

    public void setJenis_tiket(String jenis_tiket) {
        this.jenis_tiket = jenis_tiket;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getIdTiket() {
        return idTiket;
    }

    public void setIdTiket(Integer idTiket) {
        this.idTiket = idTiket;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public String getKodeTiket() {
        return kodeTiket;
    }

    public void setKodeTiket(String kodeTiket) {
        this.kodeTiket = kodeTiket;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
