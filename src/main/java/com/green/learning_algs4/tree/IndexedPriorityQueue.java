package com.green.learning_algs4.tree;

import com.green.learning_algs4.list.XArrayList;
import com.green.learning_algs4.list.XList;
import com.green.learning_algs4.sort.MergeSortOpt;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A special Priority Queue inside which every element is attached
 * with an index. Clients can change elements by specifying the
 * index and inserting the new element.
 * @see edu.princeton.cs.algs4.IndexMinPQ
 * @see edu.princeton.cs.algs4.IndexMaxPQ
 * @see java.util.PriorityQueue
 */
public class IndexedPriorityQueue<E extends Comparable<E>>
    implements Iterable<E>
{
    private final Comparator<E> comparator;
    private static final boolean DEFAULT_SMALL_PRIORITY = true;
    
    private int size;
    
    /**
     * {@code keys} array contains keys corresponding to specific index.
     * Unless a key is removed by calling {@code removeKey} or
     * {@code dequeue}, its index remains unchanged once inserted.
     */
    private E[] keys; // 0-based index
    
    /**
     * {@code index2Heap} and {@code heap2Index} are two parallel
     * indexed arrays for fast retrieving and indexing. One is the
     * inverse of the other.
     * -> heap2Index[index2Heap[i]] = i
     * -> index2Heap[heap2Index[i]] = i
     *
     * {@code index2Heap[i]} represents the heap position of the
     *                       key with the specific index {@code i}.
     * {@code heap2Index[node]} represents the index of the key
     *                       in heap position {@code node}.
     */
    private int[] index2Heap; // the heap position of the key with index i
    private int[] heap2Index; // the index of the key in heap position i
    
    // null entry in either indexed array
    private final static int NULL_ENTRY = -1;
    
    public IndexedPriorityQueue(int N)
    {
        this(N, DEFAULT_SMALL_PRIORITY);
    }
    
    public IndexedPriorityQueue(int N, boolean smallPriority)
    {
        checkKeysCount(N);
        if(smallPriority)
            comparator = Comparator.naturalOrder();
        else comparator = Comparator.reverseOrder();
        initHeap(N);
    }
    
    @SuppressWarnings("unchecked")
    public IndexedPriorityQueue(int N, Comparator<E> comparator)
    {
        checkKeysCount(N);
        this.comparator = comparator;
        initHeap(N);
    }
    
    public IndexedPriorityQueue(E[] keys)
    {
        this(keys, DEFAULT_SMALL_PRIORITY);
    }
    
    public IndexedPriorityQueue(E[] keys, boolean smallPriority)
    {
        if(smallPriority)
            comparator = Comparator.naturalOrder();
        else comparator = Comparator.reverseOrder();
        constructHeapFromKeys(keys);
    }
    
    public IndexedPriorityQueue(E[] keys, Comparator<E> comparator)
    {
        this.comparator = comparator;
        constructHeapFromKeys(keys);
    }
    
    private boolean hasHigherPriority(E o1, E o2)
    {
        return comparator.compare(o1, o2) <= 0;
    }
    
    /**
     * @param node1 a heap position
     * @param node2 another heap position
     * @return true if the element corresponding to {@code node1} has higher
     *          priority than the element corresponding to {@code node2}
     */
    private boolean hasHigherPriority(int node1, int node2)
    {
        return hasHigherPriority(keys[heap2Index[node1]], keys[heap2Index[node2]]);
    }
    
    private void checkKeysCount(int N)
    {
        if (N <= 0)
            throw new IllegalArgumentException("N should be positive");
    }
    
    private void checkKeys(E[] keys)
    {
        if (keys == null || keys.length == 0)
            throw new IllegalArgumentException("keys should be a array with length more than 1.");
        for (E element : keys)
            if (element == null)
                throw new IllegalArgumentException("null element isn't allowed");
    }
    
    private void validateIndex(int index)
    {
        if(index < 0 || index >= keys.length)
            throw new IllegalArgumentException("invalid index: " + index);
    }
    
    @SuppressWarnings("unchecked")
    private void initHeap(int N)
    {
        keys = (E[]) new Comparable[N];
        
        index2Heap = new int[N];
        Arrays.fill(index2Heap,NULL_ENTRY);
        
        heap2Index = new int[N];
        Arrays.fill(heap2Index, NULL_ENTRY);
    }
    
    @SuppressWarnings("unchecked")
    private void constructHeapFromKeys(E[] keys)
    {
        checkKeys(keys);
        final int N = keys.length;
        this.keys = (E[]) new Comparable[N];
        System.arraycopy(keys, 0, this.keys, 0, N);
        size = N;
        index2Heap = new int[N];
        heap2Index = new int[N];
        for(int i = 0; i < N; i++)
        {
            index2Heap[i] = i;
            heap2Index[i] = i;
        }
        
        for (int i = size / 2 - 1; i > 0; i--)
            sink(i);
    }
    
    private void checkEmpty()
    {
        if (isEmpty())
            throw new NoSuchElementException("The indexed priority queue is empty");
    }
    
    private void exchange(int node1, int node2)
    {
        index2Heap[heap2Index[node1]] = node2;
        index2Heap[heap2Index[node2]] = node1;
        
        int temp = heap2Index[node1];
        heap2Index[node1] = heap2Index[node2];
        heap2Index[node2] = temp;
    }
    
    private void sink(int node)
    {
        while (node + 1 <= size >>> 1)
        {
            int j = (node << 1) + 1;
            if (j + 1 < size && hasHigherPriority(j + 1, j))
                j++;
            if (!hasHigherPriority(node, j))
            {
                exchange(node, j);
                node = j;
            } else break;
        }
    }
    
    private void swim(int node)
    {
        while (node > 0)
        {
            int parent = (node - 1) >>> 1;
            if (!hasHigherPriority(parent, node))
            {
                exchange(parent, node);
                node = parent;
            } else break;
        }
    }
    
    public int size()
    {
        return size;
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    public boolean containsIndex(int index)
    {
        return keys[index] != null;
    }
    
    public E getKey(int index)
    {
        validateIndex(index);
        if(!containsIndex(index)) throw new NoSuchElementException("index " + index + " is not in the indexed priority queue");
        return keys[index];
    }
    
    public void set(int index, E element)
    {
        validateIndex(index);
        if (element == null)
            throw new IllegalArgumentException("null element is not allowed");
        
        if(keys[index] != null)
        {
            keys[index] = element;
            swim(index2Heap[index]);
            sink(index2Heap[index]);
        }else {
            keys[index] = element;
            heap2Index[size] = index;
            index2Heap[index] = size;
            size++;
            swim(index2Heap[index]);
        }
    }
    
    public void decreasePriority(int index, E key)
    {
        if(key == null || hasHigherPriority(key, keys[index]))
            throw new IllegalArgumentException("The new key must have lower priority than the old one.");
        keys[index] = key;
        sink(index2Heap[index]);
    }
    
    public void increasePriority(int index, E key)
    {
        if(key == null || hasHigherPriority(keys[index], key))
            throw new IllegalArgumentException("The new key must have higher priority than the old one.");
        keys[index] = key;
        swim(index2Heap[index]);
    }
    
    public E peek()
    {
        checkEmpty();
        return keys[heap2Index[0]];
    }
    
    public E dequeue()
    {
        checkEmpty();
        E val = keys[heap2Index[0]];
        keys[heap2Index[0]] = null;
        
        index2Heap[heap2Index[0]] = NULL_ENTRY;
        index2Heap[heap2Index[size - 1]] = 0;
        
        heap2Index[0] = heap2Index[size - 1];
        heap2Index[size - 1] = NULL_ENTRY;

        size--;
        if (!isEmpty()) sink(0);
        return val;
    }
    
    public void removeKey(int index)
    {
        validateIndex(index);
        if(!containsIndex(index))
            throw new NoSuchElementException("index " + index + " is not in the indexed priority queue");
        keys[index] = null;
        
        heap2Index[index2Heap[index]] = heap2Index[size - 1];
        index2Heap[heap2Index[size - 1]] = index;
        
        index2Heap[index] = NULL_ENTRY;
        heap2Index[size - 1] = NULL_ENTRY;
        
        size--;
        if(!isEmpty())
        {
            swim(heap2Index[index]);
            sink(heap2Index[index]);
        }
    }
    
    @SuppressWarnings("unchecked")
    public String keys()
    {
        if(isEmpty()) return "[]";
        E[] keys = (E[]) new Comparable[size];
        
        // O(slogs), s = size
//        for(int i = 0, j = 0; i < this.keys.length; i++)
//            if(this.keys[i] != null)
//                keys[j++] = this.keys[i];
//        MergeSortOpt.sort(keys);
        
        // O(s)
        for(int node = 0; node < heap2Index.length && heap2Index[node] != NULL_ENTRY; node++)
            keys[node] = this.keys[heap2Index[node]];
        
        StringBuilder sb = new StringBuilder("[");
        final String sep = ", ";
        for(int i = 0; i < size - 1; i++)
            sb.append(keys[i]).append(sep);
        sb.append(keys[size - 1]).append("]");
        return sb.toString();
    }
    
    @Override
    public String toString()
    {
        final String lineSep = System.lineSeparator();
        StringBuilder sb = new StringBuilder("indexed priority queue with ")
                .append(keys.length).append(" entries among which ").
                        append(size).append(" keys exist").append(lineSep);
//        for(int node = 0; node < heap2Index.length && heap2Index[node] != NULL_ENTRY; node++)
//            sb.append(node).append(": ").append(keys[heap2Index[node]]).append(lineSep);
        for(int i = 0; i < keys.length; i++)
        {
            sb.append(i).append(": ");
            if(keys[i] != null)
                sb.append(keys[i]);
            sb.append(lineSep);
        }
//        sb.append(lineSep);
        return sb.toString();
    }
    
    @Override
    public Iterator<E> iterator()
    {
        checkEmpty();
        XList<E> keyList = new XArrayList<>();
        for(int node = 0; node < heap2Index.length && heap2Index[node] != NULL_ENTRY; node++)
            keyList.append(this.keys[heap2Index[node]]);
        return keyList.iterator();
    }
    
    /**
     * for unit test, O(N)
     * @return true if the {@code keys} array constructs a heap
     */
    boolean isHeap()
    {
        for (int i = size - 1; i > 0; i--)
        {
            int parent = (i - 1) >>> 1;
            if (!hasHigherPriority(parent, i))
                return false;
        }
        return true;
    }
}
