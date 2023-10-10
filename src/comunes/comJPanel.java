/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comunes;

import com.toedter.calendar.JDateChooser;
import comunes.Conexion;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author pcsproject
 */
public class comJPanel
{
    /**
     *  Verifica si cuadro de texto estan vacios
     * @param jpanel
     */
    public static boolean setValidate(JPanel jpanel)
    {
        JTextField jTexto;
        boolean retorna=false;
        for (int i = 0; i < jpanel.getComponentCount(); i++)
        {
             if (jpanel.getComponent(i).getClass().equals(JTextField.class))
                {
                    jTexto=(JTextField)jpanel.getComponent(i);
                    if (jTexto.getText().isEmpty()) {                        
                        retorna=true;
                    } else {
                        retorna=false;
                    }
                }
        }
       return retorna;

    }
    /**
     * Habilita o deshabilita en grupo lo que contiene un jPanel, ya sea 
     * @param jpanel
     * @param boo 
     */
    public static void setEnabled(JPanel jpanel , boolean boo)
    {
        for (int i = 0; i < jpanel.getComponentCount(); i++) 
        {
            jpanel.getComponent(i).setEnabled(boo);
        }
    }
    /**
     * Coloca visible o invisible lo que contiene un jPanel
     * @param jpanel
     * @param boo 
     */
    public static void setVisible(JPanel jpanel , boolean boo)
    {
        for (int i = 0; i < jpanel.getComponentCount(); i++) 
        {
            jpanel.getComponent(i).setVisible(boo);
        }
    }
    /**
     * Todos los jTextField contenidas en un jPanel coloca en verdadero o falso
     * @param jpanel
     * @param boo 
     */
    public static void setjTextfieldEnabled(JPanel jpanel, boolean boo)
    {
        for (int i = 0; i < jpanel.getComponentCount(); i++) 
        {
            if (jpanel.getComponent(i).getClass().equals(JTextField.class)) 
            {
                jpanel.getComponent(i).setEnabled(boo);
            }
        }
    }
    /**
     * Coloca a todo lo que se encuentre dentro de un jPanel en el formato null. Los formatos soportados por este metedo son: jTextfiedl, jCombox, jDateChooser, jFormatterTextfield.
     * @param jpanel 
     */
    public static void setAllNullComponents(JPanel jpanel) {
        //combox, textfield, jFormatt
        JTextField jText;
        JDateChooser jDC;
        JComboBox jCom;
        JFormattedTextField jFT;
        JPasswordField jPF;
        JSpinner jSP;
        Conexion c = new Conexion();
        c.estaConectado();
            for (int i = 0; i < jpanel.getComponentCount(); i++) {
                if (jpanel.getComponent(i).getClass().equals(JTextField.class)) {
                    jText=(JTextField)jpanel.getComponent(i);
                    jText.setText(null);
                }
                else
                    if (jpanel.getComponent(i).getClass().equals(JComboBox.class)) {
                        jCom=(JComboBox)jpanel.getComponent(i);
                        //c.cargarCombo("select '', ''", jCom);
                        jCom.removeAllItems();
                    }
                    else
                        if (jpanel.getComponent(i).getClass().equals(JFormattedTextField.class)) {
                            try {
                                jFT=(JFormattedTextField)jpanel.getComponent(i);
                                jFT.setValue(null);
                            }
                            catch (Exception e) {
                                jFT=(JFormattedTextField)jpanel.getComponent(i);
                                jFT.setText(null);
                            }
                        }else{
                            if (jpanel.getComponent(i).getClass().equals(JDateChooser.class)) {
                                jDC=(JDateChooser)jpanel.getComponent(i);
                                jDC.setDate(null);
                            }
                            else {
                                if (jpanel.getComponent(i).getClass().equals(JPasswordField.class)) {
                                    jPF=(JPasswordField)jpanel.getComponent(i);
                                    jPF.setText(null);
                                }
                                else {
                                    if (jpanel.getComponent(i).getClass().equals(JSpinner.class)) {
                                        jSP=(JSpinner)jpanel.getComponent(i);
                                        jSP.setValue(0);
                                    }
                                }
                            }
                        }
            }
        c.cierraConexion();
    }
    /**
     * Recibe un grupo de panels para colocarles nulo siempre y cuando se encuentre dentro de los rangos validos entre: jtextfield, jforamtted, jdatechooser, jcombox
     * @param jpanel 
     */
    public static void setAllNullComponents(JPanel[]jpanel)
    {
        for (int i = 0; i < jpanel.length; i++) 
            setAllNullComponents(jpanel[i]);
    }
    
