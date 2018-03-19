package id.pens.eventorganizer.model;

/**
 * Created by MONKEY on 07/02/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventDone {

    @SerializedName("id_event")
    @Expose
    private Integer idEvent;
    @SerializedName("nama_event")
    @Expose
    private String namaEvent;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}