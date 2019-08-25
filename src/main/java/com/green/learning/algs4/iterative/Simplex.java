package com.green.learning.algs4.iterative;

/**
 * just a toy example to demonstrate the basic ideas of the simplex method solving
 * the linear programming problem.
 *
 * @see edu.princeton.cs.algs4.LinearProgramming
 */
public class Simplex
{
    private final int m;
    private final int n;
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
     * Solve linear system Ax = b to maximize the objective function c^Tx
     * @param A m by n matrix
     * @param b right hand side vector with length m
     * @param c cost vector with length n
     */
    public Simplex(double[][] A, double[] b, double[] c)
    {
        m = b.length;
        n = c.length;
        if (m == 0 || n == 0)
            throw new IllegalArgumentException("wrong dimension of vector b and c");
        if (A.length != m || A[0].length != n)
            throw new IllegalArgumentException("wrong dimension of matrix A");
        for(int i = 0; i < m; i++)
            if(b[i] < 0)
                throw new IllegalArgumentException("right hand side has negative entry " + b[i] + " at index " + i);
        
        N = m + n;
        a = new double[m + 1][N + 1];
        basicVars = new int[m];
        
        // copy matrix A
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = A[i][j];
        
        // initialize slack variables
        for (int i = 0; i < m; i++) {
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
            int col = pivotCol();
            if (col == -1) break;
            int row = pivotRow(col);
            if (row == -1)
                throw new IllegalArgumentException("The linear programming problem is infeasible!");
            
            pivot(col, row);
        }
    }
    
    /**
     * Use brand's rule to find out a pivot column
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
        basicVars[row] = col;
    }
    
    private double[] basicFeasibleSolution()
    {
        double[] b = new double[N];
        for (int i = 0; i < m; i++)
            b[basicVars[i]] = a[i][N];
        return b;
    }
    
    public double[] optimalSolution()
    {
        double[] x = new double[n];
        for(int i = 0; i < m; i++)
            if(basicVars[i] < n)
                x[basicVars[i]] = a[i][N];
        return x;
    }
    
    public double maxObjectiveValue()
    {
        return -a[m][N];
    }
}
