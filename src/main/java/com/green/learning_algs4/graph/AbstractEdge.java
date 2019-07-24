package com.green.learning_algs4.graph;

public class AbstractEdge
{
    protected int v;
    protected int w;
    
    protected AbstractEdge(int v, int w)
    {
        GraphUtils.validateVertexParam(v);
        GraphUtils.validateVertexParam(w);
        this.v = v;
        this.w = w;
    }
    
    public int other(int x)
    {
        if(x == v) return w;
        return v;
    }
    
    @Override
    public String toString()
    {
        return "<" + v + ", " + w  + ">";
    }
}
