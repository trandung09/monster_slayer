package org.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JFrame menuFrame;

    public RegistrationForm() {
        setTitle("Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        // Tạo các components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel emailLabel = new JLabel("Email:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailField = new JTextField(20);

        JButton registerButton = new JButton("Register");

        // Thiết lập layout
        setLayout(new GridLayout(4, 2));

        // Thêm components vào frame
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(emailLabel);
        add(emailField);
        add(new JLabel()); // empty label for spacing
        add(registerButton);

        // Thiết lập sự kiện cho nút "Register"
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lấy giá trị từ các trường nhập liệu
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();

                // Thực hiện xử lý đăng ký (ở đây chỉ hiển thị thông tin)
                JOptionPane.showMessageDialog(null, "Registered:\nUsername: " + username + "\nEmail: " + email);

                // Chuyển đến frame MENU
                openMenuFrame();
            }
        });

        // Khởi tạo frame MENU
        initMenuFrame();
    }

    // Khởi tạo frame MENU
    private void initMenuFrame() {
        menuFrame = new JFrame("MENU");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(300, 200);

        // Tạo components cho MENU
        JButton backButton = new JButton("Back to Registration Form");

        // Thiết lập layout cho MENU
        menuFrame.setLayout(new FlowLayout());

        // Thêm components vào MENU
        menuFrame.add(backButton);

        // Thiết lập sự kiện cho nút "Back to Registration Form"
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Đóng frame MENU và hiển thị frame đăng ký
                menuFrame.setVisible(false);
                setVisible(true);
            }
        });
    }

    // Mở frame MENU
    private void openMenuFrame() {
        menuFrame.setVisible(true);
        setVisible(false); // Ẩn frame đăng ký
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RegistrationForm form = new RegistrationForm();
                form.setVisible(true);
            }
        });
    }
}
