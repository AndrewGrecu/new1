/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.guilford.finalprojectandrewgrecu;

/**
 *
 * @author User
 */
public class driver {

    private double GPA;
    private double SAT;
    private boolean athlete;
    private boolean legacy;
    private boolean major;

    public boolean isMajor() {
        return major;
    }

    public void setMajor(boolean major) {
        this.major = major;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public double getSAT() {
        return SAT;
    }

    public void setSAT(double SAT) {
        this.SAT = SAT;
    }

    public boolean isAthlete() {
        return athlete;
    }

    public void setAthlete(boolean athlete) {
        this.athlete = athlete;
    }

    public boolean isLegacy() {
        return legacy;
    }

    public void setLegacy(boolean legacy) {
        this.legacy = legacy;
    }
    
    public driver(double GPA, double SAT, boolean athlete, boolean legacy, boolean major) {
        this.GPA=GPA;
        this.SAT=SAT;
        this.athlete=athlete;
        this.legacy=legacy;
        this.major=major;
        
                

    }
    
public double calculateGPAPoints() {
        double gpaPoints = 0;

        if (GPA >= 4.0) {
            gpaPoints = 5;
        } else if (GPA >= 3.0) {
            gpaPoints = 4;
        } else if (GPA >= 2.0) {
            gpaPoints = 3;
        } else {
            gpaPoints = 2;
        }

        return gpaPoints;
    }
public double calculateSATPoints() {
        double satPoints = 0;

        if (SAT >= 1400) {
            satPoints = 5;
        } else if (SAT >= 1300) {
            satPoints = 4;
        } else if (SAT >= 1100) {
            satPoints = 3;
        } else {
            satPoints = 2;
        }

        return satPoints;
    }
public double calculateInStatePoints() {
        double booleanPoints = 0;

        if (legacy) {
            booleanPoints += 2;
        }

        if (athlete) {
            booleanPoints += 2;
        }

        if (major) {
            booleanPoints -= 1;
        }

        return booleanPoints;
    }
}
