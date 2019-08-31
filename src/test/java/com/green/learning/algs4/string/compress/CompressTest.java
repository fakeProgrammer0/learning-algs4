package com.green.learning.algs4.string.compress;

import com.green.learning.algs4.string.Alphabet;
import com.green.learning.algs4.util.FileUtils;
import com.green.learning.algs4.util.XTimer;
import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryOut;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CompressTest
{
    private static final String SOURCE_DIR = "string/compress/src";
    private static final String TEMP_DIR = "string/compress/temp";
    private static final String WORKING_DIR = "string/compress/temp";
    
    @ParameterizedTest
    @ValueSource(classes = {
//            RunLength.class,
//            Huffman.class,
//            LZWToy.class
            LZW.class
    })
    void test1(Class<? extends Compress> clazz)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        XTimer timer = new XTimer(clazz.getSimpleName());
        Method method = clazz.getDeclaredMethod("getInstance");
        Compress instance = (Compress) method.invoke(null);
        String suffix = instance.compressFileSuffix();
        
        String[] files = {
                "ababLZW.txt",
                "abraLZW.txt",
                "letters.txt",
                "democracy.txt",
                "notes.pdf",
                "GifCam.exe",
                "largeEWD.txt",
                "leipzig1M.txt"
        };
        
        for (String filename : files)
        {
            String compressFilename = filename + '.' + suffix;
            
            String originalFilePath = FileUtils.getFile(SOURCE_DIR, filename).getAbsolutePath();
            String compressFilePath = FileUtils.getFile(TEMP_DIR, compressFilename).getAbsolutePath();
            String expandFilePath = FileUtils.getFile(TEMP_DIR, filename).getAbsolutePath();
            
            BinaryIn compressInput = new BinaryIn(originalFilePath);
            BinaryOut compressOutput = new BinaryOut(compressFilePath);
            
            System.out.println("compress <" + filename + ">");
            
            timer.start("compress " + filename);
            instance.compress(compressInput, compressOutput);
//            LZW.getInstance().compress(compressInput, compressOutput);
            timer.stop();
            
            long originalLength = new File(originalFilePath).length();
            long compressLength = new File(compressFilePath).length();
            double compressRatio = 1.0 * compressLength / originalLength;
            System.out.printf("original file length: %d\n", originalLength);
            System.out.printf("compress file length: %d\n", compressLength);
            System.out.printf("compress ratio: %.4f\n", compressRatio);
            
            BinaryIn expandInput = new BinaryIn(compressFilePath);
            BinaryOut expandOutput = new BinaryOut(expandFilePath);
            
            timer.start("expand " + filename);
            instance.expand(expandInput, expandOutput);
//            LZWToy.getInstance().expand(expandInput, expandOutput);
            timer.stop();
            
            String originalFileMD5 = FileUtils.md5(originalFilePath);
            String expandFileMD5 = FileUtils.md5(expandFilePath);
            assertEquals(originalFileMD5, expandFileMD5);
//        System.out.println("original file md5: " + originalFileMD5);
            
            String originalFileSHA = FileUtils.sha(originalFilePath);
            String expandFileSHA = FileUtils.sha(expandFilePath);
            assertEquals(originalFileSHA, expandFileSHA);
//        System.out.println("original file sha: " + originalFileSHA);
            
            System.out.println();
        }
        
        System.out.println();
        System.out.println(timer);
    }
    
    @Test
    void test2()
    {
        assertSame(LZW.getInstance().compressFileSuffix(),
                LZW.getInstance().compressFileSuffix());
//        System.out.println(Alphabet.LOWERCASE());
    }
    
    
}