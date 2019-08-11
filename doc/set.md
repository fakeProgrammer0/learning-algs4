# Set


Q：链式哈希 `XLinkedHashSetX` ，为什么每个桶的链表采用 `Node` 实现，而不是直接用一个实现了的链表结构 `XLinkedList` 呢？

A：节省每个链表的 `size` 和 `tail` 引用的开销，以及链表数组每个entry的 `list` 引用开销

`add` 和 `remove` 接口的返回值处理：
* `add` 和 `remove` 没有返回值（void）：保持接口的简单设计
* `add` 和 `remove` 返回 `boolean` ：一次API调用，可获得更多信息，更节省计算开销


