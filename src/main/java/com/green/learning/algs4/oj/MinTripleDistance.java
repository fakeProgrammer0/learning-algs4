package com.green.learning.algs4.oj;

/**
 * 三元组(a, b, c)的距离 d = |a – b| + |b - c| + |c - a|
 * 已知三个升序整数数组：A[], B[], C[]
 * 请在三个数组中各找一个元素 a ∈ A, b ∈ B, c ∈ C
 * 使得组成的三元组(a, b, c)距离最小
 */
public class MinTripleDistance
{
    public static int tripleDistance(int a, int b, int c)
    {
        return Math.abs(a - b) + Math.abs(b - c) + Math.abs(c - a);
    }
    
    public static int solve(int[] A, int[] B, int[] C)
    {
        final int n1 = A.length, n2 = B.length, n3 = C.length;
        int i = 0, j = 0, k = 0;
        int a = A[i], b = B[j], c = C[k];
        int d = tripleDistance(a, b, c);
        int minDist = d;
        while (true)
        {
            if (a < b)
            {
                if (a <= c)
                {
                    if(i + 1 == n1) break;
                    a = A[++i];
                } else
                {
                    if(k + 1 == n3)break;
                    c = C[++k];
                }
            } else if (b <= c)
            {
                if(j + 1 == n2) break;
                b = B[++j];
            } else
            {
                if(k + 1 == n3) break;
                c = C[++k];
            }
            
            d = tripleDistance(a, b, c);
            if (d < minDist)
                minDist = d;
        }
        
        d = tripleDistance(a, b, c);
        if (d < minDist)
            minDist = d;
        return minDist;
    }
    
    public static int bruteForce(int[] A, int[] B, int[] C)
    {
        int a = A[0], b = B[0], c = C[0];
        int d = tripleDistance(a, b, c);
        int minDist = d;
        for (int i = 0; i < A.length; i++)
        {
            a = A[i];
            for (int j = 0; j < B.length; j++)
            {
                b = B[j];
                for (int k = 0; k < C.length; k++)
                {
                    c = C[k];
                    d = tripleDistance(a, b, c);
                    if (d < minDist)
                        minDist = d;
                }
            }
        }
        return minDist;
    }
}
