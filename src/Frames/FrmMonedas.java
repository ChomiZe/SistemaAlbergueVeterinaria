/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Frames;
import garra.Garra;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class FrmMonedas extends javax.swing.JDialog {
    Garra conex;
    Statement ejecutar;
    ResultSet resultado;
    String sentencia;
    char operacion;
    DefaultTableModel tabla = new DefaultTableModel();
    /**
     * Creates new form FrmMonedas
     */
    public FrmMonedas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
         this.setLocationRelativeTo(null);
        inicio();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaMonedas = new javax.swing.JTable();
        txtDescripmon = new javax.swing.JTextField();
        txtIDmon = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 153, 0));
        jLabel13.setText("REGISTRO DE MONEDAS");

        jLabel14.setIcon(new javax.swing.ImageIcon("D:\\NetBeansProjects\\Garra\\garrita.png")); // NOI18N
        jLabel14.setText("jLabel4");

        jLabel15.setText("Id Monedas:");

        jLabel16.setText("Descripcion:");

        tablaMonedas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descripcion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaMonedas.getTableHeader().setReorderingAllowed(false);
        tablaMonedas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMonedasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tablaMonedasMouseEntered(evt);
            }
        });
        jScrollPane4.setViewportView(tablaMonedas);
        if (tablaMonedas.getColumnModel().getColumnCount() > 0) {
            tablaMonedas.getColumnModel().getColumn(0).setMinWidth(50);
            tablaMonedas.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        txtDescripmon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripmonActionPerformed(evt);
            }
        });
        txtDescripmon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescripmonKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripmonKeyTyped(evt);
            }
        });

        txtIDmon.setEditable(false);
        txtIDmon.setEnabled(false);

        btnAgregar.setText("Añadir");
        btnAgregar.setToolTipText("click para agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnGuardar.setText("Aceptar");
        btnGuardar.setToolTipText("click para guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("click para cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnModificar.setText("Editar");
        btnModificar.setToolTipText("click para modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Suprimir");
        btnEliminar.setToolTipText("click para eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.setToolTipText("click para cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripmon, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDmon, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrar)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13))
                    .addComponent(jLabel14))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtIDmon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtDescripmon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnCerrar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaMonedasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMonedasMouseClicked
        cargarCuadros();
    }//GEN-LAST:event_tablaMonedasMouseClicked

    private void tablaMonedasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMonedasMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaMonedasMouseEntered

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        accionAgregar();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        accionGuardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        accionCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        accionModificar(); // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        accionEliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtDescripmonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripmonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripmonKeyPressed

    private void txtDescripmonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripmonActionPerformed
        btnGuardar.doClick(); // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripmonActionPerformed

    private void txtDescripmonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripmonKeyTyped
        char car = evt.getKeyChar();
        if((car < 'A' || car > 'z')){
            evt.consume();
        } // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripmonKeyTyped

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMonedas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMonedas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMonedas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMonedas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmMonedas dialog = new FrmMonedas(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tablaMonedas;
    private javax.swing.JTextField txtDescripmon;
    private javax.swing.JTextField txtIDmon;
    // End of variables declaration//GEN-END:variables

    private void inicio() {
       try {
            ejecutar = conex.getConexion().createStatement();
            mostrarMonedas();
            modoEdicion(false);
        } catch (SQLException ex) {
            Logger.getLogger(FrmMonedas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modoEdicion(boolean bl) {
        txtIDmon.setEnabled(bl);
        txtDescripmon.setEnabled(bl);
        
        btnAgregar.setEnabled(!bl);
        btnGuardar.setEnabled(bl);
        btnCancelar.setEnabled(bl);
        btnModificar.setEnabled(!bl);
        btnEliminar.setEnabled(!bl);
        btnCerrar.setEnabled(!bl);
        
        tablaMonedas.setEnabled(!bl);
        
    }

    private void accionAgregar() {
        sentencia = "SELECT MAX(idMonedas) FROM Monedas";
        try {
            resultado = ejecutar.executeQuery(sentencia);
            resultado.first();
            limpiarCuadros();
            txtIDmon.setText(Integer.toString(resultado.getInt(1)+1));
            modoEdicion(true);
            operacion = 'A';
            txtDescripmon.grabFocus();
        } catch (SQLException ex) {
            Logger.getLogger(FrmMonedas.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    private void accionGuardar() {
        String v1 = txtIDmon.getText();
        String v2 = txtDescripmon.getText();
        if (v2.isEmpty()){
            JOptionPane.showMessageDialog(this, "Debe"+ " ingresar datos"," Revise el casillero vacio",JOptionPane.ERROR_MESSAGE);
            txtDescripmon.grabFocus(); 
             }
        sentencia = "INSERT INTO Monedas VALUES('"+v1+"','"+v2+"')";
        if (operacion == 'M'){
            sentencia = "UPDATE Monedas SET Descrip='"+v2+"'WHERE idMonedas='"+v1+"'";
        }
        try {
            ejecutar.executeUpdate(sentencia);
            limpiarCuadros();
        } catch (SQLException ex) {
    }
        mostrarMonedas();
        modoEdicion(false);
}

    private void limpiarCuadros() {
        txtIDmon.setText(null);
        txtDescripmon.setText(null);
    }

    private void mostrarMonedas() {
        tabla.setRowCount(0);
        tabla = (DefaultTableModel)tablaMonedas.getModel();
        sentencia = "SELECT * FROM Monedas";
        try {
        resultado = ejecutar.executeQuery(sentencia);
        while(resultado.next()){
                Object fila[] = new Object[2];
                fila[0] = resultado.getInt(1);
                fila[1] = resultado.getString(2);
                
                tabla.addRow(fila);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrmMonedas.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    private void accionCancelar() {
       modoEdicion(false);
       limpiarCuadros();
    }

    private void accionModificar() {
      if(tablaMonedas.getSelectedRow()>=0){
            operacion = 'M';
            modoEdicion(true);
            txtDescripmon.grabFocus();
       }else{
            JOptionPane.showMessageDialog(this,"No se ha seleccionado ningun registro","Seleccione...",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminar() {
       int fsel = tablaMonedas.getSelectedRow();
        if(fsel>=0){
             String xid = tablaMonedas.getValueAt(fsel, 0).toString();
             String xdes = tablaMonedas.getValueAt(fsel,1).toString();
             int resp = JOptionPane.showConfirmDialog(this, "Confirma que desea eliminar la Moneda *"+xdes.toUpperCase()+"* del Sistema?","Confirme...",
                    JOptionPane.YES_NO_OPTION);
              if(resp == JOptionPane.YES_OPTION){
                sentencia = "DELETE FROM Monedas WHERE idMonedas='"+xid+"'";
                 try {
                    ejecutar.executeUpdate(sentencia);
                } catch (SQLException ex) {
                    Logger.getLogger(FrmMonedas.class.getName()).log(Level.SEVERE, null, ex);
                } 
    }
              mostrarMonedas();
              limpiarCuadros();
    }
    }

    private void cargarCuadros() {
      int fsel = tablaMonedas.getSelectedRow();
        if(fsel>=0){
             String xid = tablaMonedas.getValueAt(fsel, 0).toString();
              sentencia = "SELECT * FROM Monedas WHERE idMonedas = '"+xid+"'";
            try {
                resultado = ejecutar.executeQuery(sentencia);
                resultado.first();
                txtIDmon.setText(resultado.getString(1));
                txtDescripmon.setText(resultado.getString(2));
            } catch (SQLException ex) {
                Logger.getLogger(FrmMonedas.class.getName()).log(Level.SEVERE, null, ex);
            }  
    }
}
}