/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visualizar;

import ConexionSQL.ConexionSQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jamt_
 */
public class DetalleHistorial extends javax.swing.JInternalFrame {

    /**
     * Creates new form GuardarVisita
     */
    DefaultTableModel model;
    
    public DetalleHistorial() {
        initComponents();
        cargar2("");
    }
    
    void cargar2(String valor){
        
        String mostrar="SELECT p.dni,m.nombre,c.idCama,h.peso,h.talla,d.descripcionD,h.fechaHistorial FROM Historial h INNER JOIN Paciente p ON h.idPaciente=p.idPaciente INNER JOIN Medico m ON h.idMedico=m.idMedico INNER JOIN Cama c ON h.idCama=c.idCama INNER JOIN Diagnostico d ON h.idDiagnostico=d.idDiagnostico WHERE p.dni LIKE '%"+valor+"%' OR m.nombre LIKE '%"+valor+"%'";

        String []titulos={"DNI. Paciente","Medico","Cod. Cama","Peso Paciente","Talla Paciente","Diagnostico Paciente","Fecha de Ingreso"};

        String []Registros=new String[7];
        model= new DefaultTableModel(null, titulos);
        try {
              Statement st = cn.createStatement();
              ResultSet rs = st.executeQuery(mostrar);
              while(rs.next())
              {
                  Registros[0]= rs.getString("dni");
                  Registros[1]= rs.getString("nombre");
                  Registros[2]= rs.getString("idCama");
                  Registros[3]= rs.getString("peso");
                  Registros[4]= rs.getString("talla");
                  Registros[5]= rs.getString("descripcionD");
                  Registros[6]= rs.getString("fechaHistorial");       
                  model.addRow(Registros);
              }
              tablaMostrarHistorial.setModel(model);
        } catch (SQLException ex) {
            System.out.println("Error en la tabla paciente: " + ex);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMostrarHistorial = new javax.swing.JTable();
        btnMostrarTodos = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("LISTA HISTORIAL");

        txtBuscar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("BUSCAR:");

        tablaMostrarHistorial.setBackground(new java.awt.Color(204, 204, 204));
        tablaMostrarHistorial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaMostrarHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaMostrarHistorial.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablaMostrarHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMostrarHistorialMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaMostrarHistorial);

        btnMostrarTodos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnMostrarTodos.setText("Mostrar Todos");
        btnMostrarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMostrarTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("LISTA HISTORIAL");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        cargar2(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tablaMostrarHistorialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMostrarHistorialMouseClicked
        // TODO add your handling code here:
        /*String dni=(String) tablaMostrarHistorial.getValueAt(tablaMostrarHistorial.getSelectedRow(),0);
        String mostrar;
        try {
            String ConsultaSQL="SELECT * FROM Paciente WHERE dni='"+dni+"'";

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ConsultaSQL);

            if(rs.next()){
                txtDNI.setText(rs.getString("dni"));
                txtNombre.setText(rs.getString("nombre"));
                txtPaterno.setText(rs.getString("apellidoPaterno"));
                txtMaterno.setText(rs.getString("apellidoMaterno"));
                dateNacimiento.setDate(rs.getDate("fechaNacimiento"));
                txtDireccion.setText(rs.getString("direccion"));
                cboSexo.setSelectedItem(rs.getString("sexo"));
                txtHisClinica.setText(rs.getString("historiaClinica"));
                cboFinanciador.setSelectedIndex(rs.getInt("idFinanciador"));
            }
            desbloquear();
            btnModificar2();
            System.out.println("dd: " + dni);
        } catch (Exception e) {
            System.out.println("ERROR seleccionar datos: "+e.getMessage());
        }*/
    }//GEN-LAST:event_tablaMostrarHistorialMouseClicked

    private void btnMostrarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTodosActionPerformed
        // TODO add your handling code here:
        txtBuscar.setText("");
        cargar2("");
    }//GEN-LAST:event_btnMostrarTodosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMostrarTodos;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaMostrarHistorial;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
ConexionSQL cc = new ConexionSQL();
Connection cn= ConexionSQL.conexionn();
}
