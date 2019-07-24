package com.green.learning_algs4.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class XLinkedStack<E> implements XStack<E>
{
    protected static class Node<Item>
    {
        protected Item item;
        protected Node<Item> next;
    
        private Node(Item item)
        {
            this.item = item;
        }
    
        private Node(Item item, Node<Item> next)
        {
            this.item = item;
            this.next = next;
        }
    }
    
    protected int size = 0;
    protected Node<E> head;
    
    public XLinkedStack()
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
    
    @Override
    public void push(E e)
    {
        head = new Node<>(e, head);
        size++;
    }
    
    protected void checkEmpty()
    {
        if(isEmpty()) throw new NoSuchElementException("The stack is empty.");
    }
    
    @Override
    public E peek()
    {
        checkEmpty();
        return head.item;
    }
    
    @Override
    public E pop()
    {
        checkEmpty();
        E item = head.item;
        Node<E> node = head;
        head = head.next;
        // help gc
        node.item = null;
        node.next = null;
        size--;
        return item;
    }
    
    @Override
    public void clear()
    {
        for(Node<E> x = head; x != null; x = head.next)
        {
            head.next = x.next;
            x.item = null;
            x.next = null;
        }
        head = null;
        size = 0;
    }
    
    @Override
    public String toString()
    {
        if(isEmpty()) return "[]";
        final String elementSep = ", ";
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        for (; curr.next != null; curr = curr.next)
            sb.append(curr.item).append(elementSep);
        sb.append(curr.item);
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public Iterator<E> iterator()
    {
        return new LinkedStackIterator();
    }
    
    private class LinkedStackIterator implements Iterator<E>
    {
        private Node<E> current;
    
        public LinkedStackIterator()
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
            if(!hasNext())
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