    /**
     * Todos los jTextField contenidas en un jPanel coloca en verdadero o falso
     * @param jpanel
     * @param boo 
     */
    public static void setjTextfieldSetTextNull(JPanel jpanel)
    {
        JTextField jjj = new JTextField();
        for (int i = 0; i < jpanel.getComponentCount(); i++) 
        {
            if (jpanel.getComponent(i).getClass().equals(JTextField.class)) 
            {
                jjj =(JTextField) jpanel.getComponent(i);
                jjj.setText(null);
            }
        }
    }
    /**
     * Coloca a todos los jTextfield dentro de un jPanel con valor nulo y excluye el jtf que se le pasa
     */
    public static void setjTextfieldSetTextNullException(JPanel jpanel, JTextField jtf)
    {
        JTextField jjj = new JTextField();
        for (int i = 0; i < jpanel.getComponentCount(); i++) 
        {
            if (jpanel.getComponent(i).getClass().equals(JTextField.class)) 
            {
                jjj =(JTextField) jpanel.getComponent(i);
                if (jjj.equals(jtf)) 
                   continue;
                jjj.setText(null);
            }
        }
    }
    /**
     * Metodo que permite recibir un contenedor y colocarle segun el booleano exceptuando los objetos que recibe para excluirlos.
     * @param jpanel
     * @param boo
     * @param jtf 
     */
    public static void setEnabledException(JPanel jpanel, Boolean boo, Object[]ObjectException)
    {
        Boolean booLocal=false;
        for (int i = 0; i < jpanel.getComponentCount(); i++) 
        {
            booLocal=false;
            for (int j = 0; j < ObjectException.length; j++) 
                if (jpanel.getComponent(i).equals(ObjectException[j])) 
                {
                    jpanel.getComponent(i).setEnabled(!boo);
                    booLocal=true;
                    break;
                }
            if(!booLocal)
                jpanel.getComponent(i).setEnabled(boo);
        }
    }
    /**
     * Coloca a todos los jTextfield dentro de un jPanel con valor nulo y excluye todos los que se encuentren dentro del vector
     * @param jpanel
     * @param jtf 
     */
    public static void setjTextfieldSetTextNullException(JPanel jpanel, JTextField[]jtf)
    {
        JTextField jjj = new JTextField();
        Boolean pass=false;
        for (int i = 0; i < jpanel.getComponentCount(); i++) 
        {
            if (jpanel.getComponent(i).getClass().equals(JTextField.class)) 
            {
                jjj =(JTextField) jpanel.getComponent(i);
                for (int j = 0; j < jtf.length; j++) 
                    if (jjj.equals(jtf[j]))
                        pass=true;
                if (!pass) 
                {
                    jjj.setText(null);
                    pass=false;
                }
            }
        }
    }
    /**
     * Todos los jCombox contenidas en un jPanel coloca en verdadero o falso
     * @param jpanel
     * @param boo 
     */
    public static void setjComboxEnabled(JPanel jpanel, boolean boo)
    {
        for (int i = 0; i < jpanel.getComponentCount(); i++) 
        {
            if (jpanel.getComponent(i).getClass().equals(JComboBox.class)) 
            {
                jpanel.getComponent(i).setEnabled(boo);
            }
        }
    }
    /**
     * Metodo que permite recibir todos los combox que se encuentren en un jPanel para 
     * colocar los valores vacios.
     * @param jpanel 
     */
    public static void setjComboxSetNull(JPanel jpanel)
    {
        JComboBox bbb = new JComboBox();
        Conexion c = new Conexion();
        c.estaConectado();
        for (int i = 0; i < jpanel.getComponentCount(); i++) 
        {
            if (jpanel.getComponent(i).getClass().equals(JComboBox.class)) 
            {
                bbb = (JComboBox) jpanel.getComponent(i);
                bbb.removeAllItems();
                //c.cargarCombo("select '',''", bbb);
            }
        }
        c.cierraConexion();
    }
    public static void setjComboxSetNullWithException(JPanel jpanel, JComboBox[]jComboBoxs)
    {
        JComboBox bbb = new JComboBox();
        Conexion c = new Conexion();
        Boolean pass=false;
        c.estaConectado();
        for (int i = 0; i < jpanel.getComponentCount(); i++)
        {
            pass=false;
            if (jpanel.getComponent(i).getClass().equals(JComboBox.class))
            {
                bbb = (JComboBox) jpanel.getComponent(i);
                for (int j = 0; j < jComboBoxs.length; j++)
                    if(bbb.equals(jComboBoxs[j]))
                    {
                        pass=true;
                    }
                if (!pass)
                {
                    bbb.removeAllItems();
                    c.cargarCombo("select '',''", bbb);
                }
            }
        }
        c.cierraConexion();
    }
    /**
     * Todos los jButton contenidas en un jPanel coloca en verdadero o falso
     * @param jpanel
     * @param boo 
     */
    public static void setjButtonEnabled(JPanel jpanel, boolean boo)
    {
        for (int i = 0; i < jpanel.getComponentCount(); i++) 
        {
            if (jpanel.getComponent(i).getClass().equals(JButton.class)) 
            {
                jpanel.getComponent(i).setEnabled(boo);
            }
        }
    }

