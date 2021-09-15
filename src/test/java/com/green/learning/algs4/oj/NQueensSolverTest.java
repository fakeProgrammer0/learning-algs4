package com.green.learning.algs4.oj;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NQueensSolverTest
{
    @RepeatedTest(5)
    void testCorrectness()
    {
        int[] QueensCount = {4, 8, 16};
        long maxSearchSecs = 1;
        final int maxTries = 3;
        System.out.println("Test correctness of the algorithm NQueensSolver\n");
        for (int i = 0; i < QueensCount.length; i++)
        {
            int N = QueensCount[i];
            NQueensSolver solver = new NQueensSolver(N);

            for (int t = 0; t < maxTries; t++)
            {
                int[] cols = solver.solve(maxSearchSecs, true);
                if (cols != null)
                {
                    assertFalse(NQueensUtil.existConflicts(cols), "The solution is wrong!");
                    break;
                }
            }
        }
    }

    @Test
    void testN()
    {
        int[] QueensCount = {100, 500, 1000, 2000, 5000, 10000, 30000, 50000};
        long maxSearchSecs = 60 * 30;
        final int maxTries = 3;
        for (int i = 0; i < QueensCount.length; i++)
        {
            int N = QueensCount[i];

//            NQueensSolver_v1 solver = new NQueensSolver_v1(N);
            NQueensSolver solver = new NQueensSolver(N);

            for (int t = 0; t < maxTries; t++)
            {
                int[] cols = solver.solve(maxSearchSecs, false);
                if (cols != null)
                {
                    assertFalse(NQueensUtil.existConflicts(cols), "The solution is wrong!");
                    break;
                }
            }
        }
    }

    @Test
    void testMaxN()
    {
        int[] QueensCount = {50000, 100000, 500000, 1000000};
        long maxSearchSecs = 60 * 30;
        final int maxTries = 3;
        for (int i = 0; i < QueensCount.length; i++)
        {
            int N = QueensCount[i];

//            NQueensSolver_v1 solver = new NQueensSolver_v1(N);
            NQueensSolver solver = new NQueensSolver(N);

            for (int t = 0; t < maxTries; t++)
            {
                int[] cols = solver.solve(maxSearchSecs, false);
                if (cols != null)
                {
                    assertFalse(NQueensUtil.existConflicts(cols), "The solution is wrong!");
                    break;
                }
            }
        }
    }

    @Test
    void testMaxN_2()
    {
        int[] QueensCount = {50000, 100000, 200000, 250000, 300000, 350000, 400000, 500000};
        long maxSearchSecs = 60 * 30;
        final int maxTries = 3;
        for (int i = 0; i < QueensCount.length; i++)
        {
            int N = QueensCount[i];
            NQueensSolver solver = new NQueensSolver(N);

            for (int t = 0; t < maxTries; t++)
            {
                int[] cols = solver.solve(maxSearchSecs, false);
                if (cols != null)
                {
                    assertFalse(NQueensUtil.existConflicts(cols), "The solution is wrong!");
                    break;
                }
            }
        }
    }
}