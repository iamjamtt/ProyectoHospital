/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visualizar;

import ConexionSQL.ConexionSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Davis
 */
public class Visita extends javax.swing.JInternalFrame {

    /**
     * Creates new form Visita
     */
    SpinnerNumberModel modeloSpinner = new SpinnerNumberModel();
    
    
    public Visita() {
        initComponents();
        modeloSpinner.setMaximum(4);
        modeloSpinner.setMinimum(0);
        spinerVisita.setModel(modeloSpinner);
        noeditable();
    }

    int idPacientee = 0;
   
    void obtenerIdPaciente(){  
            String dni = txtDniPaciente.getText();       
            String mostrar="SELECT idPaciente FROM Paciente  WHERE dni='"+dni+"'";
            String dnii = "";
            try {
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery(mostrar);

                    if(rs.next())
                    {
                        idPacientee = rs.getInt("idPaciente");
                    }

            } catch (SQLException ex) {
                System.out.println("error en seleccionar paciente: " + ex);
            }
    }
 
    int idVisita = 0;
    void mostrarDatosCampos(){
            try {
                if (!valida()){
                    if(repetidoDNI==true){
                        JOptionPane.showMessageDialog(null, "El DNI ingresado no existe.");
                        limpiar();
                    }else{
                        if(vr==true){
                        JOptionPane.showMessageDialog(null, "Campo de DNI imcompleto");
                        limpiar();
                        }else{
                            if(campo=true){
                                JOptionPane.showMessageDialog(null, "Falta ingresar campos.");
                                limpiar();
                            }
                        }
                    }
                    
                }else if(adddd==true){  
                        String ConsultaSQL="SELECT h.idHistorial,h.fechaHistorial,h.idPaciente,h.idVisita,p.dni,p.nombre,p.apellidoPaterno,c.idCama,v.nroVisita FROM Historial h INNER JOIN Paciente p ON h.idPaciente=p.idPaciente INNER JOIN Cama c ON h.idCama=c.idCama INNER JOIN Visita v ON h.idVisita=v.idVisita WHERE h.idPaciente="+idPacientee+"";

                        Statement st = cn.createStatement();
                        ResultSet rs = st.executeQuery(ConsultaSQL);                    
                     
                        while(rs.next()){
                            if(idPacientee == rs.getInt("idPaciente")){
                                txtNroHistorial.setText(rs.getString("idHistorial"));
                                txtDNIpaciente.setText(rs.getString("dni"));
                                String nombre = rs.getString("nombre") + " " + rs.getString("apellidoPaterno");
                                txtNombreP.setText(nombre);
                                txtNroCAMA.setText(rs.getString("idCama"));
                                txtFechaIngre.setText(rs.getString("fechaHistorial"));
                                txtCantVisita.setText(rs.getString("nroVisita"));
                                idVisita = rs.getInt("idVisita");
                            }
                        } 
                        System.out.println("id P: "+ idPacientee);
                        mostrarCupo();
                }
            } catch (Exception e) {
                System.out.println("ERROR seleccionar datos: "+e.getMessage());
            }
    }
   
    int nroVis=0;
    
