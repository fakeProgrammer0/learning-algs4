package com.green.learning_algs4.graph.uf;

import edu.princeton.cs.algs4.StdRandom;

public class QuickUnion extends UnionFind
{
    private final int[] parent;
    private int count;
    
    public QuickUnion(int N)
    {
        super(N);
        count = N;
        parent = new int[N];
        for (int i = 0; i < N; i++)
            parent[i] = i;
    }
    
    private int root(int v)
    {
        int x = v;
        while (x != parent[x])
            x = parent[x];
        return x;
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
        // 增加随机性，使得树尽可能变得平衡
        int root_v = root(v), root_w = root(w);
        if (root_v != root_w)
        {
            // 直接把一棵树挂到另一棵树的根节点下
            if (StdRandom.bernoulli())
                parent[root_v] = root_w;
            else parent[root_w] = root_v;
    
            // 树的深度很容易就加深了很多。。
//            if (StdRandom.bernoulli())
//                parent[root_v] = w;
//            else parent[root_w] = v;
            count--;
        }
    }
    
    @Override
    public int id(int v)
    {
        validateVertex(v);
        return root(v);
    }
    
    @Override
    public int count()
    {
        return count;
    }
}
