package GUI.Form;

import DAO.NhanVienDAO;
import DTO.TaiKhoanDTO;
import Utils.Validation;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

public class TaiKhoanForm extends JPanel {

    private JTextField tentk, matkhau;
    private JComboBox<Integer> manv;
    private JComboBox<String> role;
    private TaiKhoanDTO tkDTO;

    public TaiKhoanForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));

        tentk = new JTextField();
        matkhau = new JTextField();
        manv = new JComboBox<>();
        role = new JComboBox<>();

        // style
        tentk.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập tên tài khoản");
        matkhau.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "123456");

        // add to panel
        createTitle("Thông tin tài khoản");

        add(new JLabel("Tên tài khoản"), "gapy 5 0");
        add(tentk);
        add(new JLabel("Mật khẩu"), "gapy 5 0");
        add(matkhau);
        add(new JLabel("Mã NV"), "gapy 5 0");
        add(manv);
        add(new JLabel("Vai trò"), "gapy 5 0");
        add(role);

        initComboItem(manv);
        initComboRole(role);
    }

    private void createTitle(String title) {
        JLabel lb = new JLabel(title);
        lb.putClientProperty(FlatClientProperties.STYLE, "font:+2");
        add(lb, "gapy 5 0");
        add(new JSeparator(), "height 2!,gapy 0 0");
    }

    private void initComboItem(JComboBox<Integer> combo) {
        NhanVienDAO dao = NhanVienDAO.getInstance();
        ArrayList<Integer> maNVList = dao.getAllMaNV();

        combo.removeAllItems();
        for (Integer ma : maNVList) {
            combo.addItem(ma);
        }
    }

    private void initComboRole(JComboBox<String> combo) {
        String[] roles = {"Quản lý", "Nhân viên nhập kho", "Nhân viên xuất kho"};
        combo.removeAllItems();
        for (String r : roles) {
            combo.addItem(r);
        }
    }

    public void formOpen() {
        tentk.grabFocus();
    }

    public boolean Validation() {
        if (Validation.isEmpty(tentk.getText())) {
            JOptionPane.showMessageDialog(this, "Tên tài khoản không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Validation.isEmpty(matkhau.getText()) || matkhau.getText().length() < 6) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được rỗng và phải có ít nhất 6 ký tự", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (manv.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Phải chọn Mã nhân viên", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (role.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Phải chọn Vai trò", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // Gán dữ liệu từ DTO vào form
    public void setData(TaiKhoanDTO data) {
        if (data != null) {
            tentk.setText(data.getUsername());
            matkhau.setText(data.getMatkhau());
            if (data.getManv()!= 0) {
                manv.setSelectedItem(data.getManv());
            }
            if (data.getRole() != null) {
                role.setSelectedItem(data.getRole());
            }
            this.tkDTO = data;
        }
    }

    // Lấy dữ liệu người dùng nhập
    public TaiKhoanDTO getData() {
        if (tkDTO == null) {
            tkDTO = new TaiKhoanDTO();
        }
        tkDTO.setUsername(tentk.getText());
        tkDTO.setMatkhau(matkhau.getText());
        if (manv.getSelectedItem() != null) {
            tkDTO.setManv((Integer) manv.getSelectedItem());
        }
        if (role.getSelectedItem() != null) {
            tkDTO.setManhomquyen((String) role.getSelectedItem());
        }
        return tkDTO;
    }
}
