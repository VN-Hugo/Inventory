package GUI.Form;

import DTO.NhanVienDTO;
import GUI.Panel.NhanVien;
import Utils.Validation;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import raven.datetime.DatePicker;

public class NhanVienForm extends JPanel {


    private JTextField tenNV, sdt,email;
    private DatePicker datePicker;
    private NhanVienDTO nvDTO;
    private ButtonGroup groupGender;
    private JRadioButton jrMale;
    private JRadioButton jrFemale;
    public NhanVienForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));
   
        tenNV = new JTextField();
        email = new JTextField();
        sdt = new JTextField();
        datePicker = new DatePicker();
        JFormattedTextField dateEditor = new JFormattedTextField();
        datePicker.setEditor(dateEditor);
        datePicker.setCloseAfterSelected(true);

        tenNV.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập tên nhà cung cấp");
        sdt.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0123456789");
        email.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "email@gamil.com");
        // add to panel
        createTitle("Thông tin nhân viên");

        add(new JLabel("Tên nhân viên"), "gapy 5 0");
        add(tenNV);
        add(new JLabel("Email"), "gapy 5 0");
        add(email);
        add(new JLabel("Giới tính"), "gapy 5 0");
        add(createGenderPanel());
        add(new JLabel("Số điện thoại"), "gapy 5 0");
        add(sdt);
        add(new JLabel("Ngày sinh"), "gapy 5 0");
        add(dateEditor, "growx");
  
    }

    private void createTitle(String title) {
        JLabel lb = new JLabel(title);
        lb.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:+2");
        add(lb, "gapy 5 0");
        add(new JSeparator(), "height 2!,gapy 0 0");
    }


    public void formOpen() {
        tenNV.grabFocus();
    }
    public boolean Validation(){
         if (Validation.isEmpty(tenNV.getText())) {
            JOptionPane.showMessageDialog(this, "Tên nhà cung cấp không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
         }
          else if (Validation.isEmpty(email.getText()) || !Validation.isEmail(email.getText())) {
            JOptionPane.showMessageDialog(this, "Email không được rỗng và phải đúng cú pháp", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
         }
         else if (Validation.isEmpty(sdt.getText()) || !Validation.isNumber(sdt.getText()) && sdt.getText().length()!=10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải là 10 ký tự số", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
         }
          return true;
    }


      // Gán dữ liệu từ DTO vào form
    public void setData(NhanVienDTO data) {
        if (data != null) {
            tenNV.setText(data.getHoten());
            email.setText(data.getEmail());
            sdt.setText(data.getSdt());
            this.nvDTO = data;
            if (data.getNgaysinh() != null) {
            Date date = data.getNgaysinh();
            if (date instanceof java.sql.Date) {
                    // Cách chuyển sang LocalDate đúng nhất với java.sql.Date
                LocalDate localDate = ((java.sql.Date) date).toLocalDate();
                datePicker.setSelectedDate(localDate);
    } else {
        // Với java.util.Date hoặc Timestamp
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        datePicker.setSelectedDate(localDate);
    }
}
            // Xử lý giới tính
            if (data.getGioitinh() == 1) {
                    jrMale.setSelected(true);
            } else {
                jrFemale.setSelected(true);
                }
        }
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
    // Lấy dữ liệu người dùng nhập
    public NhanVienDTO getData() {
        if (nvDTO == null) {
            nvDTO = new NhanVienDTO();
        }
        nvDTO.setHoten(tenNV.getText());
        nvDTO.setEmail(email.getText());
        nvDTO.setSdt(sdt.getText());
        LocalDate localDate = datePicker.getSelectedDate();
        int gioitinh = jrMale.isSelected() ? 1 : 0;
        nvDTO.setGioitinh(gioitinh);
        if (localDate != null) {
            Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            nvDTO.setNgaysinh(utilDate);  // bạn cần thêm setter này trong NhanVienDTO
        }       else {
        nvDTO.setNgaysinh(null);  // hoặc xử lý mặc định
        }
        return nvDTO;
    }

}