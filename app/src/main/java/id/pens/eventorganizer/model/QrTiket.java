package id.pens.eventorganizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by MONKEY on 25/01/2018.
 */

public class QrTiket {

    @SerializedName("id_order")
    @Expose
    private Integer idOrder;
    @SerializedName("kode_qr")
    @Expose
    private String kodeQr;
    @SerializedName("nama_event")
    @Expose
    private String namaEvent;
    @SerializedName("nama_peserta")
    @Expose
    private String namaPeserta;
    @SerializedName("jenis_tiket")
    @Expose
    private String jenisTiket;

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public String getKodeQr() {
        return kodeQr;
    }

    public void setKodeQr(String kodeQr) {
        this.kodeQr = kodeQr;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public String getNamaPeserta() {
        return namaPeserta;
    }

    public void setNamaPeserta(String namaPeserta) {
        this.namaPeserta = namaPeserta;
    }

    public String getJenisTiket() {
        return jenisTiket;
    }

    public void setJenisTiket(String jenisTiket) {
        this.jenisTiket = jenisTiket;
    }
}
