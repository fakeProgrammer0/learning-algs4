package com.green.learning.algs4.graph;

import com.green.learning.algs4.list.XBag;
import edu.princeton.cs.algs4.In;

import java.util.NoSuchElementException;

public class AdjListUnweightedDigraph extends UnweightedDigraph
{
    private XBag<Integer>[] adj;
    private int[] inDegrees;
    
    public AdjListUnweightedDigraph(int V)
    {
        super(V);
        init();
    }
    
    public AdjListUnweightedDigraph(In in)
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
    
    private void init()
    {
        inDegrees = new int[this.V];
        adj = (XBag<Integer>[]) new XBag[this.V];
        for(int v = 0; v < this.V; v++)
            adj[v] = new XBag<>();
    }
    
    @Override
    public void addEdge(int v, int w)
    {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        inDegrees[w]++;
        this.E++;
    }
    
    @Override
    public Iterable<Integer> adj(int v)
    {
        validateVertex(v);
        return adj[v];
    }
    
    @Override
    public UnweightedDigraph reverse()
    {
        UnweightedDigraph reverseGraph = new AdjListUnweightedDigraph(this.V);
        for(int v = 0; v < this.V; v++)
            for (int w : adj(v))
                reverseGraph.addEdge(w, v);
        return reverseGraph;
    }
    
    @Override
    public int outDegree(int v)
    {
        validateVertex(v);
        return adj[v].size();
    }
    
    @Override
    public int inDegree(int v)
    {
        validateVertex(v);
        return inDegrees[v];
    }
    
}
