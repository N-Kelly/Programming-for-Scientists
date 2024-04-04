/*
 * PROJECT II: Polynomial.java
 * 
 * This class is designed to use Complex in order to represent polynomials
 * with complex co-efficients. It provides very basic functionality. The project formulation contains a
 * complete description.
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 * 
 * Tasks:
 *
 * 1) Complete this class.
 *
 * 2) Fill in the following fields:
 *
 * NAME: Niall Kelly
 * UNIVERSITY ID: 2123618
 * DEPARTMENT: Mathematics
 */

 import java.lang.Math;

public class Polynomial {
    /**
     * An array storing the complex co-efficients of the polynomial.
     */
    Complex[] coeff;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * General constructor: assigns this polynomial a given set of
     * co-efficients.
     *
     * @param coeff  The co-efficients to use for this polynomial.
     */
    public Polynomial(Complex[] coeff) {
        this.coeff = coeff;
        Complex zero = new Complex();
        int count = 0;
        for (int i = this.coeff.length - 1; coeff[i] == zero; i--) {
            count++;
        }

        coeff = new Complex[this.coeff.length - count];
        for (int j = 0; j < this.coeff.length - count; j++){
            coeff[j] = this.coeff[j];
        }
    }
    
    /**
     * Default constructor: sets the Polynomial to the zero polynomial.
     */
    public Polynomial() {
        Complex zero = new Complex();
        coeff = new Complex[]{zero};
    }

    // ========================================================
    // Operations and functions with polynomials.
    // ========================================================

    /**
     * Return the coefficients array.
     *
     * @return  The coefficients array.
     */
    public Complex[] getCoeff() {
        return this.coeff;
    }

    /**
     * Create a string representation of the polynomial.
     * Use z to represent the variable.  Include terms
     * with zero co-efficients up to the degree of the
     * polynomial.
     *
     * For example: (-5.000+5.000i) + (2.000-2.000i)z + (-1.000+0.000i)z^2
     */
    public String toString() {
        String s = "("+this.coeff[0].toString()+")";
        for (int i = 1; i<this.coeff.length; i++){
            s += (" + ("+this.coeff[i].toString()+")"+"Z^"+i);
        }
        return s;
    }

    /**
     * Returns the degree of this polynomial.
     */
    public int degree() {
        return (this.coeff.length - 1);
    }

    /**
     * Evaluates the polynomial at a given point z.
     *
     * @param z  The point at which to evaluate the polynomial
     * @return   The complex number P(z).
     */
    public Complex evaluate(Complex z) {
        Complex[] my_coeff = this.getCoeff();
        int num = my_coeff.length;
        Complex total = new Complex();
        for (int n=0; n < num; n++) {
            Complex z_pow = new Complex(1);
            for (int i=1; i <= n; i++) {
                z_pow = z_pow.multiply(z);
            }
            Complex term = my_coeff[n].multiply(z_pow);
            total = total.add(term);
        }

        return total;
    }

    
    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        // Testing constructors
        Complex c1 = new Complex();
        Complex c2 = new Complex(2, 3);
        Complex c3 = new Complex(5, 2);
        Complex[] CArray = new Complex[]{c1, c2, c3};
        Polynomial P = new Polynomial (CArray);
        Polynomial F = new Polynomial();

        // Testing to string method
        System.out.println("Polynomial P = "+P.toString());

        // Testing getters
        Complex[] coeffarray = P.getCoeff();
        System.out.println("The coefficients of P are: "+coeffarray[0]+", "+coeffarray[1]+", "+coeffarray[2]);

        // Testing the degree method
        System.out.println("The degree of P is "+P.degree());
        System.out.println("The degree of F is "+F.degree());

        // Testing Evaluate method
        Complex eval = new Complex(1,1);
        System.out.println("P("+eval.toString()+") = "+P.evaluate(eval));
    }
}