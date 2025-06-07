package GUI.Form;

import DTO.KhuVucKhoDTO;
import Utils.Validation;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KhuVucKhoForm extends JPanel {

    private JTextField tenkvk;
    private JTextArea ghichu;
    private KhuVucKhoDTO kvkDTO;

    public KhuVucKhoForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));

        tenkvk = new JTextField();
        ghichu = new JTextArea();
        ghichu.setWrapStyleWord(true);
        ghichu.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(ghichu);

        // Placeholder
        tenkvk.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập tên khu vực kho");
        ghichu.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ghi chú về khu vực kho");

        // Tiêu đề
        createTitle("Thông tin khu vực kho");

        // Add vào panel
        add(new JLabel("Tên khu vực kho"), "gapy 5 0");
        add(tenkvk);
        add(new JLabel("Ghi chú"), "gapy 5 0");
        add(scroll, "height 100::150");

        // Ctrl + Enter để xác nhận
        ghichu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.isControlDown() && e.getKeyChar() == 10) {
                    ModalBorderAction modalBorderAction = ModalBorderAction.getModalBorderAction(KhuVucKhoForm.this);
                    if (modalBorderAction != null) {
                        modalBorderAction.doAction(SimpleModalBorder.YES_OPTION);
                    }
                }
            }
        });
    }

    private void createTitle(String title) {
        JLabel lb = new JLabel(title);
        lb.putClientProperty(FlatClientProperties.STYLE, "font:+2");
        add(lb, "gapy 5 0");
        add(new JSeparator(), "height 2!,gapy 0 0");
    }

    public void formOpen() {
        tenkvk.grabFocus();
    }

    public boolean Validation() {
        if (Validation.isEmpty(tenkvk.getText())) {
            JOptionPane.showMessageDialog(this, "Tên khu vực kho không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void setData(KhuVucKhoDTO data) {
        if (data != null) {
            tenkvk.setText(data.getTenkhuvuc());
            ghichu.setText(data.getGhichu());
            this.kvkDTO = data;
        }
    }

    public KhuVucKhoDTO getData() {
        if (kvkDTO == null) {
            kvkDTO = new KhuVucKhoDTO();
        }
        kvkDTO.setTenkhuvuc(tenkvk.getText());
        kvkDTO.setGhichu(ghichu.getText());
        return kvkDTO;
    }
}
