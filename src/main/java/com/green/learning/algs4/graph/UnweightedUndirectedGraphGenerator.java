package com.green.learning.algs4.graph;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdRandom;

import java.util.HashSet;
import java.util.Set;

public class UnweightedUndirectedGraphGenerator
{
    private static class DistinctEdge extends UndirectedEdge
    {
        public DistinctEdge(int v, int w)
        {
            super(v, w);
            if(v > w)
            {
                this.v = w;
                this.w = v;
            }
        }
        
        @Override
        public boolean equals(Object obj)
        {
            if (obj == null) return false;
            if (this == obj) return true;
            if (obj.getClass() != this.getClass()) return false;
            DistinctEdge edge = (DistinctEdge) obj;
            return this.v == edge.v && this.w == edge.w;
        }
    
        @Override
        public int hashCode()
        {
            // 低效率实现，容易碰撞
//            return Integer.valueOf(v).hashCode() + Integer.valueOf(w).hashCode();
            return v * v / 2 + w * w / 2;
        }
    }
    
    private UnweightedUndirectedGraphGenerator()
    {
    }
    
    private static void validateEdgeExistenceProbability(double p)
    {
        if (p < 0.0 || p > 1.0)
            throw new IllegalArgumentException("The probability p should be between 0.0 and 1.0");
    }
    
    private static int[] randomVertices(int V)
    {
        GraphUtils.validateVerticesCnt(V);
        int[] vertices = new int[V];
        for (int i = 0; i < V; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);
        return vertices;
    }
    
    // no loops, no parallel edges
    public static UnweightedUndirectedGraph simpleGraph(int V, int E)
    {
        GraphUtils.validateVerticesCnt(V);
        GraphUtils.validateEdgesCnt(E);
        
        if (E > (long) V * (V - 1) / 2)
            throw new IllegalArgumentException("Too many edges");
        UnweightedUndirectedGraph graph = new AdjListUnweightedUndirectedGraph(V);
        Set<DistinctEdge> edges = new HashSet<>();
        while (graph.E() < E)
        {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            DistinctEdge edge = new DistinctEdge(v, w);
            if (!edges.contains(edge) && v != w)
            {
                graph.addEdge(v, w);
                edges.add(edge);
            }
        }
        
        return graph;
    }
    
    /**
     * @param V the number of vertices
     * @param p the probability of a edge exists
     * @return a {@link UnweightedUndirectedGraph} with no loops and no parallel edges
     */
    public static UnweightedUndirectedGraph randomSimpleGraph(int V, double p)
    {
        GraphUtils.validateVerticesCnt(V);
        validateEdgeExistenceProbability(p);
        UnweightedUndirectedGraph graph = new AdjListUnweightedUndirectedGraph(V);
        for (int v = 0; v < V; v++)
            for (int w = v + 1; w < V; w++)
            {
                if (StdRandom.bernoulli(p))
                {
                    graph.addEdge(v, w);
                }
            }
        
        return graph;
    }
    
    // complete graph 完全图
    public static UnweightedUndirectedGraph completeGraph(int V)
    {
        GraphUtils.validateVerticesCnt(V);
        UnweightedUndirectedGraph graph = new AdjListUnweightedUndirectedGraph(V);
        for (int v = 0; v < V; v++)
            for (int w = v + 1; w < V; w++)
                graph.addEdge(v, w);
        return graph;
    }
    
    public static UnweightedUndirectedGraph bipartiteGraph(int V1, int V2, int E)
    {
        GraphUtils.validateVerticesCnt(V1);
        GraphUtils.validateVerticesCnt(V2);
        GraphUtils.validateEdgesCnt(E);
        if (E > (long) V1 * V2) throw new IllegalArgumentException("too many edges");
        final int V = V1 + V2;
        int[] vertices = randomVertices(V);
        UnweightedUndirectedGraph graph = new AdjListUnweightedUndirectedGraph(V);
        Set<DistinctEdge> edgeSet = new HashSet<>();
        while (graph.E() < E)
        {
            int v = vertices[StdRandom.uniform(V1)];
            int w = vertices[StdRandom.uniform(V2) + V1];
            DistinctEdge edge = new DistinctEdge(v, w);
            if (!edgeSet.contains(edge))
            {
                edgeSet.add(edge);
                graph.addEdge(v, w);
            }
        }
        return graph;
    }
    
