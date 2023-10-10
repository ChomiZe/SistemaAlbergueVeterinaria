package cobros;

import Frames.DialogPadre;

import com.toedter.calendar.JTextFieldDateEditor;
import comunes.comJCombox;
import comunes.comJPanel;
import comunes.comJSQL;
import comunes.comJVarios;
import comunes.reportes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Donaciones extends DialogPadre {

    String tituloGral = "Donaciones";
    String tabla = "donaciones";
    String campoID = "idDonaciones";
    String campoValue = "DescripDonaciones";
    String NroFactura = null;
    String idProveedor = null;
    String hoyformatopy;
    String hoy;
    String modoediciondetalle = "1";
    boolean encabezado_g = false;
    boolean confirmado = false;
    Date hoydate = null;

    JTextField txtPorcentajeiva = new JTextField();
    Float cantidad;

    reportes jasper = new reportes();
    private String NroTimbrado;

    public Donaciones(java.awt.Frame parent, boolean modal, String modoed) {
        super(parent, modal);
        AddTitleExtendSuf("Donaciones");
        initComponents();
        this.setLocationRelativeTo(null);
        modoedicion = modoed;
        comJPanel.setAllNullComponents(PanelEncabezado);
        restablecer();
        con.autocommit(false);
        //VerificarPermisos();
    }

    @Override
    public void restablecer() {
        hoyformatopy = comJVarios.damefecha()[1];
        hoy = comJVarios.damefecha()[0];

        con.cargarCombo("select * from monedas order by idMonedas", comboMonedas);

    }

    @Override
    public void nuevo() {
        String sql = "SELECT * FROM aperturacaja WHERE aperturacaja.idaperturacaja NOT IN (SELECT idaperturacaja FROM cierrecaja) ";
        try {
            ResultSet rs = con.dameLista(sql);
            if (rs.next()) {
                txtCodApertura.setText(rs.getString(1));
            } else {
                JOptionPane.showMessageDialog(this, "No hay ninguna apertura de caja");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Donaciones.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sentencia = "SELECT MAX(idDonaciones) FROM Donaciones ";
        try {
            ResultSet rs = con.dameLista(sentencia);
            rs.first();
            txtCodigo.setText(Integer.toString(rs.getInt(1) + 1));
            txtDescripcion.grabFocus();
        } catch (SQLException ex) {
            Logger.getLogger(Donaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.comboMonedas.setEnabled(true);

        this.txtDescripcion.setText("");
        this.comboMonedas.setSelectedIndex(0);

        estadoedicionencab(true);
        BotonConfirmar.setEnabled(true);
        modoedicion = "1";
        encabezado_g = false;
        //this.dateFecha.setDate(hoy);
        //this.txtFecha.setText("" + hoyformatopy);
        super.nuevo();
    }

    @Override
    public String[] ver(String[] valores) {

        
        //this.BotonConfirmar.setEnabled(false);
        this.BotonCancelar.setEnabled(false);
        this.BotonNuevo.setEnabled(true);
        this.BotonEliminar.setEnabled(true);

        this.comboMonedas.setEnabled(false);
       
        BotonConfirmar.setEnabled(false);
        NroFactura = "'" + valores[0] + "'";
        idProveedor = "'" + valores[1] + "'";
        try {
            modoedicion = "2";
            modoediciondetalle = "1";
            Object[] datos = {txtCodigo, txtDescripcion, txtMonto, comboMonedas, txtCodApertura};
            comJVarios.recuperaDatos("idDonaciones, DescripDonaciones, CantidadMonto, idMonedas, idAperturaCaja", "donaciones_v", "idDonaciones = '" + valores[0] + "'", datos);

            comJPanel.setEnabled(PanelEncabezado, false);
        } catch (Exception ex) {
            Logger.getLogger(DialogPadre.class.getName()).log(Level.SEVERE, null, ex);
        }
        // 
        return super.ver(valores); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] modificar(String[] valores) {
        NroFactura = "'" + valores[0] + "'";
        idProveedor = "'" + valores[1] + "'";

        try {
            modoedicion = "2";
            modoediciondetalle = "1";
            encabezado_g = true;
            estadoedicionencab(true);
            Object[] datos = {txtCodigo, txtDescripcion, txtMonto, comboMonedas, txtCodApertura};
            comJVarios.recuperaDatos("idDonaciones, DescripDonaciones,CantidadMonto, idMonedas,idAperturaCaja", "donaciones_v", "idDonaciones", "'"+ valores[0]+"'",datos);
            comJPanel.setEnabled(PanelEncabezado, true);
            comJPanel.setEnabled(PanelBotones, true);

        } catch (Exception ex) {
            Logger.getLogger(DialogPadre.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.modificar(valores); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        jasper.runreporte(new String[]{"numero"}, new String[]{txtCodigo.getText()}, "Donaciones", true);
        //jasper.runReporteExcel(new String[] {"numero","ruc"}, new String[] {txtNroFactura.getText(),txtRUC.getText()}, "FacturaCompra");
    }

    @Override
    public void eliminar(String[] valores) {
        super.eliminar(valores);
        modoediciondetalle = "3";
        txtCodigo.setText(valores[0]);
        modoediciondetalle = "1";
        if (con.borrarRegistro("donaciones", "idDonaciones = '" + valores[0] + "'")) {
            JOptionPane.showMessageDialog(this, "El registro ha sido eliminado");
            con.commit();
            this.dispose();
        }
    }

    public void estadoedicionencab(boolean valor) {
        comJPanel.setEnabled(PanelEncabezado, valor);
        this.comboMonedas.setEnabled(valor);

        this.BotonCancelar.setEnabled(valor);
        this.BotonConfirmar.setEnabled(valor);
        this.BotonNuevo.setEnabled(!valor);
        this.BotonEliminar.setEnabled(!valor);

    }

    public void estadocommit(boolean valor) {
        //estadoedicionencab(!valor);
        //estadoediciondetalle(!valor);
        // comJPanel.setEnabledException(PanelBotones1, valor, new Object[]{btnNuevo});
    }

    private boolean InsertarEncabezado() {
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un número");
            return false;
        }
        if (txtDescripcion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ingrese una descripcion");
            return false;
        }
        if (modoedicion.equals("1")) {
            if (comJSQL.existeRegistro("select * from donaciones where idDonaciones = '" + txtCodigo.getText() + "'")) {
                JOptionPane.showMessageDialog(this, "Error. El registro ya existe");
                return false;
            }
        }

        String[] ve_Ordenado = new String[]{txtCodigo.getText(), txtDescripcion.getText(), txtMonto.getText(), comJCombox.getComboxString(comboMonedas, 1), txtCodApertura.getText(),hoy};
        if (guardarToDataBase("abm_donaciones", ve_Ordenado, modoedicion)) {
            encabezado_g = true;
            estadoedicionencab(false);
            con.commit();
            //btnAgregarDetalleActionPerformed(null);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Ocurrió un error en la base de datos");
            return false;
        }
    }

    private static boolean validaNumero(String valor) {
        try {
            Double.parseDouble(valor);
            return true;
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Ingrese numeros", "Campo numerico",JOptionPane.ERROR_MESSAGE);

            return false;
        }
    }//fin validaNumero

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        PanelEncabezado = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboMonedas = new javax.swing.JComboBox();
        txtDescripcion = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtCodApertura = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        PanelBotones = new javax.swing.JPanel();
        BotonConfirmar = new javax.swing.JButton();
        BotonCancelar = new javax.swing.JButton();
        BotonNuevo = new javax.swing.JButton();
        BotonEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Donaciones");

        PanelEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setText("Código:");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setText("Moneda:");

        comboMonedas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboMonedas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMonedasActionPerformed(evt);
            }
        });
        comboMonedas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comboMonedasKeyPressed(evt);
            }
        });

        jLabel14.setText("Monto:");

        jLabel16.setText("Cod. Apertura:");

        txtCodApertura.setEditable(false);
        txtCodApertura.setEnabled(false);

        jLabel6.setText("Descripción");

        javax.swing.GroupLayout PanelEncabezadoLayout = new javax.swing.GroupLayout(PanelEncabezado);
        PanelEncabezado.setLayout(PanelEncabezadoLayout);
        PanelEncabezadoLayout.setHorizontalGroup(
            PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(txtMonto))
                .addGap(10, 10, 10)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(comboMonedas, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodApertura, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        PanelEncabezadoLayout.setVerticalGroup(
            PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(comboMonedas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(txtCodApertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        BotonConfirmar.setText("Guardar");
        BotonConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonConfirmarActionPerformed(evt);
            }
        });

        BotonCancelar.setText("Cancelar");
        BotonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCancelarActionPerformed(evt);
            }
        });

        BotonNuevo.setText("Nuevo");
        BotonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonNuevoActionPerformed(evt);
            }
        });

        BotonEliminar.setText("Eliminar");
        BotonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelBotonesLayout = new javax.swing.GroupLayout(PanelBotones);
        PanelBotones.setLayout(PanelBotonesLayout);
        PanelBotonesLayout.setHorizontalGroup(
            PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BotonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotonConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(234, 234, 234))
        );
        PanelBotonesLayout.setVerticalGroup(
            PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBotonesLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonConfirmar)
                    .addComponent(BotonCancelar)
                    .addComponent(BotonNuevo)
                    .addComponent(BotonEliminar)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelEncabezado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelBotones, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(PanelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PanelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonConfirmarActionPerformed
        InsertarEncabezado();
    }//GEN-LAST:event_BotonConfirmarActionPerformed

    private void BotonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCancelarActionPerformed

        dispose();
    }//GEN-LAST:event_BotonCancelarActionPerformed

    private void BotonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonNuevoActionPerformed
        nuevo();
    }//GEN-LAST:event_BotonNuevoActionPerformed

    private void BotonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEliminarActionPerformed
        this.NroFactura = this.txtCodigo.getText();

        eliminar(new String[]{NroFactura});
    }//GEN-LAST:event_BotonEliminarActionPerformed

    private void comboMonedasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMonedasActionPerformed

    }//GEN-LAST:event_comboMonedasActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        BotonCancelarActionPerformed(null);
    }//GEN-LAST:event_formWindowClosing

    private void comboMonedasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboMonedasKeyPressed
  
    }//GEN-LAST:event_comboMonedasKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FacturasCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Donaciones dialog = new Donaciones(new javax.swing.JFrame(), true, "");
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
    private javax.swing.JButton BotonCancelar;
    private javax.swing.JButton BotonConfirmar;
    private javax.swing.JButton BotonEliminar;
    private javax.swing.JButton BotonNuevo;
    private javax.swing.JPanel PanelBotones;
    private javax.swing.JPanel PanelEncabezado;
    private javax.swing.JComboBox comboMonedas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField txtCodApertura;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}
