package com.green.learning_algs4.graph.routine;


import com.green.learning_algs4.graph.GraphUtils;
import com.green.learning_algs4.graph.UndirectedGraph;

import java.util.*;

/*
A simple graph G is called bipartite if its vertex set V can be partitioned into two disjoint
sets V1 and V2 such that every edge in the graph connects a vertex in V1 and a vertex in V2
(so that no edge in G connects either two vertices in V1 or two vertices in V2). When this
condition holds, we call the pair (V1, V2) a bipartition of the vertex set V of G.

1.简单图
2.两个不相交的顶点集 U 和 V

用“染色法”解决二分图问题：判断一个图是否为二分图
如果是二分图，则给出图的两个顶点集 U 和 V
否则，找到一条具有奇数个顶点的路径
 */
public class BipartiteGraphSolverDFS extends BipartiteGraphSolver
{
    private final boolean[] marked; // 顶点是否已被染色（即遍历）
    private final int[] edgeTo;
    private static final int NULL_VERTEX = -1;
    
    private final int[] ids;
    private static final int NULL_ID = -1;
    private static final int NEG_ID = 0;
    private static final int POS_ID = 1;
    
    private boolean isBipartiteGraph = true;
    private Set<Integer>[] bipartiteVertices;
    private List<Integer> oddLengthCycle;
    
    public BipartiteGraphSolverDFS(UndirectedGraph graph)
    {
//        Bipartite
        marked = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        Arrays.fill(edgeTo, NULL_VERTEX);
        ids = new int[graph.V()];
        Arrays.fill(ids, NULL_ID);
        
        for(int v = 0; v < graph.V(); v++)
        {
            if (!marked[v])
                dfs(graph, v, NEG_ID); // 遍历每一个连通分量
            if(!isBipartiteGraph)
                break;
        }
        
        // 划分顶点集
        if (isBipartiteGraph)
        {
            bipartiteVertices = (Set<Integer>[]) new Set[2];
            bipartiteVertices[0] = new LinkedHashSet<>();
            bipartiteVertices[1] = new LinkedHashSet<>();
            for (int v = 0; v < ids.length; v++)
            {
                int id = ids[v];
                bipartiteVertices[id].add(v);
            }
        }
    }
    
    private void dfs(UndirectedGraph graph, int v, int id)
    {
        if(!isBipartiteGraph) return;
        
        marked[v] = true;
        ids[v] = id;
        for (int w : graph.adj(v))
        {
            if(!marked[w])
            {
                edgeTo[w] = v;
                dfs(graph, w, inverseId(id));
            }
            else if (ids[w] == ids[v]) // 相邻两个顶点染色相同
            {
                isBipartiteGraph = false;
                oddLengthCycle = new ArrayList<>();
                oddLengthCycle.add(w); // 路径上w出现两次
                for(int x = v; x != w; x = edgeTo[x]) // w和v相邻，当前在遍历v，说明w仍未出栈，所以存在一条从w到v的路径
                    oddLengthCycle.add(x);
                oddLengthCycle.add(w);
            }
            
            if(!isBipartiteGraph) break;
        }
    }
    
    private int inverseId(int id)
    {
        assert id != NULL_ID;
        return 1 - id;
    }
    
    @Override
    public boolean isBipartiteGraph()
    {
        return isBipartiteGraph;
    }
    
    @Override
    public Set<Integer>[] getBipartiteVertices()
    {
        checkBipartite();
        return bipartiteVertices;
    }
    
    @Override
    public boolean inSameColor(int v, int w)
    {
        checkBipartite();
        GraphUtils.vaildateVertex(this.marked.length, v);
        GraphUtils.vaildateVertex(this.marked.length, w);
        return ids[v] == ids[w];
    }
    
    @Override
    public Iterable<Integer> getOddLengthCycle()
    {
        return oddLengthCycle;
    }
}
