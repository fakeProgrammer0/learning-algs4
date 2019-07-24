package com.green.learning_algs4.graph;

/**
 * 抽象图类
 */
public abstract class AbstractGraph implements Graph
{
    protected int V;
    protected int E;
    
    // 维持一个无参数构造方法，便于使用子类继承时自定义多样的构造方法
    protected AbstractGraph()
    {
    }
    
    // lazy initialization: 所有节点之间都没有边相连
    protected AbstractGraph(int V)
    {
        GraphUtils.validateVerticesCnt(V);
        this.V = V;
    }
    
//    @Deprecated
//    public AbstractGraph(In in)
//    {
//        try{
//            V = in.readInt();
//            if(V <= 0) throw new IllegalArgumentException("numbers of vertices in a graph must be postive");
//            // init() ?
////            adj = (XBag<Integer>[]) new XBag[V];
////            for(int i = 0; i < V; i++)
////                adj[i] = new XBag<>();
//            E = in.readInt();
//            if(E < 0) throw new IllegalArgumentException("numbers of edges in a graph must be nonnegative");
//
//            for(int i = 0; i < E; i++)
//            {
//                int v = in.readInt();
//                int w = in.readInt();
//                addEdge(v, w);
//            }
//        }catch (NoSuchElementException ex)
//        {
//            throw new IllegalArgumentException("wrong format of the graph file", ex);
//        }
//    }
    
    @Override
    public int V()
    {
        return V;
    }
    
    @Override
    public int E()
    {
        return E;
    }
    
    /**
     * 判断两个顶点之间是否存在一条边，可能是有向边 v -> w，也可能是无向边 v - w
     * 低效率的实现，子类可结合具体的数据结构实现，覆盖该方法
     */
    @Override
    public boolean edgeBetween(int v, int w)
    {
        for(int x: adj(v))
            if(x == w)
                return true;
        
        return false;
    }
    
    // 检验节点参数是否在正确的范围内，供子类检验参数时调用
    protected void validateVertex(int v)
    {
        GraphUtils.vaildateVertex(this.V, v);
    }
    
    @Override
    public String toString()
    {
        final String newLineSep = System.lineSeparator();
        String className = this.getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className).append(newLineSep);
        sb.append(V).append(" vertices; ").append(E).append(" edges").append(newLineSep);
        for(int v = 0; v < V; v++)
        {
            sb.append(v).append(": ");
            for (int w : adj(v))
                sb.append(w).append(" ");
            sb.append(newLineSep);
        }
        return sb.toString();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
