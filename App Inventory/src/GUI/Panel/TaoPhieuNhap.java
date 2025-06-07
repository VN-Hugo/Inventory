package GUI.Panel;

import BUS.NhaCungCapBUS;
import BUS.PhieuNhapBUS;
import BUS.SanPhamBUS;
import BUS.KhuVucKhoBUS;

import DAO.KhuVucKhoDAO;
import DAO.NhanVienDAO;
import DTO.ChiTietPhieuNhapDTO;
import DTO.KhuVucKhoDTO;
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import DTO.SanPhamDTO;
import DTO.TaiKhoanDTO;
import GUI.Component.ButtonAction;

import GUI.Component.ButtonCustom;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Component.SelectForm;


import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import Utils.Formater;
import Utils.Validation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.PlainDocument;
import net.miginfocom.swing.MigLayout;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class TaoPhieuNhap extends JPanel implements  ActionListener  {

    PanelBorderRadius right, left;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter, left_top, main, content_right_bottom, content_btn;
    JTable tablePhieuNhap, tableSanPham;
    JScrollPane scrollTablePhieuNhap, scrollTableSanPham;
    DefaultTableModel tblModel, tblModelSP;
    ButtonAction btnAddSp, btnEditSP, btnDelete, btnNhapHang;
    InputForm txtMaphieu, txtNhanVien;
    JTextField txtTenSp, txtSoLuong, txtMaSp;
    JComboBox<String>  cbxKvk;
    SelectForm cbxNhaCungCap;
    JTextField txtTimKiem;
    JLabel lbltongsanpham;


    Color BackgroundColor = new Color(240, 247, 250);
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);

    SanPhamBUS spBUS = new SanPhamBUS();
    NhaCungCapBUS nccBus = new NhaCungCapBUS();
    PhieuNhapBUS phieunhapBus = new PhieuNhapBUS();
    KhuVucKhoDAO kvkDAO = new KhuVucKhoDAO();
    NhanVienDAO nvDAO = new NhanVienDAO();
    TaiKhoanDTO user;
    NhanVienDTO nvDto;

    ArrayList<DTO.SanPhamDTO> listSP = spBUS.getAll();
    ArrayList<ChiTietPhieuNhapDTO> chitietphieu;

    int maphieunhap;
    int rowPhieuSelect = -1;


    public TaoPhieuNhap(TaiKhoanDTO acc) {
        this.user =acc;
        nvDto = nvDAO.selectById(String.valueOf(user.getManv()));
        initComponent();
        maphieunhap = phieunhapBus.phieunhapDAO.getAutoIncrement();
        loadDataTalbeSanPham(listSP);
    }

    public void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 5));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 5));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(5, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(5, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Phiếu nhập
        tablePhieuNhap = new JTable();
        scrollTablePhieuNhap = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã SP", "Tên sản phẩm", "Số lượng","Khu vực kho"};
        tblModel.setColumnIdentifiers(header);
        tablePhieuNhap.setModel(tblModel);
        scrollTablePhieuNhap.setViewportView(tablePhieuNhap);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tablePhieuNhap.getColumnModel();
        for (int i = 0; i < 5; i++) {
            if (i != 2) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        tablePhieuNhap.getColumnModel().getColumn(2).setPreferredWidth(300);
        tablePhieuNhap.setDefaultEditor(Object.class, null);
        tablePhieuNhap.setFocusable(false);
        scrollTablePhieuNhap.setViewportView(tablePhieuNhap);

        tablePhieuNhap.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = tablePhieuNhap.getSelectedRow();
                if (index != -1) {
                    setFormChiTietPhieu(chitietphieu.get(index));
                    rowPhieuSelect = index;
                    actionbtn("update");
                }
            }
        });

        // Table sản phẩm
        tableSanPham = new JTable();
        scrollTableSanPham = new JScrollPane();
        tblModelSP = new DefaultTableModel();
        String[] headerSP = new String[]{"Mã SP", "Tên sản phẩm"};
        tblModelSP.setColumnIdentifiers(headerSP);
        tableSanPham.setModel(tblModelSP);
        scrollTableSanPham.setViewportView(tableSanPham);
        tableSanPham.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(300);
        tableSanPham.setDefaultEditor(Object.class, null);
        tableSanPham.setFocusable(false);
        scrollTableSanPham.setViewportView(tableSanPham);

        tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = tableSanPham.getSelectedRow();
                if (index != -1) {
                    resetForm();
                    setInfoSanPham(listSP.get(index));
                    ChiTietPhieuNhapDTO ctp = checkTonTai();
                    if (ctp == null) {
                        actionbtn("add");
                    } else {
                        actionbtn("update");
                        setFormChiTietPhieu(ctp);
                    }
                }
            }
        });

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(5, 5));
        this.add(contentCenter, BorderLayout.CENTER);

        left = new PanelBorderRadius();
        left.setLayout(new BorderLayout(0, 5));
        left.setBackground(Color.white);

        left_top = new JPanel(); // Chứa tất cả phần ở phía trái trên cùng
        left_top.setLayout(new BorderLayout());
        left_top.setBorder(new EmptyBorder(5, 5, 10, 10));
        left_top.setOpaque(false);

        JPanel content_top, content_left, content_right, content_right_top;
        content_top = new JPanel(new GridLayout(1, 2, 5, 5));
        content_top.setOpaque(false);
        content_left = new JPanel(new BorderLayout(5, 5));
        content_left.setOpaque(false);
        content_left.setPreferredSize(new Dimension(0, 300));

        txtTimKiem = new JTextField();
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Tên sản phẩm, mã sản phẩm...");
        txtTimKiem.putClientProperty("JTextField.showClearButton", true);
        txtTimKiem.putClientProperty("JTextField.leadingIcon", new FlatSVGIcon("./Img/svg/search.svg"));

        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ArrayList<SanPhamDTO> rs = spBUS.search(txtTimKiem.getText());
                loadDataTalbeSanPham(rs);
            }
        });
        
        txtTimKiem.setPreferredSize(new Dimension(100, 40));
        content_left.add(txtTimKiem, BorderLayout.NORTH);
        content_left.add(scrollTableSanPham, BorderLayout.CENTER);
