import comunes.Conexion;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;


public class Jasper {    
    private Connection conn;
    Conexion con = new Conexion();
    //private String valorpar;
    //public String data = "/home/"+System.getProperty("user.name")+"/base/data/libros.fdb";
    public String url = "jdbc:mysql://"+con.getHost()+"/"+con.getBaseActiva();
    public String user = con.getUsuario();
    public String pass = con.getContra();
    
    

    public Jasper() {
        Jasper jasper = new Jasper();
        
        JasperReport report;
        try {
            report = JasperCompileManager.compileReport("C:\\report.jrxml");
            JasperPrint print = JasperFillManager.fillReport(report, null, conn);
            //Exporta el informe a PDF
           String destFileNamePdf="C:\\trabajo\\reporte1.pdf";
           //Creación del PDF
            JasperExportManager.exportReportToPdfFile(print, destFileNamePdf);
            /*Otras importaciones
            //Exporta el informe a HTML
             JasperExportManager.exportReportToHtmlFile(print, destFileNamePdf);
            //Exporta el informe a excel
            OutputStream ouputStream= new FileOutputStream(new File("C:/trabajo/catalog.xls"));
           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JRXlsExporter exporterXLS = new JRXlsExporter();
            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,print);
           exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);
            exporterXLS.exportReport();
            ouputStream.write(byteArrayOutputStream.toByteArray());
           ouputStream.flush();
            ouputStream.close();
          //Exporta el informe a csv
            String destFileNamePdf="C:\\trabajo\\reporte1.csv";
            JRCsvExporter exporter = new JRCsvExporter();
            File destFile = new File(destFileNamePdf);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
            destFile.toString());
            exporter.exportReport();
            //Exporta el informe a rtf
            OutputStream ouputStream= new FileOutputStream(new File("C:/trabajo/catalog.rtf"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JRRtfExporter exporter = new JRRtfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            exporter.exportReport();
            ouputStream.write(byteArrayOutputStream.toByteArray());
            ouputStream.flush();
            ouputStream.close();
            */
        } catch (Exception e) {System.out.println("Error"+e);}
    }
    }
