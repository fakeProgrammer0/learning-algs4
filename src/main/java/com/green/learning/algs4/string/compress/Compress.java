package com.green.learning.algs4.string.compress;

import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryOut;

public interface Compress
{
    void compress(BinaryIn in, BinaryOut out);
    
    void expand(BinaryIn in, BinaryOut out);
    
    String compressFileSuffix();
    
//    Compress getInstance();
}
