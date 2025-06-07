package GUI.Form;

import DAO.TonKhoDAO;
import DAO.KhuVucKhoDAO;
import DTO.SanPhamDTO;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.event.TableModelEvent;

public class KiemKeForm extends JPanel {
    private JLabel title;
    private JTable table;
    private DefaultTableModel tableModel;
    private TonKhoDAO tonKhoDAO = TonKhoDAO.getInstance();
    private KhuVucKhoDAO khoDAO = KhuVucKhoDAO.getInstance();

    public KiemKeForm(SanPhamDTO sp) {
        initGUI();
        loadDataTable(sp);
    }

    private void initGUI() {
        setLayout(new MigLayout("fillx,wrap,insets 10 30 10 30", "[fill]", "[]10[]10[grow]10[]"));

        title = new JLabel("Sản phẩm");
        add(title);

        String[] columnNames = {"Khu vực kho", "Tồn kho hệ thống", "Tồn kho thực tế", "Chênh lệch"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        table = new JTable(tableModel);
        add(new JScrollPane(table), "height 300");

        // Bắt sự kiện nhập liệu để cập nhật chênh lệch
        tableModel.addTableModelListener(e -> {
        if (e.getType() == TableModelEvent.UPDATE) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        if (column == 2) { // cột "Tồn kho thực tế"
            updateChenhLech(row);
        }
        }
        });
    }

    public void loadDataTable(SanPhamDTO sp) {
        tableModel.setRowCount(0); // xóa dữ liệu cũ

        Integer maSP = sp.getMasp();
        title.setText("Kiểm kê sản phẩm: " + sp.getTensp());

        // Lấy danh sách mã khu vực kho có tồn kho sản phẩm này
        List<Integer> listKhuVuc = khoDAO.getMaKhuVucTheoMaSanPham(maSP);

        for (Integer maKhuVuc : listKhuVuc) {
            
            int tonKhoHeThong = tonKhoDAO.getSoLuongTonByMaSPAndKhuVuc(maSP, maKhuVuc);
            String tenkhuvuc = khoDAO.getTenKhuVucKhoByMa(maKhuVuc);
            // Thêm dòng vào bảng
            Object[] rowData = {
                tenkhuvuc,
                tonKhoHeThong,
                "",   // Tồn kho thực tế, người dùng nhập
                ""    // Chênh lệch, tính sau
            };
            tableModel.addRow(rowData);
        }
    }

    private void updateChenhLech(int row) {
    try {
        int tonHeThong = Integer.parseInt(tableModel.getValueAt(row, 1).toString()); // cột 1
        String input = tableModel.getValueAt(row, 2).toString().trim(); // cột 2
        int tonThucTe = input.isEmpty() ? 0 : Integer.parseInt(input);
        int chenhLech = tonThucTe - tonHeThong;
        tableModel.setValueAt(chenhLech, row, 3); // cột 3
    } catch (Exception e) {
        // lỗi parse số thì không làm gì
    }
}
    public Map<Integer, Integer> getTonKhoThucTeTheoKhuVuc() {
    // Trả về map: key = mã khu vực kho (mã kho), value = tồn kho thực tế người dùng nhập
    Map<Integer, Integer> tonKhoThucTeMap = new HashMap<>();
    for (int i = 0; i < tableModel.getRowCount(); i++) {
        String tenKhuVuc = tableModel.getValueAt(i, 0).toString();
        int tonKhoHeThong = Integer.parseInt(tableModel.getValueAt(i, 1).toString());

        String tonThucTeStr = tableModel.getValueAt(i, 2).toString().trim();
        int tonThucTe = tonThucTeStr.isEmpty() ? 0 : Integer.parseInt(tonThucTeStr);

        // Lấy mã khu vực kho theo tên khu vực
        int maKhuVuc = khoDAO.getMaKhuVucKhoByTen(tenKhuVuc);
        if (maKhuVuc != -1) {
            tonKhoThucTeMap.put(maKhuVuc, tonThucTe);
        }
    }
    return tonKhoThucTeMap;
    }
}
