package compras;

import Frames.DialogPadre;

import com.toedter.calendar.JTextFieldDateEditor;
import comunes.Numero_a_letra;
import comunes.comJCombox;
import comunes.comJPanel;
import comunes.comJSQL;
import comunes.comJTable;
import comunes.comJVarios;
import comunes.comLlamadaBuscadorComun;
import comunes.comLlamadaBuscadorComun2;
import comunes.reportes;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FacturasCompra extends DialogPadre {

    String tituloGral = "Facturas de Compra";
    String tabla = "facturascompra";
    String campoID = "NroFacturaCompra";
    String campoValue = "NroFacturaCompra";
    String NroFactura = null;
    String idProveedor = null;
    String hoyformatopy;
    String hoy;
    String modoediciondetalle = "1";
    boolean encabezado_g = false;
    boolean confirmado = false;
    Date hoydate = null;
    private float TotalFactura;
    private float TotalTransaccion;
    private boolean DesdeOrden;
    private boolean PedirCheque;
    private boolean PedirTarjeta;
    private boolean ChequeInsertado;
    private boolean TarjetaInsertada;
    JTextField txtPorcentajeiva = new JTextField();
    Float cantidad;

    reportes jasper = new reportes();
    private String NroTimbrado;

    /**
     * Creates new form Compras
     */
    public FacturasCompra(java.awt.Frame parent, boolean modal, String modoed) {
        super(parent, modal);
        AddTitleExtendSuf("Factura de Compra");
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

        this.btnGuardarEncabezado.setVisible(false);
        con.cargarCombo("select * from condicionpago order by idCondicionPago", comboTipoFactura);
        con.cargarCombo("select * from condicionpago order by idCondicionPago", comboFormaPago);
        con.cargarCombo("select * from monedas", comboMonedas);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            hoydate = dateFormat.parse(hoyformatopy);

        } catch (ParseException ex) {
            Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        ((JTextFieldDateEditor) this.dateFecha.getDateEditor()).setEditable(false);
        this.dateFecha.setMaxSelectableDate(hoydate);
        this.dateFecha.setDate(hoydate);
    }

    private void VerificarPermisos() {
        ResultSet rs = con.dameLista("select idOperacion from permisos_roles where idRol = " + con.getRol());
        boolean consultar = false, agregar = false, modificar = false, eliminar = false;
        try {
            while (rs.next()) {
                //Consultar
//                if (rs.getInt(1) == 2)
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
            BotonNuevo.setVisible(agregar);
            BotonConfirmar.setVisible(modificar);
            if (!modificar && BotonNuevo.isVisible()) {
                BotonConfirmar.setVisible(true);
            }
            BotonEliminar.setVisible(eliminar);
        } catch (SQLException ex) {
            Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nuevo() {
        this.txtNroFactura.grabFocus();
        this.btnGuardarEncabezado.setVisible(true);
        this.btnBuscarProveedor.setVisible(true);
        this.PanelDetalle.setVisible(false);

        this.comboTipoFactura.setEnabled(true);
        this.PanelBotonesDetalle.setVisible(true);
        this.txtNroFactura.setEditable(true);
        this.lblIva10.setText("0");
        this.lblIva5.setText("0");
        this.lblTotal.setText("0");
        this.lblTotalIva.setText("0");

        this.txtNroFactura.setText("000-000-0000000");
        txtNroFactura.setSelectionStart(0);
        txtNroFactura.setSelectionEnd(txtNroFactura.getText().length());
        txtNroFactura.grabFocus();
        this.txtRUC.setText("");
        this.txtRazonSocial.setText("");
        this.comboTipoFactura.setSelectedIndex(0);
        comJPanel.setAllNullComponents(PanelDetalle);
        estadoedicionencab(true);
        BotonConfirmar.setEnabled(true);
        modoedicion = "1";
        encabezado_g = false;
        //this.dateFecha.setDate(hoy);
        //this.txtFecha.setText("" + hoyformatopy);
        RecuperaDetalles();
        RecuperaTotales();
        super.nuevo();
    }

    @Override
    public String[] ver(String[] valores) {
        this.btnGuardarEncabezado.setVisible(false);
        this.btnBuscarProveedor.setVisible(false);
        this.PanelDetalle.setVisible(false);
        //this.BotonConfirmar.setEnabled(false);
        this.BotonCancelar.setEnabled(false);
        this.BotonNuevo.setEnabled(true);
        this.BotonEliminar.setEnabled(true);
        this.PanelBotonesDetalle.setVisible(false);
        this.txtNroFactura.setEditable(false);
        this.comboTipoFactura.setEnabled(false);
        this.dateFecha.getCalendarButton().setEnabled(false);
        BotonConfirmar.setEnabled(false);
        NroFactura = "'" + valores[0] + "'";
        idProveedor = "'" + valores[1] + "'";
        try {
            modoedicion = "2";
            modoediciondetalle = "1";
            Object[] datos = {txtNroFactura, txtCodProveedor, txtRUC, txtRazonSocial, dateFecha, comboTipoFactura};
            comJVarios.recuperaDatos("NroFact, idProveedores, RUC, DescripProveedores, Fecha, idcondicionpago", "compras_v", "NroFact = '" + valores[0] + "' AND NroTimbrado", valores[1], datos);
            RecuperaDetalles();
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
            estadoedicionencab(false);
            estadoediciondetalle(false);
            Object[] datos = {txtNroFactura, txtNroTimbrado, txtCodProveedor, txtRUC, txtRazonSocial, dateFecha, comboTipoFactura};
            comJVarios.recuperaDatos("NroFact, NroTimbrado,idProveedores, RUC, DescripProveedores, Fecha, idcondicionpago", "compras_v", "NroFact = '" + valores[0] + "' AND NroTimbrado", "'" + valores[1] + "'", datos);
            comJPanel.setEnabled(PanelEncabezado, false);
            comJPanel.setEnabled(PanelBotones, true);
            PanelDetalle.setVisible(true);
            RecuperaDetalles();
        } catch (Exception ex) {
            Logger.getLogger(DialogPadre.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.modificar(valores); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        jasper.runreporte(new String[]{"nrofactura", "nrotimbrado"}, new String[]{txtNroFactura.getText(), txtNroTimbrado.getText()}, "Compras", true);
        //jasper.runReporteExcel(new String[] {"numero","ruc"}, new String[] {txtNroFactura.getText(),txtRUC.getText()}, "FacturaCompra");
    }

    @Override
    public void eliminar(String[] valores) {
        super.eliminar(valores);
        modoediciondetalle = "3";
        txtNroFactura.setText(valores[0]);
        txtNroTimbrado.setText(valores[1]);
        if (ActualizarInv()) {
            modoediciondetalle = "1";
            if (con.borrarRegistro("facturacompra", "NroFact = '" + valores[0] + "' AND NroTimbrado = '" + valores[1] + "'")) {
                JOptionPane.showMessageDialog(this, "La factura ha sido eliminada");
                con.commit();
                this.dispose();
            }
        }
    }

    public void estadoedicionencab(boolean valor) {
        comJPanel.setEnabled(PanelEncabezado, valor);
        this.comboTipoFactura.setEnabled(valor);
        this.PanelBotonesDetalle.setVisible(true);
        this.txtNroFactura.setEditable(true);
        estadoediciondetalle(!valor);
        this.BotonCancelar.setEnabled(valor);
        this.BotonConfirmar.setEnabled(false);
        this.BotonNuevo.setEnabled(!valor);
        this.BotonEliminar.setEnabled(false);

        this.btnGuardarEncabezado.setEnabled(valor);
    }

    public void estadoediciondetalle(boolean valor) {
        //if (!this.chkOrdenCompra.isSelected()){
        //comJPanel.setEnabled(PanelDetalle, valor);
        //comJPanel.setEnabled(PanelBotonesDetalle, valor);
        //}else{
        //comJPanel.setEnabled(PanelDetalle, !valor);
        //comJPanel.setEnabled(PanelBotonesDetalle, !valor);
        //}
    }

    public void estadocommit(boolean valor) {
        //estadoedicionencab(!valor);
        //estadoediciondetalle(!valor);
        // comJPanel.setEnabledException(PanelBotones1, valor, new Object[]{btnNuevo});
    }

    private void RecuperaDetalles() {
        try {
            String sql = "select idItems,DescripItems,Cantidad,format(PrecioCostUnitario,0), format(Exentas,0), format(Gravadas5,0), format(Gravadas10,0) from comprasdet_v "
                    + "where NroFact = '" + this.txtNroFactura.getText() + "' AND NroTimbrado = '" + txtNroTimbrado.getText() + "'";
            comJTable.cargarGrilla(tblDatos, new String[]{"Código", "Ítem", "Cantidad", "Precio", "Exentas", "IVA 5%", "IVA 10%"}, sql);
        } catch (Exception ex) {
            Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        comJTable.formatear(tblDatos, new int[]{0, 1, 2, 4, 5, 6}, new int[]{60, 350, 50, 70, 30, 100});
        comJTable.orientacionColumnas(tblDatos, new int[]{4, 5, 6}, 4);
        RecuperaTotales();
    }

    private void RecuperaTotales() {
        Numero_a_letra nroaletra = new Numero_a_letra();
        NumberFormat formatter = new DecimalFormat("#,###,###");

        Object[] datos = {lblIva5, lblIva10, lblTotalIva, lblTotal};
        try {
            comJVarios.recuperaDatos("Liquidacion5, Liquidacion10, TotalIva, Total", "compratotal_v", "NroFact = '" + this.txtNroFactura.getText() + "' and NroTimbrado ", "'" + txtNroTimbrado.getText() + "'", datos);
            String iva5 = formatter.format(Float.parseFloat(lblIva5.getText()));
            String iva10 = formatter.format(Float.parseFloat(lblIva10.getText()));
            String TotalIva = formatter.format(Float.parseFloat(lblTotalIva.getText()));
            String Total = formatter.format(Float.parseFloat(lblTotal.getText()));
            Number nTotal = formatter.parse(Total);
            this.lblIva5.setText(iva5.toString());
            this.lblIva10.setText(iva10.toString());
            this.lblTotalIva.setText(TotalIva.toString());
            this.lblTotal.setText(Total.toString());
            Double TotalDouble = nTotal.doubleValue();
            //this.txtLetras.setText("Son Gs.: "+nroaletra.Convertir(TotalDouble.toString(), true));
            this.txtLetras.setText(nroaletra.Convertir(nTotal + "", true));
        } catch (Exception ex) {
            Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



  private boolean InsertarEncabezado() {
        if (txtNroFactura.getValue() == null) {
            JOptionPane.showMessageDialog(this, "Ingrese un número correcto de factura");
            return false;
        }
        if (txtCodProveedor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
            return false;
        }
        if (modoedicion.equals("1")) {
            if (comJSQL.existeRegistro("select * from facturacompra where NroFact = '" + txtNroFactura.getText() + "' and NroTimbrado = '" + txtNroTimbrado.getText() + "'")) {
                JOptionPane.showMessageDialog(this, "Error. El registro ya existe");
                return false;
            }
        }

        String[] ve_Ordenado = new String[]{txtNroFactura.getText(), txtNroTimbrado.getText(), hoy, comJCombox.getComboxString(comboTipoFactura, 1), comJCombox.getComboxString(comboMonedas, 1), txtCodProveedor.getText()};
        if (guardarToDataBase("abm_facturacompra", ve_Ordenado, modoedicion)) {
            encabezado_g = true;
            estadoedicionencab(false);
            BotonConfirmar.setEnabled(true);
            btnAgregarDetalleActionPerformed(null);
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

    public boolean ActualizarPrecios() {
        ResultSet rs;
        ResultSet rs2;
        boolean valor = false;
        rs = con.dameLista("select idItem from factcompradet where NroFactura = '" + txtNroFactura.getText() + "' "
                + "and idProveedor = " + txtCodProveedor.getText());
        try {
            while (rs.next()) {
                rs2 = con.dameLista("select (ar.Preciocosto+fa.Precio)/2 as U "
                        + "from items ar join factcompradet fa on ar.iditems = fa.iditems "
                        + "where ar.idItems = " + rs.getString(1) + " limit 1");
                if (rs2.next()) {
                    if (con.actualizarRegistro("items", "PrecioCosto = " + rs2.getString(1), "iditems= " + rs.getString(1))) {
                        valor = true;
                    }
                }
            }
        } catch (SQLException ex) {
            valor = false;
        }
        return valor;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPrecios = new javax.swing.JDialog();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        cbPrecios1 = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtPCompra = new javax.swing.JTextField();
        txtPVenta = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtMargen = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        PanelEncabezado = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboTipoFactura = new javax.swing.JComboBox();
        txtRUC = new javax.swing.JTextField();
        txtRazonSocial = new javax.swing.JTextField();
        btnBuscarProveedor = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtNroFactura = new javax.swing.JFormattedTextField();
        dateFecha = new com.toedter.calendar.JDateChooser();
        txtCodProveedor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        comboFormaPago = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        txtNroTimbrado = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        comboMonedas = new javax.swing.JComboBox();
        btnGuardarEncabezado = new javax.swing.JButton();
        PanelDetalle = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtCodItem = new javax.swing.JTextField();
        txtNombreItem = new javax.swing.JTextField();
        btnBuscarItem = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPorcIva = new javax.swing.JTextField();
        txtPrecioCosto = new javax.swing.JFormattedTextField();
        PanelBotonesDetalle = new javax.swing.JPanel();
        btnAgregarDetalle = new javax.swing.JButton();
        btnQuitarDetalle = new javax.swing.JButton();
        btnModificarDetalle = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        lblIva5 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lblTotalIva = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblIva10 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        PanelBotones = new javax.swing.JPanel();
        BotonConfirmar = new javax.swing.JButton();
        BotonCancelar = new javax.swing.JButton();
        BotonNuevo = new javax.swing.JButton();
        BotonEliminar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        txtLetras = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        cbPrecios1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbPrecios1, 0, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbPrecios1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Seleccionar", jPanel4);

        jLabel33.setText("Compra:");

        jLabel34.setText("Venta:");

        txtPCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPCompraKeyTyped(evt);
            }
        });

        txtPVenta.setEditable(false);
        txtPVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPVentaKeyTyped(evt);
            }
        });

        jLabel35.setText("Margen:");

        txtMargen.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtMargenCaretUpdate(evt);
            }
        });
        txtMargen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMargenKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPCompra)
                            .addComponent(txtPVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMargen)))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtPCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtPVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtMargen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Agregar", jPanel5);

        jLabel36.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Precios");

        jButton6.setText("Aceptar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Cancelar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPreciosLayout = new javax.swing.GroupLayout(jPrecios.getContentPane());
        jPrecios.getContentPane().setLayout(jPreciosLayout);
        jPreciosLayout.setHorizontalGroup(
            jPreciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPreciosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPreciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane2)
                    .addGroup(jPreciosLayout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPreciosLayout.setVerticalGroup(
            jPreciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPreciosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPreciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Facturas de Compra");

        PanelEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setText("Nro. Factura:");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel4.setText("Fecha");

        jLabel3.setText("Tipo Factura");

        comboTipoFactura.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboTipoFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoFacturaActionPerformed(evt);
            }
        });
        comboTipoFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comboTipoFacturaKeyPressed(evt);
            }
        });

        txtRUC.setEditable(false);
        txtRUC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRUCKeyPressed(evt);
            }
        });

        txtRazonSocial.setEditable(false);

        btnBuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProveedorActionPerformed(evt);
            }
        });

        jLabel5.setText("Proveedor");

        try {
            txtNroFactura.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-#######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtNroFactura.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNroFacturaCaretUpdate(evt);
            }
        });
        txtNroFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroFacturaActionPerformed(evt);
            }
        });
        txtNroFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNroFacturaKeyPressed(evt);
            }
        });

        dateFecha.setDateFormatString("dd-MM-yyyy");
        dateFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateFechaKeyPressed(evt);
            }
        });

        txtCodProveedor.setEditable(false);
        txtCodProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodProveedorKeyPressed(evt);
            }
        });

        jLabel10.setText("Forma de pago:");

        comboFormaPago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboFormaPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFormaPagoActionPerformed(evt);
            }
        });
        comboFormaPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comboFormaPagoKeyPressed(evt);
            }
        });

        jLabel11.setText("Timbrado:");

        txtNroTimbrado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNroTimbradoKeyPressed(evt);
            }
        });

        jLabel13.setText("Moneda:");

        comboMonedas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout PanelEncabezadoLayout = new javax.swing.GroupLayout(PanelEncabezado);
        PanelEncabezado.setLayout(PanelEncabezadoLayout);
        PanelEncabezadoLayout.setHorizontalGroup(
            PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(24, 24, 24)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboTipoFactura, 0, 164, Short.MAX_VALUE)
                    .addComponent(txtNroFactura))
                .addGap(18, 18, 18)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                    .addComponent(txtNroTimbrado))
                .addGap(18, 18, 18)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboMonedas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarProveedor)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        PanelEncabezadoLayout.setVerticalGroup(
            PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtRUC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(txtNroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCodProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(txtNroTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(comboTipoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addComponent(dateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(comboFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(comboMonedas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGuardarEncabezado.setText("Guardar encabezado");
        btnGuardarEncabezado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarEncabezadoActionPerformed(evt);
            }
        });

        PanelDetalle.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel7.setText("Artículo");

        txtCodItem.setEditable(false);
        txtCodItem.setNextFocusableComponent(txtPrecioCosto);
        txtCodItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodItemActionPerformed(evt);
            }
        });
        txtCodItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodItemKeyPressed(evt);
            }
        });

        txtNombreItem.setEditable(false);

        btnBuscarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarItemActionPerformed(evt);
            }
        });

        jLabel8.setText("Cantidad");

        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        jLabel6.setText("Precio");

        jLabel9.setText("IVA");

        txtPorcIva.setEditable(false);

        txtPrecioCosto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###"))));
        txtPrecioCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrecioCostoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout PanelDetalleLayout = new javax.swing.GroupLayout(PanelDetalle);
        PanelDetalle.setLayout(PanelDetalleLayout);
        PanelDetalleLayout.setHorizontalGroup(
            PanelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDetalleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtCodItem, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombreItem, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPrecioCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(PanelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPorcIva, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(PanelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDetalleLayout.createSequentialGroup()
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarItem))
                    .addComponent(jLabel8))
                .addContainerGap(396, Short.MAX_VALUE))
        );
        PanelDetalleLayout.setVerticalGroup(
            PanelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDetalleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCodItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNombreItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPrecioCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPorcIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscarItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnAgregarDetalle.setText("Agregar");
        btnAgregarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDetalleActionPerformed(evt);
            }
        });

        btnQuitarDetalle.setText("Quitar");
        btnQuitarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarDetalleActionPerformed(evt);
            }
        });

        btnModificarDetalle.setText("Modificar");
        btnModificarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarDetalleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelBotonesDetalleLayout = new javax.swing.GroupLayout(PanelBotonesDetalle);
        PanelBotonesDetalle.setLayout(PanelBotonesDetalleLayout);
        PanelBotonesDetalleLayout.setHorizontalGroup(
            PanelBotonesDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotonesDetalleLayout.createSequentialGroup()
                .addComponent(btnAgregarDetalle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQuitarDetalle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarDetalle)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        PanelBotonesDetalleLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAgregarDetalle, btnModificarDetalle, btnQuitarDetalle});

        PanelBotonesDetalleLayout.setVerticalGroup(
            PanelBotonesDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotonesDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnAgregarDetalle)
                .addComponent(btnQuitarDetalle)
                .addComponent(btnModificarDetalle))
        );

        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
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

        jLabel20.setText("5%:");

        lblIva5.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        lblIva5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIva5.setText("0");
        lblIva5.setOpaque(true);

        jLabel25.setText("Total Iva:");

        lblTotalIva.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        lblTotalIva.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalIva.setText("0");
        lblTotalIva.setOpaque(true);

        jLabel21.setText("10%:");

        lblIva10.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        lblIva10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIva10.setText("0");
        lblIva10.setOpaque(true);

        jLabel22.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel22.setText("Total:");

        lblTotal.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("0");
        lblTotal.setOpaque(true);

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

        jButton5.setText("Imprimir");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addGap(157, 157, 157))
        );
        PanelBotonesLayout.setVerticalGroup(
            PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBotonesLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonConfirmar)
                    .addComponent(BotonCancelar)
                    .addComponent(BotonNuevo)
                    .addComponent(BotonEliminar)
                    .addComponent(jButton5)))
        );

        txtLetras.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        txtLetras.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtLetras.setText(" ");

        jLabel12.setText("Liquidación del I.V.A.:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtLetras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(PanelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(PanelDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PanelBotonesDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardarEncabezado)
                        .addGap(105, 105, 105))
                    .addComponent(PanelEncabezado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(359, 410, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblIva5, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblIva10, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotalIva, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(PanelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarEncabezado)
                    .addComponent(PanelBotonesDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel20)
                    .addComponent(lblIva5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(lblIva10, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(lblTotalIva, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLetras)
                .addGap(22, 22, 22)
                .addComponent(PanelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtRUCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRUCKeyPressed
        if (evt.getKeyCode() == 10) {
            btnBuscarProveedorActionPerformed(null);
        }
    }//GEN-LAST:event_txtRUCKeyPressed

    private void btnBuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProveedorActionPerformed
        if (comLlamadaBuscadorComun2.BuscadorjDialog("select idProveedores, RUC, DescripProveedores from proveedores", new String[]{"Código", "RUC", "Razón Social"}, "idProveedores", new String[][]{{"idProveedores", "0"}}, new int[]{}, new int[]{500, 300}, new int[]{100, 120, 240}, "DescripProveedores")) {
            comLlamadaBuscadorComun2.BuscadorjDialogetStringObjAlt(new Object[]{txtCodProveedor, txtRUC, txtRazonSocial}, new int[]{1, 2, 3});
            modoediciondetalle = "1";
            comboTipoFactura.grabFocus();
        }

    }//GEN-LAST:event_btnBuscarProveedorActionPerformed

    private void txtNroFacturaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNroFacturaCaretUpdate
    }//GEN-LAST:event_txtNroFacturaCaretUpdate

    private void txtNroFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroFacturaActionPerformed
    }//GEN-LAST:event_txtNroFacturaActionPerformed

    private void txtNroFacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroFacturaKeyPressed
        if (evt.getKeyCode() == 10) {
            txtNroTimbrado.grabFocus();
        }
    }//GEN-LAST:event_txtNroFacturaKeyPressed

    private void btnGuardarEncabezadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarEncabezadoActionPerformed
        InsertarEncabezado();
    }//GEN-LAST:event_btnGuardarEncabezadoActionPerformed

    private void txtCodItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodItemActionPerformed
        btnBuscarItemActionPerformed(null);
    }//GEN-LAST:event_txtCodItemActionPerformed

    private void txtCodItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodItemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscarItemActionPerformed(null);
        }
    }//GEN-LAST:event_txtCodItemKeyPressed

    private void btnBuscarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarItemActionPerformed
