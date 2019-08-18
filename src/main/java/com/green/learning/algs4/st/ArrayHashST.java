package com.green.learning.algs4.st;

import com.green.learning.algs4.set.XLinkedHashSet;
import com.green.learning.algs4.set.XSet;
import com.green.learning.algs4.util.MathUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * {@link edu.princeton.cs.algs4.LinearProbingHashST}
 * @param <K>
 * @param <V>
 */
public class ArrayHashST<K, V> implements ST<K, V>
{
    private static int DEFAULT_CAPACITY = 16;
    private static int MAXIMUM_CAPACITY = 1 << 30;
    private int capacity;
    
    private static float MAXIMUM_LOAD_FACTOR = 0.75f;
    private float loadFactor;
    
    private int size = 0;
    private ST.Entry<K, V>[] table;
    
    public ArrayHashST()
    {
        this(DEFAULT_CAPACITY, MAXIMUM_LOAD_FACTOR);
    }
    
    public ArrayHashST(int initialCapacity)
    {
        this(initialCapacity, MAXIMUM_LOAD_FACTOR);
    }
    
    public ArrayHashST(int initialCapacity, float loadFactor)
    {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("capacity should be positive");
        if (initialCapacity > MAXIMUM_CAPACITY)
            throw new IllegalArgumentException("maximum capacity is " + MAXIMUM_CAPACITY);
        this.capacity = MathUtil.expandToPowerOf2(initialCapacity);
        if (this.capacity > MAXIMUM_CAPACITY) this.capacity = MAXIMUM_CAPACITY;
        
        if (loadFactor <= 0)
            throw new IllegalArgumentException("load factor should be positive.");
        this.loadFactor = loadFactor;
        table = (ST.Entry<K, V>[]) new ST.Entry[capacity];
    }
    
    // tombStone的设置方案
    private void setTombstone(ST.Entry<K, V> entry)
    {
        assert entry != null;
        entry.setKey(null);
        entry.setValue(null);
    }
    
    private boolean isTombstone(ST.Entry<K, V> entry)
    {
        return entry != null && entry.getKey() == null && entry.getValue() == null;
    }
    
    private void checkNull(Object o)
    {
        if (o == null)
            throw new IllegalArgumentException("Null argument is not allowed.");
    }
    
    private void ensureCapacity()
    {
        if (capacity < MAXIMUM_CAPACITY && size >= capacity * loadFactor)
        {
            int newCapacity = (capacity >= MAXIMUM_CAPACITY >> 1) ? MAXIMUM_CAPACITY : capacity << 1;
            rehash(newCapacity);
        }
    }
    
    private void saveCapacity()
    {
        if (table.length > DEFAULT_CAPACITY && 2 * size < table.length)
        {
            int newCapacity = table.length / 2;
            if (newCapacity < DEFAULT_CAPACITY)
                newCapacity = DEFAULT_CAPACITY;
            if (size < newCapacity * loadFactor) // 没有超过装载因子
                rehash(newCapacity);
        }
    }
    
    private void rehash(int capacity)
    {
        assert capacity >= size;
        ST.Entry<K, V>[] newTable = table;
        table = (ST.Entry<K, V>[]) new ST.Entry[capacity];
        size = 0;
        for (ST.Entry<K, V> entry : newTable)
            if (entry != null && !isTombstone(entry))
                put(entry.getKey(), entry.getValue());
    }
    
    @Override
    public int size()
    {
        return size;
    }
    
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    private int h1(K key)
    {
        return key.hashCode() & (capacity - 1);
    }
    
    private int h2(K key)
    {
        return ((int) Math.pow(key.hashCode(), 2)) & (capacity - 1);
    }
    
    //    private int hash(K key)
    //    {
    //        for (int i = 0; ; i++)
    //        {
    //            return hash(key, i);
    //        }
    //    }
    //
    //    private int hash(K key, int i)
    //    {
    //        return h1(key) + i * h2(key);
    //    }
    //
    @Override
    public boolean containsKey(K key)
    {
        checkNull(key);
        
        int homeSlot = h1(key);
        if (table[homeSlot] != null
                && !isTombstone(table[homeSlot])
                && table[homeSlot].getKey().equals(key))
            return true;
        
        int slot = homeSlot;
        int gap = h2(key);
        for (int i = 1; table[slot] != null; i++)
        {
            slot = (homeSlot + i * gap) & (capacity - 1);
            
            if (slot == homeSlot) //防止出现死循环
                return false;
            // i * gap % capacity = 0
            // 由等价的数学原理可知，之后走的槽会和以前的序列重复
            
            //如果是墓碑的话，调用equals方法直接崩了
            if (table[slot] != null && !isTombstone(table[slot]) && table[slot].getKey().equals(key))
                return true;
        }
        
        return false;
    }
    
