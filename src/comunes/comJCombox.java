/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comunes;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author pcsproject
 */
public class comJCombox
{
    /**
     * Coloca a un conjunto de combox ya sea para habilitar o deshabilitar. Obsoleto desde la implementacion del ajPanel (deshabilitar o habilitar en grupo)
     * @param JComboBox
     * @param booleano 
     */
//    public static void combox(JComboBox[]JComboBox, boolean booleano)
//    {
//        for(int x=0;x<JComboBox.length;x++)
//            JComboBox[x].setEnabled(booleano);
//    }
    /**
     * Ubica la posicion del item buscado si encuentra segun la columna que le pasemos para dicha busqueda.
     * @param jcomb
     * @param txtBus
     * @param columnBus 
     */
    public static void setComboxPosition(JComboBox jComboxTemp, String itemBuscar, int ColumnBuscar) {
        ColumnBuscar-=1;
        int passDato = 0; //es el que recorre el vector y captura la posicion
        boolean encontrado=false;
        try {
            for(int x=0;x<jComboxTemp.getItemCount();x++) {
                jComboxTemp.setSelectedIndex(x);
                if(String.valueOf((((String[])jComboxTemp.getSelectedItem())[ColumnBuscar])).equals(itemBuscar.trim())) {
                    encontrado=true;
                    passDato=x;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar, asegúrese que estén cargados los combox", "Búsquedas", 0);
            return;
        }
        if (encontrado)
            jComboxTemp.setSelectedIndex(passDato);
        else {
            JOptionPane.showMessageDialog(null, "El item buscado no fue encontrado, por tanto, vuelve al estándar de "+(((String[])jComboxTemp.getSelectedItem())[1]), "Items Buscar", 0);
        }
    }
    /**
     * Recibe de un combox los datos como un String, el <code>returnColumn</code>
     * se refiere a que columna segun nuestra consulta debe retornar, ya sea 
     * <code>Codigo</code> o <code>Descripcion</code>.
     */
    public static String getComboxString(JComboBox jcombox, int returnColumn) {
        returnColumn-=1;
        return (((String[])jcombox.getSelectedItem())[returnColumn]);
    }
    
    public static Object[] getComboxStringArray(JComboBox jcombox, int returnColumn) {
        returnColumn-=1;
        Object valores[] = new Object[jcombox.getItemCount()];
        for (int i=0; i<jcombox.getItemCount(); i++) {
            valores[i] = (((String[])jcombox.getItemAt(i))[returnColumn]);
        }
        return valores;
    }
    
    /**
     * Recibe de un combox los datos como un int, el <code>returnColumn</code>
     * se refiere a que columna segun nuestra consulta debe retornar, ya sea
     * <code>Codigo</code> o <code>Descripcion</code>.
     * @param jcombox
     * @param returnColumn
     * @return
     */
    public static int getComboxInt(JComboBox jcombox, int returnColumn) {
        returnColumn-=1;
        return Integer.parseInt((((String[])jcombox.getSelectedItem())[returnColumn]));
    }
    
    public static void vaciarCombox(JComboBox[]vejCombox) {
        Conexion c = new Conexion();
        c.estaConectado();
        for (int i = 0; i < vejCombox.length; i++) {
            vejCombox[i].removeAllItems();
            c.cargarCombo("select '',''", vejCombox[i]);
        }
        c.cierraConexion();
    }
    /**
     * Permite cargar un combox con la forma de vector una dimension (String[])
     * @param comboBox
     * @param veDatos
     */
    public static void cargarDatos(JComboBox comboBox, String[]veDatos) {
        comboBox.removeAllItems();
        for (int i = 0; i < veDatos.length; i++) {
            comboBox.addItem(veDatos[i]);
        }
    }
    /**
     * Permite cargar un conjunto de combox con la forma de vector una dimension (String[])
     * @param comboBox
     * @param veDatos
     */
    public static void cargarDatos(JComboBox[]comboBox, String[]veDatos) {
        for (int i = 0; i < comboBox.length; i++)
            cargarDatos(comboBox[i], veDatos);
    }
    /**
     * Permite posicionar el indice de un combo a partir de la busqueda de un String.<br>
     * Este metodo solamente evalua un combo predeterminado y no de doble dimension
     * @param comboBox
     * @param datoBuscar
     */
    public static void setComboxManual(JComboBox comboBox, String datoBuscar) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).toString().equals(datoBuscar)) {
                comboBox.setSelectedIndex(i);
            }
        }
    }
    /**
     * Recibe los datos de un combo seleccionado. Su nombre se debe porque el combox no fue cargado a traves de una consulta sql
     * @param comboBox
     * @return
     */
    public static String getComboxManual(JComboBox comboBox)
    {
        return comboBox.getSelectedItem().toString();
    }

}
