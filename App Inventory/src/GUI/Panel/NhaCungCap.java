package GUI.Panel;

import BUS.NhaCungCapBUS;
import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import GUI.Component.ButtonAction;
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

import raven.modal.option.Location;
import raven.modal.option.Option;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;
import GUI.Form.NhaCungCapForm;

public class NhaCungCap extends JPanel  implements ActionListener, ItemListener {

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
    public NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    public ArrayList<NhaCungCapDTO> listncc = nccBUS.getAll();
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);

    public NhaCungCap() {
        initComponents();
        initUI();
        loadDataTable(listncc);
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
    public void loadDataTable(ArrayList<NhaCungCapDTO> result) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0); 
    for (NhaCungCapDTO ncc : result) {
        model.addRow(new Object[]{
            ncc.getMancc(), ncc.getTenncc(), ncc.getDiachi(), ncc.getEmail(), ncc.getSdt()
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

        cbxChoose = new JComboBox<>(new String[]{"Tất cả", "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Email", "Số điện thoại"});
        btnReset = new  ButtonAction();

        cbxChoose.addItemListener(this);
        txtSearch.addKeyListener(new KeyAdapter() {
           @Override
           public void keyReleased(KeyEvent e) {
                String type = (String)cbxChoose.getSelectedItem();
                String txt = txtSearch.getText();
                listncc = nccBUS.search(txt, type);
                loadDataTable(listncc);
        }
        });
        setLayout(new java.awt.BorderLayout());

        scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        table.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Mã NCC", "Tên nhà cung cấp", "Địa chỉ", "Email", "SĐT"
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

        lbTitle.setText("DANH SÁCH NHÀ CUNG CẤP");

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

    public void importExcel() {
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;
        ArrayList<DTO.NhaCungCapDTO> listExcel = new ArrayList<DTO.NhaCungCapDTO>();
        JFileChooser jf = new JFileChooser();
        int result = jf.showOpenDialog(null);
        jf.setDialogTitle("Open file");
        Workbook workbook = null;
        int k = 0;
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = jf.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);
                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                    int check = 1;
                    XSSFRow excelRow = excelSheet.getRow(row);
                    int id = NhaCungCapDAO.getInstance().getAutoIncrement();
                    String tenNCC = excelRow.getCell(0).getStringCellValue();
                    String diachi = excelRow.getCell(1).getStringCellValue();
                    String email = excelRow.getCell(2).getStringCellValue();
                    String sdt = excelRow.getCell(3).getStringCellValue();
                    if (Validation.isEmpty(tenNCC) || Validation.isEmpty(email)
                            || !Validation.isEmail(email) || Validation.isEmpty(sdt) || !isPhoneNumber(sdt)
                            || sdt.length() != 10 || Validation.isEmpty(diachi)) {
                        check = 0;
                    }
                    if (check == 0) {
                        k += 1;
                    } else {
                        nccBUS.add(new NhaCungCapDTO(id, tenNCC, diachi, email, sdt));
                    }
                }
                if (k != 0) {
                    JOptionPane.showMessageDialog(this, "Những dữ liệu không chuẩn không được thêm vào");
                } else {
                    JOptionPane.showMessageDialog(this, "Nhập dữ liệu thành công");
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Lỗi đọc file");
            } catch (IOException ex) {
                System.out.println("Lỗi đọc file");
            }
        }

        loadDataTable(listncc);
    }

    public int getRowSelected() {
        int index = table.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp");
        }
        return index;
    }

    @Override

     public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cmdNew) {
             showModal("Thêm Nhà Cung Cấp", null);
        } else if (e.getSource() == cmdEdit) {
            int index = getRowSelected();
            if (index != -1) { 
               showModal("Sửa Nhà Cung Cấp", listncc.get(index));
            }
        } else if (e.getSource() == cmdDelete) {
            int index = getRowSelected();
            if (index != -1) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa nhà cung cấp!", "Xóa nhà cung cấp",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == 0) {
                    nccBUS.delete(listncc.get(index), index);
                    loadDataTable(listncc);
                }
            }
        }   else if (e.getSource() == btnReset) {
            txtSearch.setText("");
            listncc = nccBUS.getAll();
            loadDataTable(listncc);
        }  
}



    public static boolean isPhoneNumber(String str) {
        // Loại bỏ khoảng trắng và dấu ngoặc đơn nếu có
        str = str.replaceAll("\\s+", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\-", "");

        // Kiểm tra xem chuỗi có phải là một số điện thoại hợp lệ hay không
        if (str.matches("\\d{10}")) { // Kiểm tra số điện thoại 10 chữ số
            return true;
        } else if (str.matches("\\d{3}-\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu gạch ngang
            return true;
        } else if (str.matches("\\(\\d{3}\\)\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu ngoặc đơn
            return true;
        } else {
            return false; // Trả về false nếu chuỗi không phải là số điện thoại hợp lệ
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String type = (String)cbxChoose.getSelectedItem();
        String txt = txtSearch.getText();
        listncc = nccBUS.search(txt, type);
        loadDataTable(listncc);
    }
   private void showModal(String title, NhaCungCapDTO data) {
    NhaCungCapForm inputForm = new NhaCungCapForm();
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
                        NhaCungCapDTO result = inputForm.getData();
                        if(data == null)
                        {
                        int mancc = NhaCungCapDAO.getInstance().getAutoIncrement();  
                        this.nccBUS.add(new NhaCungCapDTO(mancc, result.getTenncc(), result.getDiachi(), result.getEmail(), result.getSdt()));
                        }
                        else
                        {
                        int mancc = result.getMancc();
                        this.nccBUS.update(new NhaCungCapDTO(mancc, result.getTenncc(), result.getDiachi(), result.getEmail(), result.getSdt()));
                        }
                        this.loadDataTable(this.listncc);
                    }
                }
            }
    ), option);
}

}

