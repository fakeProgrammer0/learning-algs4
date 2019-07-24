package com.green.learning_algs4.tree;

import com.green.learning_algs4.list.XArrayList;
import com.green.learning_algs4.list.XList;
import com.green.learning_algs4.util.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Random;

public class IndexedPriorityQueueTest
{
    @Test
    public void simpleTest()
    {
        Character[] chars = {'A', 'S', 'O', 'R', 'T', 'I', 'N', 'G'};
        final int N = chars.length;
        IndexedPriorityQueue<Character> pq = new IndexedPriorityQueue<>(chars);
        System.out.println(pq);
        
        Assertions.assertTrue(pq.isHeap());
        Assertions.assertEquals(N, pq.size());
        Assertions.assertFalse(pq.isEmpty());
        
        for(int i = 0; i < N; i++)
        {
            Assertions.assertTrue(pq.containsIndex(i));
            Assertions.assertEquals(chars[i], pq.getKey(i));
        }
        Assertions.assertEquals('A', pq.peek());
    
        System.out.println(pq.keys());
        pq.set(3, 'B');
        Assertions.assertTrue(pq.isHeap());
        Assertions.assertEquals('B', pq.getKey(3));
        System.out.println(pq.keys());
        
        Assertions.assertEquals('T',pq.getKey(4));
        Assertions.assertThrows(IllegalArgumentException.class, ()-> pq.increasePriority(4, null));
        Assertions.assertThrows(IllegalArgumentException.class, ()-> pq.increasePriority(4, 'Y'));
        Assertions.assertThrows(IllegalArgumentException.class, ()-> pq.increasePriority(4, 'T'));
        pq.increasePriority(2, 'C');
        System.out.println(pq.keys());
        
        Assertions.assertEquals('A', pq.getKey(0));
        Assertions.assertThrows(IllegalArgumentException.class, ()-> pq.decreasePriority(0, null));
        Assertions.assertThrows(IllegalArgumentException.class, ()-> pq.decreasePriority(0, (char)('A' - 1)));
        Assertions.assertThrows(IllegalArgumentException.class, ()-> pq.decreasePriority(0, 'A'));
        pq.decreasePriority(0, 'Z');
        Assertions.assertTrue(pq.isHeap());
        
        System.out.println(pq);
        
        pq.removeKey(5);
        Assertions.assertTrue(pq.isHeap());
        Assertions.assertThrows(NoSuchElementException.class, ()->pq.getKey(5));
        System.out.println(pq);
        
        while (!pq.isEmpty())
            System.out.print(pq.dequeue() + " ");
        Assertions.assertEquals(0, pq.size());
        Assertions.assertThrows(NoSuchElementException.class, pq::peek);
        Assertions.assertThrows(NoSuchElementException.class, pq::dequeue);
        Assertions.assertThrows(NoSuchElementException.class, pq::iterator);
    }
    
    @RepeatedTest(10)
    public void intTest()
    {
        final int N = 1000000;
        final int bound = 1000_000;
        Random random = new Random();
        IndexedPriorityQueue<Integer> pq = new IndexedPriorityQueue<>(N);
        XList<Integer> list = new XArrayList<>();
        for(int i = 0; i < N; i++)
            pq.set(i, random.nextInt(bound));
        while (!pq.isEmpty())
            list.append(pq.dequeue());
        Assertions.assertTrue(ArrayUtils.isSorted(list.toArray(new Integer[0])));
    }
}
