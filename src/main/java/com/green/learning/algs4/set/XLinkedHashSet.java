package com.green.learning.algs4.set;


import com.green.learning.algs4.list.XArrayList;
import com.green.learning.algs4.list.XList;
import com.green.learning.algs4.util.MathUtil;

import java.util.Iterator;

/**
 * 链式哈希，每个桶的链表采用Node实现，节省链表的size和list,tail引用的开销
 * {@link java.util.HashMap}
 */
public class XLinkedHashSet<E> implements XSet<E>
{
    // 静态类，节省内存
    private static class Node<T>
    {
        T item;
        Node<T> next;
        
        public Node(T item)
        {
            this.item = item;
        }
        
        public Node(T item, Node<T> next)
        {
            this.item = item;
            this.next = next;
        }
    }
    
    private static int DEFAULT_CAPACITY = 16;
    private static int MAXIMUM_CAPACITY = 1 << 30;
    private int capacity;
    
    private static float DEFAULT_LOAD_FACTOR = 0.75f;
    private float loadFactor;
    
    private int size = 0;
    private Node<E>[] table;
    
    public XLinkedHashSet()
    {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }
    
    public XLinkedHashSet(int initialCapacity)
    {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }
    
    public XLinkedHashSet(int initialCapacity, float loadFactor)
    {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("capacity should be positive");
        if (initialCapacity > MAXIMUM_CAPACITY)
            throw new IllegalArgumentException("maximum capacity is " + MAXIMUM_CAPACITY);
        this.capacity = MathUtil.expandToPowerOf2(initialCapacity);
        if (this.capacity > MAXIMUM_CAPACITY) this.capacity = MAXIMUM_CAPACITY;
        
        if (loadFactor <= 0)
            throw new IllegalArgumentException("load factor should be positive.");
        this.loadFactor = loadFactor;
        
        table = (Node<E>[]) new Node[capacity];
    }
    
    public XLinkedHashSet(Iterable<E> iterable)
    {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
        for (E e : iterable)
            add(e);
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
        if (e == null) return 0; // 支持null
        return e.hashCode() & (capacity - 1); // 采用位操作&代替%
    }
    
    @Override
    public boolean contains(E e)
    {
        Node<E> curr = table[h(e)];
        if (curr != null)
        {
            if (e != null)
            {
                for (; curr != null; curr = curr.next)
                    if (curr.item.equals(e))
                        return true;
            } else
                for (; curr != null; curr = curr.next)
                    if (curr.item == null)
                        return true;
        }
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
        Node<E>[] newTable = (Node<E>[]) new Node[newCapacity];
        for (Node<E> node : table)
            if (node != null)
            {
                for (; node != null; node = node.next)
                {
                    int hashVal = h(node.item);
                    if (newTable[hashVal] == null)
                        newTable[hashVal] = new Node<>(node.item);
                    else
                    {
                        Node<E> curr = newTable[hashVal];
                        while (curr.next != null)
                            curr = curr.next;
                        curr.next = new Node<>(node.item);
                    }
                }
            }
        
        table = newTable;
    }
    
    @Override
    public boolean add(E e)
    {
        if (contains(e))
            return false;
        
        ensureCapacity();
        int hashVal = h(e);
        if (table[hashVal] == null)
            table[hashVal] = new Node<>(e);
        else
        {
            // because the set doesn't contain the item,
            // so it's no need to check whether some items will equal to {@code e}
            Node<E> curr = table[hashVal];
            while (curr.next != null)
                curr = curr.next;
            curr.next = new Node<>(e);
        }
        size++;
        return true;
    }
    
    @Override
    public boolean remove(E e)
    {
        int hashVal = h(e);
        Node<E> curr = table[hashVal], pre = null;
        if (curr != null)
        {
            if (e == curr.item || e != null && e.equals(curr.item))
            {
                table[hashVal] = curr.next;
                size--;
                return true;
            } else if (e == null)
            {
                while (curr != null)
                {
                    if (curr.item == null)
                    {
                        pre.next = curr.next;
                        size--;
                        return true;
                    }
                    pre = curr;
                    curr = curr.next;
                }
            } else
            {
                while (curr != null)
                {
                    if (e.equals(curr.item))
                    {
                        pre.next = curr.next;
                        size--;
                        return true;
                    }
                    pre = curr;
                    curr = curr.next;
                }
            }
        }
        
        return false;
    }
    
    @Override
    public void clear()
    {
        for (int i = 0; i < table.length; i++)
            if (table[i] != null)
            {
                Node<E> curr = table[i], pre = null;
                while (curr != null)
                {
                    curr.item = null;
                    pre = curr;
                    curr = curr.next;
                    pre.next = null;
                }
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
        for (Node<E> node : table)
            if (node != null)
            {
                while (node != null)
                {
                    sb.append(node.item).append(sep);
                    node = node.next;
                }
            }
        sb.delete(sb.lastIndexOf(sep), sb.length());
        sb.append("]");
        return sb.toString();
    }
    
    public XList<E> setToList()
    {
        XList<E> elements = new XArrayList<>();
        for (Node<E> node : table)
            if (node != null)
                while (node != null)
                {
                    elements.append(node.item);
                    node = node.next;
                }
        return elements;
    }
    
    @Override
    public Iterator<E> iterator()
    {
        return setToList().iterator();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof XSet)) return false;
        XSet<E> set = (XSet<E>) obj;
        for (E e : set)
            if (!contains(e))
                return false;
        return true;
    }
}
