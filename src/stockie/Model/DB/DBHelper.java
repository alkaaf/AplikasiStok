/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockie.Model.DB;

import Constant.C;
import Constant.Table;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import stockie.Model.Satuan;

/**
 *
 * @author dalbo
 */
public class DBHelper {

    Connection c;

    public DBHelper() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + C.DB_PATH);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        try {
            if (!c.isClosed()) {
                c.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getNewID(String table) {
        int id = 0;
        ResultSet rs;
        PreparedStatement ps;
        try {
            ps = c.prepareStatement("select seq from sqlite_sequence where name=?");
            ps.setString(1, table);
            rs = ps.executeQuery();
            if (!rs.isClosed()) {
                id = rs.getInt("seq");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id + 1;
    }

    public void stokKeluar(int idBarang, double val) {
        double oldStok = getStokBarang(idBarang);
        double newStok = oldStok - val;
        System.out.println("Oldstok " + oldStok);
        System.out.println("newStok " + newStok);
        String sql = "update " + Table.BarangStok.TABLE + " set " + Table.BarangStok.STOK + "=? where " + Table.BarangStok.IDBARANG + "=?";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setDouble(1, newStok);
            ps.setInt(2, idBarang);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void stokMasuk(int idBarang, double val) {
        double oldStok = getStokBarang(idBarang);
        double newStok = oldStok + val;
        System.out.println("Oldstok " + oldStok);
        System.out.println("newStok " + newStok);
        String sqlupdate = "update " + Table.BarangStok.TABLE + " set " + Table.BarangStok.STOK + "=? where " + Table.BarangStok.IDBARANG + "=?";
        String sqlinsert = "insert into " + Table.BarangStok.TABLE + "("
                + Table.BarangStok.IDBARANG + ","
                + Table.BarangStok.STOK + ") "
                + "values (?,?)";
        try {
            if (oldStok == 0) {
                PreparedStatement ps = c.prepareStatement(sqlinsert);
                ps.setInt(1, idBarang);
                ps.setDouble(2, newStok);
                ps.executeUpdate();
            } else {
                PreparedStatement ps = c.prepareStatement(sqlupdate);
                ps.setDouble(1, newStok);
                ps.setInt(2, idBarang);
                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double getStokBarang(int idBarang) {
        double stok = 0.0;
        ResultSet rs = null;
        try {
            rs = c.createStatement().executeQuery("select " + Table.BarangStok.IDBARANG + ", " + Table.BarangStok.STOK + " from " + Table.BarangStok.TABLE + " where " + Table.BarangStok.IDBARANG + "=" + idBarang);
            if (!rs.isClosed()) {
                stok = rs.getDouble(Table.BarangStok.STOK);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stok;
    }

    public List<Satuan> selectSatuan() {
        List<Satuan> temp = new ArrayList<>();
        String sql = "select * from " + Table.DataSatuan.TABLE + " where hapus = 0";
        try {
            ResultSet rs = getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                Satuan tempRow = new Satuan();
                tempRow.setIdSatuan(rs.getInt(Table.DataSatuan.IDSATUAN));
                tempRow.setNamaSatuan(rs.getString(Table.DataSatuan.NAMA_SATUAN));
                temp.add(tempRow);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    public Connection getConnection() {
        return c;
    }
}
