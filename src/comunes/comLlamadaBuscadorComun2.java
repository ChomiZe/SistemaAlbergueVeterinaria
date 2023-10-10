package comunes;

import Frames.buscadorComun2;
import com.toedter.calendar.JDateChooser;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
/**
 *
 * @author pcsproject
 */
public class comLlamadaBuscadorComun2 {

    public static Boolean BuscadorjDialog(String campos, String tabla, String[][] campoID, String[] campobusqueda, String condicion, int[]ColumnSelect, String[]veTitle, int[]columnHide, int[] dialogSize, int[] columnSizes, String defaultComboItem) {
        calljDialog(campos, tabla, campoID, campobusqueda, condicion, ColumnSelect, veTitle, columnHide, dialogSize, columnSizes, defaultComboItem);
        return buscadorComun2.getVeDato().length != 0;
    }
   
    public static Boolean BuscadorjDialog(String sql, String[]veTitle, String Campos, String[][] veCamposID, int[] hideColumns, int[] dialogSize, int[] columnSizes, String defaultComboItem) {
        int[]ColumnSelect = new int[veTitle.length];
        for (int i=0; i<ColumnSelect.length; i++)
            ColumnSelect[i] = (i+1);
        //calljDialog(comJSQL.getCamposSQL(sql), comJSQL.getTablaSQL(sql), comJSQL.getCamposIDSQL(sql), comJSQL.getWhereSQL(sql), comJSQL.getFinalWhereSQL(sql), comJSQL.getPassCamposToVector(sql), ColumnSelect, veTitle, new int[]{});
        String where = "";
        if (!comJSQL.getWhereSQL(sql).isEmpty()) {
            where = "where "+comJSQL.getWhereSQL(sql);
        }
        calljDialog(comJSQL.getCamposSQL(sql), comJSQL.getTablaSQL(sql), veCamposID, comJSQL.getPassCamposToVector(sql), where, ColumnSelect, veTitle, hideColumns, dialogSize, columnSizes, defaultComboItem);
        if (buscadorComun2.getVeDato().length == 0)
            return false;
        return true;
    }

    public static String[]BuscadorjDialogetString() {
        return buscadorComun2.getVeDato();
    }
    /**
     * Retorna la columna que seleccionemos como un String
     * @param columna
     * @return
     */
    public static String BuscadorjDialogetStringCol(int columna) {
        return buscadorComun2.getVeDato()[columna-1];
    }
    /**
     * Permite la insercion en forma masiva, atendiendo al orden con que consultamos e insertamos en el grupo de objetos. <br><br>
     * Advertencia: El combox solamente evalua la parte de texto o descripcion.
     * @param OrdenDatos
     */
    public static void BuscadorjDialogetStringObjSeq(Object[]OrdenDatos) {
        JTextField jText;
        JComboBox jCom;
        JFormattedTextField jFT;
        JDateChooser jDat;
        JCheckBox jchk;
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
                        comJCombox.setComboxPosition(jCom, BuscadorjDialogetString()[i], 1);
                    }
                    else
                        if (OrdenDatos[i].getClass().equals(JFormattedTextField.class))
                        {
                            jFT=(JFormattedTextField)OrdenDatos[i];
                            jFT.setText(BuscadorjDialogetString()[i]);
                        }
                        else
                            if (OrdenDatos[i].getClass().equals(JDateChooser.class))
                            {
                                jDat=(JDateChooser)OrdenDatos[i];
                                comJDateChooser.setDatejDateChooser(jDat, BuscadorjDialogetString()[i]);
                            }
                             else
                                if (OrdenDatos[i].getClass().equals(JCheckBox.class))
                                    {
                                        String selchk = BuscadorjDialogetString()[i];
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
    /**
     * Permite insertan en forma masiva y aleatoria, los datos que, despues de una consulta, hallan sido seleccionados.<br><br>
     * Advertencia: El combox solamente evalua la parte de texto o descripcion como mejor sea.<hr>
     * El vector de objeto va como sigue de ejemplo: new Object[]{combox,textfield,jdatechooser,jformatted}
     * @param OrdenDatos
     * @param selectColumn
     */
    public static void BuscadorjDialogetStringObjAlt(Object[]OrdenDatos, int[]selectColumn) {
        // <editor-fold defaultstate="collapsed" desc="Condicion">
        if (OrdenDatos.length != selectColumn.length) {
            JOptionPane.showMessageDialog(null, "El orden de datos y la seleccion no coinciden");
            return;
        }// </editor-fold>
        JTextField jText;
        JComboBox jCom;
        JLabel jLbl;
        JFormattedTextField jFT;
        String cadena;
        Conexion c = new Conexion();
        c.estaConectado();

        for (int i = 0; i < selectColumn.length; i++)
            selectColumn[i]-=1;
        try {
            for (int i = 0; i < OrdenDatos.length; i++) {
                if (OrdenDatos[i].getClass().equals(JTextField.class)) {
                    jText=(JTextField)OrdenDatos[i];
                    jText.setText(BuscadorjDialogetString()[selectColumn[i]]);
                }
                else {
                    if (OrdenDatos[i].getClass().equals(JComboBox.class)) {
                        jCom=(JComboBox)OrdenDatos[i];
                        comJCombox.setComboxPosition(jCom, BuscadorjDialogetString()[selectColumn[i]], 2);
                    }
                    else {
                        if (OrdenDatos[i].getClass().equals(JFormattedTextField.class)) {
                            jFT=(JFormattedTextField)OrdenDatos[i];
                            jFT.setText(BuscadorjDialogetString()[selectColumn[i]]);
                        } else {
                            if (OrdenDatos[i].getClass().equals(JLabel.class)) {
                                jLbl=(JLabel)OrdenDatos[i];
                                jLbl.setText(BuscadorjDialogetString()[selectColumn[i]]);
                            }
                            else {
                                cadena = BuscadorjDialogetString()[selectColumn[i]];
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Uno o más datos se encuentran fuera de rango seleccionado\n"+e);
        }
        c.cierraConexion();
    }

    public static void BuscadorjDialogetStringTabla(JTable jTable, int fila, int[]columnastabla, int[]selectColumn) {
        for (int i = 0; i < selectColumn.length; i++) {
            selectColumn[i] -= 1;
        }
        try {
            
            comJTable.setValues(jTable, fila, columnastabla, selectColumn, BuscadorjDialogetString());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public static void BuscadorjDialogetStringTabla(JTable jTable, int[]columnastabla, int[]selectColumn) {
        for (int i = 0; i < selectColumn.length; i++)
                selectColumn[i]-=1;
        try {
            comJTable.setValuesVariasFilas(jTable, BuscadorjDialogetString().length, columnastabla, selectColumn, BuscadorjDialogetString());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
        
    /**
     * Llama al buscador jDialog
     */
    private static void calljDialog(String campos, String tabla, String[][] camposID, String[] camposbusqueda, String condicion, int[]ColumnSelect, String[]veTitle, int[]columnHide, int[] DialogSize, int[] columnSizes, String defaultComboItem) {
        //buscadorComun2 bbb = new buscadorComun2(null, true, campos, tabla, campoID, campoBusqueda, ColumnSelect, veTitle, columnHide);
        buscadorComun2 bbb = new buscadorComun2(new JDialog(), true, camposID, tabla, campos, camposbusqueda, condicion, ColumnSelect, veTitle, columnHide, columnSizes, defaultComboItem);
        bbb.setSize(DialogSize[0],DialogSize[1]);
        bbb.setVisible(true);
        bbb.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                e.getWindow().dispose();
            }
        });
    }
}
