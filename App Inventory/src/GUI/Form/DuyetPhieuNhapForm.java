package GUI.Form;

import BUS.PhieuNhapBUS;
import DAO.NhaCungCapDAO;
import DAO.NhanVienDAO;
import DTO.PhieuNhapDTO;
import GUI.Component.ButtonAction;
import Utils.Formater;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import Table.CheckBoxTableHeader;
import Table.TableHeader;
import Utils.SendEmailSMTP;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DuyetPhieuNhapForm extends JPanel implements ActionListener {
    
    private JPanel main,main_btn;
    private JScrollPane scrollTable;
    private JTable table;
    private DefaultTableModel tblModel;
    private TableRowSorter<TableModel> rowSorter;
    private JCheckBox selectAllCheckbox;
    private ButtonAction btnDuyet, btnHuy;
    private PhieuNhapBUS phieunhapBus;
    private ArrayList<PhieuNhapDTO> phieu;

    public DuyetPhieuNhapForm() {
        phieunhapBus = new PhieuNhapBUS();
        init();
        loadDataTable(phieunhapBus.getAllPhieuNhapChuaDuyet()); 
    }

    private void init() {
        main = new JPanel(new MigLayout("fill, wrap 1", "[grow]", "[grow]"));
        this.setLayout(new BorderLayout());
        this.add(main, BorderLayout.CENTER);
        // Header với cột checkbox đầu tiên
        String[] header = {
                "Chọn", "Mã Phiếu", "Nhà Cung Cấp", "Nhân Viên", "Thời Gian Tạo", "Trạng Thái"
        };

        Class<?>[] columnClass = {
                Boolean.class, String.class, String.class, String.class, String.class, String.class
        };

        tblModel = new DefaultTableModel(null, header) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnClass[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho edit cột checkbox
                return column == 0;
            }
        };

        table = new JTable(tblModel);
        table.setFocusable(false);

        // Căn giữa dữ liệu các cột trừ cột checkbox
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Style FlatLaf
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE,
                "height:30; hoverBackground:null; pressedBackground:null; separatorColor:$TableHeader.background; font:bold;");
        table.putClientProperty(FlatClientProperties.STYLE,
                "rowHeight:40; showHorizontalLines:true; intercellSpacing:0,1; cellFocusColor:$TableHeader.hoverBackground; selectionBackground:$TableHeader.hoverBackground; selectionForeground:$Table.foreground;");
        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeader(table, 0));
        table.getTableHeader().setDefaultRenderer(new TableHeader(table));
        // ScrollPane chứa bảng
        scrollTable = new JScrollPane(table);
        scrollTable.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
                "trackArc:999; trackInsets:3,3,3,3; thumbInsets:3,3,3,3; background:$Table.background;");
        scrollTable.setBorder(null);
        main.add(scrollTable, "grow");

        // TableRowSorter để có thể filter/sort (nếu cần)
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
         // Panel chứa nút
        main_btn = new JPanel(new MigLayout("center", "[]20[]", "")); 
        btnDuyet = new ButtonAction();
        btnDuyet.setText("Duyệt phiếu");
        btnHuy = new ButtonAction();
        btnHuy.setText("Huỷ phiếu");
        btnDuyet.addActionListener(this);
        btnHuy.addActionListener(this);
        main_btn.add(btnDuyet, "split 2");
        main_btn.add(btnHuy, "split 2");
        main.add(main_btn, "dock south");
    }

    

    public void loadDataTable(ArrayList<PhieuNhapDTO> dsPhieu) {
        tblModel.setRowCount(0);
        for (PhieuNhapDTO phieu : dsPhieu) {
            String mancc = NhaCungCapDAO.getInstance().selectById(String.valueOf(phieu.getManhacungcap())).getTenncc();
            String hotenNV = NhanVienDAO.getInstance().selectById(String.valueOf(phieu.getManguoitao())).getHoten();
            String thoigian = Formater.FormatTime(phieu.getThoigiantao());
            String trangthai = "Chưa duyệt";
            tblModel.addRow(new Object[]{
                    false,                        // checkbox mặc định chưa chọn
                    "PN" + phieu.getMaphieu(),
                    mancc,
                    hotenNV,
                    thoigian,
                    trangthai
            });
        }
    }

    @Override
public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();

    if (source == btnDuyet) {
        ArrayList<Integer> selectedMaphieu = getSelectedMaphieu();
        if (selectedMaphieu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một phiếu để duyệt.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn duyệt " + selectedMaphieu.size() + " phiếu đã chọn?", 
                "Xác nhận duyệt", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) { 
            for (Integer maphieu : selectedMaphieu) {
                try {
                    phieunhapBus.duyetPhieuNhap(maphieu);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DuyetPhieuNhapForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            loadDataTable(phieunhapBus.getAllPhieuNhapChuaDuyet());
        }
        SendEmailSMTP.sendPhieuNhapApproved(
    "23520121@gm.uit.edu.vn",
    "Phiếu nhập số 001",
    "Nguyễn Văn A",
    "06/06/2025");
    }

    else if (source == btnHuy) {
        ArrayList<Integer> selectedMaphieu = getSelectedMaphieu();
        if (selectedMaphieu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một phiếu để huỷ.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn huỷ " + selectedMaphieu.size() + " phiếu đã chọn?", 
                "Xác nhận huỷ", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            for (Integer maphieu : selectedMaphieu) {
                phieunhapBus.huyPhieuNhap(String.valueOf(maphieu));
            }
            // Reload lại dữ liệu
            loadDataTable(phieunhapBus.getAllPhieuNhapChuaDuyet());
        }
    }
}

private ArrayList<Integer> getSelectedMaphieu() {
    ArrayList<Integer> selected = new ArrayList<>();
    for (int i = 0; i < tblModel.getRowCount(); i++) {
        Boolean isChecked = (Boolean) tblModel.getValueAt(i, 0);
        if (isChecked != null && isChecked) {
            // Cột mã phiếu là cột 1, dạng "PN123"
            String maPhieuStr = (String) tblModel.getValueAt(i, 1);
            // Lấy số phía sau "PN"
            try {
                int maphieu = Integer.parseInt(maPhieuStr.substring(2));
                selected.add(maphieu);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    }
    return selected;
    }
}
