/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockie.Gui.Transaksi;

import Constant.Table;
import com.sun.org.apache.bcel.internal.generic.ISTORE;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import stockie.Model.DB.DBHelperRekapTransaksi;
import stockie.Model.DB.DBHelperTransaksi;
import stockie.Model.DaftarJual;
import stockie.Model.Transaksi;
import stockie.Model.TransaksiDetail;

/**
 *
 * @author dalbo
 */
public class FrameTransaksi extends javax.swing.JFrame {

    DBHelperTransaksi dbHelper;

    /**
     * Creates new form FrameTransaksi
     */
    public FrameTransaksi() {
        initComponents();
        initData();
        initDataTransaksi(-1,-1);
    }

    /**
     * Data initialization when opening FrameTransaksi. Including daftar barang
     */
    DefaultComboBoxModel<String> daftarJualModel;
    List<DaftarJual> daftarJualList;
    Transaksi transaksi;
    List<TransaksiDetail> transaksiDetail;
    DaftarJual selected;
    DefaultTableModel tableModel;
    final String[] columnName = new String[]{"ID Barang", "Nama Barang", "Satuan", "Harga Satuan", "Jumlah Beli", "Total"};

    public void initData() {

        // init tanggal
        // init dbhelper
        dbHelper = new DBHelperTransaksi();
        // init daftar barang
        daftarJualModel = new DefaultComboBoxModel<>();
        daftarJualList = dbHelper.getBarangJual();
        transaksiDetail = new ArrayList<>();
        transaksi = new Transaksi();
        daftarJualList.add(0, new DaftarJual());
        for (int i = 0; i < daftarJualList.size(); i++) {
            daftarJualModel.addElement(daftarJualList.get(i).toString());
        }
        listDaftarBarang.setModel(daftarJualModel);

        // init table model
        tableModel = new DefaultTableModel(new Object[0][0], columnName);
        tableDetail.setModel(tableModel);
        clearInput();

    }

    public FrameTransaksi(DBHelperTransaksi dbHelper, DefaultComboBoxModel<String> daftarJualModel, List<DaftarJual> daftarJualList, Transaksi transaksi, List<TransaksiDetail> transaksiDetail, DaftarJual selected, DefaultTableModel tableModel, JButton bHapus, JTextField iBayar, JTextField iHarga, JTextField iIdTransaksi, JTextField iJumlahBeli, JTextField iKembalian, JTextArea iKeterangan, JTextField iNamaBarang, JTextField iStok, JTextField iTotal, JButton jButton1, JButton jButton2, JButton jButton3, JComboBox<String> jComboBox1, JComboBox<String> jComboBox2, JComboBox<String> jComboBox3, JComboBox<String> jComboBox4, JComboBox<String> jComboBox5, JComboBox<String> jComboBox6, JLabel jLabel1, JLabel jLabel10, JLabel jLabel11, JLabel jLabel12, JLabel jLabel13, JLabel jLabel14, JLabel jLabel15, JLabel jLabel2, JLabel jLabel3, JLabel jLabel4, JLabel jLabel5, JLabel jLabel6, JLabel jLabel7, JLabel jLabel8, JLabel jLabel9, JPanel jPanel1, JPanel jPanel2, JScrollPane jScrollPane1, JScrollPane jScrollPane2, JScrollPane jScrollPane3, JScrollPane jScrollPane4, JSeparator jSeparator1, JSeparator jSeparator2, JSeparator jSeparator3, JSeparator jSeparator4, JTabbedPane jTabbedPane1, JComboBox<String> listDaftarBarang, JTable tDaftarTransaksi, JTable tDetailTransaksi, JTable tableDetail) throws HeadlessException {
        this.dbHelper = dbHelper;
        this.daftarJualModel = daftarJualModel;
        this.daftarJualList = daftarJualList;
        this.transaksi = transaksi;
        this.transaksiDetail = transaksiDetail;
        this.selected = selected;
        this.tableModel = tableModel;
        this.bHapus = bHapus;
        this.iBayar = iBayar;
        this.iHarga = iHarga;
        this.iJumlahBeli = iJumlahBeli;
        this.iKembalian = iKembalian;
        this.iKeterangan = iKeterangan;
        this.iNamaBarang = iNamaBarang;
        this.iStok = iStok;
        this.iTotal = iTotal;
        this.jButton1 = jButton1;
        this.jButton2 = jButton2;
        this.jButton3 = jButton3;

        this.jLabel1 = jLabel1;
        this.jLabel10 = jLabel10;
        this.jLabel11 = jLabel11;
        this.jLabel12 = jLabel12;
        this.jLabel14 = jLabel14;
        this.jLabel15 = jLabel15;
        this.jLabel2 = jLabel2;
        this.jLabel3 = jLabel3;
        this.jLabel4 = jLabel4;
        this.jLabel5 = jLabel5;
        this.jLabel6 = jLabel6;
        this.jLabel7 = jLabel7;
        this.jLabel8 = jLabel8;
        this.jLabel9 = jLabel9;
        this.jPanel1 = jPanel1;
        this.jPanel2 = jPanel2;
        this.jScrollPane1 = jScrollPane1;
        this.jScrollPane2 = jScrollPane2;
        this.jScrollPane3 = jScrollPane3;
        this.jScrollPane4 = jScrollPane4;
        this.jSeparator1 = jSeparator1;
        this.jSeparator2 = jSeparator2;
        this.jSeparator3 = jSeparator3;
        this.jSeparator4 = jSeparator4;
        this.jTabbedPane1 = jTabbedPane1;
        this.listDaftarBarang = listDaftarBarang;
        this.tDaftarTransaksi = tDaftarTransaksi;
        this.tDetailTransaksi = tDetailTransaksi;
        this.tableDetail = tableDetail;
    }

