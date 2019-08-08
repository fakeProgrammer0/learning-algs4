package com.green.learning_algs4.st;

import com.green.learning_algs4.string.OrderedStringCollection;

/**
 * String symbol table, specially designed for symbol tables using strings as keys.
 * Besides basic key-value operations, it also supports several high efficient
 * character-based operations to work with string keys.
 * @param <V> value type
 * @see com.green.learning_algs4.st.ST
 */
public interface OrderedStringST<V>
        extends ST<String, V>, OrderedStringCollection
{

}
