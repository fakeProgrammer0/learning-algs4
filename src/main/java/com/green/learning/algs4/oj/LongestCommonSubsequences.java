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

        for (int i = 1; i <= M; i++)
        {
            for (int j = 1; j <= N; j++)
            {
                if (x.charAt(i - 1) == y.charAt(j - 1))
                    lens[i][j] = lens[i - 1][j - 1] + 1;
                else lens[i][j] = Math.max(lens[i - 1][j], lens[i][j - 1]);
            }
        }

        Stack<Character> commonSubsequence = new Stack<>();
        int u = M, v = N;
        while (u > 0 && v > 0)
        {
            if (lens[u][v] == lens[u - 1][v])
                u--;
            else if (lens[u][v] == lens[u][v - 1])
                v--;
            else
            {
                commonSubsequence.push(x.charAt(u - 1));
                u--;
                v--;
            }
        }

        StringBuilder result = new StringBuilder();
        while (!commonSubsequence.empty())
            result.append(commonSubsequence.pop());
        return result.toString();
    }

    public static String lcs_dp2(String x, String y)
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

    public static int lcs_len(String x, String y)
    {
        int short_len, long_len;
        String short_str, long_str;
        if(x.length() <= y.length())
        {
            short_str = x;
            short_len = x.length();
            long_str = y;
            long_len = y.length();
        }else {
            short_str = y;
            short_len = y.length();
            long_str = x;
            long_len = x.length();
        }

        int[][] lens = new int[2][short_len + 1];
        for(int i = 1; i <= long_len; i++)
        {
            int cur_row = i & 1; // cur_row = i % 2
            int last_row = cur_row ^ 1; // last_row = (i - 1) % 2
            for(int j = 1; j <= short_len; j++)
            {
                if (long_str.charAt(i - 1) == short_str.charAt(j - 1))
                    lens[cur_row][j] = lens[last_row][j - 1] + 1;
                else lens[cur_row][j] = Math.max(lens[last_row][j], lens[cur_row][j - 1]);
            }
        }

        return lens[long_len & 1][short_len];
    }

}