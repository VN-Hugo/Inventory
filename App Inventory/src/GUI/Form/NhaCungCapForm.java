package GUI.Form;

import DTO.NhaCungCapDTO;
import Utils.Validation;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

import javax.swing.*;
import java.awt.event.KeyAdapter;

public class NhaCungCapForm extends JPanel {
    

    private JTextField tenNcc, diachi,email,sodienthoai;
    private NhaCungCapDTO nccDTO;
    public NhaCungCapForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));
   
        tenNcc = new JTextField();
        sodienthoai = new JTextField();
        email = new JTextField();
        diachi = new JTextField();

        

        // style
        tenNcc.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập tên nhà cung cấp");
        sodienthoai.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0123456789");
        email.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "email@example.com");

        // add to panel
        createTitle("Thông tin nhà cung cấp");

        add(new JLabel("Tên nhà cung cấp"), "gapy 5 0");
        add(tenNcc);
        add(new JLabel("Số điện thoại"), "gapy 5 0");
        add(sodienthoai);
        add(new JLabel("Địa chỉ email"), "gapy 5 0");
        add(email);

        add(new JLabel("Địa chỉ nhà cung cấp"), "gapy 5 0");
//        add(scroll, "height 150,grow,pushy");
        add(diachi);
     

  }
    
    

    private void createTitle(String title) {
        JLabel lb = new JLabel(title);
        lb.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:+2");
        add(lb, "gapy 5 0");
        add(new JSeparator(), "height 2!,gapy 0 0");
    }


    public void formOpen() {
        tenNcc.grabFocus();
    }
    public boolean Validation(){
         if (Validation.isEmpty(tenNcc.getText())) {
            JOptionPane.showMessageDialog(this, "Tên nhà cung cấp không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
         }
         else  if (Validation.isEmpty(diachi.getText())) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
         }
         else if (Validation.isEmpty(email.getText()) || !Validation.isEmail(email.getText())) {
            JOptionPane.showMessageDialog(this, "Email không được rỗng và phải đúng cú pháp", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
         }
         else if (Validation.isEmpty(sodienthoai.getText()) || !Validation.isNumber(sodienthoai.getText()) && sodienthoai.getText().length()!=10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải là 10 ký tự số", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
         }
          return true;
    }


    // Gán dữ liệu từ DTO vào form
    public void setData(NhaCungCapDTO data) {
        if (data != null) {
            tenNcc.setText(data.getTenncc());
            sodienthoai.setText(data.getSdt());
            email.setText(data.getEmail());
            diachi.setText(data.getDiachi());
            this.nccDTO = data;
        }
    }

    // Lấy dữ liệu người dùng nhập
    public NhaCungCapDTO getData() {
        if (nccDTO == null) {
            nccDTO = new NhaCungCapDTO();
        }
        nccDTO.setTenncc(tenNcc.getText());
        nccDTO.setSdt(sodienthoai.getText());
        nccDTO.setEmail(email.getText());
        nccDTO.setDiachi(diachi.getText());
        return nccDTO;
    }

}