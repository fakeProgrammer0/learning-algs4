package com.green.learning.algs4.oj;

public class LongestCommonSubsequencesRecursive
{
    private final String x;
    private final String y;
    private final int M;
    private final int N;
    private final int[][] lens;
    
    public LongestCommonSubsequencesRecursive(String x, String y)
    {
        this.x = x;
        this.y = y;
        this.M = x.length();
        this.N = y.length();
        this.lens = new int[M + 1][N + 1];
        lcs(M, N);
    }
    
    private int lcs(int i, int j)
    {
        if (i == 0 || j == 0) return 0;
        if (lens[i][j] > 0) return lens[i][j];
        
        if (x.charAt(i - 1) == y.charAt(j - 1))
        {
            lens[i][j] = lcs(i - 1, j - 1) + 1;
            System.out.print(x.charAt(i - 1));
        } else
        {
            lens[i][j] = Math.max(lcs(i - 1, j), lcs(i, j - 1));
        }
        return lens[i][j];
    }
}