    List<Transaksi> rekapTransaksi;
    List<TransaksiDetail> rekapTransaksiDetail;
    DefaultTableModel modelRekapTransaksi;
    DefaultTableModel modelRekapTransaksiDetail;
    DBHelperRekapTransaksi dBHelperRekapTransaksi;
    DBHelperRekapTransaksi dBHelperDetailTransaksi;
    final String[] rekapTransaksiColumn = new String[]{"ID Transaksi", "Tanggal", "Tagihan", "Bayar", "Kembalian", "Keterangan"};
    final Object[][] tempRekapTransaksi = new Object[0][0];

    private void initDataTransaksi(long start, long end) {
        dBHelperRekapTransaksi = new DBHelperRekapTransaksi();
        rekapTransaksi = dBHelperRekapTransaksi.selectTransaksi(start, end);
        modelRekapTransaksi = new DefaultTableModel(tempRekapTransaksi, rekapTransaksiColumn);
        modelRekapTransaksiDetail = new DefaultTableModel(tempRekapTransaksi, TransaksiDetail.namaKolom);
        tDaftarTransaksi.setModel(modelRekapTransaksi);
        tDetailTransaksi.setModel(modelRekapTransaksiDetail);
        for (int i = 0; i < rekapTransaksi.size(); i++) {
            modelRekapTransaksi.addRow(rekapTransaksi.get(i).getRow());
        }
        modelRekapTransaksi.fireTableDataChanged();
        dBHelperRekapTransaksi.close();
    }

    private void initDataDetailTransaksi(int idTransaksi) {
        dBHelperDetailTransaksi = new DBHelperRekapTransaksi();
        rekapTransaksiDetail = dBHelperDetailTransaksi.getDetailTransaksi(idTransaksi);
        modelRekapTransaksiDetail.setRowCount(0);

        for (int i = 0; i < rekapTransaksiDetail.size(); i++) {
            modelRekapTransaksiDetail.addRow(rekapTransaksiDetail.get(i).getRow());
        }
        modelRekapTransaksiDetail.fireTableDataChanged();
        dBHelperDetailTransaksi.close();
    }

    private void clearInput() {
        iBayar.setText("");
        iKembalian.setText("");
        iStok.setText("");
        iTotal.setText("");
        iJumlahBeli.setText("");
        iKeterangan.setText("");
        iNamaBarang.setText("");
        iHarga.setText("");
    }

    private void setKembalian() {
        double bayar = iBayar.getText().length() == 0 ? 0 : Double.parseDouble(iBayar.getText());
        iKembalian.setText(bayar - getGrandTotal() + "");
    }

    private void reddingInput() {
        if (iJumlahBeli.getText().length() == 0) {
            return;
        }
        if (listDaftarBarang.getSelectedIndex() != 0) {
            int idBarang = selected.getIdBarang();
            Double pakai = Double.parseDouble(iJumlahBeli.getText());
            if (isStockAvailable(idBarang, pakai)) {
                iJumlahBeli.setForeground(Color.BLACK);
            } else {
                iJumlahBeli.setForeground(Color.red);

            }
        }
    }

    private void reddingBayar() {
        if (isBayarCukup()) {
            iBayar.setForeground(Color.BLACK);
        } else {
            iBayar.setForeground(Color.red);
        }
        setKembalian();
    }

    private boolean isStockAvailable(int idBarang, double jumlahBeli) {
        double stok;
        stok = dbHelper.getStokBarang(idBarang);

        return stok >= jumlahBeli + getJumlahTerpilih(idBarang);
    }

    private double getJumlahTerpilih(int idBarang) {
        double total = 0;
        for (int i = 0; i < transaksiDetail.size(); i++) {
            if (idBarang == transaksiDetail.get(i).getIdBarang()) {
                total += transaksiDetail.get(i).getJumlah();
            }
        }
        return total;
    }

