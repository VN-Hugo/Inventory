package GUI.OtherForm;

import raven.modal.ModalDialog;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import raven.modal.Toast;


public class FormRead extends JPanel {
    
    private JTextField txtTenSanPham;
    private JTextField txtGiaSanPham;
    private JButton btnLuu;
    private JButton btnHuy;

    public FormRead() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTenSanPham = new JLabel("Tên sản phẩm:");
        txtTenSanPham = new JTextField(20);

        JLabel lblGiaSanPham = new JLabel("Giá sản phẩm:");
        txtGiaSanPham = new JTextField(20);

        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");

        // Dòng 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblTenSanPham, gbc);

        gbc.gridx = 1;
        add(txtTenSanPham, gbc);

        // Dòng 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblGiaSanPham, gbc);

        gbc.gridx = 1;
        add(txtGiaSanPham, gbc);

        // Dòng 3 (Nút)
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(btnLuu, gbc);

        gbc.gridx = 1;
        add(btnHuy, gbc);
       
        // Sự kiện nút
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ten = txtTenSanPham.getText();
                String gia = txtGiaSanPham.getText();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã lưu: " + ten + " - " + gia);
                ModalDialog.closeAllModal();  // <-- Đóng modal đúng cách
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModalDialog.closeAllModal();  // <-- Đóng modal đúng cách
            }
        });
    }

    
}
