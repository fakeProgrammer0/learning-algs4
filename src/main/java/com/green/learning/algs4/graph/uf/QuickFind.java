package com.green.learning.algs4.graph.uf;

public class QuickFind extends UnionFind
{
    private final int[] ids;
    private int count;
    
    public QuickFind(int N)
    {
        super(N);
        count = N;
        ids = new int[N];
        for (int i = 0; i < N; i++)
            ids[i] = i;
    }
    
    @Override
    public boolean isConnected(int v, int w)
    {
        validateVertex(v);
        validateVertex(w);
        return ids[v] == ids[w];
    }
    
    @Override
    public void union(int v, int w)
    {
        validateVertex(v);
        validateVertex(w);
        if(ids[v] == ids[w]) return;
        int max = Math.max(ids[v], ids[w]);
        int min = Math.min(ids[v], ids[w]);
        for (int i = 0; i < N; i++)
            if (ids[i] == max)
                ids[i] = min;
        count--;
    }
    
    @Override
    public int id(int v)
    {
        return ids[v];
    }
    
    @Override
    public int count()
    {
        return count;
    }
}
