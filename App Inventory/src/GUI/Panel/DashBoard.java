package GUI.Panel;

import DAO.SanPhamDAO;
import DAO.TonKhoDAO;
import DTO.SanPhamDTO;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import raven.chart.ChartLegendRenderer;
import raven.chart.bar.HorizontalBarChart;
import raven.chart.bar.LabelBar;
import raven.chart.data.category.DefaultCategoryDataset;
import raven.chart.data.pie.DefaultPieDataset;
import raven.chart.line.LineChart;
import raven.chart.pie.PieChart;
import raven.chart.data.DefaultKeyedValues;
import Utils.DateCalculator;
import java.util.List;


public class DashBoard extends JPanel{
    private SanPhamDAO sanPhamDAO;
    TonKhoDAO tonKhoDAO = TonKhoDAO.getInstance();
    public DashBoard() {
        sanPhamDAO = new SanPhamDAO(); 
        init();
    }

    public void formRefresh() {
        pieChart1.startAnimation();
        pieChart2.startAnimation();
        pieChart3.startAnimation();
        barChart1.startAnimation();
        barChart2.startAnimation();
    }


    public void formInitAndOpen() {
        System.out.println("init and open");
    }


    public void formOpen() {
        System.out.println("Open");
    }

    private void init() {
        setLayout(new MigLayout("wrap,fill,gap 10", "fill"));
        createPieChart();
        createBarChart();
    }

    private void createPieChart() {
        List<SanPhamDTO> listSP = sanPhamDAO.selectAll();
        pieChart1 = new PieChart();
        JLabel header1 = new JLabel("Sản phẩm nhập");
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1");
        pieChart1.setHeader(header1);
        pieChart1.getChartColor().addColor(Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"), Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        pieChart1.setDataset(createPieData1(listSP)); 
        add(pieChart1, "split 3,height 290");
        pieChart2 = new PieChart();
        JLabel header2 = new JLabel("Sản phẩm trong kho");
        header2.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1");
        pieChart2.setHeader(header2);
        pieChart2.getChartColor().addColor(Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"), Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart2.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        pieChart2.setDataset(createPieData1(listSP));
        add(pieChart2, "height 290");

        pieChart3 = new PieChart();
        JLabel header3 = new JLabel("Sản phẩm sắp hết hàng");
        header3.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1");
        pieChart3.setHeader(header3);
        pieChart3.getChartColor().addColor(Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"), Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart3.setChartType(PieChart.ChartType.DONUT_CHART);
        pieChart3.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        pieChart3.setDataset(createPieDataSapHetHang(listSP,10));
        add(pieChart3, "height 290");
    }

    private void createBarChart() {
        // BarChart 1
        barChart1 = new HorizontalBarChart();
        JLabel header1 = new JLabel("Lượng nhập tháng");
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1;"
                + "border:0,0,5,0");
        barChart1.setHeader(header1);
        barChart1.setBarColor(Color.decode("#f97316"));
        barChart1.setDataset(createData());
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        panel1.add(barChart1);
        add(panel1, "split 2,gap 0 20");

        // BarChart 2
        barChart2 = new HorizontalBarChart();
        JLabel header2 = new JLabel("Lượng xuất tháng");
        header2.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1;"
                + "border:0,0,5,0");
        barChart2.setHeader(header2);
        barChart2.setBarColor(Color.decode("#10b981"));
        barChart2.setDataset(createData());
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        panel2.add(barChart2);
        add(panel2);
    }

    private DefaultPieDataset<String> createData() {
    DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
    Random random = new Random();
    dataset.setValue("hi", 1);
    dataset.addValue(("July (ongoing)"), random.nextInt(100));
    dataset.addValue(("June"), random.nextInt(100));
    dataset.addValue(("May"), random.nextInt(100));
    dataset.addValue(("April"), random.nextInt(100));
    dataset.addValue(("March"), random.nextInt(100));
    dataset.addValue(("February"), random.nextInt(100));
    return dataset;
    }   
    private DefaultPieDataset<String> createPieData1(List<SanPhamDTO> listSP) {
    DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
    for (SanPhamDTO sp : listSP) {
        int tongSoLuongTon = tonKhoDAO.getTongSoLuongTonByMaSanPham(sp.getMasp());
        dataset.addValue(sp.getTensp(), tongSoLuongTon); // Giá trị số lượng, tên sản phẩm là key
        }
        return dataset;
    }
    private DefaultPieDataset<String> createPieDataSapHetHang(List<SanPhamDTO> listSP, int nguongCanhBao) {
    DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
    int sapHet = 0;
    int duHang = 0;
    for (SanPhamDTO sp : listSP) {
        int ton = tonKhoDAO.getTongSoLuongTonByMaSanPham(sp.getMasp());
        if (ton < nguongCanhBao) sapHet++;
        else duHang++;
    }
    dataset.setValue("Sắp hết hàng", sapHet);
    dataset.setValue("Đủ hàng", duHang);
    return dataset;
    }
    private HorizontalBarChart barChart1;
    private HorizontalBarChart barChart2;
    private PieChart pieChart1;
    private PieChart pieChart2;
    private PieChart pieChart3;
}