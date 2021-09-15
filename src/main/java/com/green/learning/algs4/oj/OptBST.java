package com.green.learning.algs4.oj;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OptBST
{
    /**
     * @param A sorted array, the probabilities of searching a specific value stored in the BST
     * @param B sorted array, the probabilities of searching a specific interval which contains values not stored in the BST
     * @param n the number of BST nodes
     * @return the average search length of the optimal BST
     */
    public static int optBSTAvgSearchLength(double[] A, double[] B, int n)
    {
        int[][] M = new int[n + 2][n + 2];
        double[] W = new double[n + 1];
        W[0] = B[0];
        for (int i = 1; i <= n; i++)
        {
            W[i] = W[i - 1] + A[i] + B[i];
        }

        for (int r = 1; r <= n; r++)
        {
            for (int i = 1, j = i + r - 1; j <= n; i++, j++)
            {
                M[i][j] = M[i][i - 1] + M[i + 1][j];
                for (int k = i + 1; k <= j; k++)
                {
                    M[i][j] = Math.min(M[i][k - 1] + M[k + 1][j], M[i][j]);
                }
                M[i][j] += W[j] - W[i - 1];
            }
        }
        return M[1][n];
    }

    public static class TreeNode
    {
        private int val;
        private TreeNode left, right;

        public TreeNode(int val)
        {
            this.val = val;
        }

        /**
         * print a binary tree by layer traversal
         *
         * @param root the root of the binary tree
         */
        public static void printTree(TreeNode root)
        {
            List<Integer> vals = new ArrayList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            int layerNodeNum = 1;
            TreeNode lastNode = root;
            while (!queue.isEmpty())
            {
                TreeNode cur = queue.poll();
                if (cur == null)
                {
                    System.out.print("( ) ");
                    continue;
                }

                System.out.printf("(%d) ", cur.val);
                if (cur == lastNode)
                    System.out.println();

                if (cur.left != null)
                {
                    queue.add(cur.left);
                    lastNode = cur.left;
                }
                if (cur.right != null)
                {
                    vals.add(cur.right.val);
                    queue.add(cur.right);
                } else vals.add(null);
            }

            for (int i = 0; i < vals.size(); i++)
            {

            }
        }
    }
}
