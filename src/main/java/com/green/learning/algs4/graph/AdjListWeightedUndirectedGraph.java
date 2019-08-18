package com.green.learning.algs4.graph;

import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XBag;
import com.green.learning.algs4.list.XQueue;
import com.green.learning.algs4.set.XLinkedHashSetX;
import com.green.learning.algs4.set.XSet;
import edu.princeton.cs.algs4.In;

/**
 * 加权无向图，邻接链表实现
 */
public class AdjListWeightedUndirectedGraph extends WeightedUndirectedGraph
{
    private XBag<WeightedUndirectedEdge>[] adj;
    
    @SuppressWarnings("unchecked")
    public AdjListWeightedUndirectedGraph(In in)
    {
        this.V = in.readInt();
        GraphUtils.validateVerticesCnt(V);
        adj = (XBag<WeightedUndirectedEdge>[]) new XBag[V];
        for(int v = 0; v < V; v++)
            adj[v] = new XBag<>();
        
        int E = in.readInt();
        GraphUtils.validateEdgesCnt(E);
        for(int i = 0; i < E; i++)
        {
            int v = in.readInt(), w = in.readInt();
            double weight = in.readDouble();
            addEdge(v, w, weight);
        }
    }
    
    @SuppressWarnings("unchecked")
    public AdjListWeightedUndirectedGraph(int V)
    {
        super(V);
        adj = (XBag<WeightedUndirectedEdge>[]) new XBag[V];
        for(int v = 0; v < V; v++)
            adj[v] = new XBag<>();
    }
    
    @Override
    public Iterable<Integer> adj(int v)
    {
        validateVertex(v);
        XQueue<Integer> neighbors = new XLinkedQueue<>();
        for(WeightedUndirectedEdge edges: adj[v])
            neighbors.enqueue(edges.other(v));
        return neighbors;
    }
    
    @Override
    public Iterable<WeightedUndirectedEdge> edges()
    {
        XSet<WeightedUndirectedEdge> edgeSet = new XLinkedHashSetX<>(E());
        for(int v = 0; v < V; v++)
            for(WeightedUndirectedEdge edge: adj[v])
                if(!edgeSet.contains(edge))
                    edgeSet.add(edge);
        return edgeSet;
    }
    
    @Override
    public Iterable<WeightedUndirectedEdge> adjEdges(int v)
    {
        validateVertex(v);
        return adj[v];
    }
    
    @Override
    public void addEdge(WeightedUndirectedEdge edge)
    {
        int v = edge.either();
        int w = edge.other(v);
        validateVertex(v);
        validateVertex(w);
        adj[v].add(edge);
        if (v != w)
            adj[w].add(edge);
        this.E++;
    }
}
