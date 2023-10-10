 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package garra;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


/**
 *
 * @author Usuario
 */
public class Garra {
 private static Connection conexion;

    public static Connection getConexion() {
        try {
             Class.forName("com.mysql.jdbc.Driver").newInstance();
             conexion = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost/garra",
                    "root", "123456");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse", "Conexion",
                    JOptionPane.ERROR_MESSAGE);
        }
        return conexion;
        }
}
