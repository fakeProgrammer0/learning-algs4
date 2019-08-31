package com.green.learning.algs4.string.regexp;

import com.green.learning.algs4.graph.AdjListUnweightedDigraph;
import com.green.learning.algs4.graph.Digraph;
import com.green.learning.algs4.graph.UnweightedDigraph;
import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XLinkedStack;
import com.green.learning.algs4.list.XQueue;
import com.green.learning.algs4.list.XStack;
import com.green.learning.algs4.set.XLinkedHashSet;
import com.green.learning.algs4.set.XSet;
import com.green.learning.algs4.string.StringUtils;

/**
 * @see edu.princeton.cs.algs4.NFA
 */
public class RegExpNFA
{
    private final String regexp;
    
    /**
     * accept state
     */
    private final int M;
    
    private final UnweightedDigraph digraph;
    
    private static final char WILDCARD_CHAR = '.';
    
    public RegExpNFA(String regexp)
    {
        if (StringUtils.isEmpty(regexp))
            throw new IllegalArgumentException();
//        if (regexp.charAt(0) == '(' && regexp.charAt(regexp.length() - 1) == ')')
//        {
//            this.regexp = regexp;
//        } else
        {
            this.regexp = '(' + regexp + ')';
        }
        this.M = this.regexp.length();
        digraph = buildTransitionGraph();
    }
    
    private UnweightedDigraph buildTransitionGraph()
    {
        UnweightedDigraph graph = new AdjListUnweightedDigraph(M + 1);
        XStack<Integer> operators = new XLinkedStack<>();
        
        for (int i = 0; i < M; i++)
        {
            int lp = i;
            char token = regexp.charAt(i);
            if (token == '|')
            {
                operators.push(i);
            } else if (token == '(')
            {
                operators.push(i);
                graph.addEdge(i, i + 1);
            } else if (token == ')')
            {
                graph.addEdge(i, i + 1);
                int op = operators.pop();
                if (regexp.charAt(op) == '|')
                {
                    lp = operators.pop();
                    graph.addEdge(lp, op + 1);
                    graph.addEdge(op, i);
                }
            }
            
            if (i < M - 1)
            {
                char lookahead = regexp.charAt(i + 1);
                if(lookahead == '*' || lookahead == '+')
                {
                    graph.addEdge(i + 1, lp);
                    graph.addEdge(i + 1, i + 2);
                    if(lookahead == '*')
                        graph.addEdge(lp, i + 1);
                    i++;
                }
            }
        }
        
        return graph;
    }
    
    public boolean match(String text)
    {
        XSet<Integer> states = new XLinkedHashSet<>(M);
        states.add(0);
        transition(states);
        
        final int N = text.length();
        for (int i = 0; i < N; i++)
        {
            states.remove(M);
            XSet<Integer> newStates = new XLinkedHashSet<>();
            char c = text.charAt(i);
            for (int state : states)
            {
                if (regexp.charAt(state) == c || regexp.charAt(state) == WILDCARD_CHAR)
                    newStates.add(state + 1);
            }
            
            transition(newStates);
            states = newStates;
        }
        
        return states.contains(M);
    }
    
    /**
     * a bfs method helps states transition
     *
     * @param states current states
     */
    private void transition(XSet<Integer> states)
    {
        XQueue<Integer> queue = new XLinkedQueue<>();
        for (Integer state : states)
            queue.enqueue(state);
        
        while (!queue.isEmpty())
        {
            int v = queue.dequeue();
            for (int w : digraph.adj(v))
                if (!states.contains(w))
                {
                    queue.enqueue(w);
                    states.add(w);
                }
        }
    }
}
