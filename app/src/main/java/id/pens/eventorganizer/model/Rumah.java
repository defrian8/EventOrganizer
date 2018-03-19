package id.pens.eventorganizer.model;

/**
 * Created by MONKEY on 09/01/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Rumah {

    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("harga")
    @Expose
    private Integer harga;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }
}
