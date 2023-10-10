package comunes;

import java.sql.*;

public class comReportaInca {

    private Connection conn;
    Conexion con = new Conexion();
    //private String valorpar;
    //public String data = "/home/"+System.getProperty("user.name")+"/base/data/libros.fdb";
    public String url = "jdbc:mysql://"+con.getHost()+"/"+con.getBaseActiva();
    public String user = con.getUsuario();
    public String pass = con.getContra();

    public comReportaInca() {
    
        try { 
            Class.forName("com.mysql.jdbc.Driver"); //se carga el driver
            conn = DriverManager.getConnection(url,user,pass);
        }
        catch (ClassNotFoundException | SQLException ex) {
        }   
    }
    
    public void runreporteinca(String nombre) {
     
    }


   
}
