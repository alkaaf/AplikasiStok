/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockie.Model;

import java.util.Vector;
import stockie.Anotate.Database;

/**
 *
 * @author dalbo
 */
public class Satuan {

    @Database(column = "idsatuan", type = "int")
    public int idSatuan;
    @Database(column = "namaSatuan", type = "string")
    public String namaSatuan;

    public int getIdSatuan() {
        return idSatuan;
    }

    public void setIdSatuan(int idSatuan) {
        this.idSatuan = idSatuan;
    }

    @Override
    public String toString() {
        return this.namaSatuan;
    }

    public String getNamaSatuan() {
        return namaSatuan;
    }

    public void setNamaSatuan(String namaSatuan) {
        this.namaSatuan = namaSatuan;
    }

    public Vector getRow() {
        Vector v = new Vector();
        v.add(idSatuan);
        v.add(namaSatuan);
        return v;
    }

}
