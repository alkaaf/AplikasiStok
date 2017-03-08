/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockie.Model;

/**
 *
 * @author dalbo
 */
public class Transaksi {
    int idTransaksi;
    String tanggal;
    double tagihan;
    double bayar;
    double kembalian;
    String keterangan;

    public Transaksi() {
    }

    public Transaksi(String tanggal, double tagihan, double bayar, double kembalian, String keterangan) {
        this.tanggal = tanggal;
        this.tagihan = tagihan;
        this.bayar = bayar;
        this.kembalian = kembalian;
        this.keterangan = keterangan;
    }

    public Transaksi(int idTransaksi, String tanggal, double tagihan, double bayar, double kembalian, String keterangan) {
        this.idTransaksi = idTransaksi;
        this.tanggal = tanggal;
        this.tagihan = tagihan;
        this.bayar = bayar;
        this.kembalian = kembalian;
        this.keterangan = keterangan;
    }
    
    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public double getTagihan() {
        return tagihan;
    }

    public void setTagihan(double tagihan) {
        this.tagihan = tagihan;
    }

    public double getBayar() {
        return bayar;
    }

    public void setBayar(double bayar) {
        this.bayar = bayar;
    }

    public double getKembalian() {
        return kembalian;
    }

    public void setKembalian(double kembalian) {
        this.kembalian = kembalian;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
}
