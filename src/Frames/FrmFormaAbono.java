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
public class FrmFormaAbono extends javax.swing.JDialog {
 Garra conex;
    Statement ejecutar;
    ResultSet resultado;
    String sentencia;
    char operacion;
    DefaultTableModel tabla = new DefaultTableModel();
   
    /**
     * Creates new form FrmFormaAbono
     */
    public FrmFormaAbono(java.awt.Frame parent, boolean modal) {
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
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaFormas = new javax.swing.JTable();
        txtDescripform = new javax.swing.JTextField();
        txtIDform = new javax.swing.JTextField();
        btnAgregar3 = new javax.swing.JButton();
        btnGuardar3 = new javax.swing.JButton();
        btnCancelar3 = new javax.swing.JButton();
        btnModificar3 = new javax.swing.JButton();
        btnEliminar3 = new javax.swing.JButton();
        btnCerrar3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 153, 0));
        jLabel13.setText("REGISTRO DE FORMAS DE PAGO");

        jLabel15.setText("Id Forma pago:");

        jLabel16.setText("Descripcion:");

        tablaFormas.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaFormas.getTableHeader().setReorderingAllowed(false);
        tablaFormas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaFormasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tablaFormasMouseEntered(evt);
            }
        });
        jScrollPane4.setViewportView(tablaFormas);
        if (tablaFormas.getColumnModel().getColumnCount() > 0) {
            tablaFormas.getColumnModel().getColumn(0).setMinWidth(50);
            tablaFormas.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        txtDescripform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripformActionPerformed(evt);
            }
        });
        txtDescripform.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripformKeyTyped(evt);
            }
        });

        txtIDform.setEditable(false);
        txtIDform.setEnabled(false);

        btnAgregar3.setText("Añadir");
        btnAgregar3.setToolTipText("click para agregar");
        btnAgregar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregar3ActionPerformed(evt);
            }
        });

        btnGuardar3.setText("Aceptar");
        btnGuardar3.setToolTipText("click para guardar");
        btnGuardar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar3ActionPerformed(evt);
            }
        });

        btnCancelar3.setText("Cancelar");
        btnCancelar3.setToolTipText("click para cancelar");
        btnCancelar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar3ActionPerformed(evt);
            }
        });

        btnModificar3.setText("Editar");
        btnModificar3.setToolTipText("click para modificar");
        btnModificar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificar3ActionPerformed(evt);
            }
        });

        btnEliminar3.setText("Suprimir");
        btnEliminar3.setToolTipText("click para eliminar");
        btnEliminar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar3ActionPerformed(evt);
            }
        });

        btnCerrar3.setText("Cerrar");
        btnCerrar3.setToolTipText("click para cerrar");
        btnCerrar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar3ActionPerformed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon("J:\\Garra\\garrita.png")); // NOI18N
        jLabel11.setText("jLabel4");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnAgregar3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrar3)
                        .addGap(0, 4, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDescripform, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIDform, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 5, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(txtIDform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(txtDescripform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar3)
                    .addComponent(btnGuardar3)
                    .addComponent(btnCancelar3)
                    .addComponent(btnModificar3)
                    .addComponent(btnEliminar3)
                    .addComponent(btnCerrar3))
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

    private void tablaFormasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaFormasMouseClicked
        cargarCuadros();
    }//GEN-LAST:event_tablaFormasMouseClicked

    private void tablaFormasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaFormasMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaFormasMouseEntered

    private void btnAgregar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar3ActionPerformed
        accionAgregar();
    }//GEN-LAST:event_btnAgregar3ActionPerformed

    private void btnGuardar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar3ActionPerformed
        accionGuardar();
    }//GEN-LAST:event_btnGuardar3ActionPerformed

    private void btnCancelar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar3ActionPerformed
        accionCancelar();
    }//GEN-LAST:event_btnCancelar3ActionPerformed

    private void btnModificar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificar3ActionPerformed
        accionModificar(); // TODO add your handling code here:
    }//GEN-LAST:event_btnModificar3ActionPerformed

    private void btnEliminar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar3ActionPerformed
        accionEliminar();
    }//GEN-LAST:event_btnEliminar3ActionPerformed

    private void btnCerrar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar3ActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrar3ActionPerformed

    private void txtDescripformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripformActionPerformed
       btnGuardar3.doClick(); // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripformActionPerformed

    private void txtDescripformKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripformKeyTyped
        char car = evt.getKeyChar();
        if((car < 'a' || car > 'z')){
            evt.consume(); 
        } // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripformKeyTyped

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
            java.util.logging.Logger.getLogger(FrmFormaAbono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmFormaAbono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmFormaAbono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmFormaAbono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmFormaAbono dialog = new FrmFormaAbono(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAgregar3;
    private javax.swing.JButton btnCancelar3;
    private javax.swing.JButton btnCerrar3;
    private javax.swing.JButton btnEliminar3;
    private javax.swing.JButton btnGuardar3;
    private javax.swing.JButton btnModificar3;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tablaFormas;
    private javax.swing.JTextField txtDescripform;
    private javax.swing.JTextField txtIDform;
    // End of variables declaration//GEN-END:variables

    private void inicio() {
        try {
            ejecutar = conex.getConexion().createStatement();
            mostrarFormap();
            modoEdicion(false);
        } catch (SQLException ex) {
            Logger.getLogger(FrmFormaAbono.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

    private void modoEdicion(boolean bl) {
        txtIDform.setEnabled(bl);
        txtDescripform.setEnabled(bl);
        
        btnAgregar3.setEnabled(!bl);
        btnGuardar3.setEnabled(bl);
        btnCancelar3.setEnabled(bl);
        btnModificar3.setEnabled(!bl);
        btnEliminar3.setEnabled(!bl);
        btnCerrar3.setEnabled(!bl);
        
        tablaFormas.setEnabled(!bl);
        
        
    }

    private void accionGuardar() {
       String v1 = txtIDform.getText();
        String v2 = txtDescripform.getText();
        if (v2.isEmpty()){
            JOptionPane.showMessageDialog(this, "Debe"+ " ingresar datos"," Revise el casillero vacio",JOptionPane.ERROR_MESSAGE);
            txtDescripform.grabFocus(); 
             }
        sentencia = "INSERT INTO FormaAbono VALUES('"+v1+"','"+v2+"')";
        if (operacion == 'M'){
            sentencia = "UPDATE FormaAbono SET DescripAbono='"+v2+"'WHERE idFormaAbono='"+v1+"'";
        }
        try {
            ejecutar.executeUpdate(sentencia);
            limpiarCuadros();
        } catch (SQLException ex) {
    }
        mostrarFormap();
        modoEdicion(false);

    }

    private void accionAgregar() {
       sentencia = "SELECT MAX(idFormaAbono) FROM FormaAbono";
        try {
            resultado = ejecutar.executeQuery(sentencia);
            resultado.first();
            limpiarCuadros();
            txtIDform.setText(Integer.toString(resultado.getInt(1)+1));
            modoEdicion(true);
            operacion = 'A';
            txtDescripform.grabFocus();
        } catch (SQLException ex) {
            Logger.getLogger(FrmFormaAbono.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }

    private void limpiarCuadros() {
       txtIDform.setText(null);
        txtDescripform.setText(null);
    
    }

    private void mostrarFormap() {
       tabla.setRowCount(0);
        tabla = (DefaultTableModel)tablaFormas.getModel();
        sentencia = "SELECT * FROM FormaAbono";
        try {
        resultado = ejecutar.executeQuery(sentencia);
        while(resultado.next()){
                Object fila[] = new Object[2];
                fila[0] = resultado.getInt(1);
                fila[1] = resultado.getString(2);
                
                tabla.addRow(fila);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrmFormaAbono.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    private void accionModificar() {
        if(tablaFormas.getSelectedRow()>=0){
            operacion = 'M';
            modoEdicion(true);
            txtDescripform.grabFocus();
       }else{
            JOptionPane.showMessageDialog(this,"No se ha seleccionado ningun registro","Seleccione...",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionCancelar() {
        modoEdicion(false);
       limpiarCuadros();
    }

    private void accionEliminar() {
       int fsel = tablaFormas.getSelectedRow();
        if(fsel>=0){
             String xid = tablaFormas.getValueAt(fsel, 0).toString();
             String xdes = tablaFormas.getValueAt(fsel,1).toString();
             int resp = JOptionPane.showConfirmDialog(this, "Confirma que desea eliminar la Forma de Pago *"+xdes.toUpperCase()+"* del Sistema?","Confirme...",
                    JOptionPane.YES_NO_OPTION);
              if(resp == JOptionPane.YES_OPTION){
                sentencia = "DELETE FROM FormaAbono WHERE idFormaAbono='"+xid+"'";
                 try {
                    ejecutar.executeUpdate(sentencia);
                } catch (SQLException ex) {
                    Logger.getLogger(FrmFormaAbono.class.getName()).log(Level.SEVERE, null, ex);
                } 
    }
              mostrarFormap();
              limpiarCuadros();
    }
    }

    private void cargarCuadros() {
        int fsel = tablaFormas.getSelectedRow();
        if(fsel>=0){
             String xid = tablaFormas.getValueAt(fsel, 0).toString();
              sentencia = "SELECT * FROM FormaAbono WHERE idFormaAbono = '"+xid+"'";
            try {
                resultado = ejecutar.executeQuery(sentencia);
                resultado.first();
                txtIDform.setText(resultado.getString(1));
                txtDescripform.setText(resultado.getString(2));
            } catch (SQLException ex) {
                Logger.getLogger(FrmFormaAbono.class.getName()).log(Level.SEVERE, null, ex);
            }  
    
    }
}
}