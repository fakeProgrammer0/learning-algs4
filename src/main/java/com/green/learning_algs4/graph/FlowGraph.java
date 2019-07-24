package com.green.learning_algs4.graph;

public abstract class FlowGraph extends Digraph
{
    public FlowGraph()
    {
    }
    
    public FlowGraph(int V)
    {
        super(V);
    }
    
    public abstract Iterable<FlowEdge> adjEdges(int v);
    
    public abstract Iterable<FlowEdge> edges();
    
    public abstract void addEdge(FlowEdge edge);
    
    public void addEdge(int v, int w, double capacity)
    {
        addEdge(new FlowEdge(v, w, capacity));
    }
    
    @Override
    public int outDegree(int v)
    {
        int outDegree = 0;
        for(FlowEdge edge: adjEdges(v))
            if(edge.start() == v)
                outDegree++;
        return outDegree;
    }
    
    @Override
    public int inDegree(int v)
    {
        int inDegree = 0;
        for(FlowEdge edge: adjEdges(v))
            if(edge.end() == v)
                inDegree++;
        return inDegree;
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
            for (FlowEdge edge : adjEdges(v))
                sb.append(edge).append(sep);
            if (sb.lastIndexOf(sep) + sep.length() == sb.length())
                sb.delete(sb.length() - sep.length(), sb.length());
            sb.append(newLineSep);
        }
        return sb.toString();
    }
    
    @Override
    public abstract FlowGraph reverse();
}
