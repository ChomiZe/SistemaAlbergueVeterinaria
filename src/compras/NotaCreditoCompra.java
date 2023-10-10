package compras;

import Frames.DialogPadre;
import com.toedter.calendar.JTextFieldDateEditor;
import comunes.Numero_a_letra;
import comunes.comJPanel;
import comunes.comJTable;
import comunes.comJVarios;
import comunes.comLlamadaBuscadorComun;
import comunes.comLlamadaBuscadorComun2;
import comunes.reportes;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author alexxa
 */
public class NotaCreditoCompra extends DialogPadre {

    Date fechaactual;
    boolean headissaved = false;
    String modoediciondet, modoediciontrans;
    String ci_user;
    Double total;
    Float cantidad;
    JTextField valor = new JTextField();
    reportes jasper = new reportes();
    
    /**
     * Creates new form NotaCreditoCompra
     */
    public NotaCreditoCompra(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        super.AddTitleExtendSuf("Nota de Crédito Compra");
        initComponents();
        setLocationRelativeTo(null);
        PanelDetalles.setVisible(false);
        modoedicion = "1";
        ((JTextFieldDateEditor) this.txtFecha.getDateEditor()).setEditable(false);
        fechaactual = con.dameFecha();
        RecuperaDatos("");
        restablecer();
        con.autocommit(false);
    }
    
    @Override
    public void restablecer() {
        super.restablecer();
        comJPanel.setAllNullComponents(PanelEncabezado);
        comJPanel.setAllNullComponents(PanelDetalles);
        PanelDetalles.setVisible(false);
        txtFecha.setDate(fechaactual);
        txtFecha.setMaxSelectableDate(fechaactual);
        modoedicion = "1";
    }
    
