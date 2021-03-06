/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockie.Gui.Keuangan;

import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.combobox.ListComboBoxModel;
import org.sqlite.core.DB;
import stockie.Model.DB.DBKeuangan;
import stockie.Model.Jurnal;
import stockie.Model.KeuanganAkun;

/**
 *
 * @author dalbo
 */
public class FrameLapKeuangan extends javax.swing.JFrame {

    /**
     * Creates new form FrameLapKeuangan
     */
    public FrameLapKeuangan() {
        initComponents();
        initData();
    }

    public void initData() {
        initTableJurnal();
        initAkunBukuBesar();
    }

    DefaultListModel<String> modelListAkun;
    List<KeuanganAkun> dataAkun;

    public void initAkunBukuBesar() {
        DBKeuangan db = new DBKeuangan();
        modelListAkun = new DefaultListModel<>();
        dataAkun = db.getDaftarAkun();
        for (int i = 0; i < dataAkun.size(); i++) {
            modelListAkun.addElement(dataAkun.get(i).getNamaAkun());
        }
        listAkun.setModel(modelListAkun);
        db.close();
    }

    int selectedBukuBesarAkun = 0;
    List<Jurnal> dataBukuBesar;
    DefaultTableModel modelBukuBesar;

    public void initBukuBesar() {
        if (selectedBukuBesarAkun > 0) {
            DBKeuangan db = new DBKeuangan();
            modelBukuBesar = new DefaultTableModel(new Object[0][0], Jurnal.columnNameSaldo);
            if (bukuStart.getDate() != null && bukuStart.getDate() != null) {
                dataBukuBesar = db.getBukuBesar(selectedBukuBesarAkun, bukuStart.getDate().getTime(), bukuEnd.getDate().getTime());
            } else {
                dataBukuBesar = db.getBukuBesar(selectedBukuBesarAkun);
            }

            for (int i = 0; i < dataBukuBesar.size(); i++) {
                modelBukuBesar.addRow(dataBukuBesar.get(i).getRowSaldo());
            }
            tBuku.setModel(modelBukuBesar);
            modelBukuBesar.fireTableDataChanged();
            db.close();
        }
    }

    DefaultTableModel modelJurnal;
    List<Jurnal> dataJurnal;

    public void initTableJurnal() {
        DBKeuangan db = new DBKeuangan();
        Object[][] obj = new Object[0][0];
        modelJurnal = new DefaultTableModel(obj, Jurnal.columnName);
        tJurnal.setModel(modelJurnal);
        dataJurnal = db.getJurnal();
        for (int i = 0; i < dataJurnal.size(); i++) {
            modelJurnal.addRow(dataJurnal.get(i).getRow());

        }
        modelJurnal.fireTableDataChanged();
        db.close();
    }

    public void initTableJurnal(long start, long end) {
        DBKeuangan db = new DBKeuangan();
        Object[][] obj = new Object[0][0];
        modelJurnal = new DefaultTableModel(obj, Jurnal.columnName);
        tJurnal.setModel(modelJurnal);
        dataJurnal = db.getJurnal(start, end);
        for (int i = 0; i < dataJurnal.size(); i++) {
            modelJurnal.addRow(dataJurnal.get(i).getRow());

        }
        modelJurnal.fireTableDataChanged();
        db.close();
    }

    public void setKeterangan(int index) {
        iKeterangan.setText(dataJurnal.get(index).getKeterangan());
    }

