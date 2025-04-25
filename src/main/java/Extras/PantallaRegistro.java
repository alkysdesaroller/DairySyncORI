
package Extras;

import DatabaseConnection.DatabaseConnection;
import com.dairy.menu.MenuPrincipal;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author o.o
 */
public class PantallaRegistro extends javax.swing.JFrame {
  
    public class Credenciales{
    private String correo;
    private String contraseña;
    
      public Credenciales(String correo,String contraseña ){
        this.correo = correo;
        this.contraseña = contraseña;
      }
      
      public boolean VerificarCredenciales(String correoIngresado, String contraseñaIngresada){
      
        return this.correo.equals(correoIngresado) && this.contraseña.equals(contraseñaIngresada);
      }
    }
    
    public PantallaRegistro() {   
        initComponents();
        setSize(930, 670);
        
        txtApellido.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Apellido");
        txtApellido.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        txtNombre.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nombre");
        txtNombre.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        txtCorreo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Correo");
        txtCorreo.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Contraseña");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");
        
        
              
    }
    private void login(){
     
       String correoIngresado = txtApellido.getText();
       String contraseñaIngresada = new String(txtPassword.getPassword());
       Connection  conn = null;
       PreparedStatement stmt = null;
       ResultSet rs = null;
       
      
       Credenciales creden = new Credenciales("awi123", "hola123");
       Credenciales creden1 = new Credenciales("alb809", "alna732");
       if(creden.VerificarCredenciales(correoIngresado, contraseñaIngresada) || creden1.VerificarCredenciales(correoIngresado, contraseñaIngresada)){
         JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.");
         dispose(); 
       
           MenuPrincipal menu = new MenuPrincipal(); 
        menu.setVisible(true);
       }
       else{
        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
        txtApellido.setText("");
        txtPassword.setText("");
       
       }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtApellido = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        empezar = new javax.swing.JButton();
        panelBeige = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jlabelIniciaSesion = new javax.swing.JLabel();
        botonregistrarse = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(226, 237, 207));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtApellido.setBackground(new java.awt.Color(248, 255, 236));
        txtApellido.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });
        jPanel1.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 170, 220, 50));

        txtPassword.setBackground(new java.awt.Color(248, 255, 236));
        txtPassword.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        jPanel1.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 460, 60));

        empezar.setBackground(new java.awt.Color(126, 143, 74));
        empezar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        empezar.setForeground(new java.awt.Color(255, 255, 255));
        empezar.setText("Crear cuenta");
        empezar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        empezar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empezarActionPerformed(evt);
            }
        });
        jPanel1.add(empezar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 410, 320, 50));

        panelBeige.setBackground(new java.awt.Color(126, 143, 74));
        panelBeige.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cow.png"))); // NOI18N
        panelBeige.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-60, 0, 490, 650));

        jPanel1.add(panelBeige, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 340, 640));

        jlabelIniciaSesion.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jlabelIniciaSesion.setForeground(new java.awt.Color(126, 143, 74));
        jlabelIniciaSesion.setText("Regístrate");
        jPanel1.add(jlabelIniciaSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        botonregistrarse.setBackground(new java.awt.Color(255, 255, 255));
        botonregistrarse.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        botonregistrarse.setForeground(new java.awt.Color(126, 143, 74));
        botonregistrarse.setText("Inicia sesión");
        botonregistrarse.setBorder(null);
        botonregistrarse.setBorderPainted(false);
        botonregistrarse.setContentAreaFilled(false);
        botonregistrarse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonregistrarseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonregistrarseMouseExited(evt);
            }
        });
        botonregistrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonregistrarseActionPerformed(evt);
            }
        });
        jPanel1.add(botonregistrarse, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 470, 80, 20));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("¿Ya tienes una cuenta?  ");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, -1, 20));

        txtCorreo.setBackground(new java.awt.Color(248, 255, 236));
        txtCorreo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 460, 60));

        txtNombre.setBackground(new java.awt.Color(248, 255, 236));
        txtNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 220, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 640));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void empezarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empezarActionPerformed
        login();
    }//GEN-LAST:event_empezarActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void botonregistrarseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonregistrarseMouseEntered
        botonregistrarse.setForeground(new Color(153,0,153));
    }//GEN-LAST:event_botonregistrarseMouseEntered

    private void botonregistrarseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonregistrarseMouseExited
        botonregistrarse.setForeground(new Color(255,51,255));
    }//GEN-LAST:event_botonregistrarseMouseExited

    private void botonregistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonregistrarseActionPerformed

        PantallaInicio inicio = new PantallaInicio();
        inicio.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_botonregistrarseActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       try {
            
          FlatLightLaf.setup();
         UIManager.put("Component.innerFocusWidth", 3);
         // UIManager.put("TextComponent.arc", 999);
           
        } catch (Exception ex) {
            System.err.println("No se pudo cargar el tema FlatLaf MacOS Light.");
            ex.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaRegistro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonregistrarse;
    private javax.swing.JButton empezar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jlabelIniciaSesion;
    private javax.swing.JPanel panelBeige;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
