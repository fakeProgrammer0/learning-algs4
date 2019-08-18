package com.green.learning.algs4.graph.routine;


import com.green.learning.algs4.graph.Graph;

import java.util.*;

/**
 * 判断给定图是否存在环
 * 如果存在，找出所有回路
 * 适用于有向图和无向图
 */
public class CirclePathsSolver
{
    private final boolean[] marked;
    private final boolean[] inStack;
    private final int[] edgeTo;
    private final static int NULL_VERTEX = -1;
    
    private boolean hasACircle = false;
//    private List<Integer> circlePaths;
    private Set<CirclePath> circlePaths;
    
    public CirclePathsSolver(Graph graph)
    {
        marked = new boolean[graph.V()];
        inStack = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        Arrays.fill(edgeTo, NULL_VERTEX);
        circlePaths = new HashSet<>();
//        edgeTo[0] = 0;
        for(int v = 0; v < graph.V(); v++)
        {
            if(!marked[v]) // 每次遍历一个连通分量
                dfs(graph, v);
        }
        if(circlePaths.isEmpty()) circlePaths = null;
    }
    
    private void dfs(Graph graph, int v)
    {
        // 全局终止搜索，提高效率
        // 防止circle变量被覆盖
//        if(hasACircuit) return;
        
        marked[v] = true;
        inStack[v] = true;
        for(int w: graph.adj(v))
        {
            if(!marked[w])
            {
                edgeTo[w] = v;
                dfs(graph, w);
            }
            else if(edgeTo[v] != w && inStack[w])
            {
//                hasACircuit = true;
                // find a path
                LinkedList<Integer> circlePath = new LinkedList<>();
                int x = v;
                for(; x != w; x = edgeTo[x])
                    circlePath.addFirst(x);
//                if(x == NULL_VERTEX) continue;
                circlePath.addFirst(w);
                circlePaths.add(new CirclePath(circlePath));
//                return;
            }
    
//            if(hasACircuit) return;
        }
        
        inStack[v] = false;
    }
    
    public boolean hasACircle()
    {
//        return hasACircuit;
        return circlePaths != null && circlePaths.size() > 0;
    }
    
    public Set<CirclePath> getCirclePaths()
    {
        return circlePaths;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(circlePaths.size() + " circle paths: \n");
        sb.append("size\tpath\n");
        for(CirclePath circlePath: circlePaths)
            sb.append(circlePath.getPath().size()).append("\t").append(circlePath).append("\n");
        return sb.toString();
    }
    
    public static class CirclePath
    {
        private List<Integer> path;
    
        public CirclePath(List<Integer> path)
        {
            this.path = new ArrayList<>(path);
        }
    
        public List<Integer> getPath()
        {
            return path;
        }
    
        public void setPath(List<Integer> path)
        {
            this.path = new ArrayList<>(path);
        }
    
        @Override
        public boolean equals(Object obj)
        {
            if(obj == null) return false;
            if(this == obj) return true;
            if(obj.getClass() != this.getClass()) return false;
            CirclePath that = (CirclePath) obj;
            if(this.path.size() != that.path.size()) return false;
            
            int size = this.path.size();
//            List<Integer> path1 = new ArrayList<>(this.path);
//            List<Integer> path2 = new ArrayList<>(that.path);
            List<Integer> path1 = this.path;
            List<Integer> path2 = that.path;
            int start = path2.indexOf(path1.get(0));
            if(start == -1) return false;
            for(int i = 0, j = start;i < size; i++, j = (j+1) % size)
            {
                if(path1.get(i) != path2.get(j)) return false;
            }
            return true;
        }
    
        @Override
        public String toString()
        {
            return path.toString();
        }
    }
}