// Từ khúc này
// Sử dụng layout 1 cột, mỗi dòng là 1 thành phần
JPanel formPanel = new JPanel(new MigLayout("wrap 1, fillx", "[grow]"));
formPanel.setOpaque(false);
formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


formPanel.add(new JLabel("Mã sản phẩm"));
txtMaSp = new JTextField();
txtMaSp.setEditable(false);
formPanel.add(txtMaSp, "growx");
formPanel.add(new JLabel("Tên sản phẩm"));
txtTenSp = new JTextField();
txtTenSp.setEditable(false);
formPanel.add(txtTenSp, "growx");
formPanel.add(new JLabel("Số lượng"));
txtSoLuong = new JTextField();
PlainDocument docSL = (PlainDocument) txtSoLuong.getDocument();
docSL.setDocumentFilter(new NumericDocumentFilter());
formPanel.add(txtSoLuong, "growx");
formPanel.add(new JLabel("Khu vực kho"));
cbxKvk = new JComboBox();
initComboItem(cbxKvk);
formPanel.add(cbxKvk, "growx");
content_right = new JPanel(new BorderLayout(5, 5));
content_right.setOpaque(false);
content_right.add(formPanel, BorderLayout.NORTH);
content_top.add(content_left);
content_top.add(content_right);


        content_btn = new JPanel();
        content_btn.setPreferredSize(new Dimension(0, 47));
        content_btn.setLayout(new GridLayout(1, 4, 5, 5));
        content_btn.setBorder(new EmptyBorder(8, 5, 0, 10));
        content_btn.setOpaque(false);
        btnAddSp = new ButtonAction();
        btnAddSp.setText("Thêm sản phẩm");
        btnEditSP = new ButtonAction();
        btnEditSP.setText("Sửa sản phẩm");
        btnDelete = new ButtonAction();
        btnDelete.setText("Xoá sản phẩm");
        

        btnAddSp.addActionListener(this);
        btnEditSP.addActionListener(this);
        btnDelete.addActionListener(this);

        btnEditSP.setEnabled(false);
        btnDelete.setEnabled(false);
        content_btn.add(btnAddSp);
        content_btn.add(btnEditSP);
        content_btn.add(btnDelete);

        left_top.add(content_top, BorderLayout.CENTER);

        main = new JPanel();
        main.setOpaque(false);
        main.setPreferredSize(new Dimension(0, 250));
        main.setBorder(new EmptyBorder(0, 5, 10, 10));
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.add(scrollTablePhieuNhap);
        left.add(left_top, BorderLayout.CENTER);
        left.add(main, BorderLayout.SOUTH);

        right = new PanelBorderRadius();
        right.setPreferredSize(new Dimension(320, 0));
        right.setBorder(new EmptyBorder(5, 5, 5, 5));
        right.setLayout(new BorderLayout());

        JPanel right_top, right_center, right_bottom, pn_tongtien;
        right_top = new JPanel(new GridLayout(4, 1, 0, 0));
        right_top.setPreferredSize(new Dimension(300, 360));
        right_top.setOpaque(false);
        txtMaphieu = new InputForm("Mã phiếu nhập");
        txtMaphieu.setText("PN" + maphieunhap);
        txtMaphieu.setEditable(false);
        txtNhanVien = new InputForm("Nhân viên nhập");
        txtNhanVien.setText(nvDto.getHoten());
        txtNhanVien.setEditable(false);
        cbxNhaCungCap = new SelectForm("Nhà cung cấp", nccBus.getArrTenNhaCungCap());
        right_top.add(txtMaphieu);
        right_top.add(txtNhanVien);
        right_top.add(cbxNhaCungCap);

        right_center = new JPanel();
        right_center.setPreferredSize(new Dimension(100, 100));
        right_center.setOpaque(false);

        right_bottom = new JPanel(new GridLayout(2, 1));
        right_bottom.setPreferredSize(new Dimension(300, 100));
        right_bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
        right_bottom.setOpaque(false);

        pn_tongtien = new JPanel(new FlowLayout(1, 20, 0));
        pn_tongtien.setOpaque(false);
        JLabel lblsp = new JLabel("TỔNG SỐ LƯỢNG: ");
        lblsp.setFont(new Font(FlatRobotoFont.FAMILY, 1, 18));
        lbltongsanpham = new JLabel("0 Sản Phẩm");
        lbltongsanpham.setFont(new Font(FlatRobotoFont.FAMILY, 1, 18));
        lblsp.setForeground(new Color(255, 51, 51));
        pn_tongtien.add(lblsp);
        pn_tongtien.add(lbltongsanpham);
        right_bottom.add(pn_tongtien);

        btnNhapHang = new ButtonAction();
        btnNhapHang.setText("Nhập hàng");
        btnNhapHang.addActionListener(this);
        right_bottom.add(btnNhapHang);
        left_top.add(content_btn, BorderLayout.SOUTH);

        right.add(right_top, BorderLayout.NORTH);
        right.add(right_center, BorderLayout.CENTER);
        right.add(right_bottom, BorderLayout.SOUTH);

        contentCenter.add(left, BorderLayout.CENTER);
        contentCenter.add(right, BorderLayout.EAST);
    }
