package com.green.learning.algs4.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        UNIX("\n"),
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
        String filename = file.getAbsolutePath();
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
    
        try
        {
            java.nio.file.Files.delete(file.toPath());
            java.nio.file.Files.move(target.toPath(),file.toPath());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args)
    {
        String dirname = "./src/";
//        String dirname = "./test/";
        replaceLineSep(LineSep.UNIX, dirname, Pattern.compile(".*\\.java"));
    }
    
}
