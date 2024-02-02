package dashboard;


import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.event.*;

public class Dashboard extends JFrame {

    int time;

    public Dashboard() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1.0, "Water Level", time + "");
        JFreeChart chart = ChartFactory.createLineChart("Livello acqua", "Tempo (secondi)", "Altezza", dataset);     
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);

        ActionListener taskPerformer = new ActionListener() { // Aggiorna ogni 5 secondi
            public void actionPerformed(ActionEvent e) {

                if(dataset.getColumnCount() >= 10) {
                    Comparable<?> category = dataset.getColumnKey(0);
                    dataset.removeValue("Water Level", category);
                }
                dataset.addValue(time*0.23, "Water Level", time + "");
                chart.fireChartChanged();
                time++;
            }
        };
        Timer timer = new Timer(1000, taskPerformer);
        timer.start();

        
        setTitle("Dashboard");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dashboard example = new Dashboard();
            example.setVisible(true);
        });
    }
}