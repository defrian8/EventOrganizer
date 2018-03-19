package id.pens.eventorganizer.model;

/**
 * Created by MONKEY on 07/02/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendCheck {
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("waktu")
    @Expose
    private String waktu;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
