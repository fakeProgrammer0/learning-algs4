package com.green.learning.algs4.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.com/problems/unique-binary-search-trees-ii/?tab=Description
 */
public class UniqueBST
{
    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode
    {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode()
        {
        }

        TreeNode(int val)
        {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right)
        {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<TreeNode> generateTrees(int n)
    {
        return generateTrees(1, n);
    }

    private List<TreeNode> generateTrees(int low, int high)
    {
        List<TreeNode> bsts = new LinkedList<>();
        if (low > high)
        {
            bsts.add(null);
        } else
        {
            for (int root_val = low; root_val <= high; root_val++)
            {
                List<TreeNode> leftBSTs = generateTrees(low, root_val - 1);
                List<TreeNode> rightBSTs = generateTrees(root_val + 1, high);

                for (TreeNode leftBST : leftBSTs)
                {
                    for (TreeNode rightBST : rightBSTs)
                    {
                        TreeNode root = new TreeNode(root_val);
                        root.left = leftBST;
                        root.right = rightBST;
                        bsts.add(root);
                    }
                }
            }
        }

        return bsts;
    }

    //    public List<TreeNode> generateTrees(int n)
//    {
//        List<TreeNode> bsts = new LinkedList<>();
//        generateTrees(bsts, null, 1, n);
//        return bsts;
//    }

//    private TreeNode generateTrees(List<TreeNode> bsts, TreeNode curBstRoot, int low, int high)
//    {
//        if (low > high) return null;
//
//        for (int root_val = low; root_val <= high; root_val++)
//        {
//            TreeNode curBst = new TreeNode(root_val);
//            if (curBstRoot == null) curBstRoot = curBst;
//            curBst.left = generateTrees(bsts, curBstRoot, low, root_val - 1);
//            curBst.right = generateTrees(bsts, curBstRoot, root_val + 1, high);
////            bsts.add(deepCopyBST(curBstRoot));
//
//            return curBst;
//        }
//        return null;
//    }
//
//    private TreeNode deepCopyBST(TreeNode srcBst)
//    {
//        if (srcBst == null) return null;
//        TreeNode copyBst = new TreeNode(srcBst.val);
//        copyBst.left = deepCopyBST(srcBst.left);
//        copyBst.right = deepCopyBST(srcBst.right);
//        return copyBst;
//    }

}
