package GUI.Panel;

import BUS.SanPhamBUS;
import DAO.SanPhamDAO;
import DAO.TonKhoDAO;
import DTO.SanPhamDTO;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import GUI.Component.ButtonAction;
import GUI.Form.SanPhamForm;
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

import raven.modal.option.Location;
import raven.modal.option.Option;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;
import GUI.Form.KiemKeForm;
import raven.toast.Notifications;
public class SanPham extends JPanel   implements ActionListener {

    public JComboBox<String> cbxChoose;
    public ButtonAction btnReset;
    private TableRowSorter<TableModel> rowSorter;
    private ButtonAction cmdExport;
    private ButtonAction cmdImport;
    private ButtonAction cmdDelete;
    private ButtonAction cmdEdit;
    private ButtonAction cmdNew;
    private JSeparator jSeparator1;
    private JLabel lbTitle;
    private JPanel panel;
    private JScrollPane scroll;
    private JTable table;
    private JTextField txtSearch;
    public SanPhamBUS spBUS = new SanPhamBUS();
    public ArrayList<SanPhamDTO> listsp = spBUS.getAll();
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    TonKhoDAO tonKhoDAO = TonKhoDAO.getInstance();
    public SanPham() {
        initComponents();
        initUI();
        loadDataTable(listsp);
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
  public void loadDataTable(ArrayList<SanPhamDTO> result) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0); 
    for (SanPhamDTO sp : result) {
        int tongSoLuongTon = tonKhoDAO.getTongSoLuongTonByMaSanPham(sp.getMasp());
        model.addRow(new Object[]{
            sp.getMasp(),
            sp.getTensp(),
            sp.getThuonghieu(),
            sp.getXuatxu(),
            tongSoLuongTon
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
        cmdEdit = new ButtonAction();
        cmdNew = new ButtonAction();
        cmdImport = new ButtonAction();
        cmdExport = new ButtonAction();
        cbxChoose = new JComboBox<>(new String[]{"Tất cả"});
        btnReset = new  ButtonAction();
//
//        cbxChoose.addItemListener(this);
        txtSearch.addKeyListener(new KeyAdapter() {
           @Override
           public void keyReleased(KeyEvent e) {
                String type = (String)cbxChoose.getSelectedItem();
                String txt = txtSearch.getText();
                listsp = spBUS.search(txt);
                loadDataTable(listsp);
        }
        });
        setLayout(new java.awt.BorderLayout());

        scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        table.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Thương hiệu", "Xuất xứ", "Số lượng tồn"
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

        lbTitle.setText("DANH SÁCH SẢN PHẨM");
        cmdImport.setText("Kiểm Kê");
        cmdExport.setText("Xuất Excel");
        cmdDelete.setText("Xoá");
        cmdEdit.setText("Sửa");
        cmdNew.setText("Thêm");
        btnReset.setText("Làm mới");


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
                        .addComponent(cmdNew, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdImport, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdExport, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(cmdNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdImport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        add(panel, java.awt.BorderLayout.CENTER);
        cmdImport.addActionListener(this);
        cmdExport.addActionListener(this);
        cmdNew.addActionListener(this);
        cmdEdit.addActionListener(this);
        cmdDelete.addActionListener(this);
        btnReset.addActionListener(this);
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
            Notifications.getInstance().show(Notifications.Type.ERROR, "Vui lòng chọn sản phẩm.");
        }
        return index;
    }
    

    @Override

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == cmdNew) {
        showModal("Thêm Sản Phẩm", null);
    } else if (e.getSource() == cmdEdit) {
        int index = getRowSelected();
        if (index != -1) {
            showModal("Sửa Sản Phẩm", listsp.get(index));
        }
    } else if (e.getSource() == cmdDelete) {
        int index = getRowSelected();
        if (index != -1) {
            int input = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn xóa sản phẩm!", "Xóa sản phẩm",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input == JOptionPane.OK_OPTION) {
                spBUS.delete(listsp.get(index), index);
                loadDataTable(listsp);
            }
        }
    } else if (e.getSource() == btnReset) {
        txtSearch.setText("");
        listsp = spBUS.getAll();
        loadDataTable(listsp);
    } else if (e.getSource() == cmdImport) {
        int index = getRowSelected();
        if (index != -1) {
            showModalKiemKe(listsp.get(index));
        }
    } else if (e.getSource() == cmdExport) {
        try {
            JTableExporter.exportJTableToExcel(table);
        } catch (IOException ex) {
            Logger.getLogger(SanPhamDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


    private void showModal(String title, SanPhamDTO data) {
    SanPhamForm inputForm = new SanPhamForm();
    if (data != null) {
        inputForm.setData(data);
    }
    Option option = ModalDialog.createOption();
    option.getLayoutOption().setSize(-1, 1f)
            .setLocation(Location.TRAILING, Location.TOP)
            .setAnimateDistance(0.7f, 0);

    ModalDialog.showModal(this, new SimpleModalBorder(
            inputForm, title, SimpleModalBorder.YES_NO_OPTION,
            (controller, action) -> {
                if (action == SimpleModalBorder.YES_OPTION) {
                    if (inputForm.Validation()) {
                        SanPhamDTO result = inputForm.getData();
                        if(data == null)
                        {
                        int masp = SanPhamDAO.getInstance().getAutoIncrement();  
                               this.spBUS.add(new SanPhamDTO(masp, result.getTensp(),result.getXuatxu(), result.getThuonghieu()));
                        }
                        else
                        {
                        int masp = result.getMasp();
                        this.spBUS.update(new SanPhamDTO(masp, result.getTensp(),result.getXuatxu(), result.getThuonghieu()));
                        }
                        this.loadDataTable(this.listsp);
                    }
                }
            }
    ), option);
}   
 private void showModalKiemKe(SanPhamDTO data) {
    Option option = ModalDialog.createOption();
    option.getLayoutOption().setSize(-1, 1f)
            .setLocation(Location.TRAILING, Location.TOP)
            .setAnimateDistance(0.7f, 0);

    KiemKeForm kiemKeForm = new KiemKeForm(data);

    ModalDialog.showModal(this, new SimpleModalBorder(
            kiemKeForm, "Kiểm kê sản phẩm", SimpleModalBorder.YES_NO_OPTION,
            (controller, action) -> {
                if (action == SimpleModalBorder.YES_OPTION) {
                    // Lấy map tồn kho thực tế
                    Map<Integer, Integer> tonKhoThucTeMap = kiemKeForm.getTonKhoThucTeTheoKhuVuc();
                    // Cập nhật vào database tồn kho thực tế
                    tonKhoThucTeMap.forEach((maKhuVuc, tonThucTe) -> {
                       tonKhoDAO.capNhatSoLuongThucTe(data.getMasp(),tonThucTe,maKhuVuc);
                    });
                  this.loadDataTable(this.listsp);
                     Notifications.getInstance().show(Notifications.Type.SUCCESS, "Ghi nhận kiểm kê thành công");
                }
            }
    ), option);
    }
}

