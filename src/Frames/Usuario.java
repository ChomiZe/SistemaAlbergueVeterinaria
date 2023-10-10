/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Frames;
import java.sql.ResultSet;
//import clases.classreportes;
import garra.Garra;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Usuario
 */
public class Usuario extends javax.swing.JDialog {
    Garra nCon;
    Statement ejecutar;
    ResultSet resultado;
    String sentencia;
    char operacion;
    DefaultTableModel mitabla = new DefaultTableModel();
    //classreportes jasper = new classreportes();

    /**
     * Creates new form Usuario
     */
    public Usuario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        try {
            ejecutar = nCon.getConexion().createStatement();
             btnAgregar.grabFocus();
            cargarUsuario();
            mostrarUsuario();
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
          modoEdicion(false);
        this.setLocationRelativeTo(null);
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtalias = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        cmbxvol = new javax.swing.JComboBox();
        txtseguridad = new javax.swing.JTextField();
        txtCodseguridad = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        chkActivo = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        txtclave = new javax.swing.JPasswordField();
        jLabel11 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Voluntario:");

        jLabel4.setText("Alais:");

        jLabel6.setText("Clave:");

        txtalias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtaliasActionPerformed(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 0));
        jLabel7.setText("DATOS DEL USUARIO");

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        cmbxvol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbxvolActionPerformed(evt);
            }
        });

        txtseguridad.setEditable(false);
        txtseguridad.setFocusable(false);

        txtCodseguridad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodseguridadActionPerformed(evt);
            }
        });

        jLabel2.setText("Ingrese codigo de seguridad:");

        jLabel9.setText("Activo:");

        chkActivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chkActivoKeyPressed(evt);
            }
        });

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Alias", "Activo", "idVoluntario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaUsuarios);
        if (tablaUsuarios.getColumnModel().getColumnCount() > 0) {
            tablaUsuarios.getColumnModel().getColumn(0).setMinWidth(50);
            tablaUsuarios.getColumnModel().getColumn(0).setMaxWidth(50);
            tablaUsuarios.getColumnModel().getColumn(1).setMinWidth(80);
            tablaUsuarios.getColumnModel().getColumn(1).setMaxWidth(80);
            tablaUsuarios.getColumnModel().getColumn(2).setMinWidth(80);
            tablaUsuarios.getColumnModel().getColumn(2).setMaxWidth(80);
            tablaUsuarios.getColumnModel().getColumn(3).setMinWidth(80);
            tablaUsuarios.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        jLabel11.setIcon(new javax.swing.ImageIcon("J:\\Garra\\garrita.png")); // NOI18N
        jLabel11.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 12, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cmbxvol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtalias)
                                            .addComponent(txtclave, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(80, 80, 80)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtseguridad)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(chkActivo)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(txtCodseguridad, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)))
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
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
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmbxvol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtseguridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtalias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtCodseguridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtclave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(chkActivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnSalir))
                .addGap(49, 49, 49))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtaliasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtaliasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtaliasActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        accionagregar();// TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        accioneleminar();        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
      accionguardar();        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cmbxvolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbxvolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbxvolActionPerformed

    private void txtCodseguridadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodseguridadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodseguridadActionPerformed

    private void chkActivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chkActivoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            txtCodseguridad.grabFocus();
        }
    }//GEN-LAST:event_chkActivoKeyPressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
      accionCancelar();  // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
      accionModificar();  // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
       dispose(); // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed

    private void tablaUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUsuariosMouseClicked
        cargarCuadros();
    }//GEN-LAST:event_tablaUsuariosMouseClicked

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
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Usuario dialog = new Usuario(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JComboBox cmbxvol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaUsuarios;
    private javax.swing.JTextField txtCodseguridad;
    private javax.swing.JTextField txtalias;
    private javax.swing.JPasswordField txtclave;
    private javax.swing.JTextField txtseguridad;
    // End of variables declaration//GEN-END:variables

    private void cargarUsuario() {
        sentencia = " SELECT * FROM Voluntarios";
        try {
            resultado = ejecutar.executeQuery(sentencia);
             while (resultado.next()) {
                cmbxvol.addItem(resultado.getString(1)+"- "+ resultado.getString(2).toUpperCase());
             }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarUsuario() {
       mitabla.setRowCount(0);
         mitabla = (DefaultTableModel) tablaUsuarios.getModel();
        sentencia = "SELECT nombre,alias,usuactivo,idvoluntarios FROM vistausuario";
        try {
            resultado = ejecutar.executeQuery(sentencia);
            while (resultado.next()) {
                Object fila[] = new Object[4];
                fila[0] = resultado.getString(1);
                fila[1] = resultado.getString(2);
                fila[2] = resultado.getString(3);
                fila[3] = resultado.getString(4);
                
               mitabla.addRow(fila);
            }
            tablaUsuarios.setModel( mitabla);
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void modoEdicion(boolean bL) {
        cmbxvol.setEnabled(bL);
        txtalias.setEnabled(bL);
        txtclave.setEnabled(bL);
        chkActivo.setEnabled(bL);
        txtCodseguridad.setEnabled(bL);
        btnAgregar.setEnabled(!bL);
        btnGuardar.setEnabled(bL);
        btnCancelar.setEnabled(bL);
        btnModificar.setEnabled(!bL);
        btnEliminar.setEnabled(!bL);
        btnSalir.setEnabled(!bL);

        tablaUsuarios.setEnabled(!bL);
    }

    private void accionagregar() {
        limpiarCuadros();
        sentencia = "SELECT MAX(idVoluntarios) FROM Usuario";
        try {
            resultado = ejecutar.executeQuery(sentencia);
            resultado.first();
            modoEdicion(true);
            operacion = 'A';
            GenerarCodigo();
            txtalias.grabFocus();
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
       }

    private void limpiarCuadros() {
        txtalias.setText(null);
        txtclave.setText(null);
        txtseguridad.setText(null);
        chkActivo.setText(null);
    }

    private void GenerarCodigo() {
        String xcon = "";
        int r1;
        String cadena = "abcdefghijklmnopqrABCDEFGHIJKLMNO1234567890";
        for (int e = 0; e< 4; e++ ){
            Random w = new Random();
            int rd = w.nextInt(42);
            char xk = cadena.charAt(rd);
            r1 = (int) xk;
            xcon = xcon + xk;
            this.txtseguridad.setText(xcon);
            
        }       

    }

    private void accionguardar() {
         String fu = cmbxvol.getSelectedItem().toString();
        int pos = fu.indexOf("-");
        String v1 = fu.substring(0, pos);
        String v2 = txtalias.getText();
        int x1, x2;
        
          String cadena, v3 = "";
        cadena = txtclave.getText();
        
       for (int i = 0; i < cadena.length(); i++) {
            char xC = cadena.charAt(i);
            x1 = (int) xC;
            x2 = x1 + 128;
            xC = (char) x2;
            v3 = v3 + xC;
        }
         int v4;
        if (chkActivo.isSelected()) {
            v4 = 0;
        } else {
            v4 = 1;
        }
        if (v2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el Alias","Cuadro vacio", JOptionPane.ERROR_MESSAGE);
            txtalias.grabFocus();
        } else {
            if (txtclave.getText().length() <6) {
                JOptionPane.showMessageDialog(this, "Debe ingresar mas de 6 caracter",
                        "Poco caracter", JOptionPane.ERROR_MESSAGE);
                txtclave.grabFocus();
            } else {
                if (txtseguridad.getText().equals(txtseguridad.getText())) {
                    if (operacion == 'A') {
                        sentencia = "INSERT INTO Usuario VALUES('"+v3+"','"+v2+"','"+v4+"','"+v1+"')";
                    
                    }
                    if (operacion == 'M') {
                        sentencia = "UPDATE Usuario SET Alias='"+v2+"',Usuclave='"+v1+"',UsuActivo='"+v3+"'WHERE idVoluntarios='"+v4+"'";
                    }
                     try {
                        ejecutar.executeUpdate(sentencia);
                        limpiarCuadros();
                        modoEdicion(false);
                    } catch (SQLException ex) {
                        //Logger.getLogger(frmUsuario.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(this, sentencia+"  \nNo se guardo nada"+ex);
                    }
                    mostrarUsuario();
                } else {
                    JOptionPane.showMessageDialog(null, "Es diferente el Codigo");
                    txtseguridad.setText(null);
                    txtseguridad.grabFocus();
                    //            limpiarCuadros();
                    GenerarCodigo();
                    modoEdicion(false);
                }
            }
        }
        }

    private void accionCancelar() {
        modoEdicion(false);
      }
 private void cargarCuadros() {
       int fSel = tablaUsuarios.getSelectedRow();
        if (fSel >= 0) {
            String xid = tablaUsuarios.getValueAt(fSel, 3).toString();
            sentencia = "SELECT idvoluntarios,alias,usuclave FROM vistausuario WHERE idVoluntarios='"+xid+"'"; 
        }
            try {
                resultado = ejecutar.executeQuery(sentencia);
                 resultado.first();
                String xc = resultado.getString(1)+"- "+resultado.getString(1).toUpperCase();
                cmbxvol.setSelectedItem(xc);
                 //comJCombox.setComboxPosition(cmbxvol, resultado.getString(1), 1);
                txtalias.setText(resultado.getString(2));
                String codigo = resultado.getString(3);
                String codigo1 = "";
                int x1;
                for (int i = 0; i < codigo.length(); i++) {
                    char xC = codigo.charAt(i);
                    x1 = ((int) xC - 128);
                    codigo1 = codigo1 + (char) x1;
                }
                txtclave.setText(codigo1);
                chkActivo.setSelected(true);
            } catch (SQLException ex) {
              Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            mostrarUsuario();
    }

    private void accioneleminar() {
         int fSel = tablaUsuarios.getSelectedRow();
        if (fSel >= 0) {
            String xid = tablaUsuarios.getValueAt(fSel, 0).toString();
            String xal = tablaUsuarios.getValueAt(fSel, 1).toString();
            int resp = JOptionPane.showConfirmDialog(this,
                    "Confirma que desea eliminar el Usuario " + xal.toUpperCase()
                    + " del Sistema?", "Confirme...", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                sentencia = "DELETE FROM Usuario WHERE idVoluntarios='"+xid+"'";
                try {
                    ejecutar.executeUpdate(sentencia);
                    mostrarUsuario();
                } catch (SQLException ex) {
                    Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }

        }

    private void accionModificar() {
          if (tablaUsuarios.getSelectedRow() >= 0) {
            operacion = 'M';
            modoEdicion(true);
            txtalias.grabFocus();
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado registro", "Seleccione...",
                    JOptionPane.ERROR_MESSAGE);
        }

     }
}