package com.green.learning_algs4.util;

import com.green.learning_algs4.sort.MergeSortOpt;

import java.util.Random;

public class ArrayUtils
{
    /**
     * Knuth Shuffle
     *
     * @param A the array to shuffle
     * @throws IllegalArgumentException if {@code A} is {@code null}
     * @see edu.princeton.cs.algs4.StdRandom
     * Rearranges the elements of the specified array in uniformly random order.
     */
    public static void shuffle(Object[] A)
    {
        final int N = A.length;
        for (int i = N; i > 1; )
        {
            int j = (int) (Math.random() * i); // j ∈ [0, i - 1]
            swap(A, --i, j);
        }
    }
    
    public static void swap(int[] A, int i, int j)
    {
        if (i != j)
        {
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
    }
    
    public static <E> void swap(E[] A, int i, int j)
    {
        if (i != j)
        {
            E temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
    }
    
    @Deprecated
    public static <E> void UnBufferedPrint(E[] A)
    {
        print(A, false);
    }
    
    @Deprecated
    public static <E> void bufferedPrint(E[] A)
    {
        print(A, true);
    }
    
    public static <E> void print(E[] A, boolean buffered)
    {
        final String sep = ", ";
        
        if (buffered)
        {
            StringBuilder sb = new StringBuilder("[");
            for (E x : A)
                sb.append(x).append(sep);
            int idx = sb.lastIndexOf(sep);
            if (idx >= 0 && idx + sep.length() == sb.length())
                sb.delete(idx, sb.length());
            sb.append("]");
            System.out.println(sb.toString());
        } else
        {
            System.out.print("[");
            for (int i = 0; i < A.length - 1; i++)
            {
                System.out.print(A[i]);
                System.out.print(sep);
            }
            System.out.print(A[A.length - 1]);
            System.out.println("]");
        }
    }
    
    public static <E> void print(E[] A, boolean buffered, int elementsPerLine)
    {
        final String sep = ", ";
        final String lineSep = System.lineSeparator();
        
        if (buffered)
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < A.length - 1; i++)
            {
                sb.append(A[i]).append(sep);
                if ((i + 1) % elementsPerLine == 0)
                    sb.append(lineSep);
            }
            sb.append(A[A.length - 1]).append(lineSep);
            System.out.println(sb.toString());
        } else
        {
            for (int i = 0; i < A.length - 1; i++)
            {
                System.out.print(A[i]);
                System.out.print(sep);
                if ((i + 1) % elementsPerLine == 0)
                    System.out.println();
            }
            System.out.println(A[A.length - 1]);
        }
    }
    
    public static <E extends Comparable<E>> boolean isSorted(E[] A, int low, int high)
    {
        for (int i = low; i < high; i++)
            if (A[i].compareTo(A[i + 1]) > 0)
                return false;
        return true;
    }
    
    public static <E extends Comparable<E>> boolean isSorted(E[] A)
    {
        return isSorted(A, 0, A.length - 1);
    }
    
    public static boolean isSorted(char[] A, int low, int high)
    {
        for (int i = low; i < high; i++)
            if (A[i] > A[i + 1])
                return false;
        return true;
    }
    
    public static boolean isSorted(char[] A)
    {
        return isSorted(A, 0, A.length - 1);
    }
    
    public static void printChars(char[] A)
    {
        for (int i = 0; i < A.length; i++)
            System.out.printf("%4X ", (int) A[i]);
        System.out.println();
    }
    
    /**
     * Used by quicksort
     *
     * @return the index of the median element among A[i], A[j] and A[k]
     */
    public static <E extends Comparable<E>> int medianIndex(E[] A, int i, int j, int k)
    {
//        int pivot;
//        if (A[j].compareTo(A[i]) < 0 && A[i].compareTo(A[k]) <= 0
//                || A[k].compareTo(A[i]) < 0 && A[i].compareTo(A[j]) <= 0)
//            pivot = i;
//        else if (A[i].compareTo(A[j]) < 0 && A[j].compareTo(A[k]) <= 0
//                || A[k].compareTo(A[j]) < 0 && A[j].compareTo(A[i]) <= 0)
//            pivot = j;
//        else pivot = k;
//        return pivot;
        
        return (A[i].compareTo(A[j]) <= 0) ?
                (A[j].compareTo(A[k]) <= 0 ? j : A[i].compareTo(A[k]) <= 0 ? k : i) :
                (A[j].compareTo(A[k]) > 0 ? j : A[i].compareTo(A[k]) <= 0 ? i : k);
    }
    
    public static void checkNull(Object[] A)
    {
        if (A == null)
            throw new IllegalArgumentException();
        for (Object o : A)
            if (o == null)
                throw new IllegalArgumentException();
    }
    
    public static Integer[] rndInts(int N)
    {
        Random rnd = new Random();
        return rnd.ints(N).boxed().toArray(Integer[]::new);
    }
    
    public static Integer[] rndInts(int N, int L, int U)
    {
        Random rnd = new Random();
        return rnd.ints(N, L, U).boxed().toArray(Integer[]::new);
    }
    
    
}
