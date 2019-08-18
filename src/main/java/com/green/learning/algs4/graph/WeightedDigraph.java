package com.green.learning.algs4.graph;

public abstract class WeightedDigraph extends Digraph
{
    public WeightedDigraph()
    {
    }
    
    public WeightedDigraph(int V)
    {
        super(V);
    }
    
    public abstract Iterable<WeightedDirectedEdge> adjEdges(int v);
    
    public abstract Iterable<WeightedDirectedEdge> edges();
    
    public abstract void addEdge(WeightedDirectedEdge edge);
    
    public void addEdge(int v, int w, double weight)
    {
        addEdge(new WeightedDirectedEdge(v, w, weight));
    }
    
    @Override
    public String toString()
    {
        final String newLineSep = System.lineSeparator();
        final String sep = ", ";
        String className = this.getClass().getName();
        StringBuilder sb = new StringBuilder(className).append(newLineSep);
        sb.append(V).append(" vertices; ").append(E).append(" edges").append(newLineSep);
        for (int v = 0; v < V; v++)
        {
            sb.append(v).append(": ");
            for (WeightedDirectedEdge edge : adjEdges(v))
                sb.append(edge).append(sep);
            if (sb.lastIndexOf(sep) + sep.length() == sb.length())
                sb.delete(sb.length() - sep.length(), sb.length());
            sb.append(newLineSep);
        }
        return sb.toString();
    }
}
