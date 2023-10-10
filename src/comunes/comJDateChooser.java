/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comunes;

import com.toedter.calendar.JDateChooser;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author pcsproject
 */
public class comJDateChooser {
    /**
     * Metodo que permite recibir de un jDateChooser su fecha
     * en la cual fue seleccionada
     * @param tmp
     * @return 
     */
    public static String getDatejDateChooser(JDateChooser tmp)
    {
        return String.valueOf(tmp.getDate().getYear()+1900)+"-"+String.valueOf(tmp.getDate().getMonth()+1)+"-"+String.valueOf(tmp.getDate().getDate());
    }
    /**
     * Metodo que consiste en colocar a un jDateChooser una fecha deseada
     * Para colocar un parametro se requiere con los guiones como:
     * 2011-05-05
     * Porque el metodo analiza en guiones para separar
     * @param tmp
     * @param Fecha_yyyyMMdd 
     */
    public static void setDatejDateChooser(JDateChooser tmp, String Fecha_yyyyMMdd) {
        int anho=0, mes=0, dia=0;
        String tmp2="";
        Calendar actual2;
        actual2 = Calendar.getInstance();
        if(Fecha_yyyyMMdd.length()>=8) {
            try {
                for(int x=0,c=0;x<Fecha_yyyyMMdd.length();x++) {
                    if(Fecha_yyyyMMdd.substring(x, x+1).equals("-")) {
                        c+=1;
                        tmp2="";
                    }
                    else {
                        tmp2=tmp2+Fecha_yyyyMMdd.substring(x, x+1);
                        switch(c) {
                            case 0:
                                anho=Integer.valueOf(tmp2);
                                break;
                            case 1:
                                mes=Integer.valueOf(tmp2);
                                break;
                            default:
                                dia=Integer.valueOf(tmp2);
                                break;
                        }
                    }
                }
                actual2.set(anho, (mes-1), dia);
                tmp.setCalendar(actual2);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "La fecha ingresada no es valida, recuerde: yyyy-mm-dd", "Error de Fecha", 0);
                tmp.setDate(null);
            }
        }
        else
        {
            tmp.setDate(null);
        }
    }
}
