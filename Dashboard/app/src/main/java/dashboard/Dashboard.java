package dashboard;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.event.*;

public class Dashboard extends JFrame {

    private int time;
    private DefaultCategoryDataset dataset;

    public Dashboard() throws Exception {
        dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createLineChart("Livello acqua", "Tempo (secondi)", "Altezza", dataset);     
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);

        ActionListener taskPerformer = new ActionListener() { // Aggiorna ogni 5 secondi
            public void actionPerformed(ActionEvent e) {
                try {
                    String response = HTPPClient.getResponse();
                    dataset.addValue(Integer.parseInt(response), "Water Level", Integer.toString(time));
                    if (time > 10) {
                        dataset.removeValue("Water Level", Integer.toString(time - 10));
                    }
                    time++;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        Timer timer = new Timer(1000, taskPerformer);
        timer.start();

        setTitle("Dashboard");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
