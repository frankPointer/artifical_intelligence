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

# 遗传算法

**找到一个路径，从某个城市出发经过所有城市后回到这个城市的所走的路最小**

交叉算子：指导书的是错的，具体方法看这个 https://www.keyangou.com/topic/1143

交叉率的应用：

- 交叉率不能用来减少种群，如果每次下一代都是 上一代×交叉率，那最后可能都没个体了
- 交叉率就是对于两个个体，生成个随机概率，随机概率小于交叉率就交叉

变异率：对于每一个个体，生成随机概率，随机概率小于变异率就进行变异

---

类说明：

- `GAConstants`：
  遗传算法里面用的一些常数
  一个根据染色体序列计算距离的方法
- `Individual` 个体类，有属性：染色体序列、适应度、距离
- `Population` 类，属性：个体数组，最佳个体，个体数量
- `GA`类：
  1. `getNextPopulation` 获取下一代的总方法
  2. `getNextPopulation` 调用`selectParentsByTournament`完成 选择复制
  3. 选择复制 的值传给 `performMutation` 进行交叉
     1. 调用 `crossoverByOX` 进行交叉
  4. 交叉完成之后通过方法 `performMutation`来变异
     1. 使用 `mutation` 来对单个个体的染色体进行变异
  5. 变异完成之后生成一个新的 `Population` 返回

Main 测试类
```java
import com.genetic.GA;
import com.genetic.GAConstants;
import com.genetic.Individual;
import com.genetic.Population;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // 创建初始种群
        Individual[] initialGeneration = generateInitialPopulation();
        Population population = new Population(initialGeneration);

        // 迭代进化
        for (int generationCount = 1; generationCount <= 100; generationCount++) {
            System.out.println("Generation: " + generationCount);
            System.out.println("Best Individual: " + Arrays.toString(population.getBestIndividual().getChromosome()));
            System.out.println("Best Fitness: " + population.getBestIndividual().getFitness());
            System.out.println("Best Distance: "+ population.getBestIndividual().getDistance());
            System.out.println("--------------------------------");

            population = GA.getNextPopulation(population);
        }
    }

    // 生成初始种群
    private static Individual[] generateInitialPopulation() {
        Individual[] population = new Individual[GAConstants.INDIVIDUAL_NUM];

        for (int i = 0; i < GAConstants.INDIVIDUAL_NUM; i++) {
            int[] chromosome = generateRandomChromosome();
            population[i] = new Individual(chromosome);
        }

        return population;
    }

    // 生成随机染色体
    private static int[] generateRandomChromosome() {
        int[] chromosome = new int[GAConstants.CHROMOSOME_LENGTH];

        for (int i = 0; i < GAConstants.CHROMOSOME_LENGTH; i++) {
            chromosome[i] = i;
        }

        // 随机打乱染色体顺序
        for (int i = 0; i < GAConstants.CHROMOSOME_LENGTH; i++) {
            int randomIndex = (int) (Math.random() * GAConstants.CHROMOSOME_LENGTH);
            int temp = chromosome[i];
            chromosome[i] = chromosome[randomIndex];
            chromosome[randomIndex] = temp;
        }

        return chromosome;
    }
}
```

# 八数码问题

规划：

棋盘状态使用一位数组来存储

一个State类：

- 属性
  - 棋盘状态
  - 距离
  - 目标状态
- 方法
  - 判断当前状态是不是目标状态
  - 计算距离的方法
  - 计算从当前状态出发能到达的状态

Puzzle类：实现逻辑部分