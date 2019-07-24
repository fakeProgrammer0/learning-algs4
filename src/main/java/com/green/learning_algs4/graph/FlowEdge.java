package com.green.learning_algs4.graph;

public class FlowEdge extends DirectedEdge
{
    private final double capacity;
    private double flow;
    
    public FlowEdge(int v, int w, double capacity)
    {
        super(v, w);
        this.capacity = capacity;
    }
    
    public double getCapacity()
    {
        return capacity;
    }
    
    public double getFlow()
    {
        return flow;
    }
    
    public double residualCapacityTo(int vertex)
    {
        if (vertex == w) return capacity - flow; // forward edge
        if (vertex == v) return flow; // backward edge
        throw new IllegalArgumentException("vertex should be " + v + " or " + w);
    }
    
    public void addResidualFlowTo(int vertex, double delta)
    {
        if (vertex == w) // forward edge
        {
            if (delta > capacity - flow)
                throw new IllegalArgumentException("delta exceeds the residual capacity");
            
            flow += delta;
        } else if (vertex == v) // backward edge
        {
            if (flow < delta)
                throw new IllegalArgumentException("delta exceeds the residual capacity");
            
            flow -= delta;
        } else
            throw new IllegalArgumentException("vertex should be " + v + " or " + w);
    }
    
    @Override
    public String toString()
    {
        return String.format("<%d -[ %.2f / %.2f ]-> %d>", v, flow, capacity, w);
        //        return "<" + v + " -[ " + flow + " / " + capacity + " ]-> " + w + ">";
    }
}
