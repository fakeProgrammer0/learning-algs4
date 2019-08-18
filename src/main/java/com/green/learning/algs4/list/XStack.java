package com.green.learning.algs4.list;

public interface XStack<E> extends Iterable<E>
{
    int size();
    
    boolean isEmpty();
    
    void push(E e);
    
    E peek();
    
    E pop();
    
    void clear();
}
