
package GUI;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import raven.toast.Notifications;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import javax.swing.UIManager;
import javax.swing.*;
import java.awt.*;


import GUI.MainForm.FormManager;


 

public class App extends JFrame{
    private static App instance;
    public App()
    {
        init();
    }
    private void init()
    {
        setTitle("Smart Metro Login");
        setSize(new Dimension(1600,900)); // Tỉ lệ khung hình app
        setLocationRelativeTo(null);
        FormManager.getInstance().initApplication(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Notifications.getInstance().setJFrame(this);
    }


    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    
    // Trả về instance của App (để gọi từ nơi khác)
    public static App getInstance() {
        return instance;
    }
    
    public static void main(String[] args)
    {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("Themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN,13));
        FlatMacDarkLaf.setup();
        EventQueue.invokeLater(() -> new App().setVisible(true));
    }
}
