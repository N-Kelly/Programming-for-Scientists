/*
 * PROJECT II: Project2.java
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

// These next lines import the relevant classes needed to output an image and
// *SHOULD NOT* be changed.
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.ImageIO;

import java.util.ArrayList;

public class Project2 {
    /**
     * A reference to the Secant iterator object.
     */
    private Secant iterator;
    
    /**
     * The top-left corner of the square in the complex plane to examine.
     */
    private Complex origin;
    
    /**
     * The width of the square in the complex plane to examine.
     */
    private double width;
    
    /**
     * A list of roots of the polynomial.
     */
    private ArrayList<Complex> roots;
    
    /**
     * A two dimensional array holding the colours of the plot.
     */
    private Color[][] colours;

    /**
     * A flag indicating the type of plot to generate. If true, colourPixel will
     * choose darker colours if a particular root takes longer to converge.
     */
    private boolean colourIterations;

    /**
     * A standard Java object which allows us to store a simple image in
     * memory. This will be set up by setupFractal.
     */
    private BufferedImage fractal;
    
    /**
     * This object is another standard Java object which allows us to perform
     * basic graphical operations (drawing lines, rectangles, pixels, etc) on
     * the BufferedImage. This will be set up by setupFractal.
     */
    private Graphics2D g2;

    /**
     * Defines the width (in pixels) of the BufferedImage and hence the
     * resulting image.
     */
    public static final int NUMPIXELS = 400;
    
    // ========================================================
    // Constructor function.
    // ========================================================
    
    /**
     * Constructor function which initialises the instance variables
     * above. IMPORTANT: Remember to call setupFractal at the end of this
     * function!!
     *
     * @param p       The polynomial to generate the fractal of.
     * @param origin  The top-left corner of the square to image.
     * @param width   The width of the square to image.
     */
    public Project2(Polynomial p, Complex origin, double width) {
        iterator = new Secant(p);
        this.origin = origin;
        this.width = width;
        this.roots = new ArrayList<Complex>();
        setupFractal();
    }
    
    // ========================================================
    // Basic operations.
    // ========================================================

    /**
     * Print out all of the roots found so far, which are contained in the
     * roots ArrayList.
     */
    public void printRoots() {
        int l = roots.size();
        for (int i=0; i<l; i++) {
            System.out.println(roots.get(i));
        }
    }

    /**
     * 
     * @return an ArrayList of roots for the polynomial
     */
    public ArrayList<Complex> getRoots() {
        return roots;
    }
    
    /**
     * Check to see if root is in the roots ArrayList (up to tolerance).
     *
     * @param root  Root to find in this.roots.
     * @return The index of root within roots (-1 if the root is not found)
     */
    public int index(Complex root) {
        int index = 0;
        for (int i=0; i < this.roots.size(); i++) {
            Complex r = this.roots.get(i);
            if ((r.add((root).negate())).abs() < Secant.TOL) {
                index = i;
                return index;
            }
        }        
        return -1;
    }
    
    /**
     * Convert from pixel indices (i,j) to the complex number (origin.real +
     * i*dz, origin.imag - j*dz).
     *
     * @param i  x-axis co-ordinate of the pixel located at (i,j)
     * @param j  y-axis co-ordinate of the pixel located at (i,j)
     * 
     */
    public Complex pixelToComplex(int i, int j) {

        double dz = width/NUMPIXELS;

        return new Complex(origin.getReal() + i*dz, origin.getImag() - j*dz);
    }
    
    // ========================================================
    // Fractal generating function.
    // ========================================================

    /**
     * Generate the fractal image.  Use colourPixel() to add coloured pixels
     * to the image for this fractal.
     */
    public void createFractal(boolean colourIterations) {
        this.colourIterations = colourIterations;
        Complex r;
        for (int n=0; n<NUMPIXELS; n++) {
            for (int m=0; m<NUMPIXELS; m++) {
                Complex x = pixelToComplex(n, m);
                iterator.iterate(new Complex(), x);
                if (iterator.getError() == Secant.Error.OK) {
                    r = iterator.getRoot();
                    if (index(r) == -1) {
                        roots.add(r);    
                    }
                    colourPixel(n, m, index(r), iterator.getNumIterations());
                }
            }
        }
    }

    // ========================================================
    // Tester function.
    // ========================================================
    
    public static void main(String[] args) {
        // Here is some example code which generates the two images seen in
        // figure 1 of the formulation.
        Complex[] coeff = new Complex[] { new Complex(1,0.0),  new Complex(), new Complex(), new Complex(5.0,0.0) };
        Polynomial p    = new Polynomial(coeff);
        Project2 project = new Project2(p, new Complex(-1.0,1.0), 2.0);
        
        // The following lines of code will raise Exceptions initially
        // because the createFractal and the constructor are incomplete
        project.createFractal(false);
        project.saveFractal("fractal-light.png");
        project.createFractal(true);
        project.saveFractal("fractal-dark.png");
    }
    
    // ====================================================================
    // OTHER FUNCTIONS
    // ====================================================================
    
    /**
     * Sets up all the fractal image. Make sure that your constructor calls
     * this function!
     */
    private void setupFractal()
    {
        int i, j;

        if (iterator.getF().degree() < 3 || iterator.getF().degree() > 5)
            throw new RuntimeException("Degree of polynomial must be between 3 and 5 inclusive!");

        this.colours       = new Color[5][Secant.MAXITER];
        this.colours[0][0] = Color.RED;
        this.colours[1][0] = Color.GREEN;
        this.colours[2][0] = Color.BLUE;
        this.colours[3][0] = Color.CYAN;
        this.colours[4][0] = Color.MAGENTA;

        
        for (i = 0; i < 5; i++) {
            float[] components = colours[i][0].getRGBComponents(null);
            float[] delta      = new float[3];
            
            for (j = 0; j < 3; j++)
                delta[j] = 0.8f*components[j]/Secant.MAXITER;
            
            for (j = 1; j < Secant.MAXITER; j++) {
                float[] tmp  = colours[i][j-1].getRGBComponents(null);
                colours[i][j] = new Color(tmp[0]-delta[0], tmp[1]-delta[1], 
                                         tmp[2]-delta[2]);
            }
        }
        
        fractal = new BufferedImage(NUMPIXELS, NUMPIXELS, BufferedImage.TYPE_INT_RGB);
        g2      = fractal.createGraphics();
    }
    
    /**
     * Colours a pixel in the image.
     *
     * @param i          x-axis co-ordinate of the pixel located at (i,j)
     * @param j          y-axis co-ordinate of the pixel located at (i,j)
     * @param rootColour  An integer between 0 and 4 inclusive indicating the
     *                   root number.
     * @param numIter    Number of iterations at this root.
     */
    private void colourPixel(int i, int j, int rootColour, int numIter) 
    {
        if (colourIterations)
            g2.setColor(colours[rootColour][numIter-1]);
        else
            g2.setColor(colours[rootColour][0]);
        g2.fillRect(i,j,1,1);
    }

    /**
     * Saves the fractal image to a file.
     *
     * @param fileName  The filename to save the image as. Should end in .png.
     */
    public void saveFractal(String fileName) {
        try {
            File outputfile = new File(fileName);
            ImageIO.write(fractal, "png", outputfile);
        } catch (IOException e) {
            System.out.println("I got an error trying to save! Maybe you're out of space?");
        }
    }
}