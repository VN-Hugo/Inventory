package GUI.Form;

import DTO.KhachHangDTO;
import GUI.Panel.KhachHang;
import Utils.Validation;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import raven.datetime.DatePicker;

public class KhachHangForm extends JPanel {


    private JTextField tenKH, diachi,sodienthoai;
    private DatePicker datePicker;
    private KhachHangDTO khDTO;
    public KhachHangForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));
   
        tenKH = new JTextField();
        sodienthoai = new JTextField();
        diachi = new JTextField();
        datePicker = new DatePicker();
        JFormattedTextField dateEditor = new JFormattedTextField();
        datePicker.setEditor(dateEditor);
        datePicker.setCloseAfterSelected(true);

        tenKH.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập tên nhà cung cấp");
        sodienthoai.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0123456789");

        // add to panel
        createTitle("Thông tin khách hàng");

        add(new JLabel("Tên khách hàng"), "gapy 5 0");
        add(tenKH);
        add(new JLabel("Số điện thoại"), "gapy 5 0");
        add(sodienthoai);

        add(new JLabel("Địa chỉ khách hàng"), "gapy 5 0");
        add(diachi);
        add(new JLabel("Ngày tham gia"), "gapy 5 0");
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
        tenKH.grabFocus();
    }
    public boolean Validation(){
         if (Validation.isEmpty(tenKH.getText())) {
            JOptionPane.showMessageDialog(this, "Tên nhà cung cấp không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
         }
         else  if (Validation.isEmpty(diachi.getText())) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
         }
         else if (Validation.isEmpty(sodienthoai.getText()) || !Validation.isNumber(sodienthoai.getText()) && sodienthoai.getText().length()!=10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải là 10 ký tự số", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
         }
          return true;
    }


      // Gán dữ liệu từ DTO vào form
    public void setData(KhachHangDTO data) {
        if (data != null) {
            tenKH.setText(data.getHoten());
            sodienthoai.setText(data.getSdt());
            diachi.setText(data.getDiachi());
            this.khDTO = data;
            if (data.getNgaythamgia() != null) {
                LocalDate localDate = data.getNgaythamgia().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
                datePicker.setSelectedDate(localDate);
            }
        }
    }

    // Lấy dữ liệu người dùng nhập
    public KhachHangDTO getData() {
        if (khDTO == null) {
            khDTO = new KhachHangDTO();
        }
        khDTO.setHoten(tenKH.getText());
        khDTO.setSdt(sodienthoai.getText());
        khDTO.setDiachi(diachi.getText());
         LocalDate localDate = datePicker.getSelectedDate();
        if (localDate != null) {
        Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            khDTO.setNgaythamgia(utilDate);  // bạn cần thêm setter này trong KhachHangDTO
        }       else {
        khDTO.setNgaythamgia(null);  // hoặc xử lý mặc định
        }
         return khDTO;
    }

}