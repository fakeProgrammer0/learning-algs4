package com.green.learning.algs4.graph;

public abstract class UnweightedDigraph extends Digraph
{
    public UnweightedDigraph()
    {
    }
    
    public UnweightedDigraph(int V)
    {
        super(V);
    }
    
    public abstract void addEdge(int v, int w);
}
