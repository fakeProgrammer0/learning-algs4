package com.green.learning_algs4.graph;

public abstract class WeightedUndirectedGraph extends UndirectedGraph
{
    public WeightedUndirectedGraph()
    {
    }
    
    public WeightedUndirectedGraph(int V)
    {
        super(V);
    }
    
    public abstract Iterable<WeightedUndirectedEdge> adjEdges(int v);
    
    public abstract Iterable<WeightedUndirectedEdge> edges();
    
    public abstract void addEdge(WeightedUndirectedEdge edge);
    
    public void addEdge(int v, int w, double weight)
    {
        addEdge(new WeightedUndirectedEdge(v, w, weight));
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
            for (WeightedUndirectedEdge edge : adjEdges(v))
                sb.append(edge).append(sep);
            if (sb.lastIndexOf(sep) + sep.length() == sb.length())
                sb.delete(sb.length() - sep.length(), sb.length());
            sb.append(newLineSep);
        }
        return sb.toString();
    }
}
