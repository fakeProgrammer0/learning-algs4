package com.green.learning.algs4.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @see edu.princeton.cs.algs4.ResizingArrayQueue
 * The implementation uses a resizing array, which doubles when the queue
 * is full and halves when elements take up less space than 1 / 4 of the array.
 * @param <E> the generic type
 */
public class XArrayQueue<E> implements XQueue<E>, Iterable<E>
{
    private E[] elements;
    private int front = 0;
    private int rear = -1;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 16;
    private static final int MAXIMUM_CAPACITY = Integer.MAX_VALUE;
    
    /**
     * When autoShrink is enabled, the list will auto shrink to
     * half of the current capacity if 75% of its space is empty.
     * default: autoShrink = false
     */
    private boolean autoShrink;
    
    public XArrayQueue()
    {
        this(DEFAULT_CAPACITY);
    }
    
    @SuppressWarnings("unchecked")
    public XArrayQueue(int capacity)
    {
        if (capacity <= 0) throw new IllegalArgumentException("capacity >= 0");
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
    
    // expand the array to enlarge capacity
    private void ensureCapacity()
    {
        if (size == elements.length)
        {
            if (size == MAXIMUM_CAPACITY) throw new OutOfMemoryError("No more element can be added into the queue.");
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
    
    public int size()
    {
        return size;
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    @SuppressWarnings("unchecked")
    private void resize(int capacity)
    {
        assert capacity >= size;
        E[] temp = (E[]) new Object[capacity];
        // simple implementation
        for(int i = 0; i < size; i++)
            temp[i] = elements[(front + i) % elements.length];
        elements = temp;
        front = 0;
        rear = size - 1;
    }
    
    public void enqueue(E e)
    {
        ensureCapacity();
        rear = (rear + 1) % elements.length;
        elements[rear] = e;
        size++;
    }
    
    public E dequeue()
    {
        if (isEmpty())
            throw new NoSuchElementException("The queue is empty.");
        
        E element = elements[front];
        elements[front] = null;
        front = (front + 1) % elements.length;
        size--;
        
        saveCapacity();
        return element;
    }
    
    @SuppressWarnings("unchecked")
    public void clear()
    {
        front = 0;
        rear = -1;
        size = 0;
        if(autoShrink)
            elements = (E[]) new Object[DEFAULT_CAPACITY];
        else Arrays.fill(elements, null);
    }
    
    @Override
    public E peek()
    {
        if (isEmpty())
            throw new NoSuchElementException("The queue is empty.");
        return elements[front];
    }
    
    @Override
    public String toString()
    {
        if(isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        final String elementSep = ", ";
        for (int i = front; i != rear; i = (i + 1) % elements.length)
            sb.append(elements[i]).append(elementSep);
        sb.append(elements[rear]);
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public Iterator<E> iterator()
    {
        return new ArrayQueueIterator();
    }
    
    private class ArrayQueueIterator implements Iterator<E>
    {
        private int currIdx;
        
        private ArrayQueueIterator()
        {
            this.currIdx = 0;
        }
        
        @Override
        public boolean hasNext()
        {
            return currIdx < size;
        }
        
        @Override
        public E next()
        {
            if(!hasNext())
                throw new NoSuchElementException();
            E element = elements[(front + currIdx) % elements.length];
            currIdx++;
            return element;
        }
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
