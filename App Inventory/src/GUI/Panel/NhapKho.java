package GUI.Panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.datetime.DatePicker;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import DTO.NhanVienDTO;
import DTO.SanPhamDTO;
import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import BUS.SanPhamBUS;

import javax.swing.*;

public class NhapKho extends JPanel {
    
    SanPhamBUS spBUS = new SanPhamBUS();
    NhaCungCapBUS nccBus = new NhaCungCapBUS();
    public NhapKho() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("wrap 2,fillx,insets n 35 n 35", "[fill,200]"));

        JLabel lbContactDetail = new JLabel("Phiếu nhập kho");
        lbContactDetail.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2;");
        add(lbContactDetail, "gapy 10 10,span 2");

        add(new JLabel("Nhà cung cấp"), "span 2");
        JComboBox comboPaymentType = new JComboBox();
        comboPaymentType.addItem("Bank Transfer");
        comboPaymentType.addItem("Cash");
        comboPaymentType.addItem("Mobile Payment");
        comboPaymentType.addItem("Online Payment Gateways");
        comboPaymentType.addItem("Credit/Debit Card");

        add(comboPaymentType, "gapy n 5,span 2");

        add(new JLabel("Tên sản phẩm"));
        add(new JLabel("Nhân viên nhập"));

        JTextField txtName = new JTextField();
        JTextField txtEmail = new JTextField();
        txtName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên sản phẩm");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nguyễn Văn A");
        add(txtName);
        add(txtEmail);

        JLabel lbRequestDetail = new JLabel("Chi tiết phiếu nhập");
        lbRequestDetail.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2;");
        add(lbRequestDetail, "gapy 10 10,span 2");

        add(new JLabel("Số lượng"));
        add(new JLabel("Ngày nhập"));

        JTextField txtAmount = new JTextField();
        JFormattedTextField dateEditor = new JFormattedTextField();
        DatePicker datePicker = new DatePicker();
        datePicker.setEditor(dateEditor);

        txtAmount.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0,1,2,..");

        add(txtAmount);
        add(dateEditor);

        add(new JLabel("Khu vực kho"), "gapy 5,span 2");
        JComboBox comboCompany = new JComboBox();
        comboCompany.addItem("Khu vực 1");
        comboCompany.addItem("GreenWave Enterprises");
        comboCompany.addItem("Skyline Innovations");
        comboCompany.addItem("BlueOcean Ventures");
        comboCompany.addItem("UrbanCore Technologies");

        add(comboCompany, "Span 2");


        JTextArea textArea = new JTextArea();
        textArea.setEnabled(false);
        textArea.setText("Thông tin phiếu nhập sẽ được lưu trữ trong hệ thống để phục vụ cho việc kiểm kê, theo dõi và thống kê hàng tồn kho.");
        textArea.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:0,0,0,0;" +
                "font:-1;" +
                "background:null;");
        add(textArea, "gapy 5 5,span 2");

        JLabel lbAlerts = new JLabel("Phiếu nhập sẽ được lưu vào hệ thống sau khi xác nhận");
        lbAlerts.setIcon(new FlatSVGIcon("Img/svg/clock.svg"));
        lbAlerts.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:8,8,8,8;" +
                "arc:$Component.arc;" +
                "background:fade(#1aad2c,10%);");
        add(lbAlerts, "gapy n 10,span 2");

        // action button
        
        JButton cmdPayment = new JButton("Xác nhận") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        cmdPayment.addActionListener(actionEvent -> {
            ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.OK_OPTION);
        });
        add(cmdPayment, "grow 0, al trailing");
    }
}