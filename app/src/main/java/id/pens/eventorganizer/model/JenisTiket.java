package id.pens.eventorganizer.model;

/**
 * Created by MONKEY on 17/01/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class JenisTiket {
    @SerializedName("id_jenis_tiket")
    @Expose
    private Integer idJenisTiket;
    @SerializedName("available")
    @Expose
    private Integer available;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("nama_tiket")
    @Expose
    private String namaTiket;
    @SerializedName("ket")
    @Expose
    private String ket;
    @SerializedName("id_event")
    @Expose
    private Integer idEvent;

    public Integer getIdJenisTiket() {
        return idJenisTiket;
    }

    public void setIdJenisTiket(Integer idJenisTiket) {
        this.idJenisTiket = idJenisTiket;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getNamaTiket() {
        return namaTiket;
    }

    public void setNamaTiket(String namaTiket) {
        this.namaTiket = namaTiket;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }
}
