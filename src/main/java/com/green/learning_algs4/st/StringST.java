package com.green.learning_algs4.st;

/**
 * String symbol table
 * @param <V> value type
 */
public interface StringST<V> extends ST<String, V>
{
    Iterable<String> orderKeys();
    
    Iterable<String> keysWithPrefix(String prefix);
    
    Iterable<String> keysMatch(String pattern);
    
    String longestPrefix(String str);
    
    String longestPrefixKeyMatch(String query);
}
