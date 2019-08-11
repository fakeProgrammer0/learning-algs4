# ST

ArrayHashST

为了维持寻址的连续性，必须有墓碑值存在。

（泛型）墓碑值的设置？3个方案:
1. (key, value) = (k, null) 允许null作为key
2. (key, value) = (null, v) 比其他两个方案差
3. (key, value) = (null, null) 有利于GC

首先，存入的value是不允许设置为null的

put(k, null) throws IllegalArgumentException