    @Override
    public String[] ver(String[] valores) {
        txtNumero.setText(valores[0]);
        txtNroTimbrado.setText(valores[1]);
        txtNroFactura.setValue(valores[2]);

        Object[] OrdenDatos = new Object[] {txtFecha,txtRazonSocial};
        try {
            comJVarios.recuperaDatos("Fecha,RazonSocial",
                    "notacreditocompra_v","NroNotaCredito", txtNumero.getText()+" and NroTimbrado = '"
                    + txtNroTimbrado.getText()+"'"
                    , OrdenDatos);
            RecuperaDatos("");
            comJPanel.setEnabled(PanelEncabezado, false);
            headissaved = true;
            modoedicion = "2";
            btnAgregar.setEnabled(false);
            btnQuitar.setEnabled(false);
            btnGuardar.setEnabled(false);
        } catch (Exception ex) {
            Logger.getLogger(NotaCreditoCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.modificar(valores);
    }
    
    @Override
    public String[] modificar(String[] valores) {
        return ver(valores);
    }

    @Override
    public void eliminar(String[] valores) {
        txtNumero.setText(valores[0]);
        txtNroTimbrado.setText(valores[1]);
        txtNroFactura.setValue(valores[2]);
//        txtNroTimbradoFact.setText(valores[3]);
        String[] ve_Ordenado = new String[] {valores[0],valores[1],"0000-00-00",valores[2],"1"};
        guardarToDataBase("abm_NotaCreditoCompra", ve_Ordenado, "3");
        modoediciondet = "3";
        ActualizarInv();
    }
    
    @Override
    public void imprimir() {
         jasper.runreporte(new String[] {"nronota","nrotimbrado"}, new String[] {txtNumero.getText(),txtNroTimbrado.getText()}, "NotaCredito", true);
    }
    
    private void RecuperaDatos(String cadena) {
        DecimalFormat f = new DecimalFormat("###,###.00");
        String sql = "select iditems,DescripItems,Preciocosto,Cantidad,"
                + "Exentas,Gravadas5,Gravadas10 from notacreditocompradet_v "
                + "where NroNotaCredito like '"+txtNumero.getText()+"' and  NroTimbrado like '"+txtNroTimbrado.getText()+"'";
        comJTable.cargarGrilla(tblDetalles, new String[] {"Código","Artículo","Precio","Cantidad","Exentas","IVA 5%","IVA 10%"}, sql);
        comJTable.formatear(tblDetalles, new int[] {0,1,3,4,5,6}, new int[] {80,200,80,60,100,100});
        comJTable.orientacionColumnas(tblDetalles, new int[] {3}, 0);
        comJTable.orientacionColumnas(tblDetalles, new int[] {4,5,6}, 4);
        RecuperarTotales();
    }
    
    public void RecuperarTotales() {
        DecimalFormat f = new DecimalFormat("###,###,###");
        Double liq5 = 0.0, liq10 = 0.0, total = 0.0;
        Numero_a_letra letras = new Numero_a_letra();
        
        ResultSet rs = con.dameLista("select Exentas, Gravadas5, Gravadas10, Liquidacion5, Liquidacion10 from notacreditocompradet_v "
                + "where NroNotaCredito like '"+txtNumero.getText()+"' and NroTimbrado = '"+txtNroTimbrado.getText()
                + "'");
        try {
            while (rs.next()) {
                liq5 += rs.getDouble("Liquidacion5");
                liq10 += rs.getDouble("Liquidacion10");
                total += (rs.getDouble("Exentas")+rs.getDouble("Gravadas5")+rs.getDouble("Gravadas10"));
            }
            txtIVA5.setText(f.format(liq5));
            txtIVA10.setText(f.format(liq10));
            txtTotalIVA.setText(f.format(liq5+liq10));
            txtTotal.setText(f.format(total));
            txtTotalLetras.setText(letras.Convertir(total+"", true));
            this.total = total;
        }
        catch (Exception e) {}
    }
    
    public void RecuperarDetalle() {
        modoediciondet = "2";
        PanelDetalles.setVisible(true);
        int fila = tblDetalles.getSelectedRow();
        Object[] OrdenDatos = {txtCodProducto,txtNombre,txtPrecio,txtCantidad,txtIVA};
        try {
            comJVarios.recuperaDatos("iditems,Descripitems,PrecioCosto,Cantidad,PorcIva", "notacreditocompradet_v",
                "NroNotaCredito = "+txtNumero.getText()+" and NroTimbrado = '"+txtNroTimbrado.getText()+"' and iditems", tblDetalles.getValueAt(fila, 0)+"", OrdenDatos);
            txtCodProducto.setEditable(false);
            txtPrecio.setValue(Double.parseDouble(txtPrecio.getText()));
            txtCantidad.grabFocus();
        }
        catch (Exception e) { JOptionPane.showMessageDialog(null, e); }
        comJTable.formatear(tblDetalles, new int[] {0,1,2,3,5}, new int[] {80,80,80,200,90});
    }

    private boolean ActualizarInv() {
        float cantidad;
        String articulo = null;
        
        if (modoediciondet.equals("1")) {
            articulo = txtCodProducto.getText();
            cantidad = Float.parseFloat(txtCantidad.getText().replace(".", "").replace(",", "."));
            ResultSet rs = con.dameLista("select Existecia from items where iditems = "+articulo);
            try {
                if (rs.next()) {
                    
                    con.executeQuery("update items set Existecia = "+((rs.getFloat(1))+(cantidad))+" where iditems = "+articulo);
                    return true;
                }
                else
                    return false;
            } catch (SQLException ex) {
                Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (modoediciondet.equals("2")) {
            articulo = txtCodProducto.getText();
            cantidad = Float.parseFloat(txtCantidad.getText().replace(".", "").replace(",", "."));
            ResultSet rs = con.dameLista("select Existecia from items where iditems = "+articulo);
            try {
                if (rs.next()) {
                    con.executeQuery("update items set Existecia = "+((rs.getFloat(1))-(this.cantidad)+(cantidad))+" where iditems = "+articulo);
                    return true;
                }
                else
                    return false;
            } catch (SQLException ex) {
                Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (modoediciondet.equals("3")) {
            if (tblDetalles.getSelectedRow() < 0) {
                ResultSet rs = con.dameLista("select iditems, Cantidad from notacreditocompradet "
                        + "where NroNotaCredito = "+txtNumero.getText()+" and NroTimbrado = '"+txtNroTimbrado.getText()+"' group by iditems");
                try {
                    while (rs.next()) {
                        ResultSet rs2 = con.dameLista("select Existecia from items where iditems = "+rs.getString(1));
                        if (rs2.next()) {
                            con.executeQuery("update items set Existecia = "+((rs2.getFloat(1))-(rs.getFloat(2)))+" where iditems = "+rs.getString(1));
                            return true;
                        }
                        else 
                            return false;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                int fila = tblDetalles.getSelectedRow();
                ResultSet rs = con.dameLista("select Existecia from items where iditems = "+tblDetalles.getValueAt(fila, 0));
                try {
                    if (rs.next()) {
                        con.executeQuery("update items set Existecia = "+((rs.getFloat(1))-(Float.parseFloat((tblDetalles.getValueAt(fila, 3)+"").replace(".", "").replace(",", "."))))+" where iditems = "+tblDetalles.getValueAt(fila, 0)); 
                    }
                    else
                        return false;
                } catch (SQLException ex) {
                    Logger.getLogger(FacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return true;
    }
    
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
        txtNumero = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNroFactura = new javax.swing.JFormattedTextField();
        txtRUC = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        btnBuscarFactura = new javax.swing.JButton();
        txtFecha = new com.toedter.calendar.JDateChooser();
        txtNroTimbradoFact = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtNroTimbrado = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalles = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        PanelDetalles = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCodProducto = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtIVA = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JFormattedTextField();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtTotalIVA = new javax.swing.JLabel();
        txtTotalLetras = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtIVA10 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtIVA5 = new javax.swing.JLabel();
        txtIVA11 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        txtIVA6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nota de Crédito - Compra");

        PanelEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setText("Número");

        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumeroKeyPressed(evt);
            }
        });

        jLabel3.setText("Factura");

        try {
            txtNroFactura.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-#######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtNroFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNroFacturaKeyPressed(evt);
            }
        });

        txtRUC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRUCKeyPressed(evt);
            }
        });

        jLabel4.setText("Fecha");

        btnBuscarFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/circular icons/magnify.png"))); // NOI18N
        btnBuscarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFacturaActionPerformed(evt);
            }
        });

        txtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFechaKeyPressed(evt);
            }
        });

        txtNroTimbradoFact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNroTimbradoFactKeyPressed(evt);
            }
        });

        jLabel11.setText("Nro Timbrado:");

        jLabel10.setText("Nro Timbrado:");

        jLabel12.setText("Proveedor:");

        javax.swing.GroupLayout PanelEncabezadoLayout = new javax.swing.GroupLayout(PanelEncabezado);
        PanelEncabezado.setLayout(PanelEncabezadoLayout);
        PanelEncabezadoLayout.setHorizontalGroup(
            PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNroFactura, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(txtNumero))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNroTimbradoFact, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(txtNroTimbrado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                        .addComponent(txtRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarFactura))
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        PanelEncabezadoLayout.setVerticalGroup(
            PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel11)
                        .addComponent(txtNroTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtNroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNroTimbradoFact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRUC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarFactura)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblDetalles.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDetalles.getTableHeader().setReorderingAllowed(false);
        tblDetalles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetallesMouseClicked(evt);
            }
        });
        tblDetalles.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDetallesKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblDetalles);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/circular icons/add.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/circular icons/minus.png"))); // NOI18N
        btnQuitar.setText("Quitar");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        PanelDetalles.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel6.setText("Producto");

        txtCodProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodProductoActionPerformed(evt);
            }
        });

        txtNombre.setEditable(false);

        jLabel7.setText("IVA");

        txtIVA.setEditable(false);

        jLabel8.setText("Precio");

        jLabel9.setText("Cantidad");

        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        txtPrecio.setEditable(false);
        txtPrecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));

        javax.swing.GroupLayout PanelDetallesLayout = new javax.swing.GroupLayout(PanelDetalles);
        PanelDetalles.setLayout(PanelDetallesLayout);
        PanelDetallesLayout.setHorizontalGroup(
            PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDetallesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDetallesLayout.createSequentialGroup()
                        .addComponent(txtCodProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(PanelDetallesLayout.createSequentialGroup()
                        .addComponent(txtIVA, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(213, Short.MAX_VALUE))
        );
        PanelDetallesLayout.setVerticalGroup(
            PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDetallesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCodProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtIVA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/circular icons/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/circular icons/save.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel5.setText("Liq. del I.V.A.:");

        txtTotalIVA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalIVA.setText("0.0");

        txtTotalLetras.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        txtTotalLetras.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalLetras.setText(" ");

        jLabel17.setText("Total IVA:");

        txtIVA10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtIVA10.setText("0.0");

        jLabel19.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel19.setText("Total:");

        txtIVA5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtIVA5.setText("0.0");

        txtIVA11.setText("10%");

        txtTotal.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotal.setText("0.0");

        txtIVA6.setText("(5%)");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/circular icons/print.png"))); // NOI18N
        jButton1.setText("Imprimir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addComponent(PanelEncabezado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnAgregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(PanelDetalles, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtTotalLetras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtIVA6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtIVA5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtIVA11)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtIVA10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel17)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(txtTotalIVA, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel19)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnGuardar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCancelar)))))
                        .addGap(12, 12, 12))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancelar, btnGuardar, jButton1});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAgregar, btnQuitar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(PanelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnQuitar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalIVA)
                    .addComponent(jLabel17)
                    .addComponent(txtIVA10)
                    .addComponent(txtIVA11)
                    .addComponent(txtIVA5)
                    .addComponent(txtIVA6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotalLetras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar)
                    .addComponent(jButton1))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if (txtNumero.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el número");
            txtNumero.grabFocus();
            return;
        }
        if (txtNroFactura.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Elija una factura");
            txtNroFactura.grabFocus();
            return;
        }
        if (txtRUC.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
            txtRUC.grabFocus();
            return;
        }
        
        if (!headissaved) {
            ResultSet rs = con.dameLista("select NroNotaCredito from notacreditocompra where NroFact = '"
                    + txtNroFactura.getText()+"' and NroTimbradoFactura = '"+txtNroTimbradoFact.getText()+"'");
            try {
                if (rs.next()) {
                    //if (rs.getString(1).equals(txtNumero.getText())) {
                        JOptionPane.showMessageDialog(this, "Ya existe una nota con ese número asociada a la factura","Error",JOptionPane.WARNING_MESSAGE);
                        txtNumero.setSelectionStart(0);
                        txtNumero.setSelectionEnd(txtNumero.getText().length());
                        txtNumero.grabFocus();
                        return;
                    //}
                }
            } catch (SQLException ex) {
                Logger.getLogger(NotaCreditoCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        modoediciondet = "1";
        if (PanelDetalles.isVisible()) {
            comJPanel.setAllNullComponents(PanelDetalles);
            PanelDetalles.setVisible(false);
            RecuperaDatos("");
            return;
        }
        else {
            PanelDetalles.setVisible(true);
            txtCodProducto.setEditable(true);
            txtCodProducto.grabFocus();
        }
        
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] ve_Ordenado = new String[] {txtNumero.getText(),txtNroTimbrado.getText(),f.format(txtFecha.getDate()),txtNroFactura.getText(),txtNroTimbradoFact.getText(),};
        if (!headissaved) {
            if (!guardarToDataBase("abm_notacreditocompra", ve_Ordenado, modoedicion)) {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
            else { headissaved = true; comJPanel.setEnabled(PanelEncabezado, false); }
        }
        RecuperaDatos("");
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtCodProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodProductoActionPerformed
        String art = "", p, pr;
        art = " and iditems like '"+txtCodProducto.getText()+"'";
        
        String sql = "select iditems, Nombre, format(Precio,0) AS Precio " +
                "from comprasdet_v where " +
                "(`comprasdet_v`.`NroFacturaCompra` = '"+txtNroFactura.getText()+"') " +
                "and (`comprasdet_v`.`NroTimbrado` = '"+txtNroTimbrado.getText()+"') "+art;
//                "and (not (exists( select 1 " +
//                "from NCreditoCompraDetalles where " +
//                "((`NCreditoCompraDetalles`.`NroNotaCredito` = "+txtNumero.getText()+") " +
//                "and (`NCreditoCompraDetalles`.`NroFacturaCompra` = '"+txtNroFactura.getText()+"') " +
//                "and (`NCreditoCompraDetalles`.`RUC_CI` = '"+txtRUC.getText()+"'))))))";
        
        if (comLlamadaBuscadorComun2.BuscadorjDialog("NroFact,NroTimbrado,iditems,DescripItems, format(PrecioCostUnitario,0) AS Precio, Cantidad ", "comprasdet_v", 
                new String[][] {{"NroFact","0"},{"NroTimbrado","1"},{"iditems","2"}}, new String[]{"iditems"},
            "where (iditems not in (select iditems from notacreditocompradet where NroNotaCredito = "+txtNumero.getText()+" and NroTimbrado = '"+txtNroTimbrado.getText()+"' ) and "
                    + "iditems in ((select iditems from factcompradet where NroFact = '"+txtNroFactura.getText()+"' and NroTimbrado = "+txtNroTimbradoFact.getText()+")))", new int[]{}, 
            new String[] {"Factura","NroTimbrado","Cod Artículo","Artículo","Precio","Cantidad"}, new int[]{0,1}, new int[] {600,400}, new int[] {0,0,60,300,100,80}, "iditems")) {
        
            //if (comLlamadaBuscadorComun.BuscadorjDialog(sql, new String[] {"Código","Nombre","Precio"})) {
            comLlamadaBuscadorComun2.BuscadorjDialogetStringObjAlt(new Object[] {txtCodProducto,txtNombre,txtCantidad,txtPrecio,txtIVA}, new int[]{3,4,5,6,7});
            txtPrecio.setValue(Double.parseDouble(txtPrecio.getText()));
            txtCantidad.setSelectionStart(0);
            txtCantidad.setSelectionEnd(txtCantidad.getText().length());
            txtCantidad.grabFocus();
        }
    }//GEN-LAST:event_txtCodProductoActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        //String precio = txtPrecio.getText().replace(".", "").replace(",", ".");
        Float cantidad = null;
        ResultSet rs = con.dameLista("select Cantidad from factcompradet where NroFact = '"
                + txtNroFactura.getText()+"' and NroTimbrado = '"+txtNroTimbradoFact.getText()+"' and iditems = "+txtCodProducto.getText());
        try {
            if (rs.next()) {
                cantidad = rs.getFloat(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotaCreditoCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        Float cant = Float.parseFloat(txtCantidad.getText());
        if (cant > cantidad) {
            JOptionPane.showMessageDialog(null, "Ingrese una cantidad menor o igual al de la factura");
            return;
        }
        
        String precio = txtPrecio.getValue().toString();
        String[] ve_Ordenado = new String[] {txtNumero.getText(),txtNroTimbrado.getText(),txtCodProducto.getText(),txtCantidad.getText(),precio,txtIVA.getText()};
        if (guardarToDataBase("abm_notacreditocompradet", ve_Ordenado, modoediciondet)) {
            ActualizarInv();
            modoediciondet = "1";
            comJPanel.setAllNullComponents(PanelDetalles);
            PanelDetalles.setVisible(false);
            txtCodProducto.setEditable(true);
            RecuperaDatos("");
        }
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void btnBuscarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFacturaActionPerformed
        String fact = "", ruc = "";
        if (comLlamadaBuscadorComun.BuscadorjDialog("select NroFact,NroTimbrado,Fecha,idProveedores,DescripProveedores "
                + "from compras_v", new String[] {"Numero","NroTimbrado","Fecha","Código","Nombre"})) {
            comLlamadaBuscadorComun.BuscadorjDialogetStringObjAlt(new Object[]{fact,ruc}, new int[]{1,2});
            ResultSet rs = con.dameLista("select sum(SubTotal) from comprasdet_v where NroFact = '"+fact
                    + "' and NroTimbrado= '"+ruc+"'");
            try {
                if (rs.next()) {
                    total = rs.getDouble(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(NotaCreditoCompra.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            
            comLlamadaBuscadorComun.BuscadorjDialogetStringObjAlt(new Object[]{txtNroFactura,txtNroTimbradoFact,txtRUC,txtRazonSocial}, new int[]{1,2,7,8});
            btnAgregar.grabFocus();
        }
    }//GEN-LAST:event_btnBuscarFacturaActionPerformed

    private void txtNroFacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroFacturaKeyPressed
        if (evt.getKeyCode() == 10) {
            txtNroTimbradoFact.grabFocus();
        }
    }//GEN-LAST:event_txtNroFacturaKeyPressed

    private void txtRUCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRUCKeyPressed
        
    }//GEN-LAST:event_txtRUCKeyPressed

    private void tblDetallesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDetallesKeyPressed
        if (evt.getKeyCode() == 10 && modoedicion.equals("1")) {
            RecuperarDetalle();
        }
        if (evt.getKeyCode() == KeyEvent.VK_DELETE && modoedicion.equals("1")) {
            btnQuitarActionPerformed(null);
        }
    }//GEN-LAST:event_tblDetallesKeyPressed

    private void tblDetallesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetallesMouseClicked
        if (evt.getClickCount() == 2 && modoedicion.equals("1")) {
            RecuperarDetalle();
        }
    }//GEN-LAST:event_tblDetallesMouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (tblDetalles.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Debe ingresar por lo menos un detalle");
        }
        else {
            restablecer();
            con.commit();
            dispose();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (!txtNumero.getText().isEmpty()) {
            int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea cancelar?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                con.rollback();
                restablecer();
                dispose();
            }
        }
        else { 
            con.rollback();
            restablecer();
            dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        formWindowClosing(null);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        int fila = tblDetalles.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione primero un registro");
        }
        else {
            int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea borrar?", "Confirme", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                    String[] ve_Ordenado = new String[] {txtNumero.getText(),txtNroFactura.getText(),txtNroTimbradoFact.getText(),
                tblDetalles.getValueAt(fila, 2)+"",tblDetalles.getValueAt(fila, 1)+"",tblDetalles.getValueAt(fila, 0)+"","0","0","0"};
                guardarToDataBase("abm_NCreditoCompraDetalles", ve_Ordenado, "3");
                ActualizarInv();
                modoediciondet = "1";
                RecuperaDatos("");
            }
        }
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jasper.runreporte(new String[] {"nronota","nrotimbrado"}, new String[] {txtNumero.getText(),
            txtNroTimbrado.getText()}, "NotaCredito", true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtNroTimbradoFactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroTimbradoFactKeyPressed
        if (evt.getKeyCode() == 10) {
            ResultSet rs = con.dameLista("select idProveedor, RUC, Proveedor from facturascompra_v where "
                    + "NroFacturaCompra like '"+txtNroFactura.getText()+"' and idProveedor like '"
                    + txtNroTimbradoFact.getText()+"'");
            try {
                if (rs.next()) {
                    txtNroTimbradoFact.setText(rs.getString(1));
                    txtRUC.setText(rs.getString(2));
                    txtRazonSocial.setText(rs.getString(3));
                    btnAgregar.grabFocus();
                }
            } catch (SQLException ex) {}
        }
    }//GEN-LAST:event_txtNroTimbradoFactKeyPressed

    private void txtNumeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyPressed
        if (evt.getKeyCode() == 10)
            txtFecha.grabFocus();
    }//GEN-LAST:event_txtNumeroKeyPressed

    private void txtFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyPressed
        btnBuscarFactura.grabFocus();
    }//GEN-LAST:event_txtFechaKeyPressed

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
            java.util.logging.Logger.getLogger(NotaCreditoCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                NotaCreditoCompra dialog = new NotaCreditoCompra(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel PanelDetalles;
    private javax.swing.JPanel PanelEncabezado;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscarFactura;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDetalles;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodProducto;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtIVA;
    private javax.swing.JLabel txtIVA10;
    private javax.swing.JLabel txtIVA11;
    private javax.swing.JLabel txtIVA5;
    private javax.swing.JLabel txtIVA6;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JFormattedTextField txtNroFactura;
    private javax.swing.JTextField txtNroTimbrado;
    private javax.swing.JTextField txtNroTimbradoFact;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JFormattedTextField txtPrecio;
    private javax.swing.JTextField txtRUC;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JLabel txtTotal;
    private javax.swing.JLabel txtTotalIVA;
    private javax.swing.JLabel txtTotalLetras;
    // End of variables declaration//GEN-END:variables
}
