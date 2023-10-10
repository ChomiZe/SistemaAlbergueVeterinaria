package compras;

import com.toedter.calendar.JTextFieldDateEditor;
import comunes.Numero_a_letra;
import comunes.comJPanel;
import comunes.comJTable;
import comunes.comJVarios;
import comunes.comLlamadaBuscadorComun;
import comunes.reportes;
import Frames.DialogPadre;
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
public class NotaDebitoCompra extends DialogPadre {

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
    public NotaDebitoCompra(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        super.AddTitleExtendSuf("Nota de Débito Compra");
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
        btnAgregar.setEnabled(true);
        btnQuitar.setEnabled(true);
        btnGuardar.setEnabled(true);
        txtFecha.setDate(fechaactual);
        txtFecha.setMaxSelectableDate(fechaactual);
        modoedicion = "1";
    }
    
    @Override
    public String[] modificar(String[] valores) {
        txtNumero.setValue(valores[0]);
        txtNroTimbrado.setText(valores[1]);
 
        Object[] OrdenDatos = new Object[] {txtNroFactura,txtNroTimbradoFact,txtFecha,txtRazonSocial};
        try {
            comJVarios.recuperaDatos("NroFact,NroTimbradoFactura,Fecha,RazonSocial",
                    "NotaDebitoCompra_v","NroNotaDebitoCompra", txtNumero.getText()+" and NroTimbrado = '"
                    + txtNroTimbrado.getText()+"'"
                    , OrdenDatos);
            RecuperaDatos("");
            comJPanel.setEnabled(PanelEncabezado, false);
            btnAgregar.setEnabled(false);
            btnQuitar.setEnabled(false);
            btnGuardar.setEnabled(false);
            headissaved = true;
            modoedicion = "2";
        } catch (Exception ex) {
            Logger.getLogger(NotaDebitoCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.modificar(valores);
    }
    
    @Override
    public void nuevo() {
        restablecer();
    }
    
    @Override
    public void eliminar(String[] valores) {
        txtNumero.setValue(valores[0]);
        txtNroTimbrado.setText(valores[1]);
        //txtRUC.setText(valores[2]);
        String[] ve_Ordenado = new String[] {valores[0],valores[1],"0000-00-00","","0"};
        guardarToDataBase("abm_notadebitocompra", ve_Ordenado, "3");
        restablecer();
        con.commit();
    }
    
    @Override
    public void imprimir() {
           jasper.runreporte(new String[] {"nronota","nrotimbrado"}, new String[] {txtNumero.getText(),
            txtNroTimbrado.getText()}, "NotaDebito", true);
    }

    private void RecuperaDatos(String cadena) {
        DecimalFormat f = new DecimalFormat("###,###.00");
        Numero_a_letra letras = new Numero_a_letra();
        Double liq5 = 0.0, liq10 = 0.0, total = 0.0, peso = 0.0;
        String sql = "select idConceptoNotaDebito,Descripcion,Precio,Cantidad,Exentas,Gravadas5,Gravadas10,Liquidacion5,Liquidacion10,SubTotal "
                + "from notadebitocompradet_v "
                + "where NroNotaDebitoCompra like '"+txtNumero.getText()+"' and NroTimbrado like '"
                + txtNroTimbrado.getText()+"'";
        comJTable.cargarGrilla(tblDetalles, new String[] {"Código","Concepto","Precio","Cantidad","Exentas","IVA 5%","IVA 10%"}, sql);
        ResultSet rs = con.dameLista(sql);
        try {
            while (rs.next()) {
                liq5 += rs.getDouble("Liquidacion5");
                liq10 += rs.getDouble("Liquidacion10");
                total += rs.getDouble("SubTotal");
            }
            txtIVA5.setText(f.format(liq5));
            txtIVA10.setText(f.format(liq10));
            txtTotalIVA.setText(f.format(liq5+liq10));
            txtTotal.setText(f.format(total));
            txtTotalLetras.setText(letras.Convertir(total+"", true));
            //this.total = total;
        }
        catch (Exception e) {}
        comJTable.formatear(tblDetalles, new int[] {0,1,2,3}, new int[] {60,200,100,60});
    }
    
    public void RecuperarDetalle() {
        modoediciondet = "2";
        PanelDetalles.setVisible(true);
        int fila = tblDetalles.getSelectedRow();
        Object[] OrdenDatos = {txtCodConcepto,txtConcepto,txtPrecio,txtCantidad,txtIVA};
        try {
            comJVarios.recuperaDatos("idConcepto,Nombre,Precio,Cantidad,PorcIva", "notadebitocompradet_v",
                "NroNotaDebitoCompra = "+txtNumero.getText()+" and NroFact = '"+txtNroFactura.getText()+"' and "
                    + "RUC = '"+txtRUC.getText()+"' and idConceptoNotaDebito", tblDetalles.getValueAt(fila, 0)+"", OrdenDatos);
            txtCodConcepto.setEditable(false);
            //txtPrecio.setValue(Double.parseDouble(txtPrecio.getText()));
            txtPrecio.grabFocus();
            txtPrecio.setSelectionStart(0);
            txtPrecio.setSelectionEnd(txtPrecio.getText().length());
        }
        catch (Exception e) { JOptionPane.showMessageDialog(this, e); }
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
        jLabel3 = new javax.swing.JLabel();
        txtNroFactura = new javax.swing.JFormattedTextField();
        txtRUC = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        btnBuscarFactura = new javax.swing.JButton();
        txtFecha = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        txtNroTimbrado = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtNroTimbradoFact = new javax.swing.JTextField();
        txtNumero = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalles = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        PanelDetalles = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCodConcepto = new javax.swing.JTextField();
        txtConcepto = new javax.swing.JTextField();
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
        btnImprimir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nota de Débito - Compra");

        PanelEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setText("Número");

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

        txtRazonSocial.setEditable(false);

        btnBuscarFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/circular icons/magnify.png"))); // NOI18N
        btnBuscarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFacturaActionPerformed(evt);
            }
        });

