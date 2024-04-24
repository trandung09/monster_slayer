package org.game.form;

import javax.swing.*;

import org.game.database.ConnectMySQL;
import org.game.enums.GameState;
import org.game.frame.GamePanel;

import java.awt.event.*;
import java.util.LinkedList;;

public class Login extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */

    private GamePanel gp;

    public Login(GamePanel gp) {
        initComponents();
        this.gp = gp;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        userNameLabel = new JLabel();
        userNameTextField = new JTextField();
        loginButton = new JButton();
        createAccountButton = new JButton();
        createAccountLabel  = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        userNameLabel.setText("Usename:");

        userNameTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                userNameTextFieldActionPerformed(evt);
            }
        });

        loginButton.setText("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        createAccountButton.setText("Sign up");
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                createAccountButtonActionPerformed(evt);
            }
        });

        createAccountLabel.setText("Account");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(loginButton)
                        .addGap(18, 18, 18)
                        .addComponent(createAccountButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(userNameLabel, GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userNameTextField, GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(createAccountLabel, GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(createAccountLabel, GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(userNameLabel, GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userNameTextField, GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(loginButton)
                    .addComponent(createAccountButton))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>                        

    private void loginButtonActionPerformed(ActionEvent evt) {  
        
        LinkedList<String[]> list = ConnectMySQL.query();
        String name = userNameTextField.getText();

        boolean check = false;
        
        for (String[] s : list) {
            if (name.equals(s[0])) {
                check = true;
                gp.player.setName(name);
                gp.mainState = GameState.START;
                setVisible(false);
                break;
            }
        }

        if (check == false) {
            JOptionPane.showMessageDialog(null,
                                "You need to create an account");
        }
    }                                              

    private void userNameTextFieldActionPerformed(ActionEvent evt) {                                                  

    }                                                 

    private void createAccountButtonActionPerformed(ActionEvent evt) {                                            
        LinkedList<String[]> list = ConnectMySQL.query();
        String name = userNameTextField.getText();

        boolean check = false;
        
        for (String[] s : list) {
            if (name.equals(s[0])) {
                check = true;
                break;
            }
        }

        if (check == false) {
            ConnectMySQL.insert(name, 1e9);
            gp.screenUI.list = ConnectMySQL.query();
            JOptionPane.showMessageDialog(null,
                                "Account created successfully");
        }
        else {
            JOptionPane.showMessageDialog(null,
                                "Username already exists");
        }
    }                                           

    // Variables declaration - do not modify                     
    private JButton createAccountButton;
    private JButton loginButton;
    private JLabel createAccountLabel;
    private JLabel userNameLabel;
    private JTextField userNameTextField;
    // End of variables declaration                   
}
