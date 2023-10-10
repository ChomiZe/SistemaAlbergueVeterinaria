/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comunes;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author arepen
 */
public class ProPrinter {
      
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("hh:mm:ss aa");
        Ticket ticket = new Ticket();
        ticket.AddCabecera("RESTAURANTE XXXX");
        ticket.AddCabecera(ticket.DarTab());
        ticket.AddCabecera("EXPEDIDO EN: ----------");
        ticket.AddCabecera(ticket.DarTab());
        ticket.AddCabecera("AV. NANAWA NO. 5 LOC. 101");
        ticket.AddCabecera(ticket.DarTab());
        ticket.AddCabecera(ticket.DibujarLinea(29));
        ticket.AddCabecera(ticket.DarTab());
        ticket.AddCabecera("CAAGUAZU, XXXXXXXXXXXX");
        ticket.AddCabecera(ticket.DarTab());
        ticket.AddCabecera("RUC: 80004566");
        ticket.AddCabecera(ticket.DarTab());
        ticket.AddSubCabecera(ticket.DarTab());
        ticket.AddSubCabecera("Caja # 1 - Ticket # 1");
        ticket.AddSubCabecera(ticket.DarTab());
        ticket.AddSubCabecera("LE ATENDIO: JORGE");
        ticket.AddSubCabecera(ticket.DarTab());
        ticket.AddSubCabecera("" + fecha.format(date) + " " + hora.format(date));
        ticket.AddSubCabecera(ticket.DarTab());
        ticket.AddSubCabecera(ticket.DibujarLinea(29));
        ticket.AddSubCabecera(ticket.DarTab());
        ticket.AddItem("1", "Articulo Prueba", "15.00");
        ticket.AddItem("", "", ticket.DarTab());
        ticket.AddItem("2", "Articulo Prueba", "25.00");
        ticket.AddItem("", "", ticket.DarTab());
        ticket.AddTotal("", ticket.DibujarLinea(29));
        ticket.AddTotal("", ticket.DarTab());
        ticket.AddTotal("SUBTOTAL", "29.75");
        ticket.AddTotal("", ticket.DarTab());
        ticket.AddTotal("IVA", "5.25");
        ticket.AddTotal("", ticket.DarTab());
        ticket.AddTotal("TOTAL", "35.00");
        ticket.AddTotal("", ticket.DarTab());
        ticket.AddTotal("", ticket.DarTab());
        ticket.AddTotal("RECIBIDO", "50.00");
        ticket.AddTotal("", ticket.DarTab());
        ticket.AddTotal("CAMBIO", "15.00");
        ticket.AddTotal("", ticket.DarTab());
        ticket.AddTotal("", ticket.DarTab());
        ticket.AddPieLinea(ticket.DibujarLinea(29));
        ticket.AddPieLinea(ticket.DarTab());
        ticket.AddPieLinea("EL xxx ES NUESTRA PASION...");
        ticket.AddPieLinea(ticket.DarTab());
        ticket.AddPieLinea("VIVE LA EXPERIENCIA EN xxx");
        ticket.AddPieLinea(ticket.DarTab());
        ticket.AddPieLinea("Gracias por su visita");
        ticket.AddPieLinea(ticket.DarTab());
        ticket.ImprimirDocumento();
    }
}
