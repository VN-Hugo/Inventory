package GUI.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.text.ParseException;
import raven.datetime.DatePicker;

public class InputDate extends JPanel {

    private JLabel lbltitle;
    public DatePicker datePicker;
    private JFormattedTextField txtDate;

    public InputDate(String title) {
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        lbltitle = new JLabel(title);
        datePicker = new DatePicker();

        // Tạo mới JFormattedTextField với format phù hợp (vd: yyyy-MM-dd)
        txtDate = new JFormattedTextField(new javax.swing.text.DateFormatter(
                new java.text.SimpleDateFormat("dd-MM-yyyy")));
        txtDate.setFocusLostBehavior(JFormattedTextField.PERSIST);

        // Gán txtDate làm editor cho datePicker
        datePicker.setEditor(txtDate);
        txtDate.setText("Ngày sinh");
        // Tự động đóng datePicker khi chọn ngày
        datePicker.setCloseAfterSelected(true);

        this.add(lbltitle);
        this.add(datePicker);
    }

    public InputDate(String title, int w, int h) {
        this(title); // gọi constructor bên trên
        this.setPreferredSize(new Dimension(w, h));
    }

    public DatePicker getDatePicker() {
        return this.datePicker;
    }

    public Date getDate() throws ParseException {
        if (datePicker.isDateSelected()) {
            LocalDate localDate = datePicker.getSelectedDate();
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else {
            return null;
        }
    }

    public void setDate(Date date) {
        if (date != null) {
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            datePicker.setSelectedDate(localDate);
        } else {
            datePicker.clearSelectedDate();
            txtDate.setText("");
        }
    }

    public void setDisable() {
        datePicker.setEnabled(false);
        txtDate.setEnabled(false);
    }
}
