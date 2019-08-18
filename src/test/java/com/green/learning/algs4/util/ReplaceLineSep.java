package com.green.learning.algs4.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ReplaceLineSep
{
    public enum LineSep
    {
        WINDOWS("\r\n"),
        LINUX("\n"),
        ;
        
        private final String token;
        
        LineSep(String token)
        {
            this.token = token;
        }
        
        public String getToken()
        {
            return token;
        }
        
        @Override
        public String toString()
        {
            return getToken();
        }
    }
    
    public static void replaceLineSep(LineSep lineSep, String dirname, Pattern filePattern)
    {
        Queue<File> dirs = new LinkedList<>();
        dirs.add(new File(dirname));
        
        while (!dirs.isEmpty())
        {
            File dir = dirs.poll();
            File[] files = dir.listFiles();
            if (files != null)
            {
                for (File file : files)
                {
                    if (file.isDirectory())
                        dirs.add(file);
                    else if (filePattern.matcher(file.getName()).matches())
                        replaceFileLineSep(file, lineSep);
                }
            }
        }
    }
    
    private static final String TEMP_SUFFIX = ".temp";
    
    private static void replaceFileLineSep(File file, LineSep lineSep)
    {
        String filename = file.getName();
        File target = new File(filename + TEMP_SUFFIX);
        
        try (
                Scanner input = new Scanner(file);
                PrintWriter output = new PrintWriter(target);
        )
        {
            while (input.hasNextLine())
            {
                String line = input.nextLine(); // contains no line separator
                line += lineSep.getToken();
                output.print(line);
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
        file.deleteOnExit();
        target.renameTo(file);
    }
    
    
    public static void main(String[] args)
    {
        replaceLineSep(LineSep.LINUX, "./src/", Pattern.compile(".*\\.java"));
    }
    
}
