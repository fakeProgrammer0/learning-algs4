package com.green.learning.algs4.list;

public interface XQueue<E> extends Iterable<E>
{
    int size();
    
    boolean isEmpty();
    void enqueue(E e);
    E dequeue();
    E peek();
    void clear();
}
