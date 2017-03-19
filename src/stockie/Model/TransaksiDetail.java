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
public class TransaksiDetail {

    int idTransaksi;
    int idBarang;
    double jumlahSatuan;
    double hargaSatuan;
    double hargaTotal;
    double jumlah;
    String satuan;
    String namaBarang;
    public static final String[] namaKolom = new String[]{"ID Barang", "Nama Barang", "Satuan", "Harga Satuan", "Jumlah", "Total"};

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public double getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(double hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public double getJumlahSatuan() {
        return jumlahSatuan;
    }

    public void setJumlahSatuan(double jumlahSatuan) {
        this.jumlahSatuan = jumlahSatuan;
    }

    public double getHargaTotal() {
        return hargaTotal;
    }

    public void setHargaTotal(double hargaTotal) {
        this.hargaTotal = hargaTotal;
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

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public Vector getRow() {
        Vector vector = new Vector();
        vector.add(String.format(C.SF_BARANG, idBarang));
        vector.add(namaBarang);
        vector.add(satuan);
        vector.add(hargaSatuan);
        vector.add(jumlah);
        vector.add(getHargaTotal());
        return vector;
    }
    
    public ReturKonsumen getRetur(double jumlah){
        ReturKonsumen k = new ReturKonsumen(idTransaksi, idBarang, jumlah, "", 0, namaBarang,0,hargaSatuan);
        return k;
    }
}
