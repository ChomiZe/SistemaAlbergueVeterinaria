/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package comunes;

import com.toedter.calendar.JDateChooser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author pcsproject
 */
public class comJVarios {
    /**
     * Retorna verdadero si el usuario ha presionado click si o ok, de otro modo es false
     * @param modalidad
     * @return
     */
    static String[] veDato;
    static String sqlxyz="";
    
    public static boolean respuesta(String modalidad)
    {
         String pregunta="";
            if(modalidad.equals("1"))
                pregunta="Guardar";
            else
                if(modalidad.equals("2"))
                    pregunta="Modificar";
                else
                    if(modalidad.equals("3"))
                        pregunta="Eliminar";
                    else
                        if(modalidad.equals("4"))
                            pregunta="Guardar los cambios efectuados";
                        else
                            if(modalidad.equals("5"))
                                pregunta="Anular";
                                  if(modalidad.equals("6"))
                                        pregunta="Efectuar Cambios en Pedido ya Ordenado";

             //0 es aceptar y 2 es cancelar
            int nroopcion=JOptionPane.showConfirmDialog(null, "Estas seguro que desea "+pregunta+"?", "Confirmacion", 2, 1);
            if(nroopcion!=0)
                return false;

            return true;
    }
    /**
     * Cambia un vector de string a datos que puede reconocer el postgres
     * @param ve_aux
     * @return
     */
    public static String ChangeToMySql(String[] ve_aux)
    {
        //Sirve para crear agrupar los cuadro de texto y construirlos en un todo y retornarlo como una sola cadena
        String str_aux="'";
        for(int x=0;x<ve_aux.length;x++)
        {
            str_aux=str_aux+ve_aux[x];

            if(x+1<ve_aux.length)
                str_aux=str_aux+"','";
            else
                str_aux=str_aux+"'";
        }
        return str_aux;
    }

       public static String[] damefecha () {
        String fecha[] = new String[7];
        try {
            ResultSet rs;            
            Conexion c = new Conexion();
            c.estaConectado();
            sqlxyz = "select CURDATE(), DATE_FORMAT(CURDATE(),'%d-%m-%Y'), CONCAT(YEAR(CURRENT_DATE()),'-01-01'), DATE_FORMAT(CURDATE()- INTERVAL DAYOFYEAR(CURDATE()) DAY,'%d-%m-%Y'), DATE_FORMAT(DATE_SUB(curdate(),INTERVAL (DAY(curdate())-1) DAY),'%d-%m-%Y'),DATE_FORMAT(curdate(),'%d/%m/%Y'),current_time() ";
            rs = c.dameLista(sqlxyz);
            if(rs.next()) {
                fecha[0] = rs.getString(1);
                fecha[1] = rs.getString(2);
                fecha[2] = rs.getString(3);
                fecha[3] = rs.getString(4);
                fecha[4] = rs.getString(5);
                fecha[5] = rs.getString(6);
                fecha[6] = rs.getString(7);
                
            }
            else {
                fecha[0] = new String();
                fecha[1] = new String();
                fecha[2] = new String();
            }
        }
        catch (SQLException ex ) {Logger.getLogger(comJVarios.class.getName()).log(Level.SEVERE, null, ex); }
        return fecha;
    }
    
    public static String EliminaCaracteres(String s_cadena, String s_caracteres) {
        String nueva_cadena = "";
        Character caracter;
        boolean valido;

        /* Va recorriendo la cadena s_cadena y copia a la cadena que va a regresar,
           sólo los caracteres que no estén en la cadena s_caracteres */
        for (int i=0; i<s_cadena.length(); i++) {
            valido = true;
            for (int j=0; j<s_caracteres.length(); j++) {
                caracter = s_caracteres.charAt(j);

                if (s_cadena.charAt(i) == caracter) {
                    valido = false;
                    break;
                }
            }
            if (valido)
                nueva_cadena += s_cadena.charAt(i);
        }
        return nueva_cadena;
    }

