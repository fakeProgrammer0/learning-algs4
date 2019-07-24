package com.green.learning_algs4.st;

public interface OrderedST<K extends  Comparable<K>, V> extends ST<K, V>
{
    K minKey();
    K maxKey();
    K floor(K key);
    int rank(K key);
    K select(int rank);
    void deleteMinKey();
    void deleteMaxKey();
    int size(K low, K high);
    Iterable<K> keys(K low, K high);
}
