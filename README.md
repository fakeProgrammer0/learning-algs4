# Learning Algs4

学习[普林斯顿大学《算法》课程](https://www.coursera.org/learn/algorithms-part1)期间写的一些经典数据结构和算法实现，部分代码参考自 [algs4 官方仓库](https://github.com/kevin-wayne/algs4) 。

## 1. Algs4 课程简介


* 针对本科《数据结构和算法》课程更进一步的“广度”层面上的学习：

  * 数据结构：并查集、Kd树、前缀树等
  * 算法：图论算法、字符串算法（排序、搜索）、压缩算法等
* 代码实现强调API设计、注重算法效率、编程作业中常有大量严格的单元测试

## 2. 本仓库实现的数据结构和算法

> 代码位于 [src/main/java/com/green/learning/algs4](./src/main/java/com/green/learning/algs4) 路径下

| package  | Description                                                  |
| -------- | ------------------------------------------------------------ |
| list     | 数组和链表两种实现：线性表、栈、队列                         |
| sort     | 通用排序算法：快排（二路划分、三路划分）、归并排序、堆排序、插入排序； |
| tree     | 优先队列、索引优先队列（Indexed Priority Queue）             |
| set / st | 集合和符号表（字典）：哈希表（拉链法、线性探测法）、二叉搜索树 |
| string   | 字符串排序：三路快排、基数排序（LSD Radix Sort、MSD Radix Sort）<br/>字符串搜索：KMP、Boyer Moore、Rabin Karp<br/>前缀树：Trie、Ternary Search Trie |
| graph    | 图遍历：DFS、BFS、拓扑排序<br/>查找连通分量：并查集、弱连通分量、强连通分量<br/>最小生成树：Prim、Kruskal<br/>最短路径：Dijkstra、Floyd、Bellman-Ford |

