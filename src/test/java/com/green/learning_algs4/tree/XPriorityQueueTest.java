package com.green.learning_algs4.tree;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;


public class XPriorityQueueTest
{
    @Test
    public void simpleTest()
    {
        int N = 26;
        char[] letters = new char[N];
        for(int i = 0; i < N; i++)
            letters[i] =(char)('A' + i);
        StdRandom.shuffle(letters);
        XPriorityQueue<Character> queue = new XPriorityQueue<>();
        
        Assertions.assertThrows(IllegalArgumentException.class, ()->queue.enqueue(null));
        queue.setAutoShrink(true);
        
        for(char letter: letters)
        {
            queue.enqueue(letter);
            System.out.print(letter + " ");
        }
        
        System.out.println();
        int code_id = 0;
        while (!queue.isEmpty())
        {
            char letter = queue.dequeue();
            Assertions.assertEquals(letter, (char)('A' + code_id));
            code_id++;
            System.out.print(letter + " ");
        }
    }
    
    @Test
    public void testIterator()
    {
        XPriorityQueue<Integer> queue = new XPriorityQueue<>(false);
        int N = 1000;
        for(int i = 0; i < N; i++)
            queue.enqueue(i);
        
        for(int i: queue)
            System.out.print(i + " ");
    
        Iterator<Integer> iterator = queue.iterator();
        Integer i = iterator.next();
        Integer j = queue.elementWithHighestPriority();
        queue.dequeue();
        Assertions.assertTrue(i == j);
    
        Assertions.assertEquals(N - 2, queue.dequeue());
        Assertions.assertEquals(N - 2, iterator.next());
        Assertions.assertEquals(N - 3, iterator.next());
        Assertions.assertEquals(N - 3, queue.dequeue());
    }
    
    @DisplayName("init using array")
    @Test
    public void arrayInit()
    {
        int N = 100;
        Integer[] A = new Integer[N];
        for(int i =0;i<N;i++)
            A[i] = i;
        StdRandom.shuffle(A);
        XPriorityQueue<Integer> pq = new XPriorityQueue<>(true, A);
        while (!pq.isEmpty())
            System.out.print(pq.dequeue() + " ");
    }
}
