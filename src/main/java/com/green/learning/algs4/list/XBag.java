package com.green.learning.algs4.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * FIFO队列，一个效率为O(n)的MultiSet
 * - 支持添加元素，不支持删除
 * - 不支持下标索引，只允许简单迭代
 * 该数据结构只关心内部存储的元素，而不关心顺序
 * 如果设计成FILO的栈，那么可以节省一个tail引用的空间消耗
 * @see edu.princeton.cs.algs4.Bag
 */
public class XBag<E> implements Iterable<E>
{
    private static class Node<E>
    {
        private E item;
        private Node<E> next;
        
        private Node(E item)
        {
            this.item = item;
        }
    }
    
    private int size;
    private Node<E> head;
    private Node<E> tail;
    
    public XBag()
    {
    }
    
    public int size()
    {
        return size;
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    public void add(E e)
    {
        if (head == null)
            head = tail = new Node<>(e);
        else
            tail = tail.next = new Node<>(e);
        size++;
    }
    
    public boolean contains(E e)
    {
        if (e == null)
        {
            for (Node<E> x = head; x != null; x = x.next)
                if (x.item == null)
                    return true;
        } else
        {
            for (Node<E> x = head; x != null; x = x.next)
                if (e.equals(x.item))
                    return true;
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        final String sep = ", ";
        StringBuilder sb = new StringBuilder("[");
        for (Node<E> x = head; x != null; x = x.next)
            sb.append(x.item).append(sep);
        int idx = sb.lastIndexOf(sep);
        if (idx >= 0 & idx + sep.length() == sb.length())
            sb.delete(idx, sb.length());
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public Iterator<E> iterator()
    {
        return new BagIterator();
    }
    
    private class BagIterator implements Iterator<E>
    {
        private Node<E> current = head;
        
        private BagIterator()
        {
        }
        
        @Override
        public boolean hasNext()
        {
            return current != null;
        }
        
        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            E item = current.item;
            current = current.next;
            return item;
        }
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
