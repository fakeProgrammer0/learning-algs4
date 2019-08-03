//package com.green.learning_algs4.st;
//
//import com.green.learning_algs4.set.XSet;
//import com.green.learning_algs4.string.Alphabet;
//
//import java.util.Iterator;
//
//public class TriesSTBackup<V> implements StringST<V>
//{
//    private static class Node<Value>
//    {
//        private int size = 0; // number of children
//        private Value value;
//        private Node<Value>[] next; // an array with length R
//    }
//
//    private final Alphabet alphabet;
//    private final int R;
//
//    private int size = 0;
//
//    private Node<V> root; // default null
//
//    public TriesSTBackup(Alphabet alphabet)
//    {
//        this.alphabet = alphabet;
//        this.R = alphabet.R();
//    }
//
//    private void initNodeNextIfNeeded(Node<V> x)
//    {
//        if (x.next == null)
//            x.next = (Node<V>[]) new Node[R];
//    }
//
//    @Override
//    public int size()
//    {
//        return size;
//    }
//
//    @Override
//    public boolean isEmpty()
//    {
//        return size == 0;
//    }
//
//    private boolean validKey(String key)
//    {
//        if (key == null || key.isEmpty()) return false;
//
//        for (int i = 0; i < key.length(); i++)
//            if (!alphabet.contains(key.charAt(i)))
//                return false;
//        return true;
//    }
//
//    private void validateNodeSize()
//    {
//        assert size == 0 || size == root.size;
//        if (!isEmpty())
//            validateNodeSize(root);
//    }
//
//    private void validateNodeSize(Node<V> x)
//    {
//        if(x.size == 0)
//        {
//            assert x.next == null;
//            return;
//        }
//        int cnt = 0;
//        for (int r = 0; r < R; r++)
//            if (x.next[r] != null)
//            {
//                cnt+= x.next[r].size == 0 ? 1 : x.next[r].size;
//                validateNodeSize(x.next[r]);
//            }
//        assert cnt == x.size;
//    }
//
//    /**
//     * put <K, V> in the tries
//     *
//     * @param key   string key
//     * @param value the associated value
//     * @throws IllegalArgumentException if {@code value} is null
//     *                                  if {@code key} contains any character not in the {@code alphabet}
//     */
//    @Override
//    public void put(String key, V value)
//    {
//        if (value == null) throw new IllegalArgumentException("null value isn't supported");
//        int[] keyIndices = alphabet.toIndices(key);
//        root = put(root, keyIndices, value, 0); // update root.size
//        validateNodeSize();
//    }
//
//    private Node<V> put(Node<V> x, int[] keyIndices, V value, int d)
//    {
//        if (x == null) x = new Node<>();
//        if (d == keyIndices.length)
//        {
//            if (x.value == null)
//            {
//                size++;
//
//                // buggy code: root is null at first insert
////                Node<V> node = root;
////                for(int i = 0; i < keyIndices.length; i++)
////                {
////                    node.size++;
////                    int index = keyIndices[i];
////                    node = node.next[index];
////                }
//            }
//            x.value = value;
//            return x;
//        }
//
//        initNodeNextIfNeeded(x);
//
//        int index = keyIndices[d];
//        boolean nullNode = x.next[index] == null;
//        if (!nullNode) x.size -= x.next[index].size;
//
//        x.next[index] = put(x.next[index], keyIndices, value, d + 1);
//
//        x.size += nullNode ? 1 : x.next[index].size;
//        return x;
//    }
//
//    @Override
//    public V get(String key)
//    {
//        // simplify operation
//        if (key == null || key.isEmpty())
//            throw new IllegalArgumentException("null or empty key isn't supported");
//
//        if (!validKey(key)) return null;
//
//        int[] keyIndices = alphabet.toIndices(key);
//        Node<V> x = get(root, keyIndices, 0);
//        return (x == null) ? null : x.value;
//    }
//
//    private Node<V> get(Node<V> x, int[] keyIndices, int d)
//    {
//        if (x == null) return null;
//        if (d == keyIndices.length) return x;
//        if (x.next == null) return null; // x has no leafs
//
//        int index = keyIndices[d];
//        return get(x.next[index], keyIndices, d + 1);
//    }
//
//    @Override
//    public void remove(String key)
//    {
//        if (!validKey(key)) return;
//
//        int[] keyIndices = alphabet.toIndices(key);
//        root = remove(root, keyIndices, 0); // update root.size
//        validateNodeSize();
//    }
//
//    private Node<V> remove(Node<V> x, int[] keyIndices, int d)
//    {
//        if (x == null) return null;
//        if (d == keyIndices.length)
//        {
//            if (x.value != null)
//            {
//                x.value = null;
//                size--;
//            }
//        } else
//        {
//            int index = keyIndices[d];
//            if (x.next[index] == null) return x;
//            x.size -= x.next[index].size;
//            x.next[index] = remove(x.next[index], keyIndices, d + 1);
//            if (x.next[index] != null)
//                x.size += x.next[index].size;
//            else if (d + 1 == keyIndices.length)
//                x.size -= 1;
//        }
//
//        return x.size == 0 ? null : x;
//    }
//
//    @Override
//    public void clear()
//    {
//        root = null;
//        size = 0;
//    }
//
//    @Override
//    public boolean containsKey(String key)
//    {
//        return get(key) != null;
//    }
//
//    @Override
//    public XSet<String> keys()
//    {
//        return null;
//    }
//
//    @Override
//    public XSet<Entry<String, V>> entries()
//    {
//        return null;
//    }
//
//    @Override
//    public Iterator<Entry<String, V>> iterator()
//    {
//        return null;
//    }
//
//    @Override
//    public Iterable<String> orderKeys()
//    {
//        return null;
//    }
//
//    @Override
//    public Iterable<String> keysWithPrefix(String prefix)
//    {
//        return null;
//    }
//
//    @Override
//    public Iterable<String> keysMatch(String pattern)
//    {
//        return null;
//    }
//
//    @Override
//    public String longestPrefixOf(String str)
//    {
//        return null;
//    }
//}
