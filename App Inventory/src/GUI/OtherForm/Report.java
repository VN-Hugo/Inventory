package GUI.OtherForm;

import DTO.Product;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.*;

public class Report {
    public static void main(String[] args) {
        try {
            // 1. Khởi tạo thiết kế Jasper
            JasperDesign design = new JasperDesign();
            design.setName("product_report");
            design.setPageWidth(595);
            design.setPageHeight(842);
            design.setColumnWidth(515);
            design.setLeftMargin(40);
            design.setRightMargin(40);
            design.setTopMargin(50);
            design.setBottomMargin(50);

            // 2. Khai báo fields theo Product
            String[] fieldNames = {
                "maSP", "tenSP", "donViTinh", "soLuong", "donGia", "hangSX", "ngaySX"
            };
            Class<?>[] fieldTypes = {
                String.class, String.class, String.class, int.class,
                double.class, String.class, String.class
            };

            for (int i = 0; i < fieldNames.length; i++) {
                JRDesignField field = new JRDesignField();
                field.setName(fieldNames[i]);
                field.setValueClass(fieldTypes[i]);
                design.addField(field);
            }

            // 3. Tiêu đề báo cáo
            JRDesignBand title = new JRDesignBand();
            title.setHeight(40);

            JRDesignStaticText titleText = new JRDesignStaticText();
            titleText.setText("BÁO CÁO SẢN PHẨM");
            titleText.setX(0);
            titleText.setY(10);
            titleText.setWidth(515);
            titleText.setHeight(30);
          
            titleText.setFontSize(18f);
            title.addElement(titleText);

            design.setTitle(title);

            // 4. Detail band hiển thị từng dòng sản phẩm
            JRDesignBand detail = new JRDesignBand();
            detail.setHeight(25);
            int columnWidth = 70;
            int x = 0;

            for (String fieldName : fieldNames) {
                JRDesignTextField tf = new JRDesignTextField();
                tf.setX(x);
                tf.setY(0);
                tf.setWidth(columnWidth);
                tf.setHeight(25);
                tf.setExpression(new JRDesignExpression("$F{" + fieldName + "}"));
                detail.addElement(tf);
                x += columnWidth;
            }

            JRDesignSection detailSection = (JRDesignSection) design.getDetailSection();
            detailSection.addBand(detail);

            // 5. Biên dịch
            JasperReport report = JasperCompileManager.compileReport(design);

            // 6. Dữ liệu mẫu
            List<Product> list = new ArrayList<>();
            list.add(new Product("SP01", "Laptop", "Cái", 5, 1200.0, "HP", "2024-01-01"));
            list.add(new Product("SP02", "Chuột", "Cái", 10, 150.5, "Logitech", "2023-12-12"));
            list.add(new Product("SP03", "Bàn phím", "Cái", 7, 300.0, "Razer", "2023-11-11"));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

            // 7. In báo cáo
            JasperPrint print = JasperFillManager.fillReport(report, new HashMap<>(), dataSource);
            JasperExportManager.exportReportToPdfFile(print, "baocao_sanpham.pdf");

            System.out.println("✅ Báo cáo sản phẩm đã được tạo.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
