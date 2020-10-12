package com.green.learning.algs4.oj;

import java.util.Stack;

/**
 * 输出两个字符串的最长公共子序列
 * 要求1:不使用辅助数组
 * 要求2(选作): 不使用m*n数组，使用2*n数组
 */
public class LongestCommonSubsequences
{
    private static enum EXTEND_ORIENT
    {FROM_X, FROM_Y, FROM_BOTH}
    
    ;
    
    public static String lcs_dp1(String x, String y)
    {
        final int M = x.length();
        final int N = y.length();
        int[][] lens = new int[M + 1][N + 1];
        EXTEND_ORIENT[][] orients = new EXTEND_ORIENT[M + 1][N + 1];
        
        for (int i = 1; i <= M; i++)
        {
            for (int j = 1; j <= N; j++)
            {
                if (x.charAt(i - 1) == y.charAt(j - 1))
                {
                    lens[i][j] = lens[i - 1][j - 1] + 1;
                    orients[i][j] = EXTEND_ORIENT.FROM_BOTH;
                } else if (lens[i - 1][j] >= lens[i][j - 1])
                {
                    lens[i][j] = lens[i - 1][j];
                    orients[i][j] = EXTEND_ORIENT.FROM_X;
                } else
                {
                    lens[i][j] = lens[i][j - 1];
                    orients[i][j] = EXTEND_ORIENT.FROM_Y;
                }
            }
        }
        
        Stack<Character> commonSubsequence = new Stack<>();
        int u = M, v = N;
        while (u > 0 && v > 0)
        {
            switch (orients[u][v])
            {
                case FROM_BOTH:
                {
                    commonSubsequence.push(x.charAt(u - 1));
                    u--;
                    v--;
                    break;
                }
                case FROM_X:
                {
                    u--;
                    break;
                }
                case FROM_Y:
                {
                    v--;
                    break;
                }
            }
        }
        
        StringBuilder result = new StringBuilder();
        while (!commonSubsequence.empty())
        {
            result.append(commonSubsequence.pop());
        }
        return result.toString();
    }
    
    private static int[][] lens_r;
    
    public  static void lcs_recursive(String x, String y)
    {
    
    }
    
}