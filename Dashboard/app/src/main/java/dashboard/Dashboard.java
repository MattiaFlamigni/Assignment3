package dashboard;

import javax.swing.*;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;



import java.awt.*;
import java.awt.event.*;
import java.net.http.HttpClient;

public class Dashboard extends JFrame {
    
    int time;
    JLabel statusLabel;
    JLabel valveLabel;

    public Dashboard() throws Exception {
        new HTPPClient();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1.0, "Water Level", time + "");
        JFreeChart chart = ChartFactory.createLineChart("Livello acqua", "Tempo (secondi)", "Altezza", dataset);     
        ChartPanel chartPanel = new ChartPanel(chart);
        
        // Impostazione della dimensione desiderata per il ChartPanel
        chartPanel.setPreferredSize(new Dimension(800, 600)); // Imposta le dimensioni desiderate
        
        // Creazione del pannello per il layout nella parte bassa della finestra
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Aggiunta di JTextField per l'input dell'utente al pannello inferiore
        JTextField textField = new JTextField(10);
        bottomPanel.add(textField);

        // Aggiunta di JButton per inviare il dato al pannello inferiore
        JButton sendButton = new JButton("Invia");
        bottomPanel.add(sendButton);
        
        // Aggiunta di JLabel per visualizzare lo stato
        statusLabel = new JLabel("Stato: ");
        bottomPanel.add(statusLabel);

        // Aggiunta di JLabel per visualizzare il grado di apertura delle valvole
        valveLabel = new JLabel("Grado di apertura: ");
        bottomPanel.add(valveLabel);
        
        ActionListener taskPerformer = new ActionListener() { // Aggiorna ogni 5 secondi
            public void actionPerformed(ActionEvent e){

                if(dataset.getColumnCount() >= 10) {
                    Comparable<?> category = dataset.getColumnKey(0);
                    dataset.removeValue("Water Level", category);
                }
                try {
                   
                    String response = HTPPClient.getResponse();
                    dataset.addValue(Integer.parseInt(response), "Water Level", time + "");
                    
                    // Aggiorna lo stato e il grado di apertura delle valvole
                    statusLabel.setText("Stato: " + HTPPClientStato.getResponse());
                    valveLabel.setText("Grado di apertura: " + HTPPClientValvola.getResponse());
                } catch (Exception e1) {
                    statusLabel.setText("Stato: Errore");
                    e1.printStackTrace();
                }
                chart.fireChartChanged();
                time++;
            }
        };
        Timer timer = new Timer(1000, taskPerformer);
        timer.start();

        // Aggiunta di ActionListener per inviare il dato quando si preme il pulsante
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String inputPercentage = textField.getText();
                    // Invia il dato tramite HTTP
                    // HttpClient.sendData(inputPercentage);

                    MyHandler handler = new MyHandler();
                    handler.inviaDato(inputPercentage);
                    //System.out.println(inputPercentage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Impostazione del layout per JFrame utilizzando BorderLayout
        setLayout(new BorderLayout());

        // Aggiunta del ChartPanel al layout della parte centrale della finestra
        add(chartPanel, BorderLayout.CENTER);
        
        // Aggiunta del pannello inferiore al layout della parte inferiore della finestra
        add(bottomPanel, BorderLayout.SOUTH);

        setTitle("Dashboard");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
