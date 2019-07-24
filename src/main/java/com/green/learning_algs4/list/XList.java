package com.green.learning_algs4.list;

public interface XList<E> extends Iterable<E>
{
    int size();
    
    boolean isEmpty();
    
    /**
     * stores an element to the end of the list
     */
    void append(E e);
    
    /**
     * inserts the element {@param e} at specific position
     * @param index the index at which the element is inserted
     * @param e the element to be inserted
     */
    void insert(int index, E e);
    
    E get(int index);
    
    /**
     * replaces the element at specific position with e
     * @param index the index of the element to be replaced
     * @param e the element to stored into the list
     * @return the element previously at the specified position
     */
    E set(int index, E e);
    
    E remove(int index);
    
    boolean removeFirst(E e);
    
    void removeAll(E e);
    
    void clear();
    
    boolean contains(E e);
    
    int indexOf(E e);
    
    int lastIndexOf(E e);
    
    Object[] toArray();
    
    E[] toArray(E[] a);
}
