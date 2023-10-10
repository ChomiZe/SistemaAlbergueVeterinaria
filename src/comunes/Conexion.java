package comunes;

import java.awt.Component;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;


public class Conexion {
    
    String error;
    
    //public final String CONTROLADOR_JDBC = "org.postgresql.Driver"; //Para la comConexion con postgres
    public final String CONTROLADOR_JDBC = "org.gjt.mm.mysql.Driver"; //Para la comConexion con mysql
    
    /**
     * Vector doble que recopila la informacion de la carpeta local del fichero de configure.xls o cualquier otro nombre que se le atribuya, <br><br>
     * en la cual se guarda las configuraciones correspondientes para que se pueda conectar a una base de datos.<hr>
     * Tener mucho cuidado a la hora de manipular los datos residentes en el archivo externo porque de otro modo podria provocar el mal funcionamiento del programa y tambien de la comConexion.
     */
  
//    private static String usuario=Main.Configure[0][1];
//    private static String contra=Main.Configure[0][2];
//    private static String importancia="1";
//    private String baseActiva=Configure[0][3];

    //private static String computadoraResidente = comJFile.getStringFromFileTXT(comJFile.RUTA_LOCAL+System.getProperty("file.separator")+"maquina.txt"); //computadora residente
     private static String computadoraResidente = "127.0.0.1";
    //private static String computadoraResidente = "192.168.1.6";
//    private static String computadoraResidente = "192.168.1.101";
//   private static String computadoraResidente = "127.0.0.1";
    private static String usuario="root";
    //private static String usuario;
    private static String usuario_sis;
    private static String rol;
//    private static String contra= "123456";
  private static String contra= "123456";
//    private static String contra;
    private String baseActiva="garra";
    private static String puerto = "3306"; //
    private static String importancia="1";
    private static String apertura;
    private static String caja;
    /** La comConexion con la base de datos */
    private Connection conexion = null;
    public PreparedStatement ps;

    Statement stmt;
    ResultSet rs;
    public String codigo;

