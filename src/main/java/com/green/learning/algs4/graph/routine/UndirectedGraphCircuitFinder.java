package com.green.learning.algs4.graph.routine;


import com.green.learning.algs4.graph.UndirectedGraph;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 判断给定图是否存在环
 * 如果存在，找出一条回路
 * 适用于无向图
 * 处理 self loop && parallel edges 的情况
 */
public class UndirectedGraphCircuitFinder extends CircuitFinder
{
    private final boolean[] marked;
    private final int[] edgeTo;
    private final static int NULL_VERTEX = -1;
    
    private LinkedList<Integer> circuit;
    
    public UndirectedGraphCircuitFinder(UndirectedGraph graph)
    {
        marked = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        Arrays.fill(edgeTo, NULL_VERTEX);
        if(hasParallelEdges(graph)) return; // constructor的return
        
        Arrays.fill(marked, false); // 重新初始化marked数组
        for(int v = 0; v < graph.V(); v++)
        {
            if(!marked[v]) // 每次遍历一个连通分量
                dfs(graph, v);
        }
    }
    
    private boolean hasParallelEdges(UndirectedGraph graph)
    {
        for(int v = 0; v < graph.V(); v++)
        {
            for(int w: graph.adj(v))
            {
                if(marked[w])
                {
                    circuit = new LinkedList<>();
                    circuit.add(v);
                    circuit.add(w);
                    circuit.add(v);
                    return true;
                }
                marked[w] = true;
            }
            
            for(int w: graph.adj(v))
                marked[w] = false;
        }
        
        return false;
    }
    
    private void dfs(UndirectedGraph graph, int v)
    {
        // 全局终止搜索，提高效率
        // 防止circle变量被覆盖
        if(hasACircuit()) return;
        
        marked[v] = true;
        for(int w: graph.adj(v))
        {
            if(w == v) // self loop
            {
                circuit = new LinkedList<>();
                circuit.add(v);
                circuit.add(v);
            }
            else if(!marked[w])
            {
                edgeTo[w] = v;
                dfs(graph, w);
            }
            // 不是由w直接到v的，即w不是v的前继节点 predecessor
            // 遍历顺序 w -> ... -> p -> v -> w
            // p必须存在，这里忽略了两个顶点的回路的情况：无向图平行边
            else if(edgeTo[v] != w)
            {
                // find a path
                circuit = new LinkedList<>();
                circuit.addFirst(w);
                // 第一次访问，edgeTo中肯定是由 w -> ... -> v
                for(int x = v; x != w; x = edgeTo[x])
                    circuit.addFirst(x);
                circuit.addFirst(w);
            }
    
            if(hasACircuit()) return;
        }
        
    }
    
    public boolean hasACircuit()
    {
        return circuit != null && circuit.size() > 0;
    }
    
    public Iterable<Integer> getCircuit()
    {
        return circuit;
    }
}
