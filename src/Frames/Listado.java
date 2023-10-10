/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

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
/**
 *
 * @author jorge
 */
public class Listado extends DialogPadre {

    DefaultTableModel plantilla;
    String table = "";
    String campoID = "";
    String campoValue = "";
    String listacampos= "";
    String[] tablahead = null;
    String[] param_radiobutton = null;
    int opcion;
    Frame formPadre;
    public DialogPadre ventana;
    Date hoydate;
    Date primerdiames;
    String primerdiamesstring;
    String hoystring;
    boolean miparametro;
    private String fecha1;
    private String fecha2;
    
    /**
     * Creates new form Listado
     */
    public Listado(java.awt.Frame parent, boolean modal, DialogPadre ventana, String title, String table, String campoID, String campoValue, int opcion, String campos, String[] tablahead, boolean miparametro, String[] param_radiobutton) {
        super(parent, modal);
        initComponents();
        AddTitleExtendSuf(title);
        this.setLocationRelativeTo(null);
        txtTitulo.setText(title);
        escape(btnSalir);
        if (miparametro){
            this.btnModificar.setText("Ver");
            this.btnEliminar.setText("Anular");
        }
        this.table = table;
        this.campoID = campoID;
        this.campoValue = campoValue; 
        this.opcion = opcion;
        this.listacampos= campos;
        this.tablahead=tablahead;
        this.param_radiobutton=param_radiobutton;
        this.miparametro=miparametro;
        hoystring = comJVarios.damefecha()[1];
        primerdiamesstring = comJVarios.damefecha()[4];
        ((JTextFieldDateEditor) this.dateFechaDesde.getDateEditor()).setEditable(false);
        ((JTextFieldDateEditor) this.dateFechaHasta.getDateEditor()).setEditable(false);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            hoydate = dateFormat.parse(hoystring);

        } catch (ParseException ex) {
            Logger.getLogger(Listado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            primerdiames = dateFormat.parse(primerdiamesstring);

        } catch (ParseException ex) {
            Logger.getLogger(Listado.class.getName()).log(Level.SEVERE, null, ex);
        }
        //this.dateFechaDesde.setMaxSelectableDate(hoydate);
        //this.dateFechaHasta.setMaxSelectableDate(hoydate);
        this.dateFechaDesde.setDate(primerdiames);
        this.dateFechaHasta.setDate(hoydate);
        try {
            ResultSet fecha = con.dameLista("select date_format(current_date,'%Y-%m-01')");
            if (fecha.next()) { this.dateFechaDesde.setDate(fecha.getDate(1)); }
        } catch (SQLException e) { JOptionPane.showMessageDialog(null, e); }

        RecuperaDatos();
    }

    public void restablecer(boolean valor) {
        super.restablecer();
        comJPanel.setEnabledException(PanelBotones, valor, new Object[]{btnAgregar,btnModificar,btnEliminar});
        comJPanel.setAllNullComponents(PanelBotones);
        //PanelBotones.setVisible(valor);
    }
    
    private void RecuperaDatos() {
        String criterio = "";
        if (jRadioButton1.isSelected()){
            criterio = param_radiobutton[0];
        }
        if (jRadioButton2.isSelected()){
            criterio = param_radiobutton[1];
        }
        if (dateFechaDesde.getDate() != null) {
            fecha1 = new SimpleDateFormat("yyyy-MM-dd").format(this.dateFechaDesde.getDate());
        }
        if (dateFechaHasta.getDate() != null) {
            fecha2 = new SimpleDateFormat("yyyy-MM-dd").format(this.dateFechaHasta.getDate());
        }
        String where = "where Fecha between '"+fecha1+"' AND '"+fecha2+"' AND "+criterio+" like '%"+this.txtBuscar.getText()+ "%' group by "+campoID;
        String sql = "select "+listacampos+" from "+table+"_v "+where;
        //comJTable.cargarGrilla(tblDatos, new String [] {"Código","Fecha","RUC/CI","Razón Social","Estado","Usuario"},sql);
        comJTable.cargarGrilla(tblDatos, tablahead,sql);
        if (tblDatos.getRowCount() == 1) { txtAviso.setText(tblDatos.getRowCount()+" registro encontrado"); }
        else { txtAviso.setText(tblDatos.getRowCount()+" registros encontrados"); } 
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
        btnModificar = new javax.swing.JButton();
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
                .addComponent(btnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelBotonesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAgregar, btnEliminar, btnModificar, jButton1});