public ChiTietPhieuNhapDTO checkTonTai() {
    int masp;
    try {
        masp = Integer.parseInt(txtMaSp.getText().trim());
    } catch (NumberFormatException e) {
        return null;
    }
    // Lấy tên khu vực kho đang chọn
    String tenKhu = (String) cbxKvk.getSelectedItem();
    if (tenKhu == null) return null;

    // Lấy mã khu vực kho từ DAO (tìm theo tên)
    KhuVucKhoDAO dao = KhuVucKhoDAO.getInstance();
    Integer makhu = dao.getMaKhuVucKhoByTen(tenKhu);  // Bạn cần có hàm này trong DAO

    if (makhu == null) return null;

    if (chitietphieu != null) {
        for (ChiTietPhieuNhapDTO p : chitietphieu) {
            if (p.getMaSanPham() == masp && p.getMaKho() == makhu) {
                return p;
            }
        }
    }
    return null;
}
//  public ChiTietPhieuNhapDTO checkTonTai() {
//    int masp;
//    try {
//        masp = Integer.parseInt(txtMaSp.getText().trim());
//    } catch (NumberFormatException e) {
//        return null;
//    }
//    // Lấy tên khu vực kho đang chọn
//    String tenKhu = (String) cbxKvk.getSelectedItem();
//    if (tenKhu == null) return null;
//    // Lấy mã khu vực kho từ DAO (tìm theo tên)
//    KhuVucKhoDAO dao = KhuVucKhoDAO.getInstance();
//    Integer makhu = dao.getMaKhuVucKhoByTen(tenKhu);  // Bạn cần có hàm này trong DAO
//
//    if (makhu == null) return null;
//
//    for (ChiTietPhieuNhapDTO p : chitietphieu) {
//        if (p.getMaSanPham()== masp && p.getMaKho()== makhu) {
//            return p;
//        }
//    }
//    return null;
//    }
     public void setInfoSanPham(SanPhamDTO sp) {
        this.txtMaSp.setText(Integer.toString(sp.getMasp()));
        this.txtTenSp.setText(sp.getTensp());
//        this.txtSoLuong
    }
    public void resetForm() {
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtSoLuong.setText("");
        cbxKvk.setSelectedIndex(0);
    }
    public void setFormChiTietPhieu(ChiTietPhieuNhapDTO phieu) {
    this.txtMaSp.setText(Integer.toString(phieu.getMaSanPham()));
    this.txtTenSp.setText(spBUS.getByMaSP(phieu.getMaSanPham()).getTensp());
    txtSoLuong.setText(String.valueOf(phieu.getSoLuong()));

     // Đặt lại combo khu vực kho đúng tên
    String tenKhu = KhuVucKhoDAO.getInstance().getTenKhuVucKhoByMa(phieu.getMaKho());
    if (tenKhu != null) {
        for (int i = 0; i < cbxKvk.getItemCount(); i++) {
            if (cbxKvk.getItemAt(i).equalsIgnoreCase(tenKhu)) {
                cbxKvk.setSelectedIndex(i);
                break;
            }
        }
    }
  }
