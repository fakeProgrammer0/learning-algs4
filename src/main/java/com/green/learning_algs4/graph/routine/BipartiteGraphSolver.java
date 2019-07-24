package com.green.learning_algs4.graph.routine;

import java.util.Set;

public abstract class BipartiteGraphSolver implements GraphProcessRoutine
{
    public abstract boolean isBipartiteGraph();
    public abstract Set<Integer>[] getBipartiteVertices();
    public abstract Iterable<Integer> getOddLengthCycle();
    public abstract boolean inSameColor(int v, int w);
    
    protected void checkBipartite()
    {
        if(!isBipartiteGraph()) throw new UnsupportedOperationException("The given graph is not bipartite!");
    }
    
    @Override
    public void printResult()
    {
        System.out.println("isBipartiteGraph: " + isBipartiteGraph());
        if(isBipartiteGraph())
        {
            System.out.println("bipartiteVertices: ");
            System.out.println("Negative Vertices: " + getBipartiteVertices()[0]);
            System.out.println("Positive Vertices: " + getBipartiteVertices()[1]);
        }else
        {
            System.out.println("has an odd length cycle: " + getOddLengthCycle());
        }
    }
}
