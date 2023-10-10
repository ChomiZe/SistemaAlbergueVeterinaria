package cobros;

import Frames.DialogPadre;

import com.toedter.calendar.JTextFieldDateEditor;
import comunes.comJCombox;
import comunes.comJPanel;
import comunes.comJSQL;
import comunes.comJVarios;
import comunes.comLlamadaBuscadorComun2;
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

public class Cobros extends DialogPadre {

    String tituloGral = "Cobros";
    String tabla = "cobros";
    String campoID = "idCobros";
    String campoValue = "idVoluntarios";
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

  
    public Cobros(java.awt.Frame parent, boolean modal, String modoed) {
        super(parent, modal);
        AddTitleExtendSuf("Cobros");
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

        con.cargarCombo("select * from condicionpago order by idCondicionPago", comboTipoFactura);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            hoydate = dateFormat.parse(hoyformatopy);

        } catch (ParseException ex) {
            Logger.getLogger(Cobros.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Cobros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nuevo() {
        String sql = "SELECT * FROM aperturacaja WHERE aperturacaja.idaperturacaja NOT IN (SELECT idaperturacaja FROM cierrecaja) ";
        try{
            ResultSet rs = con.dameLista(sql);
            if (rs.next()){
                txtCodApertura.setText(rs.getString(1));
            }else{
                JOptionPane.showMessageDialog(this, "No hay ninguna apertura de caja");
            }
        }catch (SQLException ex) {
            Logger.getLogger(Cobros.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sentencia = "SELECT MAX(idCobros) FROM Cobros ";
        try {
            ResultSet rs = con.dameLista(sentencia);
            rs.first();
            txtCodigo.setText(Integer.toString(rs.getInt(1) + 1));
            txtCodVoluntario.grabFocus();
        } catch (SQLException ex) {
            Logger.getLogger(Cobros.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.btnBuscarProveedor.setVisible(true);

        this.comboTipoFactura.setEnabled(true);

        this.txtRazonSocial.setText("");
        this.comboTipoFactura.setSelectedIndex(0);

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

        this.btnBuscarProveedor.setVisible(false);

        //this.BotonConfirmar.setEnabled(false);
        this.BotonCancelar.setEnabled(false);
        this.BotonNuevo.setEnabled(true);
        this.BotonEliminar.setEnabled(true);

        this.comboTipoFactura.setEnabled(false);
        this.dateFecha.getCalendarButton().setEnabled(false);
        BotonConfirmar.setEnabled(false);
        NroFactura = "'" + valores[0] + "'";
        idProveedor = "'" + valores[1] + "'";
        try {
            modoedicion = "2";
            modoediciondetalle = "1";
            Object[] datos = {txtCodigo, txtCodVoluntario, dateFecha, txtMonto, txtCodSocio, txtCodApertura};
            comJVarios.recuperaDatos("NroFact, idProveedores, RUC, DescripProveedores, Fecha, idcondicionpago", "compras_v", "NroFact = '" + valores[0] + "' AND NroTimbrado", valores[1], datos);

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
             Object[] datos = {txtCodigo, txtCodVoluntario, txtRazonSocial, dateFecha, txtMonto,txtCodSocio,txtRazonSocial1,comboTipoFactura,txtCodApertura};
            comJVarios.recuperaDatos("idCobros, idVoluntarios,Voluntario, Fecha,MontoEntregado,idSocioProtector, SocioProtector,idCondicionPago,idAperturaCaja", "cobros_v", "idCobros = '" + valores[0] + "' AND idVoluntarios", "'" + valores[1] + "'", datos);
            comJPanel.setEnabled(PanelEncabezado, true);
            comJPanel.setEnabled(PanelBotones, true);

        } catch (Exception ex) {
            Logger.getLogger(DialogPadre.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.modificar(valores); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        jasper.runreporte(new String[]{"numero", "ruc"}, new String[]{txtCodigo.getText(), txtCodVoluntario.getText()}, "Cobros", true);
        //jasper.runReporteExcel(new String[] {"numero","ruc"}, new String[] {txtNroFactura.getText(),txtRUC.getText()}, "FacturaCompra");
    }

    @Override
    public void eliminar(String[] valores) {
        super.eliminar(valores);
        modoediciondetalle = "3";
        txtCodigo.setText(valores[0]);
        txtCodVoluntario.setText(valores[1]);
        modoediciondetalle = "1";
        if (con.borrarRegistro("cobros", "idCobros = '" + valores[0] + "' AND idVoluntarios = '" + valores[1] + "'")) {
            JOptionPane.showMessageDialog(this,"El cobro ha sido eliminado");
            con.commit();
            this.dispose();
        }
    }

    public void estadoedicionencab(boolean valor) {
        comJPanel.setEnabled(PanelEncabezado, valor);
        this.comboTipoFactura.setEnabled(valor);

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
        if (txtCodVoluntario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un voluntario");
            return false;
        }
        if (modoedicion.equals("1")) {
            if (comJSQL.existeRegistro("select * from cobros where idCobros = '" + txtCodigo.getText() + "' and idVoluntarios = '" + txtCodVoluntario.getText() + "'")) {
                JOptionPane.showMessageDialog(this, "Error. El registro ya existe");
                return false;
            }
        }

        String[] ve_Ordenado = new String[]{txtCodigo.getText(), txtCodVoluntario.getText(), hoy, txtMonto.getText(), txtCodSocio.getText(), comJCombox.getComboxString(comboTipoFactura, 1), txtCodApertura.getText()};
        if (guardarToDataBase("abm_cobros", ve_Ordenado, modoedicion)) {
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
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboTipoFactura = new javax.swing.JComboBox();
        txtRazonSocial = new javax.swing.JTextField();
        btnBuscarProveedor = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        dateFecha = new com.toedter.calendar.JDateChooser();
        txtCodVoluntario = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtCodSocio = new javax.swing.JTextField();
        txtRazonSocial1 = new javax.swing.JTextField();
        btnBuscarProveedor1 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtCodApertura = new javax.swing.JTextField();
        PanelBotones = new javax.swing.JPanel();
        BotonConfirmar = new javax.swing.JButton();
        BotonCancelar = new javax.swing.JButton();
        BotonNuevo = new javax.swing.JButton();
        BotonEliminar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setForeground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cobros");

        PanelEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        PanelEncabezado.setForeground(new java.awt.Color(240, 240, 240));

        jLabel2.setText("Código:");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel4.setText("Fecha");

        jLabel3.setText("Condicion Pago:");

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

        txtRazonSocial.setEditable(false);

        btnBuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProveedorActionPerformed(evt);
            }
        });

        jLabel5.setText("Voluntario");

        dateFecha.setDateFormatString("dd-MM-yyyy");
        dateFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateFechaKeyPressed(evt);
            }
        });

        txtCodVoluntario.setEditable(false);
        txtCodVoluntario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodVoluntarioKeyPressed(evt);
            }
        });

        jLabel14.setText("Monto:");

        jLabel15.setText("Socio Protector:");

        txtCodSocio.setEditable(false);
        txtCodSocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodSocioKeyPressed(evt);
            }
        });

        txtRazonSocial1.setEditable(false);

        btnBuscarProveedor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProveedor1ActionPerformed(evt);
            }
        });

        jLabel16.setText("Cod. Apertura:");

        txtCodApertura.setEditable(false);
        txtCodApertura.setEnabled(false);

        javax.swing.GroupLayout PanelEncabezadoLayout = new javax.swing.GroupLayout(PanelEncabezado);
        PanelEncabezado.setLayout(PanelEncabezadoLayout);
        PanelEncabezadoLayout.setHorizontalGroup(
            PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(24, 24, 24)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                        .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigo)
                            .addComponent(dateFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodVoluntario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMonto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscarProveedor))
                            .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRazonSocial1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscarProveedor1))))
                    .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                        .addComponent(comboTipoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodApertura, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        PanelEncabezadoLayout.setVerticalGroup(
            PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(txtCodVoluntario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(dateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtRazonSocial1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)
                        .addComponent(txtCodSocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscarProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboTipoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtCodApertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jLabel11.setIcon(new javax.swing.ImageIcon("J:\\Garra\\garrita.png")); // NOI18N
        jLabel11.setText("jLabel4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PanelBotones, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PanelEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(PanelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addComponent(PanelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProveedorActionPerformed
        if (comLlamadaBuscadorComun2.BuscadorjDialog("select idVoluntarios, concat(Nombre,' ',Apellido) as Voluntario from voluntarios", new String[]{"Código", "Nombre"}, "idVoluntarios", new String[][]{{"idVoluntarios", "0"}}, new int[]{}, new int[]{500, 300}, new int[]{100, 120}, "Nombre")) {
            comLlamadaBuscadorComun2.BuscadorjDialogetStringObjAlt(new Object[]{txtCodVoluntario, txtRazonSocial}, new int[]{1, 2});
            modoediciondetalle = "1";
            comboTipoFactura.grabFocus();
        }

    }//GEN-LAST:event_btnBuscarProveedorActionPerformed

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
        this.NroTimbrado = this.txtCodVoluntario.getText();
        eliminar(new String[]{NroFactura, NroTimbrado});
    }//GEN-LAST:event_BotonEliminarActionPerformed

    private void comboTipoFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoFacturaActionPerformed

    }//GEN-LAST:event_comboTipoFacturaActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        BotonCancelarActionPerformed(null);
    }//GEN-LAST:event_formWindowClosing

    private void txtCodVoluntarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodVoluntarioKeyPressed
        if (evt.getKeyCode() == 10) {
            btnBuscarProveedorActionPerformed(null);
        }
    }//GEN-LAST:event_txtCodVoluntarioKeyPressed

    private void comboTipoFacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboTipoFacturaKeyPressed
        if (evt.getKeyCode() == 10) {
            dateFecha.grabFocus();
        }
    }//GEN-LAST:event_comboTipoFacturaKeyPressed

    private void dateFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateFechaKeyPressed
        if (evt.getKeyCode() == 10) {

        }
    }//GEN-LAST:event_dateFechaKeyPressed

    private void txtCodSocioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodSocioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodSocioKeyPressed

    private void btnBuscarProveedor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProveedor1ActionPerformed
               if (comLlamadaBuscadorComun2.BuscadorjDialog("select idSocioProtector, concat(Nombre,' ',Apellido) as Socio from SocioProtector", new String[]{"Código", "Nombre"}, "idSocioProtector", new String[][]{{"idSocioProtector", "0"}}, new int[]{}, new int[]{500, 300}, new int[]{100, 120}, "Nombre")) {
            comLlamadaBuscadorComun2.BuscadorjDialogetStringObjAlt(new Object[]{txtCodSocio, txtRazonSocial1}, new int[]{1, 2});
            modoediciondetalle = "1";
            comboTipoFactura.grabFocus();
        }
    }//GEN-LAST:event_btnBuscarProveedor1ActionPerformed

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
                Cobros dialog = new Cobros(new javax.swing.JFrame(), true, "");
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
    private javax.swing.JButton btnBuscarProveedor;
    private javax.swing.JButton btnBuscarProveedor1;
    private javax.swing.JComboBox comboTipoFactura;
    private com.toedter.calendar.JDateChooser dateFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtCodApertura;
    private javax.swing.JTextField txtCodSocio;
    private javax.swing.JTextField txtCodVoluntario;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JTextField txtRazonSocial1;
    // End of variables declaration//GEN-END:variables
}
