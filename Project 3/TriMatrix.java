/*
 * PROJECT III: TriMatrix.java
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

public class TriMatrix extends Matrix {
    /**
     * An array holding the diagonal elements of the matrix.
     */
    private double[] diagonal;

    /**
     * An array holding the upper-diagonal elements of the matrix.
     */
    private double[] upperDiagonal;

    /**
     * An array holding the lower-diagonal elements of the matrix.
     */
    private double[] lowerDiagonal;
    
    /**
     * Constructor function: should initialise iDim and jDim through the Matrix
     * constructor and set up the values array.
     *
     * @param dimension  The dimension of the array.
     */
    public TriMatrix(int dimension) {
        // calling Matrix constructor
        super(dimension, dimension);

        // initialising the 3 diagonal arrays
        diagonal = new double[dimension];
        upperDiagonal = new double[dimension - 1];
        lowerDiagonal = new double[dimension - 1];
    }
    
    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {

        // Getting and returning (i,j)th element
        if (i == j ) {
            return this.diagonal[i-1];
        }
        else if (i == j-1) {
            return this.upperDiagonal[i-1];
        }
        else if (j == i-1) {
            return this.lowerDiagonal[j-1];
        }
        else {
            return 0.0;
        }
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i      The location in the first co-ordinate.
     * @param j      The location in the second co-ordinate.
     * @param value  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double value) {

        // Setting (i,j)th element as long as it is within the three main diagonals
        if (i == j ) {
            this.diagonal[i-1] = value;
        }
        else if (i == j-1) {
            this.upperDiagonal[i-1] = value;
        }
        else if (j == i-1) {
            this.lowerDiagonal[j-1] = value;
        }

        // Throwing MatrixException when trying to set element that is not within the three main diagonals
        else {
            throw new MatrixException("You cannot change values in a Tri-matrix outside the three main diagonals");
        }
    }
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {

        // Performing LUdecomp method on our TriMatrix and calculating determinant
        // Determinant is the product of the main diagonal
        double d = 1;
        try {
            TriMatrix LU = this.LUdecomp();
            double[] diag = LU.diagonal;
            int diag_length = diag.length;
            for (int i=0; i<diag_length; i++) {
                d = d*diag[i];
            }
        }

        // Catching MatrixException if thrown
        catch (MatrixException e) {
            if (e.getMessage().equals("Matrix is singular")){
                return 0.0;
            }
            else {
                throw e;
            }
        }

        // Returning our Determinant
        return d;
    }
    
    /**
     * Returns the LU decomposition of this matrix. See the formulation for a
     * more detailed description.
     * 
     * @return The LU decomposition of this matrix.
     * 
     * We want to break our matrix up into the following form:
     * 
     * |d1 u1              |     |1                  |  | d1*  u1*                        |
     * |l2 d1 u2           |     |l2*  1             |  |      d2*  u2*                   |
     * |   l3 d3 u3        |  =  |    l3*  1         |  |         ...                     |
     * |       ...         |     |     . . .         |  |            ...                  |
     * |             u(n-1)|     |         . . .     |  |                d(n-1)*  u(n-1)* |
     * |          ln dn    |     |             ln*  1|  |                         dn*     |
     * 
     * To calculate the entries of the LU decomposition (which we will store 
     * in compressed form, in one matrix) we will use the difference equations:
     *      d1 = d1*
     *      u_k = u_k*                   for all k
     *      l_k = l_k* x d_(k-1)*        for k >= 2
     *      d_k = l_k* x u_(k-1) + d_k*  for k >= 2
     * 
     */
    public TriMatrix LUdecomp() {

        // Finding the dimension of the TriMatrix
        int dim = this.iDim;

        // Checking if we have a zero row or zero column. If we do we know the matrix is singular
        if ((this.diagonal[0]==0 && this.lowerDiagonal[0]==0) || ((this.diagonal[dim-1]==0 && this.upperDiagonal[dim-2]==0))) {
            throw new MatrixException("Matrix is singular");
        }
        if ((this.diagonal[0]==0 && this.upperDiagonal[0]==0) || ((this.diagonal[dim-1]==0 && this.lowerDiagonal[dim-2]==0))) {
            throw new MatrixException("Matrix is singular");
        }
        for (int j=0; j<dim-1; j++) {
            if (this.upperDiagonal[j]==0 && this.diagonal[j+1]==0 && this.lowerDiagonal[j+1]==0) {
                throw new MatrixException("Matrix is singular");
            }
            if (this.lowerDiagonal[j]==0 && this.diagonal[j+1]==0 && this.upperDiagonal[j+1]==0) {
                throw new MatrixException("Matrix is singular");
            }
        }

        // Creating our matrix, LU, which will contain our compressed LU decomposition
        TriMatrix LU = new TriMatrix(this.iDim);

        // Setting (1,1) element of LU to the (1,1) element of our matrix
        LU.diagonal[0] = this.diagonal[0];

        // Checking if the first element of the main diagonal is zero. If yes we know the matrix is singular
        if (LU.diagonal[0] == 0) {
            throw new MatrixException("Matrix is singular");
        }

        // Finding the length of the upper and lower diagonals of LU
        int diag_length = LU.diagonal.length;
        int ldiag_length = LU.lowerDiagonal.length;

        // Calculating our LU decomposition using the difference equations defined above
        for (int k=1; k<diag_length; k++) {
            LU.diagonal[k] = this.diagonal[k] - (this.lowerDiagonal[k-1]*this.upperDiagonal[k-1])/LU.diagonal[k-1];
            if (LU.diagonal[k] == 0) {
                throw new MatrixException("Matrix is singular");
            }
            LU.lowerDiagonal[k-1] = this.lowerDiagonal[k-1]/LU.diagonal[k-1];
            LU.upperDiagonal[k-1] = this.upperDiagonal[k-1];
        }
        
        // Returning LU
        return LU;
        
    }

    /**
     * Add the matrix to another second matrix.
     *
     * @param second  The Matrix to add to this matrix.
     * @return        The sum of this matrix with the second matrix.
     */
    public Matrix add(Matrix second){
        
        // Throwing MatrixException if matrix dimensions are not compatible
        if ((this.iDim != second.iDim) || (this.jDim != second.jDim)){
            throw new MatrixException("Matrix dimensions are not compatable for addition");
        }
        
        // If the two matrices are two TriMatrices return TriMatrix
        if ((this instanceof TriMatrix) && (second instanceof TriMatrix)) {
            TriMatrix X = new TriMatrix(this.iDim);
            for (int i=0; i<iDim; i++) {
                for (int j=0; j<jDim; j++) {
                    if (i == j) {
                        X.setIJ(i+1, j+1, this.getIJ(i+1,j+1) + second.getIJ(i+1,j+1));
                    }
                    if (i == j-1) {
                        X.setIJ(i+1, j+1, this.getIJ(i+1,j+1) + second.getIJ(i+1,j+1));
                    }
                    if (i-1 == j) {
                        X.setIJ(i+1, j+1, this.getIJ(i+1,j+1) + second.getIJ(i+1,j+1));
                    }
                }
            }

            // casting to Matrix superclass before returning
            Matrix M = (Matrix) X;

            return M;
        }

        // In any other case (When one of the matrices is a GeneralMatrix and the other a TriMatrix
        // or when both matrices are GeneralMatrices) return a GeneralMatrix
        else {
            GeneralMatrix X = new GeneralMatrix(this.iDim, this.jDim);
            for (int i=0; i<iDim; i++) {
                for (int j=0; j<jDim; j++) {
                    X.setIJ(i+1,j+1, this.getIJ(i+1,j+1) + second.getIJ(i+1,j+1));
                }
            }

            // casting to Matrix superclass before returning
            Matrix M = (Matrix) X;

            return M;
        }
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
        GeneralMatrix X = new GeneralMatrix(this.iDim, A.jDim);

        // Multiplying the two matrices
        for (int c=1; c<=X.jDim; c++) {
            for (int r=1; r<=X.iDim; r++) {
                for (int n=1; n<=this.jDim; n++) {
                    X.setIJ(r, c, X.getIJ(r,c) + this.getIJ(r,n) * A.getIJ(n,c));
                }
            }
        }

        // casting to Matrix superclass before returning
        Matrix M = (Matrix) X;

        return M;
    }
    
    /**
     * Multiply the matrix by a scalar.
     *
     * @param scalar  The scalar to multiply the matrix by.
     * @return        The product of this matrix with the scalar.
     */
    public Matrix multiply(double scalar) {

        // Creating new GeneralMatrix object
        GeneralMatrix X = new GeneralMatrix(this.iDim, this.jDim);

        // Multiplying each element of the matrix by the scalar
        for (int i=1; i<=this.iDim; i++) {
            for (int j=1; j<=this.jDim; j++) {
                X.setIJ(i, j, this.getIJ(i,j) * scalar);
            }
        }

        // casting to Matrix superclass before returning
        Matrix M = (Matrix) X;

        return M;
    }

    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {
        
        for (int k=0; k<this.iDim-1; k++) {
            this.diagonal[k] = Math.random();
            this.upperDiagonal[k] = Math.random();
            this.lowerDiagonal[k] = Math.random();
        }

        this.diagonal[this.iDim-1] = Math.random();
    }
    
    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {

        // Testing constructors
        TriMatrix T = new TriMatrix(3);
        TriMatrix R = new TriMatrix(3);
        TriMatrix One = new TriMatrix(4);
        GeneralMatrix G = new GeneralMatrix(3,3);
        GeneralMatrix F = new GeneralMatrix(3,3);

        // Testing setter
        for (int i=1; i<=4; i++) {
            One.setIJ(i,i,1);
        }
        for (int i=1; i<=3; i++) {
            One.setIJ(i+1,i,1);
            One.setIJ(i,i+1,1);
        }

        // Testing Random Function
        T.random();
        R.random();
        G.random();
        F.random();

        // Testing toString method
        System.out.format("T = %n"+T);
        System.out.format("R = %n"+R);
        System.out.format("G = %n"+G);
        System.out.format("F = %n"+F);
        System.out.format("One = %n"+One);

        // Testing getter
        System.out.println("The (2,1) entry of T is "+T.getIJ(2,1));

        // Testing addition
        System.out.format("T + R %n"+T.add(R));
        System.out.format("T + G %n"+T.add(G));
        System.out.format("G + T %n"+G.add(T));
        System.out.format("F + G %n"+F.add(G));

        // Testing multiplication
        System.out.format("T * R %n"+T.multiply(R));
        System.out.format("T * G %n"+T.multiply(G));
        System.out.format("F * G %n"+F.multiply(G));
        System.out.format("T * 3 %n"+T.multiply(3));

        // Testing determinant
        System.out.println("det(One) = "+One.determinant());
        System.out.println("det(T) = "+T.determinant());

    }
}