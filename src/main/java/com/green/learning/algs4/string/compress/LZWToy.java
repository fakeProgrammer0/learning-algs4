package com.green.learning.algs4.string.compress;

import com.green.learning.algs4.string.Alphabet;
import com.green.learning.algs4.string.trie.OrderedStringST;
import com.green.learning.algs4.string.trie.TrieST;
import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryOut;

/**
 * Lempel Ziv Welch Algorithm
 * It's just a toy example to demonstrate the basic idea of the LZW algorithm.
 * Because the {@code substring} method of {@code String} require O(N) time to
 * get a substring of a string with length <b>N</b> in java 8, the {@code compress}
 * method can be very very slow.
 * @see edu.princeton.cs.algs4.LZW
 */
public class LZWToy implements Compress
{
    private static final int R = 256;
    
    /**
     * Warning: If W is not a multiple of 8-bits, the client should automatically write
     * the {@code EOF} character to indicate the end of the compress file.
     *
     * Reason:
     * This client uses the {@code write(int, W)} method of {@code BinaryOut} to write
     * {@code W} bits to the compress file. If W is not a multiple of 8-bits, then
     * total bits of the file might not be a multiple of 8-bits, and the
     * {@code BinaryOut} library will add padding 0s at the end of the file.
     *
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
    
    private LZWToy()
    {
    }
    
    private static LZWToy instance = new LZWToy();
    
    public static LZWToy getInstance()
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
        OrderedStringST<Integer> codewords = new TrieST<>(Alphabet.EXTENDED_ASCII());
        for (char r = 0; r < R; r++)
            codewords.put(r + "", (int) r);
        
        int nextCode = EOF + 1;
        
        String input = in.readString();
        while (true)
        {
            String longestPrefix = codewords.longestPrefixKeyMatch(input);
            int code = codewords.get(longestPrefix);
            out.write(code, W);
            input = input.substring(longestPrefix.length());
            if (input.isEmpty()) break;
            if (nextCode < CODEWORD_UPPER_BOUND)
                codewords.put(longestPrefix + input.charAt(0), nextCode++);
        }
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
        if(code == EOF) return;
        String val = decodeTable[code];
        
        while (true)
        {
            out.write(val);
            code = in.readInt(W);
            if(code == EOF) break;
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
