package com.green.learning_algs4.graph;


/**
 * 有向图 抽象类
 */
public abstract class Digraph extends AbstractGraph
{
    protected Digraph()
    {
    }
    
    protected Digraph(int V)
    {
        super(V);
    }
    
    /**
     * @return 有向图的反向图，即每一条边的方向都和原来相反
     */
    public abstract Digraph reverse();
    
    /**
     * 低效率实现，可被子类覆盖
     */
    public int outDegree(int v)
    {
        int degree = 0;
        for(int w: adj(v))
            degree++;
        return degree;
    }
    
    /**
     * 低效率实现，可被子类覆盖
     */
    public int inDegree(int v)
    {
        int inDegree = 0;
        for(int x = 0; x < V; x++)
            for(int w: adj(x))
                inDegree++;
        return inDegree;
    }
}
