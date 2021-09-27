package com.green.learning.algs4.tree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 优先队列
 * @see edu.princeton.cs.algs4.MinPQ
 * @see edu.princeton.cs.algs4.MaxPQ
 * @see java.util.PriorityQueue
 */
public class XPriorityQueue<E> implements Iterable<E>, Cloneable
{
    private Comparator<E> comparator;
    private final boolean smallPriority;
    private static final boolean DEFAULT_SMALL_PRIORITY = true;
    
    public boolean isSmallPriority()
    {
        return smallPriority;
    }
    
    private boolean hasHigherPriority(E o1, E o2)
    {
        if (smallPriority)
            if (comparator == null)
                return ((Comparable<E>) o1).compareTo(o2) <= 0;
            else return comparator.compare(o1, o2) <= 0; // 更小的元素，优先级更高
        if (comparator == null)
            return ((Comparable<E>) o1).compareTo(o2) >= 0;
        return comparator.compare(o1, o2) >= 0; // 更大的元素，优先级更高
    }
    
    private int size;
    private E[] items;
    private static final int DEFAULT_CAPACITY = 16;
    private static final int MAXIMUM_CAPACITY = Integer.MAX_VALUE;
    
    private boolean autoShrink;
    
    private static final int ROOT = 1;
    private static final int NULL_NODE = 0;
    
    public XPriorityQueue()
    {
        this(DEFAULT_SMALL_PRIORITY);
    }
    
    public XPriorityQueue(boolean smallPriority)
    {
        this(smallPriority, (Comparator<E>) null);
    }
    
    public XPriorityQueue(boolean smallPriority, Comparator<E> comparator)
    {
        this.smallPriority = smallPriority;
        this.comparator = comparator;
        items = (E[]) new Object[DEFAULT_CAPACITY];
    }
    
    public XPriorityQueue(boolean smallPriority, E[] items)
    {
        this.smallPriority = smallPriority;
        for (E item : items)
            if (item == null)
                throw new IllegalArgumentException("null element isn't allowed");
        
        this.items = (E[]) new Object[items.length + 1];
        System.arraycopy(items, 0, this.items, 1, items.length);
        size = items.length;
        for (int i = size / 2; i >= ROOT; i--)
            sink(i);
//        assert isHeap();
    }
    
    public int size()
    {
        return size;
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    public boolean isAutoShrink()
    {
        return autoShrink;
    }
    
    public void setAutoShrink(boolean autoShrink)
    {
        this.autoShrink = autoShrink;
    }
    
    // a helper method
    private void resize(int capacity)
    {
        assert capacity >= size;
        items = Arrays.copyOf(items, capacity);
    }
    
    // expand the array to enlarge capacity
    private void ensureCapacity()
    {
        int actualSize = size + ROOT;
        if (actualSize == items.length)
        {
            if (actualSize == MAXIMUM_CAPACITY)
                throw new OutOfMemoryError();
            int newCapacity = actualSize >= MAXIMUM_CAPACITY >> 1 ? MAXIMUM_CAPACITY : 2 * size;
            resize(newCapacity);
        }
    }
    
    // shrink the array to save capacity
    private void saveCapacity()
    {
        if (items.length > DEFAULT_CAPACITY && 2 * size < items.length)
        {
            int newCapacity = items.length / 2;
            if (newCapacity < DEFAULT_CAPACITY)
                newCapacity = DEFAULT_CAPACITY;
            resize(newCapacity);
        }
    }
    
    private void sink(int node)
    {
        E nodeVal = items[node];
        while (2 * node <= size)
        {
            int j = node * 2;
            if (j + 1 <= size && hasHigherPriority(items[j + 1], items[j]))
                j++;
            if (hasHigherPriority(items[j], nodeVal))
            {
                items[node] = items[j];
                node = j;
            } else break;
        }
        items[node] = nodeVal;
    }
    
    private void swim(int node)
    {
        E nodeVal = items[node];
        while (node > ROOT)
        {
            int parent = node / 2;
            if (hasHigherPriority(nodeVal, items[parent]))
            {
                items[node] = items[parent];
                node = parent;
            } else break;
        }
        items[node] = nodeVal;
    }
    
    public void enqueue(E e)
    {
        if (e == null)
            throw new IllegalArgumentException("null element is not allowed");
        ensureCapacity();
        items[++size] = e;
        swim(size);
//        assert isHeap();
    }
    
    private void checkEmpty()
    {
        if (isEmpty())
            throw new NoSuchElementException("The queue is empty");
    }
    
    public E elementWithHighestPriority()
    {
        checkEmpty();
        return items[ROOT];
    }
    
    public E dequeue()
    {
        checkEmpty();
        E val = items[ROOT];
        items[ROOT] = items[size];
        items[size] = null;
        size--;
        if (!isEmpty()) sink(ROOT);
        if (autoShrink) saveCapacity();
//        assert isHeap();
        return val;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        XPriorityQueue<E> queue = (XPriorityQueue<E>) super.clone();
        
        // 数组内部的每个元素都是浅复制的指针
        queue.items = items.clone();
        
        // 跟上面同样的效果
        //        queue.items = Arrays.copyOf(items, items.length);
        
        return queue;
    }
    
    private boolean isHeap()
    {
        for (int i = size; i > ROOT; i--)
        {
//            if (hasHigherPriority(items[i], items[i / 2])) // 这样写有bug
            if (!hasHigherPriority(items[i / 2], items[i]))
                return false;
        }
        return true;
    }
    
    @Override
    public Iterator<E> iterator()
    {
        return new XPriorityQueueIterator();
    }
    
    private class XPriorityQueueIterator implements Iterator<E>
    {
        private XPriorityQueue<E> queue;
        
        public XPriorityQueueIterator()
        {
            try
            {
                queue = (XPriorityQueue<E>) XPriorityQueue.this.clone();
            } catch (CloneNotSupportedException ex)
            {
                ex.printStackTrace();
            }
        }
        
        @Override
        public boolean hasNext()
        {
            return !queue.isEmpty();
        }
        
        @Override
        public E next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            return queue.dequeue();
        }
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
