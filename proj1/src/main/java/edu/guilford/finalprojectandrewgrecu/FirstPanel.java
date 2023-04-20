/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package edu.guilford.finalprojectandrewgrecu;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import static java.nio.file.Files.size;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 *
 * @author User
 */
public class FirstPanel extends javax.swing.JFrame {

    private ArrayList<driver> students;

    public void addStudent(ArrayList<driver> students, JTextField gpaTxt, JTextField satTxt, JTextField majorTxt, JTextField legacyTxt, JTextField athleteTxt) {
        try {
            double GPA = Double.parseDouble(gpaTxt.getText());
            double SAT = Double.parseDouble(satTxt.getText());
            boolean major = Boolean.parseBoolean(majorTxt.getText());
            boolean legacy = Boolean.parseBoolean(legacyTxt.getText());
            boolean athlete = Boolean.parseBoolean(athleteTxt.getText());

            driver newStudent = new driver(GPA, SAT, major, legacy, athlete);
            Random randGenerator = new Random();

            students.add(1, newStudent);
        } catch (NumberFormatException e) {

        }

    }

    private static double calculateAverage(ArrayList<driver> students, ToDoubleFunction<driver> valueGetter) {
        if (students.isEmpty()) {
            throw new IllegalArgumentException("Invalid data.");
        }

        double sum = 0;
        for (driver student : students) {
            sum += valueGetter.applyAsDouble(student);
        }
        return sum / students.size();

    }

    private void displayResults(double userGPA, double userSAT, double medianGPA, double medianSAT, double averageGPA, double averageSAT) {
        double medianGpaDiffPercentage = ((userGPA - medianGPA) / medianGPA) * 100;
        double medianSatDiffPercentage = ((userSAT - medianSAT) / medianSAT) * 100;

        double averageGpaDiffPercentage = ((userGPA - averageGPA) / averageGPA) * 100;
        double averageSatDiffPercentage = ((userSAT - averageSAT) / averageSAT) * 100;

        resultsLabel.setText(String.format("Your GPA: %.2f, Median GPA: %.2f, Difference: %.2f%%, Average GPA: %.2f, Difference: %.2f%%",
                userGPA, medianGPA, medianGpaDiffPercentage, averageGPA, averageGpaDiffPercentage));
        results2Label.setText(String.format("Your SAT: %d, Median SAT: %d, Difference: %.2f%%, Average SAT: %.0f, Difference: %.2f%%",
                (int) userSAT, (int) medianSAT, medianSatDiffPercentage, averageSAT, averageSatDiffPercentage));
    }

    private void compareGPA(ArrayList<driver> students) {
        // sort students by GPA in descending order
        students.sort(Comparator.comparingDouble(driver::getGPA).reversed());

        int size = students.size();
        if (size == 0) {

            System.out.println("No students found!");

            return;
        }

        // calculate the percentage difference in GPA between each student and the top student
        double topGPA = students.get(0).getGPA();
        double topSAT = students.get(0).getSAT();
        String gpaInput = gpaTxt.getText();
        String satInput = satTxt.getText();
        for (int i = 0; i < size; i++) {
            double percentageDiff = ((Double.parseDouble(gpaInput) - students.get(i).getGPA())) / topGPA * 100;
            double percentageSATDiff = ((Double.parseDouble(satInput) - students.get(i).getSAT())) / topSAT * 100;
            System.out.println("Student " + (i + 1) + ": " + ", GPA: " + students.get(i).getGPA() + ", Percentage Difference from your GPA: "
                    + String.format("%.2f", percentageDiff) + "%" + ", SAT: "
                    + students.get(i).getSAT() + ", Percentage Difference from your SAT: "
                    + String.format("%.2f", percentageSATDiff) + "%");

        }
        System.out.println(students.get(0).getGPA());

    }

