package com.green.learning_algs4.sort;

import com.green.learning_algs4.tree.XPriorityQueue;

/**
 * heap sort using a priority queue
 * just a toy example
 */
public class HeapSortX
{
    public static <E extends Comparable<E>> void sort(E[] A)
    {
//        XPriorityQueue<E> heap = new XPriorityQueue<>(false, A);
//        for (int i = A.length - 1; i >= 0; i--)
//            A[i] = heap.dequeue();
        
        XPriorityQueue<E> heap = new XPriorityQueue<>(true, A);
        for (int i = 0; i < A.length; i++)
            A[i] = heap.dequeue();
    }
}
