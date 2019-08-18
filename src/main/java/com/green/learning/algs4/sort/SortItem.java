package com.green.learning.algs4.sort;

import java.util.Random;

/**
 * A simple k-v wrapper object used for testing sort stability
 * used in {@code Sorts.isProbablyStableSort}
 */
class SortItem implements Comparable<SortItem>
{
    private final int key;
    private final String value;
    
    private static int id = 0;
    
    public SortItem(int key)
    {
        this.key = key;
        this.value = String.valueOf(id++);
    }
    
    public int getKey()
    {
        return key;
    }
    
    public String getValue()
    {
        return value;
    }
    
    @Override
    public int compareTo(SortItem o)
    {
//        return this.key - o.key; // int溢出的bug，会给出不正确的比较值
        return Integer.compare(this.key, o.key);
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof SortItem)) return false;
        SortItem that = (SortItem) obj;
        return this.key == that.key
                && this.value.equals(that.value);
    }
    
    @Override
    public String toString()
    {
        return "{key:" + key + ", value:" + value + "}";
    }
    
    public static SortItem[] rndSamples(int N)
    {
        Random rnd = new Random();
        SortItem[] samples = new SortItem[N];
        for (int i = 0; i < N; i++)
            samples[i] = new SortItem(rnd.nextInt());
        return samples;
    }
    
    public static SortItem[] rndSamples(int N, int bound)
    {
        Random rnd = new Random();
        SortItem[] samples = new SortItem[N];
        for (int i = 0; i < N; i++)
            samples[i] = new SortItem(rnd.nextInt(bound));
        return samples;
    }
}