//         if (comLlamadaBuscadorComun.BuscadorjDialog("select idArticulo, CodigoBarras, Nombre, idPresentacion, PresCant "
//                + "from Articulos2_v where idPresentacion is not null "
//                + "group by idArticulo, idPresentacion order by idArticulo, idPresentacion", new String[] {"Código","Cód. Barras","Nombre","Cód. Pres","Presentación"})) {
//            comLlamadaBuscadorComun.BuscadorjDialogetStringObjAlt(new Object[]{txtCodItem,txtNombreItem,txtIdPres,txtPresentacion,txtPeso, txtPorcIva, }, new int[]{1,3,12,15,19,21});
//            this.txtIdPrecio.grabFocus();
//         }
//        
//         modoediciondetalle = "1";
        String where = "";
        if (!txtCodItem.getText().isEmpty()) {
            where = "where iditems = " + txtCodItem.getText();
        }

        String sql = "select iditems,DescripItems, PrecioCosto, Existecia,PorcIva "
                + "from items_vista where iditems like '%" + txtCodItem.getText() + "%'";
        if (comLlamadaBuscadorComun2.BuscadorjDialog(sql, new String[]{"Cod", "Nombre", "Precio Compra", "Existencias"},
                comJSQL.getCamposSQL(sql), new String[][]{{"iditems", "0"}}, new int[]{}, new int[]{620, 400}, new int[]{50, 280, 80, 80}, "Nombre")) {
            comLlamadaBuscadorComun2.BuscadorjDialogetStringObjAlt(new Object[]{txtCodItem, txtNombreItem, txtPrecioCosto, txtPorcIva}, new int[]{1, 2, 3, 10});
            if (txtCantidad.getText().isEmpty()) {
                txtCantidad.setText("1");
            }
            txtPrecioCosto.setSelectionStart(0);
            txtPrecioCosto.setSelectionEnd(txtPrecioCosto.getText().length());
            txtPrecioCosto.grabFocus();
            modoediciondetalle = "1";
        }
    }//GEN-LAST:event_btnBuscarItemActionPerformed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        if (evt.getKeyChar() < '0' || evt.getKeyChar() > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void btnAgregarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDetalleActionPerformed
        if (txtRUC.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
            return;
        }
        if (comboTipoFactura.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "No se seleccionó un tipo de factura");
            return;
        }
        if (dateFecha.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha");
            return;
        }
        if (!encabezado_g) {
            InsertarEncabezado();
        } else {
            modoediciondetalle = "1";
            if (PanelDetalle.isVisible()) {
                PanelDetalle.setVisible(false);
                comJPanel.setAllNullComponents(PanelDetalle);
            } else {
                PanelDetalle.setVisible(true);
                txtCodItem.setEditable(true);
                txtCodItem.grabFocus();
            }
        }
        RecuperaDetalles();
    }//GEN-LAST:event_btnAgregarDetalleActionPerformed

    private void btnQuitarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarDetalleActionPerformed
        modoediciondetalle = "3";
        if (tblDatos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione primero un registro", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            con.borrarRegistro("factcompradet", "NroFact = '" + txtNroFactura.getText() + "' AND NroTimbrado = '" + txtNroTimbrado.getText() + "' "
                    + "AND iditems = " + tblDatos.getValueAt(tblDatos.getSelectedRow(), 0));
            ActualizarInv();
            RecuperaDetalles();
        }
    }//GEN-LAST:event_btnQuitarDetalleActionPerformed

    private void btnModificarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarDetalleActionPerformed
        if (tblDatos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione primero un registro", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Object[] datos = {txtCodItem, txtNombreItem, txtCantidad, txtPrecioCosto, txtPorcIva};
                comJVarios.recuperaDatos("iditems, DescripItems, Cantidad, PrecioCostUnitario, PorcIva", "comprasdet_v", "NroFact ",
                        "'" + this.txtNroFactura.getText() + "' AND NroTimbrado = '" + this.txtNroTimbrado.getText() + "' and iditems = " + tblDatos.getValueAt(tblDatos.getSelectedRow(), 0),
                        datos);
                cantidad = Float.parseFloat(txtCantidad.getText());
                PanelDetalle.setVisible(true);
                txtPrecioCosto.setSelectionStart(0);
                txtPrecioCosto.setSelectionEnd(txtPrecioCosto.getText().length());
                txtPrecioCosto.grabFocus();
                modoediciondetalle = "2";
            } catch (Exception ex) {
                Logger.getLogger(DialogPadre.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        RecuperaDetalles();
    }//GEN-LAST:event_btnModificarDetalleActionPerformed

    private void tblDatosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatosKeyPressed
        if (evt.getKeyCode() == 10) {
            btnModificarDetalleActionPerformed(null);
        }
    }//GEN-LAST:event_tblDatosKeyPressed

    private void BotonConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonConfirmarActionPerformed
        if (tblDatos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No se puede guardar sin ingresar detalles");
            return;
        }

        NroFactura = this.txtNroFactura.getText();
        idProveedor = txtCodProveedor.getText();

        
        if (comJCombox.getComboxString(comboTipoFactura, 1).equals("2")) {
            GenerarCuentas GenerarCuentas = new GenerarCuentas(null, true);
            GenerarCuentas.setTotal(Float.parseFloat(lblTotal.getText()));
            jTabbedPane2.setVisible(false);
            GenerarCuentas.setVisible(true);

            String CantidadCuotas = GenerarCuentas.getCantidadCuotas();
            String MontoCuota = GenerarCuentas.getMontoCuota();
            String PeriodoCuota = GenerarCuentas.getPeriodoCuota();
            System.out.println(CantidadCuotas);
            System.out.println(MontoCuota);
            System.out.println(PeriodoCuota);
            if (!GenerarCuentas.getAccion()) {
            } else {
                if (con.insertarRegistro("cuentasporpagar", "NroFacturaCompra, idProveedor, CantidadCuotas, MontoCuota, idPeriodoCuota", "'" + NroFactura + "', '" + idProveedor
                        + "', '" + CantidadCuotas + "', '" + MontoCuota + "', '" + PeriodoCuota + "'")) {
                    encabezado_g = false;
                    RecuperaDetalles();
                    estadocommit(true);
                    ver(new String[]{this.txtNroFactura.getText(), txtRUC.getText()});
                }
            }
        } else {//si no es a credito
            
            con.commit();
            dispose();
//            ResultSet rs = con.dameLista("select * from facturascompratransacciones where "
//                    + "NroFacturaCompra = '"+NroFactura+"' and idProveedor = "+idProveedor);
//            try {
//                if (!rs.next()) {
//                    return;
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }//GEN-LAST:event_BotonConfirmarActionPerformed

    private void BotonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCancelarActionPerformed
        con.rollback();
        encabezado_g = false;
        estadoedicionencab(false);
        estadoediciondetalle(false);
        RecuperaDetalles();
        dispose();
    }//GEN-LAST:event_BotonCancelarActionPerformed

    private void BotonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonNuevoActionPerformed
        nuevo();
    }//GEN-LAST:event_BotonNuevoActionPerformed

    private void BotonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEliminarActionPerformed
        this.NroFactura = this.txtNroFactura.getText();
        this.NroTimbrado = this.txtNroTimbrado.getText();
        eliminar(new String[]{NroFactura, NroTimbrado});
    }//GEN-LAST:event_BotonEliminarActionPerformed

    private void comboTipoFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoFacturaActionPerformed

    }//GEN-LAST:event_comboTipoFacturaActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        BotonCancelarActionPerformed(null);
    }//GEN-LAST:event_formWindowClosing

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        if (evt.getKeyCode() == 10) {
            String precio = txtPrecioCosto.getText().replace(".", "").replace(",", ".");
            Float Cantidad = null;

            if (txtCodItem.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto");
                return;
            }
            if (!txtCantidad.getText().isEmpty()) {
                if (!validaNumero(txtCantidad.getText())) {
                    JOptionPane.showMessageDialog(this, "Ingrese una cantidad correcta");

                } else {
                    Cantidad = Float.parseFloat(this.txtCantidad.getText());
                    if (Cantidad <= 0) {
                        JOptionPane.showMessageDialog(null, "Ingrese una cantidad correcta");
                        return;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe ingresar una cantidad");
                return;
            }

            if (modoediciondetalle.equals("1")) {
                if (comJSQL.existeRegistro("select * from factcompradet where NroFact = '" + txtNroFactura.getText() + "' and NroTimbrado = " + txtNroTimbrado.getText()
                        + " AND iditems = " + txtCodItem.getText())) {
                    JOptionPane.showMessageDialog(null, "El dato que intenta ingresar ya existe");
                    return;
                }
            }

            String[] ve_Ordenado = new String[]{txtNroFactura.getText(), txtNroTimbrado.getText(), txtCodItem.getText(), txtCantidad.getText(), precio, txtPorcIva.getText()};
            if (guardarToDataBaseConRestablecer("abm_facturacompradet", ve_Ordenado, modoediciondetalle)) {
                ActualizarInv();
            } else {
                JOptionPane.showMessageDialog(null, "Ocurrió un error en la base de datos");

            }
            comJPanel.setAllNullComponents(PanelDetalle);
            PanelDetalle.setVisible(false);
            RecuperaDetalles();
        }
    }//GEN-LAST:event_txtCantidadKeyPressed

    private void txtPCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPCompraKeyTyped
        if (evt.getKeyChar() < '0' || evt.getKeyChar() > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtPCompraKeyTyped

    private void txtPVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPVentaKeyTyped

    }//GEN-LAST:event_txtPVentaKeyTyped

    private void txtMargenCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtMargenCaretUpdate
        if (!txtMargen.getText().isEmpty() && !txtPCompra.getText().isEmpty()) {
            Double margen = Double.parseDouble(txtMargen.getText());
            Double precio = Double.parseDouble(txtPCompra.getText().toString());
            txtPVenta.setText((precio + ((precio * margen) / 100)) + "");
        }
    }//GEN-LAST:event_txtMargenCaretUpdate

    private void txtMargenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMargenKeyTyped
        if (evt.getKeyChar() < '0' || evt.getKeyChar() > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtMargenKeyTyped

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (jTabbedPane2.getSelectedIndex() == 0) {
            txtPrecioCosto.setValue(Double.parseDouble(comJCombox.getComboxString(cbPrecios1, 2).replace(",", "")));
            jPrecios.dispose();
            txtCantidad.grabFocus();
            txtCantidad.setSelectionStart(0);
            txtCantidad.setSelectionEnd(txtCantidad.getText().length());
        } else {
            if (txtPCompra.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese el precio de compra");
                return;
            }
            if (txtPVenta.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese el precio de venta");
                return;
            }
            if (txtMargen.getText().isEmpty() || Double.parseDouble(txtMargen.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Ingrese el margen de ganancia");
                return;
            }
            //            if (Double.parseDouble(txtPCompra.getText()) > Double.parseDouble(txtPVenta.getText())) {
            //                JOptionPane.showMessageDialog(null, "El precio de compra no puede ser menor al de venta");
            //                return;
            //            }
            try {
                int max = con.maximoValorCampo("PreciosArticulos", "idPrecioArticulo",
                        " where idArticulo = " + txtCodItem.getText());
                String[] ve_Ordenado = new String[]{max + "", txtCodItem.getText(),
                    txtPCompra.getText(), txtPVenta.getText(), txtMargen.getText()};
                if (guardarToDataBase("abm_PreciosArticulos", ve_Ordenado, "1"));
                {
                    txtPrecioCosto.setValue(Double.parseDouble(txtPCompra.getText()));
                    txtPCompra.setText("");
                    txtPVenta.setText("");
                    jPrecios.dispose();
                    txtCantidad.grabFocus();
                    txtCantidad.setSelectionStart(0);
                    txtCantidad.setSelectionEnd(txtCantidad.getText().length());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        jPrecios.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        imprimir();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        if (evt.getClickCount() == 2) {
            btnModificarDetalleActionPerformed(null);
        }
    }//GEN-LAST:event_tblDatosMouseClicked

    private void txtPrecioCostoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioCostoKeyPressed
        if (evt.getKeyCode() == 10) {
            txtCantidad.setSelectionStart(0);
            txtCantidad.setSelectionEnd(txtCantidad.getText().length());
            txtCantidad.grabFocus();
        }
    }//GEN-LAST:event_txtPrecioCostoKeyPressed

    private void txtCodProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodProveedorKeyPressed
        if (evt.getKeyCode() == 10) {
            btnBuscarProveedorActionPerformed(null);
        }
    }//GEN-LAST:event_txtCodProveedorKeyPressed

    private void comboTipoFacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboTipoFacturaKeyPressed
        if (evt.getKeyCode() == 10) {
            dateFecha.grabFocus();
        }
    }//GEN-LAST:event_comboTipoFacturaKeyPressed

    private void dateFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateFechaKeyPressed
        if (evt.getKeyCode() == 10) {
            btnAgregarDetalle.grabFocus();
        }
    }//GEN-LAST:event_dateFechaKeyPressed

    private void comboFormaPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFormaPagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboFormaPagoActionPerformed

    private void comboFormaPagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboFormaPagoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboFormaPagoKeyPressed

    private void txtNroTimbradoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroTimbradoKeyPressed
        if (evt.getKeyCode() == 10) {
            txtCodProveedor.grabFocus();
        }
    }//GEN-LAST:event_txtNroTimbradoKeyPressed

    private void ActualizarPMP() {
        ResultSet rs1 = con.dameLista("select iditems,PrecioCostUnitario,Cantidad from factcompradet where nrofact = '" + txtNroFactura.getText() + "' and NroTimbrado =" + txtNroTimbrado.getText());
        try {
            while (rs1.next()) {
                Integer idArticulo = rs1.getInt(1);
                ResultSet rs2 = con.dameLista("select existecia, preciocosto from items where iditems= " + idArticulo);
                Float Cantidad = rs1.getFloat(3);
                Float existencias = null, stock = null, preciocompra = null;
                try {
                    if (rs2.next()) {
                        existencias = rs2.getFloat(1);
                        preciocompra = rs2.getFloat(2);
                        Float newPrecio = rs1.getFloat(2); //trae el precio del detalle
                        stock = existencias + Cantidad;
                        Float pmp = ((existencias * preciocompra) + (Cantidad * newPrecio)) / stock;
                        //con.actualizarRegistro("Articulos", "Existencias = "+stock+", PrecioCompra = "+pmp+", UltimoPrecioCompra = "+preciocompra, "idArticulo = "+idArticulo);
                        con.actualizarRegistro("items", "preciocosto= " + pmp, "iditems= " + idArticulo);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void ActualizarProducto(String idArticulo, Float Cantidad, Float newPrecio) {
        ResultSet res = con.dameLista("select existecia, preciocosto from items where iditems= " + idArticulo);
        Float existencias = null, stock = null, preciocompra = null;
        try {
            if (res.next()) {
                existencias = res.getFloat(1);
                preciocompra = res.getFloat(2);
                stock = existencias + Cantidad;
                Float pmp = ((existencias * preciocompra) + (Cantidad * newPrecio)) / stock;
                con.actualizarRegistro("items", "stock = " + stock + ", preciocosto= " + pmp, "iditem = " + idArticulo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean ActualizarInv() {
        float cantidad;
        String articulo = null;

        if (modoediciondetalle.equals("1")) {
            articulo = txtCodItem.getText();
            cantidad = Float.parseFloat(txtCantidad.getText().replace(".", "").replace(",", "."));
            ResultSet rs = con.dameLista("select existecia from items where iditems = " + articulo);
            try {
                if (rs.next()) {
                    con.executeQuery("update items set existecia = " + ((rs.getFloat(1)) + (cantidad)) + " where iditems = " + articulo);
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (modoediciondetalle.equals("2")) {
            articulo = txtCodItem.getText();
            cantidad = Float.parseFloat(txtCantidad.getText().replace(".", "").replace(",", "."));
            ResultSet rs = con.dameLista("select existecia from items where iditems= " + articulo);
            try {
                if (rs.next()) {
                    con.executeQuery("update items set existecia = " + ((rs.getFloat(1)) - (this.cantidad) + (cantidad)) + " where iditems= " + articulo);
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (modoediciondetalle.equals("3")) {
            if (tblDatos.getSelectedRow() < 0) {
                ResultSet rs = con.dameLista("select iditems, Cantidad from factcompradet"
                        + " where nrofact = '" + txtNroFactura.getText() + "' and nrotimbrado = " + txtNroTimbrado.getText() + " group by iditems");
                try {
                    while (rs.next()) {
                        ResultSet rs2 = con.dameLista("select existecia from items where iditems= " + rs.getString(1));
                        if (rs2.next()) {
                            con.executeQuery("update items set existecia= " + ((rs2.getFloat(1)) - (rs.getFloat(2))) + " where iditems= " + rs.getString(1));
                            return true;
                        } else {
                            return false;
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                int fila = tblDatos.getSelectedRow();
                ResultSet rs = con.dameLista("select existecia,PrecioCosto from items where iditems= " + tblDatos.getValueAt(fila, 0));
                try {
                    if (rs.next()) {
                        con.executeQuery("update items set existecia= " + ((rs.getFloat("existecia")) - (Float.parseFloat((tblDatos.getValueAt(fila, 2) + "").replace(".", "").replace(",", ".")))) + ", PrecioCosto = " + rs.getString("PrecioCosto") + " where iditems = " + tblDatos.getValueAt(fila, 0));
                    } else {
                        return false;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return true;
    }

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
                FacturasCompra dialog = new FacturasCompra(new javax.swing.JFrame(), true, "");
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
    private javax.swing.JPanel PanelBotonesDetalle;
    private javax.swing.JPanel PanelDetalle;
    private javax.swing.JPanel PanelEncabezado;
    private javax.swing.JButton btnAgregarDetalle;
    private javax.swing.JButton btnBuscarItem;
    private javax.swing.JButton btnBuscarProveedor;
    private javax.swing.JButton btnGuardarEncabezado;
    private javax.swing.JButton btnModificarDetalle;
    private javax.swing.JButton btnQuitarDetalle;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbPrecios1;
    private javax.swing.JComboBox comboFormaPago;
    private javax.swing.JComboBox comboMonedas;
    private javax.swing.JComboBox comboTipoFactura;
    private com.toedter.calendar.JDateChooser dateFecha;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JDialog jPrecios;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblIva10;
    private javax.swing.JLabel lblIva5;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalIva;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodItem;
    private javax.swing.JTextField txtCodProveedor;
    private javax.swing.JLabel txtLetras;
    private javax.swing.JTextField txtMargen;
    private javax.swing.JTextField txtNombreItem;
    private javax.swing.JFormattedTextField txtNroFactura;
    private javax.swing.JTextField txtNroTimbrado;
    private javax.swing.JTextField txtPCompra;
    private javax.swing.JTextField txtPVenta;
    private javax.swing.JTextField txtPorcIva;
    private javax.swing.JFormattedTextField txtPrecioCosto;
    private javax.swing.JTextField txtRUC;
    private javax.swing.JTextField txtRazonSocial;
    // End of variables declaration//GEN-END:variables
}
