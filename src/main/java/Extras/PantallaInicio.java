
package Extras;


import com.dairy.menu.MenuPrincipal;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author o.o
 */
public class PantallaInicio extends javax.swing.JFrame {
    
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
    
    public PantallaInicio() {   
        initComponents();
        setSize(930, 670);
        
        txtCorreo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Correo");
        txtCorreo.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Contraseña");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");
        
        
              
    }
    private void login(){
     
       String correoIngresado = txtCorreo.getText();
       String contraseñaIngresada = new String(txtPassword.getPassword());
       
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
        txtCorreo.setText("");
        txtPassword.setText("");
       
       }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        iconPass = new javax.swing.JLabel();
        iconUser = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        empezar = new javax.swing.JButton();
        panelBeige = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jlabelIniciaSesion = new javax.swing.JLabel();
        botonregistrarse = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(226, 237, 207));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator2.setBackground(new java.awt.Color(232, 230, 230));
        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 320, 20, 30));

        jSeparator1.setBackground(new java.awt.Color(232, 230, 230));
        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 240, 20, 30));

        iconPass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconPass.setIcon(new FlatSVGIcon(getClass().getResource("/Iconos/pass.svg")));
        jPanel1.add(iconPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 310, 60, 50));

        iconUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconUser.setIcon(new FlatSVGIcon(getClass().getResource("/Iconos/user.svg")));
        jPanel1.add(iconUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 230, 60, 50));

        txtCorreo.setBackground(new java.awt.Color(248, 255, 236));
        txtCorreo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtCorreo.setMargin(new java.awt.Insets(0, 47, 0, 0));
        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 230, 300, 50));

        txtPassword.setBackground(new java.awt.Color(248, 255, 236));
        txtPassword.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtPassword.setMargin(new java.awt.Insets(0, 47, 0, 0));
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        jPanel1.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 310, 300, 50));

        empezar.setBackground(new java.awt.Color(126, 143, 74));
        empezar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        empezar.setForeground(new java.awt.Color(255, 255, 255));
        empezar.setText("Empezar");
        empezar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        empezar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empezarActionPerformed(evt);
            }
        });
        jPanel1.add(empezar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 390, 300, 50));

        panelBeige.setBackground(new java.awt.Color(126, 143, 74));
        panelBeige.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("en cada gota!");
        panelBeige.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 260, 50));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 40)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("¡Innovacion y calidad");
        panelBeige.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/vaca.png"))); // NOI18N
        panelBeige.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-80, 240, 590, 400));

        jPanel1.add(panelBeige, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 640));

        jlabelIniciaSesion.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jlabelIniciaSesion.setForeground(new java.awt.Color(126, 143, 74));
        jlabelIniciaSesion.setText("Inicia sesión");
        jPanel1.add(jlabelIniciaSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 140, -1, -1));

        botonregistrarse.setBackground(new java.awt.Color(255, 255, 255));
        botonregistrarse.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        botonregistrarse.setForeground(new java.awt.Color(126, 143, 74));
        botonregistrarse.setText("Regístrate");
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
        jPanel1.add(botonregistrarse, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 460, 80, 20));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("¿Aún no tienes una cuenta?  ");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 460, -1, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 650));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void empezarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empezarActionPerformed
        login();
    }//GEN-LAST:event_empezarActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void botonregistrarseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonregistrarseMouseEntered
        botonregistrarse.setForeground(new Color(126,143,74));
    }//GEN-LAST:event_botonregistrarseMouseEntered

    private void botonregistrarseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonregistrarseMouseExited
        botonregistrarse.setForeground(new Color(255,51,255));
    }//GEN-LAST:event_botonregistrarseMouseExited

    private void botonregistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonregistrarseActionPerformed

        PantallaRegistro registro = new PantallaRegistro();
        registro.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_botonregistrarseActionPerformed

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
                new PantallaInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonregistrarse;
    private javax.swing.JButton empezar;
    private javax.swing.JLabel iconPass;
    private javax.swing.JLabel iconUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel jlabelIniciaSesion;
    private javax.swing.JPanel panelBeige;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