        jLabel10.setText("Timbrado");

        txtNroTimbrado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNroTimbradoKeyTyped(evt);
            }
        });

        jLabel11.setText("Timbrado:");

        txtNroTimbradoFact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNroTimbradoFactKeyTyped(evt);
            }
        });

        try {
            txtNumero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-#######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumeroKeyPressed(evt);
            }
        });

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
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNroTimbrado)
                    .addComponent(txtNroTimbradoFact, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(265, Short.MAX_VALUE))
                    .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                        .addComponent(txtRUC, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarFactura)
                        .addGap(37, 37, 37))))
        );
        PanelEncabezadoLayout.setVerticalGroup(
            PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4)
                        .addComponent(jLabel10)
                        .addComponent(txtNroTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarFactura)
                    .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtNroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtRUC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(txtNroTimbradoFact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        jLabel6.setText("Concepto");

        txtCodConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodConceptoActionPerformed(evt);
            }
        });

        txtConcepto.setEditable(false);

        jLabel7.setText("IVA");

        txtIVA.setEditable(false);
        txtIVA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIVAKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIVAKeyPressed(evt);
            }
        });

        jLabel8.setText("Precio");

        jLabel9.setText("Cantidad");

        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        txtPrecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrecioKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout PanelDetallesLayout = new javax.swing.GroupLayout(PanelDetalles);
        PanelDetalles.setLayout(PanelDetallesLayout);
        PanelDetallesLayout.setHorizontalGroup(
            PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDetallesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(PanelDetallesLayout.createSequentialGroup()
                        .addComponent(txtCodConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtIVA, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelDetallesLayout.setVerticalGroup(
            PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDetallesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIVA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/circular icons/print.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
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
                                .addComponent(btnQuitar))
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
                                        .addComponent(btnImprimir)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnGuardar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCancelar)))))
                        .addGap(12, 12, 12))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancelar, btnGuardar});

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    .addComponent(btnImprimir))
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
        
        ResultSet rs = con.dameLista("select NroNotaDebitoCompra from NotaDebitoCompra where NroFact = '"
                + txtNroFactura.getText()+"' and NroTimbradoFactura = '"+txtNroTimbradoFact.getText()+"'");
        try {
            if (rs.next()) {
          
                    JOptionPane.showMessageDialog(this, "Ya existe una nota con ese número asociada a la factura","Error",JOptionPane.WARNING_MESSAGE);
                    txtNumero.setSelectionStart(0);
                    txtNumero.setSelectionEnd(txtNumero.getText().length());
                    return;
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotaDebitoCompra.class.getName()).log(Level.SEVERE, null, ex);
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
            txtCodConcepto.setEditable(true);
            txtCodConcepto.grabFocus();
        }
        
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] ve_Ordenado = new String[] {txtNumero.getText(),txtNroTimbrado.getText(),f.format(txtFecha.getDate()),txtNroFactura.getText(),txtNroTimbradoFact.getText()};
        if (!headissaved) {
            if (!guardarToDataBase("abm_notadebitocompra", ve_Ordenado, modoedicion)) {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
            else { headissaved = true; comJPanel.setEnabled(PanelEncabezado, false); }
        }
        RecuperaDatos("");
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtCodConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodConceptoActionPerformed
        String art = "";
        String existe = " and not exists (select * from NotaDebitoCompraDet where "
                + "NroNotaDebitoCompra = "+txtNumero.getText()+" NroFact = '"+txtNroFactura.getText()+"' "
                + "and RUC = '"+txtRUC.getText()+"')";
        if (!txtCodConcepto.getText().isEmpty()) {
            art = " where idConceptoNotaDebito = "+txtCodConcepto.getText();
        }
        
        String sql = "select idConceptoNotaDebito, Descripcion, PorcIva from conceptos_notadebito_v "
                + ""+art;
//                "and (not (exists( select 1 " +
//                "from NCreditoCompraDetalles where " +
//                "((`NCreditoCompraDetalles`.`NroNCreditoCompra` = "+txtNumero.getText()+") " +
//                "and (`NCreditoCompraDetalles`.`NroFacturaCompra` = '"+txtNroFactura.getText()+"') " +
//                "and (`NCreditoCompraDetalles`.`RUC_CI` = '"+txtRUC.getText()+"'))))))";
        System.out.println(sql);
        if (comLlamadaBuscadorComun.BuscadorjDialog(sql, new String[] {"Código","Nombre","Porcentaje"})) {
            comLlamadaBuscadorComun.BuscadorjDialogetStringObjAlt(new Object[] {txtCodConcepto,txtConcepto,txtIVA}, new int[]{1,2,5});
            txtCantidad.setText("1");
            txtPrecio.grabFocus();
        }
    }//GEN-LAST:event_txtCodConceptoActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        //String precio = txtPrecio.getText().replace(".", "").replace(",", ".");
        String precio = txtPrecio.getValue().toString();
        if (txtPrecio.getText().equals("") || Double.parseDouble(precio) <= 0.0) {
            JOptionPane.showMessageDialog(this, "Ingrese un precio correcto");
            txtPrecio.grabFocus();
            return;
        }
        if (txtIVA.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese el IVA");
            txtPrecio.grabFocus();
            return;
        }
        String[] ve_Ordenado = new String[] {txtNumero.getText(),txtNroTimbrado.getText(),
            txtCodConcepto.getText(),txtCantidad.getText(),precio,txtIVA.getText()};
        if (guardarToDataBase("abm_notadebitocompradet", ve_Ordenado, modoediciondet)) {
            modoediciondet = "1";
            comJPanel.setAllNullComponents(PanelDetalles);
            PanelDetalles.setVisible(false);
            txtCodConcepto.setEditable(true);
            RecuperaDatos("");
        }
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void btnBuscarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFacturaActionPerformed
        String fact = "", ruc = "";
        if (comLlamadaBuscadorComun.BuscadorjDialog("select NroFact,NroTimbrado,Fecha,ruc,DescripProveedores "
                + "from compras_v", new String[] {"Numero","Timbrado","Fecha","RUC/CI","Nombre"})) {
            comLlamadaBuscadorComun.BuscadorjDialogetStringObjAlt(new Object[]{fact,ruc}, new int[]{1,2});
            ResultSet rs = con.dameLista("select NroNotaDebitoCompra from NotaDebitoCompra where NroFact = '"+fact
                    + "' and NroTimbrado = '"+ruc+"'");
            try {
                if (rs.next()) {
                    if (rs.getString(1).equals(txtNumero.getText())) {
                        JOptionPane.showMessageDialog(this, "La nota ya se encuentra registrada");
                        return;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(NotaDebitoCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
            comLlamadaBuscadorComun.BuscadorjDialogetStringObjAlt(new Object[]{txtNroFactura,txtNroTimbradoFact,txtRUC,txtRazonSocial}, new int[]{1,2,7,8});
        }
    }//GEN-LAST:event_btnBuscarFacturaActionPerformed

    private void txtNroFacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroFacturaKeyPressed
        if (evt.getKeyCode() == 10) {
            txtRUC.grabFocus();
        }
    }//GEN-LAST:event_txtNroFacturaKeyPressed

    private void txtRUCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRUCKeyPressed
        if (evt.getKeyCode() == 10) {
            ResultSet rs = con.dameLista("select RUC_CI, Proveedor from FacturasCompra_v where "
                    + "NroFacturaCompra like '"+txtNroFactura.getText()+"' and RUC_CI like '"
                    + txtRUC.getText()+"'");
            try {
                if (rs.next()) {
                    txtRUC.setText(rs.getString(1));
                    txtRazonSocial.setText(rs.getString(2));
                    btnAgregar.grabFocus();
                }
            } catch (SQLException ex) {}
        }
    }//GEN-LAST:event_txtRUCKeyPressed

    private void tblDetallesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDetallesKeyPressed
        if (evt.getKeyCode() == 10 && modoedicion.equals("1")) {
            RecuperarDetalle();
        }
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
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

    private void txtPrecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyPressed
        if (evt.getKeyCode() == 10) {
            txtCantidad.setSelectionStart(0);
            txtCantidad.setSelectionEnd(txtCantidad.getText().length());
            txtCantidad.grabFocus();
        }
    }//GEN-LAST:event_txtPrecioKeyPressed

    private void txtIVAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIVAKeyPressed
        if (evt.getKeyCode() == 10) {
            txtCantidad.setSelectionStart(0);
            txtCantidad.setSelectionEnd(txtCantidad.getText().length());
            txtCantidad.grabFocus();
        }
    }//GEN-LAST:event_txtIVAKeyPressed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        int fila = tblDetalles.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione primero un registro");
        }
        else {
            int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea borrar?", "Confirme", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                String[] ve_Ordenado = new String[] {txtNumero.getText(),txtNroTimbrado.getText(),
                tblDetalles.getValueAt(fila, 0).toString(),"0","0","0"};
                guardarToDataBase("abm_notadebitocompradet", ve_Ordenado, "3");
                RecuperaDatos("");
            }
        }
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void txtIVAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIVAKeyTyped
        if (evt.getKeyChar() < '0' || (evt.getKeyChar() > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtIVAKeyTyped

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        if (evt.getKeyChar() < '0' || (evt.getKeyChar() > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecioKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        if (evt.getKeyChar() < '0' || (evt.getKeyChar() > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtNroTimbradoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroTimbradoKeyTyped
       if (evt.getKeyChar()<'0'|| evt.getKeyChar()>'9'){
           evt.consume();
       }
    }//GEN-LAST:event_txtNroTimbradoKeyTyped

    private void txtNroTimbradoFactKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroTimbradoFactKeyTyped
        if (evt.getKeyChar()<'0'|| evt.getKeyChar()>'9'){
           evt.consume();
       }

    }//GEN-LAST:event_txtNroTimbradoFactKeyTyped

    private void txtNumeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroKeyPressed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
               jasper.runreporte(new String[] {"nronota","nrotimbrado"}, new String[] {txtNumero.getText(),
            txtNroTimbrado.getText()}, "NotaDebito", true);
    }//GEN-LAST:event_btnImprimirActionPerformed

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
            java.util.logging.Logger.getLogger(NotaDebitoCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NotaDebitoCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NotaDebitoCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotaDebitoCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                NotaDebitoCompra dialog = new NotaDebitoCompra(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JTextField txtCodConcepto;
    private javax.swing.JTextField txtConcepto;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtIVA;
    private javax.swing.JLabel txtIVA10;
    private javax.swing.JLabel txtIVA11;
    private javax.swing.JLabel txtIVA5;
    private javax.swing.JLabel txtIVA6;
    private javax.swing.JFormattedTextField txtNroFactura;
    private javax.swing.JTextField txtNroTimbrado;
    private javax.swing.JTextField txtNroTimbradoFact;
    private javax.swing.JFormattedTextField txtNumero;
    private javax.swing.JFormattedTextField txtPrecio;
    private javax.swing.JTextField txtRUC;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JLabel txtTotal;
    private javax.swing.JLabel txtTotalIVA;
    private javax.swing.JLabel txtTotalLetras;
    // End of variables declaration//GEN-END:variables
}
