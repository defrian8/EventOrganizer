package id.pens.eventorganizer.model;

/**
 * Created by MONKEY on 31/01/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class MyOrder {
    @SerializedName("id_pendaftaran")
    @Expose
    private Integer idPendaftaran;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("nama_tiket")
    @Expose
    private String namaTiket;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("tanggal_daftar")
    @Expose
    private String tanggalDaftar;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public Integer getIdPendaftaran() {
        return idPendaftaran;
    }

    public void setIdPendaftaran(Integer idPendaftaran) {
        this.idPendaftaran = idPendaftaran;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getNamaTiket() {
        return namaTiket;
    }

    public void setNamaTiket(String namaTiket) {
        this.namaTiket = namaTiket;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(String tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
