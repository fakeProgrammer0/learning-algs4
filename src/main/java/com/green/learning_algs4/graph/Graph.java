package com.green.learning_algs4.graph;

/**
 * 图接口，所有图类的接口
 */
public interface Graph
{
    int V();
    
    int E();
    
    Iterable<Integer> adj(int v);
    
    boolean edgeBetween(int v, int w);
    
    //    void addEdge(int v, int w);
}