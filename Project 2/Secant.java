/*
 * PROJECT II: Secant.java
 *
 * In this class, you will create a basic Java object responsible for
 * performing the Secant root finding method on a given polynomial
 * f(z) with complex co-efficients. The formulation outlines the method, as
 * well as the basic class structure, details of the instance variables and
 * how the class should operate.
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 * 
 * Tasks:
 *
 * 1) Complete this class
 *
 * 2) Fill in the following fields:
 *
 * NAME: Niall Kelly
 * UNIVERSITY ID: 2123618
 * DEPARTMENT: Mathematics
 */

public class Secant {
    /**
     * The maximum number of iterations that should be used when applying
     * Secant. Ensure this is *small* (e.g. at most 50) otherwise your
     * program may appear to freeze.
     */
    public static final int MAXITER = 20;

    /**
     * The tolerance that should be used throughout this project.
     */
    public static final double TOL = 1.0e-10;

    /**
     * The polynomial we wish to apply the Secant method to.
     */
    private Polynomial f;


    /**
     * A root of the polynomial f corresponding to the root found by the
     * iterate() function below.
     */
    private Complex root;
    
    /**
     * The number of iterations required to reach within TOL of the root.
     */
    private int numIterations;

    /**
     * An enumeration that signifies errors that may occur in the root finding
     * process.
     *
     * Possible values are:
     *   OK: Nothing went wrong.
     *   ZERO: Difference went to zero during the algorithm.
     *   DNF: Reached MAXITER iterations (did not finish)
     */
    enum Error { OK, ZERO, DNF };
    private Error err = Error.OK;
    
    
    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * Basic constructor.
     *
     * @param p  The polynomial used for Secant.
     */
    public Secant(Polynomial p) {
        f = p;
        this.root = root;
        this.numIterations = numIterations;
        this.err = err;
    }

    // ========================================================
    // Accessor methods.
    // ========================================================
    
    /**
     * Returns the current value of the err instance variable.
     */
    public Error getError() {
        return this.err;
    }

    /**
     * Returns the current value of the numIterations instance variable.
     */
    public int getNumIterations() { 
        return this.numIterations;
    }
    
    /**
     * Returns the current value of the root instance variable.
     */
    public Complex getRoot() {
        return this.root;
    }

    /**
     * Returns the polynomial associated with this object.
     */
    public Polynomial getF() {
        return this.f;
    }

    // ========================================================
    // Secant method (check the comment)
    // ========================================================
    
    /**
     * Given two complex numbers z0 and z1, apply Secant to the polynomial f in
     * order to find a root within tolerance TOL.
     *
     * One of three things may occur:
     *
     *   - The root is found, in which case, set root to the end result of the
     *     algorithm, numIterations to the number of iterations required to
     *     reach it and err to OK.
     *   - At some point the absolute difference between f(zn) and f(zn-1) becomes zero. 
     *     In this case, set err to ZERO and return.
     *   - After MAXITER iterations the algorithm has not converged. In this 
     *     case set err to DNF and return.
     *
     * @param z0,z1  The initial starting points for the algorithm.
     */
    public void iterate(Complex z0, Complex z1) {
        int iterations = 0;
        Polynomial p = this.f;
        Complex z_nplus = new Complex();
        Complex z_n = z1;
        Complex z_nminus = z0;

        if ((z1.add(z0.negate())).abs() < TOL && (p.evaluate(z1)).abs() < TOL) {
            this.root = z1;
            this.numIterations = 1;
            this.err = Error.OK;
        }
        
        while (((z_n.add(z_nminus.negate())).abs() > TOL || (p.evaluate(z_n)).abs() > TOL) && (iterations < MAXITER)) {

            iterations++;

            Complex frac = (z_n.add(z_nminus.negate())).divide(p.evaluate(z_n).add(p.evaluate(z_nminus).negate()));

            z_nplus = z_n.add(( p.evaluate(z_n).multiply(frac)).negate());

            if ((p.evaluate(z_n).add((p.evaluate(z_nminus)).negate())).abs() < TOL) {
                this.err = Error.ZERO;
                numIterations = iterations;
                break;
            }
            z_nminus = z_n;
            z_n = z_nplus;
            z_nplus = new Complex(); 
        }
        if (iterations == MAXITER && (z_n.add(z_nminus.negate())).abs() > TOL) {
            this.err = Error.DNF;
        }
        else if (((z_n.add(z_nminus.negate()).abs() < TOL) && ((p.evaluate(z_n)).abs() < TOL) && (iterations <= MAXITER))) {
            this.root = z_n;
            this.numIterations = iterations;
            this.err = Error.OK;

        }
        
    }

      
    // ========================================================
    // Tester function.
    // ========================================================
    
    public static void main(String[] args) {
        // Basic tester: find a root of f(z) = z^3-1.
        Complex[] coeff1 = new Complex[] { new Complex(-1.0,0.0), new Complex(), new Complex(), new Complex(1.0,0.0) };
        Polynomial p    = new Polynomial(coeff1);
        Secant     s1    = new Secant(p);
                
        s1.iterate(new Complex(), new Complex(1.0,1.0));
        System.out.println(s1.getNumIterations());   // 12
        System.out.println(s1.getError());           // OK
        System.out.println(s1.getRoot());
    }
}