    public static UnweightedUndirectedGraph bipartiteGraph(int V1, int V2, double p)
    {
        GraphUtils.validateVerticesCnt(V1);
        GraphUtils.validateVerticesCnt(V2);
        validateEdgeExistenceProbability(p);
        final int V = V1 + V2;
        int[] vertices = randomVertices(V);
        UnweightedUndirectedGraph graph = new AdjListUnweightedUndirectedGraph(V);
        for (int v = 0; v < V1; v++)
            for (int w = V1; w < V; w++)
                if (StdRandom.bernoulli(p))
                    graph.addEdge(vertices[v], vertices[w]);
        return graph;
    }
    
    public static UnweightedUndirectedGraph completeBipartiteGraph(int V1, int V2)
    {
        GraphUtils.validateVerticesCnt(V1);
        GraphUtils.validateVerticesCnt(V2);
        final int V = V1 + V2;
        UnweightedUndirectedGraph graph = new AdjListUnweightedUndirectedGraph(V);
        int[] vertices = randomVertices(V);
        for (int i = 0; i < V1; i++)
            for (int j = V1; j < V; j++)
                graph.addEdge(vertices[i], vertices[j]);
        return graph;
    }
    
    /**
     * @param V the number of vertices on the path
     * @return a path in which each vertex, from vertex 0 to vertex V - 1, appears exactly once
     */
    public static UnweightedUndirectedGraph path(int V)
    {
        GraphUtils.validateVerticesCnt(V);
        int[] vertices = randomVertices(V);
        UnweightedUndirectedGraph graph = new AdjListUnweightedUndirectedGraph(V);
        for (int i = 0; i < V - 1; i++)
            graph.addEdge(vertices[i], vertices[i + 1]);
        return graph;
    }
    
    public static UnweightedUndirectedGraph cycle(int V)
    {
        GraphUtils.validateVerticesCnt(V);
        int[] vertices = randomVertices(V);
        UnweightedUndirectedGraph graph = new AdjListUnweightedUndirectedGraph(V);
        for (int i = 0; i < V - 1; i++)
            graph.addEdge(vertices[i], vertices[i + 1]);
        graph.addEdge(vertices[0], vertices[V - 1]);
        return graph;
    }
    
    /**
     * W6的轮图有7个顶点
     *
     * @param V the number of vertices on the wheel graph's cycle
     * @return a wheel graph: every vertex on a cycle connects to a central vertex
     */
    public static UnweightedUndirectedGraph wheelGraph(int V)
    {
        GraphUtils.validateVerticesCnt(V); // V > 0, W0退化为单个点
        int[] vertices = randomVertices(V + 1);
        UnweightedUndirectedGraph graph = new AdjListUnweightedUndirectedGraph(V + 1);
        for (int i = 1; i < V; i++)
            graph.addEdge(vertices[i], vertices[i + 1]);
        graph.addEdge(vertices[V], vertices[1]);
        
        // connect vertex 0 to every other vertex
        for (int i = 1; i < V + 1; i++)
            graph.addEdge(vertices[0], vertices[i]);
        return graph;
    }
    
    /**
     * @param V the number of vertices
     * @return a star graph: a single vertex connects to every other vertex
     */
    public static UnweightedUndirectedGraph starGraph(int V)
    {
        GraphUtils.validateVerticesCnt(V);
        int[] vertices = randomVertices(V);
        UnweightedUndirectedGraph graph = new AdjListUnweightedUndirectedGraph(V);
        for (int i = 1; i < V; i++)
            graph.addEdge(vertices[0], vertices[i]);
        return graph;
    }
    
    // 完全树，二叉堆
    public static UnweightedUndirectedGraph completeBinaryTree(int V)
    {
        GraphUtils.validateVerticesCnt(V);
        int[] vertices = randomVertices(V);
        UnweightedUndirectedGraph graph = new AdjListUnweightedUndirectedGraph(V);
        for (int i = 1; i < V; i++)
            graph.addEdge(vertices[(i - 1) / 2], vertices[i]);
        //        for(int i = 0; i < V / 2; i++)
        //        {
        //            int parent = vertices[i];
        //            int child1 = vertices[2 * i + 1];
        //            int child2 = vertices[2 * i + 2];
        //            graph.addEdge(parent, child1);
        //            graph.addEdge(parent, child2);
        //        }
        return graph;
    }
    
