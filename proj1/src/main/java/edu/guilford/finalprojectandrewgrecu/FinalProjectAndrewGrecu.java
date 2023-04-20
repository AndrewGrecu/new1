/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package edu.guilford.finalprojectandrewgrecu;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JTextField;


/**
 *
 * @author User
 */
public class FinalProjectAndrewGrecu {

    /**
     * @param args the command line arguments
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws URISyntaxException {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FirstPanel().setVisible(true);
                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        });
        ArrayList<driver> students = new ArrayList<driver>();
        try {
            Scanner fileScan = new Scanner(new File(FinalProjectAndrewGrecu.class.getResource("/finalEntries.txt").toURI()));
            Random randGenerator = new Random();

            while (fileScan.hasNext()) {
                

                double GPA = fileScan.nextDouble();
                double SAT = fileScan.nextDouble();
               
                boolean athlete = fileScan.nextBoolean();
                boolean legacy = fileScan.nextBoolean();
                boolean major = fileScan.nextBoolean();
                

                driver theStudent = new driver(GPA,
                        SAT, athlete, legacy,
                        major);
                students.add(theStudent);

            }
            fileScan.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.exit(0);
        }
        
        
    }
    
    
}
    
    

