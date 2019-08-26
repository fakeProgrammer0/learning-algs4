package com.green.learning.algs4.iterative;

/**
 * This is just a toy example to demonstrate the basic ideas of the simplex method.
 * It can solve linear programming systems in the form {Ax ≤ b, x ≥ 0 , b ≥ 0; max c•x},
 * where A is a m-by-n matrix, b is a vector with length m, c is the cost vector with
 * length n.
 * <p>
 * For simplicity, it assumes that A is of full rank and that b ≥ 0
 * so that x = 0 is a basic feasible solution.
 * <p>
 * This is a bare-bones implementation of the <em>simplex algorithm</em>.
 * It uses Bland's rule to determine the entering and leaving variables.
 * It is not suitable for use on large inputs. It is also not robust
 * in the presence of floating-point round-off error.
 *
 * @see edu.princeton.cs.algs4.LinearProgramming for detailed documents
 */
public class Simplex
{
    /**
     * number of constraints
     */
    private final int m;
    
    /**
     * number of original variables
     */
    private final int n;
    
    /**
     * number of all variables including slack variables
     */
    private final int N;
    
    /**
     * the simplex tableau
     */
    private final double[][] a;
    
    /**
     * basicVars[i] : the basic variable of row i
     */
    private final int[] basicVars;
    
    
    /**
     * Solve linear system Ax ≤ b to maximize the objective function c•x
     *
     * @param A m by n matrix
     * @param b right hand side vector with length m, b ≥ 0
     * @param c cost vector with length n
     * @throws IllegalArgumentException if some entry of b is less than 0 or parameters of wrong dimension
     */
    public Simplex(double[][] A, double[] b, double[] c)
    {
        m = b.length;
        n = c.length;
        if (m == 0 || n == 0)
            throw new IllegalArgumentException("wrong dimension of vector b and c");
        if (A.length != m || A[0].length != n)
            throw new IllegalArgumentException("wrong dimension of matrix A");
        for (int i = 0; i < m; i++)
            if (b[i] < 0)
                throw new IllegalArgumentException("right hand side has negative entry " + b[i] + " at index " + i);
        
        N = m + n;
        a = new double[m + 1][N + 1];
        basicVars = new int[m];
        
        // copy matrix A
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = A[i][j];
        
        // initialize slack variables
        for (int i = 0; i < m; i++)
        {
            a[i][i + n] = 1.0;
            basicVars[i] = i + n;
        }
        
        // copy vector b
        for (int i = 0; i < m; i++) a[i][N] = b[i];
        
        // transfer objective function to a row vector
        for (int j = 0; j < n; j++) a[m][j] = c[j];
        
        solve();
    }
    
    private void solve()
    {
        while (true)
        {
            // find entering column
            int col = pivotCol();
            if (col == -1) break;
            
            // find leaving row
            int row = pivotRow(col);
            if (row == -1)
                throw new IllegalArgumentException("The linear programming problem is infeasible!");
            
            pivot(col, row);
            
            basicVars[row] = col;
        }
    }
    
    /**
     * Use brand's rule to find out a pivot column
     *
     * @return pivot column index or -1 if no pivot column exists
     */
    private int pivotCol()
    {
        for (int j = 0; j < N; j++)
            if (a[m][j] > 0)
                return j;
        return -1;
    }
    
    /**
     * using minimum ratio rule and brand's rule to find out the pivot row
     *
     * @param col the pivot column index
     * @return pivot row index or -1 if no pivot row exists
     */
    private int pivotRow(int col)
    {
        int r = -1;
        double ratio = 0.0;
        for (int i = 0; i < m; i++)
        {
            if (a[i][col] > 0 &&
                    (r == -1 || a[i][N] / a[i][col] < ratio))
            {
                r = i;
                ratio = a[i][N] / a[i][col];
            }
        }
        return r;
    }
    
    /**
     * perform pivoting
     *
     * @param col pivot column index
     * @param row pivot row index
     */
    private void pivot(int col, int row)
    {
        // elimination but leaving pivot column col and pivot row unchanged
        for (int i = 0; i <= m; i++)
            if (i != row)
            {
                double ratio = a[i][col] / a[row][col];
                for (int j = 0; j <= N; j++)
                    if (j != col)
                        a[i][j] -= a[row][j] * ratio;
            }
        
        // zero out pivot column except the pivot
        for (int i = 0; i <= m; i++)
            if (i != row) a[i][col] = 0.0;
        
        // scale pivot row
        for (int j = 0; j <= N; j++)
            if (j != col) a[row][j] /= a[row][col];
        a[row][col] = 1.0;
    }
    
    private double[] basicFeasibleSolution()
    {
        double[] b = new double[N];
        for (int i = 0; i < m; i++)
            b[basicVars[i]] = a[i][N];
        return b;
    }
    
    /**
     * Returns the optimal primal solution to this linear program.
     *
     * @return the optimal primal solution to this linear program
     */
    public double[] primal()
    {
        double[] x = new double[n];
        for (int i = 0; i < m; i++)
            if (basicVars[i] < n)
                x[basicVars[i]] = a[i][N];
        return x;
    }
    
    public double maxObjectiveValue()
    {
        return -a[m][N];
    }
}
