/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockie.Gui.Barang;

import java.util.List;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import stockie.Model.BarangPembelian;
import stockie.Model.DB.DBBarang;
import stockie.Model.DB.DBHelper;
import stockie.Model.DB.DBKeuangan;
import stockie.Model.KeuanganAkun;
import stockie.Model.Satuan;

/**
 *
 * @author dalbo
 */
public class FrameBarang extends javax.swing.JFrame {

    /**
     * Creates new form FrameBarang
     */
    DBBarang dBBarang;

    public FrameBarang() {
        initComponents();
        initData();
    }

    public void initData() {
        dBBarang = new DBBarang();
        initTable(-1, -1);
        initAkun();
        initSatuan();
    }
    DefaultTableModel tableModel;
    List<BarangPembelian> dataPembelian;
    Object object[][] = new Object[0][0];

    public void initTable(long start, long end) {
        tableModel = new DefaultTableModel(object, BarangPembelian.namaKolom);
        tPembelian.setModel(tableModel);
        dataPembelian = dBBarang.selectPembelianBarang(start, end);
        for (int i = 0; i < dataPembelian.size(); i++) {
            tableModel.addRow(dataPembelian.get(i).getRow());
        }
        tableModel.fireTableDataChanged();
    }
    ComboBoxModel<String> modelAkun;
    List<KeuanganAkun> dataAkun;
    DBKeuangan dBKeuangan;

    public void initAkun() {
        dBKeuangan = new DBKeuangan();
        dataAkun = dBKeuangan.getDaftarAkun(DBKeuangan.Kelompok.aset);
        String row[] = new String[dataAkun.size()];
        for (int i = 0; i < dataAkun.size(); i++) {
            row[i] = dataAkun.get(i).toString();
        }
        modelAkun = new DefaultComboBoxModel<>(row);
        listAkun.setModel(modelAkun);
        dBKeuangan.close();
        setAkun();
    }

    int idAkun;

    private void setAkun() {
        idAkun = dataAkun.get(listAkun.getSelectedIndex()).getIdAkun();
    }
    double selisih;
    double hargaBeliSatuan;
    double hargaJualSatuan;
    double hargaBeli;
    double qtyBeli;
    double qtyJual;

    public void hitungHarga() {
        if (iHarga.getText().length() > 0 && iQty.getText().length() > 0 && iHargaJual.getText().length() > 0 && iSatuanJual.getText().length() > 0) {
            hargaBeli = Double.parseDouble(iHarga.getText());
            qtyBeli = Double.parseDouble(iQty.getText());
            qtyJual = Double.parseDouble(iSatuanJual.getText());
            hargaJualSatuan = Double.parseDouble(iHargaJual.getText()) / qtyJual;
            hargaBeliSatuan = hargaBeli / qtyBeli;
            selisih = hargaJualSatuan - hargaBeliSatuan;
            iHargaSatuanBeli.setText(Double.toString(hargaBeliSatuan));
            iSelisihHarga.setText(Double.toString(selisih));
        }
    }

    ComboBoxModel<String> modelSatuan;
    List<Satuan> dataSatuan;

    public void initSatuan() {
        dataSatuan = dBBarang.selectSatuan();
        Vector vectorSatuan = new Vector();
        for (int i = 0; i < dataSatuan.size(); i++) {
            vectorSatuan.add(dataSatuan.get(i).toString());
        }
        modelSatuan = new DefaultComboBoxModel<>(vectorSatuan);
        listSatuan.setModel(modelSatuan);
        setSatuan();
    }
    int idSatuan;

    private void setSatuan() {
        idSatuan = dataSatuan.get(listSatuan.getSelectedIndex()).getIdSatuan();
    }

    BarangPembelian pembelian;

    private void simpan() {
        if (checkData()) {
            DBBarang dd = new DBBarang();
            pembelian = new BarangPembelian();
            pembelian.setHargaJual(hargaJualSatuan);
            pembelian.setQtyJual(qtyJual);
            pembelian.setHargaKulak(hargaBeli);
            pembelian.setQty(qtyBeli);
            pembelian.setNamaBarang(iNamaBarang.getText());
            pembelian.setTanggal(iTanggal.getDate().getTime());
            pembelian.setIdSatuan(idSatuan);
            pembelian.setIdAkun(idAkun);
            dd.insertPembelian(pembelian);
            dd.close();
            isiJurnal();
            initData();
        }
    }
    private void isiJurnal(){
        DBKeuangan db = new DBKeuangan();
        db.setKredit(idAkun, pembelian.getHargaKulak(), pembelian.getTanggal(), "Pembelian barang "+pembelian.getNamaBarang());
        db.setDebet(DBKeuangan.Akun.persediaan, pembelian.getHargaKulak(), pembelian.getTanggal(), "Penggunaan pembelian "+pembelian.getNamaBarang());
        db.tambahSaldoDebet(DBKeuangan.Akun.persediaan, pembelian.getHargaKulak());
        db.tambahSaldoKredit(idAkun, pembelian.getHargaKulak());
        db.close();
    }