    private static double calculateMedian(ArrayList<driver> students, ToDoubleFunction<driver> valueGetter) {
        if (students.isEmpty()) {
            throw new IllegalArgumentException("No students found.");
        }

        List<Double> values = students.stream().mapToDouble(valueGetter).sorted().boxed().collect(Collectors.toList());
        int middleIndex = values.size() / 2;

        if (values.size() % 2 == 0) {
            return (values.get(middleIndex - 1) + values.get(middleIndex)) / 2.0;
        } else {
            return values.get(middleIndex);
        }
    }

    /**
     * Creates new form FirstPanel
     * @throws URISyntaxException
     */
    public FirstPanel() throws URISyntaxException {
        initComponents();
        students = new ArrayList<driver>();

        try {
            Scanner fileScan = new Scanner(new File(FinalProjectAndrewGrecu.class.getResource("/finalEntries.txt").toURI()));
            while (fileScan.hasNext()) {
                double GPA = fileScan.nextDouble();
                double SAT = fileScan.nextDouble();
                boolean athlete = fileScan.nextBoolean();
                boolean legacy = fileScan.nextBoolean();
                boolean major = fileScan.nextBoolean();
                driver theStudent = new driver(GPA, SAT, athlete, legacy, major);
                students.add(theStudent);

            }
            fileScan.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);

            System.exit(0);
        }
        if (!students.isEmpty()) {
            double averageSAT = calculateAverage(students, driver::getSAT);
            double averageGPA = calculateAverage(students, driver::getGPA);

            String gpaInput = gpaTxt.getText();
            String satInput = satTxt.getText();
            // Replace these with the actual names of your JLabels
            String formattedAverageGPA = String.format("%.2f", averageGPA);
            String formattedAverageSAT = String.format("%.0f", averageSAT);

        }

