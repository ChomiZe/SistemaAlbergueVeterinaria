package comunes;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.awt.Component;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author pcsproject
 */
public class comJTable {
    /**
     * Recibe valor de una jTable que fue seleccionado y lo retorna como una 
     * cadena de texto, donde, el <code>column</code> representa al orden que
     * tiene la columna dentro del jTable.
     * <code>column</code> Empieza los numeros a partir de 1 en adelante
     * <code>jTable</code> Tabla especificada para recibir el string
     * @param jTable
     * @param column
     * @return 
     */
    
    public static String getValueATjTable(JTable jTable, int column) {
        String tmp;
        column-=1;
        tmp= jTable.getValueAt(jTable.getSelectedRow(), column).toString();
        return tmp;
    }
    /**
     * Coloca a una jtable una cadena de texto segun la <code>column</code>
     * Este metodo solo funciona cuando los registros ya estan insertados, de otro modo,
     * crea error a la hora de insertar, especialmente por uno vacio
     * @param jTable
     * @param texto
     * @param column 
     */
    public static void setValueATjTable(JTable jTable, String texto, int column)  {
        column-=1;
        jTable.setValueAt(texto, jTable.getSelectedRow(), column);
    }
    /**
     * Asi como en foxpro, sube los datos a la memoria de la matriz, tambien procede
     * de forma similar este metodo, a diferencia de que le hay que pasar 3 datos para
     * funcionar, los cuales son: jtextfield (a quien se le da el valor), jTable (quien
     * posee el dato a dar) y column (en que columna se le da el dato)
     * @param jTextField
     * @param jTable
     * @param column 
     */
    public static void ScatterMemvar(JTextField jTextField, JTable jTable, int column) {
        try {
            column-=1;
            jTextField.setText(
                            jTable.getValueAt(jTable.getSelectedRow(), column).toString()
                            );
        } catch (Exception e) {
        }
    }

    /**
     * Vacia un jTable
     * @param jTable
     */
    public static void vaciarJtable(JTable jTable) {
        //Cualquier error que contenga utilizar el codigo anterior 2
        DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
        dtm.setRowCount(0);
        
        //Tener en cuenta que el mas grande es el reciente
        // <editor-fold defaultstate="collapsed" desc="Codigo anterior 2">
//        DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
//        for (int c = (jTable.getRowCount() - 1); c >= 0; --c)
//            dtm.removeRow(c);
            //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Codigo anterior 1">
            //        jTable.selectAll();
            //        int[] X = jTable.getSelectedRows();
            //        DefaultTableModel dtm = (DefaultTableModel)jTable.getModel();
            //        for(int c = (X.length-1); c >= 0; --c)
            //                dtm.removeRow(X[c]);// </editor-fold>
    }
    
