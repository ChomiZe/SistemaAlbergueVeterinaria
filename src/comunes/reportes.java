package comunes;


import garra.Principal;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.*;
import org.jfree.ui.ExtensionFileFilter;

public class reportes {

    private Connection conn;
    Conexion con = new Conexion();
    //private String valorpar;
    //public String data = "/home/"+System.getProperty("user.name")+"/base/data/libros.fdb";
    public String url = "jdbc:mysql://"+con.getHost()+"/"+con.getBaseActiva();
    public String user = con.getUsuario();
    public String pass = con.getContra();
    private JasperReport report;

    public reportes() {
    
        try { 
            Class.forName("com.mysql.jdbc.Driver"); //se carga el driver
            conn = DriverManager.getConnection(url,user,pass);
        }
        catch (ClassNotFoundException | SQLException ex) {
        }   
    }
    
    public void runreporte(String nombre) {
        try {
            String master = System.getProperty("user.dir")+"/src/informes/"+nombre+".jasper";

            // String master = "/media/miparticion/UTICTERCERO/TALLERTERCERO/Transportadora/src/reportes/"+nombre+".jasper";
            System.out.println("master" + master);

            if (master == null) {
                System.out.println("No se encontró el reporte...");
                return;
            }
            JasperReport masterReport;
            java.net.URL myurl;
            myurl = Principal.class.getResource("/informes/"+nombre+".jasper");
            try  {
                masterReport = (JasperReport) JRLoader.loadObject(myurl);
            }
            catch (JRException e) {
                System.out.println("Error cargando el reporte: " + e.getMessage());
                return;
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport,null, conn);

            JDialog viewer = new JDialog(new JFrame(),"Vista previa del reporte", true);
            viewer.setSize(800,600);
            viewer.setLocationRelativeTo(null);

            // Mostrar el reporte
            JRViewer jrv = new JRViewer(jasperPrint);
        
            viewer.getContentPane().add(jrv);
            //SI EL DOCUMENTO NO TIENE PÁGINAS
            if (!jasperPrint.getPages().isEmpty()) {
                removeBlankPage(jasperPrint.getPages());
                viewer.show();
            }
        }
        catch (JRException | HeadlessException j) {
        System.out.println("Error..." + j.getMessage()); }
    }
    
    private void removeBlankPage(List<JRPrintPage> pages) {
        for (Iterator<JRPrintPage> i=pages.iterator(); i.hasNext();) {
            JRPrintPage page = i.next();
            if (page.getElements().isEmpty())
                i.remove();
        }
    }

