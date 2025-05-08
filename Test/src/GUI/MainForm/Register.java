package GUI.MainForm;

import GUI.MainForm.Login;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import GUI.MainForm.FormManager;
import raven.toast.Notifications;

import raven.datetime.DatePicker;


import javax.swing.*;
import java.awt.*;

public class Register extends JPanel {
    public Register() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        txtName = new JTextField();
//      txtBirth = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtConfirmPassword = new JPasswordField();
        
//        datePicker = new DatePicker();
//        JFormattedTextField dateEditor = new JFormattedTextField();
//        datePicker.setEditor(dateEditor);
//        datePicker.setCloseAfterSelected(true);

 // Sử dụng DatePicker thay cho JTextField cho trường ngày sinh
    DatePicker datePicker = new DatePicker();
    JFormattedTextField dateEditor = new JFormattedTextField();
    
    // Thiết lập editor cho DatePicker
    datePicker.setEditor(dateEditor);

    // Thiết lập giá trị mặc định (placeholder) cho ngày sinh
    dateEditor.setText("Ngày sinh");

    // Cài đặt để đóng popup sau khi chọn ngày
    datePicker.setCloseAfterSelected(true);

        cmdRegister = new JButton("Đăng ký");

        cmdRegister.addActionListener(e -> {
            if (isMatchPassword()) {
                //  Do something here
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Mật khẩu không khớp. Vui lòng thử lại!");
            }
        });


        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        txtName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Họ và tên");
//        txtBirth.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ngày sinh");
       
        

        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập tên đăng nhập");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mật khẩu");
        txtConfirmPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập lại mật khẩu");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        txtConfirmPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");

        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        JLabel lbTitle = new JLabel("Tạo tài khoản");
        JLabel description = new JLabel("Đăng ký ngay để sử dụng hệ thống metro để di chuyển thuận tiện hơn!");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

//        passwordStrengthStatus.initPasswordField(txtPassword);

        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Thông tin người dùng"), "gapy 10");
        panel.add(txtName, "split 2");
        panel.add(dateEditor, "split 2"); // Thêm DatePicker vào panel
        txtPhone = new JTextField();
        txtPhone.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập số điện thoại");
        txtGmail = new JTextField();
        txtGmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập Email của bạn");
        panel.add(new JLabel("Thông tin liên lạc"),"gapy 10");
        panel.add(txtPhone, "split 2");
        panel.add(txtGmail);
        panel.add(new JLabel("Giới tính"), "gapy 8");
        panel.add(CreateGenderPanel());
        panel.add(new JSeparator(), "gapy 5 5");
        panel.add(new JLabel("Tên đăng nhập"));
        panel.add(txtUsername);
        panel.add(new JLabel("Mật khẩu"), "gapy 8");
        panel.add(txtPassword);
//        panel.add(passwordStrengthStatus, "gapy 0");
        panel.add(new JLabel("Xác nhận mật khẩu"), "gapy 0");
        panel.add(txtConfirmPassword);
        panel.add(cmdRegister, "gapy 20");
        panel.add(createLoginLabel(), "gapy 10");
        add(panel);
    }

    private Component CreateGenderPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        jrMale = new JRadioButton("Nam");
        jrFemale = new JRadioButton("Nữ");
        groupGender = new ButtonGroup();
        groupGender.add(jrMale);
        groupGender.add(jrFemale);
        jrMale.setSelected(true);
        panel.add(jrMale);
        panel.add(jrFemale);
        return panel;
    }

    private Component createLoginLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        JButton cmdLogin = new JButton("<html><a href=\"#\">Đăng nhập</a></html>");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:3,3,3,3");
        cmdLogin.setContentAreaFilled(false);
        cmdLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdLogin.addActionListener(e -> {
            FormManager.getInstance().showForm(new Login());
        });
        JLabel label = new JLabel("Nếu bạn đã có tài khoản !");
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");
        panel.add(label);
        panel.add(cmdLogin);
        return panel;
    }

    public boolean isMatchPassword() {
        String password = String.valueOf(txtPassword.getPassword());
        String confirmPassword = String.valueOf(txtConfirmPassword.getPassword());
        return password.equals(confirmPassword);
    }

    private JTextField txtName;
    private JTextField txtBirth;
    private JRadioButton jrMale;
    private JRadioButton jrFemale;
    private JTextField txtUsername;
    private JTextField txtGmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private ButtonGroup groupGender;
    private JButton cmdRegister;
    private JTextField txtPhone;
    private DatePicker datePicker;
//    private PasswordStrengthStatus passwordStrengthStatus;
}