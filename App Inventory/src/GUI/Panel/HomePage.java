package GUI.Panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelShadow;
import GUI.Component.SimpleMessage;

public class HomePage extends JPanel {

    private JLabel timeLabel;
    private JLabel dateLabel;

String[][] getSt = {
    {
        "Tính chính xác",
        "tinhchinhxac_128px.svg",
        "<html>Hệ thống quản lý kho đảm bảo<br>dữ liệu hàng hóa, số lượng tồn kho và<br>giao dịch nhập xuất được cập nhật<br>chính xác theo thời gian thực,<br>giúp tránh sai lệch và thiếu hụt.</html>"
    },
    {
        "Tính bảo mật",
        "tinhbaomat_128px.svg",
        "<html>Thông tin hàng hóa, nhân viên và<br>dữ liệu giao dịch được lưu trữ an toàn.<br>Hệ thống phân quyền rõ ràng,<br>đảm bảo bảo mật và quyền riêng tư.</html>"
    },
    {
        "Tính hiệu quả",
        "tinhhieuqua_128px.svg",
        "<html>Quy trình nhập kho, xuất kho và<br>kiểm kê được tự động hoá,<br>giúp tiết kiệm thời gian, giảm sai sót<br>và tối ưu vận hành kho hàng.</html>"
    }
};


    public HomePage() {
        initComponent();
        updateTime();
    }

private void initComponent() {
    this.setLayout(new BorderLayout());
    this.setBackground(new Color(240, 247, 250)); // nền sáng nhạt
    this.setBounds(0, 200, 300, 1200);
    this.setOpaque(true);
    // === Header ===
    
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    headerPanel.setBackground(new Color(135, 206, 250));

    JLabel welcomeLabel = new JLabel("Chào mừng tới Hệ Thống Quản Lý Kho HopeHub !");
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
    welcomeLabel.setForeground(Color.WHITE);
    welcomeLabel.setIcon(new FlatSVGIcon("Img/svg/happy.svg", 40, 40));

    JPanel timePanel = new JPanel(new GridLayout(2, 1));
    timePanel.setOpaque(false);

    timeLabel = new JLabel("", JLabel.CENTER);
    timeLabel.setFont(new Font("Arial", Font.BOLD, 22));
    timeLabel.setForeground(Color.decode("#133337"));

    dateLabel = new JLabel("", JLabel.CENTER);
    dateLabel.setFont(new Font("Arial", Font.BOLD, 18));
    dateLabel.setForeground(Color.decode("#133337"));

    timePanel.add(timeLabel);
    timePanel.add(dateLabel);

    headerPanel.add(welcomeLabel);
    headerPanel.add(timePanel);
    this.add(headerPanel, BorderLayout.NORTH);

    // === CENTER: chứa cardPanel và sloganPanel, chia dọc bằng BoxLayout ===
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setOpaque(false);

    // --- Card Panel ---
    JPanel cardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
    cardPanel.setOpaque(false);
    cardPanel.setMinimumSize(new Dimension(500, 400));
    cardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500)); // Chiều cao tối đa cho phần trên

    for (int i = 0; i < getSt.length; i++) {
        PanelShadow panel = new PanelShadow(getSt[i][1], getSt[i][0], getSt[i][2]);
        cardPanel.add(panel);
    }
    SimpleMessage simpleMessage = new SimpleMessage(new Color(135, 206, 250)); // nền nhẹ
    simpleMessage.setAlignmentX(Component.CENTER_ALIGNMENT); 
    simpleMessage.setMaximumSize(new Dimension(1000,280));
    //Thêm Card Panel
    centerPanel.add(cardPanel);
    centerPanel.add(Box.createVerticalStrut(20));
    centerPanel.add(simpleMessage);


    // Thêm vào vùng CENTER của main panel
    this.add(centerPanel, BorderLayout.CENTER);

    // === Footer ===
    JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    footerPanel.setBackground(new Color(224, 255, 255));

    JLabel footerLabel = new JLabel("HopeHub - Tiếp nối tương lai!");
    footerLabel.setFont(new Font("Arial", Font.ITALIC, 14));
    footerLabel.setForeground(Color.GRAY);

    footerPanel.add(footerLabel);
    this.add(footerPanel, BorderLayout.SOUTH);

    // Timer cập nhật thời gian
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateTime();
        }
    });
    timer.start();
}


    private void updateTime() {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        timeLabel.setText(timeFormatter.format(date));
        dateLabel.setText(dateFormatter.format(date));
    }

   

}