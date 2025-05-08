
package GUI.OtherForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import DTO.Product;
import BUS.ProductBUS;
import java.sql.SQLException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import Table.CheckBoxTableHeader;
import Table.TableHeader;
import Table.ButtonAction;

import GUI.OtherForm.FormRead;
import java.util.HashMap;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;

public class ProductForm extends JPanel {

private JTable tableProduct;
private DefaultTableModel tableModel;
private ProductBUS productBUS;
private JLabel lblName, lblUnit, lblPrice, lblManufacturer;

public ProductForm() throws SQLException, ClassNotFoundException {
    productBUS = new ProductBUS();
    setLayout(new BorderLayout());
    setBackground(Color.decode("#F5F5F5")); // Màu nền nhẹ nhàng

    // Tiêu đề giao diện
    JLabel title = new JLabel("Danh Sách Sản Phẩm", JLabel.CENTER);
    title.setFont(new Font("Segoe UI", Font.BOLD, 24));
    title.setForeground(Color.decode("#2C3E50"));
    title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    add(title, BorderLayout.NORTH);

    // Tạo bảng sản phẩm
    createTable();
    // Nút chức năng
    createButtons();
}

private void createTable() throws SQLException, ClassNotFoundException {
    tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Không cho phép sửa trực tiếp trong bảng
        }
    };
    String[] columnNames = {"Select","Mã SP", "Tên SP", "Đơn Vị Tính", "Số Lượng", "Đơn Giá", "Hãng SX", "Ngày SX"};
    tableModel.setColumnIdentifiers(columnNames);

    tableProduct = new JTable(tableModel) {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component comp = super.prepareRenderer(renderer, row, column);

            if (isRowSelected(row)) {
                comp.setBackground(Color.decode("#6897bb"));
                comp.setFont(comp.getFont().deriveFont(Font.BOLD));
            } else {
                comp.setBackground(row % 2 == 0 ? Color.decode("#f5f5f5") : Color.decode("#f5f5dc"));
                comp.setFont(comp.getFont().deriveFont(Font.PLAIN));
                comp.setForeground(Color.BLACK);
            }
            return comp;
        }
    };
    tableProduct.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeader(tableProduct, 0));
    tableProduct.getTableHeader().setDefaultRenderer(new TableHeader(tableProduct));
    tableProduct.setRowHeight(30);
    tableProduct.setShowGrid(true);
    tableProduct.setGridColor(Color.decode("#D5DBDB"));
    tableProduct.setSelectionBackground(Color.decode("#76D7C4"));
    tableProduct.setSelectionForeground(Color.BLACK);
    tableProduct.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    tableProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JTableHeader header = tableProduct.getTableHeader();
    header.setFont(new Font("Segoe UI", Font.BOLD, 16));
    header.setBackground(Color.decode("#696969"));
    header.setForeground(Color.WHITE);

    // Center cột "Mã SP"
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    tableProduct.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

    JScrollPane scrollPane = new JScrollPane(tableProduct);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    add(scrollPane, BorderLayout.CENTER);

    tableProduct.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tableProduct.getSelectedRow();
            if (selectedRow != -1) {
                updateInfoPanel(selectedRow);
            }
        }
    });

    loadDataToTable();
}

