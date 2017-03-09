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
public class Transaksi {
    int idTransaksi;
    long tanggal;
    double tagihan;
    double bayar;
    double kembalian;
    String keterangan;
    SimpleDateFormat sdf;
    public Transaksi() {
        sdf = new SimpleDateFormat(C.DATE_FORMAT);
    }

    public Transaksi(long tanggal, double tagihan, double bayar, double kembalian, String keterangan) {
        sdf = new SimpleDateFormat(C.DATE_FORMAT);
        this.tanggal = tanggal;
        this.tagihan = tagihan;
        this.bayar = bayar;
        this.kembalian = kembalian;
        this.keterangan = keterangan;
    }

    public Transaksi(int idTransaksi, long tanggal, double tagihan, double bayar, double kembalian, String keterangan) {
        sdf = new SimpleDateFormat(C.DATE_FORMAT);
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

    public long getTanggal() {
        return tanggal;
    }

    public void setTanggal(long tanggal) {
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
    
    public Vector getRow(){
        Vector vector = new Vector();
        vector.add(String.format(C.SF_TRANSAKSI, idTransaksi));
        vector.add(sdf.format(new Date(tanggal)));
        vector.add(tagihan);
        vector.add(bayar);
        vector.add(kembalian);
        vector.add(keterangan);
        return vector;
    }
    
}
