/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockie.Model;

import Constant.C;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author dalbo
 */
public class BarangPembelian {

    int idPembelian;
    long tanggal;
    double qty;
    double qtyJual;
    double hargaKulak;
    double hargaJual;
    int idBarang;
    int idSatuan;
    String namaBarang;
    int idAkun;
    SimpleDateFormat sdf = new SimpleDateFormat(C.DATE_FORMAT);
    public static final String[] namaKolom = new String[]{"ID Pembelian", "ID Barang", "Nama Barang", "QTY", "Harga Kulak", "Tanggal"};

    public int getIdAkun() {
        return idAkun;
    }

    public void setIdAkun(int idAkun) {
        this.idAkun = idAkun;
    }

    
    public Vector getRow() {
        Vector v = new Vector();
        v.add(String.format(C.SF_BELI, idPembelian));
        v.add(String.format(C.SF_BARANG, idBarang));
        v.add(namaBarang);
        v.add(qty);
        v.add(hargaKulak);
        v.add(sdf.format(new Date(tanggal)));
        return v;
    }

    public double getQtyJual() {
        return qtyJual;
    }

    public void setQtyJual(double qtyJual) {
        this.qtyJual = qtyJual;
    }
    
    

    public double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }
    
    

    public int getIdSatuan() {
        return idSatuan;
    }

    public void setIdSatuan(int idSatuan) {
        this.idSatuan = idSatuan;
    }

    public int getIdPembelian() {
        return idPembelian;
    }

    public void setIdPembelian(int idPembelian) {
        this.idPembelian = idPembelian;
    }

    public long getTanggal() {
        return tanggal;
    }

    public void setTanggal(long tanggal) {
        this.tanggal = tanggal;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getHargaKulak() {
        return hargaKulak;
    }

    public void setHargaKulak(double hargaKulak) {
        this.hargaKulak = hargaKulak;
    }

    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

}
