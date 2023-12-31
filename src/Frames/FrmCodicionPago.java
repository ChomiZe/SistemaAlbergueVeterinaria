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
public class FrmCodicionPago extends javax.swing.JDialog {
     Garra conex;
    Statement ejecutar;
    ResultSet resultado;
    String sentencia;
    char operacion;
    DefaultTableModel tabla = new DefaultTableModel();
    /**
     * Creates new form FrmCodicionPago
     */
    public FrmCodicionPago(java.awt.Frame parent, boolean modal) {
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
        tablaCondiciones = new javax.swing.JTable();
        txtDescripcond = new javax.swing.JTextField();
        txtIDcond = new javax.swing.JTextField();
        btnAgregar3 = new javax.swing.JButton();
        btnGuardar3 = new javax.swing.JButton();
        btnCancelar3 = new javax.swing.JButton();
        btnModificar3 = new javax.swing.JButton();
        btnEliminar3 = new javax.swing.JButton();
        btnCerrar3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 153, 0));
        jLabel13.setText("REGISTRO DE CONDICIONES DE PAGO");

        jLabel14.setIcon(new javax.swing.ImageIcon("D:\\NetBeansProjects\\Garra\\garrita.png")); // NOI18N
        jLabel14.setText("jLabel4");

        jLabel15.setText("Id Forma pago:");

        jLabel16.setText("Descripcion:");

        tablaCondiciones.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaCondiciones.getTableHeader().setReorderingAllowed(false);
        tablaCondiciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaCondicionesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tablaCondicionesMouseEntered(evt);
            }
        });
        jScrollPane4.setViewportView(tablaCondiciones);
        if (tablaCondiciones.getColumnModel().getColumnCount() > 0) {
            tablaCondiciones.getColumnModel().getColumn(0).setMinWidth(50);
            tablaCondiciones.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        txtDescripcond.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcondActionPerformed(evt);
            }
        });
        txtDescripcond.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcondKeyTyped(evt);
            }
        });

        txtIDcond.setEditable(false);
        txtIDcond.setEnabled(false);

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
                            .addComponent(txtDescripcond, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDcond, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                            .addComponent(txtIDcond, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtDescripcond, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaCondicionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCondicionesMouseClicked
        cargarCuadros();
    }//GEN-LAST:event_tablaCondicionesMouseClicked

    private void tablaCondicionesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCondicionesMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaCondicionesMouseEntered

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

    private void txtDescripcondActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcondActionPerformed
        btnGuardar3.doClick();// TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcondActionPerformed

    private void txtDescripcondKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcondKeyTyped
        char car = evt.getKeyChar();
        if((car < 'a' || car > 'z')){
            evt.consume();
        }// TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcondKeyTyped

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
            java.util.logging.Logger.getLogger(FrmCodicionPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCodicionPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCodicionPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCodicionPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmCodicionPago dialog = new FrmCodicionPago(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tablaCondiciones;
    private javax.swing.JTextField txtDescripcond;
    private javax.swing.JTextField txtIDcond;
    // End of variables declaration//GEN-END:variables

    private void inicio() {
        try {
            ejecutar = conex.getConexion().createStatement();
            mostrarCondP();
            modoEdicion(false);
        } catch (SQLException ex) {
            Logger.getLogger(FrmCodicionPago.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

    private void modoEdicion(boolean bl) {
        txtIDcond.setEnabled(bl);
        txtDescripcond.setEnabled(bl);
        
        btnAgregar3.setEnabled(!bl);
        btnGuardar3.setEnabled(bl);
        btnCancelar3.setEnabled(bl);
        btnModificar3.setEnabled(!bl);
        btnEliminar3.setEnabled(!bl);
        btnCerrar3.setEnabled(!bl);
        
        tablaCondiciones.setEnabled(!bl);
        
    
    }

    private void accionAgregar() {
    sentencia = "SELECT MAX(idCondicionPago) FROM CondicionPago";
        try {
            resultado = ejecutar.executeQuery(sentencia);
            resultado.first();
            limpiarCuadros();
            txtIDcond.setText(Integer.toString(resultado.getInt(1)+1));
            modoEdicion(true);
            operacion = 'A';
            txtDescripcond.grabFocus();
        } catch (SQLException ex) {
            Logger.getLogger(FrmCodicionPago.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void accionGuardar() {
        String v1 = txtIDcond.getText();
        String v2 = txtDescripcond.getText();
        if (v2.isEmpty()){
            JOptionPane.showMessageDialog(this, "Debe"+ " ingresar datos"," Revise el casillero vacio",JOptionPane.ERROR_MESSAGE);
            txtDescripcond.grabFocus(); 
             }
        sentencia = "INSERT INTO CondicionPago VALUES('"+v1+"','"+v2+"')";
        if (operacion == 'M'){
            sentencia = "UPDATE CondicionPago SET DescripFormaP='"+v2+"'WHERE idCondicionPago='"+v1+"'";
        }
        try {
            ejecutar.executeUpdate(sentencia);
            limpiarCuadros();
        } catch (SQLException ex) {
    }
        mostrarCondP();
        modoEdicion(false);

    }

    private void accionCancelar() {
       modoEdicion(false);
       limpiarCuadros();  
    }

    private void limpiarCuadros() {
        txtIDcond.setText(null);
        txtDescripcond.setText(null);

    }

    private void mostrarCondP() {
        tabla.setRowCount(0);
        tabla = (DefaultTableModel)tablaCondiciones.getModel();
        sentencia = "SELECT * FROM CondicionPago ";
        try {
        resultado = ejecutar.executeQuery(sentencia);
        while(resultado.next()){
                Object fila[] = new Object[2];
                fila[0] = resultado.getInt(1);
                fila[1] = resultado.getString(2);
                
                tabla.addRow(fila);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrmCodicionPago.class.getName()).log(Level.SEVERE, null, ex);
    }

    }

    private void accionModificar() {
        if(tablaCondiciones.getSelectedRow()>=0){
            operacion = 'M';
            modoEdicion(true);
            txtDescripcond.grabFocus();
       }else{
            JOptionPane.showMessageDialog(this,"No se ha seleccionado ningun registro","Seleccione...",JOptionPane.ERROR_MESSAGE);
        }
        }
 
    private void accionEliminar() {
         int fsel = tablaCondiciones.getSelectedRow();
        if(fsel>=0){
             String xid = tablaCondiciones.getValueAt(fsel, 0).toString();
             String xdes = tablaCondiciones.getValueAt(fsel,1).toString(); 
             int resp = JOptionPane.showConfirmDialog(this, "Confirma que desea eliminar la Codicion de pago *"+xdes.toUpperCase()+"* del Sistema?","Confirme...",
                    JOptionPane.YES_NO_OPTION);
             
              if(resp == JOptionPane.YES_OPTION){
                sentencia = "DELETE FROM CondicionPago WHERE idCondicionPago='"+xid+"'";
     
                 try {
                    ejecutar.executeUpdate(sentencia);
                } catch (SQLException ex) {
                   // Logger.getLogger(FrmCodicionPago.class.getName()).log(Level.SEVERE, null, ex);
                } 

    
             mostrarCondP();
              limpiarCuadros(); 
        }
    }
    }
    
    private void cargarCuadros() {
        int fsel = tablaCondiciones.getSelectedRow();
        if(fsel>=0){
             String xid = tablaCondiciones.getValueAt(fsel, 0).toString();
              sentencia = "SELECT * FROM CondicionPago WHERE idCondicionPago = '"+xid+"'";
            try {
                resultado = ejecutar.executeQuery(sentencia);
                resultado.first();
                txtIDcond.setText(resultado.getString(1));
                txtDescripcond.setText(resultado.getString(2));
            } catch (SQLException ex) {
                Logger.getLogger(FrmMonedas.class.getName()).log(Level.SEVERE, null, ex);
            }  
    
    }
}
}