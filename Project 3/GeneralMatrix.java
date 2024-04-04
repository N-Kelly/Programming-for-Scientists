/*
 * PROJECT III: GeneralMatrix.java
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

import java.util.Arrays;
import java.lang.Math;

public class GeneralMatrix extends Matrix {
    /**
     * This instance variable stores the elements of the matrix.
     */
    private double[][] values;

    /**
     * Constructor function: should initialise iDim and jDim through the Matrix
     * constructor and set up the data array.
     *
     * @param firstDim   The first dimension of the array.
     * @param secondDim  The second dimension of the array.
     */
    public GeneralMatrix(int firstDim, int secondDim) {
        // calling Matrix constructor
        super(firstDim, secondDim);

        // initialising instance variable values
        values = new double[firstDim][secondDim];
    }

    /**
     * Constructor function. This is a copy constructor; it should create a
     * copy of the second matrix.
     *
     * @param second  The matrix to create a copy of.
     */
    public GeneralMatrix(GeneralMatrix second) {
        
        // calling Matrix constructor
        super(second.iDim, second.jDim);

        // initialising values array
        values = new double[second.iDim][second.jDim];

        // copying elements from old GeneralMatrix to new GeneralMatrix
        for (int i=0; i<second.iDim; i++) {
            for (int j=0; j<second.jDim; j++) {
                values[i][j] = second.getIJ(i+1, j+1);
            }
        }
    }
    
    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
        
        // Throwing MatrixException when trying to access element that is out of bounds
        if ((i > this.iDim) || (j > this.jDim)) {
            throw new MatrixException("The matrix element you tried to access is outside the dimensions of the matrix");
        }

        // Getting and returning (i,j)th element
        return this.values[i-1][j-1];
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the values array.
     *
     * @param i      The location in the first co-ordinate.
     * @param j      The location in the second co-ordinate.
     * @param value  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double value) {

        // Throwing MatrixException when trying to set element that is out of bounds
        if ((i > this.iDim) || (j > this.jDim)) {
            throw new MatrixException("The matrix element you tried to set is outside the dimensions of the matrix");
        }

        // Setting (i,j)th element
        this.values[i-1][j-1] = value;
    }
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {

        // creating sign array to determine sign of matrix
        double[] sign = new double[]{1};

        // creating determinant value variable
        double d = 1;

        // Performing LUdecomp method on our GeneralMatrix and calculating determinant
        // Determinant is the product of the main diagonal
        try {
            GeneralMatrix LU = this.LUdecomp(sign);
            for (int n=1; n<=iDim; n++) {
                d = d*LU.getIJ(n,n);
            }
        }

        // Catching MatrixException if thrown
        catch (MatrixException e) {
            String error = e.getMessage();
            if (error.equals("Matrix is singular")){
                return 0.0;
            }
            else {
                throw e;
            }
        }

        // Returning our Determinant
        // Multiplying by only element in sign array to get correct sign of determinant
        return d*sign[0];
    }

    /**
     * Add the matrix to another second matrix.
     *
     * @param second  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the second matrix.
     */
    public Matrix add(Matrix second) {
        
        // Throwing MatrixException if matrix dimensions are not compatible
        if ((this.iDim != second.iDim) || (this.jDim != second.jDim)){
            throw new MatrixException("Matrix dimensions are not compatable for addition");
        }

        // Creating new GeneralMatrix object
        GeneralMatrix X = new GeneralMatrix(this.iDim, this.jDim);

        // Summing each element of this matrix with the corresponding element of second matrix 
        for (int i=0; i<iDim; i++) {
            for (int j=0; j<jDim; j++) {
                X.setIJ(i+1,j+1, this.getIJ(i+1,j+1) + second.getIJ(i+1,j+1));
            }
        }

        // Returning the sum of the two matrices
        return X;
    }
    
    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {

        // Throwing MatrixException if matrix dimensions are not compatible
        if (this.jDim != A.iDim){
            throw new MatrixException("Matrix dimensions not compatible for multiplication");
        }

        // Creating new GeneralMatrix object
        GeneralMatrix Y = new GeneralMatrix(this.iDim, A.jDim);

        // Multiplying the two matrices
        for (int c=1; c<=Y.jDim; c++) {
            for (int r=1; r<=Y.iDim; r++) {
                for (int n=1; n<=this.jDim; n++) {
                    Y.setIJ(r, c, Y.getIJ(r,c) + this.getIJ(r,n) * A.getIJ(n,c));
                }
            }
        }

        // Returning the product of the two matrices
        return Y;
    }

    /**
     * Multiply the matrix by a scalar.
     *
     * @param scalar  The scalar to multiply the matrix by.
     * @return        The product of this matrix with the scalar.
     */
    public Matrix multiply(double scalar) {

        // Creating new GeneralMatrix object
        GeneralMatrix A = new GeneralMatrix(this.iDim, this.jDim);

        // Multiplying each element of the matrix by the scalar
        for (int i=1; i<=A.iDim; i++) {
            for (int j=1; j<=A.jDim; j++) {
                A.setIJ(i,j, this.getIJ(i,j) * scalar); 
            }
        }

        // Returning the product of the scalar and the matrix
        return A;
    }

    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */

    
    public void random() {

        for (int i=0; i<this.iDim; i++) {
            for (int j=0; j<this.jDim; j++) {
                this.values[i][j] = Math.random();
            }
        }
    }
     

    /**
     * Returns the LU decomposition of this matrix; i.e. two matrices L and U
     * so that A = LU, where L is lower-diagonal and U is upper-diagonal.
     * 
     * On exit, decomp returns the two matrices in a single matrix by packing
     * both matrices as follows:
     *
     * [ u_11 u_12 u_13 u_14 ]
     * [ l_21 u_22 u_23 u_24 ]
     * [ l_31 l_32 u_33 u_34 ]
     * [ l_41 l_42 l_43 u_44 ]
     *
     * where u_ij are the elements of U and l_ij are the elements of l. When
     * calculating the determinant you will need to multiply by the value of
     * sign[0] calculated by the function.
     * 
     * If the matrix is singular, then the routine throws a MatrixException.
     * In this case the string from the exception's getMessage() will contain
     * "singular"
     *
     * This method is an adaptation of the one found in the book "Numerical
     * Recipies in C" (see online for more details).
     * 
     * @param sign  An array of length 1. On exit, the value contained in here
     *              will either be 1 or -1, which you can use to calculate the
     *              correct sign on the determinant.
     * @return      The LU decomposition of the matrix.
     */
    public GeneralMatrix LUdecomp(double[] sign) {
        // This method is complete. You should not even attempt to change it!!
        if (jDim != iDim)
            throw new MatrixException("Matrix is not square");
        if (sign.length != 1)
            throw new MatrixException("d should be of length 1");
        
        int           i, imax = -10, j, k; 
        double        big, dum, sum, temp;
        double[]      vv   = new double[jDim];
        GeneralMatrix a    = new GeneralMatrix(this);
        
        sign[0] = 1.0;
        
        for (i = 1; i <= jDim; i++) {
            big = 0.0;
            for (j = 1; j <= jDim; j++)
                if ((temp = Math.abs(a.values[i-1][j-1])) > big)
                    big = temp;
            if (big == 0.0)
                throw new MatrixException("Matrix is singular");
            vv[i-1] = 1.0/big;
        }
        
        for (j = 1; j <= jDim; j++) {
            for (i = 1; i < j; i++) {
                sum = a.values[i-1][j-1];
                for (k = 1; k < i; k++)
                    sum -= a.values[i-1][k-1]*a.values[k-1][j-1];
                a.values[i-1][j-1] = sum;
            }
            big = 0.0;
            for (i = j; i <= jDim; i++) {
                sum = a.values[i-1][j-1];
                for (k = 1; k < j; k++)
                    sum -= a.values[i-1][k-1]*a.values[k-1][j-1];
                a.values[i-1][j-1] = sum;
                if ((dum = vv[i-1]*Math.abs(sum)) >= big) {
                    big  = dum;
                    imax = i;
                }
            }
            if (j != imax) {
                for (k = 1; k <= jDim; k++) {
                    dum = a.values[imax-1][k-1];
                    a.values[imax-1][k-1] = a.values[j-1][k-1];
                    a.values[j-1][k-1] = dum;
                }
                sign[0] = -sign[0];
                vv[imax-1] = vv[j-1];
            }
            if (a.values[j-1][j-1] == 0.0)
                a.values[j-1][j-1] = 1.0e-20;
            if (j != jDim) {
                dum = 1.0/a.values[j-1][j-1];
                for (i = j+1; i <= jDim; i++)
                    a.values[i-1][j-1] *= dum;
            }
        }
        
        return a;
    }

    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        
        // Testing constructor function
        GeneralMatrix A = new GeneralMatrix(2, 2);
        GeneralMatrix B = new GeneralMatrix(2, 2);
        GeneralMatrix C = new GeneralMatrix(2,2);
        GeneralMatrix R = new GeneralMatrix(3, 2);
        GeneralMatrix X1 = new GeneralMatrix(6,2);
        GeneralMatrix X2 = new GeneralMatrix(2,6);

        // Testing setter function
        A.setIJ(1,1,3);
        A.setIJ(1,2,1);
        A.setIJ(2,1,1);
        A.setIJ(2,2,1);

        B.setIJ(1,1,1);
        B.setIJ(1,2,2);
        B.setIJ(2,1,3);
        B.setIJ(2,2,4);

        C.setIJ(1,1,0);
        C.setIJ(1,2,0);
        C.setIJ(2,1,5);
        C.setIJ(2,2,5);

        // Testing random function
        R.random();
        X1.random();
        X2.random();

        // Testing copy constructor
        GeneralMatrix Copy = new GeneralMatrix(R);

        //Testing toString Matrix method
        System.out.format("A = %n"+A);
        System.out.format("B = %n"+B);
        System.out.format("C = %n"+C);
        System.out.format("R =%n"+R);
        System.out.format("Copy of R = %n"+Copy);

        //Testing operations functions
        System.out.println("det(A) = "+A.determinant());
        System.out.println("det(C) = "+C.determinant());
        System.out.format("AB = %n"+A.multiply(B));
        System.out.format("RA = %n"+R.multiply(A));
        System.out.format("A+B = %n"+A.add(B));
        System.out.format("10*B = %n"+A.multiply(10));
        System.out.format("X1*X2 = %n"+X1.multiply(X2));
        System.out.format("X2*X1 = %n"+X2.multiply(X1));
    }
}