package com.green.learning.algs4.string.compress;

import com.green.learning.algs4.list.XArrayList;
import com.green.learning.algs4.list.XLinkedList;
import com.green.learning.algs4.list.XStack;
import com.green.learning.algs4.st.LinkedHashST;
import com.green.learning.algs4.st.ST;
import com.green.learning.algs4.tree.XPriorityQueue;
import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryOut;

public class Huffman
{
    private static class Node implements Comparable<Node>
    {
        private final char c;
        private final int freq;
        private final Node left, right;
        
        public Node(char c)
        {
            this(c, 0);
        }
        
        public Node(char c, int freq)
        {
            this.c = c;
            this.freq = freq;
            this.left = this.right = null;
        }
        
        public Node(Node left, Node right)
        {
            this.c = '\0';
            this.left = left;
            this.right = right;
            this.freq = left.freq + right.freq;
        }
        
        @Override
        public int compareTo(Node o)
        {
            return Integer.compare(freq, o.freq);
        }
        
        public boolean isLeaf()
        {
            return this.left == null && this.right == null;
        }
        
        public boolean isNotLeaf()
        {
            return this.left != null || this.right != null;
        }
    }
    
    private static final int R = 256;
    public static final String COMPRESS_SUFFIX = ".huff";
    
    private Huffman()
    {
    }
    
    public static void compress(BinaryIn in, BinaryOut out)
    {
        // extended-ASCII
        String input = in.readString();
        Node root = buildEncodingTree(input);
        writeEncodingTree(out, root);
        out.write(input.length());
        
        ST<Character, boolean[]> encodeTable = encodeTable(root);
        for (int i = 0; i < input.length(); i++)
        {
            boolean[] code = encodeTable.get(input.charAt(i));
            for (boolean bit : code)
                out.write(bit);
        }
        out.close();
    }
    
    private static Node buildEncodingTree(String input)
    {
        final int[] counts = new int[R];
        for (int i = 0; i < input.length(); i++)
            counts[input.charAt(i)]++;
        
        XPriorityQueue<Node> queue = new XPriorityQueue<>();
        for (char i = 0; i < R; i++)
            if (counts[i] != 0)
                queue.enqueue(new Node(i, counts[i]));
        
        while (queue.size() > 1)
        {
            Node x = queue.dequeue();
            if (queue.isEmpty()) return x;
            Node y = queue.dequeue();
            queue.enqueue(new Node(x, y));
        }
        
        return queue.dequeue();
    }
    
    private static void writeEncodingTree(BinaryOut out, Node x)
    {
        final boolean isLeaf = x.isLeaf();
        out.write(isLeaf);
        if (isLeaf)
        {
            out.write(x.c);
        } else
        {
            writeEncodingTree(out, x.left);
            writeEncodingTree(out, x.right);
        }
    }
    
    private static Node readEncodingTree(BinaryIn in)
    {
        if (in.readBoolean()) return new Node(in.readChar());
        
        Node left = readEncodingTree(in);
        Node right = readEncodingTree(in);
        return new Node(left, right);
    }
    
    private static ST<Character, boolean[]> encodeTable(Node root)
    {
        final ST<Character, boolean[]> encodes = new LinkedHashST<>(R);
        XArrayList<Boolean> codeBuilder = new XArrayList<>(R);
        inorder(root, codeBuilder, encodes);
        return encodes;
    }
    
    private static void inorder(Node x, XArrayList<Boolean> codeBuilder, ST<Character, boolean[]> encodes)
    {
        if (x.isLeaf())
        {
            final boolean[] code = new boolean[codeBuilder.size()];
            for (int i = 0; i < codeBuilder.size(); i++)
                code[i] = codeBuilder.get(i);
            encodes.put(x.c, code);
        } else
        {
//            if (x.left != null)
            {
                codeBuilder.append(false);
                inorder(x.left, codeBuilder, encodes);
                codeBuilder.remove(codeBuilder.size() - 1);
            }

//            if (x.right != null)
            {
                codeBuilder.append(true);
                inorder(x.right, codeBuilder, encodes);
                codeBuilder.remove(codeBuilder.size() - 1);
            }
        }
    }
    
    public static void expand(BinaryIn in, BinaryOut out)
    {
        Node root = readEncodingTree(in);
        int N = in.readInt();
        for(int i = 0; i < N; i++)
        {
            Node x = root;
            while (x.isNotLeaf())
            {
                x = in.readBoolean() ? x.right : x.left;
            }
            out.write(x.c);
        }
        out.close();
    }
}
