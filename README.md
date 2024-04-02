# 决策树

项目调用流程：

1. 给出测试文件路径：filePath
2. 调用 ID3 的构建方法：`public ID3(String filePath)`
   1. 读取文件
   2. 处理文件
   3. 调用 Entropy 类计算数据的熵
3. 使用 `TreeNode.createDecision()` 创建决策树
   1. 根据传入的 id3 创建节点
   2. 创建子节点的时候，使用 ID3 的第二个构造方法：`public ID3(ArrayList<String[]> originData, ArrayList<String> attributes, String attribute, String label)`
      继承其他ID3 的数据，并加一些限制条件


根据测试数据输出结果如下：

```java
public static void main(String[] args) throws IOException {

        String filePath = "resources/test_data.txt";
        ID3 id3 = new ID3(filePath);

        TreeNode decisionTree = TreeNode.createDecisionTree(id3);

        decisionTree.levelOrderTraversal();

        System.out.println("----------------------------");

        decisionTree.levelOrderTraversalAttributes();
    }
```
```
纹理
触感 坏瓜 根蒂
好瓜 坏瓜 坏瓜 好瓜 触感
色泽 好瓜
好瓜 坏瓜
----------------------------
稍糊 模糊 清晰
软粘 硬滑 硬挺 蜷缩 稍蜷
软粘 硬滑
青绿 乌黑
```
图片数据如下
![](https://picgo-1314080015.cos.ap-nanjing.myqcloud.com/PIctures/202403301756268.png)

