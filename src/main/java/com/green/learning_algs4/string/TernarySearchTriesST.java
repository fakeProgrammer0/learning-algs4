package com.green.learning_algs4.string;

import com.green.learning_algs4.set.XLinkedHashSet;
import com.green.learning_algs4.set.XSet;
import com.green.learning_algs4.string.OrderedStringST;

import java.util.Iterator;

/**
 * @param <V> value type
 * @see OrderedStringST for string symbol table
 * @see edu.princeton.cs.algs4.TST for similar implementation
 */
public class TernarySearchTriesST<V> extends AbstractTST
        implements OrderedStringST<V>
{
    private static class Node extends AbstractTST.Node
    {
        private Object value;
        
        private Node(char c)
        {
            super(c);
        }
        
        @Override
        protected boolean isEndOfStr()
        {
            return value != null;
        }
        
        @Override
        protected void removeEndOfStr()
        {
            value = null;
        }
        
        @Override
        protected Object getValue()
        {
            return value;
        }
    }
    
    public TernarySearchTriesST()
    {
    }
    
    @Override
    public void put(String key, V value)
    {
        validateStr(key);
        char c = key.charAt(0);
        if (root == null) root = new Node(c);
        AbstractTST.Node x = root;
        int d = 0;
        while (true)
            if (c < x.c)
            {
                if (x.left == null)
                    x.left = new Node(c);
                x = x.left;
            } else if (c > x.c)
            {
                if (x.right == null)
                    x.right = new Node(c);
                x = x.right;
            } else
            {
                d++;
                if (d == key.length()) break;
                c = key.charAt(d);
                if (x.mid == null)
                    x.mid = new Node(c);
                x = x.mid;
            }
        
        Object retVal = x.getValue();
        if (!x.isEndOfStr())
            size++;
        Node node = (Node) x;
        node.value = value;
        checkNode();
//        return retVal;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public V get(String key)
    {
        AbstractTST.Node x = getNode(key);
        return x == null ? null : (V) x.getValue();
    }
    
    @Override
    public void remove(String key)
    {
        Object retVal = removeNode(key);
    }
    
    @Override
    public boolean containsKey(String key)
    {
        return super.contains(key);
    }
    
    @Override
    public XSet<Entry<String, V>> entries()
    {
        XSet<Entry<String, V>> entries = new XLinkedHashSet<>(size);
        collect(root, new StringBuilder(), entries);
        return entries;
    }
    
    @SuppressWarnings("unchecked")
    private void collect(AbstractTST.Node x, StringBuilder prefixBuilder, XSet<Entry<String, V>> set)
    {
        if (x == null) return;
        
        collect(x.left, prefixBuilder, set);
        
        prefixBuilder.append(x.c);
        if (x.isEndOfStr())
            set.add(new Entry<>(prefixBuilder.toString(), (V) x.getValue()));
        collect(x.mid, prefixBuilder, set);
        prefixBuilder.deleteCharAt(prefixBuilder.length() - 1);
        
        collect(x.right, prefixBuilder, set);
    }
    
    @Override
    public Iterator<Entry<String, V>> iterator()
    {
        return entries().iterator();
    }
}
