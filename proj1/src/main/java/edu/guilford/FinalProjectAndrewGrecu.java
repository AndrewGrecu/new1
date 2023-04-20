/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package finalprojectandrewgrecu;

import java.io.File;
import java.io.FileNotFoundException;
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
     */
    public static void main(String[] args) {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FirstPanel().setVisible(true);
            }
            
        });
        ArrayList<driver> students = new ArrayList<driver>();
        try {
            Scanner fileScan = new Scanner(new File("proj1/src/main/java/edu/guilford/finalEntries.txt"));
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
    
    

