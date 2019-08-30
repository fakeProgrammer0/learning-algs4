package com.green.learning.algs4.string.compress;

import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryOut;

/**
 * @see edu.princeton.cs.algs4.RunLength
 */
public class RunLength implements Compress
{
    private static final int R = 256;
    private static final int lgR = 8;
    private static final int MAX_CONSECUTIVE_BITS = R - 1;
    
    private static RunLength instance;
    
    private RunLength() {}
    
    public static Compress getInstance()
    {
        if(instance == null) instance = new RunLength();
        return instance;
    }
    
    public String compressFileSuffix()
    {
        return "run";
    }
    
    public void compress(BinaryIn in, BinaryOut out)
    {
        int bitCount = 0;
        boolean bit = false;
        while (!in.isEmpty())
        {
            while (bitCount < MAX_CONSECUTIVE_BITS &&
                    !in.isEmpty() && in.readBoolean() == bit)
                bitCount++;
            out.write(bitCount, lgR);
            bit = !bit;
            bitCount = bitCount == MAX_CONSECUTIVE_BITS ? 0 : 1;
        }
        if (bitCount == 1) out.write(bitCount);
        out.close();
    }
    
    public void expand(BinaryIn in, BinaryOut out)
    {
        boolean bit = false;
        while (!in.isEmpty())
        {
            int bitCount = in.readInt(lgR);
            for (int i = 0; i < bitCount; i++)
                out.write(bit);
            bit = !bit;
        }
        out.close();
    }
}
