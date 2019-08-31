package com.green.learning.algs4.string.trie;

import com.green.learning.algs4.set.XLinkedHashSet;
import com.green.learning.algs4.set.XSet;
import com.green.learning.algs4.string.Alphabet;

import java.util.Iterator;

/**
 * A String symbol table implemented by R-way tries. It implements several
 * character-based operations specified in {@code OrderedStringST} interface.
 * It also accepts empty string as a key.
 * @param <V> value type
 * @see OrderedStringST for string symbol table
 * @see edu.princeton.cs.algs4.TrieST for similar implementation
 */
public class TrieST<V> extends AbstractTrie
        implements OrderedStringST<V>
{
    private static class Node extends AbstractTrie.Node
    {
        private Object value;
    
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
        protected Object getNodeValue()
        {
            return value;
        }
    }
    
    public TrieST(Alphabet alphabet)
    {
       super(alphabet);
    }
    
    /**
     * Store a key and its associated value in the tries. When {@code key} also exists in
     * the tries, just update its associated value.
     * @param key   string key, can be an empty string
     * @param value the associated value
     * @throws IllegalArgumentException if either {@code key} or {@code value} is null
     *                                  if {@code key} contains any character not in the {@code alphabet}
     */
    @Override
    public void put(String key, V value)
    {
        validateStr(key);
        if (value == null)
            throw new IllegalArgumentException("null value isn't supported");
    
        if (root == null)
            root = new Node();
    
        AbstractTrie.Node x = root;
        for (int d = 0; d < key.length(); d++)
        {
            if (x.next == null)
                x.next = new Node[R];
            int index = alphabet.toIndex(key.charAt(d));
            if (x.next[index] == null)
            {
                x.next[index] = new Node(); // TriesSet.Node
                x.kids++;
            }
            x = x.next[index];
        }
    
//        V retVal = (V) x.getNodeValue();
        Node node = (Node) x;
        node.value = value;
        size++;
        checkNodeSize(); // for debug
//        return retVal;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public V get(String key)
    {
        AbstractTrie.Node x = getNode(key);
        if(x == null) return null;
        return (V) x.getNodeValue();
    }
    
    @Override
    public void remove(String key)
    {
        Object retVal = removeNode(key);
    }
    
    @Override
    public boolean containsKey(String key)
    {
        return contains(key);
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
    
    @SuppressWarnings("unchecked")
    private void collect(AbstractTrie.Node x, StringBuilder prefix, XSet<Entry<String, V>> set)
    {
        if (x == null) return;
        if (x.isEndOfStr())
            set.add(new Entry<>(prefix.toString(), (V) x.getNodeValue()));
        
        if (x.next != null)
            for (int r = 0; r < R; r++)
                if (x.next[r] != null) // optimized
                {
                    prefix.append(alphabet.toChar(r));
                    collect(x.next[r], prefix, set);
                    prefix.deleteCharAt(prefix.length() - 1);
                }
    }
    
    @Override
    public Iterator<Entry<String, V>> iterator()
    {
        return entries().iterator();
    }
}
