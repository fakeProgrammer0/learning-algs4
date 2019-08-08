package com.green.learning_algs4.string;

import com.green.learning_algs4.list.XLinkedQueue;
import com.green.learning_algs4.list.XQueue;
import com.green.learning_algs4.set.XLinkedHashSet;
import com.green.learning_algs4.set.XSet;
import com.green.learning_algs4.st.OrderedStringST;

import java.util.Iterator;

/**
 * @see OrderedStringST for string symbol table
 * @see edu.princeton.cs.algs4.TST for similar implementation
 */
public class TernarySearchTriesSet
{
    private static class Node
    {
        private char c;
        private boolean key;
        
        private Node left, mid, right;
        
        private Node(char c)
        {
            this.c = c;
        }
    }
    
    private Node root;
    private int size = 0;
    
    public TernarySearchTriesSet()
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

//    private boolean invalidKey(String key)
//    {
//        return key == null || key.isEmpty();
//    }
    
    private void validateKey(String key)
    {
        if (key == null || key.isEmpty())
            throw new IllegalArgumentException("null key or empty key isn't supported");
    }
    
    
    public void add(String key)
    {
        validateKey(key);
        root = put(root, key, 0);
    }
    
    private void put(String key)
    {
        // TODO
    }
    
    private Node put(Node x, String key, int d)
    {
        char c = key.charAt(d);
        if (x == null) x = new Node(c);
        if (c < x.c) x.left = put(x.left, key, d);
        else if (c > x.c) x.right = put(x.right, key, d);
        else if (d < key.length() - 1)
            x.mid = put(x.mid, key, d + 1);
        else
        {
            if (!x.key) size++;
            x.key = true;
        }
        
        return x;
    }
    
    /**
     * get the node with specific key
     * Non recursive version
     *
     * @param key string key
     * @return the node with specific key
     */
    private Node get(String key)
    {
//        assert !key.isEmpty();
        Node x = root;
        int d = 0;
        while (x != null)
        {
            char c = key.charAt(d);
            if (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else
            {
                d++;
                if (d == key.length()) break;
                x = x.mid;
            }
        }
        return x;
    }
    
    
    public void remove(String key)
    {
        validateKey(key);
        root = remove(root, key, 0);
    }
    
    private Node remove(Node x, String key, int d)
    {
        if (x == null) return null; // null link
        char c = key.charAt(d);
        if (c < x.c) x.left = remove(x.left, key, d);
        else if (c > x.c) x.right = remove(x.right, key, d);
        else if (d < key.length() - 1)
            x.mid = remove(x.mid, key, d + 1);
        else
        {
            if (x.key) size--;
            x.key = false;
        }
        
        // 1.lazy implementation
        if (!x.key && x.mid == null
                && x.left == null && x.right == null)
            return null;
        return x;
    }
    
    /**
     * lazy implementation
     */
    
    public void clear()
    {
        root = null;
        size = 0;
    }
    
    
    public boolean containsKey(String key)
    {
        Node x = get(key);
        if (x == null) return false;
        return x.key;
    }
    
    
    public XSet<String> keys()
    {
        XSet<String> set = new XLinkedHashSet<>(size);
        collect(root, new StringBuilder(), set);
        return set;
    }
    
    
    private void collect(Node x, StringBuilder prefixBuilder, XSet<String> set)
    {
        if (x == null) return;
        
        collect(x.left, prefixBuilder, set);
        
        prefixBuilder.append(x.c);
        if (x.key)
            set.add(prefixBuilder.toString());
        collect(x.mid, prefixBuilder, set);
        prefixBuilder.deleteCharAt(prefixBuilder.length() - 1);
        
        collect(x.right, prefixBuilder, set);
    }
    
    
    public Iterable<String> orderKeys()
    {
        XQueue<String> queue = new XLinkedQueue<>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }
    
    
    public Iterable<String> keysWithPrefix(String prefix)
    {
//        validateKey(prefix);
        if (prefix == null)
            throw new IllegalArgumentException("null prefix isn't supported");
        if (prefix.isEmpty())
            return orderKeys();
        
        Node x = get(prefix);
        XQueue<String> queue = new XLinkedQueue<>();
        if (x != null)
        {
            if (x.key) queue.enqueue(prefix);
            collect(x.mid, new StringBuilder(prefix), queue);
        }
        return queue;
    }
    
    private void collect(Node x, StringBuilder prefixBuilder, XQueue<String> queue)
    {
        if (x == null) return;
        
        if (x.left != null) // this line is duplicate code, just for better performance
            collect(x.left, prefixBuilder, queue);
        
        if (x.key)
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
    
    
    public Iterable<String> keysMatch(String pattern)
    {
        validateKey(pattern);
        XQueue<String> queue = new XLinkedQueue<>();
        wildcardMatch(root, new StringBuilder(), pattern, queue);
        return queue;
    }
    
    private void wildcardMatch(Node x, StringBuilder prefixBuilder, String pattern, XQueue<String> queue)
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
            } else if (x.key)
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
                } else if (x.key)
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
    
    
    public String longestPrefixKeyMatch(String query)
    {
//        validateKey(query);
        if (query == null)
            throw new IllegalArgumentException("null prefix isn't supported");
        if (query.isEmpty())
            return query;

//        int len = search(root, query, 0, 0);
        
        int len = 0;
        Node x = root;
        int d = 0;
        while (x != null && d < query.length())
        {
            char c = query.charAt(d);
            if (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else
            {
                d++;
                if (x.key) len = d;
                x = x.mid;
            }
        }
        
        return query.substring(0, len);
    }

//    private int search(Node x, String query, int d, int length)
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
//            if (x.key)
//                len2 = d + 1;
//            len2 = search(x.mid, query, d + 1, len2);
//        }
//
//        int len3 = search(x.right, query, d, length);
//
//        return Math.max(Math.max(len1, len2), len3);
//    }
    
    
    public String longestCommonPrefix(String query)
    {
        validateKey(query);
//        int len = search(root, query, 0);
        
        int d = 0;
        Node x = root;
        while (x != null && d < query.length())
        {
            char c = query.charAt(d);
            if (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else
            {
                d++;
                x = x.mid;
            }
        }
        
        return query.substring(0, d);
    }
    
    public Iterator<String> iterator()
    {
        return orderKeys().iterator();
    }
}
