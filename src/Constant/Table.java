/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Constant;

/**
 *
 * @author dalbo
 */
public class Table {

    public static class Transaksi {

        public static String TABLE = "transaksi";
        public static String IDTRANSAKSI = "idtransaksi";
        public static String TANGGAL = "tanggal_transaksi";
        public static String TAGIHAN = "tagihan";
        public static String BAYAR = "bayar";
        public static String KEMBALIAN = "kembalian";
        public static String KETERANGAN = "keterangan";
    }

    public static class TransaksiDetail {

        public static String TABLE = "transaksi_detail";
        public static String IDTRANSAKSI = "idtransaksi";
        public static String IDBARANG = "idbarang";
        public static String JUMLAH = "jumlah";
        public static String HAPUS = "hapus";
        public static String HARGATOTAL = "harga_total";
    }

    public static class DataBarang {

        public static String TABLE = "data_barang";
        public static String IDBARANG = "idbarang";
        public static String NAMA_BARANG = "nama_barang";
        public static String IDSATUAN = "idsatuan";
        public static String HAPUS = "hapus";
    }

    public static class DataSatuan {

        public static String TABLE = "data_satuan";
        public static String IDSATUAN = "idsatuan";
        public static String NAMA_SATUAN = "nama_satuan";
        public static String HAPUS = "hapus";
    }

    public static class BarangHarga {

        public static String TABLE = "barang_harga";
        public static String IDBARANG = "idbarang";
        public static String TANGGAL = "tanggal_harga";
        public static String HARGA = "harga";
        public static String QTY = "qty";
        public static String HAPUS = "hapus";
    }

    public static class BarangStok {

        public static String TABLE = "barang_stok";
        public static String IDBARANG = "idbarang";
        public static String STOK = "stok";
        public static String HAPUS = "hapus";
    }

    public static class BarangMasuk {
        public static String IDMASUK = "idmasuk";
        public static String TABLE = "barang_masuk";
        public static String IDBARANG = "idbarang";
        public static String TANGGAL = "tanggal_masuk";
        public static String QTY = "qty";
        public static String HARGA_KULAK = "harga_kulak";
        public static String HAPUS = "hapus";

    }

    public static class Retur {

    }

    public static class ReturDetail {

    }

    public static class KeuanganAkun {

        public static String TABLE = "keuangan_akun";
        public static String IDAKUN = "idakun";
        public static String IDKELOMPOK = "idkelompok";
        public static String NAMA_AKUN = "nama_akun";
        public static String STATIS = "statis";
        public static String HAPUS = "hapus";

    }

    public static class KeuanganJurnal {

        public static String TABLE = "keuangan_jurnal";
        public static String TANGGAL = "tanggal";
        public static String IDAKUN = "idakun";
        public static String DEBET = "debet";
        public static String KREDIT = "kredit";
        public static String KETERANGAN = "keterangan";
        public static String SALDO = "saldo";
        public static String ID = "id";
    }

    public static class KeuanganKelompok {

        public static String TABLE = "keuangan_kelompok";
        public static String NAMA_KELOMPOK = "nama_kelompok";
        public static String JENIS = "jenis";
        public static String HAPUS = "hapus";
        public static String IDKELOMPOK = "idkelompok";
    }

    public static class KeuanganSaldo {

        public static String TABLE = "keuangan_saldo";
        public static String DEBET = "debet";
        public static String KREDIT = "kredit";
        public static String IDAKUN = "idakun";
    }
}
