package com.green.learning.algs4.set;



import com.green.learning.algs4.list.XArrayList;
import com.green.learning.algs4.list.XLinkedList;
import com.green.learning.algs4.list.XList;

import java.util.Iterator;

/**
 * 链式哈希
 * {@link java.util.HashSet}
 * {@link java.util.HashMap}
 * {@link edu.princeton.cs.algs4.SeparateChainingHashST} lazy implementation
 * @param <E>
 */
public class XLinkedHashSetX<E> implements XSet<E>
{
    private static int DEFAULT_CAPACITY = 16;
    private static int MAXIMUM_CAPACITY = 1 << 30;
    private int capacity;
    
    private static float DEFAULT_LOAD_FACTOR = 0.75f;
    private float loadFactor;
    
    private int size = 0;
    private XLinkedList<E>[] table;
    
    public XLinkedHashSetX()
    {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }
    
    public XLinkedHashSetX(int initialCapacity)
    {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }
    
    public XLinkedHashSetX(int initialCapacity, float loadFactor)
    {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("capacity should be positive");
        if (initialCapacity > MAXIMUM_CAPACITY)
            throw new IllegalArgumentException("maximum capacity is " + MAXIMUM_CAPACITY);
        this.capacity = expandToPowerOf2(initialCapacity);
        if (this.capacity > MAXIMUM_CAPACITY) this.capacity = MAXIMUM_CAPACITY;
        
        if (loadFactor <= 0)
            throw new IllegalArgumentException("load factor should be positive.");
        this.loadFactor = loadFactor;
        
        table = (XLinkedList<E>[]) new XLinkedList[capacity];
    }
    
    private static int expandToPowerOf2(int N)
    {
        //        return (N << 1) & (1 << (Integer.SIZE - Integer.numberOfLeadingZeros(N)));
        int leadingZeroCounts = Integer.numberOfLeadingZeros(N);
        if (leadingZeroCounts + Integer.numberOfTrailingZeros(N) + 1 == Integer.SIZE)
            return N;
        if (leadingZeroCounts == 0)
            throw new IllegalArgumentException("N is too large");
        return 1 << (Integer.SIZE - leadingZeroCounts);
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
    
    private int h(E e)
    {
        //采用位操作&代替%
        if(e == null) return 0;
        return e.hashCode() & (capacity - 1);
    }
    
    @Override
    public boolean contains(E e)
    {
        XLinkedList<E> list = table[h(e)];
        if (list != null)
            return list.contains(e);
        return false;
    }
    
    private void ensureCapacity()
    {
        if (capacity < MAXIMUM_CAPACITY && size >= capacity * loadFactor)
        {
            int newCapacity = (capacity >= MAXIMUM_CAPACITY >> 1) ? MAXIMUM_CAPACITY : capacity << 1;
            rehash(newCapacity);
        }
    }
    
    private void rehash(int newCapacity)
    {
        assert newCapacity > 0;
        XLinkedList<E>[] newTable = (XLinkedList<E>[]) new XLinkedList[newCapacity];
        for (XLinkedList<E> list : table)
            if (list != null)
                for (E item : list)
                {
                    int hashVal = h(item);
                    if (newTable[hashVal] == null) newTable[hashVal] = new XLinkedList<>();
                    newTable[hashVal].addLast(item); // 浅复制
                }
        table = newTable;
    }
    
    @Override
    public boolean add(E e)
    {
        //        LinkedList<E> list = table[h(e)];
        //        if (list != null)
        //        {
        //            ListIterator<E> iterator = list.listIterator();
        //            while (iterator.hasNext())
        //            {
        //                E next = iterator.next();
        //                if (next.equals(e))
        //                    return false;
        //            }
        //        } else
        //            list = new LinkedList<>();
        
        // 以上，改写为
        if (contains(e))
            return false;
        
        ensureCapacity();
        int hashVal = h(e);
        if (table[hashVal] == null)
            table[hashVal] = new XLinkedList<>();
        
        table[hashVal].append(e);
        size++;
        return true;
    }
    
    @Override
    public boolean remove(E e)
    {
        XLinkedList<E> list = table[h(e)];
        if (list != null && list.removeFirst(e))
        {
            size--;
            return true;
        }
        
        return false;
    }
    
    @Override
    public void clear()
    {
        for (int i = 0; i < table.length; i++)
            if (table[i] != null)
            {
                table[i].clear();
                table[i] = null;
            }
        size = 0;
    }
    
    @Override
    public String toString()
    {
        if (size == 0)
            return "[]";
        
        final String sep = ",";
        StringBuilder sb = new StringBuilder("[");
        for (XLinkedList<E> list : table)
            if (list != null)
                for (E item : list)
                    sb.append(item).append(sep);
        sb.delete(sb.lastIndexOf(sep), sb.length());
        sb.append("]");
        return sb.toString();
    }
    
    public XList<E> setToList()
    {
        XList<E> elements = new XArrayList<>();
        for (XLinkedList<E> list : table)
            if (list != null)
                for (E item : list)
                    elements.append(item);
        return elements;
    }
    
    @Override
    public Iterator<E> iterator()
    {
        return new MyLinkedHashSetIterator();
    }
    
    private class MyLinkedHashSetIterator implements Iterator<E>
    {
        private XList<E> elements;
        private int currentIndex = 0;
        
        private MyLinkedHashSetIterator()
        {
            elements = setToList();
        }
        
        @Override
        public boolean hasNext()
        {
            return currentIndex < elements.size();
        }
        
        @Override
        public E next()
        {
            return elements.get(currentIndex++);
        }
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
