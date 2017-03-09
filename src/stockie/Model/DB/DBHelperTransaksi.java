/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockie.Model.DB;

import Constant.C;
import Constant.Table;
import com.j256.ormlite.spring.DaoFactory;
import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import stockie.Model.DaftarJual;
import stockie.Model.Transaksi;
import stockie.Model.TransaksiDetail;

/**
 *
 * @author dalbo
 */
public class DBHelperTransaksi {

    Connection c;

    public DBHelperTransaksi() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite://" + C.DB_PATH);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int delete(String tabel, String columnId, int id) {
        Statement st;
        int res = 0;
        try {
            st = c.createStatement();
            res = st.executeUpdate("DELETE FROM " + tabel + " WHERE " + columnId + "=" + id);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stok;
    }

    public double getHargaBarang(int idBarang) {
        double harga = -1;
        ResultSet rs = null;
        try {
            PreparedStatement ps = c.prepareStatement("select " + Table.BarangHarga.HARGA + " from " + Table.BarangHarga.TABLE + " where " + Table.BarangHarga.IDBARANG + "=?");
            ps.setInt(1, idBarang);
            rs = ps.executeQuery();
            harga = rs.getDouble(Table.BarangHarga.HARGA);

        } catch (SQLException ex) {
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }

        return harga;
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

    public void insertTransaksi(Transaksi transaksi) {
        String sql = "insert into " + Table.Transaksi.TABLE + " values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, transaksi.getIdTransaksi());
            ps.setString(2, Long.toString(System.currentTimeMillis()));
            ps.setDouble(3, transaksi.getTagihan());
            ps.setDouble(4, transaksi.getBayar());
            ps.setDouble(5, transaksi.getKembalian());
            ps.setString(6, transaksi.getKeterangan());
            ps.setInt(7, 0);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertTrasaksiDetail(TransaksiDetail detail) {
        String sql = "insert into " + Table.TransaksiDetail.TABLE + "("
                + Table.TransaksiDetail.IDTRANSAKSI + ","
                + Table.TransaksiDetail.IDBARANG + ","
                + Table.TransaksiDetail.JUMLAH
                + ") values (?,?,?)";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, detail.getIdTransaksi());
            ps.setInt(2, detail.getIdBarang());
            ps.setDouble(3, detail.getJumlah());
//            ps.setInt(4, 0);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public List<DaftarJual> getBarangJual() {
        List<DaftarJual> daftarJual = new ArrayList<>();
        ResultSet rs;
//        String query1 = "SELECT stok.stok, harga.idbarang, data.nama_barang, harga.harga, satuan.nama_satuan, harga.qty FROM barang_stok stok, data_barang data, barang_harga harga, data_satuan satuan WHERE stok.idbarang = data.idbarang AND data.idbarang = harga.idbarang AND satuan.idsatuan = data.idsatuan AND data.hapus = 0";
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
                + "order by " + Table.DataBarang.NAMA_BARANG + " ";
        ;
//        System.out.println(query1);
//        System.out.println(query);
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
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daftarJual;
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
}
