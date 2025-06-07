package GUI.Form;

import BUS.PhieuNhapBUS;
import DAO.KhachHangDAO;
import DAO.KhuVucKhoDAO;
import DAO.NhaCungCapDAO;
import DAO.NhanVienDAO;
import DAO.SanPhamDAO;
import DTO.ChiTietPhieuNhapDTO;
import DTO.KhuVucKhoDTO;
import DTO.PhieuNhapDTO;
import DTO.PhieuXuatDTO;
import DTO.SanPhamDTO;
import GUI.Component.ButtonAction;
import Utils.Formater;
import Utils.Validation;
import Utils.WritePDF;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
public class ChiTietPhieuForm extends JPanel implements ActionListener{
    
    private JTextField txtMaPhieu, txtThoiGian,txtNhaCungCap,txtNhanVien;
    private JPanel main,top,bottom,main_btn;
    private JScrollPane scrollTable;
    private JTable table;
    private JSeparator jSeparator1;
    private TableRowSorter<TableModel> rowSorter;
    private DefaultTableModel tblModel;
    private ButtonAction btnPdf,btnHuyBo;
    private PhieuNhapDTO phieunhap;
    private PhieuXuatDTO phieuxuat;
    private String loaiphieu;
    private PhieuNhapBUS phieunhapBus;
    private ArrayList<ChiTietPhieuNhapDTO> chitietphieu;
    public ChiTietPhieuForm (PhieuNhapDTO phieunhapDTO) {
        loaiphieu = "Nhap";
        this.phieunhap = phieunhapDTO;
        phieunhapBus = new PhieuNhapBUS();
        chitietphieu = phieunhapBus.getChiTietPhieu_Type(phieunhapDTO.getMaphieu());
        init();
        initPhieuNhap();
        loadDataTableChiTietPhieu(chitietphieu);
    }
     public ChiTietPhieuForm(PhieuXuatDTO phieuxuatDTO) {
        loaiphieu = "Xuat";
        this.phieuxuat = phieuxuatDTO;
        init();
        initPhieuNhap();
    }
     public void initPhieuNhap() {
        txtMaPhieu.setText("PN" + Integer.toString(this.phieunhap.getMaphieu()));
        txtNhaCungCap.setText(NhaCungCapDAO.getInstance().selectById(phieunhap.getManhacungcap() + "").getTenncc());
        txtNhanVien.setText(NhanVienDAO.getInstance().selectById(phieunhap.getManguoitao() + "").getHoten());
        txtThoiGian.setText(Formater.FormatTime(phieunhap.getThoigiantao()));
    }

