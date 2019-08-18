package com.green.learning.algs4.string.tries;

public interface OrderedStringCollection
{
    /**
     * @return all string keys in lexical order
     *         empty iterable object if it contains no key.
     */
    Iterable<String> orderKeys();
    
    /**
     * Prefix Matching: retrieve keys which match a given prefix
     * @param prefix the query prefix
     * @return all keys which match a given prefix
     *         empty iterable object if no match exists
     */
    Iterable<String> keysWithPrefix(String prefix);
    
    /**
     * Wildcard Matching: match keys against the given pattern with same length.
     * @param pattern the query pattern. The wildcard character '.' is used to match
     *                any single character.
     * @return all keys which match the given pattern
     *         empty iterable object if no match exists
     */
    Iterable<String> keysMatch(String pattern);
    
    /**
     * Longest Prefix Matching: find the longest prefix of the query string.
     * Among all keys, no other key has a longer length than the longest common
     * prefix.
     * @param query the given query string
     * @return the longest prefix key of the query string, which must be a key
     *         of the string symbol table.
     */
    String longestPrefixKeyMatch(String query);
    
    /**
     * Longest Common Prefix: find the longest common prefix of the query string.
     * @param query the given query string
     * @return the longest common prefix. Note that the longest common prefix is
     *         also a prefix of an exist key, but it may not be a key itself.
     */
    String longestCommonPrefix(String query);
}
