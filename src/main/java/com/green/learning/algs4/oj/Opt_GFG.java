package com.green.learning.algs4.oj;

/*package whatever //do not write package name here */

import java.util.*;
import java.lang.*;
import java.io.*;

/**
 * https://practice.geeksforgeeks.org/problems/optimal-binary-search-tree/0
 */
public class Opt_GFG
{
    public static class Element implements Comparable<Element>
    {
        private int key;
        private int freq;

        public Element()
        {
        }

        public Element(int key, int freq)
        {
            this.key = key;
            this.freq = freq;
        }

        public int compareTo(Element e)
        {
            if (this.key < e.key) return -1;
            if (this.key > e.key) return 1;
            return Integer.compare(this.freq, e.freq);
        }
    }

    public static int optBSTAvgSearchLength(int[] A, int N)
    {
        int[][] M = new int[N + 2][N + 2];
        int[] W = new int[N + 1];
        for (int i = 1; i <= N; i++)
        {
            W[i] = W[i - 1] + A[i];
            M[i][i] = A[i];
        }

        for (int r = 2; r <= N; r++)
        {
            for (int i = 1, j = i + r - 1; j <= N; i++, j++)
            {
                M[i][j] = M[i][i - 1] + M[i + 1][j];
                for (int k = i + 1; k <= j; k++)
                {
                    M[i][j] = Math.min(M[i][k - 1] + M[k + 1][j], M[i][j]);
                }
                M[i][j] += W[j] - W[i - 1];
            }
        }
        return M[1][N];
    }

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        for (int t = 0; t < T; t++)
        {
            int N = in.nextInt();
            Element[] elements = new Element[N];
            for (int i = 0; i < N; i++)
            {
                elements[i] = new Element();
                elements[i].key = in.nextInt();
            }
            for (int i = 0; i < N; i++)
                elements[i].freq = in.nextInt();

            Arrays.sort(elements);
            int[] A = new int[N + 1];
            for (int i = 1; i <= N; i++)
                A[i] = elements[i - 1].freq;

            System.out.println(optBSTAvgSearchLength(A, N));
        }
    }
}
