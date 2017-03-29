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
public class DaftarJual {

    int idBarang;
    String namaBarang;
    double stok;
    String satuan;
    double qty;
    double harga;
    int jenis;
    public static String stokBarangColumnName[] = new String[]{"ID Barang", "Nama Barang", "Stok", "Jenis Stok"};
    public DaftarJual() {
    }

    public DaftarJual(int idBarang, String namaBarang, double stok, String satuan, double qty, double harga, int jenis) {
        this.namaBarang = namaBarang;
        this.idBarang = idBarang;
        this.stok = stok;
        this.satuan = satuan;
        this.qty = qty;
        this.harga = harga;
        this.jenis = jenis;
    }

    public DaftarJual(int idBarang, String namaBarang, double stok, String satuan, double qty, double harga) {
        this.namaBarang = namaBarang;
        this.idBarang = idBarang;
        this.stok = stok;
        this.satuan = satuan;
        this.qty = qty;
        this.harga = harga;
    }

    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public double getStok() {
        return stok;
    }

    public void setStok(double stok) {
        this.stok = stok;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    @Override
    public String toString() {
//        return super.toString(); //To change body of generated methods, choose Tools | Templates.
        if (idBarang == 0) {
            return "";
        } else {
            return String.format("[ID%d] %s", idBarang, namaBarang);
        }
    }

    public String toStringStok() {
        if (jenis == 0) {
            return String.format("[ID%d] (%.1f) %s", idBarang, stok, namaBarang);
        } else {
            return String.format("[ID%d] (%.1f) %s (Retur)", idBarang, stok, namaBarang);
        }
    }

    public int getJenis() {
        return jenis;
    }

    public void setJenis(int jenis) {
        this.jenis = jenis;
    }
    
    public String getStrJenis(){
        return jenis == 0 ? "Stok" : "Retur";
    }

    public Vector getRow() {
        Vector v = new Vector();
        v.add(String.format("[ID%d]", idBarang));
        v.add(namaBarang);
        v.add(harga);
        return v;
    }
    
    public Object[] getObjects(){
        return new Object[]{String.format(C.SF_BARANG, idBarang), namaBarang, stok+" "+satuan, getStrJenis()};
    }
}