public ChiTietPhieuNhapDTO getInfoChiTietPhieu() {
    int masp = Integer.parseInt(txtMaSp.getText());
    int soluong = Integer.parseInt(txtSoLuong.getText());
    
    String tenKhuVuc = (String) cbxKvk.getSelectedItem();
    Integer makhuvuckho = kvkDAO.getMaKhuVucKhoByTen(tenKhuVuc);
    
    if (makhuvuckho == null) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy mã khu vực kho tương ứng với tên: " + tenKhuVuc,
            "Lỗi", JOptionPane.ERROR_MESSAGE);
        return null;
    }
    return new ChiTietPhieuNhapDTO(maphieunhap, masp, soluong, makhuvuckho);
}

public void addCtPhieu() {
    if (chitietphieu == null) {
        chitietphieu = new ArrayList<>(); // khởi tạo nếu null
    }
    
    ChiTietPhieuNhapDTO ctphieu = getInfoChiTietPhieu();
    if (ctphieu == null) {
        return; // nếu getInfo trả về null (ví dụ do lỗi), thoát luôn
    }
    
    ChiTietPhieuNhapDTO p = phieunhapBus.findCT(chitietphieu, ctphieu.getMaSanPham());
    if (p == null) {
        chitietphieu.add(ctphieu);
        loadDataTableChiTietPhieu(chitietphieu);
        resetForm();
    } else {
        int input = JOptionPane.showConfirmDialog(this, "Sản phẩm đã tồn tại trong phiếu !\nBạn có muốn chỉnh sửa không ?", "Sản phẩm đã tồn tại !", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }
}
    private void initComboItem(JComboBox<String> combo) {
    KhuVucKhoDAO dao = KhuVucKhoDAO.getInstance();
    ArrayList<String> tenKhuList = dao.getAllTenKhuVucKho();
    
    combo.removeAllItems(); 
    for (String ten : tenKhuList) {
        combo.addItem(ten);
    }
    }


    public boolean validateNhap() {
        int phuongthuc =cbxKvk.getSelectedIndex();
        if (Validation.isEmpty(txtMaSp.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm", "Chọn sản phẩm", JOptionPane.WARNING_MESSAGE);
            return false;
        } 
        else if (Validation.isEmpty(txtSoLuong.getText()) || !Validation.isNumber(txtSoLuong.getText())) {
                JOptionPane.showMessageDialog(this, "Số lượng không được để rỗng và phải là số!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
                return false;
         }
        return true;
    }

    
     public void loadDataTalbeSanPham(ArrayList<DTO.SanPhamDTO> result) {
        tblModelSP.setRowCount(0);
        for (DTO.SanPhamDTO sp : result) {
            tblModelSP.addRow(new Object[]{sp.getMasp(), sp.getTensp()});
        }
    }
    public void loadDataTableChiTietPhieu(ArrayList<ChiTietPhieuNhapDTO> ctPhieu) {
    tblModel.setRowCount(0);
    int size = ctPhieu.size();
    for (int i = 0; i < size; i++) {
        ChiTietPhieuNhapDTO ct = ctPhieu.get(i);
        Integer maSP = ct.getMaSanPham(); // trực tiếp từ DTO
        String tenSP = spBUS.getByMaSP(maSP).getTensp(); // lấy tên từ bảng sản phẩm
        int soLuong = ct.getSoLuong();
        int khuvuckho = ct.getMaKho();
        tblModel.addRow(new Object[]{
            i + 1,
            maSP,
            tenSP,
            soLuong,
            khuvuckho
        });
        lbltongsanpham.setText(String.valueOf(phieunhapBus.getTongSoLuong(ctPhieu))+" sản phẩm");

    }
}
@Override
public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();

    if (source == btnAddSp && validateNhap()) {
         addCtPhieu();  
    } 
    else if (source == btnDelete) {
        int index = tablePhieuNhap.getSelectedRow();
        if (index >= 0) {
            chitietphieu.remove(index);
            actionbtn("add");  // Quay về trạng thái thêm mới
            loadDataTableChiTietPhieu(chitietphieu);  // Cập nhật lại bảng
            resetForm();  // Reset form nhập
        }
    } else if (source == btnEditSP) {
        if (rowPhieuSelect >= 0) {
        int masp = Integer.parseInt(txtMaSp.getText());
        int soluong = Integer.parseInt(txtSoLuong.getText());
        String tenKhuVuc = (String) cbxKvk.getSelectedItem();
        Integer makhuvuckho = kvkDAO.getMaKhuVucKhoByTen(tenKhuVuc);

        ChiTietPhieuNhapDTO ctphieu = chitietphieu.get(rowPhieuSelect);
        ctphieu.setMaSanPham(masp);
        ctphieu.setSoLuong(soluong);
        ctphieu.setMaKho(makhuvuckho);

        loadDataTableChiTietPhieu(chitietphieu);
        resetForm();

        // Nếu cần reset rowPhieuSelect sau khi sửa xong
        rowPhieuSelect = -1;
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
    }
        loadDataTableChiTietPhieu(chitietphieu);
    } else if (source == btnNhapHang) {
        try {
            eventBtnNhapHang();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TaoPhieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }

    } 
}
      public void eventBtnNhapHang() throws ClassNotFoundException {
        if (chitietphieu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào trong phiếu !", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
        } else {
            int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn tạo phiếu nhập !", "Xác nhận tạo phiếu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input == 0) {
                int mancc = nccBus.getByIndex(cbxNhaCungCap.getSelectedIndex()).getMancc();
                long now = System.currentTimeMillis();
                Timestamp currenTime = new Timestamp(now);
                PhieuNhapDTO pn = new PhieuNhapDTO(mancc, maphieunhap, nvDto.getManv(), currenTime, phieunhapBus.getTongSoLuong(chitietphieu),0);
                boolean result = phieunhapBus.add(pn, chitietphieu);
                if (result) {
                    JOptionPane.showMessageDialog(this, "Nhập hàng thành công !");
                } else {
                    JOptionPane.showMessageDialog(this, "Nhập hàng không thành công !", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }


    public void actionbtn(String type) {
        boolean val_1 = type.equals("add");
        boolean val_2 = type.equals("update");
        btnAddSp.setEnabled(val_1);
        btnEditSP.setEnabled(val_2);    
        btnDelete.setEnabled(val_2);
        content_btn.revalidate();
        content_btn.repaint();
    }

}