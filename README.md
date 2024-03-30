# 决策树
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

