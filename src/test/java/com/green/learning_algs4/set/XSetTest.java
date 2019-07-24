package com.green.learning_algs4.set;

import com.green.learning_algs4.util.MathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class XSetTest
{
    @Test
    public void testXLinkedHashSet()
    {
        final int N = 1024;
        //        XLinkedHashSet<Integer> set = new XLinkedHashSet<>();
        XLinkedHashSet<Integer> set = new XLinkedHashSet<>();
        for (int i = 0; i < N; i++)
            set.add(i);
        
        Assertions.assertTrue(set.contains(N - 1));
        Assertions.assertFalse(set.contains(N));
        
        set.add(null);
        Assertions.assertTrue(set.contains(null));
        Assertions.assertTrue(set.remove(null));
        Assertions.assertFalse(set.contains(null));
        
        System.out.println(set.toString());
        
        set.clear();
        Assertions.assertTrue(set.isEmpty());
        System.out.println(set);
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 7, 8, 15, 16, 31, 32, 256})
    public void testExpandToPowerOf2(int N)
    {
        System.out.println(N + " : " + MathUtil.expandToPowerOf2(N));
    }
    
    @Test
    public void testExpandToPowerOf2_Abnormal()
    {
        // 边界值
        Assertions.assertThrows(AssertionError.class, () -> MathUtil.expandToPowerOf2(-0));
        Assertions.assertThrows(AssertionError.class, () -> MathUtil.expandToPowerOf2(-1));
        Assertions.assertThrows(AssertionError.class, () -> MathUtil.expandToPowerOf2(-128));
        
        Assertions.assertEquals(1, MathUtil.expandToPowerOf2(1 << 30) >> 30);
        Assertions.assertThrows(IllegalArgumentException.class, () -> MathUtil.expandToPowerOf2((1 << 30) + 1));
    }
    
    @Test
    public void testAdd()
    {
        XSet<Integer> set = new XLinkedHashSetX<>();
        set.add(1);
        Assertions.assertEquals(1, set.size());
        set.add(1);
        Assertions.assertEquals(1, set.size());
    }
}
