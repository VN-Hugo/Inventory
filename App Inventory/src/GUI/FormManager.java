
package GUI;

import DTO.TaiKhoanDTO;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import GUI.App;

import javax.swing.*;
import java.awt.*;



public class FormManager {
    private App application;
    private static FormManager instance;
    private Main mainForm;
    private Login loginForm;
    private TaiKhoanDTO user; 

    public static FormManager getInstance() {
        if (instance == null) {
            instance = new FormManager();
        }
        return instance;
    }

    private FormManager() {
        loginForm = new Login();
    }

    public void initApplication(App application) {
        this.application = application;
        application.setContentPane(loginForm); // Mặc định mở màn hình Login
    }

    public void showForm(JComponent form) {
        EventQueue.invokeLater(() -> {
            FlatAnimatedLafChange.showSnapshot();
            application.setContentPane(form);
            application.revalidate();
            application.repaint();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
    }
    public void login() {
        EventQueue.invokeLater(() -> {
            FlatAnimatedLafChange.showSnapshot();
            application.setContentPane(mainForm);
            mainForm.applyComponentOrientation(application.getComponentOrientation());
            mainForm.hideMenu();
            setSelectedMenu(0, 0);
            application.revalidate();
            application.repaint();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
    }
    public void logout() {
        showForm(loginForm);
    }
    public TaiKhoanDTO getUser() {
        return user;
    }
    public void setUser(TaiKhoanDTO user) {
        this.user = user;
        mainForm = new Main();
    }
    public void setSelectedMenu(int index, int subIndex) {
        mainForm.setSelectedMenu(index, subIndex);
    }
}