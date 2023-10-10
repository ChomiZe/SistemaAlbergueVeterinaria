/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package comunes;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class lx300 {
private FileWriter fw;
private BufferedWriter bw;
private PrintWriter pw;
 private static final float CM_PER_INCH = 2.54f;


/** Esta funcion inicia el  dispositivo donde se va a imprimir */
public  void setDispositivo() {

 try {
            //Esto si  es linux
            fw = new FileWriter("/dev/bus/usb/003/002");
            bw = new BufferedWriter (fw);
            pw = new PrintWriter (bw);
            pw.write(27);
            pw.write(64); // inicializa la impresora

            pw.write(27);
            pw.write(18); // letra normal

              //select 10-cpi character pitch
            select10CPI();

            //select draft quality printing
            selectDraftPrinting();

            //set character set
            setCharacterSet("25");


           
        } catch (IOException ex) {
            Logger.getLogger(lx300.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "NO SE PUEDE ESTABLECER CONEXION CON LA IMPRESORA","ERROR",JOptionPane.ERROR_MESSAGE);
        }

}

  public void advanceHorizontal(float centimeters) {
        //pre: centimeters >= 0
        //post: advances horizontal print position approx. centimeters
        float inches = centimeters / CM_PER_INCH;
        int units_low = (int) (inches * 120) % 256;
        int units_high = (int) (inches * 120) / 256;

         pw.write(27);
         pw.write(92);
         pw.write((char) units_low);
         pw.write((char) units_high);
    }

   public void setAbsoluteHorizontalPosition(float centimeters) {
        //pre: centimenters >= 0 (cm)
        //post: sets absolute horizontal print position to x centimeters from left margin
        float inches = centimeters / CM_PER_INCH;
        int units_low = (int) (inches * 60) % 256;
        int units_high = (int) (inches * 60) / 256;

        pw.write(27);
        pw.write(36);
        pw.write((char) units_low);
        pw.write((char) units_high);
    }

public void select10CPI() { //10 characters per inch (condensed available)
      pw.write(27);
      pw.write(80);
    }

    public void select15CPI() { //15 characters per inch (condensend not available)
        pw.write(27);
        pw.write(103);
    }

    public void selectDraftPrinting() { //set draft quality printing
        pw.write(27);
        pw.write(120);
        pw.write(48);

    }

    public void selectLQPrinting() { //set letter quality printing
        pw.write(27);
        pw.write(120);
        pw.write(49);
    }

    public void setCharacterSet(String charset) {
        //assign character table
        pw.write(27);
        pw.write(40);
        pw.write(116);
        pw.write(3);
        pw.write(0);
        pw.write(1);
        pw.write(charset);
        pw.write(0);


        //select character table
             pw.write(27);
             pw.write(116);
             pw.write(1); //selectable character table 1

             pw.write(27);
             pw.write(82);
             pw.write(12);
    }




public void escribir( String texto ) {
try{
pw.println(texto);
}catch(Exception e){
System.out.print(e);
}
}

public  void cerrarDispositivo(  ){
   try{
 
bw.write(27);
bw.write(02);
pw.close();

}catch(Exception e){
System.out.print(e);
}
}


public  void avanzar(int fin){
try{
int i=0;
for(i=1;i<=fin;i++){
this.salto();
}
}catch(Exception e){
System.out.print(e);
}
}

public  void salto() {
try{
pw.println("");
}catch(Exception e){
System.out.print(e);
}
}

public void negrita(){
pw.write(27);
pw.write(69);
}

public void anulanegrita(){
pw.write(27);
pw.write(70);
}



public void textoexpandido(){
pw.write(27);
pw.write(14);
}
public void cancelatextoexpandido(){
pw.write(27);
pw.write(20);
}
public void reiniciarimpresora(){

pw.write(27);
pw.write(64);

}

public void divisorlinea(){

pw.write("__________________________________________________");
//pw.write("--------------------------------------------------");
}
public void divisordoble(){

pw.write("==================================================");
//pw.write("--------------------------------------------------");
}
public void centrarTexto(){

pw.write(27);
pw.write(97);
//pw.write(1);
pw.write(49);

}
public void JustificarTexto(){

pw.write(27);
pw.write(97);
//pw.write(1);
pw.write(48);
}



}