    public void initPhieuXuat() {
        txtMaPhieu.setText("PX" + Integer.toString(this.phieuxuat.getMaphieu()));
        txtNhaCungCap.setText(KhachHangDAO.getInstance().selectById(phieuxuat.getMakh() + "").getHoten());
        txtNhanVien.setText(NhanVienDAO.getInstance().selectById(phieuxuat.getManguoitao() + "").getHoten());
        txtThoiGian.setText(Formater.FormatTime(phieuxuat.getThoigiantao()));
    }
    public void init() {
        // Panel chính dùng MigLayout
        main = new JPanel(new MigLayout("fill, wrap 1", "[grow]", "[top][grow][bottom]")); 
        this.add(main, BorderLayout.CENTER);

        // ==== Phần thông tin (Top) ====
        top = new JPanel(new MigLayout("insets 10, wrap 4", "[grow][grow][grow][grow]", "[]")); 
        txtMaPhieu = new JTextField();
        txtNhanVien = new JTextField();
        txtNhaCungCap = new JTextField();
        txtThoiGian = new JTextField();

        top.add(new JLabel("Mã phiếu"), "gapy 5 0");
        top.add(new JLabel("Nhân viên nhập"), "gapy 5 0");
        top.add(new JLabel("Nhà cung cấp"), "gapy 5 0");
        top.add(new JLabel("Thời gian tạo"), "gapy 5 0");
        txtMaPhieu.setEditable(false);
        txtNhanVien.setEditable(false);
        txtNhaCungCap.setEditable(false);
        txtThoiGian.setEditable(false);

        top.add(txtMaPhieu, "growx");
        top.add(txtNhanVien, "growx");
        top.add(txtNhaCungCap, "growx");
        top.add(txtThoiGian, "growx");

        main.add(top, "growx");

    // ==== Phần bảng (Bottom) ====
    bottom = new JPanel(new MigLayout("insets 5, fill", "[grow]", "[grow]")); 
    bottom.putClientProperty(FlatClientProperties.STYLE, "arc:25; background:$Table.background");
    table = new JTable();
    tblModel = new DefaultTableModel();
    String[] header = new String[]{"STT", "Mã SP", "Tên SP", "Số lượng","Khu vực kho"};
    tblModel.setColumnIdentifiers(header);
    table.setModel(tblModel);
    table.setFocusable(false);

// ===== Style bảng FlatLaf =====
table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
        + "height:30;"
        + "hoverBackground:null;"
        + "pressedBackground:null;"
        + "separatorColor:$TableHeader.background;"
        + "font:bold;");

table.putClientProperty(FlatClientProperties.STYLE, ""
        + "rowHeight:40;"
        + "showHorizontalLines:true;"
        + "intercellSpacing:0,1;"
        + "cellFocusColor:$TableHeader.hoverBackground;"
        + "selectionBackground:$TableHeader.hoverBackground;"
        + "selectionForeground:$Table.foreground;");

scrollTable = new JScrollPane(table);
scrollTable.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
        + "trackArc:999;"
        + "trackInsets:3,3,3,3;"
        + "thumbInsets:3,3,3,3;"
        + "background:$Table.background;");

        scrollTable.setBorder(null);
        bottom.add(scrollTable, "grow");
// ===== Căn giữa dữ liệu bảng =====
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
// ===== Sorter =====
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
// ===== Sự kiện click =====
table.addMouseListener(new MouseAdapter() {
    @Override
    public void mousePressed(MouseEvent e) {
        int index = table.getSelectedRow();
        if (index != -1 && chitietphieu != null) {
            loadDataTableChiTietPhieu(chitietphieu);  // Hiển thị lại toàn bộ danh sách
        }
    }
});

    // ==== Nút chức năng ====
    main_btn = new JPanel(new MigLayout("center", "[]20[]", "")); 
    btnPdf = new ButtonAction();
    btnPdf.setText("Xuất file PDF");
    btnHuyBo = new ButtonAction();
    btnHuyBo.setText("Huỷ");
    btnPdf.addActionListener(this);
    btnHuyBo.addActionListener(this);

    main_btn.add(btnPdf, "split 2");
    main_btn.add(btnHuyBo, "split 2");
    bottom.add(main_btn, "dock south");

    main.add(bottom, "grow");

}
public void loadDataTableChiTietPhieu(ArrayList<ChiTietPhieuNhapDTO> ctPhieu) {
    tblModel.setRowCount(0);
    int stt = 1;

    for (ChiTietPhieuNhapDTO ct : ctPhieu) {
        SanPhamDTO sp = SanPhamDAO.getInstance().selectById(String.valueOf(ct.getMaSanPham()));
        KhuVucKhoDTO kvk = KhuVucKhoDAO.getInstance().selectById(String.valueOf(ct.getMaKho()));
        
        tblModel.addRow(new Object[]{
            stt++,
            ct.getMaSanPham(),
            sp.getTensp(),       // Hiển thị tên sản phẩm
            ct.getSoLuong(),
            kvk.getTenkhuvuc()       // Hiển thị tên khu vực
        });
    }
}
@Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnPdf) {
            WritePDF w = new WritePDF();
            if (this.phieuxuat != null) {
                w.writePX(phieuxuat.getMaphieu());
            }
            if (this.phieunhap != null) {
                w.writePN(phieunhap.getMaphieu());
            }
        }
 }
}