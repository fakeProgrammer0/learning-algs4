package com.green.learning.algs4.sort;

import com.green.learning.algs4.util.ArrayUtils;
import com.green.learning.algs4.util.XTimer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

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
    
    @Test
    void testIntegerEquality()
    {
        assertNotSame(new Integer(1000), new Integer(1000));
        assertNotSame(Integer.valueOf(1000), Integer.valueOf(1000));
    
        assertNotSame(new Integer(1), new Integer(1));
        assertSame(Integer.valueOf(1), Integer.valueOf(1));
    }
    
    private static Method getSortMethod(Class<?> clazz) throws NoSuchMethodException
    {
        return clazz.getDeclaredMethod("sort", Comparable[].class);
    }
    
    @ParameterizedTest
    @ValueSource(classes = {
            BubbleSort.class,
//            SelectionSort.class, // selection sort is not stable
            InsertionSort.class,
            InsertionSortOpt.class,
            BinaryInsertionSort.class,
            ComparisonCountingSort.class,
            
//            HeapSort.class, // heap sort is not stable
            
            MergeSort.class,
//            MergeSortX.class, // unstable
            MergeSortOpt.class,
            MergeSortBU.class,
    })
    void testStableSorts(Class<?> sortClazz) throws NoSuchMethodException
    {
        final int N = 1_000;
//        final int N = 8;
        final int epoches = 1_000;
        
        assertTrue(Sorts.isProbablyStableSort(
                getSortMethod(sortClazz), epoches, N));
    }
    
    @ParameterizedTest
    @ValueSource(classes = {
            SelectionSort.class,
            ShellSort.class,
            ShellSortX.class,
            
            edu.princeton.cs.algs4.Heap.class,
            HeapSort.class,
            
            QuickSort.class,
            QuickSortOpt.class,
            QuickSort3Way.class,
            QuickSort3WayOpt.class
    })
    void testUnStableSorts(Class<?> sortClazz) throws NoSuchMethodException
    {
        final int N = 1_000;
        final int epoches = 1_000;
        
        assertFalse(Sorts.isProbablyStableSort(
                getSortMethod(sortClazz), epoches, N));
    }
    
    @Test
    void testIsStable1()
    {
        final int N = 1_000;
        SortItem[] A = SortItem.rndSamples(N);
        SortItem[] B = Arrays.copyOf(A, N);
        
        for (int i = 0; i < N; i++)
            assertSame(A[i], B[i]);
        
        for (int i = 0; i < N; i++)
        {
            int sameRef = 0;
            for (int j = 0; j < N; j++)
                if (A[i].equals(B[j]) && A[i] == B[j])
                    sameRef++;
            assertEquals(1, sameRef);
        }
    }
    
    @Test
    void testIsStable2()
    {
        final int N = 1_000;
        final int X = 1;
        SortItem[] A = new SortItem[N];
        for (int i = 0; i < N; i++)
            A[i] = new SortItem(X);
        SortItem[] B = Arrays.copyOf(A, N);
        
        for (int i = 0; i < N; i++)
            assertTrue(A[i] == B[i]);
        
        for (int i = 0; i < N; i++)
        {
            int sameRef = 0;
            for (int j = 0; j < N; j++)
                if (A[i].equals(B[j]) && A[i] == B[j])
                    sameRef++;
            assertEquals(1, sameRef);
        }
    }
    
}
