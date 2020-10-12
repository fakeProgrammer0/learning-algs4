# String Algorithms



TODO code

* [x] radix integer sort
* [x] inplace MSD
* [ ] ★suffix arrays sorting
  * [ ] keyword-in-context search
  * [ ] longest repeated substring
* [ ] ...



```java
void kmpPreprocess()
{
    int i = 0, j = -1;
    b[i] = j; // b[0] = -1; // 设置空字符串ε的最长border长度为-1
    while (i < m)
    {
        /*
        String x = pattern.substring(0, i);
        每一轮迭代开始，j=b[i]，j表示长度为i的前缀x的最长border的长度
        
        char a = pattern.chatAt(i);
        case 1: j > 0 && pattern.chatAt(j) == pattern.chatAt(i)
        	找到border v，使得va是xa的最长border
        	|v|=j，|va|=j+1，即设置b[++i]=++j
        case 2: j == 0 && pattern.chatAt(0) == pattern.chatAt(i)
        	a是xa的最长border，此时v=ε
        	|a|=1，即设置b[++i]=1=++j
        case 3: j == -1
        	找不到xa的border，即xa的最长border为ε，长度为0
        	设置b[++i]=0=++j
        */
        while (j >= 0 && pattern.chatAt(i) != pattern.charAt(j)) 
            j = b[j];
        i++; j++;
        b[i] = j;
    }
}
```