    public void setUsuario(String usuario) {
        Conexion.usuario=usuario;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario_sis(String usuario_sis) {
        Conexion.usuario_sis=usuario_sis;
    }
    public String getUsuario_sis() {
        return usuario_sis;
    }
    public void setRol(String rol) {
        Conexion.rol=rol;
    }
    public String getRol() {
        return rol;
    }
    public String getContra() {
        return contra;
    }
    public void setContra(String contra) {
        Conexion.contra = contra;
    }
    public void setImportancia(String importancia) {
        Conexion.importancia = importancia;
    }
    public String getImportancia() {
        return importancia;
    }
    
    public String getHost(){
        return computadoraResidente;
    }
    
    public void setApertura() {
        try {
            apertura = maximoValorCampo("Aperturas_v", "idApertura", "FechaCierre is null")+"";
        }
        catch (Exception e) { JOptionPane.showMessageDialog(null, e); }
        
    }
    public String getApertura(){
        return apertura;
    }
    
    public Connection getConexion(){
        return conexion;
    }

    /** Creates a new instance of ConeccionBD */
    public Conexion() {
        //System.out.println("jdbc:mysql://"+computadoraResidente+"/"+baseActiva+","+usuario+","+contra);
    }
    
    public void autocommit(boolean valor) {
        try {
            conexion.setAutoCommit(valor);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void commit() {
        try {
            conexion.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void rollback() {
        try {
            conexion.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean estaConectado() {
        if (conexion != null) {
            return true;
        }
        try {
           // Se registra el Driver de MySQL
           // DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Class.forName( CONTROLADOR_JDBC );
            try {
                // Se obtiene una comConexion con la base de datos. Hay que
                // cambiar el usuario "usuario" y la clave "" por las
                // adecuadas a la base de datos que estemos usando.
                //comConexion  = DriverManager.getConnection("jdbc:postgresql://"top 100 songs from the 90s and listen to them all while writing a novel and watching+computadoraResidente+":"+puerto+"/"+baseActiva, usuario, contra);
                // System.out.println("jdbc:mysql://"+computadoraResidente+"/"+baseActiva+","+usuario+","+contra);
                if (getUsuario() != null) {
                    conexion  = DriverManager.getConnection("jdbc:mysql://"+computadoraResidente+"/"+getBaseActiva(),getUsuario(),getContra());
                    conexion.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            //comConexion = DriverManager.getConnection(
            // "jdbc:mysql://localhost/supsys","root","");
            // "jdbc:mysql://localhost/rrhh","root","");
            System.out.println("Conexion OK");
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            conexion=null;
            return false;
        }
        return true;
    }

    public boolean borrarRegistro(String tabla, String condicion) {
        try {
            Statement s = conexion.createStatement();
	    Object[] opciones={"Sí","No"};
	    //Dialogo modal SI_NO
	    int ret=JOptionPane.showOptionDialog(null,"¿Desea borrar? ","Pregunta",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opciones,opciones[0]);
	    //Si la opcion escogida es Si
	    if(ret==JOptionPane.YES_OPTION) {
                s.executeUpdate("delete from "+tabla+" where "+condicion);
               System.out.println("delete from "+tabla+" where "+condicion);
  
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1339) {
                JOptionPane.showMessageDialog(null, "Este registro no se puede alterar", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Ocurrió un error\n"+e.getMessage() , "Atención", JOptionPane.WARNING_MESSAGE);
                System.out.println("delete from "+tabla+" where "+condicion);   
            }
            return false;
        }
        return true;
        
    }

    public boolean borrarRegistroSinPreguntar(String tabla, String condicion){
        try {
            // Se crea un Statement, para realizar la consulta
            Statement s = conexion.createStatement();
            System.out.println("delete from "+tabla+" where "+condicion);
            s.executeUpdate("delete from "+tabla+" where "+condicion);
        } catch (SQLException e){
            return false;
        }
        return true;
    }
    
    public PreparedStatement prepararStatement(String sql) {
        try {
             ps = conexion.prepareStatement(sql);
        }
        catch (SQLException e) {}
        return ps;
    }
    
    public boolean executeQuery(String sql) {
        try {
            // Se crea un Statement, para realizar la consulta
            Statement s = conexion.createStatement();
            s.executeUpdate(sql);
            System.out.println(sql);
        } catch (SQLException e) {
            System.err.println(e+" "+sql);
            return false;
        }
        
        return true;
    }
    
    public boolean executeQuerySinException(String sql) {
        try {
            // Se crea un Statement, para realizar la consulta
            Statement s = conexion.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    public void ExecuteQuery(String sql) {
        try {
            // Se crea un Statement, para realizar la consulta
            Statement s = conexion.createStatement();
            s.executeQuery(sql);
            System.out.println("ok. "+sql);
            
        } catch (SQLException e) {System.err.println(e); }
            
    }
    public void ExecuteQueryUpdate(String sql) {
        try {
            // Se crea un Statement, para realizar la consulta
            Statement s = conexion.createStatement();
            s.executeUpdate(sql);
            System.out.println("ok. "+sql);
            
        } catch (SQLException e) {System.err.println(e); }
            
    }
    
    public boolean abmRegistro(String abm_name, String valores, String modalidad){
        try {
            Statement s = conexion.createStatement();
            String sSQL = "call "+abm_name+"("+modalidad+","+valores+")";
            System.out.println(sSQL);
            // Se realiza la consulta.
            s.executeQuery(sSQL);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1339) {
                JOptionPane.showMessageDialog(null, "Este registro no se puede alterar", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Ocurrió un error\n"+e.getMessage() , "Atención", JOptionPane.WARNING_MESSAGE);
            }
            return false;
        }
        return true;
    }

    public boolean insertarRegistro(String tabla, String campos, String valores){
        try {
            Statement s = conexion.createStatement();
            s.executeUpdate("insert into "+tabla+" ("+campos+") values ("+valores+")");
            System.out.println("insert into "+tabla+" ("+campos+") values ("+valores+")");
        } catch (SQLException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Insertar Registro. Ocurrió un error\n"+e.getMessage() , "Atención",
            JOptionPane.INFORMATION_MESSAGE);
            System.out.println("error: insert into "+tabla+" ("+campos+") values ("+valores+")");
            return false;
        }
        return true;
    }
    
    public boolean insertarRegistro(String tabla, String campos, String valores[]){
        try {
            String values = "";
            String c;
            for (int i=0; i<valores.length; i++) {
                if (i != valores.length-1) {
                    c = ","; }
                else { c = ""; }
                values += "'"+valores[i]+"'"+c;
            }
            System.out.println("insert into "+tabla+" ("+campos+") values ("+values+")");
            Statement s = conexion.createStatement();
            s.executeUpdate("insert into "+tabla+" ("+campos+") values ("+values+")");
            
        } catch (SQLException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Insertar Registro. Ocurrió un error\n"+e.getMessage() , "Atención",
            JOptionPane.INFORMATION_MESSAGE);
            System.out.println("error: insert into "+tabla+" ("+campos+") values ("+Arrays.toString(valores)+")");
            return false;
        }
        return true;
    }

     public boolean actualizarRegistro(String tabla, String campos, String criterio){
        int resultado;
        try {
            Statement s = conexion.createStatement();
            resultado = s.executeUpdate("update "+tabla+" set "+campos+" where " +criterio);
            System.out.println("update "+tabla+" set "+campos+" where " +criterio);
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Error: update "+tabla+" set "+campos+" where " +criterio);
            JOptionPane.showMessageDialog(null, "Ocurrio Un error\n"+e.getMessage() , "Atencion",
            JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    public ResultSet dameLista(String campos, String tabla, String condicion) {
        ResultSet rs = null;
        try {
            Statement s = conexion.createStatement();
            rs = s.executeQuery("select "+campos+" from "+tabla+" "+condicion);
            System.out.println("select "+campos+" from "+tabla+" "+condicion);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1142) {
                JOptionPane.showMessageDialog(null, "No tiene permisos para acceder a esta sección", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Ocurrió un error\n "+e.getMessage(), "Atención", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("select "+campos+" from "+tabla+" "+condicion);
            }
        }
        return rs;
    }

    public ResultSet dameLista(String sql) {
        ResultSet rs = null;
        try {
            Statement s = conexion.createStatement();
            System.out.println(sql);
            rs = s.executeQuery(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error\n"+e.getMessage(), "Atención",
            JOptionPane.INFORMATION_MESSAGE);
        }
        return rs;
    }
    
    public ResultSet dameRS(String sql) {
        ResultSet rs = null;
        try {
            Statement s = conexion.createStatement();
            System.out.println(sql);
            rs = s.executeQuery(sql);
            if (!rs.next()) {
                return null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error\n"+e.getMessage(), "Atención",
            JOptionPane.INFORMATION_MESSAGE);
        }
        return rs;
    }
    
    public String dameInfoTable(String tabla, int lugar) {
        ResultSet rs;
        String valor = null;
        try {
            Statement s = conexion.createStatement();
            String sql = "show table status like '"+tabla+"'";
            rs = s.executeQuery(sql);
            System.out.println(sql);
            if (rs.next()) 
                valor = rs.getString(lugar);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error\n"+e.getMessage(), "Atención",
            JOptionPane.INFORMATION_MESSAGE);
        }
        return valor;
    }
    
    public Date dameFecha() {
        ResultSet rs;
        Date fecha = new Date();
        try {
            Statement s = conexion.createStatement();
            rs = s.executeQuery("select current_timestamp");
            if (rs.next())
                fecha = rs.getDate(1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error\n"+e.getMessage(), "Atención",JOptionPane.INFORMATION_MESSAGE);
        }
        return fecha;
    }
    
    public Date damePrimerDia() {
        ResultSet rs;
        Date fecha = new Date();
        try {
            Statement s = conexion.createStatement();
            String sql = "select date_format(current_date, '%Y-%m-01') ";
            rs = s.executeQuery(sql);
            System.out.println(sql);
            if (rs.next())
                fecha = rs.getDate(1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error\n"+e.getMessage(), "Atención",JOptionPane.INFORMATION_MESSAGE);
        }
        return fecha;
    }
    
    public Date dameHora() {
        ResultSet rs;
        Date hora = new Date();
        try {
            Statement s = conexion.createStatement();
            rs = s.executeQuery("select current_timestamp");
            if (rs.next())
                hora = rs.getTime(1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error\n"+e.getMessage(), "Atención",JOptionPane.INFORMATION_MESSAGE);
        }
        return hora;
    }
    
    
    
    /**
     * Carga un conjunto de combox
     * @param sql
     * @param combo
     */
    public void cargarCombo(String sql, JComboBox[]combo) {
        for (int i = 0; i < combo.length; i++) {
            cargarCombo(sql, combo[i]);
        }
    }
    
    /**
     * Carga un combox a partir de la consulta sql
     * @param sql
     * @param combo
     */
    public void cargarCombo(String sql, final JComboBox combo) {
        ResultSet rs = null;
        int contar = 0;
        combo.removeAllItems();
        
        try {
            Statement s = conexion.createStatement();
            rs = s.executeQuery(sql);
            System.out.println(sql);
            while (rs.next()) {
                String columnas[] = new String[2];
		columnas[0] = rs.getString(1);
		columnas[1] = rs.getString(2);
		combo.addItem(columnas);
                contar++;
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error: "+e.toString() , "Atención",
            JOptionPane.INFORMATION_MESSAGE);
            return;
        }finally{
//            try {
//                rs.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(comConexion.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        if (contar > 0) {  
            combo.setRenderer (new ListCellRenderer() /*DefaultListCellRenderer()*/ {
                //REVISAR
                @Override
                public Component getListCellRendererComponent (JList list, Object value, int index,boolean isSelected, boolean cellHasFocus) {
                    try {
                        JComponent item = new JLabel (((String[]) value)[1]);
                        if (isSelected) {
                            item.setOpaque(true);
                            item.setBackground(list.getSelectionBackground());
                            item.setForeground(list.getSelectionForeground());
                        }
                        return item;
                    } catch(Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al cargar" , "Atención", JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }
                }
            });
        }
        else {
            //en caso de error probar lo siguiente
            //com_horarios.removeAllItems(); puede que no lleve porque siempre se le pasa datos vacios
            //com_horarios.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"",""}));
            //fin caso de error
            
            //prototipo de error 1
            combo.addItem(new String[]{"",""});
            combo.setRenderer (new BasicComboBoxRenderer() /*DefaultListCellRenderer()*/ {
                @Override
                public Component getListCellRendererComponent (JList list, Object value, int index,boolean isSelected, boolean cellHasFocus) {
                    try {
                        JComponent item = new JMenuItem (((String[]) value)[1]);
                        if (isSelected) {
                            item.setOpaque(true);
                            item.setBackground(list.getSelectionBackground());
                            item.setForeground(list.getSelectionForeground());
                        }
                        return item;
                     } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Ocurrió un error" , "Atención",
                        JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }
                }
            });
        }
        //fin modificacion
        
    }
    
    public void cargarCombo(Object o[], final JComboBox combo, int index) {
        ResultSet rs;
        int contar = 0;
        
        Object columnas[] = new String[2];
        columnas[0] = o[0];
        columnas[1] = o[1];
        combo.insertItemAt(columnas[1], index);
        contar++;
    
        if (contar > 0) {  
            combo.setRenderer (new ListCellRenderer() /*DefaultListCellRenderer()*/ {
                //REVISAR
                @Override
                public Component getListCellRendererComponent (JList list, Object value, int index,boolean isSelected, boolean cellHasFocus) {
                    try {
                        //JComponent item = new JLabel (((String[]) value)[1]);
                        JTextField item = new JTextField (((String[]) value)[1]);
                        item.setEditable(false);
                        if (isSelected) {
                            item.setOpaque(true);
                            item.setBackground(list.getSelectionBackground());
                            item.setForeground(list.getSelectionForeground());
                        }
                        return item;
                    } catch(Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al cargar" , "Atención", JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }
                }
            });
        }
        else {
            combo.addItem(new String[]{"",""});
            combo.setRenderer (new BasicComboBoxRenderer() /*DefaultListCellRenderer()*/ {
                @Override
                public Component getListCellRendererComponent (JList list, Object value, int index,boolean isSelected, boolean cellHasFocus) {
                    try {
                        //JComponent item = new JMenuItem (((String[]) value)[1]);
                        JTextField item = new JTextField (((String[]) value)[1]);
                        item.setEditable(false);
                        if (isSelected) {
                            item.setOpaque(true);
                            item.setBackground(list.getSelectionBackground());
                            item.setForeground(list.getSelectionForeground());
                        }
                        return item;
                     } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Ocurrió un error" , "Atención",
                        JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }
                }
            });
        }
    }
    
    /**
     * Verifica si existe un usuario determinado en la base de datos
     * @param usu
     * @param clave
     * @return
     */
    public boolean existeUsuarioClave(String usu, String clave){
        ResultSet rs;
        try {
            System.out.println("select count(1) from usuarios where usuario = '"+usu+"' and contrasenha = md5('"+clave+"')");
            Statement s = conexion.createStatement();
            rs = s.executeQuery("select count(1) from usuarios where usuario = '"+usu+"' and contrasenha = md5('"+clave+"')");
               
            //rs = s.executeQuery("select count(1) from usuario where usuario = 'usu' and clave = 'clave'");

            rs.next();
            int cantidad = rs.getInt(1);

            if (cantidad == 0) { {
                return false;
            } }
            else { {
                return true;
            } }
        } catch(SQLException e) {
            return false;
        }

    }
    
    /** Cierra la comConexion con la base de datos */
    public void cierraConexion() {
//        if (conexion != null) {
//            try {
//                conexion.close();
//                System.out.println("cierra conexion");
//            } catch (SQLException e) { }
//        }
    }
    /**
     * Cuenta la cantidad de columnas dentro de una consulta
     * @param sql
     * @return
     */
    public int countColumn(String sql) {
        ResultSet rs = dameLista(sql);
        int c=0;
        try {
            while (rs.next()) {
                for (int i = 0; i < 100; i++) {
                    rs.getString(i+1);
                    c+=1;
                }
            }
        } catch (SQLException e) { }
        return c;
    }
    /**
     * Cuenta la cantidad de filas que tiene una consulta sql
     * @param sql
     * @return
     */
    public int countFila(String sql) {
        ResultSet rs = dameLista(sql);
        int c=0;
        try {
            while (rs.next()) {
              c+=1;
            }
        } catch (SQLException e) { }
        return c;
    }

    public Integer maximoValorCampo(String tabla, String campo, String where) throws Exception {
        String sql = "SELECT ifnull(MAX("+campo+"),0) AS maximo FROM " +tabla+where;
        int returnValue = 0;
        try {
            ResultSet rs;
            Statement s = conexion.createStatement();
            rs = s.executeQuery(sql);
            System.out.println(sql);

            if (rs.next()) {
                returnValue = rs.getInt("maximo");
                return 1+returnValue;
            } else {
                JOptionPane.showMessageDialog(null, "Ocurrió un error" , "Atención Error ID",
                JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
            
        }
        catch (SQLException | HeadlessException e) { return null; }
    }

    /**
     * @return the baseActiva
     */
    public String getBaseActiva() {
        return baseActiva;
    }

}