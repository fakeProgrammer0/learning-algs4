package com.green.learning.algs4.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 有点多余，因为{@link XLinkedList}已经实现了相同的内容
 * {@link edu.princeton.cs.algs4.Queue} & {@link edu.princeton.cs.algs4.LinkedQueue}
 * @param <E> the generic parameter type
 */
public class XLinkedQueue<E> implements XQueue<E>, Iterable<E>
{
    private static class Node<T>
    {
        private T item;
        private Node<T> next;
        
        private Node(T item)
        {
            this.item = item;
        }
    
        private Node(T item, Node<T> next)
        {
            this.item = item;
            this.next = next;
        }
    }
    
    private Node<E> head;
    private Node<E> tail;
    private int size = 0;
    
    public XLinkedQueue()
    {
    
    }
    
    public int size()
    {
        return size;
    }
    
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    public void enqueue(E e)
    {
        if (isEmpty())
            head = tail = new Node<>(e);
        else
            tail = tail.next = new Node<>(e);
        size++;
    }
    
    @Override
    public E peek()
    {
        if(isEmpty())
            throw new NoSuchElementException("The queue is empty");
        return head.item;
    }
    
    public E dequeue()
    {
        if(isEmpty())
            throw new NoSuchElementException("The queue is empty");
        
        E item = head.item;
        head = head.next;
        size--;
        if(isEmpty()) tail = null;
        return item;
    }
    
    public void clear()
    {
        Node<E> curr = head;
        while (curr != null)
        {
            head = head.next;
            curr.item = null;
            curr.next = null;
            curr = head;
        }
        tail = null;
        size = 0;
    }
    
    @Override
    public String toString()
    {
        if(isEmpty()) return "[]";
        final String elementSep = ", ";
        StringBuilder sb = new StringBuilder("[");
        for (Node<E> curr = head; curr != tail; curr = curr.next)
            sb.append(curr.item).append(elementSep);
        sb.append(tail.item);
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public Iterator<E> iterator()
    {
        return new LinkedQueueIterator();
    }
    
    private class LinkedQueueIterator implements Iterator<E>
    {
        private Node<E> current;
    
        private LinkedQueueIterator()
        {
            current = head;
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
