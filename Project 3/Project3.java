/*
 * PROJECT III: Project3.java
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

public class Project3 {
    /**
     * Calculates the variance of the distribution defined by the determinant
     * of a random matrix. See the formulation for a detailed description.
     *
     * @param matrix      The matrix object that will be filled with random
     *                    samples.
     * @param nSamp       The number of samples to generate when calculating 
     *                    the variance. 
     * @return            The variance of the distribution.
     */
    public static double matVariance(Matrix matrix, int nSamp) {
        
        double total_1 = 0;
        double total_2 = 0;
        double var = 0;

        // If the Matrix object is a GeneralMatrix we generate nSamp GeneralMatrices
        if (matrix instanceof GeneralMatrix){
            for (int k=1; k<=nSamp; k++) {
                GeneralMatrix X = new GeneralMatrix(matrix.iDim, matrix.jDim);
                X.random();
                total_1 += X.determinant() * X.determinant();
                total_2 += X.determinant();
            }
        }

        // If the Matrix object is a TriMatrix we generate nSamp TriMatrices
        if (matrix instanceof TriMatrix) {
            for (int k=1; k<=nSamp; k++) {
                TriMatrix X = new TriMatrix(matrix.iDim);
                X.random();
                total_1 += X.determinant() * X.determinant();
                total_2 += X.determinant();
            }
        }

        // calculating and returning the variance of the determinant
        var = (1.0/nSamp)*total_1 - ((1.0/nSamp)*total_2)*((1.0/nSamp)*total_2);
    
        return var;
    }
    
    /**
     * This function calculates the variances of matrices for matrices
     * of size 2 <= n <= 50 and prints the results to the output.
     */
    public static void main(String[] args) {

        for (int n=2; n<=50; n++) {

            // Creating nxn GeneralMatrix for 2 <= n <= 50
            GeneralMatrix G = new GeneralMatrix(n, n);

            // Creating nxn TriMatrix for 2 <= n <= 50
            TriMatrix T = new TriMatrix(n);

            // Generating and calculating the variance of 20,000 nxn GeneralMatrice
            double varGen = matVariance(G, 20000);

            // Generating and calculating the variance of 200,000 nxn TriMatrice
            double varTri = matVariance(T, 200000);

            // Printing out the value of n and the variance of both the TriMatrices and the GeneralMatrices
            StringBuilder sb = new StringBuilder();
            sb.append(n).append("    ").append(String.format("%.15E,    %.15E",varGen, varTri));
            System.out.println(sb);
        }
    }
}