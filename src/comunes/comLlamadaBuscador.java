/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package comunes;


import Frames.buscadorSimple;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
/**
 *
 * @author pcsproject
 */
public class comLlamadaBuscador {


    /**
      * Metodo que permite llamar a un buscador jDialog. En la cual retorna true si es que los datos fueron seleccionados de otro modo retorna falso. <hr>
      * Cuando los datos fueron seleccionados y se presiona la tecla ESC los datos se guardan en una vector de string. Este metodo trabaja en forma conjunta, de modo que, cuando sale del formulario
      *  hay que llamarle al metodo <code>BuscadorjDialogetString()</code> para recibir los resultados en otro String[]vector.<hr>
      * Ejemplo:
      * if( BuscadorjDialog (recibe los parametros para iniciar) )
      * {
      *      vectorLocalFormulario = BuscadorjDialogetString(); //el vector local es un string
      *      //de este modo le pasamos los resultados a otro vector de string
      * }
      * @param campos - Recibe los campos que seleccionamos para la consulta sql
      * @param tabla - Recibe la/s tabla/s para la consulta
      * @param otherWhere - Si es que es necesario para una union; ya sea, tabla=tabla (nunca "tabla=tabla and")
      * @param FinalWhere - Si es que es necesario para el uso despues de los otherWhere
      * @param campoBusqueda - Se cargan los campos de busqueda personalizada para el combox segun la consulta sql de las tablas
      * @param ColumnSelect - En ella decimos que columnas serán seleccionadas cuando salga del formulario
      * @param veTitle - Colocamos los titulos a las columnas
      * @param columnHide - Oculta columnas en la cual no queremos que se observe. Si queremos que todos se vean, le pasamos el dato en: "new int[]{}" como vacio
      * @return
      */
     public static Boolean BuscadorjDialog(String campos, String tabla, String otherWhere, String FinalWhere, String[]campoBusqueda, int[]ColumnSelect, String[]veTitle, int[]columnHide) {
         calljDialog(campos, tabla, otherWhere, FinalWhere, campoBusqueda, ColumnSelect, veTitle, columnHide);
         if (buscadorSimple.getVeDato().length==0)
             return false;
         return true;
     }
     /**
      * Metodo que permite llamar a un buscador jDialog. En la cual retorna true si es que los datos fueron seleccionados de otro modo retorna falso. <hr>
      * Cuando los datos fueron seleccionados y se presiona la tecla ESC los datos se guardan en una vector de string. Este metodo trabaja en forma conjunta, de modo que, cuando sale del formulario
      *  hay que llamarle al metodo <code>BuscadorjDialogetString()</code> para recibir los resultados en otro String[]vector.<hr>
      * Ejemplo:
      * if(BuscadorjDialog(recibe los parametros para iniciar))
      * {
      *      vectorLocalFormulario = BuscadorjDialogetString(); //el vector local es un string
      *      //de este modo le pasamos los resultados a otro vector de string
      * }
      * <p>
      * Este metodo recibe mas pocos parametros para intentar acelerar la busqueda
      * @param campos - Recibe los campos que seleccionamos para la consulta sql
      * @param tabla - Recibe la/s tabla/s para la consulta
      * @param otherWhere - Si es que es necesario para una union; ya sea, tabla=tabla (nunca "tabla=tabla and")
      * @param FinalWhere - Si es que es necesario para el uso despues de los otherWhere
      * @param campoBusqueda - Se cargan los campos de busqueda personalizada para el combox segun la consulta sql de las tablas
      * @param veTitle - Colocamos los titulos a las columnas
      * @return
      */
    public static Boolean BuscadorjDialog(String campos, String tabla, String otherWhere, String FinalWhere, String[]campoBusqueda, String[]veTitle) {
        int[] ColumnSelect = new int[veTitle.length];
        for (int i = 0; i<ColumnSelect.length; i++)
            ColumnSelect[i]=(i+1);
        calljDialog(campos, tabla, otherWhere, FinalWhere, campoBusqueda, ColumnSelect, veTitle, new int[]{});
        if (buscadorSimple.getVeDato().length == 0)
            return false;
        return true;
    }
    /**
      * * Metodo que permite llamar a un buscador jDialog. En la cual retorna true si es que los datos fueron seleccionados de otro modo retorna falso. <hr>
      * Cuando los datos fueron seleccionados y se presiona la tecla ESC los datos se guardan en una vector de string. Este metodo trabaja en forma conjunta, de modo que, cuando sale del formulario
      *  hay que llamarle al metodo <code>BuscadorjDialogetString()</code> para recibir los resultados en otro String[]vector.<hr>
      * Ejemplo:
      * if(BuscadorjDialog(recibe los parametros para iniciar))
      * {
      *      vectorLocalFormulario = BuscadorjDialogetString(); //el vector local es un string
      *      //de este modo le pasamos los resultados a otro vector de string
      * }
      * <p>
      * Este metodo recibe mas pocos parametros para intentar acelerar la busqueda. Para el uso del finalWhere es un caso especial, por eso se hay que escribir aparte.
      * @param sql - Es la consulta sql dentro de la base de datos
      * @param FinalWhere - Se refiere al uso de limit, group by, etc.
      * @param campoBusqueda - Son los campos para la realizacion de una busqueda
      * @param veTitle - Son los nombres que va a llevar la tabla de busqueda
      * @return
      */
     public static Boolean BuscadorjDialog(String sql, String FinalWhere, String[]campoBusqueda, String[]veTitle)
     {
         int[]ColumnSelect=new int[veTitle.length];
         for (int i = 0; i < ColumnSelect.length; i++)
             ColumnSelect[i]=(i+1);
         calljDialog(comJSQL.getCamposSQL(sql), comJSQL.getTablaSQL(sql), comJSQL.getWhereSQL(sql), FinalWhere, campoBusqueda, ColumnSelect, veTitle, new int[]{});

         if (buscadorSimple.getVeDato().length==0)
             return false;
         return true;
     }
     /**
      * Metodo que permite llamar a un buscador jDialog. En la cual retorna true si es que los datos fueron seleccionados de otro modo retorna falso. <hr>
      * Cuando los datos fueron seleccionados y se presiona la tecla ESC los datos se guardan en una vector de string. Este metodo trabaja en forma conjunta, de modo que, cuando sale del formulario
      *  hay que llamarle al metodo <code>BuscadorjDialogetString()</code> para recibir los resultados en otro String[]vector.<hr>
      * Ejemplo:
      * if(BuscadorjDialog(recibe los parametros para iniciar))
      * {
      *      vectorLocalFormulario = BuscadorjDialogetString(); //el vector local es un string
      *      //de este modo le pasamos los resultados a otro vector de string
      * }
      * @param sql
      * @param veTitle
      * @return
      */
     public static Boolean BuscadorjDialog(String sql, String[]veTitle)
     {
         int[]ColumnSelect=new int[veTitle.length];
         for (int i = 0; i < ColumnSelect.length; i++)
             ColumnSelect[i]=(i+1);
         calljDialog(comJSQL.getCamposSQL(sql), comJSQL.getTablaSQL(sql), comJSQL.getWhereSQL(sql), comJSQL.getFinalWhereSQL(sql), comJSQL.getPassCamposToVector(sql), ColumnSelect, veTitle, new int[]{});
         if (buscadorSimple.getVeDato().length==0)
             return false;
         return true;
     }
     /**
      * Metodo que permite llamar a un buscador jDialog. En la cual retorna true si es que los datos fueron seleccionados de otro modo retorna falso. <hr>
      * Cuando los datos fueron seleccionados y se presiona la tecla ESC los datos se guardan en una vector de string. Este metodo trabaja en forma conjunta, de modo que, cuando sale del formulario
      *  hay que llamarle al metodo <code>BuscadorjDialogetString()</code> para recibir los resultados en otro String[]vector.<hr>
      * Ejemplo:
      * if(BuscadorjDialog(recibe los parametros para iniciar))
      * {
      *      vectorLocalFormulario = BuscadorjDialogetString(); //el vector local es un string
      *      //de este modo le pasamos los resultados a otro vector de string
      * }
      * @param sql
      * @param veTitle
      * @return
      */
     public static Boolean BuscadorjDialog(String sql, String[]veTitle, int[]columnHide)
     {
         int[]ColumnSelect=new int[veTitle.length];
         for (int i = 0; i < ColumnSelect.length; i++)
             ColumnSelect[i]=(i+1);
         calljDialog(comJSQL.getCamposSQL(sql), comJSQL.getTablaSQL(sql), comJSQL.getWhereSQL(sql), comJSQL.getFinalWhereSQL(sql), comJSQL.getPassCamposToVector(sql), ColumnSelect, veTitle, columnHide);
         if (buscadorSimple.getVeDato().length==0)
             return false;
         return true;
     }
     /**
      * Metodo que permite llamar a un buscador jDialog. En la cual retorna true si es que los datos fueron seleccionados de otro modo retorna falso. <hr>
      * Cuando los datos fueron seleccionados y se presiona la tecla ESC los datos se guardan en una vector de string. Este metodo trabaja en forma conjunta, de modo que, cuando sale del formulario
      *  hay que llamarle al metodo <code>BuscadorjDialogetString()</code> para recibir los resultados en otro String[]vector.<hr>
      * Ejemplo:
      * if(BuscadorjDialog(recibe los parametros para iniciar))
      * {
      *      vectorLocalFormulario = BuscadorjDialogetString(); //el vector local es un string
      *      //de este modo le pasamos los resultados a otro vector de string
      * }
      * <p>
      * Como tiene un solo parametro para recibir, su modo de personalizar es nula. Tener mucho cuidado cuando se va a usar este metodo, pues, si es que en nuestra consulta sql existe un registro concatenado como: (nombre || ', ' || apellido) el compilador interpretara como dos columnas separadas, creando error a la hora de la consulta
      * @param sql
      * @param veTitle
      * @return
      */
     public static Boolean BuscadorjDialog(String sql)
     {
         int[]ColumnSelect=new int[comJSQL.getPassCamposToVector(sql).length];
         for (int i = 0; i < ColumnSelect.length; i++)
             ColumnSelect[i]=(i+1);
         calljDialog(comJSQL.getCamposSQL(sql), comJSQL.getTablaSQL(sql), comJSQL.getWhereSQL(sql), comJSQL.getFinalWhereSQL(sql), comJSQL.getPassCamposToVector(sql), ColumnSelect, comJSQL.getPassCamposToVector(sql), new int[]{});
         if (buscadorSimple.getVeDato().length==0)
             return false;
         return true;
     }
     /**
      * Metodo que permite llamar a un buscador jDialog. En la cual retorna true si es que los datos fueron seleccionados de otro modo retorna falso. <hr>
      * Cuando los datos fueron seleccionados y se presiona la tecla ESC los datos se guardan en una vector de string. Este metodo trabaja en forma conjunta, de modo que, cuando sale del formulario
      *  hay que llamarle al metodo <code>BuscadorjDialogetString()</code> para recibir los resultados en otro String[]vector.<hr>
      * Ejemplo:
      * if(BuscadorjDialog(recibe los parametros para iniciar))
      * {
      *      vectorLocalFormulario = BuscadorjDialogetString(); //el vector local es un string
      *      //de este modo le pasamos los resultados a otro vector de string
      * }
      * <p>
      * Como tiene dos solo parametro para recibir, su modo de personalizar es nula.
      * @param sql
      * @param veTitle
      * @return
      */
     public static Boolean BuscadorjDialog(String sql, int[]columnHide)
     {
         int[]ColumnSelect=new int[comJSQL.getPassCamposToVector(sql).length];
         for (int i = 0; i < ColumnSelect.length; i++)
             ColumnSelect[i]=(i+1);
         calljDialog(comJSQL.getCamposSQL(sql), comJSQL.getTablaSQL(sql), comJSQL.getWhereSQL(sql), comJSQL.getFinalWhereSQL(sql), comJSQL.getPassCamposToVector(sql), ColumnSelect, comJSQL.getPassCamposToVector(sql), columnHide);
         if (buscadorSimple.getVeDato().length==0)
             return false;
         return true;
     }
     /**
      * Metodo que permite llamar a un buscador jDialog. En la cual retorna true si es que los datos fueron seleccionados de otro modo retorna falso. <hr>
      * Cuando los datos fueron seleccionados y se presiona la tecla ESC los datos se guardan en una vector de string. Este metodo trabaja en forma conjunta, de modo que, cuando sale del formulario
      *  hay que llamarle al metodo <code>BuscadorjDialogetString()</code> para recibir los resultados en otro String[]vector.<hr>
      * Ejemplo:
      * if(BuscadorjDialog(recibe los parametros para iniciar))
      * {
      *      vectorLocalFormulario = BuscadorjDialogetString(); //el vector local es un string
      *      //de este modo le pasamos los resultados a otro vector de string
      * }
      * <p>
      * La implementacion de este metodo es la de llamar a un buscador en su expresion mas rapida para programar.
      * @param sql - Es la consulta sql dentro de la base de datos
      * @param FinalWhere - Se refiere al uso de limit, group by, etc.
      * @param campoBusqueda - Son los campos para la realizacion de una busqueda
      * @param veTitle - Son los nombres que va a llevar la tabla de busqueda
      * @return
      */
     public static Boolean BuscadorjDialog(String sql, String[]campoBusqueda, String[]veTitle)
     {
         int[]ColumnSelect=new int[veTitle.length];
         for (int i = 0; i < ColumnSelect.length; i++)
             ColumnSelect[i]=(i+1);
         calljDialog(comJSQL.getCamposSQL(sql), comJSQL.getTablaSQL(sql), comJSQL.getWhereSQL(sql), comJSQL.getFinalWhereSQL(sql), campoBusqueda, ColumnSelect, veTitle, new int[]{});
         if (buscadorSimple.getVeDato().length==0)
             return false;
         return true;
     }
     /**
      * * Metodo que permite llamar a un buscador jDialog. En la cual retorna true si es que los datos fueron seleccionados de otro modo retorna falso. <hr>
      * Cuando los datos fueron seleccionados y se presiona la tecla ESC los datos se guardan en una vector de string. Este metodo trabaja en forma conjunta, de modo que, cuando sale del formulario
      *  hay que llamarle al metodo <code>BuscadorjDialogetString()</code> para recibir los resultados en otro String[]vector.<hr>
      * Ejemplo:
      * if(BuscadorjDialog(recibe los parametros para iniciar))
      * {
      *      vectorLocalFormulario = BuscadorjDialogetString(); //el vector local es un string
      *      //de este modo le pasamos los resultados a otro vector de string
      * }
      * <p>
      * Este metodo recibe mas pocos parametros para intentar acelerar la busqueda. Para el uso del finalWhere es un caso especial, por eso se hay que escribir aparte y tambien si se desea, se puede ocultar las columnas que queramos
      * @param sql - Es la consulta sql dentro de la base de datos
      * @param FinalWhere - Se refiere al uso de limit, group by, etc.
      * @param campoBusqueda - Son los campos para la realizacion de una busqueda
      * @param veTitle - Son los nombres que va a llevar la tabla de busqueda
      * @return
      */
     public static Boolean BuscadorjDialog(String sql, String FinalWhere, String[]campoBusqueda, String[]veTitle, int[]columnHide)
     {
         int[]ColumnSelect=new int[veTitle.length];
         for (int i = 0; i < ColumnSelect.length; i++)
             ColumnSelect[i]=(i+1);
         calljDialog(comJSQL.getCamposSQL(sql), comJSQL.getTablaSQL(sql), comJSQL.getWhereSQL(sql), FinalWhere, campoBusqueda, ColumnSelect, veTitle, columnHide);
         if (buscadorSimple.getVeDato().length==0)
             return false;
         return true;
     }
     /**
      * Permite retornar el resultado de una busqueda realizada por el metodo <code>BuscadorjDialog</code>, si es que esta cargado
      * @return
      */
     public static String[]BuscadorjDialogetString()
     {
         return buscadorSimple.getVeDato();
     }
     /**
      * Retorna la columna que seleccionemos como un String
      * @param columna
      * @return
      */
     public static String BuscadorjDialogetStringCol(int columna)
     {
         return buscadorSimple.getVeDato()[columna-1];
     }
     /**
      * Permite la insercion en forma masiva, atendiendo al orden con que consultamos e insertamos en el grupo de objetos. <br><br>
      * Advertencia: El combox solamente evalua la parte de texto o descripcion como mejor sea.
      * @param OrdenDatos
      */
     public static void BuscadorjDialogetStringObjSeq(Object[]OrdenDatos)
     {
         JTextField jText;
         JComboBox jCom;
         JFormattedTextField jFT;
         Conexion c = new Conexion();
         c.estaConectado();
             for (int i = 0; i < OrdenDatos.length; i++)
             {
                 if (OrdenDatos[i].getClass().equals(JTextField.class))
                 {
                     jText=(JTextField)OrdenDatos[i];
                     jText.setText(BuscadorjDialogetString()[i]);
                 }
                 else
                     if (OrdenDatos[i].getClass().equals(JComboBox.class))
                     {
                         jCom=(JComboBox)OrdenDatos[i];
                         comJCombox.setComboxPosition(jCom, BuscadorjDialogetString()[i], 2);
                     }
                     else
                         if (OrdenDatos[i].getClass().equals(JFormattedTextField.class))
                         {
                             jFT=(JFormattedTextField)OrdenDatos[i];
                             jFT.setText(BuscadorjDialogetString()[i]);
                         }
             }
         c.cierraConexion();
     }
     /**
      * Permite insertan en forma masiva y aleatoria, los datos que, despues de una consulta, hallan sido seleccionados.<br><br>
      * Advertencia: El combox solamente evalua la parte de texto o descripcion como mejor sea.<hr>
      * El vector de objeto va como sigue de ejemplo: new Object[]{combox,textfield,jdatechooser,jformatted}
      * @param OrdenDatos
      * @param selectColumn
      */
     public static void BuscadorjDialogetStringObjAlt(Object[]OrdenDatos, int[]selectColumn)
     {
         // <editor-fold defaultstate="collapsed" desc="Condicion">
         if (OrdenDatos.length != selectColumn.length) {
             JOptionPane.showMessageDialog(null, "El orden de datos y la seleccion no coinciden");
             return;
         }// </editor-fold>
         JTextField jText;
         JComboBox jCom;
         JFormattedTextField jFT;
         Conexion c = new Conexion();
         c.estaConectado();

         for (int i = 0; i < selectColumn.length; i++)
             selectColumn[i]-=1;
         try
         {
             for (int i = 0; i < OrdenDatos.length; i++)
             {
                 if (OrdenDatos[i].getClass().equals(JTextField.class))
                 {
                     jText=(JTextField)OrdenDatos[i];
                     jText.setText(BuscadorjDialogetString()[selectColumn[i]]);
                 }
                 else
                     if (OrdenDatos[i].getClass().equals(JComboBox.class))
                     {
                         jCom=(JComboBox)OrdenDatos[i];
                         comJCombox.setComboxPosition(jCom, BuscadorjDialogetString()[selectColumn[i]], 2);
                     }
                     else
                         if (OrdenDatos[i].getClass().equals(JFormattedTextField.class))
                         {
                             jFT=(JFormattedTextField)OrdenDatos[i];
                             jFT.setText(BuscadorjDialogetString()[selectColumn[i]]);
                         }

             }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Uno o mas datos se encuentran fuera de rango seleccionado");
         }
         c.cierraConexion();
     }
     /**
    * Llama al buscador jDialog
    */
    private static void calljDialog(String campos, String tabla, String otherWhere, String FinalWhere, String[]campoBusqueda, int[]ColumnSelect, String[]veTitle, int[]columnHide)
    {
        buscadorSimple bbb = new buscadorSimple(null, true, campos, tabla, otherWhere, FinalWhere, campoBusqueda, ColumnSelect, veTitle, columnHide);
        bbb.setVisible(true);
        //System.out.println("pase del call");
        bbb.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                e.getWindow().dispose();
            }
        });
    }
}
