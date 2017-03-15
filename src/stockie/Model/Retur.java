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
public class Retur {
    int idRetur;
    long tanggal;
    int idBarang;
    String namaBarang;
    double qty;
    double biaya;
    String keterangan;

    public int getIdRetur() {
        return idRetur;
    }

    public void setIdRetur(int idRetur) {
        this.idRetur = idRetur;
    }

    public long getTanggal() {
        return tanggal;
    }

    public void setTanggal(long tanggal) {
        this.tanggal = tanggal;
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

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getBiaya() {
        return biaya;
    }

    public void setBiaya(double biaya) {
        this.biaya = biaya;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    public static final String[] columnName = new String[]{"IDRetur", "IDBarang", "Tanggal", "Nama Barang", "QTY", "Biaya", "Keterangan"};
    
    public Vector getRow(){
        Vector v = new Vector();
        v.add(String.format(C.SF_RETUR, idRetur));
        v.add(String.format(C.SF_BARANG, idBarang));
        v.add(new SimpleDateFormat(C.DATE_FORMAT).format(new Date(tanggal)));
        v.add(namaBarang);
        v.add(qty);
        v.add(biaya);
        v.add(keterangan);
        return v;
    }
}