    /**
     * Dentro de un jPanel agrupa todos los objetos que sean jButton y retorna un conjunto de vectores solamente con jButton
     * @param jPanel
     * @param obj
     * @return 
     */
    public static JButton[]groupCategoryJButton(JPanel jPanel)
    {
        int c=0;
        JButton[]veJB=new JButton[jPanel.getComponentCount()];
        for (int i = 0; i < jPanel.getComponentCount(); i++) 
        {
            if(jPanel.getComponent(i).getClass().equals(JButton.class))
            {
                veJB[c]=(JButton)jPanel.getComponent(i);
                c+=1;
            }
        }
        JButton[]veAux=new JButton[c];
        for (int i = 0; i < c; i++) 
            veAux[i]=veJB[i];
        return veAux;
    }
    /**
     * Agrupa dentro de un panel el conjunto de jCheckBox que encuentre y lo retorna como vector
     * @param jPanel
     * @return
     */
    public static JCheckBox[]groupCategoryJCheckBox(JPanel jPanel)
    {
        int c=0;
        JCheckBox[]veJB=new JCheckBox[jPanel.getComponentCount()];
        for (int i = 0; i < jPanel.getComponentCount(); i++)
        {
            if(jPanel.getComponent(i).getClass().equals(JCheckBox.class))
            {
                veJB[c]=(JCheckBox)jPanel.getComponent(i);
                c+=1;
            }
        }
        JCheckBox[]veAux=new JCheckBox[c];
        for (int i = 0; i < c; i++)
            veAux[i]=veJB[i];
        return veAux;
    }
    /**
     * Dentro de un jPanel agrupa todos los objetos que sean jTextfield y lo retorna como un vector donde se encuentra todos los jTextfield que encontro
     * @param jPanel
     * @param obj
     * @return 
     */
    public static JTextField[]groupCategoryJTextField(JPanel jPanel)
    {
        int c=0;
        JTextField[]veJB=new JTextField[jPanel.getComponentCount()];
        for (int i = 0; i < jPanel.getComponentCount(); i++) 
        {
            if(jPanel.getComponent(i).getClass().equals(JTextField.class))
            {
                veJB[c]=(JTextField)jPanel.getComponent(i);
                c+=1;
            }
        }
        JTextField[]veAux=new JTextField[c];
        for (int i = 0; i < c; i++) 
            veAux[i]=veJB[i];
        return veAux;
    }
    /**
     * Dentro de un jPanel agrupa todos los objetos que sean JComboBox y lo retorna como un vector donde se encuentra todos los JComboBox que encontro
     * @param jPanel
     * @param obj
     * @return 
     */
    public static JComboBox[]groupCategoryJCombox(JPanel jPanel)
    {
        int c=0;
        JComboBox[]veJB=new JComboBox[jPanel.getComponentCount()];
        for (int i = 0; i < jPanel.getComponentCount(); i++) 
        {
            if(jPanel.getComponent(i).getClass().equals(JComboBox.class))
            {
                veJB[c]=(JComboBox)jPanel.getComponent(i);
                c+=1;
            }
        }
        JComboBox[]veAux=new JComboBox[c];
        for (int i = 0; i < c; i++) 
            veAux[i]=veJB[i];
        return veAux;
    }
    /**
     * Dentro de un jPanel agrupa todos los objetos que sean JLabel y lo retorna como un vector donde se encuentra todos los JLabel que encontro
     * @param jPanel
     * @param obj
     * @return 
     */
    public static JLabel[]groupCategoryJLabel(JPanel jPanel)
    {
        int c=0;
        JLabel[]veJB=new JLabel[jPanel.getComponentCount()];
        for (int i = 0; i < jPanel.getComponentCount(); i++) 
        {
            if(jPanel.getComponent(i).getClass().equals(JLabel.class))
            {
                veJB[c]=(JLabel)jPanel.getComponent(i);
                c+=1;
            }
        }
        JLabel[]veAux=new JLabel[c];
        for (int i = 0; i < c; i++) 
            veAux[i]=veJB[i];
        return veAux;
    }
   
