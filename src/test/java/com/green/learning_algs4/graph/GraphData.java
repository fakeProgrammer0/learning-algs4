package com.green.learning_algs4.graph;

import com.green.learning_algs4.Config;
import edu.princeton.cs.algs4.In;

import java.io.File;

public enum GraphData
{
    TINYG("tinyG", AdjListUnweightedUndirectedGraph.class),
    MEDIUMG("mediumG", AdjListUnweightedUndirectedGraph.class),
    LARGEG("largeG", AdjListUnweightedUndirectedGraph.class),
    TINYDG("tinyDG", AdjListUnweightedDigraph.class),
    MEDIUMDG("mediumDG", AdjListUnweightedDigraph.class),
    LARGEDG("largeDG", AdjListUnweightedDigraph.class),
    TINYDAG("tinyDAG", AdjListUnweightedDigraph.class),
    
    BIPARTITEG_1("bipartiteG_1", UnweightedUndirectedGraph.class),
    BIPARTITEG_2("bipartiteG_2", UnweightedUndirectedGraph.class);
    
    GraphData(String filename, Class<? extends Graph> graphClass)
    {
        this.filename = filename;
        this.graphClass = graphClass;
    }
    
    private String filename;
    private Class<? extends Graph> graphClass;
    
    private static final String DIR = "data" + File.separator + "graph" + File.separator;
    
    private String getFilePath()
    {
        return DIR + this.filename + ".txt";
    }
    
    public <E extends Graph> E getGraph(Class<E> graphClass)
    {
        In in = new In(getFilePath());
        try
        {
            return (graphClass.getConstructor(In.class)).newInstance(in);
        } catch (Exception ex)
        {
            ex.printStackTrace();
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
    
    public Graph getGraph()
    {
        In in = new In(getFilePath());
        Graph graph = null;
        try
        {
            graph = (graphClass.getConstructor(In.class)).newInstance(in);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return graph;
    }
    
    public UndirectedGraph getUndirectedGraph()
    {
        String graphFilePath = DIR + this.filename + ".txt";
        In in = new In(graphFilePath);
        return new AdjListUnweightedUndirectedGraph(in);
    }
    
    public static UndirectedGraph getUndirectedGraph(GraphData graphData)
    {
        if (graphData == null) return null;
        return graphData.getUndirectedGraph();
    }
    
    public Digraph getDigraph()
    {
        String graphFilePath = DIR + this.filename + ".txt";
        In in = new In(graphFilePath);
        return new AdjListUnweightedDigraph(in);
    }
    
    public static Digraph getDigraph(GraphData graphData)
    {
        if (graphData == null) return null;
        return graphData.getDigraph();
    }
}
