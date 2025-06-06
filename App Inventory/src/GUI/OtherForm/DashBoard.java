package GUI.OtherForm;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Random;



public class DashBoard extends JPanel {

    public DashBoard() {
        setLayout(new GridLayout(3, 2, 10, 10));
        add(createPieChartPanel("Product Income"));
        add(createPieChartPanel("Product Cost"));
        add(createPieChartPanel("Product Profit"));
        add(createLineChartPanel("Income Data"));
        add(createBarChartPanel("Monthly Income"));
        add(createBarChartPanel("Monthly Expense"));
    }

    private JPanel createPieChartPanel(String title) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Bags", rand(50, 150));
        dataset.setValue("Hats", rand(50, 150));
        dataset.setValue("Glasses", rand(50, 150));
        dataset.setValue("Watches", rand(50, 150));
        dataset.setValue("Jewelry", rand(50, 150));

        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%")));
        return new ChartPanel(chart);
    }

    private JPanel createLineChartPanel(String title) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 1; i <= 30; i++) {
            dataset.addValue(rand(5, 705), "Income", "Day " + i);
            dataset.addValue(rand(5, 705), "Expense", "Day " + i);
            dataset.addValue(rand(5, 705), "Profit", "Day " + i);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                title, "Date", "Amount",
                dataset, PlotOrientation.VERTICAL,
                true, true, false);
        return new ChartPanel(chart);
    }

    private JPanel createBarChartPanel(String title) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(rand(100, 300), "Amount", "February");
        dataset.addValue(rand(100, 300), "Amount", "March");
        dataset.addValue(rand(100, 300), "Amount", "April");
        dataset.addValue(rand(100, 300), "Amount", "May");
        dataset.addValue(rand(100, 300), "Amount", "June");
        dataset.addValue(rand(100, 300), "Amount", "July");

        JFreeChart chart = ChartFactory.createBarChart(
                title, "Month", "Amount",
                dataset, PlotOrientation.VERTICAL,
                false, true, false);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.GRAY);
        return new ChartPanel(chart);
    }

    private int rand(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}
