package com.green.learning.algs4.set;

/**
 * @param <E> generic element type
 * @see java.util.Set
 * @see edu.princeton.cs.algs4.SET
 */
public interface XSet<E> extends Iterable<E>
{
    int size();
    
    boolean isEmpty();
    
    boolean contains(E e);
    
    /**
     * @param e the element added to the set
     * @return true if this set did not already contain the specified element
     *         false if the specified element already exist
     */
    boolean add(E e);
    
    boolean remove(E e);
    
    void clear();
}
