package com.green.learning_algs4.sort;

import com.green.learning_algs4.string.sort.MSDStringSortOpt;
import com.green.learning_algs4.string.sort.QuickSort3WayString;
import com.green.learning_algs4.util.ArrayUtils;
import com.green.learning_algs4.util.XTimer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringSortTest
{
    @RepeatedTest(5)
    @DisplayName("Compare String Sorts efficiencies")
    void test5(TestInfo testInfo)
    {
        final int R = Character.MAX_VALUE + 1;
        final int N = 1_000_000;
        final int MAX_LEN = 1000; // exclusive
        
        XTimer timer = new XTimer(testInfo.getDisplayName());
        
        timer.start("init random strings");
        String[] A = new String[N];
        for(int i = 0; i < N; i++)
        {
            int len = (int)(Math.random() * MAX_LEN); // [0, MAX_LEN)
            StringBuilder sb = new StringBuilder(len);
            for(int d = 0; d < len; d++)
                sb.append((char) (Math.random() * R));
            A[i] = sb.toString();
        }
        timer.stop();
        
        String[] B;
        
        B = Arrays.copyOf(A, N);
        timer.start("MSD Opt");
        MSDStringSortOpt.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
    
        B = Arrays.copyOf(A, N);
        timer.start("QuickSort 3 Way String");
        QuickSort3WayString.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
    
        B = Arrays.copyOf(A, N);
        timer.start("QuickSort 2 Way Opt");
        QuickSortOpt.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
    
        B = Arrays.copyOf(A, N);
        timer.start("QuickSort 3 Way Opt");
        QuickSort3WayOpt.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("JDK merge sort");
        Arrays.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
    
        B = Arrays.copyOf(A, N);
        timer.start("JDK parallel sort");
        Arrays.parallelSort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        System.out.println(timer);
    }
}