private void createButtons() {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setBackground(Color.decode("#F5F5F5"));

    JButton addButton = new JButton("Thêm");
    JButton deleteButton = new JButton("Xóa");
    JButton updateButton = new JButton("Sửa");
    JButton reportButton = new JButton("Xem Báo Cáo");
    styleButton(reportButton, "#9B59B6");
    styleButton(addButton, "#2ECC71");
    styleButton(deleteButton, "#E74C3C");
    styleButton(updateButton, "#3498DB");
    
    
    
    addButton.addActionListener(e -> showAddProductForm());
    
   

    reportButton.addActionListener(e -> showReport());
    buttonPanel.add(addButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(updateButton);
    buttonPanel.add(reportButton);
    add(buttonPanel, BorderLayout.SOUTH);
}
private void showAddProductForm() {
// Tạo form popup thêm sản phẩm  
// Sử dụng GlassPanePopup để hiển thị form popup
JPanel panel = new JPanel();
panel.setPreferredSize(new Dimension(300, 300));
}

private void styleButton(JButton button, String colorHex) {
    button.setFont(new Font("Segoe UI", Font.BOLD, 16));
    button.setBackground(Color.decode(colorHex));
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
}

private void loadDataToTable() throws SQLException, ClassNotFoundException {
    ArrayList<Product> danhSach = productBUS.getDanhSachSanPham();
    tableModel.setRowCount(0); // Xóa trắng table trước khi load mới
    for (Product sp : danhSach) {
        Object[] row = {
            false,
            sp.getMaSP(),
            sp.getTenSP(),
            sp.getDonViTinh(),
            sp.getSoLuong(),
            sp.getDonGia(),
            sp.getHangSX(),
            sp.getNgaySX()
        };
        tableModel.addRow(row);
    }
}
private void showReport() {
try {
    
    // 1. Lấy danh sách sản phẩm từ table
    ArrayList<Product> list = productBUS.getDanhSachSanPham();
    
    
    // 2. Thiết kế JasperReport đơn giản
    JasperDesign design = new JasperDesign();
    design.setName("product_report");
    design.setPageWidth(595);
    design.setPageHeight(842);
    design.setColumnWidth(515);
    design.setLeftMargin(40);
    design.setRightMargin(40);
    design.setTopMargin(50);
    design.setBottomMargin(50);

    String[] fieldNames = {
        "maSP", "tenSP", "donViTinh", "soLuong", "donGia", "hangSX", "ngaySX"
    };
    Class<?>[] fieldTypes = {
        String.class, String.class, String.class, Integer.class,
         Double.class, String.class, String.class
     };

    for (int i = 0; i < fieldNames.length; i++) {
        JRDesignField field = new JRDesignField();
        field.setName(fieldNames[i]);
        field.setValueClass(fieldTypes[i]);
        design.addField(field);
    }
JRDesignBand title = new JRDesignBand();
title.setHeight(60); // đủ chứa 3 dòng
// Mã hóa đơn (góc trái)
JRDesignStaticText maHD = new JRDesignStaticText();
maHD.setText("Mã HĐ: HD001");
maHD.setX(0);
maHD.setY(0);
maHD.setWidth(200);
maHD.setHeight(15);
maHD.setFontSize(10f);
maHD.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
title.addElement(maHD);

// Ngày tạo (dưới mã hóa đơn)
JRDesignStaticText ngayTao = new JRDesignStaticText();
ngayTao.setText("Ngày tạo: 06/05/2025");
ngayTao.setX(0);
ngayTao.setY(18);  // cách dòng trên một chút
ngayTao.setWidth(200);
ngayTao.setHeight(15);
ngayTao.setFontSize(10f);
ngayTao.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
title.addElement(ngayTao);

// Tên công ty (ở giữa)
JRDesignStaticText companyName = new JRDesignStaticText();
companyName.setText("CÔNG TY TNHH ABC");
companyName.setX(0);
companyName.setY(0);
companyName.setWidth(515);
companyName.setHeight(20);
companyName.setFontSize(12f);
companyName.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
title.addElement(companyName);

// Tiêu đề báo cáo
JRDesignStaticText titleText = new JRDesignStaticText();
titleText.setText("BÁO CÁO DANH SÁCH SẢN PHẨM");
titleText.setX(0);
titleText.setY(35);
titleText.setWidth(515);
titleText.setHeight(25);
titleText.setFontSize(16f);
titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
title.addElement(titleText);

design.setTitle(title);





    // Nội dung dòng sản phẩm
    JRDesignBand detail = new JRDesignBand();
    detail.setHeight(25);
    int x = 0;
    int width = 70;

    for (String field : fieldNames) {
        JRDesignTextField tf = new JRDesignTextField();
        tf.setX(x);
        tf.setY(0);
        tf.setWidth(width);
        tf.setHeight(25);
        tf.setExpression(new JRDesignExpression("$F{" + field + "}"));
        detail.addElement(tf);
        x += width;
    }

    ((JRDesignSection) design.getDetailSection()).addBand(detail);

    // Biên dịch
    JasperReport report = JasperCompileManager.compileReport(design);
    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
    JasperPrint print = JasperFillManager.fillReport(report, new HashMap<>(), ds);

    // Hiển thị báo cáo
    JasperViewer.viewReport(print, false);

} catch (Exception ex) {
    ex.printStackTrace();
    JOptionPane.showMessageDialog(this, "Không thể tạo báo cáo: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
}
}

private void updateInfoPanel(int selectedRow) {
    lblName.setText("Tên SP: " + tableModel.getValueAt(selectedRow, 1));
    lblUnit.setText("Đơn Vị: " + tableModel.getValueAt(selectedRow, 2));
    lblPrice.setText("Đơn Giá: " + tableModel.getValueAt(selectedRow, 4));
    lblManufacturer.setText("Hãng SX: " + tableModel.getValueAt(selectedRow, 5));
}


}