    public static void recuperaDatos(String tabla, String idcol, String iddata, Object[]OrdenDatos) throws Exception {
            ResultSet rs;
            Conexion c = new Conexion();
            c.estaConectado();

            sqlxyz = "select * from "+tabla+" where "+idcol+" = "+ iddata;
            System.out.println(sqlxyz);
            try {
                int numcol = c.countColumn(sqlxyz);
                veDato = new String[numcol];
                rs = c.dameLista(sqlxyz);
                if(rs.next())
                {
                  for (int i = 0; i < numcol; i++)
                        veDato[i]=rs.getString(i+1);
                }
             } catch (Exception e) {
                 System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Hubo errores al buscar", "Errores", 0);
                return;
            }                  
            //recuperando datos
            JTextField jText;
            JComboBox jCom;
            JFormattedTextField jFT;
            JDateChooser jDat;
            JCheckBox jchk;
            JLabel jEtiq;

            for (int i = 0; i < veDato.length; i++) {
                if (OrdenDatos[i].getClass().equals(JTextField.class)) {
                    jText=(JTextField)OrdenDatos[i];
                    jText.setText(veDato[i]);
                }
                else
                    if (OrdenDatos[i].getClass().equals(JComboBox.class)) {
                        jCom=(JComboBox)OrdenDatos[i];
                        comJCombox.setComboxPosition(jCom,veDato[i],1);
                    }
                    else
                        if (OrdenDatos[i].getClass().equals(JFormattedTextField.class)) {
                            jFT=(JFormattedTextField)OrdenDatos[i];
                            jFT.setText(veDato[i]);
                        }
                        else
                            if (OrdenDatos[i].getClass().equals(JLabel.class))
                            {
                                jEtiq=(JLabel)OrdenDatos[i];
                                jEtiq.setText(veDato[i]);
                            }
                            else
                                if (OrdenDatos[i].getClass().equals(JDateChooser.class))
                                {
                                    jDat=(JDateChooser)OrdenDatos[i];
                                    comJDateChooser.setDatejDateChooser(jDat, veDato[i]);
                                }
                                 else
                                    if (OrdenDatos[i].getClass().equals(JCheckBox.class))
                                        {
                                        String selchk = veDato[i];
                                        System.out.print(selchk);
                                        jchk=(JCheckBox)OrdenDatos[i];
                                        if (selchk.equals("1")) {
                                            jchk.setSelected(true);
                                        } else {
                                            jchk.setSelected(false);
                                        }
                                    }
            }
            c.cierraConexion();
        }
    
    public static void recuperaDatos(String campos, String tabla, String idcol, String iddata, Object[]OrdenDatos) throws Exception {
        ResultSet rs;
        Conexion c = new Conexion();
        c.estaConectado();

        sqlxyz = "select "+campos+" from "+tabla+" where "+idcol+" = "+ iddata;

        int numcol = c.countColumn(sqlxyz);
        veDato = new String[numcol];
        try {
            rs = c.dameLista(sqlxyz);
            if(rs.next()) {
              for (int i = 0; i < numcol; i++)
                    veDato[i]=rs.getString(i+1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Hubo errores al buscar", "Errores", 0);
            System.out.println("error recuperadatos: "+sqlxyz);
            return;
        }                  
        //recuperando datos
        JTextField jText;
        JComboBox jCom;
        JFormattedTextField jFT;
        JDateChooser jDat;
        JCheckBox jchk;
        JLabel jEtiq;
        String jString;

        for (int i = 0; i < veDato.length; i++) {
            if (OrdenDatos[i].getClass().equals(JTextField.class)) {
                jText=(JTextField)OrdenDatos[i];
                jText.setText(veDato[i]);
            }
            else
                if (OrdenDatos[i].getClass().equals(JComboBox.class)) {
                    jCom=(JComboBox)OrdenDatos[i];
                    comJCombox.setComboxPosition(jCom,veDato[i],1);
                }
                else
                    if (OrdenDatos[i].getClass().equals(JFormattedTextField.class)) {
                        jFT=(JFormattedTextField)OrdenDatos[i];
                        jFT.setText(veDato[i]);
                        System.out.println("desde jvarios "+veDato[i]);
                    }
                    else
                        if (OrdenDatos[i].getClass().equals(JLabel.class))
                        {
                            jEtiq=(JLabel)OrdenDatos[i];
                            jEtiq.setText(veDato[i]);
                        }
                        else
                            if (OrdenDatos[i].getClass().equals(JDateChooser.class))
                            {
                                jDat=(JDateChooser)OrdenDatos[i];
                                comJDateChooser.setDatejDateChooser(jDat, veDato[i]);
                            }
                            else
                               if (OrdenDatos[i].getClass().equals(JCheckBox.class))
                                   {
                                   String selchk = veDato[i];
                                   jchk=(JCheckBox)OrdenDatos[i];
                                   if (selchk.equals("1")) {
                                       jchk.setSelected(true);
                                   } else {
                                       jchk.setSelected(false);
                                   }
                               }
            }
            c.cierraConexion();
        }
    
    public static boolean esNumero (String valor) {
        try {
            Double.parseDouble(valor);
        }
        catch (Exception e) { return false; }
        return true;
    }
}