    @Override
    public void put(K key, V value)
    {
        if (key == null || value == null)
            throw new IllegalStateException("Neither key nor value can be null");
        ensureCapacity();
        
        int homeSlot = h1(key);
        if (table[homeSlot] == null || isTombstone(table[homeSlot]))
        {
            table[homeSlot] = new ST.Entry<>(key, value);
            size++;
            return;
        } else if (table[homeSlot].getKey().equals(key))
        {
            table[homeSlot].setValue(value);
            return;
        }
        int slot = homeSlot, gap = h2(key);
        for (int i = 1; table[slot] != null; i++)
        {
            slot = (homeSlot + i * gap) & (capacity - 1);
    
            // 防止出现死循环
            // 但也总不能退出吧。。
            if (slot == homeSlot)
            {
                return;
            }
            
            if (isTombstone(table[slot]))
            {
                table[slot].setKey(key);
                table[slot].setValue(value);
                size++;
                return;
            } else if (table[slot] != null && table[slot].getKey().equals(key))
            {
                table[slot].setValue(value);
                return;
            }
        }
        
        table[slot] = new ST.Entry<>(key, value);
        size++;
    }
    
    @Override
    public V get(K key)
    {
        checkNull(key);
        
        int homeSlot = h1(key);
        int slot = homeSlot;
        int gap = h2(key);
        for (int i = 0; table[slot] != null; i++)
        {
            slot = (homeSlot + i * gap) & (capacity - 1);
            
            if (i == capacity) //遍历过很多次的槽，避免陷入死循环
                return null;
            
            if (!isTombstone(table[slot]) && table[slot].getKey().equals(key))
                return table[slot].getValue();
        }
        
        return null;
    }
    
    @Override
    public void remove(K key)
    {
        checkNull(key);
        
        int homeSlot = h1(key);
        int slot = homeSlot;
        int gap = h2(key);
        for (int i = 0; table[slot] != null; i++)
        {
            slot = (homeSlot + i * gap) & (capacity - 1);
            
            if (i == capacity) //遍历过很多次的槽，避免陷入死循环
                return;
            
            if (table[slot] != null && !isTombstone(table[slot]) && table[slot].getKey().equals(key))
            {
                setTombstone(table[slot]);
                size--;
                return;
            }
        }
        
    }
    
    @Override
    public void clear()
    {
        Arrays.fill(table, null);
        size = 0;
    }
    
    // 效率太低
    @Deprecated
    public boolean containsValue(V value)
    {
        if (value == null)
            return false;
        
        for (ST.Entry<K, V> entry : table)
            if (entry != null && !isTombstone(entry))
                if (entry.getValue().equals(value))
                    return true;
        
        return false;
    }
    
    @Override
    public XSet<Entry<K, V>> entries()
    {
        if (size == 0)
            return new XLinkedHashSet<>();
        
        XSet<ST.Entry<K, V>> set = new XLinkedHashSet<>();
        for (ST.Entry<K, V> entry : table)
            if (entry != null && !isTombstone(entry))
                set.add(entry);
        
        return set;
    }
    
    @Override
    public XSet<K> keys()
    {
        XSet<K> set = new XLinkedHashSet<>();
        for (ST.Entry<K, V> entry : table)
            if (entry != null && entry.getKey() != null)
                set.add(entry.getKey());
        
        return set;
    }
    
    public Set<V> values()
    {
        Set<V> set = new HashSet<>();
        for (ST.Entry<K, V> entry : table)
            if (entry != null && !isTombstone(entry))
                set.add(entry.getValue());
        
        return set;
    }
    
    @Override
    public String toString()
    {
        if (size == 0)
            return "[]";
        
        StringBuilder sb = new StringBuilder("[");
        for (ST.Entry<K, V> entry : table)
            if (entry != null && !isTombstone(entry))
                sb.append(entry.toString() + ", ");
        
        sb.delete(sb.length() - ", ".length(), sb.length());
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public Iterator<Entry<K, V>> iterator()
    {
        return null;
    }
}
