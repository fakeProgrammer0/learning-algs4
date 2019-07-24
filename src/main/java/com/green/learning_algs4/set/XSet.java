package com.green.learning_algs4.set;

public interface XSet<E> extends Iterable<E>
{
    int size();
    
    boolean isEmpty();
    
    boolean contains(E e);
    
    boolean add(E e);
    
    boolean remove(E e);
    
    void clear();
}
