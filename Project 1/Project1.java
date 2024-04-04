/*
 * PROJECT I: Project1.java
 *
 * This file is the last file you should implement, as it depends on both
 * Point and Circle. Thus your tasks are to:
 *
 * 1) Make sure you have carefully read the project formulation.
 * 2) Write the class Point.
 * 3) Write the class Circle
 * 4) Write this class, Project1. The results() method will perform the tasks
 *    laid out in the project formulation.
 */
 
 /*
  * NAME: Niall Kelly
  * UNIVERSITY ID: 2123618
  * DEPARTMENT: Mathematics
  */

import java.util.*;
import java.io.*;

public class Project1 {
    // -----------------------------------------------------------------------
    // Do not modify the names or types of the instance variables below! This 
    // is where you will store the results generated in the Results() function.
    // -----------------------------------------------------------------------
    public int      circleCounter; // Number of non-singular circles in the file.
    public double[] aabb;          // The bounding rectangle for the first and 
                                   // last circles
    public double   Smax;          // Area of the largest circle (by area).
    public double   Smin;          // Area of the smallest circle (by area).
    public double   areaAverage;   // Average area of the circles.
    public double   areaSD;        // Standard deviation of area of the circles.
    public double   areaMedian;    // Median of the area.
    public int      stamp = 220209;
    // -----------------------------------------------------------------------
    // You should implement - but *not* change the types, names or parameters of
    // the variables and functions below.
    // -----------------------------------------------------------------------

    /**
     * Default constructor for Project1. You should leave it empty.
     */
    public Project1() {
    }

    /**
     * Results function. It should open the file called fileName (using
     * Scanner), and from it generate the statistics outlined in the project
     * formulation. These are then placed in the instance variables above.
     *
     * @param fileName  The name of the file containing the circle data.
     */
    public void results(String fileName){
        
        double x, y, rad;
        circleCounter = 0;
        aabb = new double [4];

        // Making arraylist of circles
        ArrayList <Circle> arrayList = new ArrayList<>();

        // Reading text file
        try (
            Scanner scanner = new Scanner(new BufferedReader(new FileReader("student.data")))
            ) {

            while(scanner.hasNext()) {
      
                // Read the three values on each line of the file
                x = scanner.nextDouble();
                y = scanner.nextDouble();
                rad = scanner.nextDouble();

                // Finding number of non-singular circles and adding them to my arraylist
                if (rad > Point.GEOMTOL) {
                    Circle C = new Circle(x, y, rad);
                    circleCounter++;
                    arrayList.add(C);
                }
            }

            // Creating array of non-singular circles
            Circle [] my_array = new Circle[arrayList.size()];
            for (int i=0; i<arrayList.size(); i++){
                my_array[i] = arrayList.get(i);
            }

            // Calculating aabb array (Finding bounding rectangle of 10th and 20th circles)
            Circle [] C10C20 = {my_array[9], my_array[19]};
            aabb = calculateAABB(C10C20);

            // Finding the maximum circle area
            Smax = Double.MIN_VALUE;
            for (int i=0; i < my_array.length; i++) {
                if (my_array[i].area() > Smax) {
                    Smax = my_array[i].area();
                }
            }

            // Finding the minimum circle area
            Smin = Double.MAX_VALUE;
            for (int i=0; i < my_array.length; i++) {
                if (my_array[i].area() < Smin) {
                    Smin = my_array[i].area();
                }
            }

            // Finding the average area
            areaAverage = averageCircleArea(my_array);

            // Finding the standard deviation of the area of the circles
            areaSD = areaStandardDeviation(my_array);

            // Finding the median area
            //putting the areas of the set of circles into an array
            double [] areaArray = new double[my_array.length];
            for (int i=0; i<my_array.length; i++) {
                areaArray[i] = my_array[i].area();
            }
            //sorting the array
            for(int i=1; i<areaArray.length; i++) {
                double current = areaArray[i];
                int j = i-1;
                while(j>=0 && current <= areaArray[j]) {
                    areaArray[j+1] = areaArray[j];
                    j = j-1;
                }
                areaArray[j+1] = current;
            }
            //finding the median of the area array
            if (areaArray.length%2 != 0) {
                int index = (areaArray.length - 1)/2;
                areaMedian = areaArray[index];
            }
            else {
                int index_1 = (areaArray.length)/2;
                int index_2 = (areaArray.length - 2)/2;
                areaMedian = (areaArray[index_1] + areaArray[index_2])/2;
            }


        } catch(Exception e) {
        System.err.println("An error has occured. See below for details");
        e.printStackTrace();
        }
    }  


