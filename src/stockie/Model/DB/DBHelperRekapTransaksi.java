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
import stockie.Model.Transaksi;
import stockie.Model.TransaksiDetail;

/**
 *
 * @author dalbo
 */
public class DBHelperRekapTransaksi {

    Connection c;

    public DBHelperRekapTransaksi() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + C.DB_PATH);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelperTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Transaksi> selectTransaksi(long start, long end) {
        List<Transaksi> temp = new ArrayList();
        String sql = "select * from " + Table.Transaksi.TABLE;
        String sql1 = "select * from " + Table.Transaksi.TABLE + " where " + Table.Transaksi.TANGGAL + ">=? and " + Table.Transaksi.TANGGAL + "<=?";
        ResultSet rs;
        try {
            PreparedStatement ps = null;
            if(start != -1 && end != -1){
                ps = c.prepareStatement(sql1);
                ps.setLong(1, start);
                ps.setLong(2, end);
            } else {
                ps = c.prepareStatement(sql)
;            }
            
            rs = ps.executeQuery();
            while (rs.next()) {
                Transaksi tempTr = new Transaksi(
                        rs.getInt(Table.Transaksi.IDTRANSAKSI),
                        rs.getLong(Table.Transaksi.TANGGAL),
                        rs.getDouble(Table.Transaksi.TAGIHAN),
                        rs.getDouble(Table.Transaksi.BAYAR),
                        rs.getDouble(Table.Transaksi.KEMBALIAN),
                        rs.getString(Table.Transaksi.KETERANGAN));
                temp.add(tempTr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelperRekapTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    public List<TransaksiDetail> getDetailTransaksi(int idTransaksi) {
        List<TransaksiDetail> temp = new ArrayList();
        String sql
                = "select "
                + "data." + Table.DataBarang.IDBARANG + ", "
                + "harga." + Table.BarangHarga.QTY + ", "
                + "data." + Table.DataBarang.NAMA_BARANG + ", "
                + "harga." + Table.BarangHarga.HARGA + ", "
                + "harga." + Table.BarangHarga.QTY + ", "
                + "satuan." + Table.DataSatuan.NAMA_SATUAN + ", "
                + "detail." + Table.TransaksiDetail.JUMLAH + ", "
                + "detail."+Table.TransaksiDetail.HARGATOTAL+" "
                + "from "
                + Table.DataSatuan.TABLE + " satuan, "
                + Table.TransaksiDetail.TABLE + " detail, "
                + Table.DataBarang.TABLE + " data, "
                + Table.BarangHarga.TABLE + " harga "
                + "where "
                + "data." + Table.DataBarang.IDSATUAN + "=satuan." + Table.DataSatuan.IDSATUAN + " and "
                + "harga." + Table.BarangHarga.IDBARANG + "=detail." + Table.TransaksiDetail.IDBARANG + " and "
                + "detail." + Table.TransaksiDetail.IDBARANG + " = data." + Table.DataBarang.IDBARANG + " and "
                + "detail." + Table.TransaksiDetail.IDTRANSAKSI + " = ?";

        System.out.println(sql);
        ResultSet rs;
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, idTransaksi);
            rs = ps.executeQuery();
            while (rs.next()) {
                TransaksiDetail tempDetail = new TransaksiDetail();
                tempDetail.setIdBarang(rs.getInt(Table.DataBarang.IDBARANG));
                tempDetail.setNamaBarang(rs.getString(Table.DataBarang.NAMA_BARANG));
                tempDetail.setJumlahSatuan(rs.getDouble(Table.BarangHarga.QTY));
                tempDetail.setSatuan(rs.getString(Table.DataSatuan.NAMA_SATUAN));
                tempDetail.setHargaSatuan(rs.getDouble(Table.BarangHarga.HARGA));
                tempDetail.setJumlah(rs.getDouble(Table.TransaksiDetail.JUMLAH));
                tempDetail.setHargaTotal(rs.getDouble(Table.TransaksiDetail.HARGATOTAL));
                temp.add(tempDetail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelperRekapTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    public void close() {
        try {
            if (!c.isClosed()) {
                c.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelperRekapTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
