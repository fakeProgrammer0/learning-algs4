package com.green.learning.algs4.sort;

import com.green.learning.algs4.set.XLinkedHashSet;
import com.green.learning.algs4.set.XSet;
import com.green.learning.algs4.string.sort.DistributionCountingSort;
import com.green.learning.algs4.string.sort.RadixSort;
import com.green.learning.algs4.util.ArrayUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @see java.util.Arrays
 */
public class Sorts
{
    
    public static <E extends Comparable<E>> void bubbleSort(E[] A)
    {
        BubbleSort.sort(A);
    }
    
    public static <E extends Comparable<E>> void selectionSort(E[] A)
    {
        SelectionSort.sort(A);
    }
    
    public static <E extends Comparable<E>> void insertionSort(E[] A)
    {
        InsertionSort.sort(A);
    }
    
    public static <E extends Comparable<E>> void comparisonCountingSort(E[] A)
    {
        ComparisonCountingSort.sort(A);
    }
    
    public static <E extends Comparable<E>> void shellSort(E[] A)
    {
        ShellSort.sort(A);
    }
    
    public static <E extends Comparable<E>> void quickSort(E[] A)
    {
        QuickSort.sort(A);
    }
    
    public static <E extends Comparable<E>> void mergeSort(E[] A)
    {
        MergeSort.sort(A);
    }
    
    public static <E extends Comparable<E>> void heapSort(E[] A)
    {
        HeapSort.sort(A);
    }
    
    // 桶排序，即binSort或bucketSort
    public static void distributionCountingSort(Integer[] A)
    {
        DistributionCountingSort.sort(A);
    }
    
    public static void radixSort(Integer[] A)
    {
        RadixSort.sort(A);
    }
    
    public static void myRadixSortUnsignedInt(Integer[] A)
    {
        RadixSort.sortUnsignedInt(A);
    }
    
    //桶排序 + 分布计数排序
    public static void radixSortUnsignedInt(Integer[] A)
    {
        RadixSort.radixSortUnsignedInt(A);
    }
    
    public static <E extends Comparable<E>> boolean isStable(E[] origin, E[] sorted)
    {
        if (origin.length != sorted.length)
            throw new IllegalArgumentException("The original array and the sorted" +
                    " array must be equal length");
        MergeSortOpt.sort(origin);
        for (int i = 0; i < origin.length; i++)
            if (origin[i] != sorted[i])
                return false;
        return true;
    }
    
    /**
     * Validate the {@code sorted} array is consistent with the {@code origin} array.
     *
     * @param origin the original array before sorting
     * @param sorted the sorted array after sorting
     * @param <E>    element type
     * @throws IllegalArgumentException if two arrays are not consistent
     */
    public static <E extends Comparable<E>> void validateSort(E[] origin, E[] sorted)
    {
        if (origin.length != sorted.length)
            throw new IllegalArgumentException("two arrays must be equal length");
        
        if (!ArrayUtils.isSorted(sorted))
            throw new IllegalArgumentException("The \"sorted\" array is not sorted");
        
        XSet<E> items = new XLinkedHashSet<>(origin.length);
        for (E e : origin)
            items.add(e);
        for (E e : sorted)
            if (!items.contains(e))
                throw new IllegalArgumentException("The \"origin\" array contains <"
                        + e + "> while the \"sorted\" array doesn't");
    }
    
    /**
     * Test whether a sorting method is stable or not
     *
     * @param sortMethod a generic sorting method taking a Comparable array as
     *                   argument, which may be declared as:
     *                   {@code public static <E extends Comparable<E>> void sort(E[] A)}
     * @param epoches    the number of sorting tests to be run
     * @param N          the number of elements to be sorted
     * @return false if the sorting method isn't stable
     * true if the sorting method is probably stable
     * @throws IllegalArgumentException if the sorting method cannot correctly sort
     *                                  the elements
     */
    public static boolean isProbablyStableSort(Method sortMethod, int epoches, int N)
    {
        int bound = N / 4; // generate duplicate integers
        if (bound < 0) bound = 1;
        
        for (int epoch = 0; epoch < epoches; epoch++)
        {
            SortItem[] origin = SortItem.rndSamples(N, bound);
            SortItem[] sorted = Arrays.copyOf(origin, N);
            
            try
            {
                sortMethod.invoke(null, (Object) sorted);
            } catch (IllegalAccessException | InvocationTargetException ex)
            {
                ex.printStackTrace();
            }
            
            validateSort(origin, sorted);

            // use stable sorting method to sort the original array
//            MergeSortOpt.sort(origin);
            Arrays.sort(origin);
            
            for (int i = 0; i < origin.length; i++)
            {
                if (origin[i].getKey() != sorted[i].getKey())
                    throw new IllegalArgumentException("Incorrect sorting method: " + sortMethod.getName());
                if (origin[i] != sorted[i])
                    return false;
            }
        }
        
        return true;
    }
}
