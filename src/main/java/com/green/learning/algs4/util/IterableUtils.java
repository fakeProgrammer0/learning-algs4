package com.green.learning.algs4.util;

public class IterableUtils
{
    public static <E extends Comparable<E>> boolean isSorted(Iterable<E> iterable)
    {
        E pre = null;
        for(E e: iterable)
        {
            if(pre != null)
                if(pre.compareTo(e) > 0)
                    return false;
            pre = e;
        }
        return true;
    }
}
