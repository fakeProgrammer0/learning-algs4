package com.green.learning.algs4.graph.uf;

import java.util.Arrays;

/**
 * @see edu.princeton.cs.algs4.UF
 */
public class SuperUF extends UnionFind
{
    private final int[] parent;
    private final int[] size;
    private int count;
    
    public SuperUF(int N)
    {
        super(N);
        count = N;
        parent = new int[N];
        for (int i = 0; i < N; i++)
            parent[i] = i;
        size = new int[N];
        Arrays.fill(size, 1);
    }
    
    private int root(int v)
    {
        while (v != parent[v])
        {
            parent[v] = parent[parent[v]];
            v = parent[v];
        }
        return v;
    }
    
    @Override
    public boolean isConnected(int v, int w)
    {
        validateVertex(v);
        validateVertex(w);
        return root(v) == root(w);
    }
    
    @Override
    public void union(int v, int w)
    {
        validateVertex(v);
        validateVertex(w);
        int root_v = root(v), root_w = root(w);
        if (root_v != root_w)
        {
            if (size[root_v] >= size[root_w])
            {
                parent[root_w] = root_v;
                size[root_v] += size[root_w];
            } else
            {
                parent[root_v] = root_w;
                size[root_w] += size[root_v];
            }
            count--;
        }
    }
    
    @Override
    public int id(int v)
    {
        validateVertex(v);
        return parent[v];
    }
    
    @Override
    public int count()
    {
        return count;
    }
}
