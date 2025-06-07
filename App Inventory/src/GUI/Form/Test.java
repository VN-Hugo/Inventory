
package GUI.Form;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import GUI.Panel.NhaCungCap;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import Utils.Validation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import net.miginfocom.swing.MigLayout;
import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JButton;

public class Test extends JDialog implements ActionListener {

    private NhaCungCap jpNcc;
    private HeaderTitle titlePage;
    private JPanel pnmain, pnbottom;
    private JButton btnThem, btnCapNhat, btnHuyBo;
    private JTextField tenNcc, diachi,email,sodienthoai;
    private NhaCungCapDTO nccDTO;

    public Test(NhaCungCap jpNcc, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.jpNcc = jpNcc;
        initComponents(title, type);
    }

    public Test(NhaCungCap jpNcc, JFrame owner, String title, boolean modal, String type, NhaCungCapDTO nccdto) {
        super(owner, title, modal);
        this.jpNcc = jpNcc;
        this.nccDTO = nccdto;
        initComponents(title, type);
    }

    public void initComponents(String title, String type) {
        this.setSize(new Dimension(900, 360));
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        
        JLabel lbtenNcc = new JLabel("Tên nhà cung cấp");
        JLabel lbdiachi = new JLabel("Địa chỉ");
        JLabel lbemail = new JLabel("Email");
        JLabel lbsodienthoai = new JLabel("Số điện thoại");
        tenNcc = new JTextField(20);
        diachi = new JTextField(20);
        email = new JTextField(20);
        sodienthoai = new JTextField(20);
        

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        
        
        tenNcc.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên nhà cung cấp");
        diachi.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Địa chỉ");
        email.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Email");
        sodienthoai.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Số điện thoại");

       
        JLabel lbTitle = new JLabel("Form nhà cung cấp");
        JLabel description = new JLabel("Bổ sung và cập nhật thông tin nhà cung cấp.");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE,
                "[light]foreground:lighten(@foreground,30%);" +
                   "[dark]foreground:darken(@foreground,30%)");

        panel.add(lbTitle);
        panel.add(description);
        panel.add(lbtenNcc, "gapy 10");
        panel.add(tenNcc);
        panel.add(lbdiachi, "gapy 10");
        panel.add(diachi);
        panel.add(lbemail, "gapy 10");
        panel.add(email);
        panel.add(lbsodienthoai, "gapy 10");
        panel.add(sodienthoai);
        
        btnThem= new JButton("Thêm đơn vị");
        btnCapNhat= new JButton("Lưu thay đổi");
        btnHuyBo= new JButton("Huỷ bỏ");
        

        btnThem.putClientProperty(FlatClientProperties.STYLE, "borderWidth:0; focusWidth:0; innerFocusWidth:0");
        btnCapNhat.putClientProperty(FlatClientProperties.STYLE, "borderWidth:0; focusWidth:0; innerFocusWidth:0");
        btnHuyBo.putClientProperty(FlatClientProperties.STYLE, "borderWidth:0; focusWidth:0; innerFocusWidth:0");

        //Add MouseListener btn
        btnThem.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnHuyBo.addActionListener(this);

        switch (type) {
            case "create" ->
                panel.add(btnThem, "gapy 20,split 2, center");
            case "update" -> {
                panel.add(btnCapNhat, "gapy 20,split 2, center");
                initInfo();
            }
            case "view" -> {
                initInfo();
                initView();
            }
            default ->
                throw new AssertionError();
        }
        panel.add(btnHuyBo, "gapy 20,split 2, center");
        add(panel);
        pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void initInfo() {
        tenNcc.setText(nccDTO.getTenncc());
        diachi.setText(nccDTO.getDiachi());
        email.setText(nccDTO.getEmail());
        sodienthoai.setText(nccDTO.getSdt());
    }

    public void initView() {
        tenNcc.setEditable(false);
        diachi.setEditable(false);
        email.setEditable(false);
        sodienthoai.setEditable(false);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThem && Validation()) {
            int mancc = NhaCungCapDAO.getInstance().getAutoIncrement();  
            jpNcc.nccBUS.add(new NhaCungCapDTO(mancc, tenNcc.getText(), diachi.getText(), email.getText(), sodienthoai.getText()));
            jpNcc.loadDataTable(jpNcc.listncc);
            dispose();

            } else if (e.getSource() == btnHuyBo) {
            dispose();
        } else if (e.getSource() == btnCapNhat && Validation()) {
            jpNcc.nccBUS.update(new NhaCungCapDTO(nccDTO.getMancc(), tenNcc.getText(), diachi.getText(), email.getText(), sodienthoai.getText()));
            jpNcc.loadDataTable(jpNcc.listncc);
            dispose();
        }
    }
    
}