    /**
     * A function to calculate the avarage area of circles in the array provided. 
     * This array may contain 0 or more circles.
     *
     * @param circles  An array of Circles
     */
    public double averageCircleArea(Circle[] circles) {
        double total_Area = 0;
        for (int i=0; i < circles.length; i++) {
                total_Area += circles[i].area();
            }
        double area = total_Area/circles.length;

      return area;
    }
    
    /**
     * A function to calculate the standard deviation of areas in the circles in the array provided. 
     * This array may contain 0 or more circles.
     * 
     * @param circles  An array of Circles
     */
    public double areaStandardDeviation(Circle[] circles) {
      double total = 0;
        for (int i=0; i < circles.length; i++) {
                total += Math.pow(circles[i].area(), 2);
            }
        double Step1 = total/circles.length;
        double Step2 = Math.pow(averageCircleArea(circles), 2);
        double sigma = Math.sqrt(Step1 - Step2);
      return sigma;
    }
    
    /**
     * Returns 4 values in an array [X1,Y1,X2,Y2] that define the rectangle
     * that surrounds the array of circles given.
     * This array may contain 0 or more circles.
     *
     * @param circles  An array of Circles
     * @return An array of doubles [X1,Y1,X2,Y2] that define the bounding rectangle with
     *         the origin (bottom left) at [X1,Y1] and opposite corner (top right)
     *         at [X2,Y2]
     */
    public double[] calculateAABB(Circle[] circles)
    {
         double X2 = Double.MIN_VALUE;
         double Y2 = Double.MIN_VALUE;
         double X1 = Double.MAX_VALUE;
         double Y1 = Double.MAX_VALUE;

         for (int i=0; i<circles.length; i++) {
             Circle C = circles[i];
             double Cx = C.getCentre().getX();
             double Crad  = C.getRadius();

             if (Cx - Crad < X1) {
                 X1 = Cx - Crad;
             }
             else if (Cx + Crad > X2) {
                 X2 = Cx + Crad;
             }
         }
         for (int i=0; i<circles.length; i++) {
             Circle C = circles[i];
             double Cy = C.getCentre().getY();
             double Crad  = C.getRadius();

             if (Cy - Crad < Y1) {
                 Y1 = Cy - Crad;
             }
             else if (Cy + Crad > Y2) {
                 Y2 = Cy + Crad;
             }
         }
        return new double[]{X1,Y1,X2,Y2};
    }

  
    // =======================================================
    // Tester - tests methods defined in this class
    // =======================================================

    /**
     * Your tester function should go here (see week 14 lecture notes if
     * you're confused). It is not tested by BOSS, but you will receive extra
     * credit if it is implemented in a sensible fashion.
     */
    public static void main(String args[]){
        // Creating an instance of Project 1 
        Project1 P1 = new Project1();
        P1.results("student.data");

        // Testing Circle count
        System.out.println("The number of nonsingular circles are "+P1.circleCounter);

        // Testing aabb array (The bounding rectangle of the 10th and 20th circles)
        System.out.println("The 4 doubles representing the axis-aligned bounding box are : ");
        System.out.format("[x1, y1, x2, y2] = [%.9f, %.9f, %.9f, %.9f]", P1.aabb[0], P1.aabb[1], P1.aabb[2], P1.aabb[3]);
        System.out.println();

        // Testing the maximum and minimum circle area
        System.out.format("The maximum area of the circles is Smax = %.9f", P1.Smax);
        System.out.println();
        System.out.format("The minimum area of the circles is Smin (formated to 14 dp for clarity)= %.14f", P1.Smin);
        System.out.println();

        // Testing the average circle area
        System.out.format("The average circle area is = %.9f",P1.areaAverage);
        System.out.println();

        // Testing the circle area standard deviation
        System.out.format("The circle area standard deviation is = %.9f", P1.areaSD);
        System.out.println();

        // Testing the median circle area
        System.out.format("The median circle area is = %.9f", P1.areaMedian);
        System.out.println();
    }
}