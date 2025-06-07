package GUI.Form;

import DAO.KhuVucKhoDAO;
import DTO.SanPhamDTO;
import Utils.Validation;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import raven.toast.Notifications;


public class SanPhamForm extends JPanel {
    

    private JTextField tensp,xuatxu,thuonghieu;
    private JComboBox khuvuckho = new JComboBox();
    private SanPhamDTO spDTO;
    public SanPhamForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));
   
        tensp = new JTextField();
        thuonghieu = new JTextField();
        xuatxu = new JTextField();
        // style
        tensp.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập tên sản phẩm");
        thuonghieu.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "VinFast,...");
        xuatxu.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Việt Nam,...");

        // add to panel
        createTitle("Thông tin sản phẩm");

        add(new JLabel("Tên sản phẩm"), "gapy 5 0");
        add(tensp);
        add(new JLabel("Thương hiệu"), "gapy 5 0");
        add(thuonghieu);
        add(new JLabel("Xuất xứ"), "gapy 5 0");
        add(xuatxu);

    }

    private void createTitle(String title) {
        JLabel lb = new JLabel(title);
        lb.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:+2");
        add(lb, "gapy 5 0");
        add(new JSeparator(), "height 2!,gapy 0 0");
    }
    private void initComboItem(JComboBox<String> combo) {
    KhuVucKhoDAO dao = KhuVucKhoDAO.getInstance();
    ArrayList<String> tenKhuList = dao.getAllTenKhuVucKho();
    
    combo.removeAllItems(); 
    for (String ten : tenKhuList) {
        combo.addItem(ten);
    }
    }

    public void formOpen() {
        tensp.grabFocus();
    }
    public boolean Validation(){
         if (Validation.isEmpty(tensp.getText())) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Tên sản phẩm không được để trống!");
            return false;
         }
         else if (Validation.isEmpty(xuatxu.getText())) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Tên xuất xứ không được để trống!");
            return false;
         }
         else if (Validation.isEmpty(thuonghieu.getText())) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Tên thương hiệu không được để trống!");
            return false;
         }
          return true;
    }


      // Gán dữ liệu từ DTO vào form
    public void setData(SanPhamDTO data) {
        if (data != null) {
            tensp.setText(data.getTensp());
            thuonghieu.setText(data.getThuonghieu());
            xuatxu.setText(data.getXuatxu());
            this.spDTO = data;
        }
  }

    // Lấy dữ liệu người dùng nhập
    public SanPhamDTO getData() {
        if (spDTO == null) {
            spDTO = new SanPhamDTO();
        }
        spDTO.setTensp(tensp.getText());
        spDTO.setThuonghieu(thuonghieu.getText());
        spDTO.setXuatxu(xuatxu.getText());
        return spDTO;
    }

}