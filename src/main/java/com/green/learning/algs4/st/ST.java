package com.green.learning.algs4.st;

import com.green.learning.algs4.set.XSet;

/**
 *
 * @param <K> key type
 * @param <V> value type
 * @see java.util.Map
 */
public interface ST<K, V> extends Iterable<ST.Entry<K, V>>
{
    class Entry<K, V>
    {
//        private final K key;
        private K key;
        private V value;
        
        public Entry(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
        
        // key 类型 Immutable，不然hashCode一不小心被修改，就会很麻烦
        public K getKey()
        {
            return key;
        }
        
        public V getValue()
        {
            return value;
        }
        
        public void setValue(V value)
        {
            this.value = value;
        }
    
        public void setKey(K key)
        {
            this.key = key;
        }
    
        @Override
        public String toString()
        {
            return "<" + key + ", " + value + ">";
        }
    }
    
    int size();
    
    boolean isEmpty();
    
    void put(K key, V value);
    
    V get(K key);
    
    /**
     * remove the key and its value from the table
     * @param key the key to be removed
     */
    void remove(K key);
    
    void clear();
    
    boolean containsKey(K key);
    
    XSet<K> keys();
    
    XSet<ST.Entry<K, V>> entries();
}
