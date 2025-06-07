package GUI.Panel;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import Utils.Validation;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.datetime.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Register extends JDialog {

    private JTextField txtName;
    private JRadioButton jrMale;
    private JRadioButton jrFemale;
    private JTextField txtUsername;
    private JTextField txtGmail;
    private ButtonGroup groupGender;
    private JButton cmdSave;
    private JButton cmdCancel;
    private JTextField txtPhone;
    private DatePicker datePicker;
    private NhanVienDTO nvDTO;
    private NhanVien jpNv;
    private String type;

    public Register(Frame owner, boolean modal) {
        super(owner, "Thêm nhân viên", modal);
        init("Thêm nhân viên", "add");
    }

    public Register(NhanVien jpNv, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.jpNv = jpNv;
        init(title, type);
    }

    public Register(NhanVien jpNv, JFrame owner, String title, boolean modal, String type, NhanVienDTO nvDTO) {
        super(owner, title, modal);
        this.jpNv = jpNv;
        this.nvDTO = nvDTO;
        this.type = type;
        init(title, type);
        txtName.setText(nvDTO.getHoten());
        txtGmail.setText(nvDTO.getEmail());
        txtPhone.setText(nvDTO.getSdt());
        datePicker.setSelectedDate(nvDTO.getNgaysinh()
        .toInstant()
        .atZone(java.time.ZoneId.systemDefault())
        .toLocalDate());
        if (nvDTO.getGioitinh() == 1) {
            jrMale.setSelected(true);
        } else {
            jrFemale.setSelected(true);
        }
    }

    private void init(String title, String type) {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(new Dimension(900, 360));
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        this.type = type;

        txtName = new JTextField();
        txtGmail = new JTextField();
        txtPhone = new JTextField();

        datePicker = new DatePicker();
        JFormattedTextField dateEditor = new JFormattedTextField();
        datePicker.setEditor(dateEditor);
        datePicker.setCloseAfterSelected(true);

        cmdCancel = new JButton("Huỷ");
        cmdSave = new JButton("Lưu");

        cmdSave.addActionListener(this::handleSave);
        cmdCancel.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "arc:20");

        JLabel lbTitle = new JLabel("Thông tin nhân viên");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        JLabel description = new JLabel("Cập nhật thông tin nhân viên nhanh chóng và chính xác.");
        description.putClientProperty(FlatClientProperties.STYLE,
                "[light]foreground:lighten(@foreground,30%);" +
                        "[dark]foreground:darken(@foreground,30%)");

        txtName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Họ và tên");
        txtPhone.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập số điện thoại");
        txtGmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập Email của bạn");
        
        datePicker.addDateSelectionListener(evt -> {
    LocalDate date = datePicker.getSelectedDate();
    System.out.println("Ngày được chọn: " + date);
});
        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Họ và tên"), "gapy 10");
        panel.add(txtName);
        panel.add(new JLabel("Ngày sinh"), "gapy 10");
        panel.add(dateEditor, "growx");
        panel.add(new JLabel("Số điện thoại"), "gapy 10");
        panel.add(txtPhone);
        panel.add(new JLabel("Email"), "gapy 10");
        panel.add(txtGmail);
        panel.add(new JLabel("Giới tính"), "gapy 10");
        panel.add(createGenderPanel());

        panel.add(new JSeparator(), "gapy 5 5");
        panel.add(cmdCancel, "split 2, center, gapx 10");
        panel.add(cmdSave, "center");

        add(panel);
        pack();
        setLocationRelativeTo(getOwner());
    }

    private JPanel createGenderPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
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

    private boolean validateInput() {
        if (Validation.isEmpty(txtName.getText())) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được rỗng");
            return false;
        } else if (txtName.getText().length() < 6) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên phải có ít nhất 6 ký tự");
            return false;
        } else if (Validation.isEmpty(txtGmail.getText()) || !Validation.isEmail(txtGmail.getText())) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ");
            return false;
        } else if (Validation.isEmpty(txtPhone.getText()) || !Validation.isNumber(txtPhone.getText()) || txtPhone.getText().length() != 10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 10 chữ số");
            return false;
        }
        return true;
    }

    private void handleSave(ActionEvent e) {
        if (!validateInput()) return;

        int gioitinh = jrMale.isSelected() ? 1 : 0;
        LocalDate localDate = datePicker.getSelectedDate();
        java.util.Date date = java.util.Date.from(
        localDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
 
        if (type.equals("add")) {
            int manv = NhanVienDAO.getInstance().getAutoIncrement();
            jpNv.nvBUS.add(new NhanVienDTO(manv, txtName.getText(), gioitinh, date, txtPhone.getText(), 1, txtGmail.getText()));
        } else if (type.equals("edit") && nvDTO != null) {
            jpNv.nvBUS.update(new NhanVienDTO(nvDTO.getManv(), txtName.getText(), gioitinh, date, txtPhone.getText(), 1, txtGmail.getText()));
        }
        jpNv.loadDataTable(jpNv.listnv);
        dispose();
    }
}
