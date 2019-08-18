package com.green.learning.algs4.graph;

import java.util.*;

/**
 * 通过构造方法传递进来的Class，动态实例化特定的Graph类
 */
public class SymbolGraph extends AbstractGraph
{
    private AbstractGraph graph;
    protected final List<String> vertex2Symbol;
    protected final Map<String, Integer> symbol2Vertex;
    
    public SymbolGraph(AbstractGraph graph, Set<String> vertexSymbols)
    {
        if (vertexSymbols == null || vertexSymbols.size() != graph.V())
            throw new IllegalArgumentException("Symbols' size should equal to the number of vertices in the graph");
        this.graph = graph;
        List<String> symbolList = new ArrayList<>(vertexSymbols.size());
        Map<String, Integer> map = new HashMap<>();
        int v = 0;
        for (String symbol : vertexSymbols)
        {
            map.put(symbol, v++);
            symbolList.add(symbol);
        }
        vertex2Symbol = Collections.unmodifiableList(symbolList);
        symbol2Vertex = Collections.unmodifiableMap(map);
        
//        try
//        {
//            this.graph = (AbstractGraph) graph.clone();
//        } catch (CloneNotSupportedException e)
//        {
//            e.printStackTrace();
//        }
    }
    
    protected void validateSymbol(String symbol)
    {
        if (!symbol2Vertex.containsKey(symbol))
            throw new IllegalArgumentException("Invalid symbol: " + symbol);
    }
    
//    public void addEdge(String x, String y)
//    {
//        validateSymbol(x);
//        validateSymbol(y);
//        int v = symbol2Vertex.get(x);
//        int w = symbol2Vertex.get(y);
//        graph.addEdge(v, w);
//    }
    
    public Iterable<String> adj(String x)
    {
        validateSymbol(x);
        int v = symbol2Vertex.get(x);
        List<String> adjSymbols = new ArrayList<>();
        for (int w : graph.adj(v))
            adjSymbols.add(vertex2Symbol.get(w));
        return adjSymbols;
    }
    
    @Override
    public Iterable<Integer> adj(int v)
    {
        return graph.adj(v);
    }
    
    public List<String> symbols()
    {
        return vertex2Symbol;
    }
    
    public Map<String, Integer> symbol2VertexMap()
    {
        return symbol2Vertex;
    }
    
    public AbstractGraph getGraph()
    {
        return graph;
    }
    
    @Override
    public String toString()
    {
        final String newLineSep = System.lineSeparator();
        StringBuilder sb = new StringBuilder("Symbol Graph").append(newLineSep);
        sb.append(graph.V()).append(" vertices; ").append(graph.E()).append(" edges").append(newLineSep);
        for (int v = 0; v < graph.V(); v++)
        {
            sb.append(vertex2Symbol.get(v)).append(": ");
            for (int w : graph.adj(v))
                sb.append(vertex2Symbol.get(w)).append(", ");
            int idx = sb.lastIndexOf(", ");
            if (idx + ", ".length() == sb.length())
                sb.delete(idx, sb.length());
            sb.append(newLineSep);
        }
        return sb.toString();
    }
}
