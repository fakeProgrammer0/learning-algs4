package com.green.learning_algs4.tree;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * {@link edu.princeton.cs.algs4.Heap}
 * @param <E>
 */
@Deprecated
public class Heap<E extends Comparable<E>>
{
    private int size = 0;
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 16;
    private static final int MAX_CAPACITY = Integer.MAX_VALUE;
    
    private static final int ROOT = 1; // 1-based index
    private static final int NULL_INDEX = 0;
    
    public Heap()
    {
        this(DEFAULT_CAPACITY);
    }
    
    public Heap(int capacity)
    {
        if(capacity <= 0) throw new IllegalArgumentException("capacity should be positive");
        elements = (E[]) new Comparable[capacity];
    }
    
    public Heap(E[] items)
    {
        if(items == null)
            throw new IllegalArgumentException("items should not be null");
        for(E e: items)
            checkNullElement(e);
    
        // bottom up construction
        size = items.length;
        this.elements = (E[]) new Comparable[size + ROOT];
        System.arraycopy(items, 0, this.elements, ROOT, size);
        constructHeapFromBottomUp(); //自底向上构建堆
        
        // additive construction
//        for (E e : elements)
//            add(e);
    }
    
    private void checkNullElement(E e)
    {
        if(e == null)
            throw new IllegalArgumentException("The element shouldn't be null");
    }
    
    private int parentOf(int index)
    {
        checkElementIndex(index);
        return index / 2; //当为根结点时，返回0
    }
    
    private int leftChild(int index)
    {
        checkElementIndex(index);
        int leftChildIndex = index * 2;
        return isElementsIndex(leftChildIndex) ? leftChildIndex : NULL_INDEX;
    }
    
    private int rightChild(int index)
    {
        checkElementIndex(index);
        int rightChildIndex = index * 2 + 1;
        return isElementsIndex(rightChildIndex) ? rightChildIndex : NULL_INDEX;
    }
    
    private boolean isElementsIndex(int index)
    {
        return index >= ROOT && index <= size - 1 + ROOT;
    }
    
    private void checkElementIndex(int index)
    {
        if (!isElementsIndex(index))
            throw new IndexOutOfBoundsException(index + " is not a valid element index");
    }
    
    private void ensureCapacity()
    {
        if(size == elements.length)
        {
            if(size == MAX_CAPACITY)
                throw new OutOfMemoryError();
            int newCapacity = size >= MAX_CAPACITY >> 1 ? MAX_CAPACITY : 2 * size;
            resize(newCapacity);
        }
    }
    
    private void resize(int capacity)
    {
        assert capacity >= size;
        elements = Arrays.copyOf(elements, capacity);
    }
    
    //实际上就是所谓的shiftdown方法，把节点沉到合适的位置
    //    private void constructHeapAtNode(int node)
    //    {
    //        checkElementIndex(node);
    //
    //        int k = node;
    //        E nodeVal = elements[k];
    //        boolean isHeap = false;
    //        while (!isHeap && 2 * k <= size)
    //        {
    //            int j = 2 * k;
    //            if (j < size) //存在右节点
    //                if (elements[j].compareTo(elements[j + 1]) < 0)
    //                    j++;
    //
    //            if (nodeVal.compareTo(elements[j]) >= 0)
    //                isHeap = true;
    //            else
    //            {
    //                elements[k] = elements[j];
    //                k = j;
    //            }
    //        }
    //        elements[k] = nodeVal;
    //    }
    
    private void constructHeapFromBottomUp()
    {
        int lastInternalNode = size / 2;
        for (int i = lastInternalNode; i >= ROOT; i--)
        {
            //            constructHeapAtNode(i);
            siftDown(i);
        }
    }
    
    private void siftDown(int node)
    {
        checkElementIndex(node);
        
        int k = node;
        E nodeVal = elements[k];
        // 存在左子节点
        while (2 * k <= size)
        {
            int j = 2 * k; // left child
            
            // 存在右节点
            if (j < size && elements[j].compareTo(elements[j + 1]) < 0)
                j++;
            
            if (nodeVal.compareTo(elements[j]) < 0)
            {
                elements[k] = elements[j];
                k = j;
            } else break;
        }
        elements[k] = nodeVal;
    }
    
    private void pushUp(int node)
    {
        checkElementIndex(node);
        
        E nodeVal = elements[node];
        int currIdx = node;
        while (currIdx > ROOT)
        {
            int parent = currIdx / 2;
            if (elements[parent].compareTo(nodeVal) < 0)
            {
                elements[currIdx] = elements[parent];
                currIdx = parent;
            } else
                break;
        }
        elements[currIdx] = nodeVal;
    }
    
    public E root()
    {
        if(isEmpty())
            throw new NoSuchElementException("The heap is empty");
        return elements[ROOT];
    }
    
    public void clear()
    {
        size = 0;
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    public int size()
    {
        return size;
    }
    
    public void add(E e)
    {
        ensureCapacity();
        elements[++size] = e;
        pushUp(size);
    }
    
    public E remove()
    {
        if(isEmpty())
            throw new NoSuchElementException("The heap is empty");
        
        E removeValue = elements[ROOT];
        elements[ROOT] = elements[size];
        elements[size] = removeValue;
        size--;
        if (size > 0)
            siftDown(ROOT);
        return removeValue;
    }
    
    public E remove(int node)
    {
        checkElementIndex(node);
        
        if (node == size) size--;
        else
        {
            E removeVal = elements[node];
            elements[node] = elements[size];
            elements[size] = removeVal;
            size--;
            
            pushUp(node);
            siftDown(node);
        }
        return elements[size + 1];
    }
}
