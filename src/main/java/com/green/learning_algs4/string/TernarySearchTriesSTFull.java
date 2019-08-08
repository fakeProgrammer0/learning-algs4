package com.green.learning_algs4.string;

import com.green.learning_algs4.list.XLinkedQueue;
import com.green.learning_algs4.list.XQueue;
import com.green.learning_algs4.set.XLinkedHashSet;
import com.green.learning_algs4.set.XSet;

import java.util.Iterator;

/**
 * @param <V> value type
 * @see OrderedStringST for string symbol table
 * @see edu.princeton.cs.algs4.TST for similar implementation
 */
public class TernarySearchTriesSTFull<V> implements OrderedStringST<V>
{
    private static class Node<Value>
    {
        private char c;
        private Value value;
        
        private Node<Value> left, mid, right;
        
        public Node(char c)
        {
            this.c = c;
        }
    }
    
    private Node<V> root;
    private int size = 0;
    
    public TernarySearchTriesSTFull()
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

//    private boolean invalidKey(String key)
//    {
//        return key == null || key.isEmpty();
//    }
    
    private void validateKey(String key)
    {
        if (key == null || key.isEmpty())
            throw new IllegalArgumentException("null key or empty key isn't supported");
    }
    
    @Override
    public void put(String key, V value)
    {
        validateKey(key);
        if (value == null) throw new IllegalArgumentException("null value isn't supported");
        
        root = put(root, key, value, 0);
    }
    
    private Node<V> put(Node<V> x, String key, V value, int d)
    {
        char c = key.charAt(d);
        if (x == null) x = new Node<>(c);
        if (c < x.c) x.left = put(x.left, key, value, d);
        else if (c > x.c) x.right = put(x.right, key, value, d);
        else if (d < key.length() - 1)
            x.mid = put(x.mid, key, value, d + 1);
        else
        {
            if (x.value == null) size++;
            x.value = value;
        }
        
        return x;
    }
    
    @Override
    public V get(String key)
    {
        validateKey(key);
//        Node<V> x = get(root, key, 0);
        Node<V> x = get(root, key);
        return x == null ? null : x.value;
    }
    
    private Node<V> get(Node<V> x, String key, int d)
    {
        if (x == null) return null;
//        assert key.length() > 0;
        char c = key.charAt(d);
        if (c < x.c) return get(x.left, key, d);
        else if (c > x.c) return get(x.right, key, d);
        else if (d < key.length() - 1)
            return get(x.mid, key, d + 1);
        else return x;
    }
    
