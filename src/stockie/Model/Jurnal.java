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
public class Jurnal {

    int idAkun;
    String namaAkun;
    double debet;
    double kredit;
    long tanggal;
    String keterangan;
    public static final String[] columnName = new String[]{"Tanggal", "Akun", "Debet", "Kredit"};

    public Jurnal() {
    }

    public int getIdAkun() {
        return idAkun;
    }

    public void setIdAkun(int idAkun) {
        this.idAkun = idAkun;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public double getDebet() {
        return debet;
    }

    public void setDebet(double debet) {
        this.debet = debet;
    }

    public double getKredit() {
        return kredit;
    }

    public void setKredit(double kredit) {
        this.kredit = kredit;
    }

    public long getTanggal() {
        return tanggal;
    }

    public void setTanggal(long tanggal) {
        this.tanggal = tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Vector getRow() {
        Vector v = new Vector();
        v.add(new SimpleDateFormat(C.DATE_FORMAT).format(new Date(tanggal)));
        v.add(namaAkun);
        v.add(debet);
        v.add(kredit);
        return v;
    }

}
