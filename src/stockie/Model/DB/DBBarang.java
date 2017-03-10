/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockie.Model.DB;

import Constant.Table;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import stockie.Model.BarangPembelian;
import stockie.Model.Satuan;

/**
 *
 * @author dalbo
 */
public class DBBarang extends DBHelper {

    public void insertPembelian(BarangPembelian barang) {
        int newId = getNewID(Table.DataBarang.TABLE);
        insertBarang(newId, barang.getNamaBarang(), barang.getIdSatuan());
        insertBarangMasuk(newId, barang.getTanggal(), barang.getQty(), barang.getHargaKulak());
        insertHarga(newId, barang.getQtyJual(), barang.getHargaJual(), barang.getTanggal());
        insertStok(newId, barang.getQty());
    }

    public void insertBarangMasuk(int idBarang, long tanggal, double qty, double harga) {
        String sql = "insert into " + Table.BarangMasuk.TABLE + "("
                + Table.BarangMasuk.IDBARANG + ","
                + Table.BarangMasuk.TANGGAL + ","
                + Table.BarangMasuk.QTY + ","
                + Table.BarangMasuk.HARGA_KULAK + ")"
                + " values (?,?,?,?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, idBarang);
            ps.setLong(2, tanggal);
            ps.setDouble(3, qty);
            ps.setDouble(4, harga);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBBarang.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertBarang(int idBarang, String namaBarang, int satuan) {
        String sql = "insert into " + Table.DataBarang.TABLE + "("
                + Table.DataBarang.IDBARANG + ","
                + Table.DataBarang.NAMA_BARANG + ","
                + Table.DataBarang.IDSATUAN + ")"
                + " values (?,?,?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, idBarang);
            ps.setString(2, namaBarang);
            ps.setInt(3, satuan);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertHarga(int idBarang, double qty, double harga, long tanggal) {
        String sql = "insert into " + Table.BarangHarga.TABLE + "("
                + Table.BarangHarga.IDBARANG + ","
                + Table.BarangHarga.QTY + ","
                + Table.BarangHarga.HARGA + ","
                + Table.BarangHarga.TANGGAL + ") "
                + "values (?,?,?,?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, idBarang);
            ps.setDouble(2, qty);
            ps.setDouble(3, harga);
            ps.setLong(4, tanggal);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double getHargaKulakSatuan(int idBarang) {
        double harga = 0;
        final String alias = "harga_kulak_satuan";
        ResultSet rs;
        String sql = "select harga_kulak/qty " + alias + " from barang_masuk where " + Table.BarangMasuk.IDBARANG + "=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, idBarang);
            rs = ps.executeQuery();
            if (!rs.isClosed()) {
                harga = rs.getDouble(alias);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return harga;

    }

    public void insertStok(int idBarang, double masuk) {
        stokMasuk(idBarang, masuk);
    }

    public List<BarangPembelian> selectPembelianBarang(long start, long end) {
        List<BarangPembelian> temp = new ArrayList<>();
        ResultSet rs;
        String sql = "select * from " + Table.BarangMasuk.TABLE + " masuk, " + Table.DataBarang.TABLE + " data where data." + Table.DataBarang.IDBARANG + "=masuk." + Table.BarangMasuk.IDBARANG;
        String sql2 = "select * from " + Table.BarangMasuk.TABLE + " masuk, " + Table.DataBarang.TABLE + " data where masuk." + Table.BarangMasuk.TANGGAL + ">=? and masuk." + Table.BarangMasuk.TANGGAL + "<=?" + " and data." + Table.DataBarang.IDBARANG + "=masuk." + Table.BarangMasuk.IDBARANG;
        System.out.println(sql);
        System.out.println(sql2);
        PreparedStatement ps = null;
        try {
            if (start != -1 && end != -1) {
                ps = getConnection().prepareStatement(sql2);
                ps.setLong(1, start);
                ps.setLong(2, end);
            } else {
                ps = getConnection().prepareStatement(sql);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                BarangPembelian tempRow = new BarangPembelian();
                tempRow.setIdBarang(rs.getInt(Table.BarangMasuk.IDBARANG));
                tempRow.setIdPembelian(rs.getInt(Table.BarangMasuk.IDMASUK));
                tempRow.setHargaKulak(rs.getDouble(Table.BarangMasuk.HARGA_KULAK));
                tempRow.setNamaBarang(rs.getString(Table.DataBarang.NAMA_BARANG));
                tempRow.setTanggal(rs.getLong(Table.BarangMasuk.TANGGAL));
                tempRow.setQty(rs.getDouble(Table.BarangMasuk.QTY));
                temp.add(tempRow);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

}
