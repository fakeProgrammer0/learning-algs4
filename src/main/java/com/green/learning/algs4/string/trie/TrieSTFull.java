package com.green.learning.algs4.string.trie;

import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XQueue;
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
public class TrieSTFull<V> implements OrderedStringST<V>
{
    private static class Node<Value>
    {
        // number of children
        // not number of keys under this node!!
        private int kids = 0;
        private Value value;
        private Node<Value>[] next; // an array with length R
    }
    
    private final Alphabet alphabet;
    private final int R;
    
    private int size = 0;
    
    private Node<V> root; // default null
    
    public TrieSTFull(Alphabet alphabet)
    {
        this.alphabet = alphabet;
        this.R = alphabet.radix();
    }
    
    @SuppressWarnings("unchecked")
    private void initNodeNextIfNeeded(Node<V> x)
    {
        if (x.next == null)
            x.next = (Node<V>[]) new Node[R];
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
    
    private boolean invalidKey(String key)
    {
        if (key == null) return true;
        
        // allow empty string
        for (int i = 0; i < key.length(); i++)
            if (!alphabet.contains(key.charAt(i)))
                return true;
        return false;
    }
    
    private void validateNodeSize()
    {
        if (isEmpty())
            assert size == 0 && root == null;
        else validateNodeSize(root);
    }
    
    private void validateNodeSize(Node<V> x)
    {
        if (x.kids == 0)
        {
            assert x.next == null;
            return;
        }
        
        int cnt = 0;
        for (int r = 0; r < R; r++)
            if (x.next[r] != null)
            {
                cnt++;
                validateNodeSize(x.next[r]);
            }
        assert cnt == x.kids;
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
        if (key == null) throw new IllegalArgumentException("null key isn't supported");
        if (value == null) throw new IllegalArgumentException("null value isn't supported");
        int[] keyIndices = alphabet.toIndices(key);
        root = put(root, keyIndices, value, 0); // update root
//        validateNodeSize(); // for debug
    }
    
    private Node<V> put(Node<V> x, int[] keyIndices, V value, int d)
    {
        if (x == null) x = new Node<>();
        if (d == keyIndices.length)
        {
            if (x.value == null) size++;
            x.value = value;
        } else
        {
            initNodeNextIfNeeded(x);
            int index = keyIndices[d];
            if (x.next[index] == null) x.kids++;
            x.next[index] = put(x.next[index], keyIndices, value, d + 1);
        }
        
        return x;
    }
    
    @Override
    public V get(String key)
    {
        // simplify operation
        if (key == null)
            throw new IllegalArgumentException("null key isn't supported");
        
        if (invalidKey(key)) return null;
        
        int[] keyIndices = alphabet.toIndices(key);
        Node<V> x = get(root, keyIndices, 0);
        return (x == null) ? null : x.value;
    }
    
    private Node<V> get(Node<V> x, int[] keyIndices, int d)
    {
        if (x == null) return null; // null root or null link of empty leaf
        if (d == keyIndices.length) return x;
        if (x.next == null) return null; // x is leaf, has no kids
        
        int index = keyIndices[d];
        return get(x.next[index], keyIndices, d + 1);
    }
    
    @Override
    public void remove(String key)
    {
        if (invalidKey(key)) return;
        
        int[] keyIndices = alphabet.toIndices(key);
        root = remove(root, keyIndices, 0); // update root
//        validateNodeSize(); // for debug
    }
    
    private Node<V> remove(Node<V> x, int[] keyIndices, int d)
    {
        if (x == null) return null;
        if (d == keyIndices.length)
        {
            if (x.value != null)
            {
                x.value = null;
                size--;
            }
        } else
        {
            int index = keyIndices[d];
            if (x.next == null || x.next[index] == null) return x;
            x.next[index] = remove(x.next[index], keyIndices, d + 1);
            if (x.next[index] == null) x.kids--;
        }
        
        if (x.kids == 0)
        {
            x.next = null;
            if (x.value == null) return null;
        }
        return x;
    }
    
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
    
    private void collect(Node<V> x, StringBuilder prefix, XSet<Entry<String, V>> set)
    {
        if (x == null) return;
        if (x.value != null)
            set.add(new Entry<>(prefix.toString(), x.value));
        
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
    
    @Override
    public Iterable<String> orderKeys()
    {
//        XQueue<String> queue = new XArrayQueue<>(size);
//        StringBuilder prefix = new StringBuilder();
//        collect(root, prefix, queue);
//        return queue;
        return keysWithPrefix("");
    }
    
    private void collect(Node<V> x, StringBuilder prefix, XQueue<String> queue)
    {
        if (x == null) return;
        if (x.value != null)
            queue.enqueue(prefix.toString());
        
        if (x.next != null)
            for (int r = 0; r < R; r++)
                if (x.next[r] != null) // optimized
                {
                    prefix.append(alphabet.toChar(r));
                    collect(x.next[r], prefix, queue);
                    prefix.deleteCharAt(prefix.length() - 1);
                }
    }
    
    @Override
    public Iterable<String> keysWithPrefix(String prefix)
    {
        int[] keyIndices = alphabet.toIndices(prefix);
        Node<V> x = get(root, keyIndices, 0);
        XQueue<String> queue = new XLinkedQueue<>();
        if (x == null) return queue;
        collect(x, new StringBuilder(prefix), queue);
        return queue;
    }
    
    private final static char WILDCAST_TOKEN = '.';
    
    @Override
    public Iterable<String> keysMatch(String pattern)
    {
        XQueue<String> queue = new XLinkedQueue<>();
        wildMatch(root, pattern, new StringBuilder(), queue);
        return queue;
    }
    
    private void wildMatch(Node<V> x, String pattern, StringBuilder prefix, XQueue<String> queue)
    {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length())
        {
            if (x.value != null)
                queue.enqueue(prefix.toString());
            return;
        }
        
        if (x.next == null) return;
        
        char c = pattern.charAt(d);
        if (c == WILDCAST_TOKEN)
        {
            for (int r = 0; r < R; r++)
                if (x.next[r] != null)
                {
                    prefix.append(alphabet.toChar(r));
                    wildMatch(x.next[r], pattern, prefix, queue);
                    prefix.deleteCharAt(prefix.length() - 1);
                }
        } else
        {
            int index = alphabet.toIndex(c);
            if (x.next[index] != null)
            {
                prefix.append(c);
                wildMatch(x.next[index], pattern, prefix, queue);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
    }
    
    @Override
    public String longestCommonPrefix(String query)
    {
        if(query == null) throw new IllegalArgumentException("null prefix isn't allowed");
        // extract valid characters contained in the alphabet
        StringBuilder validPrefix = new StringBuilder();
        // null pointer if query == null
        for (int i = 0; i < query.length(); i++)
        {
            char c = query.charAt(i);
            if (!alphabet.contains(c)) break;
            validPrefix.append(c);
        }
        
        int[] keyIndices = alphabet.toIndices(validPrefix.toString());
        int len = search(root, keyIndices, 0);
        return query.substring(0, len);
    }
    
    private int search(Node<V> x, int[] keyIndices, int d)
    {
        if (x == null // null root
                || d == keyIndices.length)
            return d;
        
        int index = keyIndices[d];
        if (x.next != null && x.next[index] != null)
            return search(x.next[index], keyIndices, d + 1);
        
        return d;
    }
    
    @Override
    public String longestPrefixKeyMatch(String query)
    {
        if(query == null) throw new IllegalArgumentException("null prefix isn't allowed");
        // extract valid characters contained in the alphabet
        StringBuilder validPrefix = new StringBuilder();
        // null pointer if query == null
        for (int i = 0; i < query.length(); i++)
        {
            char c = query.charAt(i);
            if (!alphabet.contains(c)) break;
            validPrefix.append(c);
        }

        int[] keyIndices = alphabet.toIndices(validPrefix.toString());
        int len = search(root, keyIndices, 0, 0);
        return query.substring(0, len);
    }

    private int search(Node<V> x, int[] keyIndices, int d, int length)
    {
        if (x == null) return length; // null root
        if (x.value != null) length = d; // update match length
        if(d == keyIndices.length) return length;

        int index = keyIndices[d];
        if (x.next != null && x.next[index] != null)
            return search(x.next[index], keyIndices, d + 1, length);

        return length;
    }
}
