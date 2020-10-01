/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visualizar;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author jamt_
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ImageIcon icon = new ImageIcon(getClass().getResource("/Imagenes/FondoHospital.jpg"));
        Image image = icon.getImage();
        Escritorio = new javax.swing.JDesktopPane(){

            public void paintComponent(Graphics g){
                g.drawImage(image,0,0,getWidth(),getHeight(),this);
            }

        };
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuArchivo = new javax.swing.JMenu();
        MenuRegistros = new javax.swing.JMenu();
        MenuPaciente = new javax.swing.JMenuItem();
        MenuMedicos = new javax.swing.JMenuItem();
        MenuGenerar = new javax.swing.JMenu();
        MenuHospitalizacion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout EscritorioLayout = new javax.swing.GroupLayout(Escritorio);
        Escritorio.setLayout(EscritorioLayout);
        EscritorioLayout.setHorizontalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1050, Short.MAX_VALUE)
        );
        EscritorioLayout.setVerticalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 626, Short.MAX_VALUE)
        );

        MenuArchivo.setText("Archivo");
        MenuArchivo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuBar1.add(MenuArchivo);

        MenuRegistros.setText("Registros");
        MenuRegistros.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        MenuPaciente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MenuPaciente.setText("Pacientes");
        MenuPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuPacienteActionPerformed(evt);
            }
        });
        MenuRegistros.add(MenuPaciente);

        MenuMedicos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MenuMedicos.setText("Medicos");
        MenuMedicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuMedicosActionPerformed(evt);
            }
        });
        MenuRegistros.add(MenuMedicos);

        jMenuBar1.add(MenuRegistros);

        MenuGenerar.setText("Generar");
        MenuGenerar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        MenuHospitalizacion.setText("Hospitalizacion");
        MenuHospitalizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuHospitalizacionActionPerformed(evt);
            }
        });
        MenuGenerar.add(MenuHospitalizacion);

        jMenuBar1.add(MenuGenerar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Escritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Escritorio)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuPacienteActionPerformed
        // TODO add your handling code here:
        IngresarPaciente ingPac = new IngresarPaciente();
        Escritorio.add(ingPac);
        ingPac.show();
    }//GEN-LAST:event_MenuPacienteActionPerformed

    private void MenuMedicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuMedicosActionPerformed
        // TODO add your handling code here:
        IngresarMedico ingMed = new IngresarMedico();
        Escritorio.add(ingMed);
        ingMed.show();
    }//GEN-LAST:event_MenuMedicosActionPerformed

    private void MenuHospitalizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuHospitalizacionActionPerformed
        // TODO add your handling code here:
        GenerarHospitalizacion genHos = new GenerarHospitalizacion();
        Escritorio.add(genHos);
        genHos.show();
    }//GEN-LAST:event_MenuHospitalizacionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane Escritorio;
    private javax.swing.JMenu MenuArchivo;
    private javax.swing.JMenu MenuGenerar;
    private javax.swing.JMenuItem MenuHospitalizacion;
    private javax.swing.JMenuItem MenuMedicos;
    private javax.swing.JMenuItem MenuPaciente;
    private javax.swing.JMenu MenuRegistros;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
