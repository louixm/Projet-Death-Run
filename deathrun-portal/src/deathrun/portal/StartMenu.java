/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathrun.portal;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author trazafit
 */
public class StartMenu extends javax.swing.JFrame {
    /**
     * Creates new form StartMenu
     */
    static Image img;
    
    public StartMenu() {
        initComponents();
        imageSentryBot.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon("./images/sentrybot.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        imageBotBleu.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon("./images/robotBleu.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        imageBotOrange.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon("./images/robotOrange.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        //try {
        //    //img = ImageIO.read(new File("./images/DoorUnlocked.png"));
        //} catch (IOException ex) {
        //    Logger.getLogger(StartMenu.class.getName()).log(Level.SEVERE, null, ex);
        //}
        try {
            background.setIcon(new javax.swing.ImageIcon(ImageIO.read(new File("./images/fond_4.png"))));
        } catch (IOException ex) {
            Logger.getLogger(StartMenu.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("pas d'image");
        }
        
        alive = true;
        this.addWindowListener(new WindowAdapter() {
            @Override      
            public void windowClosing(WindowEvent e) {
                alive = false;
            }
        });
        
        updateList();
    }
    
    public int avatar = 0;
    public String pseudo;
    public boolean start;
    public boolean alive;
    
    public ArrayList<String> liste;
    public  Sync sync;
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        startButton = new javax.swing.JButton();
        labelChoixAvatar = new javax.swing.JLabel();
        labelDeathRun = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listeJoueurs = new javax.swing.JList<>();
        labelJoueursConnectes = new javax.swing.JLabel();
        imageBotOrange = new javax.swing.JButton();
        imageSentryBot = new javax.swing.JButton();
        imageBotBleu = new javax.swing.JButton();
        Pseudo_label = new javax.swing.JLabel();
        Text_Pseudo = new javax.swing.JTextField();
        background = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(400, 400));
        setMinimumSize(new java.awt.Dimension(500, 350));
        setPreferredSize(new java.awt.Dimension(400, 280));
        setResizable(false);
        getContentPane().setLayout(null);

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });
        getContentPane().add(startButton);
        startButton.setBounds(410, 270, 57, 23);

        labelChoixAvatar.setFont(new java.awt.Font("Trebuchet MS", 3, 18)); // NOI18N
        labelChoixAvatar.setForeground(new java.awt.Color(255, 153, 51));
        labelChoixAvatar.setText("Choisir un avatar");
        labelChoixAvatar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        labelChoixAvatar.setMaximumSize(new java.awt.Dimension(100, 50));
        labelChoixAvatar.setMinimumSize(new java.awt.Dimension(100, 50));
        getContentPane().add(labelChoixAvatar);
        labelChoixAvatar.setBounds(30, 100, 170, 21);

        labelDeathRun.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        labelDeathRun.setForeground(new java.awt.Color(0, 102, 255));
        labelDeathRun.setText("Death Run Portal");
        getContentPane().add(labelDeathRun);
        labelDeathRun.setBounds(90, 10, 360, 42);

        listeJoueurs.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listeJoueursValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listeJoueurs);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(370, 90, 110, 140);

        labelJoueursConnectes.setForeground(new java.awt.Color(255, 153, 51));
        labelJoueursConnectes.setText("Joueurs connectés");
        getContentPane().add(labelJoueursConnectes);
        labelJoueursConnectes.setBounds(380, 70, 150, 14);

        imageBotOrange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageBotOrangeActionPerformed(evt);
            }
        });
        getContentPane().add(imageBotOrange);
        imageBotOrange.setBounds(230, 130, 80, 70);

        imageSentryBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageSentryBotActionPerformed(evt);
            }
        });
        getContentPane().add(imageSentryBot);
        imageSentryBot.setBounds(20, 130, 80, 70);

        imageBotBleu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageBotBleuActionPerformed(evt);
            }
        });
        getContentPane().add(imageBotBleu);
        imageBotBleu.setBounds(120, 130, 80, 70);

        Pseudo_label.setForeground(new java.awt.Color(255, 153, 0));
        Pseudo_label.setText("Pseudo :");
        getContentPane().add(Pseudo_label);
        Pseudo_label.setBounds(20, 70, 50, 14);

        Text_Pseudo.setFont(new java.awt.Font("Times New Roman", 0, 10)); // NOI18N
        Text_Pseudo.setToolTipText("votre nom");
        Text_Pseudo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Text_PseudoKeyReleased(evt);
            }
        });
        getContentPane().add(Text_Pseudo);
        Text_Pseudo.setBounds(70, 70, 240, 20);

        background.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        background.setMaximumSize(new java.awt.Dimension(500, 300));
        background.setMinimumSize(new java.awt.Dimension(500, 300));
        background.setPreferredSize(new java.awt.Dimension(500, 300));
        getContentPane().add(background);
        background.setBounds(0, 0, 500, 300);

        statusLabel.setText("status");
        getContentPane().add(statusLabel);
        statusLabel.setBounds(50, 250, 260, 14);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if (pseudo != null) {
            start = true;
            alive = false;
            dispose();
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void imageBotOrangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageBotOrangeActionPerformed
        avatar = 1;
    }//GEN-LAST:event_imageBotOrangeActionPerformed

    private void imageSentryBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageSentryBotActionPerformed
        avatar = 2;
    }//GEN-LAST:event_imageSentryBotActionPerformed

    private void imageBotBleuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageBotBleuActionPerformed
        avatar = 0;
    }//GEN-LAST:event_imageBotBleuActionPerformed

    private void listeJoueursValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listeJoueursValueChanged
      
    }//GEN-LAST:event_listeJoueursValueChanged

    private void Text_PseudoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Text_PseudoKeyReleased
        pseudo = Text_Pseudo.getText();
    }//GEN-LAST:event_Text_PseudoKeyReleased

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
            java.util.logging.Logger.getLogger(StartMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Pseudo_label;
    private javax.swing.JTextField Text_Pseudo;
    private javax.swing.JLabel background;
    private javax.swing.JButton imageBotBleu;
    private javax.swing.JButton imageBotOrange;
    private javax.swing.JButton imageSentryBot;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labelChoixAvatar;
    private javax.swing.JLabel labelDeathRun;
    private javax.swing.JLabel labelJoueursConnectes;
    private javax.swing.JList<String> listeJoueurs;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables

    private void updateList() {
        try {
            sync = new Sync(DriverManager.getConnection(
                    "jdbc:mysql://nemrod.ens2m.fr:3306/20182019_s2_vs2_tp1_deathrun?serverTimezone=UTC", 
                    "deathrun2", 
                    "5V8HVbDZMtkOHwaX"
                ));
            PreparedStatement req = sync.srv.prepareStatement("SELECT name FROM players");
            ResultSet r = req.executeQuery();
            DefaultListModel listModel = new DefaultListModel();
            while (r.next()) {
                        String name = r.getString("name");                    
                        listModel.addElement(name); 
            }
            listeJoueurs.setModel(listModel);
        }
                
        catch (SQLException err) {
            System.out.println("sql connection error, fail to init game:\n\t"+err);
        }
    }
}
