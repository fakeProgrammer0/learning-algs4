package com.green.learning_algs4.graph;

import java.util.Set;

public class UndirectedSymbolGraph extends SymbolGraph
{
    private UnweightedUndirectedGraph graph;
    
    public UndirectedSymbolGraph(UnweightedUndirectedGraph graph, Set<String> vertexSymbols)
    {
        super(graph, vertexSymbols);
        this.graph = graph;
    }
    
    public void addEdge(String x, String y)
    {
        validateSymbol(x);
        validateSymbol(y);
        int v = symbol2Vertex.get(x);
        int w = symbol2Vertex.get(y);
        graph.addEdge(v, w);
    }
    
    @Override
    public UnweightedUndirectedGraph getGraph()
    {
        return this.graph;
    }
    
    // 如果Graph有提供removeVertex这样的操作，那么就得考虑使用代理模式了
    //    private static class UnModifiedUndirectedGraph extends AdjListUndirectedGraph
    //    {
    //        public UnModifiedUndirectedGraph(int V)
    //        {
    //            super(V);
    //        }
    //
    //        // 仅开放接口给UndirectedSymbolGraph使用
    //        private void addEdgeHelper(int v, int w)
    //        {
    //            super.addEdge(v, w);
    //        }
    //
    //        @Override
    //        public void addEdge(int v, int w)
    //        {
    //            throw new UnsupportedOperationException("Add edge operation is not allowed. Use UndirectedSymbolGraph.addEdge instead");
    //        }
    //    }
}
