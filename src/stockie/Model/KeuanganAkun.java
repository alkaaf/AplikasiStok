/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockie.Model;

import java.util.Vector;

/**
 *
 * @author dalbo
 */
public class KeuanganAkun {
    int idAkun;
    int idKelompok;
    String namaAkun;
    double saldo;

    public int getIdAkun() {
        return idAkun;
    }

    public void setIdAkun(int idAkun) {
        this.idAkun = idAkun;
    }

    public int getIdKelompok() {
        return idKelompok;
    }

    public void setIdKelompok(int idKelompok) {
        this.idKelompok = idKelompok;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
//        return super.toString(); //To change body of generated methods, choose Tools | Templates.
        return String.format("%s (Saldo %.0f)",namaAkun,saldo);
    }
    
    public Vector getList(){
        Vector v = new Vector();
        v.add(namaAkun);
        v.add(saldo);
        return v;
    }
}
