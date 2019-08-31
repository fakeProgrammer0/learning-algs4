package com.green.learning.algs4.string.regexp;

/**
 * @see edu.princeton.cs.algs4.GREP
 */
public class Grep
{
    private final RegExpNFA pattern;
    
    public Grep(String regexp)
    {
        String re = "(.*" + regexp + ".*)";
        pattern = new RegExpNFA(re);
    }
    
    public boolean search(String text)
    {
        return pattern.match(text);
    }
}
