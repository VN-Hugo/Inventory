package Table;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.*;

public class CheckBoxTableHeader extends DefaultTableCellRenderer {
    private final JTable table;
    private final int checkboxColumnIndex;
    private final JCheckBox selectAll;

    public CheckBoxTableHeader(JTable table, int checkboxColumnIndex) {
        this.table = table;
        this.checkboxColumnIndex = checkboxColumnIndex;
        this.selectAll = new JCheckBox();
        selectAll.setHorizontalAlignment(SwingConstants.CENTER);

        // Đăng ký sự kiện click trên header
        JTableHeader header = table.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = table.columnAtPoint(e.getPoint());
                if (col == checkboxColumnIndex) {
                    boolean checked = !selectAll.isSelected();
                    selectAll.setSelected(checked);
                    toggleAllCheckboxes(checked);
                    header.repaint();
                }
            }
        });
    }

    private void toggleAllCheckboxes(boolean value) {
        TableModel model = table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(value, i, checkboxColumnIndex);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        return selectAll;
    }
}
