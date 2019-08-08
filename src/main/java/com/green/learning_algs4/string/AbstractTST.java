package com.green.learning_algs4.string;

import com.green.learning_algs4.list.XLinkedQueue;
import com.green.learning_algs4.list.XLinkedStack;
import com.green.learning_algs4.list.XQueue;
import com.green.learning_algs4.list.XStack;
import com.green.learning_algs4.set.XLinkedHashSet;
import com.green.learning_algs4.set.XSet;

/**
 * @see OrderedStringST for string symbol table
 * @see edu.princeton.cs.algs4.TST for similar implementation
 */
public class AbstractTST implements OrderedStringCollection
{
    protected abstract static class Node
    {
        protected char c;
        protected Node left, mid, right;
        
        protected Node(char c)
        {
            this.c = c;
        }
        
        protected abstract boolean isEndOfStr();
        
        protected abstract void removeEndOfStr();
        
        protected abstract Object getValue();
    }
    
    protected Node root;
    protected int size = 0;
    
    protected AbstractTST()
    {
    }
    
    public int size()
    {
        return size;
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    protected void validateStr(String str)
    {
        if (str == null || str.isEmpty())
            throw new IllegalArgumentException("null str or empty str isn't supported");
    }
    
    protected void checkNode()
    {
        if(isEmpty())
            assert root == null && size == 0;
        else checkNode(root);
    }
    
    protected void checkNode(Node x)
    {
        if(x == null) return;
        if(!x.isEndOfStr())
            assert x.left != null || x.mid != null || x.right != null;
        checkNode(x.left);
        checkNode(x.mid);
        checkNode(x.right);
    }
    
    /**
     * get the node with specific string
     * Non recursive version
     *
     * @param str string
     * @return the node with specific string
     */
    protected Node getNode(String str)
    {
        validateStr(str);
        Node x = root;
        int d = 0;
        while (x != null)
        {
            char c = str.charAt(d);
            if (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else
            {
                d++;
                if (d == str.length()) break;
                x = x.mid;
            }
        }
        return x;
    }
    
    protected enum Link
    {LEFT, MID, RIGHT}
    
    ;
    
    protected Object removeNode(String str)
    {
        validateStr(str);
        Node x = root;
        XStack<Node> ancestor = new XLinkedStack<>();
        XStack<Link> links = new XLinkedStack<>();
        char c = str.charAt(0);
        int d = 0;
        while (x != null)
        {
            if (c < x.c)
            {
                ancestor.push(x);
                x = x.left;
                links.push(Link.LEFT);
            } else if (c > x.c)
            {
                ancestor.push(x);
                x = x.right;
                links.push(Link.RIGHT);
            } else
            {
                d++;
                if(d == str.length()) break;
                c = str.charAt(d);
                ancestor.push(x);
                x = x.mid;
                links.push(Link.MID);
            }
        }
        
        if (x == null || !x.isEndOfStr()) return null;
        
        Object retVal = x.getValue();
        x.removeEndOfStr();
        size--;
        
        while (!x.isEndOfStr() && x.mid == null
                && x.left == null && x.right == null)
        {
            if (ancestor.isEmpty())
            {
                root = null;
                break;
            }
            
            Node parent = ancestor.pop();
            Link link = links.pop();
            switch (link)
            {
                case LEFT:
                    parent.left = null;
                    break;
                case RIGHT:
                    parent.right = null;
                    break;
                case MID:
                    parent.mid = null;
                    break;
            }
            x = parent;
        }
        
        checkNode(); // for debug
        return retVal;
    }
    
    /**
     * lazy implementation
     */
    public void clear()
    {
        root = null;
        size = 0;
    }
    
    protected boolean contains(String str)
    {
        Node x = getNode(str);
        return x != null && x.isEndOfStr();
    }
    
    public XSet<String> keys()
    {
        XSet<String> set = new XLinkedHashSet<>(size);
        collect(root, new StringBuilder(), set);
        return set;
    }
    
    
    private void collect(Node x, StringBuilder prefixBuilder, XSet<String> set)
    {
        if (x == null) return;
        
        collect(x.left, prefixBuilder, set);
        
        prefixBuilder.append(x.c);
        if (x.isEndOfStr())
            set.add(prefixBuilder.toString());
        collect(x.mid, prefixBuilder, set);
        prefixBuilder.deleteCharAt(prefixBuilder.length() - 1);
        
        collect(x.right, prefixBuilder, set);
    }
    
    @Override
    public Iterable<String> orderKeys()
    {
        XQueue<String> queue = new XLinkedQueue<>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }
    
    @Override
    public Iterable<String> keysWithPrefix(String prefix)
    {
//        validateKey(prefix);
        if (prefix == null)
            throw new IllegalArgumentException("null prefix isn't supported");
        if (prefix.isEmpty())
            return orderKeys();
        
        Node x = getNode(prefix);
        XQueue<String> queue = new XLinkedQueue<>();
        if (x != null)
        {
            if (x.isEndOfStr()) queue.enqueue(prefix);
            collect(x.mid, new StringBuilder(prefix), queue);
        }
        return queue;
    }
    
    private void collect(Node x, StringBuilder prefixBuilder, XQueue<String> queue)
    {
        if (x == null) return;
        
        if (x.left != null) // this line is duplicate code, just for better performance
            collect(x.left, prefixBuilder, queue);
        
        prefixBuilder.append(x.c);
        if (x.isEndOfStr())
            queue.enqueue(prefixBuilder.toString());
        if (x.mid != null) // this line is duplicate code, just for better performance
            collect(x.mid, prefixBuilder, queue);
        prefixBuilder.deleteCharAt(prefixBuilder.length() - 1);
        
        if (x.right != null) // this line is duplicate code, just for better performance
            collect(x.right, prefixBuilder, queue);
    }
    
    protected final static char WILDCARD_CHARACTER = '.';
    
    @Override
    public Iterable<String> keysMatch(String pattern)
    {
        validateStr(pattern);
        XQueue<String> queue = new XLinkedQueue<>();
        wildcardMatch(root, new StringBuilder(), pattern, queue);
        return queue;
    }
    
    private void wildcardMatch(Node x, StringBuilder prefixBuilder, String pattern, XQueue<String> queue)
    {
        if (x == null) return;
        int d = prefixBuilder.length();
//        if (d == pattern.length()) return;
        
        char c = pattern.charAt(d);
        if (c == WILDCARD_CHARACTER)
        {
            if (x.left != null) // duplicate line, just for better performance
                wildcardMatch(x.left, prefixBuilder, pattern, queue);
            
            prefixBuilder.append(x.c);
            if (prefixBuilder.length() < pattern.length())
            {
                if (x.mid != null) // duplicate line, just for better performance
                    wildcardMatch(x.mid, prefixBuilder, pattern, queue);
            } else if (x.isEndOfStr())
                queue.enqueue(prefixBuilder.toString());
            prefixBuilder.deleteCharAt(prefixBuilder.length() - 1);
            
            if (x.right != null) // duplicate line, just for better performance
                wildcardMatch(x.right, prefixBuilder, pattern, queue);
        } else
        {
            if (c == x.c)
            {
                prefixBuilder.append(x.c);
                if (prefixBuilder.length() < pattern.length())
                {
                    if (x.mid != null) // duplicate line, just for better performance
                        wildcardMatch(x.mid, prefixBuilder, pattern, queue);
                } else if (x.isEndOfStr())
                    queue.enqueue(prefixBuilder.toString());
                prefixBuilder.deleteCharAt(prefixBuilder.length() - 1);
            } else if (c < x.c)
            {
                if (x.left != null) // duplicate line, just for better performance
                    wildcardMatch(x.left, prefixBuilder, pattern, queue);
            } else if (x.right != null) // duplicate line, just for better performance
                wildcardMatch(x.right, prefixBuilder, pattern, queue);
        }
    }
    
    @Override
    public String longestPrefixKeyMatch(String query)
    {
//        validateKey(query);
        if (query == null)
            throw new IllegalArgumentException("null prefix isn't supported");
        if (query.isEmpty())
            return query;
        
        int len = 0;
        Node x = root;
        int d = 0;
        while (x != null && d < query.length())
        {
            char c = query.charAt(d);
            if (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else
            {
                d++;
                if (x.isEndOfStr()) len = d;
                x = x.mid;
            }
        }
        
        return query.substring(0, len);
    }
    
    @Override
    public String longestCommonPrefix(String query)
    {
        validateStr(query);
        
        int d = 0;
        Node x = root;
        while (x != null && d < query.length())
        {
            char c = query.charAt(d);
            if (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else
            {
                d++;
                x = x.mid;
            }
        }
        
        return query.substring(0, d);
    }
}
