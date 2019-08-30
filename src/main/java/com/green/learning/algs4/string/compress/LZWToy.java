package com.green.learning.algs4.string.compress;

import com.green.learning.algs4.string.Alphabet;
import com.green.learning.algs4.string.tries.OrderedStringST;
import com.green.learning.algs4.string.tries.TriesST;
import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryOut;

/**
 * Lempel Ziv Welch Algorithm
 *
 * @see edu.princeton.cs.algs4.LZW
 */
public class LZWToy implements Compress
{
    private static final int R = 256;
    private static final int W = 16;
    private static final int MAX_CODEWORD = 1 << W;
    
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
    
    private static OrderedStringST<Integer> getCodewords()
    {
        OrderedStringST<Integer> codewords = new TriesST<>(Alphabet.EXTENDED_ASCII());
        for (char r = 0; r < R; r++)
            codewords.put(r + "", (int) r);
        return codewords;
    }
    
    @Override
    public void compress(BinaryIn in, BinaryOut out)
    {
        OrderedStringST<Integer> codewords = getCodewords();
        int currMaxCode = R;
        String input = in.readString();
        while (true)
        {
            String longestPrefix = codewords.longestPrefixKeyMatch(input);
            int code = codewords.get(longestPrefix);
            out.write(code, W);
            input = input.substring(longestPrefix.length());
            if (input.isEmpty()) break;
            if (currMaxCode < MAX_CODEWORD)
                codewords.put(longestPrefix + input.charAt(0), currMaxCode++);
        }
        out.close();
    }
    
    @Override
    public void expand(BinaryIn in, BinaryOut out)
    {
        String[] decodeTable = new String[MAX_CODEWORD];
        for (char r = 0; r < R; r++)
            decodeTable[r] = String.valueOf(r);
        int currMaxCode = R - 1;
        
        String predecessor = null;
        while (!in.isEmpty())
        {
            int code = in.readInt(W);
            String decode = (code == currMaxCode + 1)
                    ? predecessor + predecessor.charAt(0)
                    : decodeTable[code];
            out.write(decode);
            if (currMaxCode < MAX_CODEWORD && predecessor != null)
                decodeTable[currMaxCode++] = predecessor + decode.charAt(0);
            predecessor = decode;
        }
        out.close();
    }
}
