package com.green.learning_algs4.string.tries;

import java.util.Iterator;

/**
 * @see OrderedStringST for string symbol table
 * @see edu.princeton.cs.algs4.TST for similar implementation
 */
public class TernarySearchTriesSet extends AbstractTST
        implements OrderedStringSet
{
    private static class Node extends AbstractTST.Node
    {
        private boolean endOfStr;
        
        private Node(char c)
        {
            super(c);
        }
    
        @Override
        protected boolean isEndOfStr()
        {
            return endOfStr;
        }
    
        @Override
        protected void removeEndOfStr()
        {
            endOfStr = false;
        }
    
        @Override
        protected Object getValue()
        {
            return endOfStr;
        }
    }
    
    public TernarySearchTriesSet()
    {
    }
    
    @Override
    public boolean contains(String str)
    {
        return super.contains(str);
    }
    
    public boolean add(String key)
    {
        validateStr(key);
        char c = key.charAt(0);
        if(root == null) root = new Node(c);
        AbstractTST.Node x = root;
        int d = 0;
        while (true)
            if (c < x.c)
            {
                if (x.left == null)
                    x.left = new Node(c);
                x = x.left;
            } else if (c > x.c)
            {
                if (x.right == null)
                    x.right = new Node(c);
                x = x.right;
            } else
            {
                d++;
                if (d == key.length()) break;
                c = key.charAt(d);
                if (x.mid == null)
                    x.mid = new Node(c);
                x = x.mid;
            }
        
        if(x.isEndOfStr()) return false; // already exists
        
        size++;
        Node node = (Node) x;
        node.endOfStr = true;
        checkNode();
        return true;
    }
    
    public boolean remove(String key)
    {
        Object retVal = removeNode(key);
        if(retVal == null) return false;
        return (Boolean) retVal;
    }
    
    public Iterator<String> iterator()
    {
        return orderKeys().iterator();
    }
}