   /**
    * Sirve para deshabilitar o habilitar en grupo un conjunto de componentes dentro de un panel
    * @param jPanels
    * @param boo 
    */
   public static void setEnabled(JPanel[]jPanels, Boolean boo)
   {
       for (int i = 0; i < jPanels.length; i++) 
           comJPanel.setEnabled(jPanels[i], boo);
   }
   /**
    * De un grupo de componentes los coloca a todos segun el booleano y de paso null segun el parametro de rangos.
    * @param jPanels
    * @param boo 
    */
   public static void setEnabledAndNull(JPanel[]jPanels, Boolean boo)
   {
       for (int i = 0; i < jPanels.length; i++)
       {
           comJPanel.setEnabled(jPanels[i], boo);
           comJPanel.setAllNullComponents(jPanels[i]);
       }
   }
   /**
    * Habilita y deshabilita en grupo un conjunto de componentes que se encuentro dentro de un panel con las opciones de la excepcion.
    * @param jpanel
    * @param boo
    * @param ObjectException 
    */
   public static void setEnabledException(JPanel[]jpanel, Boolean boo, Object[]ObjectException)
   {
       for (int i = 0; i < jpanel.length; i++) 
           setEnabledException(jpanel[i], boo, ObjectException);
   }
  
   /**
    * Agrupa un conjunto de textField y se evalua si por lo menos un dato se encuentra vacio para retornar true de otro modo retorna falso
    * @param jPanel_Textfield
    * @return 
    */
   public static Boolean isEmptyGroupJTextfield(JPanel jPanel_Textfield)
   {
       JTextField[]veJTx=comJPanel.groupCategoryJTextField(jPanel_Textfield);
       for (int i = 0; i < veJTx.length; i++)
            if (veJTx[i].getText().isEmpty()) 
                return true;
       return false;
   }
}
