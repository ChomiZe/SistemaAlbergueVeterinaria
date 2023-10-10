/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comunes;

/**
 *
 * @author arepen
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

public class Ticket {

    static ArrayList<String> CabezaLineas = new ArrayList<String>();
    static ArrayList<String> subCabezaLineas = new ArrayList<String>();
    static ArrayList<String> items = new ArrayList<String>();
    static ArrayList<String> totales = new ArrayList<String>();
    static ArrayList<String> LineasPie = new ArrayList<String>();

    public static void AddCabecera(String line) {
        CabezaLineas.add(line);
    }

    public static void AddSubCabecera(String line) {
        subCabezaLineas.add(line);
    }

    public static void AddItem(String cantidad, String item, String price) {
        OrderItem newItem = new OrderItem(' ');
        items.add(newItem.GeneraItem(cantidad, item, price));
    }

    public static void AddTotal(String name, String price) {
        OrderTotal newTotal = new OrderTotal(' ');
        totales.add(newTotal.GeneraTotal(name, price));
    }

    public static void AddPieLinea(String line) {
        LineasPie.add(line);
    }

    public static String DibujarLinea(int valor) {
        String raya = "";
        for (int x = 0; x < valor; x++) {
            raya += "-";
        }
        return raya;
    }
    public static String AgregaEspacio(int cantidad, int valor) {
        String espacio = "";
        for (int x = 0; x < cantidad-valor; x++) {
            espacio += " ";
        }
        return espacio;
    }
    
    public static String AgregaLinea(int valor) {
        String linea = "";
        for (int x = 0; x < 11-valor; x++) {
            linea += "\n ";
        }
        return linea;
    }
    
    public static String AgregaLineaFactura(int valor) {
        String linea = "";
        for (int x = 0; x < 12-valor; x++) {
            linea += "\n ";
        }
        return linea;
    }

    public static String NuevaLinea() {
        return "\n";
    }
    
    public static String DarTab() {
        return "\t";
    }

    public static void ImprimirDocumento() {
        String cadena = "";
        
        for (int cabecera = 0; cabecera < CabezaLineas.size(); cabecera++) {
            cadena += CabezaLineas.get(cabecera);
        }
//        for (int subcabecera = 0; subcabecera < subCabezaLineas.size(); subcabecera++) {
//            cadena += subCabezaLineas.get(subcabecera);
//        }
//        for (int ITEM = 0; ITEM < items.size(); ITEM++) {
//            cadena += items.get(ITEM);
//        }
//        for (int total = 0; total < totales.size(); total++) {
//            cadena += totales.get(total);
//        }
//        for (int pie = 0; pie < LineasPie.size(); pie++) 
//            cadena += LineasPie.get(pie);
//        }
        System.err.println(cadena);
//        DocFlavor flavor = DocFlavor.BYTE_ARRAY .AUTOSENSE;
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
//        DocFlavor flavor = new DocFlavor("text/plain; charset=utf-8", "java.io.InputStream");
        
        //Aqui selecciona tu impresora, el ejemplo tomará la impresora predeterminada.
        PrintService  service = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob  pj = service.createPrintJob();
        byte[] bytes = cadena.getBytes();
        Doc  doc = new SimpleDoc(bytes, flavor, null);
        try {
            pj.print(doc, null);
            cadena = "";
            bytes = null;
            CabezaLineas.clear();
        } catch (PrintException  e) {
            System.err.print(e);
        }
    }
}
