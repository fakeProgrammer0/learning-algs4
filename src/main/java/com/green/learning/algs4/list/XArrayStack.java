package com.green.learning.algs4.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @see edu.princeton.cs.algs4.ResizingArrayStack
 * This implementation uses a resizing array, which doubles the underlying array
 * when it is full and shrinks the underlying array when 75% of the space is empty.
 * The <em>push</em> and <em>pop</em> operations take constant amortized time.
 * @param <E> the generic type of elements to be stored
 */
public class XArrayStack<E> implements XStack<E>, Iterable<E>
{
    private static final int DEFAULT_CAPACITY = 16;
    private static final int MAXIMUM_CAPACITY = Integer.MAX_VALUE;
    private int size = 0;
    private E[] elements;
    
    /**
     * When autoShrink is enabled, the list will auto shrink to
     * half of the current capacity if 75% of its space is empty.
     * default: autoShrink = false
     */
    private boolean autoShrink;
    
    public XArrayStack()
    {
        this(DEFAULT_CAPACITY);
    }
    
    @SuppressWarnings("unchecked")
    public XArrayStack(int capacity)
    {
        if (capacity <= 0) throw new IllegalArgumentException("capacity >= 0");
        if (capacity > MAXIMUM_CAPACITY) throw new IllegalArgumentException("maximum capacity is " + MAXIMUM_CAPACITY);
        elements = (E[]) new Object[capacity];
    }
    
    /**
     * for unit test
     * @return capacity of the current list
     */
    int getCapacity()
    {
        return elements.length;
    }
    
    public boolean isAutoShrink()
    {
        return autoShrink;
    }
    
    public void setAutoShrink(boolean autoShrink)
    {
        this.autoShrink = autoShrink;
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
    
    // a helper function
    private void resize(int capacity)
    {
        assert capacity >= size;
        elements = Arrays.copyOf(elements, capacity);
    }
    
    // expand the array to enlarge capacity
    private void ensureCapacity()
    {
        if (size == elements.length)
        {
            if (size == MAXIMUM_CAPACITY) throw new OutOfMemoryError("No more element can be pushed into the stack.");
            if (elements.length < MAXIMUM_CAPACITY)
            {
                int newSize = size >= (MAXIMUM_CAPACITY >> 1) ? MAXIMUM_CAPACITY : 2 * size;
                resize(newSize);
            }
        }
    }
    
    // shrink the array to save capacity
    private void saveCapacity()
    {
        if (autoShrink 
                && elements.length > DEFAULT_CAPACITY 
                && 4 * size < elements.length)
        {
            int newCapacity = elements.length / 2;
            if (newCapacity < DEFAULT_CAPACITY)
                newCapacity = DEFAULT_CAPACITY;
            resize(newCapacity);
        }
    }
    
    /**
     * 压缩空间到恰好容纳所有元素
     * 客户端主动要求压缩空间
     */
    public void trimToSize()
    {
        if (size > 0 && size < elements.length)
        {
            int newCapacity = size < DEFAULT_CAPACITY ? DEFAULT_CAPACITY : size;
            resize(newCapacity);
        }
    }
    
    @Override
    public void push(E element)
    {
        ensureCapacity();
        elements[size++] = element;
    }
    
    private void checkUnderFlow()
    {
        if (isEmpty())
            throw new NoSuchElementException("Stack Underflow");
    }
    
    @Override
    public E peek()
    {
        checkUnderFlow();
        return elements[size - 1];
    }
    
    @Override
    public E pop()
    {
        checkUnderFlow();
        E element = elements[--size];
        elements[size] = null; // help gc
        saveCapacity();
        return element;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void clear()
    {
        size = 0;
        if(autoShrink)
            elements = (E[]) new Object[DEFAULT_CAPACITY];
        else Arrays.fill(elements, null);
    }
    
    @Override
    public String toString()
    {
        if(isEmpty()) return "[]";
        
        StringBuilder sb = new StringBuilder("[");
        final String elementSep = ", ";
        for (int i = size - 1; i > 0; i--)
            sb.append(elements[i]).append(elementSep);
        sb.append(elements[0]);
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public Iterator<E> iterator()
    {
        return new XArrayStackIterator();
    }
    
    private class XArrayStackIterator implements Iterator<E>
    {
        private int currIdx = size - 1;
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean hasNext()
        {
            return currIdx >= 0;
        }
        
        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            return elements[currIdx--];
        }
    }
}
