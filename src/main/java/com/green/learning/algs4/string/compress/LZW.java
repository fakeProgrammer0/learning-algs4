package com.green.learning.algs4.string.compress;

import com.green.learning.algs4.list.XLinkedStack;
import com.green.learning.algs4.list.XStack;
import com.green.learning.algs4.util.MathUtils;
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
    /**
     * alphabet: extended-ASCII
     */
    private static class CodewordTable
    {
        private static class Node
        {
            private int codeword;
            private Node[] next;
            
            private Node(int codeword)
            {
                this.codeword = codeword;
            }
        }
        
        private static class CharBuffer
        {
            private static final int DEFAULT_CAPACITY = 4096;
            private final int M; // capacity - 1
            private final char[] chars;
            private int head = 0;
            private int tail = -1;
            private int size;
            
            private CharBuffer()
            {
                this(DEFAULT_CAPACITY);
            }
            
            private CharBuffer(int capacity)
            {
                capacity = MathUtils.expandToPowerOf2(capacity);
                chars = new char[capacity];
                M = capacity - 1;
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
            
            public boolean isNotFull()
            {
                return size < chars.length;
            }
            
            public int available()
            {
                return chars.length - size;
            }
            
            public void enqueue(char c)
            {
                if (isFull())
                    throw new IllegalArgumentException("buffer is full");
//                tail = (tail + 1) % chars.length;
                tail = tail == M ? 0 : tail + 1;
                chars[tail] = c;
                size++;
            }
            
            public char dequeue()
            {
                if (isEmpty())
                    throw new NoSuchElementException("buffer is empty");
                char c = chars[head];
//                head = (head + 1) % chars.length;
                head = head == M ? 0 : head + 1;
                size--;
                return c;
            }
            
            public char peek()
            {
                if (isEmpty())
                    throw new NoSuchElementException("buffer is empty");
                return chars[head];
            }
            
            public void removeChars(int count)
            {
                if (count > size)
                    throw new IllegalArgumentException("count of chars to be removed is larger than buffer size");
                head = (head + count) & M;
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
                    char c = chars[(head + currIndex) & M];
                    currIndex++;
                    return c;
                }
            }
            
            @Override
            public String toString()
            {
                StringBuilder sb = new StringBuilder(size);
                for (int i = 0; i < size; i++)
                    sb.append(chars[(head + i) & M]);
                return sb.toString();
            }
        }
        
        private Node root;
        
        private final BinaryIn in;
        private final CharBuffer buffer;
        private int nextCodeword;
        
        private CodewordTable(BinaryIn in)
        {
            this.in = in;
            buffer = new CharBuffer();
            
            root = new Node(EOF);
            root.next = new Node[R];
            for (char r = 0; r < R; r++)
                root.next[r] = new Node(r);
            nextCodeword = EOF + 1;
            
            fillBuffer();
        }
        
        private void fillBuffer()
        {
            while (buffer.isNotFull() && !in.isEmpty())
                buffer.enqueue(in.readChar());

//            final int N = buffer.available();
//            for(int i = 0; i < N && !in.isEmpty(); i++)
//                buffer.enqueue(in.readChar());
        }
        
        public Node longestPrefixNode()
        {
            if (buffer.isEmpty()) return null;
            
            Node x = root;
            int matchLen = 0;
            do
            {
                fillBuffer();
                CharBuffer.CharBufferIterator iterator = buffer.iterator();
                do
                {
                    char c = iterator.next();
                    if (x.next[c] == null) break;
                    matchLen++;
                    x = x.next[c];
                } while (x.next != null && iterator.hasNext());
                buffer.removeChars(matchLen);
            } while (buffer.isEmpty() && !in.isEmpty());
            
            return x;
        }
        
        public void updateCodewords(Node x)
        {
            // when buffer is empty, {@code in} is also empty
            if (nextCodeword == CODEWORD_UPPER_BOUND || buffer.isEmpty()) return;
            char lookahead = buffer.peek();
            if (x.next == null) x.next = new Node[R];
//            assert x.next[lookahead] == null;
            x.next[lookahead] = new Node(nextCodeword++);
        }
    }
    
    private static class DecodeTable
    {
        private static class Node
        {
            private final char c;
            private final Node parent;
            
            private Node(char c)
            {
                this(c, null);
            }
            
            private Node(char c, Node parent)
            {
                this.c = c;
                this.parent = parent;
            }
        }
        
        private static class CharStack
        {
            private static final int DEFAULT_CAPACITY = 4096;
            private int size;
            private final char[] chars;
            
            private CharStack()
            {
                this(DEFAULT_CAPACITY);
            }
            
            private CharStack(int capacity)
            {
                chars = new char[capacity];
            }
            
            public boolean isEmpty()
            {
                return size == 0;
            }
            
            public boolean isFull()
            {
                return size == chars.length;
            }
            
            public void push(char c)
            {
                if (isFull())
                    throw new IllegalArgumentException("stack is full");
                chars[size++] = c;
            }
            
            public char pop()
            {
                if (isEmpty())
                    throw new NoSuchElementException("stack is empty");
                return chars[--size];
            }
            
            public char peek()
            {
                if (isEmpty())
                    throw new NoSuchElementException("stack is empty");
                return chars[size];
            }
            
            @Override
            public String toString()
            {
                StringBuilder sb = new StringBuilder(size);
                for (int i = size - 1; i >= 0; i--)
                    sb.append(chars[i]);
                return sb.toString();
            }
            
            public CharStackIterator iterator()
            {
                return new CharStackIterator();
            }
            
            public class CharStackIterator
            {
                private int currIndex = size;
                
                public boolean hasNext()
                {
                    return currIndex >= 0;
                }
                
                public char next()
                {
                    if (!hasNext())
                        throw new NoSuchElementException();
                    return chars[currIndex--];
                }
            }
        }
        
        private final Node[] decode;
        private final BinaryOut out;
        private final Node root;
        
        private int nextCodeword;
        private XStack<CharStack> stacks = new XLinkedStack<>();
        
        private DecodeTable(BinaryOut out)
        {
            this.out = out;
            
            decode = new Node[CODEWORD_UPPER_BOUND];
            root = decode[(char) EOF] = new Node((char) EOF);
            for (char r = 0; r < R; r++)
                decode[r] = new Node(r, root);
            nextCodeword = EOF + 1;
            
            stacks.push(new CharStack());
        }
        
        public Node writeFirstDecode(int codeword)
        {
            assert codeword < nextCodeword;
            Node x = decode[codeword];
            out.write(x.c);
            return x;
        }
        
        public Node writeDecode(Node pre, int codeword)
        {
            assert codeword <= nextCodeword;
            boolean trickyCase = codeword == nextCodeword;
    
            CharStack charStack = stacks.peek();
            Node x = trickyCase ? pre : decode[codeword];
            
            assert x != null;
            while (x.parent != root)
            {
                if (charStack.isFull())
                {
                    charStack = new CharStack();
                    stacks.push(charStack);
                }
                charStack.push(x.c);
                x = x.parent;
            }
            char first = x.c;
            charStack.push(first);
            
            if (nextCodeword < CODEWORD_UPPER_BOUND)
                decode[nextCodeword++] = new Node(first, pre);
            
            while (!stacks.isEmpty())
            {
                charStack = stacks.pop();
                while (!charStack.isEmpty())
                    out.write(charStack.pop());
            }
            if(trickyCase) out.write(first);
            
            stacks.push(charStack);
            return decode[codeword];
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
    
    private static final String COMPRESS_FILE_SUFFIX = "lzw";
    
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
        return COMPRESS_FILE_SUFFIX;
    }
    
    @Override
    public void compress(BinaryIn in, BinaryOut out)
    {
        CodewordTable codewords = new CodewordTable(in);
        do
        {
            CodewordTable.Node x = codewords.longestPrefixNode();
            if (x == null) break;
            out.write(x.codeword, W);
            codewords.updateCodewords(x);
        } while (true);
        
        out.write(EOF, W);
        out.close();
    }
    
    @Override
    public void expand(BinaryIn in, BinaryOut out)
    {
        int codeword = in.readInt(W);
        if (codeword == EOF)
        {
            out.close();
            return;
        }
        
        DecodeTable decodeTable = new DecodeTable(out);
        DecodeTable.Node x = decodeTable.writeFirstDecode(codeword);
        
        while (true)
        {
            codeword = in.readInt(W);
            if (codeword == EOF) break;
            x = decodeTable.writeDecode(x, codeword);
        }
        
        out.close();
    }
}
