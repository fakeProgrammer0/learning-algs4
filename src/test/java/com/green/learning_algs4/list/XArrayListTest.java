package com.green.learning_algs4.list;

import com.green.learning_algs4.util.TimingExtension;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.IntStream;

@ExtendWith(TimingExtension.class)
public class XArrayListTest
{
    @Test
    void testCapacityConstructor()
    {
        assertThrows(IllegalArgumentException.class, ()-> new XArrayList<String>(-1));
        assertThrows(IllegalArgumentException.class, ()-> new XArrayList<String>(0));
        assertDoesNotThrow(()-> new XArrayList<String>(200));
        
        final int capacity = 20;
        XArrayList<Integer> list = new XArrayList<>(capacity);
        assertEquals(capacity, list.getCapacity());
        assertEquals(0, list.size());
    }
    
    @Test
    void testElementConstructor1()
    {
        final int N = 200;
        Integer[] elements = IntStream.range(0, N).boxed().toArray(Integer[]::new);
        XArrayList<Integer> list = new XArrayList<>(elements);
        assertEquals(N, list.getCapacity());
        for(int i = 0; i < N; i++)
            assertEquals(i, list.get(i));
        Arrays.fill(elements, null);
        assertFalse(list.contains(null));
        System.out.println(list);
    }
    
    @Test
    void testElementConstructor2()
    {
        assertThrows(IllegalArgumentException.class, ()-> new XArrayList<>((Integer[])null));
        
        Integer[] elements = new Integer[0];
        XArrayList<Integer> list = new XArrayList<>(elements);
        list.append(1);
        assertEquals(1,list.get(0));
    }
    
    @Test
    void checkExpansionShrink()
    {
        final int N = 200;
        XArrayList<Integer> list = new XArrayList<>();
        int capacity = list.getCapacity();
        int from = 0, to;
        for (int i = 0; i < N; i++)
        {
            if (StdRandom.bernoulli(0.2))
                list.append(null);
            else list.append(i);
            
            System.out.printf("%d: capacity = %d\n", i, list.getCapacity());
            
            if (i > 0 && capacity != list.getCapacity())
            {
                to = i - 1;
                System.out.printf("from %d to %d, capacity = %d\n", from, to, capacity);
                capacity = list.getCapacity();
                from = i;
            }
        }
        
        list.trimToSize();
        assertEquals(list.size(), list.getCapacity());
        
        list.setAutoShrink(true);
        assertTrue(list.isAutoShrink());
        
        while (!list.isEmpty())
        {
            list.remove(StdRandom.uniform(list.size()));
            System.out.printf("size = %d, capacity = %d\n", list.size(), list.getCapacity());
        }
    }
    
    @Test
    @DisplayName("test simple functionality")
    public void simpleTest()
    {
        final int N = 32;
        XArrayList<Integer> list = new XArrayList<>();
        for (int i = 0; i < N; i++)
        {
            if (StdRandom.bernoulli(0.6))
                list.append(i);
            else if (StdRandom.bernoulli())
                list.append(-1);
            else list.append(null);
        }
        
        Iterator<Integer> iterator = list.iterator();
        
        assertThrows(UnsupportedOperationException.class, iterator::remove);
        int count = 0;
        while (iterator.hasNext())
        {
            Integer i = iterator.next();
            assertTrue(i == null || i == count || i == -1);
            count++;
        }
        
        System.out.println(list);
        list.insert(5, 100);
        assertEquals(100, list.get(5));
        System.out.println(list);
        list.insert(8, 100);
        assertEquals(100, list.get(8));
        System.out.println(list);
        
        list.insert(list.size(), 99);
        assertThrows(IndexOutOfBoundsException.class, () -> list.insert(N * 2, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.insert(-1, 1));
        System.out.println(list);
        
        list.set(3, 33);
        assertEquals(33, list.get(3));
        list.remove(0);
        System.out.println(list);
        System.out.println("index of 100: " + list.indexOf(100));
        System.out.println("last index of 100: " + list.lastIndexOf(100));
        list.removeAll(100);
        assertFalse(list.contains(100));
        System.out.println(list);
        
        list.removeFirst(null);
        System.out.println(list);
        
        list.removeAll(null);
        assertFalse(list.contains(null));
        System.out.println(list);
        
        list.removeAll(-1);
        System.out.println(list);
        
        list.clear();
        assertTrue(list.isEmpty());
        list.append(100);
        assertEquals(0, list.lastIndexOf(100));
        assertEquals(0, list.indexOf(100));
    }
    
    @Test
    public void testRemove()
    {
        final int N = 16;
        XArrayList<Integer> list = new XArrayList<>();
        for (int i = 0; i < N; i++)
            if (i % 2 == 0)
                list.append(0);
            else list.append(1);
        
        System.out.println(list);
        list.removeAll(1);
        System.out.println(list);
        for (int i = 0; i < list.size(); i++)
            assertEquals(0, list.get(i));
        list.removeAll(0);
        assertEquals(0, list.size());
        System.out.println(list);
    }
    
    private static Random random = new Random();
    
    private static <E> E randomElement(XList<E> list)
    {
        if (!list.isEmpty())
            return list.get(random.nextInt(list.size()));
        return null;
    }
    
    @Test
    public void randomTest()
    {
        XList<Character> letters = new XArrayList<>(52);
        for (int i = 0; i < 26; i++)
            letters.append((char) ('A' + i));
        for (int i = 0; i < 26; i++)
            letters.append((char) ('a' + i));
        System.out.println(letters);
        
        XArrayList<Character> list = new XArrayList<>();
        
        int counter = 0;
        final int N = 1000_000;
        
        for (int i = 0; i < N; i++)
        {
            switch (random.nextInt(6))
            {
                case 0:
                {
                    list.append(randomElement(letters));
                    counter++;
                    break;
                }
                case 1:
                {
                    int index = list.isEmpty() ? 0 : random.nextInt(list.size());
                    list.insert(index, randomElement(letters));
                    counter++;
                    break;
                }
                case 2:
                {
                    assertEquals(counter, list.size());
                    break;
                }
                case 3:
                {
                    System.out.println(list.get(random.nextInt(list.size())));
                    break;
                }
                case 4:
                {
                    if (list.isEmpty())
                    {
                        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
                        continue;
                    }
                    
                    int rndIdx = random.nextInt(list.size());
                    Character rndSample = randomElement(letters);
                    list.set(rndIdx, rndSample);
                    assertEquals(rndSample, list.get(rndIdx));
                    break;
                }
                case 5:
                {
                    if (list.isEmpty())
                    {
                        assertEquals(0, list.size());
                        continue;
                    }
                    
                    int rndIdx = random.nextInt(list.size());
                    list.remove(rndIdx);
                    counter--;
                    break;
                }
            }
        }
    }
    
    
}
