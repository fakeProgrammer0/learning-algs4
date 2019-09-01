package com.green.learning.algs4.string.regexp;

import com.green.learning.algs4.graph.AdjListUnweightedDigraph;
import com.green.learning.algs4.graph.Digraph;
import com.green.learning.algs4.graph.UnweightedDigraph;
import com.green.learning.algs4.list.*;
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
    private static final char ESCAPE_CHAR = '\\';
    
    private static final XSet<Character> META_CHARACTERS;
    
    static
    {
        char[] metaChars = {'(', ')', '|', '?', '*', '+'};
        XSet<Character> metaCharSet = new XLinkedHashSet<>();
        for (char metaChar : metaChars)
            metaCharSet.add(metaChar);
        
        META_CHARACTERS = metaCharSet;
    }
    
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
            // left parenthesis
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
                    // 2-way or
//                    lp = operators.pop();
//                    graph.addEdge(lp, op + 1);
//                    graph.addEdge(op, i);
                    
                    // multi-way or
                    XBag<Integer> ors = new XBag<>();
                    do
                    {
                        ors.add(op);
                        op = operators.pop();
                    } while (regexp.charAt(op) == '|');
                    
                    assert regexp.charAt(op) == '(';
                    lp = op;
                    
                    for (int or : ors)
                    {
                        graph.addEdge(lp, or + 1);
                        graph.addEdge(or, i);
                    }
                }
                
                if (regexp.charAt(op) == '(')
                    lp = op;
                else throw new IllegalArgumentException();
            }
            
            if (i < M - 1)
            {
                char lookahead = regexp.charAt(i + 1);
                if (lookahead == '*' || lookahead == '+' || lookahead == '?')
                {
                    i++;
                    switch (lookahead)
                    {
                        case '?':
                        {
                            graph.addEdge(lp, i);
                            graph.addEdge(i, i + 1);
                            break;
                        }
                        case '*':
                        {
                            graph.addEdge(lp, i);
                        }
                        case '+':
                        {
                            graph.addEdge(i, lp);
                            graph.addEdge(i, i + 1);
                        }
                    }
                }
            }
        }
        
        if (!operators.isEmpty())
            throw new IllegalArgumentException("invalid regular expression");
        
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
            char c = text.charAt(i);
            
            states.remove(M);
            XSet<Integer> newStates = new XLinkedHashSet<>();
            for (int state : states)
            {
                if (regexp.charAt(state) == WILDCARD_CHAR
                        || !META_CHARACTERS.contains(c) && regexp.charAt(state) == c)
                    newStates.add(state + 1);
            }
            
            transition(newStates);
            states = newStates;
            
            // optimized if no states reachable
            if (states.isEmpty()) return false;
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
