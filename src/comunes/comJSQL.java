/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comunes;

//import clases.comConexion; //Colocarle el nombre segun el paquete de clases que ,le contenga a la clase comConexion
import comunes.Conexion; //Colocarle el nombre segun el paquete de clases que ,le contenga a la clase comConexion
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author pcsproject 
 */
public class comJSQL
{
    /**
     * Recibe el primer campo y lo convierte en numerico de una consulta sql. Retorna un numerico
     * @param sql
     * @return
     */
    public static int getCampoUnicoInt(String sql)
    {
        int xTmp=0;
        Conexion d = new Conexion();
        d.estaConectado();
        ResultSet rs = d.dameLista(sql);
        try 
        {
            while (rs.next()) 
            {                
                xTmp=Integer.parseInt(rs.getString(1));
                break;
            }
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "No fue posible procesar el sql, por favor verifique su estructura", "Error de integridad", 0);
            d.cierraConexion();
            return xTmp=0;
        }

        d.cierraConexion();
        return xTmp;
    }
    /**
     * Recibe el campo y lo convierte en numerico de una consulta sql y seleccionando la columna segun hallamos colocado. Retorna un numerico
     * @param sql
     * @param columnSelect
     * @return
     */
    public static int getCampoUnicoInt(String sql, int columnSelect)
    {
        int xTmp=0;
        Conexion d = new Conexion();
        d.estaConectado();
        ResultSet rs = d.dameLista(sql);
        try 
        {
            while (rs.next()) 
            {                
                xTmp=Integer.parseInt(rs.getString(columnSelect));
                break;
            }
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "No fue posible procesar el sql, por favor verifique su estructura", "Error de integridad", 0);
            d.cierraConexion();
            return xTmp=0;
        }

        d.cierraConexion();
        return xTmp;
    }
    /**
     * Recibe una consulta sql y retorna un String de la primera columna
     * @param sql
     * @return
     */
    public static String getCampoUnicoStrin(String sql)
    {
        String xTmp="";
        Conexion d = new Conexion();
        d.estaConectado();
        ResultSet rs = d.dameLista(sql);
        try 
        {
            while (rs.next()) 
            {
                xTmp=rs.getString(1);
                break;
            }
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "No fue posible procesar el sql, por favor verifique su estructura", "Error de integridad", 0);
            d.cierraConexion();
            return xTmp="";
        }
        
        d.cierraConexion();
        return xTmp;
    }
    /**
     * Recibe una consulta sql y retorna un String de la columna que hallamos seleccionado por <code>columnSelect</code>
     * @param sql
     * @param columnSelect
     * @return
     */
    public static String getCampoUnicoString(String sql, int columnSelect)
    {
        String xTmp="";
        Conexion d = new Conexion();
        d.estaConectado();
        ResultSet rs = d.dameLista(sql);
        try 
        {
            while (rs.next()) 
            {                
                xTmp=rs.getString(columnSelect);
                break;
            }
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "No fue posible procesar el sql, por favor verifique su estructura", "Error de integridad", 0);
            d.cierraConexion();
            return xTmp="";
        }

        d.cierraConexion();
        return xTmp;
    }
    /**
     * Retorna verdadero si se encuentra mas de un registro segun la consulta sql que hallamos colocado. En todo caso es falso
     * @param sql
     * @return
     */
    public static boolean existeRegistro(String sql)
    {
        ResultSet rs=null;
        int tmp=0;
        Conexion cc= new Conexion();
        cc.estaConectado();
        rs = cc.dameLista(sql);
        
        try
        {
            while(rs.next())
            {
                tmp+=1;
                break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo errores al buscar", "Errores", 0);
        }
        cc.cierraConexion();
        if(tmp==0)
            return false;
        return true;
    }
    /**
     * Devuelve la cantidad de columnas que halla contado durante una consulta sql
     * @param sql
     * @return
     */
    public static int getColumnCountSQL(String sql)
    {
        int tmp=0;
        Conexion c = new Conexion();
        c.estaConectado();
            tmp=c.countColumn(sql);
        c.cierraConexion();
        return tmp;
    }
    /**
     * Permite retorna mediante una cadena de caracteres los campos que fueron encontrados a partir de una consulta sql
     * @param sql
     * @return
     */
    public static String getCamposSQL(String sql)
    {
        return getCamposFromWhereSQL(sql)[0];
    }
    public static String getCamposIDSQL(String sql)
    {
        return getCamposFromWhereSQL(sql)[4];
    }
    /**
     * Permite retorna mediante una cadena de caracteres el contenido de un 'From', o sea, las tablas asociadas dentro, a partir de una consulta sql
     * @param sql
     * @return
     */
    public static String getTablaSQL(String sql)
    {
        return getCamposFromWhereSQL(sql)[1];
    }
    /**
     * Permite retorna mediante una cadena de caracteres el contenido de un 'Where' a partir de una consulta sql
     * @param sql
     * @return
     */
    public static String getWhereSQL(String sql) {
        return getCamposFromWhereSQL(sql)[2];
    }
    /**
     * Permite retornar mediante una cadena de caracteres el contenido de un 'FinalWhere' o sea, con los siguientes comandos en adelante como:
     * {"group by","order by","having","limit","offset","fetch"} <p>
     * El metodo no soporta bien las funciones de: FOR, UNION; porque este metodo solamente se preparo para los mas usuales.
     * @param sql
     * @return
     */
    public static String getFinalWhereSQL(String sql)
    {
        return getCamposFromWhereSQL(sql)[3];
    }
    /**
     * Retorna un vector de caracteres con las siguientes posiciones:<p>
     * 1 - campos, 2 - from, 3 - where<hr>
     * Segun la consulta SQL.
     * @param sql
     * @return
     */
    public static String[]getCamposFromWhereSQL(String sql) {
        //sql=sql.toLowerCase();
        String[]vCamposFromWhere=new String[5];
        String[]veFinal={"group by","order by","having","limit","offset","fetch"};
        Boolean select=false, from=false, where=false, bfinal=false;
        String campos="", tabla="", swhere="", sfinal="";
                //if (sql.substring(i, i+6)=="select") limit 10
        for (int i = 0; i < sql.length(); i++) {
            if(i+6<sql.length())
                if(sql.substring(i, i+6).equals("select")) {
                    select=true;
                    i=i+6;
                }
            else
                if(i+4<sql.length())
                    if(sql.substring(i, i+4).equals("from")) {
                        select=false;
                        from=true;
                        i=i+4;
                    }
                else
                    if(i+5<sql.length())
                        if(sql.substring(i, i+5).equals("where")) {
                            from=false;
                            where=true;
                            i=i+5;
                        }
                    else
                        if(!bfinal)
                            for (int j = 0; j < veFinal.length; j++)
                                if(i+veFinal[j].length()<sql.length())
                                    if(sql.substring(i, i+veFinal[j].length()).equals(veFinal[j])) {
                                        where=false;
                                        bfinal=true;
                                        break;
                                    }
            if (select)
                campos=campos+sql.substring(i, i+1);
            else
                if (from)
                    tabla=tabla+sql.substring(i, i+1);
                else
                    if(where)
                        swhere=swhere+sql.substring(i, i+1);
                    else
                        sfinal=sfinal+sql.substring(i, i+1);
        }
        vCamposFromWhere[0]=campos.trim();
        String[] campoID = vCamposFromWhere[0].split(",");
        vCamposFromWhere[1]=tabla.trim();
        vCamposFromWhere[2]=swhere.trim();
        vCamposFromWhere[3]=sfinal.trim();
        vCamposFromWhere[4]=campoID[0];

        return vCamposFromWhere;
    }
    /**
     * Metodo que recibe un SQL y luego quita los campos, los cuales, esto a su vez, los pasa a vector, cada uno segun su posicion. Ejemplo: <hr>
     * campo1 - posicion 0 ;
     * campo2 - posicion 1 ;
     * campo3 - posicion 2 ;
     * @param sql
     * @return
     */
    public static String[]getPassCamposToVector(String sql)
    {
        return getStringToVector(comJSQL.getCamposSQL(sql));
        //<editor-fold defaultstate="collapsed" desc="anterior">
        //String tmpsql=comJSQL.getCamposSQL(sql);
//        String[]veSQL= new String[tmpsql.length()];
//        for (int i = 0; i < veSQL.length; i++)
//            veSQL[i]="";
//        int c=0;
//        for (int i = 0; i < tmpsql.length(); i++)
//        {
//            if (tmpsql.substring(i, i+1).equals(","))
//            {
//                c+=1;
//                continue;
//            }
//            if (!tmpsql.substring(i, i+1).equals(" "))
//                veSQL[c]=veSQL[c]+tmpsql.substring(i, i+1);
//        }
//        String[]veSQLaux=new String[c+1];
//        for (int i = 0; i < veSQLaux.length; i++)
//            veSQLaux[i]=veSQL[i];
//        return veSQLaux;
        //</editor-fold>
    }

    /**
    * Recibe un string y lo convierte en vector de una dimension. Ej.: ["2","3","4"]
    * @param StringEnComa
    * @return
    */
   public static String[]getStringToVector(String StringEnComa)
   {
        //String tmpsql=StringEnComa;
        String[]veSQL= new String[StringEnComa.length()];
        for (int i = 0; i < veSQL.length; i++)
            veSQL[i]="";
        int c=0;
        for (int i = 0; i < StringEnComa.length(); i++)
        {
            if (StringEnComa.substring(i, i+1).equals(","))
            {
                c+=1;
                continue;
            }
            if (!StringEnComa.substring(i, i+1).equals(" "))
                veSQL[c]=veSQL[c]+StringEnComa.substring(i, i+1);
        }
        String[]veSQLaux=new String[c+1];
        for (int i = 0; i < veSQLaux.length; i++)
            veSQLaux[i]=veSQL[i];
        return veSQLaux;
   }


    /**
     * Segun nuestra consulta SQL la cantidad de rows que retorna, ese resultado lo convierte en un String[]. Este metodo, solamente toma la primera columna.
     * @param sql
     * @return
     */
    public static String[]getRowsToVector(String sql)
    {
        String[]veDatos;
        Conexion c = new Conexion();
        c.estaConectado();
        try
        {
            ResultSet rs = c.dameLista(sql);
            ArrayList lista = new ArrayList();
            while (rs.next())
            {
                lista.add(rs.getString(1));
            }
            veDatos = new String[lista.size()];
            for (int i = 0; i < lista.size(); i++)
            {
                veDatos[i]=lista.get(i).toString();
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Ocurrio un error");
            c.cierraConexion();
            return null;
        }
        return veDatos;
    }
    /**
     * Recibe una consulta sql y el resultado lo retorna como un String[fila][columna] de doble vector.
     * @param sql
     * @return
     */
    public static String[][]getSQLtoVectorDouble(String sql)
    {
        //[fila][columna]
        String[][]veDatos={};
        Conexion c = new Conexion();c.estaConectado();
        int Columna = c.countColumn(sql);
        int Fila = c.countFila(sql);
        veDatos = new String[Fila][Columna];

        ResultSet rs = c.dameLista(sql);

        try
        {
            while (rs.next())
            {
                //System.out.println("fila - "+rs.getRow());
                for (int i = 0; i < Columna; i++)
                {
                    veDatos[rs.getRow()-1][i]=rs.getString(i+1);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error durante la carga del String[][]");
	c.cierraConexion();
            return new String[][]{};
        }
	c.cierraConexion();
        return veDatos;
    }
}
