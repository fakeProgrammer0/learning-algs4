package com.green.learning_algs4.string;

import com.green.learning_algs4.list.XLinkedQueue;
import com.green.learning_algs4.list.XQueue;
import com.green.learning_algs4.set.XSet;
import com.green.learning_algs4.st.OrderedStringST;

import java.util.Iterator;

/**
 * A String symbol table implemented by R-way tries. It implements several
 * character-based operations specified in {@code OrderedStringST} interface.
 * It also accepts empty string as a endOfStr.
 *
 * @see OrderedStringST for string symbol table
 * @see edu.princeton.cs.algs4.TrieST for similar implementation
 */
public class TriesSet extends AbstractTries
        implements OrderedStringSet
{
    private static class Node extends AbstractTries.Node
    {
        // does this Node represents a string?
        private boolean endOfStr;
        
        @Override
        protected boolean isEndOfStr()
        {
            return endOfStr;
        }
        
        @Override
        protected void removeEndOfStr()
        {
            this.endOfStr = false;
        }
    }
    
    public TriesSet(Alphabet alphabet)
    {
        super(alphabet);
    }
    
    /**
     * Store a string in the tries set. When {@code str} also exists in
     * the tries, just leave it unchanged.
     *
     * @param str the string to be stored, can be an empty string
     * @return true if the specified string isn't contained in the tries set
     * @throws IllegalArgumentException if either {@code str} is null or contains
     *                                  any character not in the {@code alphabet}
     */
    public boolean add(String str)
    {
        if (str == null)
            throw new IllegalArgumentException("null string isn't supported");
        if (invalidStr(str))
            throw new IllegalArgumentException("input string contains " +
                    "character(s) not in the alphabet");
        
        if (root == null)
            root = new Node();
        
        AbstractTries.Node x = root;
        for (int d = 0; d < str.length(); d++)
        {
            if (x.next == null)
                x.next = new Node[R];
            int index = alphabet.toIndex(str.charAt(d));
            if (x.next[index] == null)
            {
                x.next[index] = new Node(); // TriesSet.Node
                x.kids++;
            }
            x = x.next[index];
        }
        
        if (x.isEndOfStr()) return false;
        
        Node node = (Node) x;
        node.endOfStr = true;
        size++;
        checkNodeSize(); // for debug
        return true;
    }
    
    public Iterator<String> iterator()
    {
        return orderKeys().iterator();
    }
}