    public void setBukuBesarKeterangan(int index) {
        iKetBukuBesar.setText(dataBukuBesar.get(index).getKeterangan());
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
        dateStart = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        dateEnd = new org.jdesktop.swingx.JXDatePicker();
        jScrollPane1 = new javax.swing.JScrollPane();
        tJurnal = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        iKeterangan = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tBuku = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        bukuEnd = new org.jdesktop.swingx.JXDatePicker();
        jLabel5 = new javax.swing.JLabel();
        bukuStart = new org.jdesktop.swingx.JXDatePicker();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        listAkun = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        iKetBukuBesar = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Laporan Keuangan");

        jLabel1.setText("Periode");

        jLabel2.setText("-");

        tJurnal.setModel(new javax.swing.table.DefaultTableModel(
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
        tJurnal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tJurnalMouseClicked(evt);
            }
        });
        tJurnal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tJurnalKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tJurnal);

        jButton1.setText("Set");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        iKeterangan.setEditable(false);
        iKeterangan.setColumns(20);
        iKeterangan.setRows(5);
        jScrollPane2.setViewportView(iKeterangan);

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(dateStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(dateStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(dateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Jurnal Keuangan", jPanel1);

        tBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tBukuMouseClicked(evt);
            }
        });
        tBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tBukuKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tBuku);

        jLabel3.setText("Pilih akun");

        jLabel4.setText("Buku besar");

        jButton3.setText("Set");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel5.setText("-");

        jLabel6.setText("Periode");

        listAkun.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listAkun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listAkunMouseClicked(evt);
            }
        });
        listAkun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                listAkunKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(listAkun);

        iKetBukuBesar.setEditable(false);
        iKetBukuBesar.setColumns(20);
        iKetBukuBesar.setRows(5);
        jScrollPane3.setViewportView(iKetBukuBesar);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bukuStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bukuEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jButton3)
                    .addComponent(bukuEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(bukuStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Buku Besar", jPanel2);

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

    private void listAkunKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listAkunKeyReleased
        // TODO add your handling code here:
        selectedBukuBesarAkun = dataAkun.get(listAkun.getSelectedIndex()).getIdAkun();
        initBukuBesar();
    }//GEN-LAST:event_listAkunKeyReleased

    private void listAkunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listAkunMouseClicked
        // TODO add your handling code here:
        selectedBukuBesarAkun = dataAkun.get(listAkun.getSelectedIndex()).getIdAkun();
        initBukuBesar();
    }//GEN-LAST:event_listAkunMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        selectedBukuBesarAkun = dataAkun.get(listAkun.getSelectedIndex()).getIdAkun();
        initBukuBesar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tBukuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tBukuKeyReleased
        // TODO add your handling code here:
        if (tBuku.getSelectedRow() >= 0) {
            setBukuBesarKeterangan(tBuku.getSelectedRow());
        }
    }//GEN-LAST:event_tBukuKeyReleased

    private void tBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tBukuMouseClicked
        // TODO add your handling code here:
        if (tBuku.getSelectedRow() >= 0) {
            setBukuBesarKeterangan(tBuku.getSelectedRow());
        }
    }//GEN-LAST:event_tBukuMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        initData();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (dateStart.getDate() != null && dateEnd.getDate() != null) {
            initTableJurnal(dateStart.getDate().getTime(), dateEnd.getDate().getTime());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tJurnalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tJurnalKeyReleased
        // TODO add your handling code here:
        if (tJurnal.getSelectedRow() > -1) {
            setKeterangan(tJurnal.getSelectedRow());
        }
    }//GEN-LAST:event_tJurnalKeyReleased

    private void tJurnalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tJurnalMouseClicked
        // TODO add your handling code here:
        if (tJurnal.getSelectedRow() > -1) {
            setKeterangan(tJurnal.getSelectedRow());
        }
    }//GEN-LAST:event_tJurnalMouseClicked

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
            java.util.logging.Logger.getLogger(FrameLapKeuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameLapKeuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameLapKeuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameLapKeuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameLapKeuangan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker bukuEnd;
    private org.jdesktop.swingx.JXDatePicker bukuStart;
    private org.jdesktop.swingx.JXDatePicker dateEnd;
    private org.jdesktop.swingx.JXDatePicker dateStart;
    private javax.swing.JTextArea iKetBukuBesar;
    private javax.swing.JTextArea iKeterangan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<String> listAkun;
    private javax.swing.JTable tBuku;
    private javax.swing.JTable tJurnal;
    // End of variables declaration//GEN-END:variables
}
