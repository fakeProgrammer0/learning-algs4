package com.green.learning.algs4.list;

public abstract class XAbstractList<E> implements XList<E>
{
    protected int size;
    
    protected XAbstractList()
    {
    }
    
    public void append(E e)
    {
        insert(size, e);
    }
    
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    @Override
    public int size()
    {
        return size;
    }
    
    /**
     * remove the first occurrence of {@param e}
     * A low efficient implementation
     *
     * @param e the element to be removed from the list
     */
    @Override
    public boolean removeFirst(E e)
    {
        int index = indexOf(e);
        if (index >= 0)
        {
            remove(index);
            return true;
        }
        
        return false;
    }
    
    /**
     * remove all the elements which {@code equals} {@param e}
     * A low efficient implementation
     *
     * @param e the element to be removed from the list
     */
    @Override
    public void removeAll(E e)
    {
        while (contains(e))
            removeFirst(e);
    }
    
    @Override
    public boolean contains(E e)
    {
        return indexOf(e) >= 0;
    }
    
    @Override
    public String toString()
    {
        if(isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        final String elementSep = ", ";
        for (int i = 0; i < size - 1; i++)
            sb.append(get(i)).append(elementSep);
        sb.append(get(size - 1));
        sb.append("]");
        return sb.toString();
    }
    
    protected void validateElementIndex(int index)
    {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid access to index: " + index);
    }
    
    protected void validateInsertIndex(int index)
    {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Invalid insert at index: " + index);
    }
}
