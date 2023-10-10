/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package comunes;
import comunes.Conexion;
import java.sql.ResultSet;
import java.net.URL;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author pcsproject
 */
public class comGeneraReportes
{

    public comGeneraReportes() {
    }

    /**
     * Recibe la consulta sql para generar el reporte segun el jasper y los parametros que van de la siguiente manera:<br><br>
     * [posicion][parametroJasper(0), valor(1)]
     * @param sql
     * @param iReport_jasper
     * @param ve_parameters 
     */
    public void generaReportes_ve(String sql, String iReport_jasper, String[][]ve_parameters) //throws JRException, Exception
    {
        //Cuando se recibe el iReport_jasper no se tiene en cuenta el nombre completo de alumnos.jasper sino que alumnos directamente
        //zona posible de error
        Conexion c;
        c=new Conexion();
        c.estaConectado();

        ResultSet resu = (ResultSet) c.dameLista(sql);

        JRResultSetDataSource jrRS = new JRResultSetDataSource(resu);
        HashMap parameters = new HashMap();
        //Map parameters = new HashMap();
        for(int x=0;x<ve_parameters.length;x++)
        {
            //el orden para el uso correcto de estos parametros es como sigue
            //parameters.put(key_ParametroJasper, valuePersonalizado);
            parameters.put(ve_parameters[x][0], ve_parameters[x][1]);
        }

        try{
                //la urlMaestro agarra apartir de la carpeta de "src"
                URL urlMaestro = getClass().getClassLoader().getResource("reportes/"+iReport_jasper+".jasper");
                // Cargamos el reporte

                JasperReport masterReport = null;
                masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);
                JasperPrint masterPrint = null;
                masterPrint = JasperFillManager.fillReport(masterReport, parameters,jrRS);

                JasperViewer ventana = new JasperViewer(masterPrint,false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);

        }catch(JRException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ocurrio un error "+e.toString(),"ATENCION ", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void generaReportes(String sql, String iReport_jasper) //throws JRException, Exception
    {
        //Cuando se recibe el iReport_jasper no se tiene en cuenta el nombre completo de alumnos.jasper sino que alumnos directamente
        //zona posible de error
        Conexion c;
        c=new Conexion();
        c.estaConectado();

        ResultSet resu = (ResultSet) c.dameLista(sql);

        JRResultSetDataSource jrRS = new JRResultSetDataSource(resu);
        HashMap parameters = new HashMap();
        //Map parameters = new HashMap();

        try{
                //la urlMaestro agarra apartir de la carpeta de "src"
                URL urlMaestro = getClass().getClassLoader().getResource("reportes/"+iReport_jasper+".jasper");

                // Cargamos el reporte
                JasperReport masterReport = null;
                masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);
                JasperPrint masterPrint = null;
                masterPrint = JasperFillManager.fillReport(masterReport, parameters,jrRS);

                JasperViewer ventana = new JasperViewer(masterPrint,false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);

        }catch(JRException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ocurrio un error "+e.toString(),"ATENCION ", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
