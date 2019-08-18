package com.green.learning.algs4.graph;

public class GraphUtils
{
    public static void vaildateVertex(Graph graph, int v)
    {
        vaildateVertex(graph.V(), v);
    }
    
    public static void vaildateVertex(int V, int v)
    {
        if(v < 0 || v > V) throw new IllegalArgumentException("v should be between 0 and " + (V - 1));
    }
    
    public static void validateVerticesCnt(int V)
    {
        if (V <= 0) throw new IllegalArgumentException("The number of vertices must be greater 0");
    }
    
    public static void validateEdgesCnt(int E)
    {
        if (E < 0) throw new IllegalArgumentException("The number of edges must equal to or be greater 0");
    }
    
    public static void validateVertexParam(int v)
    {
        if(v < 0) throw new IllegalArgumentException("vertex must be greater than 0");
    }
}
