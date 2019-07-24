package com.green.learning_algs4.sort;

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
    
    public static <E extends Comparable<E>> void shellSort(E[] A) {ShellSort.sort(A);}
    
    public static <E extends Comparable<E>> void quickSort(E[] A) {QuickSort.sort(A);}
    
    public static <E extends Comparable<E>> void mergeSort(E[] A) {MergeSort.sort(A);}
    
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
    
}
