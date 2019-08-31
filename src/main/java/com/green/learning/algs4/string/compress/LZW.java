package com.green.learning.algs4.string.compress;

import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryOut;

import java.util.NoSuchElementException;

/**
 * Lempel Ziv Welch Algorithm
 * It's just a toy example to demonstrate the basic idea of the LZW algorithm.
 * Because the {@code substring} method of {@code String} require O(N) time to
 * get a substring of a string with length <b>N</b> in java 8, the {@code compress}
 * method can be very very slow.
 *
 * @see edu.princeton.cs.algs4.LZW
 */
public class LZW implements Compress
{
    // alphabet: extended-ASCII
    private static class CodewordTable
    {
        private static class Node
        {
            private int codeword;
            private boolean key;
            private Node[] next;
            
            private Node()
            {
            }
            
            private Node(int codeword)
            {
                this.codeword = codeword;
                this.key = true;
            }
            
            private Node(int codeword, boolean key)
            {
                this.codeword = codeword;
                this.key = key;
            }
            
            public boolean isKey()
            {
                return key;
            }
            
            public void setCodeword(int codeword)
            {
                this.codeword = codeword;
                this.key = true;
            }
        }
        
        private static class CharBuffer
        {
            private static final int DEFAULT_CAPACITY = 4096;
            private final char[] chars;
            private int head = 0;
            private int tail = -1;
            private int size;
            
            public CharBuffer()
            {
                this(DEFAULT_CAPACITY);
            }
            
            public CharBuffer(int capacity)
            {
                chars = new char[capacity];
            }
            
            public int size()
            {
                return size;
            }
            
            public boolean isEmpty()
            {
                return size == 0;
            }
            
            public boolean isFull()
            {
                return size == chars.length;
            }
            
            public void enqueue(char c)
            {
                if (size == chars.length)
                    throw new IllegalArgumentException("chars is full");
                tail = (tail + 1) % chars.length;
                chars[tail] = c;
                size++;
            }
            
            public char dequeue()
            {
                if (isEmpty())
                    throw new NoSuchElementException("chars is empty");
                char c = chars[head];
                head = (head + 1) % chars.length;
                size--;
                return c;
            }
            
            public char peek()
            {
                if (isEmpty())
                    throw new NoSuchElementException("chars is empty");
                return chars[head];
            }
            
            public void removeChars(int count)
            {
                if (count > size)
                    throw new IllegalArgumentException("remove chars' count is larger than buffer size");
                head = (head + count) % chars.length;
                size -= count;
            }
            
            public CharBufferIterator iterator()
            {
                return new CharBufferIterator();
            }
            
            public class CharBufferIterator
            {
                private int currIndex;
                
                public boolean hasNext()
                {
                    return currIndex < size;
                }
                
                public char next()
                {
                    if (!hasNext())
                        throw new NoSuchElementException();
                    char c = chars[(head + currIndex) % chars.length];
                    currIndex++;
                    return c;
                }
            }
        }
        
//        private int size;
        
        private Node root;
        
        private final BinaryIn in;
        private final CharBuffer buffer;
        private static final int CHAR_LEN = 16;
        private int nextCodeword;
        
        public CodewordTable(BinaryIn in)
        {
            this.in = in;
            buffer = new CharBuffer();
            
            root = new Node();
            root.next = new Node[R];
            for (char r = 0; r < R; r++)
            {
                root.next[r] = new Node(r, true);
            }
            nextCodeword = EOF + 1;
//            size = R;
        }

//        public int size()
//        {
//            return size;
//        }
//
//        public boolean isEmpty()
//        {
//            return size == 0;
//        }
        
        private void fillBuffer()
        {
            while (!buffer.isFull() && !in.isEmpty())
                buffer.enqueue(in.readChar());
        }
        
        public Node longestPrefixOf()
        {
            if (in.isEmpty()) return null;
            
            Node x = root, node = null;
            int d = 0, matchLen = 0;
            do
            {
                fillBuffer();
                CharBuffer.CharBufferIterator iterator = buffer.iterator();
                while (x.next != null && iterator.hasNext())
                {
                    char c = iterator.next();
                    if (x.next[c] == null) break;
                    x = x.next[c];
                    d++;
                    if (x.key)
                    {
                        node = x;
                        matchLen = d;
                    }
                }
                buffer.removeChars(matchLen);
            } while (buffer.isEmpty() && !in.isEmpty());
            
            return node;
        }
        
        public void updateCodewords(Node x)
        {
            if (buffer.isEmpty()) return; // in.isEmpty() == true
            char lookahead = buffer.peek();
            if(x.next == null) x.next = new Node[R];
            if (x.next[lookahead] == null)
                x.next[lookahead] = new Node(nextCodeword++, true);
            else
                x.next[lookahead].setCodeword(nextCodeword++);
        }
        
    }
    
    
    private static final int R = 256;
    
    /**
     * Warning: If W is not a multiple of 8-bits, the client should automatically write
     * the {@code EOF} character to indicate the end of the compress file.
     * <p>
     * Reason:
     * This client uses the {@code write(int, W)} method of {@code BinaryOut} to write
     * {@code W} bits to the compress file. If W is not a multiple of 8-bits, then
     * total bits of the file might not be a multiple of 8-bits, and the
     * {@code BinaryOut} library will add padding 0s at the end of the file.
     * <p>
     * When the client uses {@code readInt(W)} method of {@code BinaryIn} to read
     * a {@code W}-bit integer, it will finally get the padding zeros which are
     * no more than {@code W} bits. At this time, an {@code NoSuchElementException}
     * will be thrown by the {@code BinaryIn} class.
     */
    private static final int EOF = R;
    
    //    private static final int W = 16;
    private static final int W = 12;
    
    /**
     * codeword upper bound (exclusive)
     */
    private static final int CODEWORD_UPPER_BOUND = 1 << W;
    
    private LZW()
    {
    }
    
    private static LZW instance = new LZW();
    
    public static LZW getInstance()
    {
        return instance;
    }
    
    @Override
    public String compressFileSuffix()
    {
        return "lzw";
    }
    
    @Override
    public void compress(BinaryIn in, BinaryOut out)
    {
        CodewordTable codewords = new CodewordTable(in);
        
        do{
            CodewordTable.Node x = codewords.longestPrefixOf();
            if(x == null) break;
            out.write(x.codeword, W);
            codewords.updateCodewords(x);
        }while (true);
        
        out.write(EOF, W);
        out.close();
    }
    
    @Override
    public void expand(BinaryIn in, BinaryOut out)
    {
        String[] decodeTable = new String[CODEWORD_UPPER_BOUND];
        for (char r = 0; r < R; r++)
            decodeTable[r] = String.valueOf(r);
        decodeTable[EOF] = ""; // look ahead for EOF
        int nextCode = EOF + 1;
        
        int code = in.readInt(W);
        if (code == EOF) return;
        String val = decodeTable[code];
        
        while (true)
        {
            out.write(val);
            code = in.readInt(W);
            if (code == EOF) break;
            String decode = code == nextCode
                    ? val + val.charAt(0)
                    : decodeTable[code];
            if (nextCode < CODEWORD_UPPER_BOUND)
                decodeTable[nextCode++] = val + decode.charAt(0);
            val = decode;
        }
        out.close();
    }
}
