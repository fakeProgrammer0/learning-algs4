package com.green.learning_algs4.graph;

public class DirectedEdge extends AbstractEdge
{
    public DirectedEdge(int v, int w)
    {
        super(v, w);
    }
    
    public int start()
    {
        return this.v;
    }
    public int end() {return this.w;}
    
    @Override
    public String toString()
    {
        return "<" + v + " -> " + w + ">";
    }
}
