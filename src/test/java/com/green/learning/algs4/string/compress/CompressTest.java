package com.green.learning.algs4.string.compress;

import com.green.learning.algs4.string.Alphabet;
import com.green.learning.algs4.util.FileUtils;
import com.green.learning.algs4.util.XTimer;
import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryOut;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CompressTest
{
    private static final String SOURCE_DIR = "string/compress/src";
    private static final String TEMP_DIR = "string/compress/temp";
    private static final String WORKING_DIR = "string/compress/temp";
    
    
    @Test
    void test1()
    {
        XTimer timer = new XTimer("Huffman");
        String suffix = Huffman.COMPRESS_SUFFIX;
        
//        String filename = "letters.txt";
//        String filename = "democracy.txt";
//        String filename = "notes.pdf";
        String filename = "GifCam.exe";
        
        String originalFilePath = FileUtils.getFile(SOURCE_DIR, filename).getAbsolutePath();
        String compressFilePath = FileUtils.getFile(TEMP_DIR, filename + suffix).getAbsolutePath();
        String expandFilePath = FileUtils.getFile(TEMP_DIR,filename).getAbsolutePath();
        
        BinaryIn compressInput = new BinaryIn(originalFilePath);
        BinaryOut compressOutput = new BinaryOut(compressFilePath);
        
        timer.start("compress");
        Huffman.compress(compressInput,compressOutput);
        timer.stop();
        
        long originalLength = new File(originalFilePath).length();
        long compressLength = new File(compressFilePath).length();
        double compressRatio = 1.0 * compressLength / originalLength;
        System.out.printf("compress ratio: %.4f\n", compressRatio);
        
        BinaryIn expandInput = new BinaryIn(compressFilePath);
        BinaryOut expandOutput = new BinaryOut(expandFilePath);
        
        timer.start("expand");
        Huffman.expand(expandInput,expandOutput);
        timer.stop();
        
        String originalFileMD5 = FileUtils.md5(originalFilePath);
        String expandFileMD5 = FileUtils.md5(originalFilePath);
        assertEquals(originalFileMD5,expandFileMD5);
//        System.out.println("original file md5: " + originalFileMD5);
        
        String originalFileSHA = FileUtils.sha(originalFilePath);
        String expandFileSHA = FileUtils.sha(originalFilePath);
        assertEquals(originalFileSHA,expandFileSHA);
//        System.out.println("original file sha: " + originalFileSHA);
        
        System.out.println();
        System.out.println(timer);
    }
    
    @Test
    void test2()
    {
        System.out.println(Alphabet.LOWERCASE());
    }
    
    
}