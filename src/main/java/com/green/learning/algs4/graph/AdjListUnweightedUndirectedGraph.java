package com.green.learning.algs4.graph;

import com.green.learning.algs4.list.XBag;
import edu.princeton.cs.algs4.In;

import java.util.NoSuchElementException;

public class AdjListUnweightedUndirectedGraph extends UnweightedUndirectedGraph
{
    private XBag<Integer>[] adj;
    
    private void init()
    {
        adj = (XBag<Integer>[]) new XBag[V]; // ugly cast
        for (int v = 0; v < V; v++)
            adj[v] = new XBag<>();
    }
    
    public AdjListUnweightedUndirectedGraph(int V)
    {
        super(V);
        init();
    }
    
    public AdjListUnweightedUndirectedGraph(In in)
    {
        try{
            this.V = in.readInt();
            GraphUtils.validateVerticesCnt(V);
            init();
            int E = in.readInt(); // local E != this.E
            GraphUtils.validateEdgesCnt(E);
            
            for(int i = 0; i < E; i++)
            {
                int v = in.readInt();
                int w = in.readInt();
                addEdge(v, w);
            }
        }catch (NoSuchElementException ex)
        {
            throw new IllegalArgumentException("wrong format of the graph file", ex);
        }
    }
    
    @Override
    public void addEdge(int v, int w)
    {
        validateVertex(v);
        validateVertex(w);
        if(v != w)
        {
            adj[v].add(w);
            adj[w].add(v);
        }else
        {
            adj[v].add(v); // self-loop
        }
        
        E++;
    }
    
    @Override
    public Iterable<Integer> adj(int v)
    {
        validateVertex(v);
        return adj[v];
    }
    
}
