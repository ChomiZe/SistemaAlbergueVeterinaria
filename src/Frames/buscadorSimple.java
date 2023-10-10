/*
 * buscadorSimple2.java
 *
 * Created on 04/12/2011, 12:43:53 PM
 */

package Frames;

import comunes.comJTable;

/**
 *
 * @author pcsproject
 */
public class buscadorSimple extends javax.swing.JDialog {
    private String campos="",tabla="",otherWhere="",FinalWhere="",sqlxyz="";
    private String[]campoBusqueda, veTitle;
    private int[]ColumnSelect,columnHide;
    private Class[]veClass;
    static private String[]veDato;
    private int[]colEdit={0};
        
    /** Creates new form buscadorSimple2 */
    public buscadorSimple(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public static String[] getVeDato() {
        return veDato;
    }
    protected static void setVeDatoEmpty() {
        veDato = new String[]{};
    }
    
    private void traspasarDatos() {
        veDato = new String[ColumnSelect.length];
            for (int i = 0; i < ColumnSelect.length; i++)
                veDato[i]=jTable1.getValueAt(jTable1.getSelectedRow(), ColumnSelect[i]).toString();
        this.dispose();
    }

    public buscadorSimple(java.awt.Frame parent, boolean modal, String campos, String tabla, String otherWhere, String FinalWhere,
            String[]campoBusqueda, int[]ColumnSelect, String[]veTitle, int[]columnHide) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
            this.campos = campos; this.tabla = tabla;
            this.otherWhere = otherWhere; this.FinalWhere = FinalWhere;
            this.campoBusqueda = campoBusqueda; this.ColumnSelect = ColumnSelect;
            this.veTitle = veTitle;veClass = new Class[veTitle.length];

            this.columnHide = columnHide;

            for (int i = 0; i < veTitle.length; i++)
                veClass[i]=Object.class;
            for (int i = 0; i < ColumnSelect.length; i++)
                ColumnSelect[i]-=1;
            veDato = new String[]{};
            com_bus.removeAllItems();
            for (int i = 0; i < campoBusqueda.length; i++)
                com_bus.addItem(campoBusqueda[i]);
            txt_bus.setText(null);
            txt_bus.requestFocus();
            setVeDatoEmpty();
            ConsultaSimple(otherWhere, FinalWhere);
            //ajCombox.cargarDatos(com_column, comJTable.getHeardersName(jTable1));

            txt_bus.requestFocus();
    }

    private void ConsultaSimple(String otherWhere, String FinalWhere) {
        if (!(otherWhere.isEmpty()) && !(FinalWhere.isEmpty()))
            sqlxyz="select "+campos+" from "+tabla+" where "+otherWhere+" "+FinalWhere;
            else
                if (otherWhere.isEmpty() && FinalWhere.isEmpty())
                    sqlxyz="select "+campos+" from "+tabla;
                else
                    if(!otherWhere.isEmpty())
                        sqlxyz="select "+campos+" from "+tabla+" where "+otherWhere;
                    else
                        if(!FinalWhere.isEmpty())
                            sqlxyz="select "+campos+" from "+tabla+" "+FinalWhere;
            //setVeDatoInicial(columnHide);
            comJTable.cargarGrilla(jTable1, veClass, colEdit , veTitle, sqlxyz);
            comJTable.Columnas.setHide(jTable1, columnHide);
    }

    private void ConsultaLargo(String otherWhere, String FinalWhere) {
        if (!(otherWhere.isEmpty()) && !(FinalWhere.isEmpty()))
              sqlxyz="select "+campos+" from "+tabla+" where "+otherWhere+" and upper(cast("+com_bus.getSelectedItem().toString()+" as char)) like upper('%"+txt_bus.getText()+"%')"+" "+FinalWhere;
            else
                if(otherWhere.isEmpty() && FinalWhere.isEmpty())
                    sqlxyz="select "+campos+" from "+tabla+" where upper(cast("+com_bus.getSelectedItem().toString()+" as char)) like upper('%"+txt_bus.getText()+"%')";
                else
                    if(!otherWhere.isEmpty())
                        sqlxyz="select "+campos+" from "+tabla+" where "+otherWhere+" and upper(cast("+com_bus.getSelectedItem().toString()+" as char)) like upper('%"+txt_bus.getText()+"%')";
                    else
                        if(!FinalWhere.isEmpty())
                            sqlxyz="select "+campos+" from "+tabla+" where upper(cast("+com_bus.getSelectedItem().toString()+" as char)) like upper('%"+txt_bus.getText()+"%') "+FinalWhere;
            //setVeDatoInicial(columnHide);
            comJTable.cargarGrilla(jTable1, veClass, colEdit , veTitle, sqlxyz);
            comJTable.Columnas.setHide(jTable1, columnHide);
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
        jScrollPane1.setViewportView(jTable1);

        txt_bus.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_busCaretUpdate(evt);
            }
        });
        txt_bus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_busKeyReleased(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/circular icons/yes.png"))); // NOI18N
        jButton1.setMnemonic('A');
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Filtrar por:");

        com_bus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(jTable1.getSelectedRow()!=-1) {
            traspasarDatos();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_busKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busKeyReleased
        if (txt_bus.getText().isEmpty())
            ConsultaSimple(otherWhere, FinalWhere);
        else
            ConsultaLargo(otherWhere, FinalWhere);
    }//GEN-LAST:event_txt_busKeyReleased

    private void txt_busCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_busCaretUpdate
        if (txt_bus.getText().isEmpty())
            ConsultaSimple(otherWhere, FinalWhere);
        else
            ConsultaLargo(otherWhere, FinalWhere);
    }//GEN-LAST:event_txt_busCaretUpdate

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                buscadorSimple dialog = new  buscadorSimple(new javax.swing.JFrame(), true);          
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
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