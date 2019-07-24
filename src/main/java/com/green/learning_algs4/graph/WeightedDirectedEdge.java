package com.green.learning_algs4.graph;

public class WeightedDirectedEdge extends DirectedEdge
        implements Comparable<WeightedDirectedEdge>
{
    private final double weight;
    
    public WeightedDirectedEdge(int v, int w, double weight)
    {
        super(v, w);
        this.weight = weight;
    }
    
    public double getWeight()
    {
        return weight;
    }
    
    /**
     * Compare the weight of two weighted edges.
     * @param o
     * @return 0 if both edges have the same weight;
     *          -1 if the object has a edge whose weight is less than the other's.
     *          +1 on the contrary
     */
    @Override
    public int compareTo(WeightedDirectedEdge o)
    {
        if(o == null) throw new NullPointerException();
        if(this.weight == o.weight) return 0;
        else if(this.weight < o.weight) return -1;
        return 1;
    }
    
    @Override
    public String toString()
    {
        return String.format("<%d -[ %.6f ]-> %d>", v, weight, w);
//        return "<" + v + " -> " + w + "; " + String.format("%.2f",weight) + ">";
    }
}
