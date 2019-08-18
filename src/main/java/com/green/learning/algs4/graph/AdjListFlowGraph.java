package com.green.learning.algs4.graph;

import com.green.learning.algs4.list.XBag;
import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XQueue;
import com.green.learning.algs4.set.XLinkedHashSet;
import com.green.learning.algs4.set.XSet;
import edu.princeton.cs.algs4.In;

public class AdjListFlowGraph extends FlowGraph
{
    private XBag<FlowEdge>[] adj;
    
    @SuppressWarnings("unchecked")
    public AdjListFlowGraph(int V)
    {
        super(V);
        adj = (XBag<FlowEdge>[]) new XBag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new XBag<>();
    }
    
    @SuppressWarnings("unchecked")
    public AdjListFlowGraph(In in)
    {
        this.V = in.readInt();
        GraphUtils.validateVerticesCnt(V);
        adj = (XBag<FlowEdge>[]) new XBag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new XBag<>();
        
        int E = in.readInt();
        GraphUtils.validateEdgesCnt(E);
        for (int i = 0; i < E; i++)
        {
            int v = in.readInt(), w = in.readInt();
            double capacity = in.readDouble();
            addEdge(v, w, capacity);
        }
    }
    
    @SuppressWarnings("unchecked")
    public AdjListFlowGraph(FlowGraph graph)
    {
        V = graph.V();
        E = graph.E();
        adj = (XBag<FlowEdge>[]) new XBag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new XBag<>();
        
        for (FlowEdge edge : graph.edges())
            addEdge(edge);
    }
    
    @Override
    public void addEdge(FlowEdge edge)
    {
        int v = edge.start(), w = edge.end();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(edge);
        if(v != w)
            adj[w].add(edge);
    }
    
    @Override
    public Iterable<Integer> adj(int v)
    {
        validateVertex(v);
        XQueue<Integer> neighbors = new XLinkedQueue<>();
        for (FlowEdge edge : adj[v])
            if (edge.start() == v)
                neighbors.enqueue(edge.end());
        return neighbors;
    }
    
    @Override
    public Iterable<FlowEdge> adjEdges(int v)
    {
        validateVertex(v);
        return adj[v];
    }
    
    @Override
    public Iterable<FlowEdge> edges()
    {
        XSet<FlowEdge> edgeSet = new XLinkedHashSet<>();
        for (XBag<FlowEdge> neighbors : adj)
            for (FlowEdge edge : neighbors)
                if (!edgeSet.contains(edge))
                    edgeSet.add(edge);
        return edgeSet;
    }
    
    
    @Override
    public FlowGraph reverse()
    {
        return null;
    }
}
