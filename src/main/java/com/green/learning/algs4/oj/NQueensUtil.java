package com.green.learning.algs4.oj;

public class NQueensUtil
{
    /**
     * 打乱数组
     */
    public static void shuffle(int[] A)
    {
        for (int i = A.length - 1; i > 0; i--)
        {
            int j = (int) (Math.random() * (i + 1));
            swap(A, i, j);
        }
    }

    public static void swap(int[] A, int i, int j)
    {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    /**
     * 检测 N 个皇后之间是否存在冲突
     */
    public static boolean existConflicts(int[] cols)
    {
        for (int i = 1; i < cols.length; i++)
        {
            for (int j = 0; j < i; j++)
            {
                if (cols[i] == cols[j] || Math.abs(cols[i] - cols[j]) == i - j)
                    return true;
            }
        }
        return false;
    }

    public static void displayQueens(int[] cols)
    {
        StringBuilder sb = new StringBuilder();
        final int N = cols.length;

        sb.append("Queens' positions:\n");
        for (int i = 0; i < N; i++)
        {
            sb.append('(').append(i).append(" , ").append(cols[i]).append(")\n");
        }

        for (int i = 0; i < N; i++)
        {
            int j = 0;
            for (; j < cols[i]; j++)
                sb.append("* ");
            sb.append("Q ");
            for (; j < N - 1; j++)
                sb.append("* ");
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
}
