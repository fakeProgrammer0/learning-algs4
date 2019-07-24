package com.green.learning_algs4.list;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class XLinkedListTest
{
    
    @Test
    @DisplayName("test simple functionality")
    public void simpleTest()
    {
        final int N = 16;
        XLinkedList<Integer> list = new XLinkedList<>();
//        XLinkedListX<Integer> list = new XLinkedListX<>();
        for (int i = 0; i < N; i++)
        {
            if (StdRandom.bernoulli(0.6))
                list.append(i);
            else if (StdRandom.bernoulli())
                list.append(null);
            else list.append(-1);
        }
        
        Iterator<Integer> iterator = list.iterator();
        Assertions.assertThrows(UnsupportedOperationException.class, iterator::remove);
        int count = 0;
        while (iterator.hasNext())
        {
            Integer i = iterator.next();
            Assertions.assertTrue(i == null || i == count || i == -1);
            count++;
        }
        
        System.out.println(list);
        list.addFirst(-2);
        Assertions.assertEquals(-2, list.getFirst());
        list.addLast(999);
        Assertions.assertEquals(999, list.getLast());
        list.append(100);
        Assertions.assertNotEquals(999, list.getLast());
    
        System.out.println(list);
        list.removeFirst();
        list.removeLast();
        System.out.println(list);
        
        list.insert(5, 100);
        Assertions.assertEquals(100, list.get(5));
        System.out.println(list);
        list.insert(8, 100);
        Assertions.assertEquals(100, list.get(8));
        System.out.println(list);
        
        list.insert(list.size(), 99);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.insert(N * 2, 1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.insert(-1, 1));
        System.out.println(list);
        
        list.set(3, 33);
        Assertions.assertEquals(33, list.get(3));
        list.remove(0);
        System.out.println(list);
        System.out.println("index of 100: " + list.indexOf(100));
        System.out.println("last index of 100: " + list.lastIndexOf(100));
        list.removeAll(100);
        Assertions.assertFalse(list.contains(100));
        System.out.println(list);
        
        list.removeFirst(null);
        System.out.println(list);
        
        list.removeAll(null);
        Assertions.assertFalse(list.contains(null));
        System.out.println(list);
        
        list.removeAll(-1);
        System.out.println(list);
        
        list.clear();
        Assertions.assertTrue(list.isEmpty());
        list.append(100);
        list.addLast(null);
        Assertions.assertEquals(0, list.lastIndexOf(100));
        Assertions.assertEquals(0, list.indexOf(100));
    }
    
    @Test
    public void simpleTest2()
    {
        XLinkedList<Integer> list = new XLinkedList<>();
        final int N = 32;
        for(int i = 0; i < N; i++)
            list.append(i);
        list.set(StdRandom.uniform(N), null);
        list.set(StdRandom.uniform(N), null);
        System.out.println(list);
        list.removeFirst(30);
        System.out.println(list);
        list.removeAll(29);
        list.removeAll(null);
        System.out.println(list);
    }
}
