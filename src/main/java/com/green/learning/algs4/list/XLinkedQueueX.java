package com.green.learning.algs4.list;

import com.green.learning.algs4.set.XLinkedHashSet;
import com.green.learning.algs4.set.XSet;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A queue implemented by a single recurrent linked list with only a tail reference
 *
 * @param <E> generic element type
 */
public class XLinkedQueueX<E> implements XQueue<E>
{
    private static class Node<T>
    {
        private T e;
        private Node<T> next;
        
        private Node(T e)
        {
            this.e = e;
        }
        
        private Node(T e, Node<T> next)
        {
            this.e = e;
            this.next = next;
        }
    }
    
    private int size;
    private Node<E> tail;
    
    public XLinkedQueueX()
    {
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
    
    private void checkNodeLink()
    {
        if (isEmpty())
            assert tail == null;
        else if(size == 1)
            assert tail == tail.next;
        else
        {
            Node<E> x = tail.next;
            XSet<Node<E>> nodes = new XLinkedHashSet<>();
            for(int i = 0; i < size - 1; i++)
            {
                assert !nodes.contains(x);
                nodes.add(x);
                assert nodes.contains(x);
                x = x.next;
            }
            assert  x == tail;
        }
    }
    
    @Override
    public void enqueue(E e)
    {
        if (tail == null)
        {
            tail = new Node<>(e);
            tail.next = tail; // 循环单链表
        } else
        {
            tail.next = new Node<>(e, tail.next);
            tail = tail.next;
        }
        size++;
//        checkNodeLink(); // debug
    }
    
    @Override
    public E dequeue()
    {
        if (isEmpty())
            throw new NoSuchElementException("The queue is empty");
        Node<E> head = tail.next;
        E retVal = head.e;
        if (head == tail)
            tail = null;
        else tail.next = head.next;
        size--;
//        checkNodeLink(); // debug
        return retVal;
    }
    
    @Override
    public E peek()
    {
        if (isEmpty())
            throw new NoSuchElementException("The queue is empty");
        return tail.next.e;
    }
    
    @Override
    public void clear()
    {
        Node<E> x = tail, t;
        while (x != null)
        {
            x.e = null;
            t = x.next;
            x.next = null;
            x = t;
        }
        tail = null;
        size = 0;
//        checkNodeLink();
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("[");
        if (!isEmpty())
        {
            final String sep = ", ";
            Node<E> head = tail.next;
            Node<E> x = head;
            do
            {
                sb.append(x.e).append(sep);
                x = x.next;
            } while (x != head);
            sb.delete(sb.length() - sep.length(), sb.length());
        }
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public Iterator<E> iterator()
    {
        return new XLinkedQueueXIterator();
    }
    
    private class XLinkedQueueXIterator implements Iterator<E>
    {
        private Node<E> curr;
        
        private XLinkedQueueXIterator()
        {
            curr = (tail == null) ? null : tail.next;
        }
        
        @Override
        public boolean hasNext()
        {
            return curr != null;
        }
        
        @Override
        public E next()
        {
            E retVal = curr.e;
            curr = curr == tail ? null : curr.next;
            return retVal;
        }
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
