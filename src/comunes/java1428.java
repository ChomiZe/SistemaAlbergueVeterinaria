/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comunes;

/**
 *
 * @author joarevalos
 */
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class java1428 extends JPanel {
    Thread hilo;
    Object objeto = new Object();
    boolean pideParar = false;
    JTextField texto;
    JProgressBar barra;

    public java1428() {
        setLayout( new BorderLayout() );

        texto = new JTextField();
        add( texto,BorderLayout.NORTH );

        JPanel panelInferior = new JPanel();
        barra = new JProgressBar();
        panelInferior.setLayout( new GridLayout(0,1) );
        panelInferior.add( barra );
        panelInferior.add( new JLabel( "Cargando..." ) );

        JPanel panelBotones = new JPanel();
        JButton botonArranque = new JButton( "Arrancar" );
        botonArranque.setBackground( SystemColor.control );
        panelBotones.add( botonArranque );
        botonArranque.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent evt ) {
                iniciaCuenta();
            }
        } );

        JButton botonParar = new JButton( "Parar" );
        botonParar.setBackground( SystemColor.control );
        panelBotones.add( botonParar );
        botonParar.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent evt ) {
                detieneCuenta();
            }
        } );

        panelInferior.add( panelBotones );
        add( panelInferior,BorderLayout.SOUTH );
    }


    public void iniciaCuenta() {
        if( hilo == null ) {
            hilo = new ThreadCarga();
            pideParar = false;
            hilo.start();
        }
    }


    public void detieneCuenta() {
        synchronized( objeto ) {
            pideParar = true;
            objeto.notify();
        }
    }


    class ThreadCarga extends Thread {
        public void run() {
            int min = 0;
            int max = 100;

            barra.setValue( min );
            barra.setMinimum( min );
            barra.setMaximum( max );

            for( int i=min; i <= max; i++ ) {
                barra.setValue( i );
                texto.setText( ""+i );
                synchronized( objeto ) {
                    if( pideParar )
                        break;
                    try {
                        objeto.wait( 100 );
                } catch( InterruptedException e ) {
                        // Se ignoran las excepciones
                    }
                }
            }
            hilo = null;
        }
    }


    public static void main( String args[] ) {
        JFrame frame = new JFrame( "Tutorial de Java, Swing" );
        frame.setDefaultCloseOperation( frame.EXIT_ON_CLOSE );
        frame.getContentPane().add( new java1428(),BorderLayout.CENTER );
        frame.setSize( 400,150 );
        frame.setVisible( true );
    }
}
