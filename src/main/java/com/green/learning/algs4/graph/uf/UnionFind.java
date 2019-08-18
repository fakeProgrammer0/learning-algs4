package com.green.learning.algs4.graph.uf;

public abstract class UnionFind
{
    protected final int N;
    protected UnionFind(int N)
    {
        if(N <= 0) throw new IllegalArgumentException("N should be greater than 0");
        this.N = N;
    }
    
    public abstract boolean isConnected(int v, int w);
    public abstract void union(int v, int w);
    public abstract int id(int v);
    public abstract int count();
    
    protected final void validateVertex(int v)
    {
        if(v < 0 || v >= N)
            throw new IllegalArgumentException("valid v: 0 < v < N");
    }
}
