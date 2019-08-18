package com.green.learning.algs4.list;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @see java.util.ArrayList
 */
public class XArrayList<E> extends XAbstractList<E>
{
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 16;
    private static final int MAXIMUM_CAPACITY = Integer.MAX_VALUE;
    
    /**
     * When autoShrink is enabled, the list will auto shrink to
     * half of the current capacity if 75% of its space is empty.
     * default: autoShrink = false
     */
    private boolean autoShrink;
    
    public XArrayList()
    {
        this(DEFAULT_CAPACITY);
    }
    
    @SuppressWarnings("unchecked")
    public XArrayList(int capacity)
    {
        if (capacity <= 0) throw new IllegalArgumentException("capacity should be positive");
        if (capacity > MAXIMUM_CAPACITY) throw new IllegalArgumentException("maximum capacity is " + MAXIMUM_CAPACITY);
        elements = (E[]) new Object[capacity];
    }
    
    @SuppressWarnings("unchecked")
    public XArrayList(E[] elements)
    {
        if(elements == null)
            throw new IllegalArgumentException("The parameter 'elements' should not be null");
        if(elements.length == 0)
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        else
        {
            this.elements = Arrays.copyOf(elements, elements.length);
            size = elements.length;
        }
    }
    
    public XArrayList(Iterable<E> elements)
    {
        this(DEFAULT_CAPACITY);
        if(elements == null)
            throw new IllegalArgumentException("The parameter 'elements' should not be null");
        Iterator<E> iterator = elements.iterator();
//        if(!iterator.hasNext())
//            throw new IllegalArgumentException("No element");
        while (iterator.hasNext())
            append(iterator.next());
    }
    
    public boolean isAutoShrink()
    {
        return autoShrink;
    }
    
    public void setAutoShrink(boolean autoShrink)
    {
        this.autoShrink = autoShrink;
    }
    
//        @Deprecated
    /**
     * for unit test
     * @return capacity of the current list
     */
    int getCapacity()
    {
        return elements.length;
    }
    
    private void resize(int capacity)
    {
        assert capacity >= size;
        elements = Arrays.copyOf(elements, capacity);
    }
    
    private void ensureCapacity()
    {
        if (size >= elements.length)
        {
            if (size == MAXIMUM_CAPACITY)
                throw new OutOfMemoryError("The XArrayList can contain at most " + MAXIMUM_CAPACITY + " elements");
            else if (elements.length < MAXIMUM_CAPACITY)
            {
                int newCapacity = size >= (MAXIMUM_CAPACITY >> 1) ? MAXIMUM_CAPACITY : size * 2;
                resize(newCapacity);
            }
        }
    }
    
    private void saveCapacity()
    {
        if (autoShrink
                && elements.length > DEFAULT_CAPACITY
                && size * 4 < elements.length)
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
    public void insert(int index, E element)
    {
        validateInsertIndex(index);
        ensureCapacity();
        
        for (int i = size; i > index; i--)
            elements[i] = elements[i - 1];
        elements[index] = element;
        size++;
    }
    
    @Override
    public E get(int index)
    {
        validateElementIndex(index);
        return elements[index];
    }
    
    @Override
    public E set(int index, E element)
    {
        validateElementIndex(index);
        E oldVal = elements[index];
        elements[index] = element;
        return oldVal;
    }
    
    @Override
    public E remove(int index)
    {
        validateElementIndex(index);
        E element = elements[index];
        for (int i = index; i < size - 1; i++)
            elements[i] = elements[i + 1];
        
        elements[--size] = null;
        saveCapacity();
        return element;
    }
    
    @Override
    public void removeAll(E element)
    {
        int j = -1;
        if (element == null)
        {
            for (int i = 0; i < size; i++)
            {
                if (element == elements[i])
                    continue;
                else j++;
                elements[j] = elements[i];
            }
        } else
        {
            for (int i = 0; i < size; i++)
            {
                if (element.equals(elements[i]))
                    continue;
                else j++;
                elements[j] = elements[i];
            }
        }
        
        Arrays.fill(elements, j + 1, size, null);
        size = j + 1;
        saveCapacity();
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
    public int indexOf(E element)
    {
        if (element == null)
        {
            for (int i = 0; i < size; i++)
                if (elements[i] == null)
                    return i;
        } else
        {
            for (int i = 0; i < size; i++)
                if (element.equals(elements[i]))
                    return i;
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(E element)
    {
        if (element == null)
        {
            for (int i = size - 1; i >= 0; i--)
                if (elements[i] == element)
                    return i;
        } else
        {
            for (int i = size - 1; i >= 0; i--)
                if (element.equals(elements[i]))
                    return i;
        }
        
        return -1;
    }
    
    @Override
    public Object[] toArray()
    {
        return Arrays.copyOf(elements, size);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray(E[] a)
    {
        if(a.length < size)
            return (E[]) Arrays.copyOf(elements, size, a.getClass());
        System.arraycopy(elements, 0, a, 0, size);
        return a;
    }
    
    public java.util.Iterator<E> iterator()
    {
        return new XArrayListIterator();
    }
    
    private class XArrayListIterator implements java.util.Iterator<E>
    {
        private int current = 0;
        
        @Override
        public boolean hasNext()
        {
            return current < size;
        }
        
        @Override
        public E next()
        {
            return elements[current++];
        }
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Removing an element from an iterator is not supported");
        }
    }
}