    private boolean isBayarCukup() {
        if (iBayar.getText().length() != 0) {
            double bayar = Double.parseDouble(iBayar.getText());
            return bayar >= getGrandTotal();
        }
        return false;
    }

    private double getGrandTotal() {
        double total = 0;
        for (int i = 0; i < transaksiDetail.size(); i++) {
            total += transaksiDetail.get(i).getHargaTotal();
        }
        return total;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDetail = new javax.swing.JTable();
        listDaftarBarang = new javax.swing.JComboBox<>();
        iNamaBarang = new javax.swing.JTextField();
        iJumlahBeli = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        iKeterangan = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        iHarga = new javax.swing.JTextField();
        iStok = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        iTotal = new javax.swing.JTextField();
        iBayar = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        iKembalian = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        bHapus = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tDaftarTransaksi = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tDetailTransaksi = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        dateStart = new org.jdesktop.swingx.JXDatePicker();
        dateEnd = new org.jdesktop.swingx.JXDatePicker();
        setTanggal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Pilih Barang");

        jLabel2.setText("Harga");

        jLabel3.setText("Stok Gudang");

        tableDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableDetail);

        listDaftarBarang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listDaftarBarangItemStateChanged(evt);
            }
        });
        listDaftarBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listDaftarBarangActionPerformed(evt);
            }
        });

        iNamaBarang.setEditable(false);

        iJumlahBeli.setText("0");
        iJumlahBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iJumlahBeliActionPerformed(evt);
            }
        });
        iJumlahBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                iJumlahBeliKeyReleased(evt);
            }
        });

        jLabel4.setText("Daftar pembelian");

        jButton1.setText("Tambah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Batal");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setText("Keterangan");

        iKeterangan.setColumns(20);
        iKeterangan.setRows(5);
        jScrollPane2.setViewportView(iKeterangan);

        jButton3.setText("PROSES TRANSAKSI");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel6.setText("Nama Barang");

        iHarga.setEditable(false);

        iStok.setEditable(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Jumlah Beli");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("TOTAL");

        jLabel9.setText("PEMBAYARAN");

        iTotal.setEditable(false);

        iBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                iBayarKeyReleased(evt);
            }
        });

        iKembalian.setEditable(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel10.setText("KEMBALIAN");

        bHapus.setText("Hapus");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bHapus))
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator3)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel1))
                            .addGap(22, 22, 22)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(listDaftarBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(iNamaBarang)
                                .addComponent(iHarga)
                                .addComponent(iStok)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(jButton2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton1))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(22, 22, 22)
                            .addComponent(iJumlahBeli))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel4)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(iKembalian)
                                .addComponent(iTotal)
                                .addComponent(iBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(bHapus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(listDaftarBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(iNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(iHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(iStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(iJumlahBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jButton1))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(iTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(iBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(iKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addContainerGap(169, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Transaksi", jPanel1);

        jLabel11.setText("Rekap Transaksi");

        tDaftarTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tDaftarTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tDaftarTransaksiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tDaftarTransaksi);

        jLabel12.setText("Detail Transaksi");

        jLabel14.setText("Periode");

        jLabel15.setText("-");

        tDetailTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tDetailTransaksi);

        jLabel16.setText("-");

        setTanggal.setText("Set");
        setTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setTanggalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(dateStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(setTanggal)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16)
                    .addComponent(dateStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setTanggal))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Data Transaksi", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        // {"ID Barang", "Nama Barang", "Satuan", "Harga Satuan", "Jumlah Beli", "Total"}
        if (iJumlahBeli.getText().length() != 0 && selected.getIdBarang() != 0) {
            double jumlahBeli = Double.parseDouble(iJumlahBeli.getText());
            if (isStockAvailable(selected.getIdBarang(), jumlahBeli)) {
                TransaksiDetail detail = new TransaksiDetail();
                Vector row = new Vector();
                double hargaTotal = (selected.getHarga()/selected.getQty()) * jumlahBeli;
                // set Detail transaksi
                detail.setIdBarang(selected.getIdBarang());
                detail.setJumlah(jumlahBeli);
                detail.setJumlahSatuan(selected.getQty());
                detail.setHargaSatuan(selected.getHarga());
                detail.setNamaBarang(selected.getNamaBarang());
                detail.setSatuan(selected.getSatuan());
                detail.setHargaTotal(hargaTotal);
                // tambah detail
                transaksiDetail.add(detail);

                // set tabel
                tableModel.addRow(detail.getRow());
                tableModel.fireTableDataChanged();
                iTotal.setText(getGrandTotal() + "");

                // todo masukin ke jurnal
                reddingInput();
                reddingBayar();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Stok tidak mencukupi", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        listDaftarBarang.setSelectedIndex(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (iBayar.getText().length() != 0 && isBayarCukup()) {
            double bayar = Double.parseDouble(iBayar.getText());
            int idTransaksi = dbHelper.getNewID(Table.Transaksi.TABLE);
            // inserting id
            transaksi.setBayar(bayar);
            transaksi.setKembalian(bayar - getGrandTotal());
            transaksi.setTagihan(getGrandTotal());
            transaksi.setKeterangan(iKeterangan.getText());
            transaksi.setIdTransaksi(idTransaksi);
            dbHelper.insertTransaksi(transaksi);
            for (int i = 0; i < transaksiDetail.size(); i++) {
                transaksiDetail.get(i).setIdTransaksi(idTransaksi);
                dbHelper.insertTrasaksiDetail(transaksiDetail.get(i));
                dbHelper.stokKeluar(transaksiDetail.get(i).getIdBarang(), transaksiDetail.get(i).getJumlah());
            }
            // close db
            dbHelper.close();
            initData();
            initDataTransaksi(-1, -1);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Pembayaran tidak mencukupi", "Peringatan", JOptionPane.ERROR_MESSAGE);
            iBayar.requestFocus();
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    private void listDaftarBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listDaftarBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listDaftarBarangActionPerformed

    private void iJumlahBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iJumlahBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_iJumlahBeliActionPerformed

    private void listDaftarBarangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listDaftarBarangItemStateChanged
        int index = listDaftarBarang.getSelectedIndex();
        if (index != 0) {
            selected = daftarJualList.get(index);
            reddingInput();
            reddingBayar();
            iNamaBarang.setText(selected.getNamaBarang());
            iHarga.setText(selected.getHarga() + " @ " + selected.getQty() + " " + selected.getSatuan());
            iStok.setText(selected.getStok() + " " + selected.getSatuan());

        } else {
            iNamaBarang.setText("");
            iHarga.setText("");
            iStok.setText("");
        }
    }//GEN-LAST:event_listDaftarBarangItemStateChanged

    private void iJumlahBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_iJumlahBeliKeyReleased
        // TODO add your handling code here:
        reddingInput();
    }//GEN-LAST:event_iJumlahBeliKeyReleased

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        // TODO add your handling code here:
        int[] selectedRow = tableDetail.getSelectedRows();
        for (int i = 0; i < selectedRow.length; i++) {
            int j = selectedRow[i];
            transaksiDetail.remove(j);
            tableModel.removeRow(j);
        }
        tableModel.fireTableDataChanged();
        reddingInput();
        reddingBayar();
    }//GEN-LAST:event_bHapusActionPerformed

    private void iBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_iBayarKeyReleased
        // TODO add your handling code here:
        reddingBayar();
    }//GEN-LAST:event_iBayarKeyReleased

    private void tDaftarTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tDaftarTransaksiMouseClicked
        // TODO add your handling code here:

        int selectedRow = tDaftarTransaksi.getSelectedRow();
        System.out.println("say something " + selectedRow);
        if (tDaftarTransaksi.getSelectedRow() >= 0) {
            initDataDetailTransaksi(rekapTransaksi.get(selectedRow).getIdTransaksi());
        }
    }//GEN-LAST:event_tDaftarTransaksiMouseClicked

    private void setTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setTanggalActionPerformed
        Date start = dateStart.getDate();
        Date end = dateEnd.getDate();
        if (start != null || end != null) {
            if (start.getTime() < end.getTime()) {
                System.out.println(start.getTime() + "Start");
                System.out.println(end.getTime() + "End");
                initDataTransaksi(start.getTime(), end.getTime());
            } else {
                JOptionPane.showMessageDialog(rootPane, "Tanggal awal harus lebih kecil daripada tanggal berakhir");
            }
        }
    }//GEN-LAST:event_setTanggalActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bHapus;
    private org.jdesktop.swingx.JXDatePicker dateEnd;
    private org.jdesktop.swingx.JXDatePicker dateStart;
    private javax.swing.JTextField iBayar;
    private javax.swing.JTextField iHarga;
    private javax.swing.JTextField iJumlahBeli;
    private javax.swing.JTextField iKembalian;
    private javax.swing.JTextArea iKeterangan;
    private javax.swing.JTextField iNamaBarang;
    private javax.swing.JTextField iStok;
    private javax.swing.JTextField iTotal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<String> listDaftarBarang;
    private javax.swing.JButton setTanggal;
    private javax.swing.JTable tDaftarTransaksi;
    private javax.swing.JTable tDetailTransaksi;
    private javax.swing.JTable tableDetail;
    // End of variables declaration//GEN-END:variables
}
