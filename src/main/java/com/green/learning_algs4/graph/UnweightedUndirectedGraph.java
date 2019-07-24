package com.green.learning_algs4.graph;

public abstract class UnweightedUndirectedGraph extends  UndirectedGraph
{
    protected UnweightedUndirectedGraph()
    {
    }
    
    protected UnweightedUndirectedGraph(int V)
    {
        super(V);
    }
    
    public abstract void addEdge(int v, int w);
    
//    @Override
//    public String toString()
//    {
//        final String newLineSep = System.lineSeparator();
//        StringBuilder sb = new StringBuilder("Undirected Graph").append(newLineSep);
//        sb.append(V).append(" vertices; ").append(E).append(" edges").append(newLineSep);
//        for(int v = 0; v < V; v++)
//        {
//            sb.append(v).append(": ");
//            for (int w : adj(v))
//                sb.append(w).append(" ");
//            sb.append(newLineSep);
//        }
//        return sb.toString();
//    }
}