    public static void removeRowjTable(JTable jTable) {
        DefaultTableModel dtm = (DefaultTableModel)jTable.getModel();
        try {
            dtm.removeRow(jTable.getSelectedRow());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "La grilla esta vacia o necesita seleccionar el registro para remover", "Atencion", 0);
        }
    }
    /**
     * Sirve para insertar un unico registro recibiendo un vector de string
     * y segun el orden, es lo que va a insertando en la tabla
     * @param jTable
     * @param ve_Str
     * @param cantColumn 
     */
    public static void addRowStringUnico(JTable jTable, String[]ve_Str) {
        DefaultTableModel djTable= (DefaultTableModel) jTable.getModel();
        if (jTable.getColumnCount()!=ve_Str.length){
            JOptionPane.showMessageDialog(null, "Los datos que intenta ingresar no coinciden con la cantidad de columnas", "No concurrentes", 0);
            return;
        }
        Object[]obj=new Object[ve_Str.length];
        for (int i = 0; i < ve_Str.length; i++) 
            obj[i] = ve_Str[i];
        djTable.addRow(obj);
    }
    /**
     * Devuelve verdadero si la jTable se encuentra vacio
     * @param jTable
     * @return 
     */
    public static boolean isEmptyJtable(JTable jTable) {
        int count=0;
        for(int c = 0; c < jTable.getRowCount(); c++){
            count+=1;
            break;
        }
        if (count==0) 
            return true;
        return false;
    }
    /**
     * Metodo que retorna true si es que en un pequeño lapso de tiempo se presiono dos veces
     * sino retorna false
     * @param evt
     * @return 
     */
    public static boolean isDoubleClick(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2)
            return true;
        return false;
    }
    
    public static void formatear(JTable tabla, int[] columnas, int[] ancho) {
        for (int i=0; i<= columnas.length-1; i++) {
            tabla.getColumnModel().getColumn(columnas[i]).setPreferredWidth(ancho[i]);
        }
    }
    
    public static void formatear(JTable[] tabla, int[][] columnas, int[][] ancho) {
        for (int i=0; i<= tabla.length-1; i++) {
            formatear(tabla[i], columnas[i], ancho[i]);
        }
    }
    
    public static void esconderColumnas(JTable tabla, int[] columnas) {
        for (int i=0; i<= columnas.length-1; i++) {
            tabla.getColumnModel().getColumn(columnas[i]).setMinWidth(0);
            tabla.getColumnModel().getColumn(columnas[i]).setMaxWidth(0);
            tabla.getColumnModel().getColumn(columnas[i]).setPreferredWidth(0);
        }
    }
    
    public static void esconderColumnas(JTable[] tablas, int[][] columnas) {
        for (int i=0; i<= tablas.length-1; i++) {
            esconderColumnas(tablas[i], columnas[i]);
        }
    }
    
    public static void orientacionColumnas(JTable tabla, int[] columnas, int valor) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        //Centro = 0, Izquierda = 2, Derecha = 4
        for (int i=0; i<=columnas.length-1; i++) {
            tcr.setHorizontalAlignment(valor);
            tabla.getColumnModel().getColumn(columnas[i]).setCellRenderer(tcr);
        }
    }
    
    public static void orientacionHeaders(JTable tabla, int[] columnas, int valor) {
        DefaultTableCellRenderer tcr;
        //Centro = 0, Izquierda = 2, Derecha = 4
        for (int i=0; i<=columnas.length-1; i++) {
            tcr = (DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer();
            tcr.setHorizontalAlignment(valor);
            tabla.getTableHeader().setDefaultRenderer(tcr);
        }
    }
    
    public static void orientacionHeaders(JTable[] tablas, int[][] columnas, int[] valor) {
        for (int i=0; i<tablas.length; i++) {
            orientacionHeaders(tablas[i], columnas[i], valor[i]);
        }
    }
    
    /**
     * Retorna true si por lo menos uno se encuentra seleccionado en una tabla que contenga el checkbox
     * @param table
     * @param colBoo
     * @return 
     */
    public static boolean isSelectBoolean(JTable table, int colBoo) {
        if (getRowTrueFalseCheck(table, colBoo, true).length>=1) 
            return true;
        return false;
    }
    /**
     * Recibe el valor de un jTable de modo personalizado. El row que pide es el row que se va a usar para retornar una cadena de String y el column es para decir por quien columna se pide
     * los datos. El metodo esta preparado para advertir al usuario si coloco un row o column fuera de rango.
     * @return 
     * @param jTable
     * @param Row
     * @param Column 
     */
    
    public static JComponent getComponent (JTable tabla, int columna, int fila) {
        JComponent componente; Object o = null;
        componente = (JComponent) tabla.getColumnModel().getColumn(columna).getCellEditor().getTableCellEditorComponent(tabla, o, true, fila, columna);
        return componente;
    }
    
    public static String getValueATjTable(JTable jTable, int Row, int Column) {
        String valor;
        if (comJTable.isEmptyJtable(jTable)) {
            JOptionPane.showMessageDialog(null, "La tabla de la que intenta extraer datos se encuentra vacía", "Atención", 0);
            return null;
        }
        if (getComponent(jTable, Column, Row).getClass().equals(JComboBox.class)) {
            valor = comJCombox.getComboxString((JComboBox)getComponent(jTable, Column, Row), 1);
        }
        else {
            Row -= 1; Column -= 1;
            //jTable.selectAll();
            int[]xRow=jTable.getSelectedRows();
            int[]xColumn=jTable.getSelectedColumns();
    //        if (Row>xRow.length || Row <= -1) {
    //            JOptionPane.showMessageDialog(null, "El Row elegido excede al numero de Rows en la jTable o ha especificado un Row fuera de rango", "Atencion", 0);
    //            return null;
    //        }
    //        if (Column>xColumn.length || Column <= -1) {
    //            JOptionPane.showMessageDialog(null, "El Column elegido excede al numero de Columns en la jTable o ha especificado una Column fuera de rango", "Atencion", 0);
    //            return null;
    //        }
            valor = (String) jTable.getValueAt(Row, Column); 
        }
        return valor;
    }
    /** 
     * Clase que permite personalizar a un jTable de la siguiente manera: recibe el <code>jTable</code>, <code>veClass</code>: vector de clases para definir 
     * que tipo de datos va a soportar la columna (Ej. de uso: Class[] types = new Class [] {Object.class, Object.class, Object.class, Boolean.class};), <code>colEdit</code>: sirve 
     *  para decir que columna se va a poder editar (Ej. de uso: int[]veEdit={1,4};) donde la columna 1 y 4 serán editables el resto no... si queremos que todos no se puedan editar 
     * simplemente colocamos al vector en 0 siendo entonces int[]veEdit={0};. <hr> 
     * Para su modo de uso debemos de inicializar para que funcione siendo el primer paso: MiModelo m = new MiModelo(jTableCuotas, types, new int[]{4,1}); <br> 
     * Luego cuando hayamos personalizado a nuestro gusto (tambien podria ser: m.addColumn("ColumnNew");), el segundo paso es colocar el modelo a la tabla: jTableCuotas.setModel(m); 
     * @param jTable 
     * @param veClass 
     * @param colEdit 
     */ 
    private static class MiModelo extends DefaultTableModel
    //private static class MiModelo extends DefaultTableModel
    { 
        //<editor-fold defaultstate="collapsed" desc="Recomendaciones">
        //Debido a las referencias de variables que se pueden llegar a usar, es recomendable usar solamente este metodo con buscadores, ya que
        //al principio se declara y luego se destruye
        //</editor-fold>
        private static JTable jtable = new JTable(); 
        private static Class[]veClass; 
        private static int[]colEdit; //Se corrigio en vez del uso de una variable estatica, el cual es insustituible para que cada jtable sus edit sean de forma independiente
        //private int[]colEdit;

        public static void setColEdit(int[] colEdit) {
            MiModelo.colEdit = colEdit;
        }


        public MiModelo(JTable jTable, Class[]veClass, int[]colEdit) { 
            MiModelo.jtable=jTable; 
            MiModelo.veClass=veClass; 
            MiModelo.colEdit=colEdit; 
            for (int i = 0; i < colEdit.length; i++) 
                colEdit[i]-=1; 
        } 
        @Override
        public Class getColumnClass(int columnIndex) 
        { 
            return veClass[columnIndex]; 
        } 
        @Override
        public boolean isCellEditable(int row, int column) 
        {
            for (int i = 0; i < colEdit.length; i++) 
                if (colEdit[i]==column) 
                    return true; 
            return false; 
        }
        @Override
        public void addColumn(Object columnName) 
        { 
            super.addColumn(columnName); 
        } 
    }
    /**
     * Construye una tabla a partir de que recibe un table y le coloca un nuevo modelo con los registros vacios.
     * @param jTable - Tabla asociada
     * @param veClass - Tipos de datos que contendra la columna
     * @param colEdit - Columnas que serán editables
     * @param veTitle - Titulos asociados a las columnas
     */
    private static void construccionTableWithModel(JTable jTable, Class[]veClass, int[]colEdit, String[]veTitle)
    {
        MiModelo m = new MiModelo(jTable, veClass, colEdit);
            for (int i = 0; i < veTitle.length; i++)
                m.addColumn(veTitle[i]);
            jTable.setModel(m);
    }
    /**
     * Metodo que consiste en cargar una grilla.<BR> 
     * Descripcion de los codigos:<hr> 
     * <p> 
     * <code>jTable</code> - Se le dice al metodo que jTable se interactua<br> 
     * <code>veClass</code> - Decimos a la columna que tipo de dato va a ser (int, string, boolean)<br> 
     * <code>colEdit</code> - Especificamos que columnas van a poder ser editables (si queremos la primera columna le decimos "new int[]{1}")<br> 
     * <code>veTitle</code> - Asignamos los titulos que tendra cada columna<br> 
     * <code>sql</code> - La consulta sql tiene que coincidir con la misma cantidad de <code>veTitle</code> para que no halle errores. 
     * </p> 
     * @param jTable 
     * @param veClass 
     * @param colEdit 
     * @param veTitle 
     * @param sql 
     */ 
    public static void cargarGrilla(JTable jTable, Class[]veClass, int[]colEdit, String[]veTitle, String sql) {
        //<editor-fold defaultstate="collapsed" desc="Control anterior">
        //        if (comJSQL.getColumnCountSQL(sql)!=veTitle.length) {
        //            JOptionPane.showMessageDialog(null, "La cantidad de titulos con la cantidad de columnas no coinciden", "Error de carga", JOptionPane.ERROR_MESSAGE);
        //            return;
        //        }
        
//        MiModelo m = new MiModelo(jTable, veClass, colEdit);
//        for (int i = 0; i < veTitle.length; i++)
//            m.addColumn(veTitle[i]);
//        jTable.setModel(m);
//        addRowTable(sql, veTitle.length, (DefaultTableModel)jTable.getModel());
        //</editor-fold>
        try {
            for (int i = 0; i < veClass.length; i++)
                if(!(jTable.getColumn(i).getClass().equals(veClass[i])))
                    Integer.parseInt("creo error");
            comJTable.vaciarJtable(jTable);
        } catch (Exception e){
           construccionTableWithModel(jTable, veClass, colEdit, veTitle);
        }
        addRowTable(sql, veTitle.length, (DefaultTableModel)jTable.getModel());
    } 
    /**
     * Carga de grilla simplificado para hacer una consulta simple, la finalidad de este metodo es hacer una consulta rapida. Recordar que, la cantidad de columnas en una sql debe de coincidir con la cantidad de <code>veTitle</code> para que se pueda hacer una buena consulta
     * @param jTable - Es la jTable asociada donde se van a cargar los datos
     * @param veTitle - Son los titulos que le agregaremos a cada columna
     * @param sql - Es la consulta que hicimos en la base de datos
     */
    
    public static void cargarGrilla(JTable jTable, String[]veTitle, String sql) {
        Class[] veClass = new Class[veTitle.length];
        for (int i=0; i<veTitle.length; i++) {
            veClass[i] = Object.class;
        }
// <editor-fold defaultstate="collapsed" desc="Codigo anterior">
//        MiModelo m = new MiModelo(jTable, veClass, new int[]{0});
//        for (int i = 0; i < veTitle.length; i++)
//            m.addColumn(veTitle[i]); 
//        jTable.setModel(m);// </editor-fold>
        construccionTableWithModel(jTable, veClass, new int[]{0}, veTitle);
        addRowTable(sql, veTitle.length, (DefaultTableModel)jTable.getModel());
    }
    
    public static void cargarGrillaSinModelo(JTable jTable, String sql) {
        //construccionTableWithModel(jTable, veClass, new int[]{0}, veTitle);
        vaciarJtable(jTable);
        addRowTable(sql, jTable.getColumnCount(), (DefaultTableModel)jTable.getModel());
    }
    
    public static void setValues(JTable jTable, int fila, int[] columnas, int[] colsql, String[] value) {
        for (int x=0; x<columnas.length; x++) {
            jTable.setValueAt(value[colsql[x]], fila, columnas[x]);
        }
    }
    
    public static void setValuesVariasFilas(JTable jTable, int cantfilas, int[] columnas, int[] selectcols, String[] value) {
        for (int i=0; i<cantfilas; i++) {
            setValues(jTable, i, columnas, selectcols, value);
        }
    }
    
    public static void cargarGrillaConCombos(JTable jTable, String sql, int cols, int[] posCombo, JComboBox[] combos) {
        addRowTableConCombos(sql,cols,jTable,posCombo,combos);
    }
    
    /**
     * Carga de grilla simplificado para hacer una consulta simple, la finalidad de este metodo es hacer una consulta rapida. Recordar que, la cantidad de columnas en una sql debe de coincidir con la cantidad de <code>veTitle</code> para que se pueda hacer una buena consulta
     * @param jTable - Es la jTable asociada donde se van a cargar los datos
     * @param veTitle - Son los titulos que le agregaremos a cada columna
     * @param sql - Es la consulta que hicimos en la base de datos
     */
    public static void cargarGrilla(JTable jTable, int[]colEdit, String[]veTitle, String sql) {
//        if (comJSQL.getColumnCountSQL(sql)!=veTitle.length) {
//            JOptionPane.showMessageDialog(null, "La cantidad de titulos con la cantidad de columnas no coinciden", "Error de carga", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
        Class[]veClass=new Class[veTitle.length];
        for (int i = 0; i < veClass.length; i++)
            veClass[i]=Object.class;

        construccionTableWithModel(jTable, veClass, colEdit, veTitle);

//        MiModelo m = new MiModelo(jTable, veClass, colEdit);
//        for (int i = 0; i < veTitle.length; i++)
//            m.addColumn(veTitle[i]);
//        jTable.setModel(m);
        addRowTable(sql, veTitle.length, (DefaultTableModel)jTable.getModel());
    }
    /**
     * Carga una grilla de la forma mas simple. Los nombres de los encabezados pueden variar segun la consulta sql que se haga, atencion con este detalle
     * @param jTable
     * @param sql
     */
    public static void cargarGrilla(JTable jTable, String sql)
    {
        String[]veTitle=comJSQL.getPassCamposToVector(sql);
        Class[]veClass=new Class[veTitle.length];
        for (int i = 0; i < veClass.length; i++)
            veClass[i]=Object.class;

        construccionTableWithModel(jTable, veClass, new int[]{0}, veTitle);
//        MiModelo m = new MiModelo(jTable, veClass, new int[]{0});
//        for (int i = 0; i < veTitle.length; i++)
//            m.addColumn(veTitle[i]);
//        jTable.setModel(m);
        addRowTable(sql, veTitle.length, (DefaultTableModel)jTable.getModel());
    }
    

    
    /**
     * Permite agregar a una tabla datos para cargarlo
     * @param sql
     * @param cant_column
     * @param dtm
     */
    private static void addRowTable(String sql, int cant_column, DefaultTableModel dtm) {
        ResultSet rs = null; 
        Conexion bd = new Conexion();
        try { 
            if (!bd.estaConectado()) { 
                JOptionPane.showMessageDialog(null, "Hubo un problema con la conexión" , "A T E N C I Ó N", 
                JOptionPane.INFORMATION_MESSAGE); 
                return; 
            } 
            rs = (ResultSet) bd.dameLista(sql); 
                if (cant_column<=1) {
                    JOptionPane.showMessageDialog(null, "La cantidad de columnas es menor o igual a 1, por tanto no se puede continuar con la carga de la tabla", "Error de carga", 0);
                    return;
                }
                Object[] veObject = new Object[cant_column];
                while (rs.next()) {                
                    for (int i = 0; i < cant_column; i++) {
                        if (dtm.getColumnClass(i).equals(Boolean.class)) {
                            if (rs.getString(i+1).equals("1"))
                                veObject[i] = true;
                            else
                                veObject[i] = false;
                        }
                        else {
                            veObject[i] = rs.getString(i+1);
                        }
                    }
                    dtm.addRow(veObject);
                }
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Ocurrió un error\n"+e.getMessage(), "ATENCIÓN",0);
        } 
    } 
    
    private static void addRowTableConCombos(String sql, int cant_column, JTable tabla, int[] posCombo, JComboBox[] combos) {
        DefaultTableModel dtm = (DefaultTableModel) tabla.getModel();
        ResultSet rs; 
        try { 
            Conexion bd = new Conexion();
            if (!bd.estaConectado()) { 
                JOptionPane.showMessageDialog(null, "Hubo un problema con la conexión" , "A T E N C I Ó N", JOptionPane.INFORMATION_MESSAGE); 
                return; 
            }
            
            System.out.println(sql); 
            rs = (ResultSet) bd.dameLista(sql); 
            if (cant_column<=1) {
                JOptionPane.showMessageDialog(null, "La cantidad de columnas es menor o igual a 1, por tanto no se puede continuar con la carga de la tabla", "Error de carga", 0);
                return;
            }
            
            Object[]veObject=new Object[cant_column];
            while (rs.next()) {
                for (int i = 0; i < cant_column; i++) {
                    veObject[i]=rs.getString(i+1);
                }
                dtm.addRow(veObject);
            }

            for (int i=0; i<posCombo.length; i++) {
                tabla.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(combos[i]));
                comJCombox.setComboxManual(combos[i], tabla.getValueAt(i, posCombo[i])+"");
                tabla.setValueAt(comJCombox.getComboxString(combos[i], 2), i, posCombo[i]);
            }
            bd.cierraConexion(); 
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Ocurrió un error\n"+e.getMessage(), "ATENCIÓN",0);
        } 
    }
    
    /**
    * Retorna un vector de enteros con las posiciones que se encuentran chequeados, ya sea verdadero o falso. Si colocamos true, detecta todos los true y si false entonces retorna todos los rows con false
    * @param jTable - Es la tabla asociada
    * @param colBoo - Decimos que numero de columna es el boolean
    * @param checkBoo - Decimos que tipo de dato va a detectar, ya sea true o false
    * @return
    */
    public static int[]getRowTrueFalseCheck(JTable jTable, int colBoo, Boolean checkBoo) {
        colBoo-=1;int c=0;
        int[]veRow= new int[jTable.getRowCount()];
        int[]veRowAux;
        for (int i = 0; i < jTable.getRowCount(); i++)
        {
            try 
            {
                if(Boolean.parseBoolean(jTable.getValueAt(i, colBoo).toString())==checkBoo)
                {
                    veRow[c]=i;
                    c++;
                }
            } catch (Exception e) {
            }
        }
        veRowAux = new int[c];
        for (int i = 0; i < c; i++)
            veRowAux[i]=veRow[i];
        return veRowAux;
    }
    
    /**
     * Actualiza un registro seleccionado para cambiarlo por el nuevo valor. Recordar que, tanto la cantidad de columnas como los datos a actualizar deben ser iguales. <p>
     * Este metodo solamente funciona bien si el registro esta seleccionado, de otro modo no hace nada.
     * @param jTable
     * @param veDatosUpdate 
     */
    public static void setValueATupdate(JTable jTable, String[]veDatosUpdate) {
        if (jTable.getColumnCount()!=veDatosUpdate.length) {
            JOptionPane.showMessageDialog(null, "La cantidad de datos a actualizar, no coinciden con la cantidad de columnas de la tabla", "Integridad", 0);
            return;
        }
        try {
            for (int i = 0; i < veDatosUpdate.length; i++) 
                jTable.setValueAt(veDatosUpdate[i], jTable.getSelectedRow(), i);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Hubo errores al procesar el archivo, podria ser porque el registro en la tabla no fue seleccionado", "Advertencia", 0);
        }
    }
    /**
     * Devuelve en un vector los encabezados de las columna de una jTable.
     * @param jTable
     * @return 
     */
    public static String[]getHeardersName(JTable jTable) {
        String[]veDatos = new String[jTable.getColumnCount()];
        for (int i = 0; i < jTable.getColumnCount(); i++) 
            veDatos[i]=jTable.getColumnModel().getColumn(i).getHeaderValue().toString();
        return veDatos;
    }
    /**
     * Metodo que permite retornar la posicion de un dato que estamos buscando. Donde colocamos exactamente el numero de columna, siendo 1 referencia a la 1ra. columna, 2do. a la columna 2...<br><br>
     * Este metodo interpreta la 'x' como fila y la 'y' como columna.
     * @param tabla
     * @param datoBuscar
     * @param columna
     * @return 
     */
    public static int getPositionFila(JTable tabla, String datoBuscar, int columna) {
        int tmp=0;
        columna-=1;
        try {
            for (int i = 0; i < tabla.getRowCount(); i++)
                if (tabla.getValueAt(i, columna).equals(datoBuscar))
                    return i;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error mientras se estaba buscando el dato en las columnas");
            return tmp;
        }
        JOptionPane.showMessageDialog(null, "El dato buscado no fue encontrado, por tanto retorna la primera fila");
        return tmp;
    }
        /**
     * Metodo que permite retornar la posicion de un dato que estamos buscando. Donde colocamos exactamente el numero de fila, siendo 1 referencia a la 1ra. fila, 2do. a la fila 2...<br><br>
     * Este metodo interpreta la 'x' como fila y la 'y' como columna.
     * @param tabla
     * @param datoBuscar
     * @param fila
     * @return 
     */
    public static int getPositionColumna(JTable tabla, String datoBuscar, int fila) {
        int tmp=0;
        fila-=1;
        try{
            for (int i = 0; i < tabla.getRowCount(); i++)
                if (tabla.getValueAt(fila, i).equals(datoBuscar))
                    return i;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error mientras se estaba buscando el dato en las filas");
            return tmp;
        }
        if (tmp==0)
            JOptionPane.showMessageDialog(null, "El dato buscado no fue encontrado, por tanto retorna la primera fila");
        return tmp;
    }
    /**
     * Recorre todo el vector de la tabla buscando el dato que deseamos buscar. Si encuentra, retorna la posicion, siendo el orden de int[fila, columna], donde la posicion 0 es la fila y 1 es la columna.
     * @param tabla
     * @param datoBuscar
     * @return 
     */
    public static int[]getPositionFilCol(JTable tabla, String datoBuscar)
    {
        int[]tmp=new int[2];
        for (int fil = 0; fil < tabla.getRowCount(); fil++)
        {
            for (int col = 0; col < tabla.getColumnCount(); col++)
            {
                if (tabla.getValueAt(fil, col).equals(datoBuscar))
                {
                    tmp[0]=fil;
                    tmp[1]=col;
                    return tmp;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "El dato buscado no fue encontrado, por tanto retorna a las primeras filas y columnas");
        return tmp;
    }
    /**
     * En el mismo orden que se inserta los cuadros de textos (dentro del vector), es el mismo orden con que subira a los cuadros de textos cuando el usuario seleccione un registro dentro de la jTable. <br><br>
     * Modo de uso: scatterMemvarSeguido(jTable1, new JTextField[]{jTextField1,jTextField2,jTextField3}); "Seria mas recomendable en el uso de jTable1KeyReleased"
     * @param table
     * @param vectorJText
     */
    public static void scatterMemvarSeguido(JTable table, JTextField[]vectorJText)
    {
        try
        {
            for (int i = 0; i < table.getColumnCount(); i++)
                if(i<vectorJText.length)
                    comJTable.ScatterMemvar(vectorJText[i], table, i+1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por lo menos uno debe de estar seleccionado");
        }
    }
   
    public static class TableToTable
    {
        /**
         * A partir de la seleccion de un registro, permite transferir de una tabla origen a una tabla destino eliminando el seleccionado del origen y transfierendosela al destino.
         * <br><br>Condicion: Las columnas deben ser iguales y las colmnas tienen que ser clases de tipo objeto
         * @param OrigenTable
         * @param DestinoTable
         */
        public static void transferTableToTable(JTable OrigenTable, JTable DestinoTable)
        {
            if (OrigenTable.getSelectedRow() == -1)
            {
                JOptionPane.showMessageDialog(null, "Ningun dato del origen fue seleccionado");
                return;
            }
            if (OrigenTable.getColumnCount()!=DestinoTable.getColumnCount()) {
                JOptionPane.showMessageDialog(null, "Las cantidades de columnas de las tablas no coinciden");
                return;
            }
            copyTableToTable((DefaultTableModel)OrigenTable.getModel(), (DefaultTableModel)DestinoTable.getModel(), OrigenTable.getSelectedRow());
            removeRowjTable(OrigenTable);
        }
        /**
         * A partir de la seleccion de un registro origen copia esos datos al destino.
         * <br><br>Condicion: Las columnas deben ser iguales y las colmnas tienen que ser clases de tipo objeto
         * @param OrigenTable
         * @param DestinoTable
         */
        public static void copyTableToTable(JTable OrigenTable, JTable DestinoTable)
        {
            if (OrigenTable.getSelectedRow() == -1)
            {
                JOptionPane.showMessageDialog(null, "Ningun dato del origen fue seleccionado");
                return;
            }
            if (OrigenTable.getColumnCount()!=DestinoTable.getColumnCount()) {
                JOptionPane.showMessageDialog(null, "Las cantidades de columnas de las tablas no coinciden");
                return;
            }
            copyTableToTable((DefaultTableModel)OrigenTable.getModel(), (DefaultTableModel)DestinoTable.getModel(), OrigenTable.getSelectedRow());
        }
        /**
         * Metodo a nivel interno que hace una copia del registro seleccionado al destino
         * @param OrigenTable
         * @param DestinoTable
         * @param selectRow
         */
        private static void copyTableToTable(DefaultTableModel OrigenTable, DefaultTableModel DestinoTable, int selectRow)
        {
            Object[]obj=new Object[OrigenTable.getColumnCount()];
            for (int i = 0; i < obj.length; i++)
                obj[i]=OrigenTable.getValueAt(selectRow, i);
            DestinoTable.addRow(obj);
        }
        /**
         * Metodo interno que recolecta de una tabla, el registro seleccionado, y lo agrupa en un vector de objetos.
         * @param table
         * @return
         */
        private static Object[]getTableSelectDat(JTable table)
        {
            Object[]obj=new Object[table.getColumnCount()];
            for (int i = 0; i < obj.length; i++)
                obj[i]=table.getValueAt(table.getSelectedRow(), i);
            return obj;
        }
    }
    /**
     * Clase que permite dar opciones a una Table
     */
    public static class opciones
    {
        //                    jTable1.setEnabled(true);  // jTable1.disable(); jTable1.enable(); habilitar/deshabilitar
        /**
         * Habilita/Deshabilita las lineas horizontales de una grilla
         * @param table
         * @param boo
         */
        public static void setShowHorizontalLines(JTable table, Boolean boo)
        {
            table.setShowHorizontalLines(boo);
        }
        /**
         * Habilita/Deshabilita las lineas verticales de una grilla
         * @param table
         * @param boo
         */
        public static void setShowVerticalLines(JTable table, Boolean boo)
        {
            table.setShowVerticalLines(boo);
        }
        /**
         * Habilita/Deshabilita las lineas de una grilla
         * @param table
         * @param boo
         */
        public static void setShowGrid(JTable table, Boolean boo)
        {
            table.setShowGrid(boo);
        }
        /**
         * Habilita o deshabilita para la seleccion dentro de un grid. <br><br>
         * Anteriormente se usaba  jTable1.disable(); jTable1.enable();
         * @param table
         * @param boo
         */
        public static void setEnabled(JTable table, Boolean boo)
        {
            table.setEnabled(boo);
        }
        /**
         * Metodo que permite seleccionar un conjunto de registros si es true, sino no selecciona ninguno
         * @param table
         * @param boo
         */
        public static void setSelectionRow(JTable table, Boolean boo)
        {
            if (boo)
                table.selectAll();
            else
                table.clearSelection();
        }
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Agregar combox en jTable">
    private static class MyComboBoxRenderer extends JComboBox implements TableCellRenderer
    {
        public MyComboBoxRenderer(String[] items) {
            super(items);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column)
        {
            if (isSelected)
            {
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            // Select the current value
            setSelectedItem(value);
            return this;
        }
    }

    private static class MyComboBoxEditor extends DefaultCellEditor
    {
        public MyComboBoxEditor(String[] items)
        {
            super(new JComboBox(items));
        }
    }
    
    /**
     * A partir de una fila (dentro de un jtable) lo convierte en un vector de String. Ejemplo de uso: String[]vedat = comJTable.getColumnaToVector(jTable1, 3);
     * @param table
     * @param fila
     * @return
     */
    public static String[]getFilaToVector(JTable table, int fila)
    {
        ArrayList lista = new ArrayList();
        String[]veDatos;
        for (int i = 0; i < table.getColumnCount(); i++)
            lista.add(table.getValueAt(fila-1, i));

        veDatos = new String[lista.size()];
        try
        {
            for (int i = 0; i < lista.size(); i++)
                veDatos[i]=lista.get(i).toString();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo errores extraer los datos de la tabla porque uno o mas combox se encuentran vacios");
            return new String[]{};
        }

        return veDatos;
    }


    
    /**
     * Clase que permite la personalizacion variada de una fila o row
     */
    public static class Filas
    {
        /**
         * Permite mover una fila seleccionada subiendo un nivel arriba
         * @param table
         */
        public static void rowSelectUp(JTable table)
        {
            if (table.getSelectedRow()-1<0)
                return;
            changePositionRowAndRow(table, -1);
        }
        /**
         * Permite mover una fila seleccionada bajando un nivel abajo.
         * @param table
         */
        public static void rowSelectDown(JTable table)
        {
            if (table.getSelectedRow()+1 >= table.getRowCount() || table.getSelectedRow()==-1)
                return;
            changePositionRowAndRow(table, +1);
        }
        /**
         * Selecciona una fila a partir del cual le digamos que fila. Ya sea el primero como 1 o el ultimo (si es 10) como 10.
         * @param table
         */
        public static void rowSelectSet(JTable table, int fila)
        {
            if (fila <= 0 || fila >table.getRowCount() ) {
                return;
            }
            table.setRowSelectionInterval(fila-1, fila-1);
        }
        /**
         * A partir de la seleccion de un row lo mueve segun le especificamos, ya sea +1 posicion, -1 posicion o -3 posiciones. Y al final lo selecciona segun el objeto fue movido. <hr>
         * Este metodo no realiza ningun control de errores, tener cuidado a la hora de usarlo. Ejemplo:
         * @param table
         * @param positionNewRow
         */
        private static void changePositionRowAndRow(JTable table, int positionNewRow)
        {
            Object obj;
            for (int i = 0; i < table.getColumnCount(); i++)
            {
                obj = table.getValueAt(table.getSelectedRow(), i);
                table.setValueAt(table.getValueAt(table.getSelectedRow()+positionNewRow, i), table.getSelectedRow(), i);
                table.setValueAt(obj, table.getSelectedRow()+positionNewRow, i);
            }
            table.setRowSelectionInterval(table.getSelectedRow()+positionNewRow, table.getSelectedRow()+positionNewRow);
        }
        /**
         * Coloca a un Row especifico el tamaño de fila
         * @param table
         * @param sizeRow
         */
        public static void setRowHeight(JTable table, int Row, int sizeRow)
        {
            table.setRowHeight(Row, sizeRow);
        }
        /**
         * Coloca a un conjunto de Rows el mismo tamaño de alto
         * @param table
         * @param sizeRow
         */
        public static void setRowHeight(JTable table, int sizeRow)
        {
            table.setRowHeight(sizeRow);
        }
    }
    /**
     * Clase que guarda las interacciones con el uso de las columnas, ya sea para modificar, recibir, colocar, etc.
     */
    public static class Columnas
    {
        /**
         * Edita una columna a un combox, donde recibe los datos con que va a cargar.
         * @param table
         * @param columna
         * @param datosCombox
         */
        public static void EditarACombox(JTable table, int columna, String[]datosCombox)
        {
            try
            {
                TableColumn col = table.getColumnModel().getColumn(columna-1);
                col.setCellEditor(new MyComboBoxEditor(datosCombox));

                // If the cell should appear like a combobox in its
                // non-editing state, also set the combobox renderer
                col.setCellRenderer(new MyComboBoxRenderer(datosCombox));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "hubo errores al cargar el combox, podria ser porque la columna que intenta modificar se encuentra fuera de rango");
            }
        }
            /**
         * Metodo que permite enumerar de menor a mayor (de 1 a n) asignando a una columna especifica.
         * @param jTable
         * @param ColumnSuma
         * @return
         */
        public static void Enumerar(JTable jTable, int ColumnOrden)
        {
            ColumnOrden-=1;
            if (isEmptyJtable(jTable)){
                return;
            }
            DefaultTableModel djTable= (DefaultTableModel) jTable.getModel();
            try
            {
                for(int x=0;x<djTable.getRowCount();x++)
                    jTable.setValueAt((x+1), x, ColumnOrden);
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hubo errores al buscar el valor maximo, podria ser porque existe en la columna algun caracter", "Errores en carga", 0);
                return;
            }
            return;
        }
            /**
         * Retorna verdadero si el dato se encuentra, de otro modo es falso. Si encuentra un dato que existe
         * no muestra mensaje como el metodo <b>ColumnaExisteStringMensaje</b>. Es un metodo que solamente verifica una columna por vez
         * @param jTable1
         * @param columnVerifi
         * @param datoBuscar
         * @return
         */
        public static boolean isExisteString(JTable jTable1, int columnVerifi, String datoBuscar)
        {
            //recordar que columnVerifi empieza desde 1
           columnVerifi=columnVerifi-1;
           String tmp="";
           DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
           jTable1.selectAll();
           int[] X = jTable1.getSelectedRows();

           for(int c = (X.length-1); c >= 0; --c)
           {
                tmp=dtm.getValueAt(X[c], columnVerifi).toString();
                if(tmp.equals(datoBuscar))
                    return true;
           }
           return false;
        }
        /**
         * Permite analizar una jTable para saber si encuentra dos o mas datos duplicados dentro de un jTable
         * Para que funcione bien este metodo es necesario que se coloque de la misma forma con que se va a
         * analizar dicho fichero, siendo el ejemplo de la siguiente manera: column[]{1,5}
         * "Son las columnas 1 y 5"; luego string[]{txt1,txt5} "txt1 hace referencia en la columna 1 y el
         * 5 de la misma forma a la columna 5"; El resultado de que encuentre retorna true
         * @param jTable1
         * @param columnVerifi
         * @param datoBuscar
         * @return
         */
        public static boolean isExisteString(JTable jTable1, int[]columnVerifi, String[]datoBuscar)
        {
               jTable1.selectAll();
                   int[] X = jTable1.getSelectedRows();
                   for (int i = 0; i < columnVerifi.length; i++)
                       columnVerifi[i]-=1;
                   int igual=0;
                   try
                   {
                       for(int c = (X.length-1); c >= 0; --c)
                       {
                           igual=0;
                           for (int i = 0; i < columnVerifi.length; i++)
                           {
                               if (jTable1.getValueAt(X[c], columnVerifi[i]).equals(datoBuscar[i]))
                                   igual+=1;
                           }
                           if(igual==datoBuscar.length)
                           {
                               return true;
                           }
                       }
                   } catch (Exception e) {
                       e.printStackTrace();
                       JOptionPane.showMessageDialog(null, "Ocurrio un error en la carga, asegurese que esten bien ingresado los datos", "Error de cargas", 0);
                       return false;
                   }
               jTable1.clearSelection();
           return false;
        }
        /**
         * Retorna la suma de una columna segun el valor que le pongamos en criterio.
         * @param jTable - Tabla involucrado
         * @param ColCriterio - Donde se van a buscar los valores que coincidan con <code>criterio</code>
         * @param criterio - Valor a buscar
         * @param ColRangoSuma - Columna suma de enteros
         * @return
         */
        public static int getCondicionSumarSiInt(JTable jTable, int ColCriterio, String criterio, int ColRangoSuma)
        {
            int tmpSuma=0;
            ColCriterio-=1;ColRangoSuma-=1;
            for (int i = 0; i < jTable.getRowCount(); i++)
                if (jTable.getValueAt(i, ColCriterio).equals(criterio))
                    tmpSuma+=Integer.parseInt(jTable.getValueAt(i, ColRangoSuma).toString());
            return tmpSuma;
        }
        /**
         * Retorna la suma de una columna segun los criterios que le pasemos. Por ejemplo, en una columna donde dice empleado y otro los sueldos cobrados en el año, le colocamos el criterio que diga "Juan"; entonces el metodo busca todo lo que contenga en la columna empleado con "Juan" y lo va sumando. El resultado retorna un String como numerico.
         * @param jTable
         * @param ColCriterio - Donde va a buscar
         * @param criterio - La coincidencia que debe encontrar segun el colCriterio
         * @param ColRangoSuma - Donde a va sumar segun el colCriterio
         * @return
         */
        public static String getCondicionSumarSiString(JTable jTable, int ColCriterio, String criterio, int ColRangoSuma)
        {
            int tmpSuma=0;
            ColCriterio-=1;ColRangoSuma-=1;
            for (int i = 0; i < jTable.getRowCount(); i++)
                if (jTable.getValueAt(i, ColCriterio).equals(criterio))
                    tmpSuma+=Integer.parseInt(jTable.getValueAt(i, ColRangoSuma).toString());
            return String.valueOf(tmpSuma);
        }
            /**
         * Sirve para identificar el valor maximo de una tabla y devuelve el entero como retorno.
         * Si no encuentra retorna 0
         * @param jTable
         * @param ColumnSuma
         * @return
         */
        public static int getMaximo(JTable jTable, int ColumnMax)
        {
            int tmpMax=0;ColumnMax-=1;
            DefaultTableModel djTable= (DefaultTableModel) jTable.getModel();
            try
            {
                for(int x=0;x<djTable.getRowCount();x++)
                {
                    if(Integer.parseInt(djTable.getValueAt(x, ColumnMax).toString())>tmpMax)
                        tmpMax=Integer.parseInt(djTable.getValueAt(x, ColumnMax).toString());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hubo errores al buscar el valor maximo, podria ser porque existe en la columna algun caracter", "Errores en carga", 0);
                return 0;
            }
            return tmpMax;
        }
        /**
         * Si encuentra el valor minimo en una tabla retorna ese numero, de otro modo es 0
         * @param jTable
         * @param ColumnSuma
         * @return
         */
        public static int getMinimo(JTable jTable, int ColumnMin)
        {
            if (isEmptyJtable(jTable)){
                JOptionPane.showMessageDialog(null, "La tabla se encuentra vacio, no se puede procesar el pedido", "Maximo", 0);
                return 0;
            }
            int tmpMin=getMaximo(jTable, ColumnMin);ColumnMin-=1;
            DefaultTableModel djTable= (DefaultTableModel) jTable.getModel();
            try
            {
                for(int x=0;x<djTable.getRowCount();x++)
                {
                    if(Integer.parseInt(djTable.getValueAt(x, ColumnMin).toString())<tmpMin)
                        tmpMin=Integer.parseInt(djTable.getValueAt(x, ColumnMin).toString());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hubo errores al buscar el valor maximo, podria ser porque existe en la columna algun caracter", "Errores en carga", 0);
                return 0;
            }
            return tmpMin;
        }
        /**
         * Metodo que permite enumerar de menor a mayor (de 1 a n) asignando a una columna especifica.
         * @param jTable
         * @param ColumnSuma
         * @return
         */
        /**
         * Sirve para identificar el valor maximo de una tabla y devuelve el entero como retorno.
         * Si no encuentra retorna 0
         * @param jTable
         * @param ColumnSuma
         * @return
         */
        public static String getMaximoStr(JTable jTable, int ColumnMax)
        {
            if (isEmptyJtable(jTable)) {
                JOptionPane.showMessageDialog(null, "La tabla se encuentra vacio, no se puede procesar el pedido", "Maximo", 0);
                return "";
            }
            String tmpMax="0";ColumnMax-=1;
            DefaultTableModel djTable= (DefaultTableModel) jTable.getModel();
            try
            {
                for(int x=0;x<djTable.getRowCount();x++)
                    if(Integer.parseInt(djTable.getValueAt(x, ColumnMax).toString())>Integer.parseInt(tmpMax))
                        tmpMax=djTable.getValueAt(x, ColumnMax).toString();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hubo errores al buscar el valor maximo, podria ser porque existe en la columna algun caracter", "Errores en carga", 0);
                return "";
            }
            return tmpMax;
        }
        /**
         * Si encuentra el valor minimo en una tabla retorna ese numero, de otro modo es 0
         * @param jTable
         * @param ColumnSuma
         * @return
         */
        public static String getMinimoStr(JTable jTable, int ColumnMin)
        {
            if (isEmptyJtable(jTable)){
                JOptionPane.showMessageDialog(null, "La tabla se encuentra vacio, no se puede procesar el pedido", "Maximo", 0);
                return "";
            }
            String tmpMin=String.valueOf(getMaximo(jTable, ColumnMin))
                    ;ColumnMin-=1;
            DefaultTableModel djTable= (DefaultTableModel) jTable.getModel();
            try
            {
                for(int x=0;x<djTable.getRowCount();x++)
                {
                    if(Integer.parseInt(djTable.getValueAt(x, ColumnMin).toString())<Integer.parseInt(tmpMin))
                        tmpMin=djTable.getValueAt(x, ColumnMin).toString();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hubo errores al buscar el valor maximo, podria ser porque existe en la columna algun caracter", "Errores en carga", 0);
                return "";
            }
            return tmpMin;
        }
        /**
         * Suma una columna especifica de una jTable
         * @param jTable
         * @param ColumnSuma
         * @return
         */
        public static int getSuma(JTable jTable, int ColumnSuma)
        {
            int tmpSuma=0;ColumnSuma-=1;
            DefaultTableModel dtmsuma= (DefaultTableModel) jTable.getModel();

            try
            {
                for(int x=0;x<dtmsuma.getRowCount();x++)
                    tmpSuma=tmpSuma+Integer.parseInt(dtmsuma.getValueAt(x, ColumnSuma).toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hubo errores al sumar, podria ser porque existe en la columna algun caracter", "Errores en carga", 0);
            }

            return tmpSuma;
        }
        /**
         * Suma una columna que contenga checkboos y segun el booleano que le pasemos tiene en cuenta para sumar
         * @param jTable
         * @param ColumnSuma
         * @param colBoo
         * @param boo
         * @return
         */
        public static int getSumaWithBoolean(JTable jTable, int ColumnSuma, int colBoo, Boolean boo)
        {
            int tmpSuma=0;ColumnSuma-=1;colBoo-=1;
            int[]X=getRowTrueFalseCheck(jTable, colBoo+1, boo);
            try
            {
                for(int x=0;x<X.length;x++)
                    tmpSuma=tmpSuma+Integer.parseInt(jTable.getValueAt(X[x], ColumnSuma).toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hubo errores al sumar, podria ser porque existe en la columna algun caracter", "Errores en carga", 0);
            }

            return tmpSuma;
        }
        /**
         * Suma una columna que posea flotantes o con el uso de comas. Por ejemplo: 2.5 + 3.3 = 5.8 y el resultado retorna un flotante
         * @param jTable
         * @param ColumnSuma
         * @return
         */
        public static float getSumaWitdhComa(JTable jTable, int ColumnSuma)
        {
            float tmpSuma=0;
            ColumnSuma-=1;
            DefaultTableModel dtmsuma= (DefaultTableModel) jTable.getModel();
            try
            {
                for(int x=0;x<dtmsuma.getRowCount();x++)
                    tmpSuma=tmpSuma+Float.parseFloat(dtmsuma.getValueAt(x, ColumnSuma).toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hubo errores al sumar, podria ser porque existe en la columna algun caracter", "Errores en carga", 0);
            }
            return tmpSuma;
        }
        /**
         * Se usa en forma conjunta con el combox dentro de una tabla. <hr>
         * Como los datos de un combox(dentro de una tabla) no se pueden extraer pero si se pueden consultar la parte grafica. Este metodo lo que hace es recibir los codigos correspodientes y guardarlos en un String[].<br><br>
         * Su forma de uso es la siguiente: comJTable.getColumnaIDfromCombox(jTable1, 3, "idproveedor", "proveedor", "nombre");
         *
         * @param table - Tabla asociada que contiene el combox
         * @param colCombox - Nro. de columna del combox
         * @param nameColID - el ID que se utiliza en la BD
         * @param nameTabla - Nombre de la tabla
         * @param nameColCampo - Nombre del campo usado como descripcion
         * @return
         */
        public static String[]getColumnaIDfromCombox(JTable table, int colCombox, String nameColID, String nameTabla, String nameColCampo)
        {
            ArrayList lista = new ArrayList();
            String[]veDatos;
            for (int i = 0; i < table.getRowCount(); i++)
                lista.add(comJSQL.getCampoUnicoStrin("select "+nameColID+" from "+nameTabla+" where "+nameColCampo+" = '"+comJTable.getValueATjTable(table, i+1, colCombox)+"'"));
            veDatos = new String[lista.size()];
            try
            {
                for (int i = 0; i < lista.size(); i++)
                    veDatos[i]=lista.get(i).toString();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hubo errores extraer los datos de la tabla porque uno o mas combox se encuentran vacios");
                return new String[]{};
            }
            return veDatos;
        }
            /**
         * A partir de una columna (dentro de un jtable) lo convierte en un vector de String. Ejemplo: String[]vedat = comJTable.getColumnaToVector(jTable1, 3);
         * @param table
         * @param columna
         * @return
         */
        public static String[]getColumnaToVector(JTable table, int columna)
        {
            ArrayList lista = new ArrayList();
            String[]veDatos;
            for (int i = 0; i < table.getRowCount(); i++)
                lista.add(table.getValueAt(i, columna-1));

            veDatos = new String[lista.size()];
            try
            {
                for (int i = 0; i < lista.size(); i++)
                    veDatos[i]=lista.get(i).toString();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hubo errores extraer los datos de la tabla porque uno o mas combox se encuentran vacios");
                return new String[]{};
            }

            return veDatos;
        }
       
        /**
         * Permite alinear a una columna en el lado derecho
         * @param jTable
         */
        public static void setAlignmentRigth(JTable jTable, int ColumnAlign)
        { 
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setHorizontalAlignment(SwingConstants.RIGHT);
            jTable.getColumnModel().getColumn(ColumnAlign-1).setCellRenderer(tcr);
            jTable.repaint();
        }
        /**
         * Permite alinear a una columna en el centro
         * @param jTable
         */
        public static void setAlignmentCenter(JTable jTable, int ColumnAlign)
        {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setHorizontalAlignment(SwingConstants.CENTER);
            jTable.getColumnModel().getColumn(ColumnAlign-1).setCellRenderer(tcr);
            jTable.repaint();
        }
        /**
         * Permite alinear a una columna en el lado izquierdo
         * @param jTable
         */
        public static void setAlignmentLeft(JTable jTable, int ColumnAlign)
        {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setHorizontalAlignment(SwingConstants.LEFT);
            jTable.getColumnModel().getColumn(ColumnAlign-1).setCellRenderer(tcr);
            jTable.repaint();
        }
            /**
         * Recibe un conjunto de columnas para realizar en grupo las alineaciones
         * @param jTable
         * @param ColumnAlign
         */
        public static void setAlignmentRigth(JTable jTable, int[]ColumnAlign)
        {
            for (int i = 0; i < ColumnAlign.length; i++)
                setAlignmentRigth(jTable, ColumnAlign[i]);
        }
    /**
         * Recibe un conjunto de columnas para realizar en grupo las alineaciones
         * @param jTable
         * @param ColumnAlign
         */
        public static void setAlignmentCenter(JTable jTable, int[]ColumnAlign)
        {
             for (int i = 0; i < ColumnAlign.length; i++)
                setAlignmentCenter(jTable, ColumnAlign[i]);
        }
    /**
         * Recibe un conjunto de columnas para realizar en grupo las alineaciones
         * @param jTable
         * @param ColumnAlign
         */
        public static void setAlignmentLeft(JTable jTable, int[]ColumnAlign)
        {
            for (int i = 0; i < ColumnAlign.length; i++)
                setAlignmentLeft(jTable, ColumnAlign[i]);
        }
            /**
         * Deja a una columna en el modo oculto. Como es un vector, se puede dar como parametro una o mas columnas.
         * @param tbl
         * @param columna
         */
        public static void setHide(JTable tbl, int[]columna)
        {
            for (int i = 0; i < columna.length; i++)
                columna[i]-=1;
            try
            {
                for(int i=0;i<columna.length;i++)
                {
                    tbl.getColumnModel().getColumn(columna[i]).setMaxWidth(0);
                    tbl.getColumnModel().getColumn(columna[i]).setMinWidth(0);
                    tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMaxWidth(0);
                    tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMinWidth(0);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error mientras se ocultaba las columnas porque se selecciono columnas inexistentes");
            }

            for (int i = 0; i < columna.length; i++)
                columna[i]+=1;
        }
        /**
         * Permite colocar a una columna en modo oculto
         * @param tbl
         * @param columna
         */
        public static void setHide(JTable tbl, int columna)
        {
            columna-=1;
            try
            {
                    tbl.getColumnModel().getColumn(columna).setMaxWidth(0);
                    tbl.getColumnModel().getColumn(columna).setMinWidth(0);
                    tbl.getTableHeader().getColumnModel().getColumn(columna).setMaxWidth(0);
                    tbl.getTableHeader().getColumnModel().getColumn(columna).setMinWidth(0);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error mientras se ocultaba las columnas porque se selecciono columnas inexistentes");
            }
            columna+=1;
        }
        /**
         * Coloca a una columna en el modo visible.
         * <p>
         * Su ancho estandar es como minimo 15 y su maximo es 300. El modo normal con que se muestra es de 75 pulgadas.
         * @param tbl
         * @param columna
         */
        public static void setShow(JTable tbl, int[]columna)
        {
            for (int i = 0; i < columna.length; i++)
                columna[i]-=1;
            for(int i=0;i<columna.length;i++)
            {
                tbl.getColumnModel().getColumn(columna[i]).setMaxWidth(450);
                tbl.getColumnModel().getColumn(columna[i]).setMinWidth(15);
                tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMaxWidth(450);
                tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMinWidth(15);
                tbl.getColumnModel().getColumn(columna[i]).setPreferredWidth(75); //en vez de la utilizacion del setWidth que tiene otra funcion para su uso
            }
        }
        /**
         * De forma intercalada oculta o muestra una columna.
         * @param tbl
         * @param columna
         */
        public static void setShowHide(JTable tbl, int[]columna)
        {
            if(columna.length<=0){
                JOptionPane.showMessageDialog(null, "No se puede procesar porque el vector columna se encuentra vacio", "Datos vacios", 0);
                return;
            }
            for (int i = 0; i < columna.length; i++)
                columna[i]-=1;
            for(int i=0;i<columna.length;i++)
                if(tbl.getColumnModel().getColumn(columna[i]).getWidth()!=0)
                    setHide(tbl, new int[]{columna[i]+1});
                else
                    setShow(tbl, new int[]{columna[i]+1});
        }
        /**
         * Coloca a un conjunto de columnas un tamaño especificado segun los parametros que le vallemos asignando. Ejemplo:<p>
         * setWidth(tablaLocal, new int[]{1,2,3}, new int[]{100,120,60}); <br>
         * Donde decimos que la columna 1 su tamaño es 100, columna 2 es 120 y columna 3 es 60
         * @param tbl
         * @param columna
         * @param tamaño
         */
        public static void setWidth(JTable tbl, int[]columna, int[]tamaño)
        {
            if (columna.length!=tamaño.length) {
                JOptionPane.showMessageDialog(null, "Las columnas y los tamaños son desiguales", "Desigualdad", 0);
                return;
            }
            for (int i = 0; i < columna.length; i++)
                columna[i]-=1;
            for(int i=0;i<columna.length;i++)
                tbl.getColumnModel().getColumn(columna[i]).setPreferredWidth(tamaño[i]); //en vez de la utilizacion del setWidth que tiene otra funcion para su uso
        }
        /**
         * Se le coloca a todas las columnas con el mismo tamaño dentro de un jTable
         * @param tbl
         * @param tamaño
         */
        public static void setWidthAll(JTable tbl, int tamaño)
        {
            for(int i=0;i<tbl.getColumnCount();i++)
                tbl.getColumnModel().getColumn(i).setPreferredWidth(tamaño); //en vez de la utilizacion del setWidth que tiene otra funcion para su uso
        }
            /**
         * Coloca a una columna de booleans ya sea, todo checqueado o no.
         * @param jTable
         * @param buleano
         * @param columnBoolean
         */
        public static void setSelectAllBoolean(JTable jTable, boolean buleano, int columnBoolean)
        {
                columnBoolean-=1;
                for(int x=0;x<jTable.getRowCount();x++)
                {
                        jTable.setValueAt(buleano, x, columnBoolean);
                }
        }
        /**
         * Permite colocar a una columna boolean de forma inversa (de true a false y viceversa).
         * @param jTable
         * @param columnBoolean
         */
        public static void setSelectAllBooleanInversa(JTable jTable, int columnBoolean)
        {
             columnBoolean-=1;
             Object[]obj = new Object[1];
                for(int x=0;x<jTable.getRowCount();x++)
                {
                    try
                    {
                        obj[0]=!Boolean.parseBoolean(jTable.getValueAt(x, columnBoolean).toString());
                    } catch (Exception e) {
                        //JOptionPane.showMessageDialog(null, "No se puede cambiar de valor, podria ser porque la columna no se puede editar");
                        obj[0]=true;
                    }
                    jTable.setValueAt(obj[0], x, columnBoolean);
                }
        }
        /**
         * Recibe en un vector de enteros con los datos que haya encontrado como oculto de las columnas. Por ejemplo: si la columna 1 se encuentra oculto retorna int[]{0}.
         * @param table
         * @return
         */
        public static int[]getHide(JTable table)
        {
            ArrayList lista = new ArrayList();
            for (int i = 0; i < table.getColumnCount(); i++)
            {
                if (table.getColumnModel().getColumn(i).getWidth()==0)
                {
                    lista.add(i);
                }
            }
            int[]veInt = new int[lista.size()];
            for (int i = 0; i < lista.size(); i++)
            {
                veInt[i]=Integer.parseInt(lista.get(i).toString());
            }
            return veInt;
        }

        /**
         * Recibe en un vector de enteros con los datos que haya encontrado como oculto de las columnas. Por ejemplo: si la columna 1 se encuentra oculto retorna int[]{1}.
         * Este metodo ya le suma los valores no es como el anterior int[]getHide
         * @param table
         * @return
         */
        public static int[]getHideSum(JTable table)
        {
            ArrayList lista = new ArrayList();
            for (int i = 0; i < table.getColumnCount(); i++)
            {
                if (table.getColumnModel().getColumn(i).getWidth()==0)
                {
                    lista.add(i+1);
                }
            }
            int[]veInt = new int[lista.size()];
            for (int i = 0; i < lista.size(); i++)
            {
                veInt[i]=Integer.parseInt(lista.get(i).toString());
            }
            return veInt;
        }
        /**
         * Coloca a un encabezado, segun el boolean, para que se pueda dimensionar (si true) de otro modo no, cambiando el ancho de la columna a la forma deseada
         * @param table
         * @param boo
         */
        public static void setHeaderResizingAllowed(JTable table, Boolean boo)
        {
            table.getTableHeader().setResizingAllowed(boo);
        }
        /**
         * Coloca a las columnas para que no se pueda reordenar si es false el parametro, de otro modo es reordenable. Para cambiar de ubicacion entre columnas
         * @param table
         * @param boo
         */
        public static void setHeaderReorderingAllowed(JTable table, Boolean boo)
        {
            table.getTableHeader().setReorderingAllowed(boo);
        }
        /**
         * Permite cambiar el editor de columnas pasandole nuevos parametros. Ejemplo: new int[]{1}<br>
         * De modo que, ahora la columna 1 va a poder ser editable y el resto no.
         * <hr>
         * <b>Advertencia:</b><br>
         * Tener mucho cuidado a la hora de usar este metodo, pues, las tablas asociadas que utilicen para cargar sus grillas a partir del comJTable tiende a variar su colEdit si es que en otros le habiamos
         * especificado que la 1ra. columna editable y luego con este metodo le cambiamos a otra table en editable columna 2; entonces tanto como el 1ro. como el 2do. table serán editables la 2 columna.
         * @param newColEdit
         */
        public static void changeEdit(int[]newColEdit)
        {
            for (int i = 0; i < newColEdit.length; i++)
                newColEdit[i]-=1;
            MiModelo.setColEdit(newColEdit);
        }
    }
}
