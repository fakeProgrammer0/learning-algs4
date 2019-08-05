package com.green.learning_algs4.string;

import com.green.learning_algs4.set.XLinkedHashSet;
import com.green.learning_algs4.set.XSet;
import com.green.learning_algs4.st.LinkedHashST;
import com.green.learning_algs4.st.ST;

/**
 * @see edu.princeton.cs.algs4.KMP
 */
public class KMP
{
    private static class DFA
    {
        private final int[][] stateTrans;
        private final Alphabet alphabet; // 原来alphabet还可以这么用，简直很神了
        
        public DFA(String pattern)
        {
            if (pattern == null || pattern.isEmpty())
                throw new IllegalArgumentException("pattern is null or empty");
            alphabet = new Alphabet(pattern);
            int R = alphabet.radix();
            
            final int M = pattern.length();
            stateTrans = new int[R][M];
            
            // init transitions at state 0
            stateTrans[alphabet.toIndex(pattern.charAt(0))][0] = 1;
            
            int x = 0;
            for (int j = 1; j < M; j++)
            {
                // set mismatch transitions
                for (int r = 0; r < R; r++)
                    stateTrans[r][j] = stateTrans[r][x];
                
                // set match transition
                int index = alphabet.toIndex(pattern.charAt(j));
                stateTrans[index][j] = j + 1;
                
                // update mismatch state x
                x = stateTrans[index][x];
            }
        }
        
        /**
         * make a transition and return the next state
         *
         * @param currState the current state
         * @param c         the input character
         * @return the next state after the transition
         */
        public int transition(int currState, char c)
        {
            if(!alphabet.contains(c)) return 0;
            return stateTrans[alphabet.toIndex(c)][currState];
        }
    }
    
    private final int M;
    private final DFA dfa;
    
    public KMP(String pattern)
    {
        if (pattern == null || pattern.isEmpty())
            throw new IllegalArgumentException("pattern is null or empty");
        M = pattern.length();
        dfa = new DFA(pattern);
    }
    
    public int search(String text)
    {
        if (text == null || text.isEmpty())
            throw new IllegalArgumentException("text is null or empty");
        int i, j;
        for (i = 0, j = 0; i < text.length() && j < M; i++)
            j = dfa.transition(j, text.charAt(i));
        if (j == M) return i - M;
        return -1;
    }
    
    public static int search(String text, String pattern)
    {
        KMP kmp = new KMP(pattern);
        return kmp.search(text);
    }
    
    /**
     * consumes more space and slow than DFA
     * better implementation than DFA3
     */
    @Deprecated
    private static class DFA2
    {
        private final ST<Character, Integer>[] stateTrans;
        private final XSet<Character> charSet;
        
        @SuppressWarnings("unchecked")
        public DFA2(String pattern)
        {
            if (pattern == null || pattern.isEmpty())
                throw new IllegalArgumentException("pattern is null or empty");
            
            charSet = new XLinkedHashSet<>();
            for (int j = 0; j < pattern.length(); j++)
                charSet.add(pattern.charAt(j));
            
            final int M = pattern.length();
            stateTrans = (ST<Character, Integer>[]) new ST[M];
            for(int j = 0; j < M; j++)
                stateTrans[j] = new LinkedHashST<>();
            
            // init transitions at state 0
            for (char c : charSet)
                stateTrans[0].put(c,0);
            stateTrans[0].put(pattern.charAt(0),1);
            
            int x = 0;
            for (int j = 1; j < M; j++)
            {
                // set mismatch transitions
                for (char c : charSet)
                    stateTrans[j].put(c, transition(x,c));
                
                char input = pattern.charAt(j);
                // set match transition
                stateTrans[j].put(input,j+1);
                
                // update mismatch state x
                x = transition(x, input);
            }
        }
        
        /**
         * make a transition and return the next state
         *
         * @param currState the current state
         * @param c         the input character
         * @return the next state after the transition
         */
        public int transition(int currState, char c)
        {
            if(!charSet.contains(c)) return 0;
            return stateTrans[currState].get(c);
        }
    }
    
    @Deprecated
    private static class DFA3
    {
        private static class Transition
        {
            private final int currState;
            private final char c;
            
            public Transition(int currState, char c)
            {
                this.currState = currState;
                this.c = c;
            }
            
            @Override
            public boolean equals(Object obj)
            {
                if (obj == null) return false;
                if (obj == this) return true;
                if (!(obj instanceof Transition)) return false;
                Transition that = (Transition) obj;
                return this.currState == that.currState
                        && this.c == that.c;
            }
            
            @Override
            public int hashCode()
            {
                return Integer.hashCode(currState) ^ Character.hashCode(c);
            }
            
            @Override
            public String toString()
            {
                return String.format("{state: %d, input: %c}", currState, c);
            }
        }
        
        private final ST<Transition, Integer> trans2NextState;
        private final XSet<Character> charSet;
        
        public DFA3(String pattern)
        {
            if (pattern == null || pattern.isEmpty())
                throw new IllegalArgumentException("pattern is null or empty");
            
            charSet = new XLinkedHashSet<>();
            for (int j = 0; j < pattern.length(); j++)
                charSet.add(pattern.charAt(j));
            
            trans2NextState = new LinkedHashST<>();
            
            // init transitions at state 0
            for (char c : charSet)
                trans2NextState.put(new Transition(0, c), 0);
            trans2NextState.put(new Transition(0, pattern.charAt(0)), 1);
            
            int x = 0;
            for (int j = 1; j < pattern.length(); j++)
            {
                // set mismatch transitions
                for (char c : charSet)
                    trans2NextState.put(new Transition(j, c), transition(x, c));
                
                char input = pattern.charAt(j);
                // set match transition
                trans2NextState.put(new Transition(j, input), j + 1);
                
                // update mismatch state x
                x = transition(x, input);
            }
        }
        
        public int transition(int currState, char c)
        {
            // 1.a simple implementation
//            Integer nextState = trans2NextState.get(new Transition(currState, c));
//            return nextState == null ? 0 : nextState;
            
            // 2.an alternative optimized implementation
            if(!charSet.contains(c)) return 0;
            return trans2NextState.get(new Transition(currState,c));
        }
    }
}