        PanelBotonesLayout.setVerticalGroup(
            PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnModificar)
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
                "Title 1", "Title 2", "Title 3", "Title 4"
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
            .addComponent(jScrollPane1)
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

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if (tblDatos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione primero un registro", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            String campo1 = tblDatos.getValueAt(tblDatos.getSelectedRow(), 0).toString();
            String campo2 = tblDatos.getValueAt(tblDatos.getSelectedRow(), 1).toString();
            String campo3 = tblDatos.getValueAt(tblDatos.getSelectedRow(), 2).toString();
            String campo4 = tblDatos.getValueAt(tblDatos.getSelectedRow(), 2).toString();
            if (miparametro){
                ventana.ver(new String[] {campo1,campo2,campo3,campo4});

            }else{
                ventana.modificar(new String[] {campo1,campo2,campo3,campo4});
            }
            ventana.setVisible(true);
            ventana.restablecer();
            RecuperaDatos();
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        modoedicion = "3";
        if (tblDatos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione primero un registro", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            String operacion = "";
            if (miparametro){
                operacion = "anular";
            }else{
                operacion = "borrar";
            }
            int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea "+operacion+"?", "Confirme", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                String num = tblDatos.getValueAt(tblDatos.getSelectedRow(), 0).toString();
                String fact = tblDatos.getValueAt(tblDatos.getSelectedRow(), 1).toString();
                String ruc = tblDatos.getValueAt(tblDatos.getSelectedRow(), 2).toString();
                if (miparametro){
                    ventana.anular(new String[] {num,fact,ruc});
                    
                }else{
                    ventana.eliminar(new String[] {num,fact,ruc});
                }
                //con.borrarRegistro(table, campoValue+" = "+tblDatos.getValueAt(tblDatos.getSelectedRow(), 0));
                /*if (JOptionPane.showConfirmDialog(this, "¿Seguro que desea borrar?", "Confirme", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    if (!guardarToDataBase("abm_pedidos", new String[] {"","","",tblDatos.getValueAt(tblDatos.getSelectedRow(), 0).toString()}, modoedicion)) {
                        return;
                    }
                }*/
                RecuperaDatos();
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        ventana.nuevo();
        ventana.setVisible(true);
        RecuperaDatos();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtBuscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtBuscarCaretUpdate
        RecuperaDatos();
    }//GEN-LAST:event_txtBuscarCaretUpdate

    private void tblDatosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatosKeyPressed
       if (evt.getKeyCode() == 10) {
            btnModificarActionPerformed(null);
        }
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            btnEliminarActionPerformed(null);
        }
    }//GEN-LAST:event_tblDatosKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (tblDatos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione primero un registro", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            String num = tblDatos.getValueAt(tblDatos.getSelectedRow(), 0).toString();
            String fact = tblDatos.getValueAt(tblDatos.getSelectedRow(), 1).toString();
            String ruc = tblDatos.getValueAt(tblDatos.getSelectedRow(), 2).toString();
            if (miparametro){
                ventana.ver(new String[] {num,fact,ruc});

            }else{
                ventana.modificar(new String[] {num,fact,ruc});
            }
            ventana.imprimir();
            ventana.restablecer();
        }
        RecuperaDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        if (evt.getClickCount() == 2) {
            btnModificarActionPerformed(null);
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
            java.util.logging.Logger.getLogger(Listado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Listado dialog = new Listado(new javax.swing.JFrame(), true, new DialogPadre(new Frame(), true), null, null, null, null, 0,null, null, false, null);
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
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
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
