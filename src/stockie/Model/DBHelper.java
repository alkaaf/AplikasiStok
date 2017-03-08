/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockie.Model;

import Constant.C;
import Constant.Table;
import com.j256.ormlite.spring.DaoFactory;
import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author dalbo
 */
public class DBHelper {

    Connection c;

    public DBHelper() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite://"+C.DB_PATH);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int insert(String tabel, String kolom, String value) {
        Statement st;
        int res = 0;
        try {
            st = c.createStatement();
            res = st.executeUpdate("INSERT INTO " + tabel + "(" + kolom + ") VALUES (" + value + ")");
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public int delete(String tabel, String columnId, int id) {
        Statement st;
        int res = 0;
        try {
            st = c.createStatement();
            res = st.executeUpdate("DELETE FROM " + tabel + " WHERE " + columnId + "=" + id);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public int delete(String tabel, String columnId, String id) {
        Statement st;
        int res = 0;
        try {
            st = c.createStatement();
            res = st.executeUpdate("DELETE FROM " + tabel + " WHERE " + columnId + "='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public double getStokBarang(int idBarang) {
        double stok = -1.0;
        ResultSet rs = null;
        try {
            rs = c.createStatement().executeQuery("select " + Table.BarangStok.IDBARANG + ", " + Table.BarangStok.STOK + " from " + Table.BarangStok.TABLE + " where " + Table.BarangStok.IDBARANG + "=" + idBarang);
            rs.next();
            stok = rs.getDouble(Table.BarangStok.STOK);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stok;
    }

    public List<DaftarJual> getBarangJual() {
        List<DaftarJual> daftarJual = new ArrayList<>();
        ResultSet rs;
        String query1 = "SELECT stok.stok, harga.idbarang, data.nama_barang, harga.harga, satuan.nama_satuan, harga.qty FROM barang_stok stok, data_barang data, barang_harga harga, data_satuan satuan WHERE stok.idbarang = data.idbarang AND data.idbarang = harga.idbarang AND satuan.idsatuan = data.idsatuan AND data.hapus = 0";
        String query = "select "
                + "stok." + Table.BarangStok.STOK + ", "
                + "harga." + Table.BarangHarga.IDBARANG + ", "
                + "data." + Table.DataBarang.NAMA_BARANG + ", "
                + "harga." + Table.BarangHarga.QTY + ", "
                + "satuan." + Table.DataSatuan.NAMA_SATUAN + ", "
                + "harga." + Table.BarangHarga.HARGA + " "
                + "from "
                + Table.BarangStok.TABLE + " stok, "
                + Table.DataBarang.TABLE + " data, "
                + Table.BarangHarga.TABLE + " harga, "
                + Table.DataSatuan.TABLE + " satuan "
                + "where "
                + "stok." + Table.BarangStok.IDBARANG + " = data." + Table.DataBarang.IDBARANG + " and "
                + "data." + Table.DataBarang.IDBARANG + " = harga." + Table.BarangHarga.IDBARANG + " and "
                + "satuan." + Table.DataSatuan.IDSATUAN + " = data." + Table.DataBarang.IDSATUAN + " and "
                + "data." + Table.DataBarang.HAPUS + " = 0 "
                + "order by "+Table.DataBarang.NAMA_BARANG+" ";
                ;
        System.out.println(query1);
        System.out.println(query);
        try {
            rs = c.createStatement().executeQuery(query);
            while (rs.next()) {
                daftarJual.add(new DaftarJual(
                        rs.getInt(Table.BarangHarga.IDBARANG),
                        rs.getString(Table.DataBarang.NAMA_BARANG),
                        rs.getDouble(Table.BarangStok.STOK),
                        rs.getString(Table.DataSatuan.NAMA_SATUAN),
                        rs.getDouble(Table.BarangHarga.QTY),
                        rs.getDouble(Table.BarangHarga.HARGA)
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daftarJual;
    }
}
