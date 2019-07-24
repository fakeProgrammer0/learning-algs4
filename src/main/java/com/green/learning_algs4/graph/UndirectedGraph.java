package com.green.learning_algs4.graph;

/**
 * 抽象无向图
 */
public abstract class UndirectedGraph extends AbstractGraph
{
    protected UndirectedGraph()
    {
    }
    
    protected UndirectedGraph(int V)
    {
        super(V);
    }
    
    public int totalDegree()
    {
        return 2 * E(); // 握手定理
    }
    
    public double avgDegree()
    {
        return 1.0 * totalDegree() / V();
    }
    
    public int degree(int v)
    {
        int degree = 0;
        for(int w: adj(v))
            if(w == v) degree += 2; // self loop
            else degree++;
        return degree;
    }
    
    public int maxDegree()
    {
        int maxDegree = 0;
        for(int v = 0; v< V();v++)
            maxDegree = Math.max(maxDegree, degree(v));
        return maxDegree;
    }
    
    public int numOfSelfLoops()
    {
        int numOfSelfLoops = 0;
        for (int v = 0; v < V(); v++)
            for (int w : adj(v))
                if (v == w) numOfSelfLoops++;
        
        return numOfSelfLoops;
    }
}
