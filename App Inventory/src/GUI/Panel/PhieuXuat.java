package GUI.Panel;

import BUS.PhieuXuatBUS;
import DAO.PhieuXuatDAO;
import DAO.NhaCungCapDAO;
import DAO.NhanVienDAO;
import DTO.NhaCungCapDTO;
import DTO.NhanVienDTO;
import DTO.PhieuXuatDTO;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import GUI.Component.ButtonAction;
import GUI.Form.ChiTietPhieuForm;
import GUI.Form.DuyetPhieuXuatForm;
import GUI.Form.Test;
import Utils.JTableExporter;
import Utils.Validation;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;
import raven.modal.option.Location;
import raven.modal.option.Option;
import Utils.WritePDF;

public class PhieuXuat extends JPanel implements ActionListener {

    public JComboBox<String> cbxChoose;
    public ButtonAction btnReset;
    private TableRowSorter<TableModel> rowSorter;
    private ButtonAction cmdDelete,cmdDetail,cmdCheck;
    private JSeparator jSeparator1;
    private JLabel lbTitle;
    private JPanel panel;
    private JScrollPane scroll;
    private JTable table;
    private JTextField txtSearch;   
    public PhieuXuatBUS pnBUS = new PhieuXuatBUS();
    public ArrayList<PhieuXuatDTO> listpn = pnBUS.getAll();

    public PhieuXuat() {
        initComponents();
        initUI();
        loadDataTable(listpn);
    }

    private void initUI() {
        // Style bảng và scroll
        panel.putClientProperty(FlatClientProperties.STYLE, "arc:25; background:$Table.background");

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

        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:999;"
                + "trackInsets:3,3,3,3;"
                + "thumbInsets:3,3,3,3;"
                + "background:$Table.background;");

        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +5;");
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("Img/svg/search.svg"));
        txtSearch.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:15;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "margin:5,20,5,20;"
                + "background:$Panel.background");

        // Căn giữa dữ liệu trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // Sorter
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

    }
//    public void loadDataTable(ArrayList<PhieuXuatDTO> result) {
//    DefaultTableModel model = (DefaultTableModel) table.getModel();
//    model.setRowCount(0); 
//    for (PhieuXuatDTO pn : result) {
//        model.addRow(new Object[]{
//            pn.getMaphieu(),
//            NhaCungCapDAO.getInstance().selectById(String.valueOf((pn.getManhacungcap())),
//            pn.getManguoitao(),
//            pn.getThoigiantao(),
//            pn.getTongSP()
//        });
//         }
//    }
    public void loadDataTable(ArrayList<PhieuXuatDTO> result) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0); 
    
    for (PhieuXuatDTO pn : result) {
        // Lấy tên nhà cung cấp từ mã
        NhaCungCapDTO ncc = NhaCungCapDAO.getInstance().selectById(String.valueOf(pn.getMakh()));
        String tenKhachHang = (ncc != null) ? ncc.getTenncc(): "Không rõ";

        // Lấy tên nhân viên từ mã người tạo
        NhanVienDTO nv = NhanVienDAO.getInstance().selectById(String.valueOf(pn.getManguoitao()));
        String tenNguoiTao = (nv != null) ? nv.getHoten() : "Không rõ";

        model.addRow(new Object[]{
            pn.getMaphieu(),
            tenKhachHang,
            tenNguoiTao,
            pn.getThoigiantao(),
            pn.getTongSP()
        });
    }
}
    private void initComponents() {
        panel = new JPanel();
        scroll = new JScrollPane();
        table = new JTable();
        jSeparator1 = new JSeparator();
        txtSearch = new JTextField();
        lbTitle = new javax.swing.JLabel();
        cmdDelete = new ButtonAction();
        cmdDetail = new ButtonAction();
        cmdCheck = new ButtonAction();

        cbxChoose = new JComboBox<>(new String[]{"Tất cả"});
        btnReset = new  ButtonAction();

//        cbxChoose.addItemListener(this);
        setLayout(new java.awt.BorderLayout());

        scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        table.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Mã Phiếu", "Khách hàng", "Nhân viên tạo", "Thời gian", "Tổng số lượng"
            }
        ) {
            Class[] types = new Class [] {
                String.class, String.class, String.class, String.class, String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        table.getTableHeader().setReorderingAllowed(false);
        scroll.setViewportView(table);

        lbTitle.setText("DANH SÁCH PHIẾU NHẬP");

        cmdDelete.setText("Xoá");
        cmdDetail.setText("Chi Tiết");
        btnReset.setText("Làm mới");
        cmdCheck.setText("Duyệt");


        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1190, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTitle)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 450, Short.MAX_VALUE)
                        .addComponent(cmdDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))   
                .addGap(20, 20, 20))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lbTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxChoose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset)
                    .addComponent(cmdDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        add(panel, java.awt.BorderLayout.CENTER);
        cmdDetail.addActionListener(this);
        cmdDelete.addActionListener(this);
        cmdCheck.addActionListener(this);
//       btnReset.addActionListener(this);
    }
     public void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
            
         
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public int getRowSelected() {
        int index = table.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập");
        }
        return index;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    if (e.getSource() == cmdDetail) {
        int index = getRowSelected();
        ChiTietPhieuForm form = new ChiTietPhieuForm(listpn.get(index));
        showModal("Chi tiết phiếu nhập", form); 
    }
    if (e.getSource() == cmdCheck) {
        DuyetPhieuXuatForm duyetform = new DuyetPhieuXuatForm();
        showModal1("Duyệt phiếu nhập", duyetform); 
    }
    else if (e.getSource() == cmdDelete) {
            int index = getRowSelected();
            if (index != -1) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa phiếu nhập!", "Xóa nhà cung cấp",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == 0) {
                    pnBUS.delete(listpn.get(index), index);
                    loadDataTable(listpn);
                }
            }
    }
}
   
    private void showModal(String title, ChiTietPhieuForm chiTietForm) {
    Option option = ModalDialog.createOption();
    option.getLayoutOption().setSize(-1, 0.8f)
            .setLocation(Location.CENTER, Location.CENTER)
            .setAnimateDistance(0, 0);

    ModalDialog.showModal(this, new SimpleModalBorder(
            chiTietForm, title, SimpleModalBorder.DEFAULT_OPTION, // Chỉ có nút Đóng
            (controller, action) -> {
                // Không cần xử lý gì thêm, chỉ đóng modal
            }
    ), option);
    }
    private void showModal1(String title, DuyetPhieuXuatForm duyetForm) {
    Option option = ModalDialog.createOption();
    option.getLayoutOption().setSize(-1, 0.8f)
            .setLocation(Location.CENTER, Location.CENTER)
            .setAnimateDistance(0, 0);

    ModalDialog.showModal(this, new SimpleModalBorder(
            duyetForm, title, SimpleModalBorder.DEFAULT_OPTION, 
            (controller, action) -> {
            }
    ), option);
    }
}

