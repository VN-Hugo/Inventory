package GUI;
import java.awt.Color;
import GUI.Component.Login.Background;
import GUI.Component.Login.TextField;
import GUI.Component.Login.Button;
import GUI.Component.Login.PasswordField;
import java.awt.BorderLayout;
import javax.swing.*;
import raven.toast.Notifications;
import DTO.TaiKhoanDTO;
import DAO.TaiKhoanDAO;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
public class Login extends JPanel {

    public Login() {
        initComponents();
        setOpaque(false);
    }

    private void initComponents() {
        background = new Background();
        panel = new JPanel();
        txtUser = new TextField();
        txtPassword = new PasswordField();
        jLabel1 = new JLabel();
        cmdLogin = new Button();

        background.setBlur(panel);
        panel.setOpaque(false);

        txtUser.setHint("Tên đăng nhập");
        txtPassword.setHint("Mật khẩu");

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 24));
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Đăng nhập");

        cmdLogin.setForeground(new Color(231, 231, 231));
        cmdLogin.setText("Đăng nhập");
        cmdLogin.addActionListener(evt -> cmdLoginActionPerformed());

        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addGap(60)
                    .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtUser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPassword, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmdLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(70, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addGap(70)
                    .addComponent(jLabel1)
                    .addGap(30)
                    .addComponent(txtUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(18)
                    .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addComponent(cmdLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(70, Short.MAX_VALUE))
        );

        GroupLayout backgroundLayout = new GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addContainerGap(311, Short.MAX_VALUE)
                    .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(311, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addContainerGap(136, Short.MAX_VALUE)
                    .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(137, Short.MAX_VALUE))
        );

        setLayout(new BorderLayout());
        add(background, BorderLayout.CENTER);
    }

    private void cmdLoginActionPerformed() {
        String username = txtUser.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Vui lòng điền đầy đủ thông tin đăng nhập!");
            return;
        }
        try {
            TaiKhoanDTO taiKhoan = TaiKhoanDAO.getInstance().DangNhap(username, password);
            if (taiKhoan != null) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Đăng nhập thành công! Xin chào " + taiKhoan.getUsername());
                FormManager.getInstance().setUser(taiKhoan);
                FormManager.getInstance().login();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Tên đăng nhập hoặc mật khẩu không đúng.");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            Notifications.getInstance().show(Notifications.Type.ERROR, "Lỗi kết nối đến cơ sở dữ liệu!");
        }
    }


    
    private Background background;
    private Button cmdLogin;
    private JLabel jLabel1;
    private JPanel panel;
    private PasswordField txtPassword;
    private TextField txtUser;
}
