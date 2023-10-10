package Frames;

import comunes.Conexion;
import comunes.comJTable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;


public class buscadorComun2 extends javax.swing.JDialog {
    private String tabla="",consultasql="", campos = "";
    private String[][] veCamposID;
    private String[]veTitle;
    private int[]ColumnSelect,columnHide, columnSizes;
    private Class[]veClass;
    static private String[]veDato;
    private int[]colEdit={0};
    Integer cantAtrib;
    //String[][] equiv = new String[cantAtrib][2];
    String[][] equiv;
    String[] campoBusqueda;
    String condicion;
                
    /** Creates new form buscadorSimple2 */
    public buscadorComun2(java.awt.Dialog parent, boolean modal) {
        //super(parent, modal);
        initComponents();
        escape();
    }
    
    public void escape() {
        KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed (ActionEvent e) {
                dispose();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
    }

    public static String[] getVeDato() {
        return veDato;
    }
    protected static void setVeDatoEmpty() {
        veDato = new String[]{};
    }

    private void traspasarDatos() {
        String idsel;
        ResultSet rs;
        
        Conexion c = new Conexion();
        c.estaConectado();
        String w="", t;
        for (int i=0;i<veCamposID.length;i++) {
            if (i != veCamposID.length-1){
                t = " and ";
            } else { t = ""; }
            idsel=jTable1.getValueAt(jTable1.getSelectedRow(), Integer.parseInt(veCamposID[i][1]))+"";
            //w += veCamposID[i][0]+" = "+veCamposID[i][Integer.parseInt(idsel)] + t + " "; 
            w += veCamposID[i][0]+" = '"+(idsel)+"'"+ t + " "; 
        }
        consultasql = "select * from "+tabla+" where "+w;
        
        try {
            int numcol = c.countColumn(consultasql);
            veDato = new String[numcol];
            rs = c.dameLista(consultasql);
            while(rs.next()) {
                for (int i = 0; i < numcol; i++)
                    veDato[i] = rs.getString(i+1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Hubo errores al buscar", "Errores", 0);
        }                        
        c.cierraConexion();
        this.dispose();
    }
    
    public buscadorComun2(java.awt.Dialog parent, boolean modal, String[][] camposID, String tabla, String campos, String[] campobusqueda, String condicion, int[]ColumnSelect, String[]veTitle, int[]columnHide, int[] columnSizes, String defaultComboItem) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.veCamposID = camposID; 
        this.tabla = tabla;
        this.ColumnSelect = ColumnSelect;
        this.campos = campos;
        this.veTitle = veTitle;veClass = new Class[veTitle.length];
        this.columnHide = columnHide;
        this.condicion = condicion;
        this.campoBusqueda = campobusqueda;
        this.columnSizes = columnSizes;
        
        for (int i = 0; i < veTitle.length; i++)
            veClass[i]=Object.class;
        for (int i = 0; i < ColumnSelect.length; i++)
            ColumnSelect[i]-=1;
        
        veDato = new String[]{};
        com_bus.removeAllItems();
        
        for (int i = 0; i < campoBusqueda.length; i++)
            com_bus.addItem(campoBusqueda[i]);
        com_bus.setSelectedItem(defaultComboItem);
        
        txt_bus.setText(null);
        txt_bus.requestFocus();
        setVeDatoEmpty();
        ConsultaSimple();
//        if (columnHide.length > 0) {
//            comJTable.esconderColumnas(jTable1, columnHide);
//        }
        //ajCombox.cargarDatos(com_column, comJTable.getHeardersName(jTable1));

        txt_bus.requestFocus();
        escape();
    }

    private void ConsultaSimple() {
        consultasql="select "+campos+" from "+tabla+" "+condicion;
        System.out.println(consultasql);
        comJTable.cargarGrilla(jTable1, veClass, colEdit , veTitle, consultasql);
        comJTable.esconderColumnas(jTable1, columnHide);
        
        for(int i = 0; i < jTable1.getColumnCount(); i++) {

           jTable1.getColumnModel().getColumn(i).setPreferredWidth(columnSizes[i]);

        }
//        comJTable.Columnas.setHide(jTable1, columnHide);
        jTable1.changeSelection(0, 0, false, false);
    }
    
    private void ConsultaSimple2() {
        if (condicion.isEmpty()){
            consultasql="select "+campos+" from "+tabla+ " where upper(cast("+com_bus.getSelectedItem().toString()+" as char)) like upper('%"+txt_bus.getText()+"%') ";
        }else{
            consultasql="select "+campos+" from "+tabla+ " "+condicion+" and upper(cast("+com_bus.getSelectedItem().toString()+" as char)) like upper('%"+txt_bus.getText()+"%') ";

        }
        System.out.println(consultasql);
        comJTable.cargarGrilla(jTable1, veClass, colEdit , veTitle, consultasql);
        comJTable.esconderColumnas(jTable1, columnHide);

        for(int i = 0; i < jTable1.getColumnCount(); i++) {

           jTable1.getColumnModel().getColumn(i).setPreferredWidth(columnSizes[i]);

        }

//        comJTable.Columnas.setHide(jTable1, columnHide);
        jTable1.changeSelection(0, 0, false, false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txt_bus = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        com_bus = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        txt_bus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_busKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_busKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_busKeyTyped(evt);
            }
        });

        jButton1.setMnemonic('A');
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Filtrar por ");

        com_bus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        com_bus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                com_busKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(com_bus, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txt_bus, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                        .addGap(1, 1, 1)))
                .addGap(11, 11, 11))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(com_bus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(txt_bus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(jTable1.getSelectedRow()!=-1) {
            traspasarDatos();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_busKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busKeyReleased
   

        
    }//GEN-LAST:event_txt_busKeyReleased

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        if (evt.getKeyCode() == 10) {
            jButton1ActionPerformed(null);
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            jButton1ActionPerformed(null);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void txt_busKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busKeyPressed
       if (evt.getKeyCode() == 10){
            this.jTable1.grabFocus();
        }
    }//GEN-LAST:event_txt_busKeyPressed

    private void com_busKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_com_busKeyPressed
        if (evt.getKeyCode() == 10){
            txt_bus.grabFocus();
        }
    }//GEN-LAST:event_com_busKeyPressed

    private void txt_busKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busKeyTyped
        if (evt.getKeyChar()== '\''){
            evt.consume();
        }
        else{
            if (txt_bus.getText().isEmpty()){
                ConsultaSimple();
            }else{
                ConsultaSimple2();
            
            }
          }
    }//GEN-LAST:event_txt_busKeyTyped

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                buscadorComun2 dialog = new  buscadorComun2(new javax.swing.JDialog(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox com_bus;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txt_bus;
    // End of variables declaration//GEN-END:variables

}
