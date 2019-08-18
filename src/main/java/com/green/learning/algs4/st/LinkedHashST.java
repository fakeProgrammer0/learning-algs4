package com.green.learning.algs4.st;

import com.green.learning.algs4.set.XLinkedHashSetX;
import com.green.learning.algs4.set.XSet;
import com.green.learning.algs4.util.MathUtil;

import java.util.Iterator;

/**
 * {@link java.util.HashMap} 有很多值得借鉴的地方
 * @param <K> key type
 * @param <V> value type
 */
public class LinkedHashST<K, V> implements ST<K, V>
{
    private static class Node<K, V>
    {
        private final K key;
        private V value;
        private Node<K, V> next;
        
        public Node(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
        
        public Node(K key, V value, Node<K, V> next)
        {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        public Node(Node<K, V> node)
        {
            if (node == null)
                throw new IllegalArgumentException();
            this.key = node.key;
            this.value = node.value;
            //            this(node.key, node.value);
        }
    
        @Override
        public String toString()
        {
            return "<" + key + ", " + value + ">";
        }
    }
    
    private static int DEFAULT_CAPACITY = 16;
    private static int MAXIMUM_CAPACITY = 1 << 30;
    private int capacity;
    
    private static float DEFAULT_LOAD_FACTOR = 0.75f;
    private float loadFactor;
    
    private int size = 0;
    private Node<K, V>[] table;
    
    public LinkedHashST()
    {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }
    
    public LinkedHashST(int initialCapacity)
    {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }
    
    public LinkedHashST(int initialCapacity, float loadFactor)
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
        
        table = (Node<K, V>[]) new Node[capacity];
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
    
    private int h(K key)
    {
        if (key == null) return 0; // 支持null
        return key.hashCode() & (capacity - 1); // 采用位操作&代替%
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
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];
        for (Node<K, V> node : table)
            if (node != null)
            {
                for (; node != null; node = node.next)
                {
                    int hashVal = h(node.key);
                    if (newTable[hashVal] == null)
                        newTable[hashVal] = new Node<>(node); // 存储在头节点
                    else
                    {
                        Node<K, V> curr = newTable[hashVal];
                        while (curr.next != null)
                            curr = curr.next;
                        curr.next = new Node<>(node);
                    }
                }
            }
        
        table = newTable;
    }
    
    @Override
    public V get(K key)
    {
        Node<K, V> curr = table[h(key)];
        if (curr != null)
        {
            if (key != null)
            {
                for (; curr != null; curr = curr.next)
                    if (curr.key.equals(key))
                        return curr.value;
            } else
                for (; curr != null; curr = curr.next)
                    if (curr.key == null)
                        return curr.value;
        }
        return null;
    }
    
    @Override
    public boolean containsKey(K key)
    {
        return get(key) != null;
    }
    
    @Override
    public void put(K key, V value)
    {
        if (value == null)
            throw new IllegalArgumentException("value should not be null.");
        ensureCapacity();
        int hashVal = h(key);
        if (table[hashVal] == null)
            table[hashVal] = new Node<>(key, value);
        else
        {
            Node<K, V> curr = table[hashVal], pre = null;
            if (key == curr.key || key != null && key.equals(curr.key))
            {
                curr.value = value;
                return;
            } else if (key != null)
            {
                do
                {
                    if (key.equals(curr.key))
                    {
                        curr.value = value;
                        return;
                    }
                    pre = curr;
                    curr = curr.next;
                }while (curr != null);
            } else
            {
                do
                {
                    if (curr.key == null)
                    {
                        curr.value = value;
                        return;
                    }
                    pre = curr;
                    curr = curr.next;
                }while (curr != null);
            }
            pre.next = new Node<>(key, value);
        }
        size++;
    }
    
    @Override
    public void remove(K key)
    {
        int hashVal = h(key);
        Node<K, V> curr = table[hashVal], pre = null;
        if (curr != null)
        {
            if (key == curr.key || key != null && key.equals(curr.key))
            {
                table[hashVal] = curr.next;
                size--;
            } else if (key == null)
            {
                do
                {
                    if (curr.key == null)
                    {
                        pre.next = curr.next;
                        size--;
                    }
                    pre = curr;
                    curr = curr.next;
                } while (curr != null);
            } else
            {
                do
                {
                    if (key.equals(curr.key))
                    {
                        pre.next = curr.next;
                        size--;
                    }
                    pre = curr;
                    curr = curr.next;
                } while (curr != null);
            }
        }
    }
    
    @Override
    public void clear()
    {
        for (int i = 0; i < table.length; i++)
            if (table[i] != null)
            {
                Node<K, V> curr = table[i], pre = null;
                while (curr != null)
                {
                    curr.value = null;
                    pre = curr;
                    curr = curr.next;
                    pre.next = null;
                }
                table[i] = null;
            }
        size = 0;
    }
    
    @Override
    public XSet<K> keys()
    {
        XSet<K> keySet = new XLinkedHashSetX<>(size);
        for (Node<K, V> node : table)
            if (node != null)
                while (node != null)
                {
                    keySet.add(node.key);
                    node = node.next;
                }
        return keySet;
    }
    
    @Override
    public XSet<ST.Entry<K, V>> entries()
    {
        XSet<ST.Entry<K, V>> entrySet = new XLinkedHashSetX<>(size);
        for (Node<K, V> node : table)
            if (node != null)
                while (node != null)
                {
                    entrySet.add(new Entry<>(node.key, node.value));
                    node = node.next;
                }
        return entrySet;
    }
    
    @Override
    public Iterator<Entry<K, V>> iterator()
    {
        return entries().iterator();
    }
    
    @Override
    public String toString()
    {
        if (size == 0)
            return "[]";
        
        final String sep = ",";
        StringBuilder sb = new StringBuilder("[");
        for (Node<K, V> node : table)
            if (node != null)
            {
                while (node != null)
                {
                    sb.append(node.toString()).append(sep);
                    node = node.next;
                }
            }
        sb.delete(sb.lastIndexOf(sep), sb.length());
        sb.append("]");
        return sb.toString();
    }
}
