package GUI.Component;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SimpleMessage extends JComponent {

    public SimpleMessage(Color color) {
        setBackground(color);
        init();
    }

    private void init() {
        setLayout(new MigLayout("nogrid,insets 15,wrap 2,width 600,fillx"));
        add(createLabel("Thông tin nhóm thực hiện đồ án:", "+5 bold"), "gapx push");
        add(createLabel("HopeHub", "+5 bold", new Color(79, 156, 44)), "gapx n push,wrap");
        add(createLabel("Giảng viên hướng dẫn:", "+1 bold"), "gapx push");
        add(createLabel("Tạ Việt Phương", "+1 bold", new Color(79, 156, 44)), "gapx n push,wrap");

        add(createPanel(), "grow,wrap,gapy n 10");
        add(createLabel("Trường Đại học Công nghệ Thông tin - Đại học Quốc gia TP.HCM", "+5 bold"), "span, align center");
  

    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 10,fillx,wrap", "[center][grow 0][center][grow 0][center][grow 0][center]"));
        panel.setOpaque(false);
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:1,1,1,1,fade($Separator.foreground,30%);");

        panel.add(createLabel("Mã số sinh viên", "bold"));
        panel.add(createSeparator(), "span 1 5,grow");
        panel.add(createLabel("Họ tên", "bold"));
        panel.add(createSeparator(), "span 1 5,grow");
        panel.add(createLabel("Độ đóng góp", "bold"));
        panel.add(createSeparator(), "span 1 5,grow");
        panel.add(createLabel("Gmail", "bold"));
        
        
        //Thành viên số 1
        panel.add(createLabel("23520121", null));
        panel.add(createLabel("Nguyễn Gia Bảo:", null), "split 2");
        panel.add(createLabel("Nhóm trưởng", "bold", new Color(79, 156, 44)));
        panel.add(createLabel("Hoàn thành:", null), "split 2");
        panel.add(createLabel("100%", "bold", new Color(227, 72, 72)));
        panel.add(createLabel("23520121@gm.uit.edu.vn", null));
        
        panel.add(createLabel("23520588", null));
        panel.add(createLabel("Nguyễn Lan Hương:", null), "split 2");
        panel.add(createLabel("Thành viên", "bold", new Color(51, 51, 255)));
        panel.add(createLabel("Hoàn thành:", null), "split 2");
        panel.add(createLabel("100%", "bold", new Color(227, 72, 72)));
        panel.add(createLabel("23520588@gm.uit.edu.vn", null));
        
        panel.add(createLabel("23520131", null));
        panel.add(createLabel("Nguyễn Tri An:", null), "split 2");
        panel.add(createLabel("Thành viên", "bold", new Color(51, 51, 255)));
        panel.add(createLabel("Hoàn thành:", null), "split 2");
        panel.add(createLabel("100%", "bold", new Color(227, 72, 72)));
        panel.add(createLabel("23520131@gm.uit.edu.vn", null));
        
        panel.add(createLabel("23521535", null));
        panel.add(createLabel("Huỳnh Trần Anh Thư:", null), "split 2");
        panel.add(createLabel("Thành viên", "bold", new Color(51, 51, 255)));
        panel.add(createLabel("Hoàn thành:", null), "split 2");
        panel.add(createLabel("100%", "bold", new Color(227, 72, 72)));
        panel.add(createLabel("23521535@gm.uit.edu.vn", null));
        

        
        return panel;
    }

    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:fade($Separator.foreground,30%);");
        return separator;
    }

    private JLabel createLabel(String text, String font) {
        return createLabel(text, font, null);
    }

    private JLabel createLabel(String text, String font, Color color) {
        JLabel label = new JLabel(text);
        if (font != null) {
            label.putClientProperty(FlatClientProperties.STYLE, "" +
                    "font:" + font + ";");
        }
        if (color != null) {
            label.setForeground(color);
        }
        return label;
    }

    @Override
protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    Color color1 = getBackground();
    Color color2 = new Color(color1.getRed(), color1.getGreen(), color1.getBlue(), 0);

    int arc = 30; // Độ cong góc

    // Gradient nền bo góc
    GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight() * 0.7f, color2);
    g2.setPaint(gp);
    g2.setComposite(AlphaComposite.SrcOver.derive(0.3f));
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

    g2.dispose();
    super.paintComponent(g);
}
}