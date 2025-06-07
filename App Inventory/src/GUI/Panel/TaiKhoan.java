package GUI.Panel;

import BUS.TaiKhoanBUS;
import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import GUI.Component.ButtonAction;
import GUI.Form.TaiKhoanForm;

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

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;

public class TaiKhoan extends JPanel  implements ActionListener, ItemListener {

    public JComboBox<String> cbxChoose;
    public ButtonAction btnReset;
    private TableRowSorter<TableModel> rowSorter;
    private ButtonAction cmdDelete;
    private ButtonAction cmdEdit;
    private ButtonAction cmdNew;
    private JSeparator jSeparator1;
    private JLabel lbTitle;
    private JPanel panel;
    private JScrollPane scroll;
    private JTable table;
    private JTextField txtSearch;
    public TaiKhoanBUS tkBUS = new TaiKhoanBUS();
    public ArrayList<TaiKhoanDTO> listtk = tkBUS.getTaiKhoanAll();
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);

    public TaiKhoan() {
        initComponents();
        initUI();
        loadDataTable(listtk);
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
    public void loadDataTable(ArrayList<TaiKhoanDTO> list) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (TaiKhoanDTO taiKhoanDTO : list) {
            int tt = taiKhoanDTO.getTrangthai();
            String trangthaiString = "";
            switch (tt) {
                case 1 -> {
                    trangthaiString = "Hoạt động";
                }
                case 0 -> {
                    trangthaiString = "Ngưng hoạt động";
                }
            }
            NhanVienDTO nv = NhanVienDAO.getInstance().selectById(String.valueOf(taiKhoanDTO.getManv()));
            
            String hoten = nv != null ? nv.getHoten() : "Không rõ";
            model.addRow(new Object[]{
            nv.getManv(),
            taiKhoanDTO.getUsername(),
            taiKhoanDTO.getRole(),
            trangthaiString,
            hoten
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
        cbxChoose = new JComboBox<>(new String[]{"Tất cả", "Mã nhân viên", "Tên tài khoản"});
        btnReset = new  ButtonAction();

        cbxChoose.addItemListener(this);
        txtSearch.addKeyListener(new KeyAdapter() {
        @Override
         public void keyReleased(KeyEvent e) {
                String type = (String)cbxChoose.getSelectedItem();
                String txt = txtSearch.getText();
                listtk = tkBUS.search(txt, type);
                loadDataTable(listtk);
        }
        });
        setLayout(new java.awt.BorderLayout());

        scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        table.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Mã NV", "Tên tài khoản", "Vai trò", "Trạng thái","Tên nhân viên"
            }
        ) {
            Class[] types = new Class [] {
                String.class, String.class, String.class, String.class,String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false,false
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

        lbTitle.setText("DANH SÁCH TÀI KHOẢN");
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
                    .addComponent(cmdNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
      
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
       add(panel, java.awt.BorderLayout.CENTER);
       cmdNew.addActionListener(this);
       cmdEdit.addActionListener(this);
       cmdDelete.addActionListener(this);
       cbxChoose.addItemListener(this);
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
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để thực hiện thao tác");

        }
        return index;
    }
@Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cmdNew) {
           showModal("Thêm tài khoản", null);
        } else if (e.getSource() == cmdEdit) {
            int index = getRowSelected();
            if (index != -1) {
              showModal("Sửa tài khoản",  listtk.get(index));
            }
        } else if (e.getSource() == cmdDelete) {
            int index = getRowSelected();
            if (index != -1) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa nhà cung cấp!", "Xóa nhà cung cấp",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == 0) {
                    tkBUS.deleteACC(listtk.get(index), index);
                    loadDataTable(listtk);
                }
            }
        }   else if (e.getSource() == btnReset) {
            txtSearch.setText("");
            listtk = tkBUS.getTaiKhoanAll();
            loadDataTable(listtk);
        }  
    }

    public void itemStateChanged(ItemEvent e) {
        String type = (String)cbxChoose.getSelectedItem();
        String txt = txtSearch.getText();
        listtk = tkBUS.search(txt, type);
        loadDataTable(listtk);
    }
    private void showModal(String title, TaiKhoanDTO data) {
    TaiKhoanForm inputForm = new TaiKhoanForm();
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
                        TaiKhoanDTO result = inputForm.getData();
                        NhanVienDTO nv = NhanVienDAO.getInstance().selectById(String.valueOf(result.getManv()));
                        int matk = nv.getManv();
                        if (data == null) {
                        // Trường hợp thêm mới  
                        this.tkBUS.addAcc(new TaiKhoanDTO(matk, result.getUsername(), result.getMatkhau(), result.getRole(), 1));
    }                   else {
                        // Trường hợp cập nhật
                        this.tkBUS.updateAcc(new TaiKhoanDTO(matk, result.getUsername(), result.getMatkhau(), result.getRole(), result.getTrangthai()));
                        }
                        this.loadDataTable(this.listtk);
                    }
                }
            }
    ), option);
    }

}

