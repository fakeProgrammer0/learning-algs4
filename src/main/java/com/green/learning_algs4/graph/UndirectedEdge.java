package com.green.learning_algs4.graph;

public class UndirectedEdge extends AbstractEdge
{
    public UndirectedEdge(int v, int w)
    {
        super(v, w);
    }
    
    public int either()
    {
        return v;
    }
    
    @Override
    public String toString()
    {
        return "<" + v + " - " + w + ">";
    }
}
