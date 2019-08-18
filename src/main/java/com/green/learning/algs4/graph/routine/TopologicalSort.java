package com.green.learning.algs4.graph.routine;

public abstract class TopologicalSort implements GraphProcessRoutine
{
    public abstract boolean hasOrder();
    
    public abstract Iterable<Integer> order();
    
    public abstract int rank(int v);
    
    public abstract Iterable<Integer> cycle();
    
    @Override
    public void printResult()
    {
        if (hasOrder())
        {
            StringBuilder sb = new StringBuilder();
            final String sep = " -> ";
            for (int v : order())
            {
                sb.append(v);
                sb.append(sep);
            }
            int lastSepIdx = sb.lastIndexOf(sep);
            if (lastSepIdx > 0)
                sb.delete(lastSepIdx, sb.length());
            sb.append("]");
            System.out.println(sb.toString());
        }else {
            System.out.println("The digraph is a DAG and has no topological order");
            System.out.println("Directed cycle: " + cycle());
        }
    }
}
