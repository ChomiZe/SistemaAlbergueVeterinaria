/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package comunes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;



/**
 * Modulo donde se concentra la iteracion o enrutamiento con ficheros, de tal modo que pueda interactuar el programador.
 */
public class comJFile
{
    public static final String RUTA_LOCAL = System.getProperty("user.dir");
   
    /**
     * Determina si el contenido de un fichero existe(true) o no(false)
     * @param rutaCompleta
     * @return
     */
    public static boolean isExisteFichero(String rutaCompleta)
    {
        return new File(rutaCompleta).exists();
    }
    /**
     * A partir de la lectura de un fichero *.txt (o cualquier fichero soportado por el bloc de notas) retorna como un String
     * @param rutaCompleta
     * @return
     */
    public static String getStringFromFileTXT(String rutaCompleta)
    {
        String tmpAux = "";
        if(rutaCompleta.compareTo("") != 0)
        {
            FileReader fichero = null;
            try
            {
                fichero = new FileReader(rutaCompleta);
                BufferedReader br = new BufferedReader(fichero);

                String linea;
                while((linea=br.readLine())!=null)
                {
                   tmpAux += linea + "\n\r";
                }
                if(tmpAux.length() > 2)
                {
                    tmpAux = tmpAux.substring(0, tmpAux.length()-2);
                }
                br.close();
                fichero.close();
            }
            catch (IOException ex)
            {
            }
        }
        return tmpAux;
    }
    /**
     * Coloca un skin determinado para el uso del sistema
     * @param nro
     */

    /**
     * Carga un reporte donde recibe solamente el sql y el nombre del reporte sin la extension
     * @param sql
     * @param iReport_jasper
     */
    public static void cargarReporte(String sql, String iReport_jasper)
    {
        comGeneraReportes g = new comGeneraReportes();
        g.generaReportes(sql, iReport_jasper);
    }
}
