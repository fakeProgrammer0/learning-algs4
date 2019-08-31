package com.green.learning.algs4.string.regexp;

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
