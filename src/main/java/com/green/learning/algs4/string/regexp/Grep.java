package com.green.learning.algs4.string.regexp;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @see edu.princeton.cs.algs4.GREP
 */
public class Grep
{
    private final RegExpNFA pattern;
    private final Pattern jdkPattern;
    
    public Grep(String regexp)
    {
        String re = "(.*" + regexp + ".*)";
        pattern = new RegExpNFA(re);
        jdkPattern = Pattern.compile(re);
    }
    
    public boolean search(String text)
    {
        return pattern.match(text);
    }
    
    public void grepLine(Scanner scanner)
    {
        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            if(search(line))
            {
                System.out.println(line);
                assert jdkPattern.matcher(line).matches();
            }else assert !jdkPattern.matcher(line).matches();
        }
    }
}