    public void runreporte(String[] nombrepar, Object[] variables, String reporte, boolean mostrar) {
        try {
//            String master;
//            master = System.getProperty("user.dir")+System.getProperty("file.separator")+"src"
//                    +System.getProperty("file.separator")+"reportes"
//                    + System.getProperty("file.separator")+reporte+".jasper";
            /*master = System.getProperty("user.dir")+System.getProperty("file.separator")+"src"
                    +System.getProperty("file.separator")+"informes"
                    + System.getProperty("file.separator");*/
            /*ClassLoader classLoader = formPadre.class.getClassLoader();
            URL url = classLoader.getResource ("informes/"+reporte+".jasper");
            master = url.getPath();*/
            java.net.URL master;
            master = Principal.class.getResource("/informes/"+reporte+".jasper");
            System.out.println("master" + master);

            if (master == null) {
                System.out.println("No se encontró el reporte...");
                return;
            }
            
            JasperReport masterReport;
            
            try {
                masterReport = (JasperReport) JRLoader.loadObject(master);
            }
            catch (JRException e) {
                System.out.println("Error cargando el reporte: " + e.getMessage());
                return;
            }
            Map parametro = new HashMap();
            
            for (int i=0;i<=variables.length-1;i++) {
                parametro.put(nombrepar[i], variables[i]);
            }
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parametro, conn);
            
            if (mostrar) {
                JDialog viewer = new JDialog(new JFrame(),"Vista previa del reporte", true);
                viewer.setSize(900,600);
                viewer.setLocationRelativeTo(null);

                // Mostrar el reporte
                JRViewer jrv = new JRViewer(jasperPrint);

                viewer.getContentPane().add(jrv);
                
                //SI EL DOCUMENTO NO TIENE PÁGINAS
                if (!jasperPrint.getPages().isEmpty()) {                    
                    removeBlankPage(jasperPrint.getPages());
                    viewer.show();
                }
            }
            else {
                removeBlankPage(jasperPrint.getPages());
                JasperPrintManager.printReport(jasperPrint, false);
            }
        }
        catch (JRException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error en el reporte\n"+e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void runreporteEnPanel(String[] nombrepar, String[] variables, String reporte, boolean mostrar, JPanel panel) {
        try {
            //String master;
            java.net.URL master;
            master = Principal.class.getResource("/reportes/"+reporte+".jasper");
//            master = System.getProperty("user.dir")+System.getProperty("file.separator")+"src"
//                    +System.getProperty("file.separator")+"reportes"
//                    + System.getProperty("file.separator")+reporte+".jasper";
            /*master = System.getProperty("user.dir")+System.getProperty("file.separator")+"src"
                    +System.getProperty("file.separator")+"informes"
                    + System.getProperty("file.separator");*/
            /*ClassLoader classLoader = formPadre.class.getClassLoader();
            URL url = classLoader.getResource ("informes/"+reporte+".jasper");
            master = url.getPath();*/
            System.out.println("master" + master);

            if (master == null) {
                System.out.println("No se encontró el reporte...");
                return;
            }
            JasperReport masterReport;
            
            try {
                masterReport = (JasperReport) JRLoader.loadObject(master);
            }
            catch (JRException e) {
                System.out.println("Error cargando el reporte: " + e.getMessage());
                return;
            }
            Map parametro = new HashMap();
            
            for (int i=0;i<=variables.length-1;i++) {
                //parametro.put(nompar1, x1);
                parametro.put(nombrepar[i], variables[i]);
            }
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parametro, conn);
            
            if (mostrar) {
                //SI EL DOCUMENTO NO TIENE PÁGINAS
                if (!jasperPrint.getPages().isEmpty()) {
                    // Mostrar el reporte
                    panel.setLayout(new BorderLayout());  
                    panel.add(new JRViewer(jasperPrint));
                    panel.repaint();
                    panel.revalidate();
                }
            }
            else {
                JasperPrintManager.printReport(jasperPrint, false);
            }
        }
        catch (JRException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error en el reporte\n"+e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
 
    
    public boolean runReporteExcel(String[] nombrepar, Object[] variables, String reporte) {
        try {
//            report = JasperCompileManager.compileReport("/reportes/"+reporte+".jrxml");
            report = (JasperReport) JRLoader.loadObject(reportes.class.getResource("/reportes/"+reporte+".jasper"));
            
            Map parametro = new HashMap();
            for (int i=0;i<=variables.length-1;i++) {
                parametro.put(nombrepar[i], variables[i]);
            }
            
            JasperPrint print = JasperFillManager.fillReport(report, parametro, conn);
            
            //Exporta el informe a excel
//            OutputStream ouputStream = null;
//            try {
//                ouputStream = new FileOutputStream(new File("/home/jorge/productos.xls"));
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            JRXlsExporter exporterXLS = new JRXlsExporter();
//            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,print);
//            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);
//            exporterXLS.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING,"UTF-8");
//            exporterXLS.exportReport();
//            try {
//                ouputStream.write(byteArrayOutputStream.toByteArray());
//            } catch (IOException ex) {
//                Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                ouputStream.flush();
//            } catch (IOException ex) {
//                Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                ouputStream.close();
//            } catch (IOException ex) {
//                Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            JFileChooser f = new JFileChooser();
            //f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); 
            f.setFileFilter(new ExtensionFileFilter("Archivo Excel (*.xls)", "xls"));
            //f.setCurrentDirectory(new File(System.getProperty("user.home")+System.getProperty("file.separator")+"Desktop"));
            f.setDialogType(JFileChooser.SAVE_DIALOG);
            //f.setSelectedFile(new File(f.getCurrentDirectory()+System.getProperty("file.separator")+reporte+".xls"));
            f.showSaveDialog(null);
            
            /*****DON'T TOUCH THIS CHUNK*****/
            System.err.println(f.getSelectedFile());
            String directorio = null;
            if (!f.getSelectedFile().getName().endsWith(".xls")) {
                directorio = f.getSelectedFile()+".xls";
            }
            else
                directorio = f.getSelectedFile().getPath();
            
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,"UTF-8");
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, false);
            exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, directorio);
            
            //DON'T TOUCH THIS CHUNK EITHER -JUST IN CASE-
//            JRXlsExporter exporter = new JRXlsExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
//            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "D://"+reporte+".xls" );
            
            if (f.getSelectedFile() != null) {
                exporter.exportReport();
                if (JOptionPane.showConfirmDialog(null, "Archivo guardado. ¿Desea abrirlo ahora?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    try {
                        Process p = Runtime.getRuntime().exec ("rundll32 SHELL32.DLL,ShellExec_RunDLL "+directorio);
                    } catch (IOException ex) {
                        Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
//                JOptionPane.showMessageDialog(null, "Archivo guardado");
                return true;
            }
            else
                return false;
        } catch (JRException ex) {
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo");
            return false;
        }
    }
}
