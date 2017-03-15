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
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import stockie.Model.Jurnal;
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

    public List<Jurnal> getJurnal() {
        List<Jurnal> temp = new ArrayList<>();
        String sql = "SELECT jurnal." + Table.KeuanganJurnal.TANGGAL + ", jurnal." + Table.KeuanganJurnal.IDAKUN + ", akun." + Table.KeuanganAkun.NAMA_AKUN + ", jurnal." + Table.KeuanganJurnal.DEBET + ", jurnal." + Table.KeuanganJurnal.KREDIT + ", jurnal." + Table.KeuanganJurnal.KETERANGAN + " FROM " + Table.KeuanganAkun.TABLE + " akun, " + Table.KeuanganJurnal.TABLE + " jurnal where jurnal." + Table.KeuanganJurnal.IDAKUN + "=akun." + Table.KeuanganAkun.IDAKUN;
        ResultSet rs;
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Jurnal row = new Jurnal();
                row.setDebet(rs.getDouble(Table.KeuanganJurnal.DEBET));
                row.setKredit(rs.getDouble(Table.KeuanganJurnal.KREDIT));
                row.setIdAkun(rs.getInt(Table.KeuanganJurnal.IDAKUN));
                row.setNamaAkun(rs.getString(Table.KeuanganAkun.NAMA_AKUN));
                row.setKeterangan(rs.getString(Table.KeuanganJurnal.KETERANGAN));
                row.setTanggal(rs.getLong(Table.KeuanganJurnal.TANGGAL));
                temp.add(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    public List<Jurnal> getJurnal(long start, long end) {
        List<Jurnal> temp = new ArrayList<>();
        String sql = "SELECT jurnal." + Table.KeuanganJurnal.TANGGAL + ", jurnal." + Table.KeuanganJurnal.IDAKUN + ", akun." + Table.KeuanganAkun.NAMA_AKUN + ", jurnal." + Table.KeuanganJurnal.DEBET + ", jurnal." + Table.KeuanganJurnal.KREDIT + ", jurnal." + Table.KeuanganJurnal.KETERANGAN + " FROM " + Table.KeuanganAkun.TABLE + " akun, " + Table.KeuanganJurnal.TABLE + " jurnal where jurnal." + Table.KeuanganJurnal.IDAKUN + "=akun." + Table.KeuanganAkun.IDAKUN + " and " + Table.KeuanganJurnal.TANGGAL + ">=? and " + Table.KeuanganJurnal.TANGGAL + "<=?";
        ResultSet rs;
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setLong(1, start);
            ps.setLong(2, end);
            rs = ps.executeQuery();
            while (rs.next()) {
                Jurnal row = new Jurnal();
                row.setDebet(rs.getDouble(Table.KeuanganJurnal.DEBET));
                row.setKredit(rs.getDouble(Table.KeuanganJurnal.KREDIT));
                row.setIdAkun(rs.getInt(Table.KeuanganJurnal.IDAKUN));
                row.setNamaAkun(rs.getString(Table.KeuanganAkun.NAMA_AKUN));
                row.setKeterangan(rs.getString(Table.KeuanganJurnal.KETERANGAN));
                row.setTanggal(rs.getLong(Table.KeuanganJurnal.TANGGAL));
                temp.add(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    public void setDebet(int idAkun, double debet, long tanggal, String keterangan) {
//        HashMap<String, Object> jenisSaldo = getLastSaldoAndJenis(idAkun);
//        double saldo = 0;
//        if ((int) jenisSaldo.get(AKUN_JENIS) == Jenis.debet) {
//            saldo = (double) jenisSaldo.get(AKUN_SALDO) + debet;
//        } else {
//            saldo = (double) jenisSaldo.get(AKUN_SALDO) - debet;
//        }
        double saldo  = getLastSaldo(idAkun);
        int jenis = getJenis(idAkun);
        if(jenis == DBKeuangan.Jenis.debet){
            saldo += debet;
        } else {
            saldo -= debet;
        }
        String sql = "insert into " + Table.KeuanganJurnal.TABLE + "("
                + Table.KeuanganJurnal.IDAKUN + ","
                + Table.KeuanganJurnal.DEBET + ","
                + Table.KeuanganJurnal.TANGGAL + ","
                + Table.KeuanganJurnal.KETERANGAN + ","
                + Table.KeuanganJurnal.SALDO+") "
                + "values (?,?,?,?,?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, idAkun);
            ps.setDouble(2, debet);
            ps.setLong(3, tanggal);
            ps.setString(4, keterangan);
            ps.setDouble(5, saldo);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setKredit(int idAkun, double kredit, long tanggal, String keterangan) {
        double saldo  = getLastSaldo(idAkun);
        int jenis = getJenis(idAkun);
        if(jenis == DBKeuangan.Jenis.kredit){
            saldo += kredit;
        } else {
            saldo -= kredit;
        }
        String sql = "insert into " + Table.KeuanganJurnal.TABLE + "("
                + Table.KeuanganJurnal.IDAKUN + ","
                + Table.KeuanganJurnal.KREDIT + ","
                + Table.KeuanganJurnal.TANGGAL + ","
                + Table.KeuanganJurnal.KETERANGAN + ","
                + Table.KeuanganJurnal.SALDO+") "
                + "values (?,?,?,?,?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, idAkun);
            ps.setDouble(2, kredit);
            ps.setLong(3, tanggal);
            ps.setString(4, keterangan);
            ps.setDouble(5, saldo);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    final String AKUN_SALDO = "saldo";
    final String AKUN_JENIS = "jenis";

    public double getLastSaldo(int idAkun) {
//        HashMap<String, Object> saldoJenis = new HashMap<>();
//        saldoJenis.put(AKUN_JENIS, -1);
//        saldoJenis.put(AKUN_SALDO, 0);
        double saldo = 0;
        ResultSet rs;
        String sql = "SELECT"
                + "	jurnal.idakun,"
                + "	debet,"
                + "	kredit,"
                + "	jurnal.saldo,"
                + "	kelompok.jenis"
                + "FROM"
                + "	keuangan_jurnal jurnal,"
                + "	keuangan_kelompok kelompok,"
                + "	keuangan_akun akun"
                + "WHERE"
                + "	akun.idakun = jurnal.idakun"
                + "AND akun.idkelompok = kelompok.idkelompok"
                + "AND jurnal.idakun =?"
                + "ORDER BY"
                + "	tanggal DESC limit 1";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, idAkun);
            rs = ps.executeQuery();
            if (!rs.isClosed()) {
                saldo = rs.getDouble(Table.KeuanganJurnal.SALDO);
            }

//            saldoJenis.put(AKUN_JENIS, rs.getInt(Table.KeuanganKelompok.JENIS));
//            saldoJenis.put(AKUN_SALDO, rs.getDouble(Table.KeuanganJurnal.SALDO));
        } catch (SQLException ex) {
            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saldo;
    }

    public int getJenis(int idAkun) {
        String sql = "select jenis from keuangan_akun akun, keuangan_kelompok k where k.idkelompok=akun.idkelompok and akun.idakun=?";
        PreparedStatement ps;
        ResultSet rs;
        int jenis = -1;
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, idAkun);
            rs = ps.executeQuery();
            if (!rs.isClosed()) {
                jenis = rs.getInt(Table.KeuanganKelompok.JENIS);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jenis;
    }

    public double getSaldoDebet(int idAkun) {
        ResultSet rs;
        double saldo = -1;
        String sql = "select " + Table.KeuanganSaldo.DEBET + " from " + Table.KeuanganSaldo.TABLE + " where " + Table.KeuanganSaldo.IDAKUN + "=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, idAkun);
            rs = ps.executeQuery();
            if (!rs.isClosed()) {
                saldo = rs.getDouble(Table.KeuanganSaldo.DEBET);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saldo;
    }

    public double getSaldoKredit(int idAkun) {
        ResultSet rs;
        double saldo = -1;
        String sql = "select " + Table.KeuanganSaldo.KREDIT + " from " + Table.KeuanganSaldo.TABLE + " where " + Table.KeuanganSaldo.IDAKUN + "=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, idAkun);
            rs = ps.executeQuery();
            if (!rs.isClosed()) {
                saldo = rs.getDouble(Table.KeuanganSaldo.KREDIT);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saldo;
    }

    public void tambahSaldoDebet(int idAkun, double val) {
        double oldSaldo = getSaldoDebet(idAkun);
        String sqlinsert = "insert into " + Table.KeuanganSaldo.TABLE + "(" + Table.KeuanganSaldo.IDAKUN + "," + Table.KeuanganSaldo.DEBET + ") values (?,?)";
        String sqlupdate = "update " + Table.KeuanganSaldo.TABLE + " set " + Table.KeuanganSaldo.DEBET + "=? where " + Table.KeuanganSaldo.IDAKUN + "=?";
        try {
            PreparedStatement ps = null;
            if (oldSaldo == -1) {
                ps = getConnection().prepareStatement(sqlinsert);
                ps.setInt(1, idAkun);
                ps.setDouble(2, val + oldSaldo);

            } else {
                ps = getConnection().prepareStatement(sqlupdate);
                ps.setDouble(1, val + oldSaldo);
                ps.setInt(2, idAkun);
            }
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void kurangSaldoDebet(int idAkun, double val) {
//        double oldSaldo = getSaldoDebet(idAkun);
//        String sqlinsert = "insert into " + Table.KeuanganSaldo.TABLE + "(" + Table.KeuanganSaldo.IDAKUN + "," + Table.KeuanganSaldo.DEBET + ") values (?,?)";
//        String sqlupdate = "update " + Table.KeuanganSaldo.TABLE + " set " + Table.KeuanganSaldo.DEBET + "=? where " + Table.KeuanganSaldo.IDAKUN + "=?";
//        try {
//            PreparedStatement ps = null;
//            if (oldSaldo == -1) {
//                ps = getConnection().prepareStatement(sqlinsert);
//                ps.setInt(1, idAkun);
//                ps.setDouble(2, oldSaldo - val);
//
//            } else {
//                ps = getConnection().prepareStatement(sqlupdate);
//                ps.setDouble(1, oldSaldo - val);
//                ps.setInt(2, idAkun);
//            }
//            ps.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
    public void tambahSaldoKredit(int idAkun, double val) {
        double oldSaldo = getSaldoKredit(idAkun);
        String sqlinsert = "insert into " + Table.KeuanganSaldo.TABLE + "(" + Table.KeuanganSaldo.IDAKUN + "," + Table.KeuanganSaldo.KREDIT + ") values (?,?)";
        String sqlupdate = "update " + Table.KeuanganSaldo.TABLE + " set " + Table.KeuanganSaldo.KREDIT + "=? where " + Table.KeuanganSaldo.IDAKUN + "=?";
        try {
            PreparedStatement ps = null;
            if (oldSaldo == -1) {
                ps = getConnection().prepareStatement(sqlinsert);
                ps.setInt(1, idAkun);
                ps.setDouble(2, val + oldSaldo);

            } else {
                ps = getConnection().prepareStatement(sqlupdate);
                ps.setDouble(1, val + oldSaldo);
                ps.setInt(2, idAkun);
            }
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public void kurangSaldoKredit(int idAkun, double val) {
//        double oldSaldo = getSaldoKredit(idAkun);
//        String sqlinsert = "insert into " + Table.KeuanganSaldo.TABLE + "(" + Table.KeuanganSaldo.IDAKUN + "," + Table.KeuanganSaldo.KREDIT + ") values (?,?)";
//        String sqlupdate = "update " + Table.KeuanganSaldo.TABLE + " set " + Table.KeuanganSaldo.KREDIT + "=? where " + Table.KeuanganSaldo.IDAKUN + "=?";
//        try {
//            PreparedStatement ps = null;
//            if (oldSaldo == -1) {
//                ps = getConnection().prepareStatement(sqlinsert);
//                ps.setInt(1, idAkun);
//                ps.setDouble(2, oldSaldo - val);
//
//            } else {
//                ps = getConnection().prepareStatement(sqlupdate);
//                ps.setDouble(1, oldSaldo - val);
//                ps.setInt(2, idAkun);
//            }
//            ps.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(DBKeuangan.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

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
        String sql = "select kelompok." + Table.KeuanganKelompok.JENIS + ", kelompok." + Table.KeuanganKelompok.NAMA_KELOMPOK + ", akun." + Table.KeuanganAkun.NAMA_AKUN + ", akun." + Table.KeuanganAkun.IDAKUN + ", coalesce(saldo." + Table.KeuanganSaldo.DEBET + ",0.0) " + Table.KeuanganSaldo.DEBET + ", coalesce(saldo." + Table.KeuanganSaldo.KREDIT + ",0.0) " + Table.KeuanganSaldo.KREDIT + " "
                + "from " + Table.KeuanganAkun.TABLE + " akun "
                + "LEFT JOIN " + Table.KeuanganSaldo.TABLE + " saldo on akun." + Table.KeuanganAkun.IDAKUN + " = saldo." + Table.KeuanganSaldo.IDAKUN + " "
                + "join " + Table.KeuanganKelompok.TABLE + " kelompok on akun." + Table.KeuanganAkun.IDKELOMPOK + "=kelompok." + Table.KeuanganKelompok.IDKELOMPOK + " where akun.hapus = 0 and "
                + "kelompok." + Table.KeuanganKelompok.IDKELOMPOK + "=?";
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

//    public static void main(String[] args) {
//        DBKeuangan db = new DBKeuangan();
////        System.out.println(db.tambahSaldoDebit(1, 20) + "");
////        db.tambahSaldoDebit(1, 20);
////        db.kurangSaldoDebet(1, 220);
//        db.kurangSaldoKredit(4, 20000);
//    }
}
