package com.green.learning_algs4.sort;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TopKTest
{
    
    @RepeatedTest(1_000)
    void tesTopK()
    {
        Random rnd = new Random();
        final int N = 100;
        int lowerBound = 0, upperBound = 1000;
        Integer[] A = rnd.ints(N, lowerBound, upperBound).boxed().toArray(Integer[]::new);
        
        int k = 1 + (int) (Math.random() * N);
        Integer topKVal = TopKQuickSelect.topK(A, k);
        
        int le = 0, eq = 0;
        for (Integer i : A)
        {
            int cmp = i.compareTo(topKVal);
            if (cmp < 0) le++;
            else if (cmp == 0) eq++;
        }
        assertTrue(le + 1 <= k && k <= le + eq);
    }
    
}