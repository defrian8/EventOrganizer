package id.pens.eventorganizer.model;

/**
 * Created by MONKEY on 02/02/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Organizer {
    @SerializedName("id_event")
    @Expose
    private Integer idEvent;
    @SerializedName("nama_event")
    @Expose
    private String namaEvent;
    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;

    @SerializedName("tanggal")
    @Expose
    private String tanggal;

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
