package com.green.learning.algs4.string.tries;

import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XLinkedStack;
import com.green.learning.algs4.list.XQueue;
import com.green.learning.algs4.list.XStack;
import com.green.learning.algs4.string.Alphabet;


public abstract class AbstractTries implements OrderedStringCollection
{
    protected static abstract class Node
    {
        // number of children, not number of keys under this node!!
        protected int kids = 0;
        
        // an array with length R
        protected Node[] next;
        
        // does this Node represents a string?
        protected abstract boolean isEndOfStr();
        
        protected abstract void removeEndOfStr();
        
        protected abstract Object getNodeValue();
    }
    
    /**
     * the specified alphabet
     * the tries set only contains characters from this alphabet
     */
    protected final Alphabet alphabet;
    
    // radix of the alphabet
    protected final int R;
    
    protected int size = 0;
    protected Node root;
    
    protected AbstractTries(Alphabet alphabet)
    {
        this.alphabet = alphabet;
        this.R = alphabet.radix();
    }
    
    protected void checkNullStr(String str)
    {
        if (str == null)
            throw new IllegalArgumentException("null string isn't supported");
    }
    
    protected int validPrefixLength(String str)
    {
        int i = 0;
        while (i < str.length() && alphabet.contains(str.charAt(i)))
            i++;
        return i;
    }
    
    protected boolean invalidStr(String str)
    {
        if (str == null) return true;
        return validPrefixLength(str) != str.length();
    }
    
    protected void validateStr(String str)
    {
        checkNullStr(str);
        for(int i = 0; i < str.length(); i++)
            if(!alphabet.contains(str.charAt(i)))
                throw new IllegalArgumentException("character <" + str.charAt(i)
                        + "> isn't contained in the alphabet");
    }
    
    public int size()
    {
        return size;
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    protected void checkNodeSize()
    {
        if (isEmpty())
            assert size == 0 && root == null;
        else checkNodeSize(root);
    }
    
    private void checkNodeSize(Node x)
    {
        if (x.kids == 0)
        {
//            assert x == root || x.next == null;
            assert x.next == null;
            return;
        }
        
        int cnt = 0;
        for (int r = 0; r < R; r++)
            if (x.next[r] != null)
            {
                cnt++;
                checkNodeSize(x.next[r]);
            }
        assert cnt == x.kids;
    }
    
    protected Node getNode(String str)
    {
        checkNullStr(str);
        
        Node x = root;
        for (int d = 0; x != null && d < str.length(); d++)
        {
            if (x.next == null) return null;
            int index = alphabet.toIndex(str.charAt(d));
            x = x.next[index];
        }
        return x;
    }
    
    public boolean contains(String str)
    {
        checkNullStr(str);
        if (invalidStr(str)) return false;
        
        Node x = getNode(str);
        return x != null && x.isEndOfStr();
    }
    
    protected Object removeNode(String str)
    {
        if (invalidStr(str)) return null;
        
        Node x = root;
        XStack<Node> ancestors = new XLinkedStack<>();
        XStack<Integer> nodeIndices = new XLinkedStack<>();
        int index = -1;
        for (int d = 0; d < str.length(); d++)
        {
            if (x == null || x.next == null) return null;
            index = alphabet.toIndex(str.charAt(d));
            ancestors.push(x);
            nodeIndices.push(index);
            x = x.next[index];
        }
        
        if (x == null || !x.isEndOfStr())
            return null;
        
        Object retVal = x.getNodeValue();
        x.removeEndOfStr();
        size--;
        while (x.kids == 0)
        {
            if(ancestors.isEmpty())
            {
                root = null;
                break;
            }
            x = ancestors.pop();
            x.next[nodeIndices.pop()] = null;
            x.kids--;
        }
        
        checkNodeSize(); // for debug
        return retVal;
    }
    
    public void clear()
    {
        root = null;
        size = 0;
    }
    
    public Iterable<String> orderKeys()
    {
        return keysWithPrefix("");
    }
    
    public Iterable<String> keysWithPrefix(String prefix)
    {
        Node x = getNode(prefix);
        XQueue<String> queue = new XLinkedQueue<>();
        if (x == null) return queue;
        collect(x, new StringBuilder(prefix), queue);
        return queue;
    }
    
    private void collect(Node x, StringBuilder prefix, XQueue<String> queue)
    {
        if (x == null) return;
        if (x.isEndOfStr())
            queue.enqueue(prefix.toString());
        
        if (x.next != null)
            for (int r = 0; r < R; r++)
                if (x.next[r] != null) // optimized
                {
                    prefix.append(alphabet.toChar(r));
                    collect(x.next[r], prefix, queue);
                    prefix.deleteCharAt(prefix.length() - 1);
                }
    }
    
    protected final static char WILDCARD_TOKEN = '.';
    
    public Iterable<String> keysMatch(String pattern)
    {
        checkNullStr(pattern);
        XQueue<String> queue = new XLinkedQueue<>();
        wildMatch(root, pattern, new StringBuilder(), queue);
        return queue;
    }
    
    protected void wildMatch(Node x, String pattern,
                             StringBuilder prefix, XQueue<String> queue)
    {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length())
        {
            if (x.isEndOfStr())
                queue.enqueue(prefix.toString());
            return;
        }
        
        if (x.next == null) return;
        
        char c = pattern.charAt(d);
        if (c == WILDCARD_TOKEN)
        {
            for (int r = 0; r < R; r++)
                if (x.next[r] != null)
                {
                    prefix.append(alphabet.toChar(r));
                    wildMatch(x.next[r], pattern, prefix, queue);
                    prefix.deleteCharAt(prefix.length() - 1);
                }
        } else
        {
            int index = alphabet.toIndex(c);
            if (x.next[index] != null)
            {
                prefix.append(c);
                wildMatch(x.next[index], pattern, prefix, queue);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
    }
    
    public String longestCommonPrefix(String query)
    {
        checkNullStr(query);
        if (root == null) return "";
        
        // extract valid characters contained in the alphabet
        int validLen = validPrefixLength(query);
        
        Node x = root;
        int d;
        for (d = 0; d < query.length(); d++)
        {
            int index = alphabet.toIndex(query.charAt(d));
            if (x.next == null || x.next[index] == null) break;
            x = x.next[index];
        }
        
        return query.substring(0, d);
    }
    
    public String longestPrefixKeyMatch(String query)
    {
        checkNullStr(query);
        if (root == null) return "";
        
        // extract valid characters contained in the alphabet
        int validLen = validPrefixLength(query);
        
        Node x = root;
        int d = 0, len = 0;
        for (; ; d++)
        {
            if (x.isEndOfStr()) len = d;
            if (d == validLen) break;
            int index = alphabet.toIndex(query.charAt(d));
            if (x.next == null || x.next[index] == null) break;
            x = x.next[index];
        }
        
        return query.substring(0, len);
    }
}
