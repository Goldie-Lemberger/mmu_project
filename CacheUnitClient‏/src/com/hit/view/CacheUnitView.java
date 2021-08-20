package com.hit.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;

public class CacheUnitView {
    private PropertyChangeSupport pcs;
    private JFrame mainFrame;
    private CacheUnitPanel mainPanel;

    //*******Variables*******//
    JPanel textPanel = new JPanel();
    JPanel namesPanel = new JPanel();

    JLabel CapacityLbl = new JLabel("");
    JLabel AlgorithmLbl = new JLabel("");
    JLabel TotalRequestsLbl = new JLabel("");
    JLabel TotalDataModelsGDULbl = new JLabel("");
    JLabel TotalSwapsLbl = new JLabel("");
    JLabel namesLbl = new JLabel("\u00A9 Tammy Furman & Goldie Lemberger");
    JLabel successLbl = new JLabel("");

    JButton loadBtn = new JButton("Load a Request");
    JButton statisticsBtn = new JButton("Show Statistics");

    //Images icons:
    ImageIcon loadIcon = new ImageIcon("images\\load.png");
    ImageIcon statisticsIcon = new ImageIcon("images\\statistics.png");
    ImageIcon checkIcon = new ImageIcon("images\\check.png");
    ImageIcon xmarkIcon = new ImageIcon("images\\xmark.png");
    ImageIcon notFoundIcon = new ImageIcon("images\\notfound.png");
    ImageIcon wallpaperIcon = new ImageIcon("images\\chip.jpg");

    public CacheUnitView() {
        JLabel background = new JLabel(wallpaperIcon);
        background.setLayout( new BorderLayout() );
        mainFrame = new JFrame("Memory Management Unit");
        mainFrame.add(background);
        mainFrame.setIconImage(new ImageIcon("images\\icon.png").getImage());
        mainPanel = new CacheUnitPanel();
        pcs = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }

    public void start() {
        mainPanel.start();
    }

    public class CacheUnitPanel extends JPanel implements ActionListener {
        private Boolean openStat = false;

        public void start() {
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));

            textPanel.setSize(350, 210);
            textPanel.setLocation(280, 20);
            textPanel.setBackground(new Color(105, 161, 154));

            successLbl.setSize(300, 55);
            successLbl.setLocation(280, 100);
            successLbl.setBackground(new Color(105, 161, 154));


            //*******project's authors*******//
            namesPanel.setSize(300, 40);
            namesPanel.setLocation(220, 300);


            //*******Load A Request Button*******//
            loadBtn.setBounds(30, 20, 220, 55);
            loadBtn.setFont(new Font("Arial", Font.PLAIN, 18));
            loadBtn.setIcon(loadIcon);
            loadBtn.addActionListener(this);
            loadBtn.setBackground(new Color(51, 71, 70));

            //*******Show Statistics Button*******//
            statisticsBtn.setBounds(260, 20, 220, 55);
            statisticsBtn.setFont(new Font("Arial", Font.PLAIN, 18));
            statisticsBtn.setIcon(statisticsIcon);
            statisticsBtn.addActionListener(this);
            statisticsBtn.setBackground(new Color(70, 104, 98));
            statisticsBtn.setLocation(30, 100);


            //*******Font and Color*******//
            CapacityLbl.setFont(new Font("Arial", Font.BOLD, 18));
            AlgorithmLbl.setFont(new Font("Arial", Font.BOLD, 18));
            TotalRequestsLbl.setFont(new Font("Arial", Font.BOLD, 18));
            TotalDataModelsGDULbl.setFont(new Font("Arial", Font.BOLD, 18));
            TotalSwapsLbl.setFont(new Font("Arial", Font.BOLD, 18));
            successLbl.setFont(new Font("Arial", Font.BOLD, 18));
            namesLbl.setFont(new Font("Arial", Font.PLAIN, 16));

            CapacityLbl.setForeground(Color.BLACK);
            AlgorithmLbl.setForeground(Color.BLACK);
            TotalRequestsLbl.setForeground(Color.BLACK);
            TotalDataModelsGDULbl.setForeground(Color.BLACK);
            TotalSwapsLbl.setForeground(Color.BLACK);
            successLbl.setForeground(Color.green);

            statisticsBtn.setForeground(Color.WHITE);
            loadBtn.setForeground(Color.WHITE);
            statisticsBtn.setForeground(Color.WHITE);
            namesLbl.setForeground(Color.red);

            //*******Add Text To Panels*******//
            textPanel.setVisible(false);
            textPanel.add(CapacityLbl);
            textPanel.add(AlgorithmLbl);
            textPanel.add(TotalRequestsLbl);
            textPanel.add(TotalDataModelsGDULbl);
            textPanel.add(TotalSwapsLbl);
            namesPanel.add(namesLbl);
            //*******Add panels to main frame*******//
            mainFrame.setLayout(null);
            mainFrame.setContentPane(new JLabel(wallpaperIcon));
            mainFrame.add(loadBtn);
            mainFrame.add(statisticsBtn);
            mainFrame.add(textPanel);
            mainFrame.add(namesPanel);

            mainFrame.setPreferredSize(new Dimension(750, 400));
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);//location of the frame Relative to screen.
            mainFrame.setVisible(true);//show GUI
        }

        @Override
        public void actionPerformed(ActionEvent evt) {

            if ("Load a Request".equals(evt.getActionCommand())) {
                mainFrame.add(successLbl).setVisible(false);
                JFileChooser openFileChooser = new JFileChooser();
                openFileChooser.setCurrentDirectory(new File("src\\main\\resources"));
                openFileChooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);

                int returnValue = openFileChooser.showOpenDialog(loadBtn);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = new File(openFileChooser.getSelectedFile().getPath());
                    Scanner scanner = null;
                    StringBuilder req = new StringBuilder();
                    try {
                        scanner = new Scanner(file);
                        while (scanner.hasNext()) {
                            req.append(scanner.next());
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    CacheUnitView.this.pcs.firePropertyChange("Load a Request", null, req.toString());

                } else{
                    successLbl.setText("File Not Selected!");
                    successLbl.setIcon(notFoundIcon);
                }
                mainFrame.add(successLbl).setVisible(true);
                mainFrame.add(textPanel).setVisible(false);
            } else if("Show Statistics".equals(evt.getActionCommand())){
                if(openStat){
                    mainFrame.add(textPanel).setVisible(false);
                    openStat = false;
                    mainFrame.add(successLbl).setVisible(false);
                }
                else{
                    openStat = true;
                    CacheUnitView.this.pcs.firePropertyChange("Show Stats", null, "{ \"headers\":{\"action\":\"SHOWSTATS\"},\"body\":[]}");
                    mainFrame.add(successLbl).setVisible(false);
                }
            }
        }
    }

    public <T> void updateUIData(T t) {
        String[] response = ((String) t).split(",");
        if (response.length == 1) {
            successLbl.setText(response[0]);
            if (response[0].equals("Succeeded"))
                successLbl.setIcon(checkIcon);
            else
                successLbl.setIcon(xmarkIcon);
        } else {
            textPanel.setVisible(true);
            successLbl.setVisible(true);
            AlgorithmLbl.setVisible(true);
            AlgorithmLbl.setText("Algorithm: " + response[0]);
            CapacityLbl.setText("Capacity: " + response[1]);
            TotalSwapsLbl.setText("Total Number Of DataModel Swaps: " + response[2]);
            TotalRequestsLbl.setText("Total Number Of Requests: " + response[3]);
            TotalDataModelsGDULbl.setText("Total Number Of DataModels: " + response[4]);
        }
    }

}


