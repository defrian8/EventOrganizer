package id.pens.eventorganizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MONKEY on 01/02/2018.
 */

public class OrderDetail {
    @SerializedName("id_order")
    @Expose
    private Integer idOrder;
    @SerializedName("nama_peserta")
    @Expose
    private String namaPeserta;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("nama_event")
    @Expose
    private String namaEvent;
    @SerializedName("jenis_tiket")
    @Expose
    private String jenisTiket;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("status_order")
    @Expose
    private Integer statusOrder;
    @SerializedName("expired_time")
    @Expose
    private  String expired_time;

    public String getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(String expired_time) {
        this.expired_time = expired_time;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public String getNamaPeserta() {
        return namaPeserta;
    }

    public void setNamaPeserta(String namaPeserta) {
        this.namaPeserta = namaPeserta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public String getJenisTiket() {
        return jenisTiket;
    }

    public void setJenisTiket(String jenisTiket) {
        this.jenisTiket = jenisTiket;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Integer getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(Integer statusOrder) {
        this.statusOrder = statusOrder;
    }
}
