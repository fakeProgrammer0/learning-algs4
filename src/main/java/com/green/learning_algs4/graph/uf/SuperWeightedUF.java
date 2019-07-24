package com.green.learning_algs4.graph.uf;

import com.green.learning_algs4.list.XLinkedQueue;
import com.green.learning_algs4.list.XQueue;

import java.util.Arrays;

/**
 * @see edu.princeton.cs.algs4.UF
 */
public class SuperWeightedUF extends UnionFind
{
    private final int[] parent;
    private final int[] size;
    private int count;
    
    public SuperWeightedUF(int N)
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
        XQueue<Integer> path = new XLinkedQueue<>();
        while (v != parent[v])
        {
            path.enqueue(v);
            v = parent[v];
        }
        final int root = v;
        for(int x: path)
            parent[x] = root;
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