    /**
     * get the node with specific key
     * Non recursive version
     * @param x search start node
     * @param key string key
     * @return the node with specific key
     */
    private Node<V> get(Node<V> x, String key)
    {
//        Node<V> x = root;
//        assert !key.isEmpty();
        int d = 0;
        while (x != null)
        {
            char c = key.charAt(d);
            if (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else {
                d++;
                if(d == key.length()) break;
                x = x.mid;
            }
        }
        return x;
    }
    
    @Override
    public void remove(String key)
    {
        validateKey(key);
        root = remove(root, key, 0);
    }
    
    private Node<V> remove(Node<V> x, String key, int d)
    {
        if (x == null) return null; // null link
        char c = key.charAt(d);
        if (c < x.c) x.left = remove(x.left, key, d);
        else if (c > x.c) x.right = remove(x.right, key, d);
        else if (d < key.length() - 1)
            x.mid = remove(x.mid, key, d + 1);
        else
        {
            if (x.value != null) size--;
            x.value = null;
        }
        
        // 1.lazy implementation
        if (x.value == null && x.mid == null
                && x.left == null && x.right == null)
            return null;
        return x;
    }
    
    /**
     * lazy implementation
     */
    @Override
    public void clear()
    {
        root = null;
        size = 0;
    }
    
    @Override
    public boolean containsKey(String key)
    {
        return get(key) != null;
    }
    
    @Override
    public XSet<String> keys()
    {
        XSet<String> set = new XLinkedHashSet<>(size);
        for (Entry<String, V> entry : entries())
            set.add(entry.getKey());
        
        return set;
    }
    
    @Override
    public XSet<Entry<String, V>> entries()
    {
        XSet<Entry<String, V>> entries = new XLinkedHashSet<>(size);
        collect(root, new StringBuilder(), entries);
        return entries;
    }
    
    private void collect(Node<V> x, StringBuilder prefixBuilder, XSet<Entry<String, V>> set)
    {
        if (x == null) return;
        
        collect(x.left, prefixBuilder, set);
        
        prefixBuilder.append(x.c);
        if (x.value != null)
            set.add(new Entry<>(prefixBuilder.toString(), x.value));
        collect(x.mid, prefixBuilder, set);
        prefixBuilder.deleteCharAt(prefixBuilder.length() - 1);
        
        collect(x.right, prefixBuilder, set);
    }
    
    @Override
    public Iterable<String> orderKeys()
    {
        XQueue<String> queue = new XLinkedQueue<>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }
    
    @Override
    public Iterable<String> keysWithPrefix(String prefix)
    {
//        validateKey(prefix);
        if (prefix == null)
            throw new IllegalArgumentException("null prefix isn't supported");
        if (prefix.isEmpty())
            return orderKeys();
        
//        Node<V> x = get(root, prefix, 0);
        Node<V> x = get(root, prefix);
        XQueue<String> queue = new XLinkedQueue<>();
        if (x != null)
        {
            if (x.value != null) queue.enqueue(prefix);
            collect(x.mid, new StringBuilder(prefix), queue);
        }
        return queue;
    }
    
    private void collect(Node<V> x, StringBuilder prefixBuilder, XQueue<String> queue)
    {
        if (x == null) return;
        
        if (x.left != null) // this line is duplicate code, just for better performance
            collect(x.left, prefixBuilder, queue);
        
        if (x.value != null)
        {
            prefixBuilder.append(x.c);
            queue.enqueue(prefixBuilder.toString());
            prefixBuilder.deleteCharAt(prefixBuilder.length() - 1);
        }
        if (x.mid != null) // this line is duplicate code, just for better performance
        {
            prefixBuilder.append(x.c);
            collect(x.mid, prefixBuilder, queue);
            prefixBuilder.deleteCharAt(prefixBuilder.length() - 1);
        }
        
        if (x.right != null) // this line is duplicate code, just for better performance
            collect(x.right, prefixBuilder, queue);
    }
    
    private final static char WILDCARD_CHARACTER = '.';
    
    @Override
    public Iterable<String> keysMatch(String pattern)
    {
        validateKey(pattern);
        XQueue<String> queue = new XLinkedQueue<>();
        wildcardMatch(root, new StringBuilder(), pattern, queue);
        return queue;
    }
    
    private void wildcardMatch(Node<V> x, StringBuilder prefixBuilder, String pattern, XQueue<String> queue)
    {
        if (x == null) return;
        int d = prefixBuilder.length();
//        if (d == pattern.length()) return;
        
        char c = pattern.charAt(d);
        if (c == WILDCARD_CHARACTER)
        {
            if (x.left != null) // duplicate line, just for better performance
                wildcardMatch(x.left, prefixBuilder, pattern, queue);
            
            prefixBuilder.append(x.c);
            if (prefixBuilder.length() < pattern.length())
            {
                if (x.mid != null) // duplicate line, just for better performance
                    wildcardMatch(x.mid, prefixBuilder, pattern, queue);
            } else if (x.value != null)
                queue.enqueue(prefixBuilder.toString());
            prefixBuilder.deleteCharAt(prefixBuilder.length() - 1);
            
            if (x.right != null) // duplicate line, just for better performance
                wildcardMatch(x.right, prefixBuilder, pattern, queue);
        } else
        {
            if (c == x.c)
            {
                prefixBuilder.append(x.c);
                if (prefixBuilder.length() < pattern.length())
                {
                    if (x.mid != null) // duplicate line, just for better performance
                        wildcardMatch(x.mid, prefixBuilder, pattern, queue);
                } else if (x.value != null)
                    queue.enqueue(prefixBuilder.toString());
                prefixBuilder.deleteCharAt(prefixBuilder.length() - 1);
            } else if (c < x.c)
            {
                if (x.left != null) // duplicate line, just for better performance
                    wildcardMatch(x.left, prefixBuilder, pattern, queue);
            } else if (x.right != null) // duplicate line, just for better performance
                wildcardMatch(x.right, prefixBuilder, pattern, queue);
        }
    }
    
    @Override
    public String longestPrefixKeyMatch(String query)
    {
//        validateKey(query);
        if (query == null)
            throw new IllegalArgumentException("null prefix isn't supported");
        if (query.isEmpty())
            return query;
        
//        int len = search(root, query, 0, 0);
        
        int len = 0;
        Node<V> x = root;
        int d = 0;
        while (x != null && d < query.length())
        {
            char c = query.charAt(d);
            if(c < x.c) x = x.left;
            else if(c > x.c) x = x.right;
            else {
                d++;
                if(x.value != null) len = d;
                x = x.mid;
            }
        }
        
        return query.substring(0, len);
    }
    
//    private int search(Node<V> x, String query, int d, int length)
//    {
//        if (x == null) return length; // null root
//        if (d == query.length()) return length;
//
//        int len1 = search(x.left, query, d, length);
//
//        char c = query.charAt(d);
//        int len2 = length;
//        if (c == x.c)
//        {
//            if (x.value != null)
//                len2 = d + 1;
//            len2 = search(x.mid, query, d + 1, len2);
//        }
//
//        int len3 = search(x.right, query, d, length);
//
//        return Math.max(Math.max(len1, len2), len3);
//    }
    
    @Override
    public String longestCommonPrefix(String query)
    {
        validateKey(query);
//        int len = search(root, query, 0);
        
        int d = 0;
        Node<V> x = root;
        while (x != null && d < query.length())
        {
            char c = query.charAt(d);
            if(c < x.c) x = x.left;
            else if(c > x.c) x = x.right;
            else {
                d++;
                x = x.mid;
            }
        }
        
        return query.substring(0, d);
    }
    
//    private int search(Node<V> x, String query, int d)
//    {
//        if (x == null) return d;
//        if (d == query.length()) return d;
//
//        int len1 = search(x.left, query, d);
//
//        char c = query.charAt(d);
//        int len2 = (c == x.c) ? search(x.mid, query, d + 1) : d;
//
//        int len3 = search(x.right, query, d);
//
//        return Math.max(Math.max(len1, len2), len3);
//    }
    
    @Override
    public Iterator<Entry<String, V>> iterator()
    {
        return entries().iterator();
    }
}
