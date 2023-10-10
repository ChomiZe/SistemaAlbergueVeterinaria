package Frames;

import comunes.Conexion;
import comunes.comJVarios;
import comunes.reportes;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class DialogPadre extends javax.swing.JDialog {

    public Conexion con = new Conexion();
    public String modoedicion="";
    public SimpleDateFormat fechaparadb = new SimpleDateFormat("yyyy-MM-dd");
    public SimpleDateFormat fechaJFT = new SimpleDateFormat("dd/MM/yyyy");
    public reportes jasper = new reportes();
    
    /**
     * Creates new form Padre
     * @param parent
     * @param modal
     */
    public DialogPadre(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Garra - ");
//        java.net.URL icon;
//        icon = FormPrincipal.class.getResource(System.getProperty("file.separator")+"icons"+System.getProperty("file.separator")+"logo5.png");
        
        this.setIconImage (new ImageIcon(System.getProperty("user.dir")+System.getProperty("file.separator")+
        "src"+System.getProperty("file.separator")+"icons"+System.getProperty("file.separator")+"logo5.png").getImage());
        con.estaConectado();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
//                con.cierraConexion();
                try {
                    this.finalize();
                } catch (Throwable ex) {
                    Logger.getLogger(DialogPadre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
//        skin();
    }
    
    public void skin() {
        String skin="";
        try {
//            skin="com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
//            skin="javax.swing.plaf.metal.MetalLookAndFeel";
//            skin="com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            skin="com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
//            skin="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
//            skin="com.seaglasslookandfeel.SeaGlassLookAndFeel";
            UIManager.setLookAndFeel(skin);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
    }
    
    public void escape(final JButton boton) {
        KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if (boton.isEnabled()) {
                    boton.doClick();
                }
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
    }
    
    public void f1(final JButton boton) {
        KeyStroke f1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1,0,false);
        AbstractAction f1Action = new AbstractAction() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if (boton.isEnabled()) {
                    boton.doClick();
                }
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(f1, "F1");
        getRootPane().getActionMap().put("F1", f1Action);
    }
    
    public void f2(final JButton boton) {
        KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2,0,false);
        AbstractAction f2Action = new AbstractAction() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if (boton.isEnabled()) {
                    boton.doClick();
                }
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(f2, "F2");
        getRootPane().getActionMap().put("F2", f2Action);
    }
    
    public void f3(final JButton boton) {
        KeyStroke f3 = KeyStroke.getKeyStroke(KeyEvent.VK_F3,0,false);
        AbstractAction f3Action = new AbstractAction() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if (boton.isEnabled()) {
                    boton.doClick();
                }
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(f3, "F3");
        getRootPane().getActionMap().put("F3", f3Action);
    }
    
    public void del(final JButton boton) {
        KeyStroke del = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0,false);
        AbstractAction delAction = new AbstractAction() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if (boton.isEnabled()) {
                    boton.doClick();
                }
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(del, "DELETE");
        getRootPane().getActionMap().put("DELETE", delAction);
    }
    
    public void restablecer() {
        
    }
    
//    public void imprimir(String[] parametros, String[] nombres, String reporte, boolean mostrar) {
//        
//    }
    public void imprimir() {
        
    }
    
    public void imprimir(String[]valores) {
        
    }
    
    public String[] modificar(String[]valores){
        return valores;
    }
    
    public String[] ver(String[] valores){
        return valores;
    }
    
    public void nuevo(){
        
    }
    
    public void eliminar(String[] valores){

    }
    public void anular(String[] valores){

    }
    

     /**
     * Del actual que tenemos como titulo permite colocarle un agregado como prefijo. Por ejemplo, si dice el titulo "formulario poderoso" y luego le agregamos " momia" entonces será "formulario poderoso momia"
     * @param moreNewTitle
     */
    public void AddTitleExtendPre(String moreNewTitle) {
        setTitle(moreNewTitle+getTitle());
    }
    /**
     * Del actual que tenemos como titulo permite colocarle un agregado como prefijo. Por ejemplo, si dice el titulo "formulario poderoso" y luego le agregamos " momia" entonces será "formulario poderoso momia"
     * @param moreNewTitle
     */
    public void AddTitleExtendSuf(String moreNewTitle) {
        setTitle(getTitle()+moreNewTitle);
    }
    /**
     * Metodo con retorno booleano que permite guardar o insertar, modificar o elminar registros en una base de datos segun el abm
     * @param abm
     * @param ve_Ordenado
     * @param modal
     * @return 
     */
    public boolean guardarToDataBase(String abm, String[]ve_Ordenado, String modal) {
        return con.abmRegistro(abm, comJVarios.ChangeToMySql(ve_Ordenado),modal);
    }
    /**
     * Metodo con retorno booleano que permite guardar o insertar, modificar o elminar registros
     * en una base de datos segun el abm. Solamente le hay que dar los espacios cuando no va a
     * ser entre comas, especialmente cuando se llama a un objeto que contiene los datos.
     * Solamente soporta las modalidades estandar de 1, 2 y 3. Para otros casos hay que hacerlo manualmente.
     * @param abm
     * @param valores
     * @param modal
     * @return 
     */
    public boolean guardarToDataBaseConRestablecer(String abm, String[]valores, String modal) {
        if (modal=="3") {
            if(!comJVarios.respuesta(modoedicion))
                return true;
        }
        boolean b = con.abmRegistro(abm, comJVarios.ChangeToMySql(valores),modal);
        if (b) {
            restablecer();
        }
        return b;
    }

    public boolean guardarToDataBaseSinRestablecer(String abm, String[]valores, String modal) {
        if (modal=="3") {
            if(!comJVarios.respuesta(modoedicion))
                return true;
        }
        boolean b = con.abmRegistro(abm, comJVarios.ChangeToMySql(valores),modal);
        return b;
    }
    
    public Date ConvertFechaDate(JFormattedTextField text) {
        Date fecha = null;
        try {
            fecha = fechaparadb.parse(fechaparadb.format(fechaJFT.parse(text.getText())));
        } catch (ParseException ex) {
            Logger.getLogger(DialogPadre.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha;
    }
    
    public String ConvertFechaString(JFormattedTextField text) {
        String fecha = null;
        try {
            fecha = fechaparadb.format(fechaJFT.parse(text.getText()));
        } catch (ParseException ex) {
            Logger.getLogger(DialogPadre.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(DialogPadre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DialogPadre dialog = new DialogPadre(new javax.swing.JFrame(), true);
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
    // End of variables declaration//GEN-END:variables
}
