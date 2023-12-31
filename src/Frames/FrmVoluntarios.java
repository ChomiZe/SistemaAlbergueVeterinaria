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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class FrmVoluntarios extends javax.swing.JDialog {

    Garra conex;
    Statement ejecutar;
    ResultSet resultado;
    String sentencia;
    char operacion;
    DefaultTableModel tabla = new DefaultTableModel();

    /**
     * Creates new form FrmVoluntarios
     */
    public FrmVoluntarios(java.awt.Frame parent, boolean modal) {
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtDocumento = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtTel = new javax.swing.JTextField();
        txtDomicilio = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVoluntarios = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtFechaNac = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 0));
        jLabel1.setText("REGISTRO DE VOLUNTARIOS");

        jLabel2.setText("Id Voluntarios:");

        jLabel3.setText("Nombre:");

        jLabel5.setText("Apellido:");

        jLabel7.setText("Nro documento:");

        jLabel8.setText("Nro Tel/Cel:");

        jLabel9.setText("Domicilio:");

        txtID.setEditable(false);
        txtID.setEnabled(false);

        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtApellidoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });

        txtDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentoActionPerformed(evt);
            }
        });
        txtDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDocumentoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDocumentoKeyTyped(evt);
            }
        });

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelKeyTyped(evt);
            }
        });

        txtDomicilio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDomicilioActionPerformed(evt);
            }
        });
        txtDomicilio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDomicilioKeyTyped(evt);
            }
        });

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

        tablaVoluntarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Apellido", "Nro Doc", "Fecha Nac", "Telefono", "Domicilio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaVoluntarios.getTableHeader().setReorderingAllowed(false);
        tablaVoluntarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaVoluntariosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tablaVoluntariosMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tablaVoluntarios);
        if (tablaVoluntarios.getColumnModel().getColumnCount() > 0) {
            tablaVoluntarios.getColumnModel().getColumn(0).setMinWidth(50);
            tablaVoluntarios.getColumnModel().getColumn(0).setMaxWidth(50);
            tablaVoluntarios.getColumnModel().getColumn(1).setMinWidth(80);
            tablaVoluntarios.getColumnModel().getColumn(1).setMaxWidth(80);
            tablaVoluntarios.getColumnModel().getColumn(2).setMinWidth(80);
            tablaVoluntarios.getColumnModel().getColumn(2).setMaxWidth(80);
            tablaVoluntarios.getColumnModel().getColumn(3).setMinWidth(80);
            tablaVoluntarios.getColumnModel().getColumn(3).setMaxWidth(80);
            tablaVoluntarios.getColumnModel().getColumn(4).setMinWidth(80);
            tablaVoluntarios.getColumnModel().getColumn(4).setMaxWidth(80);
            tablaVoluntarios.getColumnModel().getColumn(5).setMinWidth(80);
            tablaVoluntarios.getColumnModel().getColumn(5).setMaxWidth(80);
        }

        jLabel4.setText("Fecha Nac:");

        txtFechaNac.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));

        jLabel11.setIcon(new javax.swing.ImageIcon("J:\\Garra\\garrita.png")); // NOI18N
        jLabel11.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
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
                        .addComponent(btnCerrar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtApellido, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                                .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING))
                                            .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtFechaNac, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                                .addComponent(txtTel, javax.swing.GroupLayout.Alignment.LEADING))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel2)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel11))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(txtDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnCerrar))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void tablaVoluntariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVoluntariosMouseClicked
        cargarCuadros();
    }//GEN-LAST:event_tablaVoluntariosMouseClicked

    private void tablaVoluntariosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVoluntariosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaVoluntariosMouseEntered

    private void txtDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocumentoActionPerformed

    private void txtDomicilioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDomicilioActionPerformed
        btnGuardar.doClick();        // TODO add your handling code here:
    }//GEN-LAST:event_txtDomicilioActionPerformed

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            txtApellido.grabFocus();
        } // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtApellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            txtDocumento.grabFocus();
        } // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoKeyPressed

    private void txtDocumentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocumentoKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            txtTel.grabFocus();
        }// TODO add your handling code here:
    }//GEN-LAST:event_txtDocumentoKeyPressed

    private void txtTelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            txtDomicilio.grabFocus();
        }  // TODO add your handling code here:
    }//GEN-LAST:event_txtTelKeyPressed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char car = evt.getKeyChar();
        if ((car < 'A' || car > 'z')) {
            evt.consume();
        }// TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        char car = evt.getKeyChar();
        if ((car < 'A' || car > 'z')) {
            evt.consume();
        } // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtDomicilioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDomicilioKeyTyped
        char car = evt.getKeyChar();
        if ((car < 'A' || car > 'z')) {
            evt.consume();
        }// TODO add your handling code here:
    }//GEN-LAST:event_txtDomicilioKeyTyped

    private void txtDocumentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocumentoKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }// TODO add your handling code here:
    }//GEN-LAST:event_txtDocumentoKeyTyped

    private void txtTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        } // TODO add your handling code here:
    }//GEN-LAST:event_txtTelKeyTyped

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
            java.util.logging.Logger.getLogger(FrmVoluntarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmVoluntarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmVoluntarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmVoluntarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmVoluntarios dialog = new FrmVoluntarios(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaVoluntarios;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JFormattedTextField txtFechaNac;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTel;
    // End of variables declaration//GEN-END:variables

    private void inicio() {
        try {
            ejecutar = conex.getConexion().createStatement();
            mostrarVol();
            modoEdicion(false);
        } catch (SQLException ex) {
            Logger.getLogger(FrmVoluntarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modoEdicion(boolean bl) {
        txtID.setEnabled(bl);
        txtNombre.setEnabled(bl);
        txtApellido.setEnabled(bl);
        txtDocumento.setEnabled(bl);
        txtDomicilio.setEnabled(bl);
        txtTel.setEnabled(bl);
        txtFechaNac.setEnabled(bl);

        btnAgregar.setEnabled(!bl);
        btnGuardar.setEnabled(bl);
        btnCancelar.setEnabled(bl);
        btnModificar.setEnabled(!bl);
        btnEliminar.setEnabled(!bl);
        btnCerrar.setEnabled(!bl);

        tablaVoluntarios.setEnabled(!bl);

    }

    private void accionAgregar() {
        sentencia = "SELECT MAX(idVoluntarios) FROM Voluntarios";
        try {
            resultado = ejecutar.executeQuery(sentencia);
            resultado.first();
            limpiarCuadros();
            txtID.setText(Integer.toString(resultado.getInt(1) + 1));
            modoEdicion(true);
            operacion = 'A';
            txtNombre.grabFocus();
        } catch (SQLException ex) {
            Logger.getLogger(FrmVoluntarios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void accionGuardar() {
        String v1 = txtID.getText();
        String v2 = txtNombre.getText();
        String v3 = txtApellido.getText();
        String v4 = txtDocumento.getText();
        Date FechaNac = (Date) txtFechaNac.getValue();
        SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy-MM-dd");        
        String fecha1 = sdf.format(FechaNac);
        String v5 = fecha1;
        String v6 = txtTel.getText();
        String v7 = txtDomicilio.getText();
        if (v2.isEmpty() || v3.isEmpty() || v4.isEmpty() || v5.isEmpty() || v6.isEmpty()|| v7.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe" + " ingresar datos", " Revise el casillero vacio", JOptionPane.ERROR_MESSAGE);
            txtNombre.grabFocus();
        }
        if (operacion == 'A') {
            sentencia = "INSERT INTO Voluntarios VALUES('" + v1 + "','" + v2 + "','" + v3 + "','" + v4 + "','" + v5 + "','" + v6 + "','"+v7+"')";
            System.out.println(sentencia);
        }
        if (operacion == 'M') {
            sentencia = "UPDATE Voluntarios SET Nombre='" + v2 + "',Apellido='" + v3 + "',nroDoc='" + v4 + "',FechaNac = '"+v5+"',NroTelCel='" + v6 + "',Domicilio='" + v7 + "' WHERE idVoluntarios='" + v1 + "'";
        }
        try {
            ejecutar.executeUpdate(sentencia);

        } catch (SQLException ex) {
             Logger.getLogger(FrmVoluntarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        limpiarCuadros();
        mostrarVol();
        modoEdicion(false);
    }

    private void accionCancelar() {
        modoEdicion(false);
        limpiarCuadros();
    }

    private void accionModificar() {
        if (tablaVoluntarios.getSelectedRow() >= 0) {
            operacion = 'M';
            modoEdicion(true);
            txtNombre.grabFocus();
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningun registro", "Seleccione...", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminar() {
        int fsel = tablaVoluntarios.getSelectedRow();
        if (fsel >= 0) {
            String xid = tablaVoluntarios.getValueAt(fsel, 0).toString();
            String xdes = tablaVoluntarios.getValueAt(fsel, 1).toString();
            int resp = JOptionPane.showConfirmDialog(this, "Confirma que desea eliminar el Voluntario *" + xdes.toUpperCase() + "* del Sistema?", "Confirme...",
                    JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                sentencia = "DELETE FROM Voluntarios WHERE idVoluntarios='" + xid + "'";
                try {
                    ejecutar.executeUpdate(sentencia);
                } catch (SQLException ex) {
                    Logger.getLogger(FrmVoluntarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            mostrarVol();
            limpiarCuadros();

        }
    }

    private void cargarCuadros() {
        int fsel = tablaVoluntarios.getSelectedRow();
        if (fsel >= 0) {
            String xid = tablaVoluntarios.getValueAt(fsel, 0).toString();
            sentencia = "SELECT idVoluntarios,Nombre,Apellido,nroDoc,DATE_FORMAT(FechaNac,'%d/%m/%Y'),NroTelCel,Domicilio FROM Voluntarios WHERE idVoluntarios = '" + xid + "'";
            try {
                resultado = ejecutar.executeQuery(sentencia);
                resultado.first();
                txtID.setText(resultado.getString(1));
                txtNombre.setText(resultado.getString(2));
                txtApellido.setText(resultado.getString(3));
                txtDocumento.setText(resultado.getString(4));
                txtFechaNac.setText(resultado.getString(5));
                txtTel.setText(resultado.getString(6));
                txtDomicilio.setText(resultado.getString(7));

            } catch (SQLException ex) {
                Logger.getLogger(FrmVoluntarios.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void limpiarCuadros() {
        txtNombre.setText(null);
        txtApellido.setText(null);
        txtDocumento.setText(null);
        txtTel.setText(null);
        txtID.setText(null);
        txtDomicilio.setText(null);
        txtFechaNac.setText(null);

    }

    private void mostrarVol() {
        tabla.setRowCount(0);
        tabla = (DefaultTableModel) tablaVoluntarios.getModel();
        sentencia = "SELECT * FROM Voluntarios";
        try {
            resultado = ejecutar.executeQuery(sentencia);
            while (resultado.next()) {
                Object fila[] = new Object[7];
                fila[0] = resultado.getInt(1);
                fila[1] = resultado.getString(2);
                fila[2] = resultado.getString(3);
                fila[3] = resultado.getInt(4);
                fila[4] = resultado.getInt(5);
                fila[5] = resultado.getString(6);
                fila[6] = resultado.getString(7);

                tabla.addRow(fila);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrmVoluntarios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
