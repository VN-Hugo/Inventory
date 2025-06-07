
package GUI;

import DTO.TaiKhoanDTO;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import raven.toast.Notifications;
import java.sql.SQLException;

import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.SimplePopupBorder;

import GUI.FormManager;
import Menu.Menu; 
import Menu.MenuAction;


import GUI.Panel.HomePage;
import GUI.Panel.KhuVucKho;
import GUI.Panel.SanPham;
import GUI.Panel.NhaCungCap;
import GUI.Panel.KhachHang;
import GUI.Panel.DashBoard;
import GUI.Panel.PhieuNhap;
import GUI.Panel.NhanVien;
import GUI.Panel.TaiKhoan;
import GUI.Panel.NhapKho;
import GUI.Panel.PhieuXuat;
import GUI.Panel.TaoPhieuNhap;
import GUI.Panel.TaoPhieuXuat;



public class Main extends JLayeredPane{
    TaiKhoanDTO user;
    public Main() {
        this.user = FormManager.getInstance().getUser(); 
        init();
    }
    private void init() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new MainFormLayout());
        menu = new Menu(user.getRole());
        panelBody = new JPanel(new BorderLayout());
        initMenuArrowIcon();
        menuButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.button.background;"
                + "arc:999;"
                + "focusWidth:0;"
                + "borderWidth:0");
        menuButton.addActionListener((ActionEvent e) -> {
            setMenuFull(!menu.isMenuFull());
        });
        initMenuEvent();
        setLayer(menuButton, JLayeredPane.POPUP_LAYER);
        add(menuButton);
        add(menu);
        HomePage hp = new HomePage();
        panelBody.add(hp,BorderLayout.CENTER);
        add(panelBody);
    }
    @Override
    // Cập nhật mũi tên phù hợp với hướng
    public void applyComponentOrientation(ComponentOrientation o) {
        super.applyComponentOrientation(o);
        initMenuArrowIcon();
    }

    private void initMenuArrowIcon() {
        if (menuButton == null) {
            menuButton = new JButton();
        }
        String icon = (getComponentOrientation().isLeftToRight()) ? "menu_left.svg" : "menu_right.svg";
        menuButton.setIcon(new FlatSVGIcon("Img/svg/" + icon, 0.7f));
    }
    
   private void initMenuEvent() {
        menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
        FormManager formManager = FormManager.getInstance();
        if (index == 0) {
            showForm(new HomePage());
        } else if (index == 1) {
            showForm(new SanPham());
        }
        else if(index == 2)
        {
            showForm(new NhaCungCap());
        }
        else if(index == 3)
        {
            showForm(new KhuVucKho());
        }
        else if(index == 4)
        {
            showForm(new KhachHang());
        }
        else if(index == 5)
        {
            showForm(new  NhanVien());
        }
        else if(index == 6)
        {
            showForm(new  TaiKhoan());
        }
        else if(index == 7)
        {
            showForm(new  PhieuXuat());
        }
        else if(index == 8)
        {
            showForm(new PhieuNhap());
        }
        else if (index == 9)
        {
            showForm(new DashBoard());
        }
        else if (index == 10)
        {
            showForm(new TaoPhieuNhap(user));
        }
        else if (index == 11)
        {
           showForm(new TaoPhieuXuat(user));
        }
        else if (index == 12) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Đăng xuất thành công!");
            formManager.logout();
        } else {
            action.cancel();
        }
    });
}

    

    private void setMenuFull(boolean full) {
        String icon;
        if (getComponentOrientation().isLeftToRight()) {
            icon = (full) ? "menu_left.svg" : "menu_right.svg";
        } else {
            icon = (full) ? "menu_right.svg" : "menu_left.svg";
        }
        menuButton.setIcon(new FlatSVGIcon("Img/svg/" + icon, 0.7f));
        menu.setMenuFull(full);
        revalidate();
    }
    
    public void hideMenu() {
        menu.hideMenuItem();
    }

    public void showForm(Component component) {
        panelBody.removeAll();
        panelBody.add(component);
        panelBody.repaint();
        panelBody.revalidate();
    }

    public void setSelectedMenu(int index, int subIndex) {
        menu.setSelectedMenu(index, subIndex);
    }

    private Menu menu;
    private JPanel panelBody;
    private JButton menuButton;
    
    private class MainFormLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(5, 5);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }
        @Override
         public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                boolean ltr = parent.getComponentOrientation().isLeftToRight();
                Insets insets = UIScale.scale(parent.getInsets());
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
                int menuX = ltr ? x : x + width - menuWidth;
                menu.setBounds(menuX, y, menuWidth, height);
                int menuButtonWidth = menuButton.getPreferredSize().width;
                int menuButtonHeight = menuButton.getPreferredSize().height;
                int menubX;
                if (ltr) {
                    menubX = (int) (x + menuWidth - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.3f)));
                } else {
                    menubX = (int) (menuX - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.7f)));
                }
                menuButton.setBounds(menubX, UIScale.scale(30), menuButtonWidth, menuButtonHeight);
                int gap = UIScale.scale(5);
                int bodyWidth = width - menuWidth - gap;
                int bodyHeight = height;
                int bodyx = ltr ? (x + menuWidth + gap) : x;
                int bodyy = y;
                panelBody.setBounds(bodyx, bodyy, bodyWidth, bodyHeight);
            }
        }
    }
}
