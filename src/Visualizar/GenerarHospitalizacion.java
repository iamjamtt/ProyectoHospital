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
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jamt_
 */
public class GenerarHospitalizacion extends javax.swing.JInternalFrame {

    /**
     * Creates new form IngresarCamas
     */
    public GenerarHospitalizacion() {
        initComponents();
        //cargarComboCama();
        cargarComboPlanta();
        obtenerDiagnostico();
        txtIDp.setVisible(false);
        txtIDp1.setVisible(false);
        txtVisitanueva.setVisible(false);
    }

    void cargarComboCama(){      
        if(cboPlantas.getSelectedIndex()==1){
            String SQL = "SELECT idCama FROM Cama WHERE idPlanta="+1+"";
            try {
                PreparedStatement pst = cn.prepareStatement(SQL);
                ResultSet rs = pst.executeQuery();
                
                while(rs.next()){
                    cboCama.addItem(rs.getString("idCama"));
                }
            } catch (Exception e) {
                System.out.println("Error en combo Cama: " + e);
            }
        }
     
    }
    
    void cargarComboPlanta(){
        String SQL = "SELECT * FROM Planta";
        try {
            PreparedStatement pst = cn.prepareStatement(SQL);
            ResultSet rs = pst.executeQuery();
            cboPlantas.addItem("Seleccione una Opcion");
            
            while(rs.next()){
                cboPlantas.addItem(rs.getString("descripcion"));
            }
        } catch (Exception e) {
            System.out.println("Error en combo Planta: " + e);
        }
    }    
      
    
    public void bucarMedico(){
        String dni=txtDniM.getText();      
            try {
                    String mostrar="SELECT m.idMedico,m.dni,m.nombre,m.apellidoPaterno,m.apellidoMaterno,e.descripcionE,m.turno FROM Medico m INNER JOIN Especialidad e ON m.idEspecialidad=e.idEspecialidad WHERE m.estado="+1+" AND m.condicion="+1+" AND m.dni='"+dni+"'";
                     //String ConsultaSQL="SELECT * FROM Paciente WHERE dni='"+dni+"'";

                     Statement st = cn.createStatement();
                     ResultSet rs = st.executeQuery(mostrar);                    
                     
                     if(rs.next()){
                         
                         String nombreC= rs.getString("nombre") +" " + rs.getString("apellidoPaterno") + " " + rs.getString("apellidoMaterno");
                         
                        txtDniM.setText(rs.getString("dni"));
                        txtNombreM.setText(nombreC);
                        txtIDp1.setText(rs.getString("idMedico"));
                        txtTurno.setText(rs.getString("turno"));
                        txtEspecialidad.setText(rs.getString("descripcionE"));
                         System.out.println("hola: " + txtIDp1.getText());
                    } 
                        
             } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
    public void bucarPaciente(){
        String dni=txtDniP.getText();   
            try {
                     String ConsultaSQL="SELECT * FROM Paciente WHERE dni='"+dni+"'";

                     Statement st = cn.createStatement();
                     ResultSet rs = st.executeQuery(ConsultaSQL);                    
                     
                     if(rs.next()){
                         
                        String nombreC= rs.getString("nombre") +" " + rs.getString("apellidoPaterno") + " " + rs.getString("apellidoMaterno"); 
                        txtDniP.setText(rs.getString("dni"));
                        txtNombreP.setText(nombreC);
                        txtIDp.setText(rs.getString("idPaciente"));
                        txtHistoriaP.setText(rs.getString("historiaClinica"));
                        System.out.println("hola: " + txtIDp.getText());
                        int financiador = rs.getInt("idFinanciador");
             
                        try {
                            String ConsultaSQLFinanciador="SELECT * FROM Financiador WHERE idFinanciador='"+financiador+"'";

                            Statement stf = cn.createStatement();
                            ResultSet rsf = stf.executeQuery(ConsultaSQLFinanciador);                    

                            if(rsf.next()){

                               txtFinancieroP.setText(rsf.getString("nombreFinanciador"));
                            } 

                            } catch (SQLException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    } 
                        
             } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
    void obtenerDiagnostico(){
            String SQL = "SELECT descripcionD FROM Diagnostico";
            try {
                PreparedStatement pst = cn.prepareStatement(SQL);
                ResultSet rs = pst.executeQuery();
                
                cboDiagnostico.addItem("Seleccione una Opcion");
                
                while(rs.next()){
                    cboDiagnostico.addItem(rs.getString("descripcionD"));
                }
            } catch (Exception e) {
                System.out.println("Error en combo Cama: " + e);
            }
        
    }
    
    
    void ingresar(){
            
        String sql="INSERT INTO Historial (idPaciente,idMedico,idCama,idDiagnostico,idVisita,peso,talla,fechaHistorial) VALUES (?,?,?,?,?,?,?,?)";
      
        try {
            PreparedStatement pst  = cn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtIDp.getText()) );
            pst.setInt(2, Integer.parseInt(txtIDp1.getText()));
            pst.setInt(3, Integer.parseInt(cboCama.getSelectedItem().toString()));   
            pst.setInt(4, cboDiagnostico.getSelectedIndex());
            
            if(valorVisita==true){
                pst.setInt(5, idVisitaCambiar);
            }
            else{
                pst.setInt(5, Integer.parseInt(txtVisitanueva.getText()));
            }       
            
            pst.setDouble(6, Double.parseDouble(txtPeso.getText()));
            pst.setDouble(7, Double.parseDouble(txtTalla.getText()));

            Date fechaActual = new Date();
            int anioactual = fechaActual.getYear()+1900;
            int mesactual = fechaActual.getMonth()+1;
            int diaactual = fechaActual.getDate();            
            String fecha = anioactual+"-"+mesactual+"-"+diaactual;
            
            System.out.println(fecha);
            System.out.println(anioactual);
            System.out.println(mesactual);
            System.out.println(diaactual);
            pst.setString(8, fecha);
            
            int n=pst.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "Registro Guardado con Exito");
            }
            
        } catch (SQLException ex) {
            System.out.println("Error al ingresar HISTORIAL datos: " + ex);
        }
    }
    
    void PesoTallaPaciente(){
        
        int idPaciente = Integer.parseInt(txtIDp.getText());
        int cont = 0;
        double peso=0, talla=0;
        
        DecimalFormat df = new DecimalFormat("#.00");
        //System.out.println(df.format(number));
        
        String mostrar="SELECT idPaciente, peso, talla FROM Historial";
             
        try {
              Statement st = cn.createStatement();
              ResultSet rs = st.executeQuery(mostrar);
              
              while(rs.next())
              {
                  if(idPaciente == rs.getInt("idPaciente")){
                      peso = rs.getDouble("peso");
                      talla = rs.getDouble("talla");
                      cont++;
                  }
              }

        } catch (SQLException ex) {
            System.out.println("Error en la tabla medico: " + ex);
        }
        
        //verificamos al mismo paciente para modificar su idCama y poner estado 1
        if(cont>0){
            txtPeso.setText(df.format(peso));
            txtTalla.setText(df.format(talla));
        }
        System.out.println(cont+"  numero de id de pacientes repetidos");
    }
    
    
    boolean valorVisita  = false;
    int idVisitaCambiar = 0;

    void VerificarVisita(){
        
        int idPaciente = Integer.parseInt(txtIDp.getText());
        int cont = 0;
        
        String mostrar="SELECT idPaciente, idVisita FROM Historial";
             
        try {
              Statement st = cn.createStatement();
              ResultSet rs = st.executeQuery(mostrar);
              
              while(rs.next())
              {
                  if(idPaciente == rs.getInt("idPaciente")){
                      idVisitaCambiar = rs.getInt("idVisita");
                      cont++;
                  }
              }

        } catch (SQLException ex) {
            System.out.println("error en seleccionar  al verificar visita en historial: " + ex);
        }
        
        //verificamos al mismo paciente para modificar su idVisita
        if(cont>0){
            valorVisita = true;
        }
        
    }
    
    
    void VerificarCama(){
        
        int idPaciente = Integer.parseInt(txtIDp.getText());
        int cont = 0, idCamaCambiar = 0;
        
        String mostrar="SELECT idPaciente, idCama FROM Historial";
             
        try {
              Statement st = cn.createStatement();
              ResultSet rs = st.executeQuery(mostrar);
              
              while(rs.next())
              {
                  if(idPaciente == rs.getInt("idPaciente")){
                      idCamaCambiar = rs.getInt("idCama");
                      cont++;
                  }
              }

        } catch (SQLException ex) {
            System.out.println("error en seleccionar historial: " + ex);
        }
        
        //verificamos al mismo paciente para modificar su idCama y poner estado 1
        if(cont>0){
            try {
                int idcama = idCamaCambiar;
   
                String insertar = "UPDATE Cama SET "
                +"estado="+1+" "
                +"WHERE idCama="+idcama+"";
                PreparedStatement pst = cn.prepareStatement(insertar);
                pst.executeUpdate();

            } catch (Exception e) {
                    System.out.println("error al MODIFICAR CAMA de entrada los datos: "+e);
            }
        }
        System.out.println(cont+"-"+idCamaCambiar);
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
        jLabel2 = new javax.swing.JLabel();
        txtDniP = new javax.swing.JTextField();
        txtNombreP = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        txtFinancieroP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtHistoriaP = new javax.swing.JTextField();
        txtIDp = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTalla = new javax.swing.JTextField();
        txtVisitanueva = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        cboDiagnostico = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cboPlantas = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cboCama = new javax.swing.JComboBox<>();
        btnobtenerCama = new javax.swing.JButton();
        btnGuardarHistorial = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtEspecialidad = new javax.swing.JTextField();
        btnsbuscar2 = new javax.swing.JToggleButton();
        txtNombreM = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDniM = new javax.swing.JTextField();
        txtTurno = new javax.swing.JTextField();
        txtIDp1 = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Hospitalizacion");

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle Paciente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("DNI:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("PACIENTE:");

        txtDniP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtNombreP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNombreP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombrePActionPerformed(evt);
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

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("FINANCIADOR:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("HISTORIA CLÍNICA:");

        jLabel11.setText("PESO (KG):");

        jLabel12.setText("TALLA (m):");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtDniP, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(txtIDp, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNombreP, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtFinancieroP, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHistoriaP)
                            .addComponent(txtTalla, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVisitanueva, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDniP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscar)
                        .addComponent(txtIDp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtVisitanueva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombreP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtHistoriaP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFinancieroP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(txtTalla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agregar Diagnostico", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboDiagnostico, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(cboDiagnostico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(204, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingresar Cama", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("PLANTA:");

        cboPlantas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboPlantas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboPlantasMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cboPlantasMousePressed(evt);
            }
        });
        cboPlantas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPlantasActionPerformed(evt);
            }
        });
        cboPlantas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboPlantasKeyPressed(evt);
            }
        });

        jLabel9.setText("CAMA:");

        cboCama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboCamaMouseClicked(evt);
            }
        });

        btnobtenerCama.setBackground(new java.awt.Color(204, 204, 204));
        btnobtenerCama.setText("OBTENER");
        btnobtenerCama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnobtenerCamaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addGap(36, 36, 36)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(cboPlantas, 0, 160, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnobtenerCama, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboCama, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cboPlantas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btnobtenerCama, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboCama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnGuardarHistorial.setBackground(new java.awt.Color(204, 204, 204));
        btnGuardarHistorial.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnGuardarHistorial.setText("GUARDAR REGISTRO");
        btnGuardarHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarHistorialActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(204, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle Medico", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        btnsbuscar2.setBackground(new java.awt.Color(204, 204, 204));
        btnsbuscar2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnsbuscar2.setText("BUSCAR");
        btnsbuscar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsbuscar2ActionPerformed(evt);
            }
        });

        txtNombreM.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("TURNO:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("ESPECIALIDAD:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("DNI:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("MÉDICO:");

        txtDniM.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtDniM, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIDp1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnsbuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNombreM, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(108, 108, 108)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                    .addComponent(txtTurno))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDniM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsbuscar2)
                    .addComponent(jLabel7)
                    .addComponent(txtTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIDp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNombreM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarHistorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnGuardarHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboPlantasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboPlantasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboPlantasKeyPressed

    private void cboPlantasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboPlantasMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cboPlantasMouseClicked

    private void cboPlantasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboPlantasMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboPlantasMousePressed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        bucarPaciente();
        PesoTallaPaciente();

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void cboPlantasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPlantasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboPlantasActionPerformed

    private void cboCamaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboCamaMouseClicked
     
    }//GEN-LAST:event_cboCamaMouseClicked

    private void btnobtenerCamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnobtenerCamaActionPerformed
        // TODO add your handling code here:
        
        /*LLAMAMOS A LA FUNCION PARA ACTIVAR LA CAMA POR UN MOMENTO
          SI AL BUSCAR PACIENTE ES EL MISMO QUE HAY EN LA BASE DE DATOS*/
        VerificarCama(); 
        
        int id = cboPlantas.getSelectedIndex();
        String ConsultaSQL = "SELECT * FROM Cama WHERE idPlanta="+id +" AND estado="+1;
        try {   
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ConsultaSQL);

            cboCama.removeAllItems();
            
            while(rs.next()){
                
                cboCama.addItem(rs.getString("idCama"));  
            }
            
            System.out.println("dd: " + id);
            
        } catch (Exception e) {
            System.out.println("ERROR seleccionar datos: "+e.getMessage());
        }
    }//GEN-LAST:event_btnobtenerCamaActionPerformed

    private void btnsbuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsbuscar2ActionPerformed
        // TODO add your handling code here:
        bucarMedico();
    }//GEN-LAST:event_btnsbuscar2ActionPerformed

    private void btnGuardarHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarHistorialActionPerformed

        /*NO BORRES NADA PUPILO, LO COMENTADO SIRVE*/
        
        /*LLAMAMOS A LA FUNCION VERIFICAR VISITA, DONDE SI ESTAMO GUARDANDO AL MISMO PACIENTE MAS DE UNA VEZ
           ESTO SE GUARDARA CON EL MISMO IDVISITA*/
        
        VerificarVisita();
        System.out.println(idVisitaCambiar);
        System.out.println(valorVisita);
        
        if(valorVisita==false){
            String sql="INSERT INTO Visita (estadoVisita,nroVisita) VALUES (?,?)";
            try {
                PreparedStatement pst  = cn.prepareStatement(sql);
                pst.setString(1, "1");
                pst.setString(2, "0");

                pst.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("Error al ingresar VISITA datos: " + ex);
            }

            String Consul = "SELECT idVisita FROM Visita WHERE idVisita = (SELECT MAX(idVisita ) FROM Visita)";
            try {   
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(Consul);

                if(rs.next()){
                    txtVisitanueva.setText(rs.getString("idVisita"));
                }

                System.out.println(" "+ txtVisitanueva.getText());

            } catch (Exception e) {
                System.out.println("ERROR seleccionar VISITA datos: "+e.getMessage());
            }
        }

        ingresar();
        
        try {
                int idcama = Integer.parseInt(cboCama.getSelectedItem().toString());
   
                    String insertar = "UPDATE Cama SET "
                    +"estado="+2+" "
                    +"WHERE idCama="+idcama+"";
                    PreparedStatement pst = cn.prepareStatement(insertar);

                    pst.executeUpdate();

        } catch (Exception e) {
                System.out.println("error al MODIFICAR CAMA los datos: "+e);
        }
        
        
    }//GEN-LAST:event_btnGuardarHistorialActionPerformed

    private void txtNombrePActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombrePActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombrePActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnBuscar;
    private javax.swing.JButton btnGuardarHistorial;
    private javax.swing.JButton btnobtenerCama;
    private javax.swing.JToggleButton btnsbuscar2;
    private javax.swing.JComboBox<String> cboCama;
    private javax.swing.JComboBox<String> cboDiagnostico;
    private javax.swing.JComboBox<String> cboPlantas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTextField txtDniM;
    private javax.swing.JTextField txtDniP;
    private javax.swing.JTextField txtEspecialidad;
    private javax.swing.JTextField txtFinancieroP;
    private javax.swing.JTextField txtHistoriaP;
    private javax.swing.JTextField txtIDp;
    private javax.swing.JTextField txtIDp1;
    private javax.swing.JTextField txtNombreM;
    private javax.swing.JTextField txtNombreP;
    private javax.swing.JTextField txtPeso;
    private javax.swing.JTextField txtTalla;
    private javax.swing.JTextField txtTurno;
    private javax.swing.JTextField txtVisitanueva;
    // End of variables declaration//GEN-END:variables
ConexionSQL cc = new ConexionSQL();
Connection cn= ConexionSQL.conexionn();
}