    /**
     * @param V the number of vertices on the cycle
     * @param E the number of edges of the cycle
     * @return an Eulerian Cycle
     */
    public static UnweightedUndirectedGraph eulerianCycle(int V, int E)
    {
        GraphUtils.validateVerticesCnt(V);
        if (E <= 0) throw new IllegalArgumentException("The must be at least one edge of an Eulerian Cycle");
        // E 个节点，刚好组成一条path，可以从头到尾走一遍
        int[] vertices = new int[E];
        for (int i = 0; i < E; i++)
            vertices[i] = StdRandom.uniform(V);
        UnweightedUndirectedGraph graph = new AdjListUnweightedUndirectedGraph(V);
        for (int i = 0; i < E - 1; i++)
            graph.addEdge(vertices[i], vertices[i + 1]);
        graph.addEdge(vertices[E - 1], vertices[0]);
        return graph;
    }
    
    public static UnweightedUndirectedGraph eulerianPath(int V, int E)
    {
        GraphUtils.validateVerticesCnt(V);
        if (E <= 0) throw new IllegalArgumentException("The must be at least one edge of an Eulerian Cycle");
        int[] vertices = new int[E + 1];
        for (int i = 0; i < E + 1; i++)
            vertices[i] = StdRandom.uniform(V);
        UnweightedUndirectedGraph graph = new AdjListUnweightedUndirectedGraph(V);
        // E个节点组成的路径，从头走到尾
        for (int i = 0; i < E; i++)
            graph.addEdge(vertices[i], vertices[i + 1]);
        return graph;
    }
    
    /**
     * Returns a uniformly random {@code k}-regular graph on {@code V} vertices
     * (not necessarily simple). The graph is simple with probability only about e^(-k^2/4),
     * which is tiny when k = 14.
     *
     * @param V the number of vertices in the graph
     * @param k degree of each vertex
     * @return a uniformly random {@code k}-regular graph on {@code V} vertices.
     */
    public static UnweightedUndirectedGraph regular(int V, int k)
    {
        if (V * k % 2 != 0) throw new IllegalArgumentException("V * k must be even");
        UnweightedUndirectedGraph G = new AdjListUnweightedUndirectedGraph(V);
        
        // create k copies of each vertex
        int[] vertices = new int[V * k];
        for (int v = 0; v < V; v++)
        {
            for (int j = 0; j < k; j++)
            {
                vertices[v + V * j] = v;
            }
        }
        
        // pick a random perfect matching
        StdRandom.shuffle(vertices);
        for (int i = 0; i < V * k / 2; i++)
        {
            G.addEdge(vertices[2 * i], vertices[2 * i + 1]);
        }
        return G;
    }
    
    // http://www.proofwiki.org/wiki/Labeled_Tree_from_Prüfer_Sequence
    // http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.36.6484&rep=rep1&type=pdf
    
    /**
     * Returns a uniformly random tree on {@code V} vertices.
     * This algorithm uses a Prufer sequence and takes time proportional to <em>V log V</em>.
     *
     * @param V the number of vertices in the tree
     * @return a uniformly random tree on {@code V} vertices
     */
    public static UnweightedUndirectedGraph tree(int V)
    {
        UnweightedUndirectedGraph G = new AdjListUnweightedUndirectedGraph(V);
        
        // special case
        if (V == 1) return G;
        
        // Cayley's theorem: there are V^(V-2) labeled trees on V vertices
        // Prufer sequence: sequence of V-2 values between 0 and V-1
        // Prufer's proof of Cayley's theorem: Prufer sequences are in 1-1
        // with labeled trees on V vertices
        int[] prufer = new int[V - 2];
        for (int i = 0; i < V - 2; i++)
            prufer[i] = StdRandom.uniform(V);
        
        // degree of vertex v = 1 + number of times it appers in Prufer sequence
        int[] degree = new int[V];
        for (int v = 0; v < V; v++)
            degree[v] = 1;
        for (int i = 0; i < V - 2; i++)
            degree[prufer[i]]++;
        
        // pq contains all vertices of degree 1
        MinPQ<Integer> pq = new MinPQ<Integer>();
        for (int v = 0; v < V; v++)
            if (degree[v] == 1) pq.insert(v);
        
        // repeatedly delMin() degree 1 vertex that has the minimum index
        for (int i = 0; i < V - 2; i++)
        {
            int v = pq.delMin();
            G.addEdge(v, prufer[i]);
            degree[v]--;
            degree[prufer[i]]--;
            if (degree[prufer[i]] == 1) pq.insert(prufer[i]);
        }
        G.addEdge(pq.delMin(), pq.delMin());
        return G;
    }
    
    public static void addRandomEdges(UnweightedUndirectedGraph graph, int E)
    {
        GraphUtils.validateEdgesCnt(E);
        for(int i = 0; i < E; i++)
        {
            int v = StdRandom.uniform(graph.V());
            int w = StdRandom.uniform(graph.V());
            graph.addEdge(v, w);
        }
    }
}
