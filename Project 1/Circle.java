/* 
 * PROJECT I: Circle.java
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


public class Circle {

    /*
     * Here, You should define private variables that represent the radius and
     * centre of this particular Circle.
     */

     // r
    private double r;
    
     // A
    private Point A;

    // =========================
    // Constructors
    // =========================
    /**
     * Default constructor - performs no initialization.
     */
    public Circle() {
    }
    
    /**
     * Alternative constructor, which sets the circle up with x and y
     * co-ordinates representing the centre, and a radius.
     *
     * @param xc   x-coordinate of the centre of the circle
     * @param yc   y-coordinate of the centre of the circle
     * @param rad  radius of the circle
     */
    public Circle(double xc, double yc, double rad) {
        A = new Point(xc, yc);
        r = rad;
    }

    /**
     * Alternative constructor, which sets the circle up with a Point
     * representing the centre, and a radius.
     *
     * @param centre  Point representing the centre
     * @param rad     Radius of the circle
     */
    
    public Circle(Point centre, double rad) {
        A = centre;
        r = rad;
    }

    // =========================
    // Setters and Getters
    // =========================

    /**
     * Setter - sets the co-ordinates of the centre.
     *
     * @param xc  new x-coordinate of the centre
     * @param yc  new y-coordinate of the centre
     */   
    public void setCentre(double xc, double yc) {
        this.A = new Point(xc, yc);
    }

    /**
     * Setter - sets the centre of the circle to a new Point.
     *
     * @param C  A Point representing the new centre of the circle.
     */   
    public void setCentre(Point C) {
        this.A = C;
    }
    
    /**
     * Setter - change the radius of this circle.
     *
     * @param rad  New radius for the circle.
     */   
    public void setRadius(double rad) {
        this.r = rad;
    }
    
    /**
     * Getter - returns the centre of this circle.
     *
     * @return The centre of this circle (a Point).
     */   
    public Point getCentre(){
        return this.A;
    }

    /**
     * Getter - extract the radius of this circle.
     *
     * @return The radius of this circle.
     */   
    public double getRadius(){
        return this.r;
    }

    // =========================
    // Convertors
    // =========================

    /**
     * Calculates a String representation of the Circle.
     *
     * @return A String of the form: "[Ax,Ay,Radius]" where Ax and Ay are
     *         numerical values of the coordinates, and Radius is a numerical
     *         value of the radius.
     */
    public String toString() {
        double Ax = this.A.getX();
        double Ay = this.A.getY();
        double Radius = this.r;
        return String.format("[%.9f,%.9f,%.9f]", Ax, Ay, Radius);
    }
    
    // ==========================
    // Service routines
    // ==========================
    
    /**
     * Similar to the equals() function in Point. Returns true if two Circles
     * are equal. By this we mean:
     * 
     * - They have the same radius (up to the tolerance defined in Point).
     * - They have the same centre (up to the tolerance defined in Point).
     * 
     * Remember that the second test is already done in the Point class!
     * 
     * @param c The circle to compare this with.
     * @return true if the two circles are equal.
     */
    public boolean equals(Circle c) {
        return ((Math.abs(r - this.getRadius()) <= Point.GEOMTOL) &&
                this.A.equals(c.getCentre()));
    }
    
    // -----------------------------------------------------------------------
    // Do not change the method below! It is essential for marking the
    // project, and you may lose marks if it is changed.
    // -----------------------------------------------------------------------

    /**
     * Compare this Circle with some Object, using the test above.
     *
     * @param obj  The object to compare this with.
     * @return true if the two objects are equal.
     */
    public boolean equals(Object obj) {
        
        if (obj instanceof Circle) {
            boolean test = false;
            Circle C = (Circle)obj;
            
            test = this.equals(C);

            return test;
        } else {
            return false;
        }
    }

    // ======================================
    // Implementors
    // ======================================
    
    /**
     * Computes and returns the area of the circle.
     *
     * @return  Area of the circle
     */
    public double area() {
        double Area = Math.PI * Math.pow(this.r, 2);
        return Area;
    }

    // =======================================================
    // Tester - test methods defined in this class
    // =======================================================
    
    public static void main(String args[]) {

        // Testing constructors
        Circle C1 = new Circle(0, 1, 2);
        Point Z = new Point(2, -3);
        Circle C2 = new Circle(Z, 3);
        Circle C3 = new Circle();

        // Testing setters and getters
        C3.setCentre(0, 1);
        C3.setRadius(2);
        System.out.println("C1 = "+C1);
        System.out.println("C2 = "+C2);
        System.out.println("C3 = "+C3);

        // Testing service routines
        System.out.println("Are C1 and C3 equal? "+C1.equals(C3));

        // Testing implementors
        System.out.format("The area of C1 = %.9f", C1.area());
        System.out.println();
        System.out.format("The area of C2 = %.9f", C2.area());
        System.out.println();
        System.out.format("The area of C3 = %.9f", C3.area());
        System.out.println();

    }
}