    void mostrarCupo(){
        String ConsultaSQL="SELECT nroVisita FROM Visita WHERE idVisita="+idVisita;
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ConsultaSQL);
            if(rs.next()){
                nroVis = rs.getInt("nroVisita");
            }
        } catch (Exception e) {
        }
        
        
        int cupoMax=4 - nroVis;
        System.out.println("n " + nroVis);
        txtCupos.setText(""+cupoMax);
        
        if(nroVis == 4){
            spinerVisita.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Ya no hay Cupos para la Visita");
        }
    }
    
    void limpiar(){
        txtDNIpaciente.setText("");
        txtCantVisita.setText("");
        txtDniPaciente.setText("");
        txtFechaIngre.setText("");
        txtNombreP.setText("");
        txtNroCAMA.setText("");
        txtNroHistorial.setText("");
        txtCupos.setText("");
        spinerVisita.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        txtDniPaciente.requestFocus();
    }
    
    void noeditable(){
        txtDNIpaciente.setEditable(false);
        txtCantVisita.setEditable(false);
        txtCupos.setEditable(false);
        txtFechaIngre.setEditable(false);
        txtNombreP.setEditable(false);
        txtNroCAMA.setEditable(false);
        txtNroHistorial.setEditable(false);
    }
    
    public int detalleDNI(){
        String dni=txtDniPaciente.getText();
        int dniRepetido = 0;
            try {
                     String ConsultaSQL="SELECT p.dni,h.idPaciente FROM Historial h INNER JOIN Paciente p ON h.idPaciente=p.idPaciente WHERE p.dni='"+dni+"'";

                     Statement st = cn.createStatement();
                     ResultSet rs = st.executeQuery(ConsultaSQL);                    
                     
                     if(rs.next()){
                       dniRepetido = rs.getInt("idPaciente");     
                         System.out.println("dniRepetido: " + dniRepetido);
                     } 
                        
             } catch (SQLException ex) {
                 //Logger.getLogger(Laboral.class.getName()).log(Level.SEVERE, null, ex);
             }
        return dniRepetido;
    }
    
    boolean vr = false;
    boolean repetidoDNI = false;
    boolean adddd = false;
    boolean campo = false;
    boolean valida(){
        if(txtDniPaciente.getText().equals("")){
            campo = true;
            return false;
        }
        if( txtDniPaciente.getText().length()<8){
            vr = true;
            return false;
        }
        if(detalleDNI() == 0){
            repetidoDNI = true;
            return false;
        }else{
            adddd = true;
        }
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDniPaciente = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        txtCantVisita = new javax.swing.JTextField();
        txtFechaIngre = new javax.swing.JTextField();
        txtNroCAMA = new javax.swing.JTextField();
        txtDNIpaciente = new javax.swing.JTextField();
        txtNroHistorial = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNombreP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        spinerVisita = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        txtCupos = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("VISITA");
        setPreferredSize(new java.awt.Dimension(1045, 625));

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle Visita", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("DNI:");

        txtDniPaciente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDniPaciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniPacienteKeyTyped(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(204, 204, 204));
        btnBuscar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtDNIpaciente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtNroHistorial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("NRO HISTORIAL");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("DNI PACIENTE");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("NRO DE CAMA");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("FECHA DE INGRESO");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("CANT. VISITA");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("PACIENTE");

        txtNombreP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNroHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDNIpaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txtNombreP, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtNroCAMA, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaIngre, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCantVisita, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNroCAMA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaIngre, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCantVisita, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNombreP, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDNIpaciente, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNroHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Nro. Vistas:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Cupos de Visitas Disponibles:");

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDniPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinerVisita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8)))
                        .addGap(18, 18, 18)
                        .addComponent(txtCupos, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDniPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(spinerVisita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtCupos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar))
                .addContainerGap(401, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        spinerVisita.setEnabled(true);
        obtenerIdPaciente();
        mostrarDatosCampos();
        txtDniPaciente.setText("");
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        try {   
                int  visitando = (Integer) spinerVisita.getValue();
                int sumaVisita = 0;

                sumaVisita = nroVis + visitando;
                
                String insertar = "UPDATE Visita SET "
                +"nroVisita="+sumaVisita+" "
                +"WHERE idVisita="+idVisita+"";
                PreparedStatement pst = cn.prepareStatement(insertar);
                int n = pst.executeUpdate();
                System.out.println("d " + visitando);
                if(n>0){
                JOptionPane.showMessageDialog(null, "Registro Guardado con Exito");
                }
                
                limpiar();
            } catch (Exception e) {
                    System.out.println("errorinhos: "+e);
            }
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtDniPacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniPacienteKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if(c<'0' || c>'9') evt.consume();
        if (txtDniPaciente.getText().length()== 8) evt.consume(); 
    }//GEN-LAST:event_txtDniPacienteKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner spinerVisita;
    private javax.swing.JTextField txtCantVisita;
    private javax.swing.JTextField txtCupos;
    private javax.swing.JTextField txtDNIpaciente;
    private javax.swing.JTextField txtDniPaciente;
    private javax.swing.JTextField txtFechaIngre;
    private javax.swing.JTextField txtNombreP;
    private javax.swing.JTextField txtNroCAMA;
    private javax.swing.JTextField txtNroHistorial;
    // End of variables declaration//GEN-END:variables
ConexionSQL cc = new ConexionSQL();
Connection cn= ConexionSQL.conexionn();
}
