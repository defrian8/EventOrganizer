package id.pens.eventorganizer.model;

/**
 * Created by MONKEY on 07/02/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendList {

    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("nama_peserta")
    @Expose
    private String namaPeserta;
    @SerializedName("telepon")
    @Expose
    private String telepon;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNamaPeserta() {
        return namaPeserta;
    }

    public void setNamaPeserta(String namaPeserta) {
        this.namaPeserta = namaPeserta;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

}