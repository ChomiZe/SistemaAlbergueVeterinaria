/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comunes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.codehaus.groovy.tools.shell.Main;

/**
 *
 * @author joarevalos
 */
public class comValidarFechas {
 
    public enum ErrorType {
 
        EXITO,
        FECHAS_FUERA_DE_RANGO,
        NO_ES_NUMERO,
        LONGITUD_INVALIDA;
    }
 
    public static ErrorType validar_Fechas_entre_rangos(Date Dato, Date FechaInicial, Date FechaFinal) {
        ErrorType Error = ErrorType.EXITO;
        if (Dato.after(FechaInicial) && Dato.before(FechaFinal)) {
            Error = ErrorType.EXITO;
        }else{
            Error = ErrorType.FECHAS_FUERA_DE_RANGO;
        }
        return Error;
    }//fin del metodo
 
    private static boolean Fecha_Valida_Formato(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy",
                    Locale.getDefault());
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
 
    public static void main(String[] args) {
        // Aquí todo el código de la aplicación.
        //Verificamos si tiene el formato que queremos que tenga
        boolean fecha = Fecha_Valida_Formato("31/12/2010");
        //Lo informamos
        if (fecha == true) {
            JOptionPane.showMessageDialog(null, "La fecha es válida");
        } else {
            JOptionPane.showMessageDialog(null, "La fecha es no es válida");
        }
        //Apelamos a otra función  para validar si la fecha se encuentra en
        //en un determinado rango.
        try {
            String FechaI = "01-01-2010";
            String FechaF = "31-12-2010";
            String Fecha_a_analizar = "15-02-2000";
 
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            Date Fi = formato.parse(FechaI);
            Date Ff = formato.parse(FechaF);
            Date Fa = formato.parse(Fecha_a_analizar);
            //llamamos a la función. y capturamos el valor devuelto.
            ErrorType n = validar_Fechas_entre_rangos(Fa, Fi, Ff);
            //Lo informamos.
            JOptionPane.showMessageDialog(null, n);
 
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}