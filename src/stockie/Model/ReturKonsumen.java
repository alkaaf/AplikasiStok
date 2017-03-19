/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockie.Model;

import Constant.C;
import java.util.Vector;

/**
 *
 * @author dalbo
 */
public class ReturKonsumen {
    int idTransaksi;
    int idBarang;
    double jumlah;
    String keterangan;
    long tanggal;
    private String namaBarang;
    double hargaKulak;
    double hargaJual;
    public static final String[] columnName = new String[]{"ID Barang", "Nama Barang", "Jumlah Retur"};

    public ReturKonsumen(int idTransaksi, int idBarang, double jumlah, String keterangan, long tanggal, String namaBarang, double hargaKulak, double hargaJual) {
        this.idTransaksi = idTransaksi;
        this.idBarang = idBarang;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
        this.namaBarang = namaBarang;
        this.hargaKulak = hargaKulak;
        this.hargaJual = hargaJual;
    }

    public double getHargaKulak() {
        return hargaKulak;
    }

    public void setHargaKulak(double hargaKulak) {
        this.hargaKulak = hargaKulak;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }

    
    public ReturKonsumen(int idTransaksi, int idBarang, double jumlah, String keterangan, long tanggal, String namaBarang) {
        this.idTransaksi = idTransaksi;
        this.idBarang = idBarang;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
        this.namaBarang = namaBarang;
    }

    public ReturKonsumen(int idTransaksi, int idBarang, double jumlah, String keterangan, long tanggal) {
        this.idTransaksi = idTransaksi;
        this.idBarang = idBarang;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
    }

    
    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public long getTanggal() {
        return tanggal;
    }

    public void setTanggal(long tanggal) {
        this.tanggal = tanggal;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }
    
    public Vector getRow(){
        Vector v = new Vector();
        v.add(String.format(C.SF_BARANG, idBarang));
        v.add(namaBarang);
        v.add(jumlah);
        return v;
    }
}
