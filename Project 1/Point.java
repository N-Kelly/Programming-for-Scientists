/*
 * 
 * PROJECT I: Project1.java
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

 /*
  * NAME: Niall Kelly
  * UNIVERSITY ID: 2123618
  * DEPARTMENT: Mathematics
  */

// Importing Math class
import java.lang.Math;

public class Point {
    /**
     * x and y co-ordinates of the point on the plane. 
     */
    private double X, Y;
    
    /**
     * GEOMTOL is described in the formulation. It describes the tolerance
     * that we are going to use throughout this project.
     */
    public static final double GEOMTOL = 1.0e-6;

    // =========================
    // Constructors
    // =========================
    
    /**
     * Default constructor - this initializes X and Y to the point (0,0).
     */
    public Point() {
        setPoint(0.0, 0.0);
    }

   /**
    * Two-parameter version of the constructor. Initialiases (X,Y) to the
    * point (a,b) which is supplied to the function.
    *
    * @param a - x-coordinate of the point
    * @param b - y-coordinate of the point
    */
    public Point (double a, double b) {
        setPoint(a, b);
    }

    // =========================
    // Setters and Getters
    // =========================
    
    /**
     * Setter for instance variables - sets the coordinates of the point.
     *
     * @param X - new x-coordinate for this point.
     * @param Y - new y-coordinate for this point
     */
    public void setPoint(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    /**
     * Getter for x co-ordinate. 
     *
     * @param  none
     * @return The x co-ordinate of this point.
     */   
    public double getX() {
        return this.X;
    }

    /**
     * Getter for y co-ordinate. 
     *
     * @param  none
     * @return The y co-ordinate of this point.
     */   
    public double getY() {
        return this.Y;
    }
    
    // =========================
    // Convertors
    // =========================

    /**
     * Calculates a String representation of the Point.
     *
     * @return A String of the form [X,Y]
     */
    public String toString() {
        return String.format("[%.9f,%.9f]",X,Y);
    }

    // ==========================
    // Implementors
    // ==========================
    
    /**
     * Compute the distance of this Point to the supplied Point x.
     *
     * @param x  Point from which the distance should be measured.
     * @return   The distance between x and this instance of a Point
     */
    public double distance(Point x) {
        double dist;
        dist = Math.sqrt(Math.pow(this.X - x.getX(), 2) + Math.pow(this.Y - x.getY(), 2));
        return dist;
    }
    
    // ==========================
    // Service routines
    // ==========================
    
    // -----------------------------------------------------------------------
    // Do not change the two methods below! They are essential for marking the
    // project, and you may lose marks if they are changed.
    //
    // We shall talk about these functions later (week 17).
    // -----------------------------------------------------------------------

    /**
     * Compare this with some Object. Two points are equal if their are in a
     * box given by the constant GEOMTOL.
     *
     * @param obj The object to compare with.
     * @return If obj is a Quaternion with the same coefficients.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point q = (Point)obj;
            return (Math.abs(X - q.X) <= GEOMTOL && 
                    Math.abs(Y - q.Y) <= GEOMTOL);
        } else {
            return false;
        }
    }

    /**
     * Compare two points. Two points are equal if their are in a box given by
     * the constant GEOMTOL.
     *
     * @param q  A Point to be compared to this instance
     * @return   true if Point q is equal to this instance.
     */
    public boolean equals(Point q) {
        return (Math.abs(X - q.X) <= GEOMTOL && 
            Math.abs(Y - q.Y) <= GEOMTOL);
    }

    // =======================================================
    // Tester - tests methods defined in this class
    // =======================================================

    /**
     * Your tester function should go here (see week 14 lecture notes if
     * you're confused). It is not tested by BOSS, but you will receive extra
     * credit if it is implemented in a sensible fashion.
     */
    public static void main (String args[]) {
        // Creating 2 new Point objects
        Point A = new Point(2, 4);
        Point B = new Point(-3, 5);
        Point C = new Point();

        // Testing setters and getters
        C.setPoint(2, 4);
        System.out.format("x coordinate of point A = %.9f", A.getX());
        System.out.println();
        System.out.format("y coordinate of point A = %.9f", A.getY());
        System.out.println();
        System.out.format("x coordinate of point B = %.9f", B.getX());
        System.out.println();
        System.out.format("y coordinate of point B = %.9f", B.getY());
        System.out.println();

        // Testing distance between 2 points
        System.out.format("Distance between A and B = %.9f", A.distance(B));
        System.out.println();

        // Testing equals function
        System.out.println("Are A and C equal? "+A.equals(C));
    } 
}