        // Replace these with the actual names of your JLabels
        students = new ArrayList<>();
        try {
            students = readDataFile(FinalProjectAndrewGrecu.class.getResource("/finalEntries.txt").toURI()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<driver> readDataFile(String filePath) throws IOException, URISyntaxException {
        ArrayList<driver> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(FinalProjectAndrewGrecu.class.getResource(filePath).toURI())))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                double GPA = Double.parseDouble(tokens[0]);
                double SAT = Double.parseDouble(tokens[1]);
                boolean athlete = Boolean.parseBoolean(tokens[2]);
                boolean legacy = Boolean.parseBoolean(tokens[3]);
                boolean major = Boolean.parseBoolean(tokens[4]);
                driver theStudent = new driver(GPA, SAT, athlete, legacy, major);
                data.add(theStudent);
            }
        }
        return data;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new javax.swing.JPanel();
        homeJPanel = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        FirstPanel = new javax.swing.JPanel();
        backButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        usernameTxt = new javax.swing.JTextField();
        showBox = new javax.swing.JCheckBox();
        loginButton = new javax.swing.JButton();
        infoTxt = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        secondPanel = new javax.swing.JPanel();
        backButton2 = new javax.swing.JButton();
        gpaTxt = new javax.swing.JTextField();
        satTxt = new javax.swing.JTextField();
        majorTxt = new javax.swing.JTextField();
        legacyTxt = new javax.swing.JTextField();
        athleteTxt = new javax.swing.JTextField();
        stateTxt = new javax.swing.JTextField();
        nextButton = new javax.swing.JButton();
        gpaLabel = new javax.swing.JLabel();
        satLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ThirdPanel = new javax.swing.JPanel();
        backButton3 = new javax.swing.JButton();
        resultsLabel = new javax.swing.JLabel();
        results2Label = new javax.swing.JLabel();
        averageLabel = new javax.swing.JLabel();
        averageLabel2 = new javax.swing.JLabel();
        medianLabel = new javax.swing.JLabel();
        medianLabel2 = new javax.swing.JLabel();
        resultsLabelLowest = new javax.swing.JLabel();
        results2LabelLowest = new javax.swing.JLabel();
        pointsLabel = new javax.swing.JLabel();
        categoryLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(804, 521));
        setMinimumSize(new java.awt.Dimension(804, 521));

        background1.setLayout(new java.awt.CardLayout());

        homeJPanel.setBackground(new java.awt.Color(153, 153, 255));
        homeJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, null));

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        startButton.setText("START");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("College Admissions Advisor By Andrew Grecu ");

        javax.swing.GroupLayout homeJPanelLayout = new javax.swing.GroupLayout(homeJPanel);
        homeJPanel.setLayout(homeJPanelLayout);
        homeJPanelLayout.setHorizontalGroup(
            homeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeJPanelLayout.createSequentialGroup()
                .addGroup(homeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(homeJPanelLayout.createSequentialGroup()
                        .addComponent(backButton)
                        .addGap(279, 279, 279)
                        .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(homeJPanelLayout.createSequentialGroup()
                        .addGap(331, 331, 331)
                        .addComponent(jLabel4)))
                .addContainerGap(359, Short.MAX_VALUE))
        );
        homeJPanelLayout.setVerticalGroup(
            homeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton)
                .addGap(173, 173, 173)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
        );

        background1.add(homeJPanel, "card3");

        FirstPanel.setBackground(new java.awt.Color(153, 153, 255));
        FirstPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        backButton1.setText("BACK");
        backButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButton1ActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Username: ");
        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(255, 51, 51));
        jLabel2.setText("Password:");
        jLabel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel2.setOpaque(true);

        showBox.setText("Show Password?");
        showBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                showBoxMousePressed(evt);
            }
        });
        showBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showBoxActionPerformed(evt);
            }
        });

        loginButton.setText("Log In");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        infoTxt.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        infoTxt.setForeground(new java.awt.Color(0, 0, 0));
        infoTxt.setText("P.S. the username is def not RobW ");
        infoTxt.setBorder(new javax.swing.border.MatteBorder(null));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("and the pass is not password");
        jLabel3.setBorder(new javax.swing.border.MatteBorder(null));

        passwordField.setText("pppppp3wwdw");
        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FirstPanelLayout = new javax.swing.GroupLayout(FirstPanel);
        FirstPanel.setLayout(FirstPanelLayout);
        FirstPanelLayout.setHorizontalGroup(
            FirstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FirstPanelLayout.createSequentialGroup()
                .addGroup(FirstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FirstPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backButton1))
                    .addGroup(FirstPanelLayout.createSequentialGroup()
                        .addGap(241, 241, 241)
                        .addGroup(FirstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(29, 29, 29)
                        .addGroup(FirstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usernameTxt)
                            .addComponent(passwordField))
                        .addGap(29, 29, 29)
                        .addComponent(showBox))
                    .addGroup(FirstPanelLayout.createSequentialGroup()
                        .addGap(308, 308, 308)
                        .addComponent(loginButton))
                    .addGroup(FirstPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(infoTxt))
                    .addGroup(FirstPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(245, Short.MAX_VALUE))
        );
        FirstPanelLayout.setVerticalGroup(
            FirstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FirstPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton1)
                .addGap(122, 122, 122)
                .addGroup(FirstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FirstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(showBox)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(loginButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addComponent(infoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        background1.add(FirstPanel, "card2");

        secondPanel.setBackground(new java.awt.Color(153, 153, 255));

        backButton2.setText("BACK");
        backButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButton2ActionPerformed(evt);
            }
        });

        gpaTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        gpaTxt.setText("_________");
        gpaTxt.setMaximumSize(new java.awt.Dimension(64, 22));

        satTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        satTxt.setText("_________");

        majorTxt.setText("_________");

        legacyTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        legacyTxt.setText("_________");

        athleteTxt.setText("_________");

        stateTxt.setText("_________");

        nextButton.setText("Next!");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        gpaLabel.setText("GPA?");

        satLabel.setText("SAT?");

        jLabel5.setText("Competitive Major? (true or false)");

        jLabel6.setText("In State? (true or false)");

        jLabel7.setText("Recruited Athlete? (true or false)");

        jLabel8.setText("Legacy? (true or false)");

        javax.swing.GroupLayout secondPanelLayout = new javax.swing.GroupLayout(secondPanel);
        secondPanel.setLayout(secondPanelLayout);
        secondPanelLayout.setHorizontalGroup(
            secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(secondPanelLayout.createSequentialGroup()
                .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(secondPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, secondPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(gpaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(satLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(secondPanelLayout.createSequentialGroup()
                                .addComponent(backButton2)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, secondPanelLayout.createSequentialGroup()
                        .addContainerGap(47, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(secondPanelLayout.createSequentialGroup()
                        .addComponent(nextButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 299, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, secondPanelLayout.createSequentialGroup()
                        .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(secondPanelLayout.createSequentialGroup()
                                .addComponent(satTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                                .addComponent(jLabel7))
                            .addGroup(secondPanelLayout.createSequentialGroup()
                                .addComponent(gpaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8))
                            .addGroup(secondPanelLayout.createSequentialGroup()
                                .addComponent(majorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)))
                        .addGap(32, 32, 32)))
                .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(athleteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(legacyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(267, 267, 267))
        );
        secondPanelLayout.setVerticalGroup(
            secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(secondPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton2)
                .addGap(162, 162, 162)
                .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gpaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(legacyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gpaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(70, 70, 70)
                .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(satTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(athleteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(satLabel)
                    .addComponent(jLabel7))
                .addGap(59, 59, 59)
                .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(majorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(49, 49, 49)
                .addComponent(nextButton)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        background1.add(secondPanel, "card4");

        ThirdPanel.setBackground(new java.awt.Color(153, 153, 255));
        ThirdPanel.setPreferredSize(new java.awt.Dimension(789, 509));

        backButton3.setText("BACK");
        backButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButton3ActionPerformed(evt);
            }
        });

        resultsLabel.setText("Results");

        results2Label.setText("Results2");

        averageLabel.setText("averageLabel");

        averageLabel2.setText("averageLabel2");

        medianLabel.setText("medianLabel");

        medianLabel2.setText("medianLabel2");

        resultsLabelLowest.setText("resultsLabelLowest");

        results2LabelLowest.setText("resultsLabelLowest");

        pointsLabel.setText("Points Label");

        categoryLabel.setText("categoryLabel");

        javax.swing.GroupLayout ThirdPanelLayout = new javax.swing.GroupLayout(ThirdPanel);
        ThirdPanel.setLayout(ThirdPanelLayout);
        ThirdPanelLayout.setHorizontalGroup(
            ThirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThirdPanelLayout.createSequentialGroup()
                .addGroup(ThirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ThirdPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backButton3))
                    .addGroup(ThirdPanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(ThirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(resultsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(results2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(averageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(medianLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ThirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(medianLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(averageLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
                            .addComponent(resultsLabelLowest, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(results2LabelLowest, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ThirdPanelLayout.createSequentialGroup()
                                .addComponent(pointsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(categoryLabel)))))
                .addContainerGap(149, Short.MAX_VALUE))
        );
        ThirdPanelLayout.setVerticalGroup(
            ThirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThirdPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton3)
                .addGap(33, 33, 33)
                .addComponent(resultsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(results2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(averageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(averageLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(medianLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(medianLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultsLabelLowest, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(results2LabelLowest, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ThirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pointsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryLabel))
                .addContainerGap(104, Short.MAX_VALUE))
        );

        background1.add(ThirdPanel, "card5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_backButtonActionPerformed

    private void backButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButton1ActionPerformed
        // TODO add your handling code here:
        homeJPanel.setVisible(true);
        FirstPanel.setVisible(false);
    }//GEN-LAST:event_backButton1ActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        homeJPanel.setVisible(false);
        FirstPanel.setVisible(true);
    }//GEN-LAST:event_startButtonActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // TODO add your handling code here:
        String username = "RobW";
        String password = "password";
        if (usernameTxt.getText().equals(username) && passwordField.getText().equals(password)) {
            FirstPanel.setVisible(false);
            secondPanel.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Wrong, Try Again");
        }


    }//GEN-LAST:event_loginButtonActionPerformed

    private void showBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showBoxActionPerformed
        // TODO add your handling code here:

        if (showBox.isSelected()) {
            passwordField.setEchoChar((char) 0);
        } else
            passwordField.setEchoChar('*');
    }//GEN-LAST:event_showBoxActionPerformed

    private void backButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButton2ActionPerformed
        // TODO add your handling code here:
        FirstPanel.setVisible(true);
        secondPanel.setVisible(false);
    }//GEN-LAST:event_backButton2ActionPerformed
    private ArrayList<double[]> data;
    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        // TODO add your handling code here:
        students.sort(Comparator.comparingDouble(driver::getGPA).reversed());
        double topGPA = students.get(0).getGPA();
        double topSAT = students.get(0).getSAT();
        String athleteInput = athleteTxt.getText();
        String satInput = satTxt.getText();
        String gpaInput = gpaTxt.getText();
        String legacyInput = legacyTxt.getText();
        String majorInput = majorTxt.getText();
        String stateInput = stateTxt.getText();
        double gpa = Double.parseDouble(gpaInput);
        double sat = Double.parseDouble(satInput);
        boolean athlete = Boolean.parseBoolean(athleteInput);
        boolean legacy = Boolean.parseBoolean(legacyInput);
        boolean major = Boolean.parseBoolean(majorInput);
        boolean inState = Boolean.parseBoolean(stateInput);

        int size = students.size();
        for (int i = 0; i < size; i++) {
            double percentageDiff = ((Double.parseDouble(gpaInput) - students.get(0).getGPA())) / topGPA * 100;
            double percentageSATDiff = ((Double.parseDouble(satInput) - students.get(0).getSAT())) / topSAT * 100;

            resultsLabel.setText(String.format("Your GPA: %.2f, Top GPA: %.2f, Difference: %.2f%%", Double.parseDouble(gpaInput), topGPA, percentageDiff));
            results2Label.setText("Your SAT: " + Integer.parseInt(satInput) + ", Top SAT: " + (int) topSAT + ", Difference: " + String.format("%.2f", percentageSATDiff) + "%");
        }
        secondPanel.setVisible(false);
        ThirdPanel.setVisible(true);
        addStudent(students, gpaTxt, satTxt, majorTxt, legacyTxt, athleteTxt);
        compareGPA(students);
        if (!students.isEmpty()) {
            double averageSAT = calculateAverage(students, driver::getSAT);
            double averageGPA = calculateAverage(students, driver::getGPA);
            String formattedAverageGPA = String.format("%.2f", averageGPA);
            String formattedAverageSAT = String.format("%.0f", averageSAT);

            double medianSAT = calculateMedian(students, driver::getSAT);
            double medianGPA = calculateMedian(students, driver::getGPA);
            String formattedMedianGPA = String.format("%.2f", medianGPA);
            String formattedMedianSAT = String.format("%.0f", medianSAT);
            double userGPA = Double.parseDouble(gpaTxt.getText());
            double userSAT = Double.parseDouble(satTxt.getText());

            double medianGpaDiffPercentage = ((userGPA - medianGPA) / medianGPA) * 100;
            double medianSatDiffPercentage = ((userSAT - medianSAT) / medianSAT) * 100;

            double averageGpaDiffPercentage = ((userGPA - averageGPA) / averageGPA) * 100;
            double averageSatDiffPercentage = ((userSAT - averageSAT) / averageSAT) * 100;

            medianLabel.setText(String.format("Your SAT: %d, Median SAT: %d, Difference: %.2f%%", (int) userSAT, (int) medianSAT, medianSatDiffPercentage));
            medianLabel2.setText(String.format("Your GPA: %.2f, Median GPA: %.2f, Difference: %.2f%%", userGPA, medianGPA, medianGpaDiffPercentage));

            averageLabel.setText(String.format("Your SAT: %d, Average SAT: %.0f, Difference: %.2f%%", (int) userSAT, averageSAT, averageSatDiffPercentage));
            averageLabel2.setText(String.format("Your GPA: %.2f, Average GPA: %.2f, Difference: %.2f%%", userGPA, averageGPA, averageGpaDiffPercentage));
            double lowestGPA = students.stream().mapToDouble(driver::getGPA).min().orElse(0);
            double lowestSAT = students.stream().mapToDouble(driver::getSAT).min().orElse(0);
            double percentageDiffLowest = ((Double.parseDouble(gpaInput) - lowestGPA)) / lowestGPA * 100;
            double percentageSATDiffLowest = ((Double.parseDouble(satInput) - lowestSAT)) / lowestSAT * 100;

            resultsLabelLowest.setText("Your GPA: " + String.format("%.2f", Double.parseDouble(gpaInput)) + ", Lowest GPA: " + String.format("%.2f", lowestGPA) + ", Difference: " + String.format("%.2f", percentageDiffLowest) + "%");
            results2LabelLowest.setText("Your SAT: " + Integer.parseInt(satInput) + ", Lowest SAT: " + (int) lowestSAT + ", Difference: " + String.format("%.2f", percentageSATDiffLowest) + "%");
            driver student = new driver(Double.parseDouble(gpaInput), Double.parseDouble(satInput), athlete, legacy, major);
            double totalPoints = student.calculateGPAPoints() + student.calculateSATPoints() + student.calculateInStatePoints();

            String schoolCategory;
            if (student.getSAT() >= 1400 && student.getGPA() >= 3.5 && totalPoints >=12) {
                schoolCategory = "Ivy League material";
            } else if (student.getSAT() >= 1300 && student.getGPA() >= 3.0 && totalPoints >=10) {
                schoolCategory = "Top 50 school material";
            } else if (student.getSAT() >= 1100 && student.getGPA() >= 2.5 && totalPoints >=5) {
                schoolCategory = "state school material";
            } else {
                schoolCategory = "below State school material";
            }
            pointsLabel.setText("This is how many points you have: " + totalPoints);
            categoryLabel.setText("You are " + schoolCategory);
        }

    }//GEN-LAST:event_nextButtonActionPerformed

    private void showBoxMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showBoxMousePressed
        if (showBox.isSelected()) {
            passwordField.setEchoChar((char) 0);
        } else {
            passwordField.setEchoChar('*');
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_showBoxMousePressed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFieldActionPerformed

    private void backButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButton3ActionPerformed
        ThirdPanel.setVisible(false);
        secondPanel.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_backButton3ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FirstPanel;
    private javax.swing.JPanel ThirdPanel;
    private javax.swing.JTextField athleteTxt;
    private javax.swing.JLabel averageLabel;
    private javax.swing.JLabel averageLabel2;
    private javax.swing.JButton backButton;
    private javax.swing.JButton backButton1;
    private javax.swing.JButton backButton2;
    private javax.swing.JButton backButton3;
    private javax.swing.JPanel background1;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JLabel gpaLabel;
    private javax.swing.JTextField gpaTxt;
    private javax.swing.JPanel homeJPanel;
    private javax.swing.JLabel infoTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField legacyTxt;
    private javax.swing.JButton loginButton;
    private javax.swing.JTextField majorTxt;
    private javax.swing.JLabel medianLabel;
    private javax.swing.JLabel medianLabel2;
    private javax.swing.JButton nextButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel pointsLabel;
    private javax.swing.JLabel results2Label;
    private javax.swing.JLabel results2LabelLowest;
    private javax.swing.JLabel resultsLabel;
    private javax.swing.JLabel resultsLabelLowest;
    private javax.swing.JLabel satLabel;
    private javax.swing.JTextField satTxt;
    private javax.swing.JPanel secondPanel;
    private javax.swing.JCheckBox showBox;
    private javax.swing.JButton startButton;
    private javax.swing.JTextField stateTxt;
    private javax.swing.JTextField usernameTxt;
    // End of variables declaration//GEN-END:variables
}
