/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compras;

import Frames.DialogPadre;
import com.toedter.calendar.JTextFieldDateEditor;
import comunes.comJPanel;
import comunes.comJTable;
import comunes.comJVarios;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class ListadoCompras extends DialogPadre {

    DefaultTableModel plantilla;

    String campoID = "";
    String campoValue = "";
    String listacampos = "";
    String[] tablahead = null;
    String[] param_radiobutton = null;
    int opcion;
    Frame formPadre;
    FacturasCompra compras = new FacturasCompra(null, true, "1");
    Date hoydate;
    Date primerdiames;
    String primerdiamesstring;
    String hoystring;
    boolean miparametro;
    private String fecha1;
    private String fecha2;
    boolean consultar = false, agregar = false, modificar = false, eliminar = false;

    /**
     * Creates new form Listado
     */
    public ListadoCompras(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        AddTitleExtendSuf("Facturas de Compra");
        this.setLocationRelativeTo(null);
        txtTitulo.setText("Facturas de compra");
        escape(btnSalir);

        hoystring = comJVarios.damefecha()[1];
        primerdiamesstring = comJVarios.damefecha()[4];
        ((JTextFieldDateEditor) this.dateFechaDesde.getDateEditor()).setEditable(false);
        ((JTextFieldDateEditor) this.dateFechaHasta.getDateEditor()).setEditable(false);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            hoydate = dateFormat.parse(hoystring);

        } catch (ParseException ex) {
            Logger.getLogger(ListadoCompras.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            primerdiames = dateFormat.parse(primerdiamesstring);

        } catch (ParseException ex) {
            Logger.getLogger(ListadoCompras.class.getName()).log(Level.SEVERE, null, ex);
        }
        //this.dateFechaDesde.setMaxSelectableDate(hoydate);
        //this.dateFechaHasta.setMaxSelectableDate(hoydate);
        this.dateFechaDesde.setDate(primerdiames);
        this.dateFechaHasta.setDate(hoydate);
        try {
            ResultSet fecha = con.dameLista("select date_format(current_date,'%Y-%m-01')");
            if (fecha.next()) {
                this.dateFechaDesde.setDate(fecha.getDate(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        //VerificarPermisos();
        //debe tener el permiso para consultar pero ahora está de manera directa
        consultar = true;
        RecuperaDatos();
    }

    public void restablecer(boolean valor) {
        super.restablecer();
        comJPanel.setEnabledException(PanelBotones, valor, new Object[]{btnAgregar, btnVer, btnEliminar});
        comJPanel.setAllNullComponents(PanelBotones);
        //PanelBotones.setVisible(valor);
    }

    private void VerificarPermisos() {
        ResultSet rs = con.dameLista("select idOperacion from permisos_roles where idRol = " + con.getRol());
//        boolean consultar = false, agregar = false, modificar = false, eliminar = false;
        try {
            while (rs.next()) {
                //Consultar
                if (rs.getInt(1) == 2 && !consultar) {
                    consultar = true;
                }
                //Ingresar
                if (rs.getInt(1) == 3 && !agregar) {
                    agregar = true;
                }
                //Modificar
                if (rs.getInt(1) == 4 && !modificar) {
                    modificar = true;
                }
                //Eliminar
                if (rs.getInt(1) == 5 && !eliminar) {
                    eliminar = true;
                }
            }
            btnAgregar.setVisible(agregar);
            btnVer.setVisible(modificar);
            btnEliminar.setVisible(eliminar);
        } catch (SQLException ex) {
            Logger.getLogger(ListadoCompras.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void RecuperaDatos() {
        String criterio = "";
        if (jRadioButton1.isSelected()) {
            criterio = "RUC";
        }
        if (jRadioButton2.isSelected()) {
            criterio = "DescripProveedores";
        }
        if (dateFechaDesde.getDate() != null) {
            fecha1 = new SimpleDateFormat("yyyy-MM-dd").format(this.dateFechaDesde.getDate());
        }
        if (dateFechaHasta.getDate() != null) {
            fecha2 = new SimpleDateFormat("yyyy-MM-dd").format(this.dateFechaHasta.getDate());
        }
        String where = "where Fecha between '" + fecha1 + "' AND '" + fecha2 + "' AND " + criterio + " like '%" + this.txtBuscar.getText() + "%' group by NroFact,idProveedores";
        String sql = "select  NroFact,NroTimbrado,DATE_FORMAT(Fecha, '%d-%m-%Y'),idCondicionPago,idMonedas,idProveedores,RUC,DescripProveedores,Total from compras_v  " + where;
        //comJTable.cargarGrilla(tblDatos, new String [] {"Código","Fecha","RUC/CI","Razón Social","Estado","Usuario"},sql);

        if (consultar) {
            comJTable.cargarGrilla(tblDatos, new String[]{"NroFactura", "NroTimbrado", "Fecha", "idCondicion","idMoneda","idproveedor","ruc","Razón Social", "Total"}, sql);
           // comJTable.esconderColumnas(tblDatos, new int[]{1});
            comJTable.formatear(tblDatos, new int[]{0, 2, 3, 4, 5, 6}, new int[]{130, 100, 220, 100, 100, 100});
            if (tblDatos.getRowCount() == 1) {
                txtAviso.setText(tblDatos.getRowCount() + " registro encontrado");
            } else {
                txtAviso.setText(tblDatos.getRowCount() + " registros encontrados");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtTitulo = new javax.swing.JLabel();
        PanelBotones = new javax.swing.JPanel();
        btnVer = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        dateFechaDesde = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        dateFechaHasta = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        txtAviso = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtTitulo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTitulo.setText("Título");

        btnVer.setText("Ver");
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jButton1.setText("Imprimir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelBotonesLayout = new javax.swing.GroupLayout(PanelBotones);
        PanelBotones.setLayout(PanelBotonesLayout);
        PanelBotonesLayout.setHorizontalGroup(
            PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBotonesLayout.createSequentialGroup()
                .addComponent(btnAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelBotonesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAgregar, btnEliminar, btnVer, jButton1});

        PanelBotonesLayout.setVerticalGroup(
            PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnVer)
                .addComponent(btnEliminar)
                .addComponent(btnAgregar)
                .addComponent(jButton1)
                .addComponent(btnSalir))
        );

        jLabel2.setText("Buscar:");

        txtBuscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtBuscarCaretUpdate(evt);
            }
        });

        jLabel3.setText("Desde:");

        dateFechaDesde.setDateFormatString("dd-MM-yyyy");

        jLabel4.setText("Hasta:");

        dateFechaHasta.setDateFormatString("dd-MM-yyyy");

        tblDatos.setAutoCreateRowSorter(true);
        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDatos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDatos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDatos.getTableHeader().setReorderingAllowed(false);
        tblDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatosMouseClicked(evt);
            }
        });
        tblDatos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblDatos);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("RUC/CI");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Razón Social");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(dateFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2))))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
            .addComponent(txtAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRadioButton1)
                        .addComponent(jRadioButton2))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(dateFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(dateFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(txtAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelBotones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTitulo)
                .addGap(18, 18, 18)
                .addComponent(PanelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        if (tblDatos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione primero un registro", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String campo1 = tblDatos.getValueAt(tblDatos.getSelectedRow(), 0).toString();
            String campo2 = tblDatos.getValueAt(tblDatos.getSelectedRow(), 1).toString();
            String campo3 = tblDatos.getValueAt(tblDatos.getSelectedRow(), 2).toString();
            String campo4 = tblDatos.getValueAt(tblDatos.getSelectedRow(), 2).toString();

            compras.modificar(new String[]{campo1, campo2, campo3, campo4});

            compras.setVisible(true);
            compras.restablecer();
            RecuperaDatos();
        }
    }//GEN-LAST:event_btnVerActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        modoedicion = "3";
        if (tblDatos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione primero un registro", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea borrar?", "Confirme", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                String num = tblDatos.getValueAt(tblDatos.getSelectedRow(), 0).toString();
                String fact = tblDatos.getValueAt(tblDatos.getSelectedRow(), 1).toString();
                String ruc = tblDatos.getValueAt(tblDatos.getSelectedRow(), 2).toString();

                compras.eliminar(new String[]{num, fact, ruc});

                RecuperaDatos();
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed

        compras.nuevo();
        compras.setVisible(true);
        RecuperaDatos();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtBuscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtBuscarCaretUpdate
        RecuperaDatos();
    }//GEN-LAST:event_txtBuscarCaretUpdate

    private void tblDatosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatosKeyPressed
        if (evt.getKeyCode() == 10) {
            btnVerActionPerformed(null);
        }
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            btnEliminarActionPerformed(null);
        }
    }//GEN-LAST:event_tblDatosKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (tblDatos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione primero un registro", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String num = tblDatos.getValueAt(tblDatos.getSelectedRow(), 0).toString();
            String fact = tblDatos.getValueAt(tblDatos.getSelectedRow(), 1).toString();
            String ruc = tblDatos.getValueAt(tblDatos.getSelectedRow(), 2).toString();

            compras.ver(new String[]{num, fact, ruc});

            compras.imprimir();
            compras.restablecer();
        }
        RecuperaDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        if (evt.getClickCount() == 2) {
            btnVerActionPerformed(null);
        }
    }//GEN-LAST:event_tblDatosMouseClicked

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListadoCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ListadoCompras dialog = new ListadoCompras(null, true);
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
    private javax.swing.JPanel PanelBotones;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVer;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser dateFechaDesde;
    private com.toedter.calendar.JDateChooser dateFechaHasta;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDatos;
    private javax.swing.JLabel txtAviso;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JLabel txtTitulo;
    // End of variables declaration//GEN-END:variables
}
