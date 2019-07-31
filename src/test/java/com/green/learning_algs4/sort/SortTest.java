package com.green.learning_algs4.sort;

import com.green.learning_algs4.util.ArrayUtils;
import com.green.learning_algs4.util.XTimer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class SortTest
{
    @RepeatedTest(1000)
    public void testSmallIntsSort()
    {
        Random rnd = new Random();
        final int N = 32;
        int L = 0, U = 127;
        Integer[] A = rnd.ints(N, L, U).boxed().toArray(Integer[]::new);
        System.out.println("before sorted: ");
        ArrayUtils.print(A, false);

//        MergeSortOpt.sort(A);
//        HeapSortX.sort(A);
//        HeapSort.sort(A);

//        ShellSort.sort(A);
//        ShellSort.sort2(A);
//        ShellSort.sort(A);
//        ShellSortX.sort2(A);
//        InsertionSortOpt.sort(A);
//        MergeSortBU.sort(A);
//        QuickX.sort(A);
        QuickSortOpt.sort(A);
//        QuickSort3WayOpt.sort(A);
    
        System.out.println("\nafter sorted: ");
        ArrayUtils.print(A, false);
        Assertions.assertTrue(ArrayUtils.isSorted(A));
    }
    
    @RepeatedTest(10)
    void testRndSort()
    {
        Random rnd = new Random();
        final int N = 1000;
        Integer[] A = rnd.ints(N).boxed().toArray(Integer[]::new);
        ArrayUtils.bufferedPrint(A);

//        MergeSortOpt.sort(A);
//        HeapSortX.sort(A);
//        HeapSort.sort(A);
//        ShellSort.sort(A);
        MergeSortBU.sort(A);
        
        ArrayUtils.UnBufferedPrint(A);
        Assertions.assertTrue(ArrayUtils.isSorted(A));
    }
    
    @ParameterizedTest
    @ValueSource(classes = {
            BubbleSort.class, SelectionSort.class, InsertionSort.class,
            QuickSort.class,
            MergeSort.class, MergeSortOpt.class,
            HeapSortX.class,
            ComparisonCountingSort.class
    })
    void testRndInts(Class c)
    {
        XTimer timer = new XTimer("test " + c.getSimpleName());
        final int N = 1000;
        Random random = new Random();
        timer.start("init rnd ints");
        Integer[] A = random.ints(N).boxed().toArray(Integer[]::new);
        timer.stop();

//        System.out.println("before sorted");
//        ArrayUtils.bufferedPrint(A);
        try
        {
            Method sortMethod = c.getDeclaredMethod("sort", Comparable[].class);
            Object o = c.newInstance();
            timer.start("sort");
            sortMethod.invoke(o, (Object) A);
            timer.stop();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e)
        {
            e.printStackTrace();
        }

//        System.out.println("after sorted");
//        ArrayUtils.bufferedPrint(A);
        
        timer.start("check if array sorted");
        Assertions.assertTrue(ArrayUtils.isSorted(A));
        timer.stop();
        
        System.out.println(timer);
    }
    
}
