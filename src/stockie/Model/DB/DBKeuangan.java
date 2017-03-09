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
import stockie.Model.KeuanganAkun;

/**
 *
 * @author dalbo
 */
public class DBKeuangan extends DBHelper {

    public static class Akun {

        public static int kas = 1;
        public static int persediaan = 2;
        public static int utang = 3;
        public static int modal = 4;
        public static int pendapatan = 5;
        public static int biaya = 6;
        public static int biayaRetur = 7;
    }

    public static class Kelompok {

        public static int aset = 1;
        public static int biaya = 2;
        public static int kewajiban = 3;
        public static int modal = 4;
        public static int pendapatan = 5;
    }

    public static class Jenis {

        public static int debet = 0;
        public static int kredit = 1;
    }

    public List<KeuanganAkun> getDaftarAkun() {
        List<KeuanganAkun> temp = new ArrayList<>();
        String sql = "select kelompok." + Table.KeuanganKelompok.NAMA_KELOMPOK + ", akun." + Table.KeuanganAkun.NAMA_AKUN + ", akun." + Table.KeuanganAkun.IDAKUN + ", coalesce(saldo." + Table.KeuanganSaldo.DEBET + ",0.0), coalesce(saldo." + Table.KeuanganSaldo.KREDIT + ",0.0) "
                + "from " + Table.KeuanganAkun.TABLE + " akun "
                + "LEFT JOIN " + Table.KeuanganSaldo.TABLE + " saldo on akun." + Table.KeuanganAkun.IDAKUN + " = saldo." + Table.KeuanganSaldo.IDAKUN + " "
                + "join " + Table.KeuanganKelompok.TABLE + " kelompok on akun." + Table.KeuanganAkun.IDKELOMPOK + "=kelompok." + Table.KeuanganKelompok.IDKELOMPOK + " where hapus = 0";
        ResultSet rs;
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                KeuanganAkun akun = new KeuanganAkun();
                akun.setIdAkun(rs.getInt(Table.KeuanganAkun.IDAKUN));
                akun.setNamaAkun(rs.getString(Table.KeuanganAkun.NAMA_AKUN));
                int jenis = rs.getInt(Table.KeuanganKelompok.JENIS);
                double saldo = 0;
                if (jenis == DBKeuangan.Jenis.debet) {
                    saldo = rs.getDouble(Table.KeuanganSaldo.DEBET) - rs.getDouble(Table.KeuanganSaldo.KREDIT);
                } else {
                    saldo = rs.getDouble(Table.KeuanganSaldo.KREDIT) - rs.getDouble(Table.KeuanganSaldo.DEBET);
                }
                akun.setSaldo(saldo);
                temp.add(akun);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    public List<KeuanganAkun> getDaftarAkun(int idKelompok) {
        List<KeuanganAkun> temp = new ArrayList<>();
        String sql = "select kelompok."+Table.KeuanganKelompok.JENIS+", kelompok." + Table.KeuanganKelompok.NAMA_KELOMPOK + ", akun." + Table.KeuanganAkun.NAMA_AKUN + ", akun." + Table.KeuanganAkun.IDAKUN + ", coalesce(saldo." + Table.KeuanganSaldo.DEBET + ",0.0) "+Table.KeuanganSaldo.DEBET+", coalesce(saldo." + Table.KeuanganSaldo.KREDIT + ",0.0) " + Table.KeuanganSaldo.KREDIT + " "
                + "from " + Table.KeuanganAkun.TABLE + " akun "
                + "LEFT JOIN " + Table.KeuanganSaldo.TABLE + " saldo on akun." + Table.KeuanganAkun.IDAKUN + " = saldo." + Table.KeuanganSaldo.IDAKUN + " "
                + "join " + Table.KeuanganKelompok.TABLE + " kelompok on akun." + Table.KeuanganAkun.IDKELOMPOK + "=kelompok." + Table.KeuanganKelompok.IDKELOMPOK + " where akun.hapus = 0 and "
                + "kelompok."+Table.KeuanganKelompok.IDKELOMPOK + "=?";
        System.out.println(sql);
        ResultSet rs;
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, idKelompok);
            rs = ps.executeQuery();
            while (rs.next()) {
                KeuanganAkun akun = new KeuanganAkun();
                akun.setIdAkun(rs.getInt(Table.KeuanganAkun.IDAKUN));
                akun.setNamaAkun(rs.getString(Table.KeuanganAkun.NAMA_AKUN));
                int jenis = rs.getInt(Table.KeuanganKelompok.JENIS);
                double saldo = 0;
                if (jenis == DBKeuangan.Jenis.debet) {
                    saldo = rs.getDouble(Table.KeuanganSaldo.DEBET) - rs.getDouble(Table.KeuanganSaldo.KREDIT);
                } else {
                    saldo = rs.getDouble(Table.KeuanganSaldo.KREDIT) - rs.getDouble(Table.KeuanganSaldo.DEBET);
                }
                akun.setSaldo(saldo);
                temp.add(akun);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }
}