    private boolean checkData() {
        if(dataAkun.get(listAkun.getSelectedIndex()).getSaldo() < hargaBeli){
            JOptionPane.showMessageDialog(rootPane, "Saldo akun \""+dataAkun.get(idAkun).getNamaAkun()+"\" tidak mencukupi");
            return false;
        }
        if (iNamaBarang.getText().length() <= 0) {
            JOptionPane.showMessageDialog(rootPane, "Nama barang belum diisi");
            return false;
        }
        if (iQty.getText().length() <= 0) {
            JOptionPane.showMessageDialog(rootPane, "Kuantitas pembelian belum diisi");
            return false;
        }
        if (iSatuanJual.getText().length() <= 0) {
            JOptionPane.showMessageDialog(rootPane, "Kuantitas penjualan belum diisi");
            return false;
        }
        if (iHarga.getText().length() <= 0) {
            JOptionPane.showMessageDialog(rootPane, "Biaya/harga pembelian belum diisi");
            return false;
        }
        if (iHargaJual.getText().length() <= 0) {
            JOptionPane.showMessageDialog(rootPane, "Harga jual satuan belum diisi");
            return false;
        }
        if (iTanggal.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Tanggal pembelian belum ditentukan");
            return false;
        }
        return true;
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
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        dateStart = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        dateEnd = new org.jdesktop.swingx.JXDatePicker();
        bSet = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tPembelian = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        iHargaSatuanBeli = new javax.swing.JTextField();
        iSelisihHarga = new javax.swing.JTextField();
        listAkun = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        iTanggal = new org.jdesktop.swingx.JXDatePicker();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        iNamaBarang = new javax.swing.JTextField();
        iHarga = new javax.swing.JTextField();
        iQty = new javax.swing.JTextField();
        listSatuan = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        iHargaJual = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        iSatuanJual = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        bSimpan = new javax.swing.JButton();
        bBatal = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Data pembelian barang");

        jLabel2.setText("Periode");

        jLabel3.setText("-");

        bSet.setText("Set");

        tPembelian.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tPembelian);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Informasi pembelian"));

        jLabel10.setText("Harga beli per satuan");

        jLabel11.setText("Selisih harga jual-beli");

        iHargaSatuanBeli.setEditable(false);

        iSelisihHarga.setEditable(false);

        listAkun.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        listAkun.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listAkunItemStateChanged(evt);
            }
        });

        jLabel12.setText("Akun pembayaran");

        jLabel13.setText("Tanggal");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(iHargaSatuanBeli, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(iSelisihHarga)
                            .addComponent(listAkun, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(iTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(iHargaSatuanBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(iSelisihHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listAkun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(iTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Pembelian persediaan barang"));

        jLabel4.setText("Nama barang");

        jLabel5.setText("Harga");

        jLabel6.setText("Kuantitas");

        jLabel7.setText("Satuan");

        iHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                iHargaKeyReleased(evt);
            }
        });

        iQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iQtyActionPerformed(evt);
            }
        });
        iQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                iQtyKeyReleased(evt);
            }
        });

        listSatuan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listSatuanItemStateChanged(evt);
            }
        });

        jLabel8.setText("Harga jual per Satuan");

        iHargaJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                iHargaJualKeyReleased(evt);
            }
        });

        jLabel9.setText("Satuan per jual");

        iSatuanJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                iSatuanJualKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(iNamaBarang)
                            .addComponent(iHarga)
                            .addComponent(iQty)
                            .addComponent(listSatuan, 0, 210, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(iSatuanJual, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(iHargaJual))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(iNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(iHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(iQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(listSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(iHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(iSatuanJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bSimpan.setText("Simpan");
        bSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSimpanActionPerformed(evt);
            }
        });

        bBatal.setText("Batal");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bSet)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(bBatal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bSimpan)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(dateStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(dateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bSet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bSimpan)
                            .addComponent(bBatal)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pembelian", jPanel1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Retur", jPanel4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Pengaturan Harga", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Stok Barang", jPanel2);

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

    private void iQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_iQtyActionPerformed

    private void iHargaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_iHargaKeyReleased
        // TODO add your handling code here:
        hitungHarga();
    }//GEN-LAST:event_iHargaKeyReleased

    private void iQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_iQtyKeyReleased
        // TODO add your handling code here:
        hitungHarga();
    }//GEN-LAST:event_iQtyKeyReleased

    private void iHargaJualKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_iHargaJualKeyReleased
        // TODO add your handling code here:
        hitungHarga();
    }//GEN-LAST:event_iHargaJualKeyReleased

    private void iSatuanJualKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_iSatuanJualKeyReleased
        // TODO add your handling code here:
        hitungHarga();
    }//GEN-LAST:event_iSatuanJualKeyReleased

    private void listSatuanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listSatuanItemStateChanged
        // TODO add your handling code here:
        setSatuan();
    }//GEN-LAST:event_listSatuanItemStateChanged

    private void bSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSimpanActionPerformed
        // TODO add your handling code here:
        simpan();
    }//GEN-LAST:event_bSimpanActionPerformed

    private void listAkunItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listAkunItemStateChanged
        // TODO add your handling code here:
        setAkun();
    }//GEN-LAST:event_listAkunItemStateChanged

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
            java.util.logging.Logger.getLogger(FrameBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBatal;
    private javax.swing.JButton bSet;
    private javax.swing.JButton bSimpan;
    private org.jdesktop.swingx.JXDatePicker dateEnd;
    private org.jdesktop.swingx.JXDatePicker dateStart;
    private javax.swing.JTextField iHarga;
    private javax.swing.JTextField iHargaJual;
    private javax.swing.JTextField iHargaSatuanBeli;
    private javax.swing.JTextField iNamaBarang;
    private javax.swing.JTextField iQty;
    private javax.swing.JTextField iSatuanJual;
    private javax.swing.JTextField iSelisihHarga;
    private org.jdesktop.swingx.JXDatePicker iTanggal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<String> listAkun;
    private javax.swing.JComboBox<String> listSatuan;
    private javax.swing.JTable tPembelian;
    // End of variables declaration//GEN-END:variables
}
