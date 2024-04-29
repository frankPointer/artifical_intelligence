package com.genetic;

import java.util.Random;

/**
 * @Date: 2024-04-28-18:49
 * @Description: 用来完成选择变异交叉
 */
public class GA {
    /**
     * 从当前种群生成下一代种群
     *
     * @param population 当前种群
     * @return 下一代种群
     */
    public static Population getNextPopulation(Population population) {
        Individual[] selectedIndividuals = selectParentsByTournament(population);
        Individual[] childIndividuals = performCrossover(selectedIndividuals);
        performMutation(childIndividuals);

        return new Population(childIndividuals);
    }


    /**
     * 使用锦标赛选择法选择父代
     *
     * @param population 当前种群
     * @return 选中的父代
     */
    private static Individual[] selectParentsByTournament(Population population) {
        int tournamentSize = GAConstants.TOURNAMENT_SIZE;
        int individualNum = population.getIndividualNum();

        Individual[] parents = new Individual[individualNum];

        for (int i = 0; i < individualNum; i++) {
            // 随机选择锦标赛的竞争者
            Individual[] competitors = new Individual[tournamentSize];
            for (int j = 0; j < tournamentSize; j++) {
                int randomIndex = (int) (Math.random() * individualNum);
                competitors[j] = population.getGeneration()[randomIndex];
            }

            // 从竞争者中选择适应度最高的个体作为父代
            Individual winner = competitors[0];
            for (int j = 1; j < tournamentSize; j++) {
                if (competitors[j].getFitness() > winner.getFitness()) {
                    winner = competitors[j];
                }
            }

            parents[i] = winner;
        }

        return parents;
    }

    /**
     * 对选中的父代进行交叉操作生成下一代
     *
     * @param parents 选中的父代
     * @return 下一代个体
     */
    private static Individual[] performCrossover(Individual[] parents) {
        int numParents = parents.length;

        Individual[] nextGeneration = new Individual[numParents];

        for (int i = 0; i < numParents; i += 2) {
            Individual parent1 = parents[i];
            Individual parent2 = parents[i + 1];

            // 根据交叉率来判断是否进行交叉
            if (Math.random() < GAConstants.CROSSOVER_RATE) {
                // 交叉生成子代
                Individual child1 = crossoverByOX(parent1, parent2);
                Individual child2 = crossoverByOX(parent2, parent1);

                nextGeneration[i] = child1;
                nextGeneration[i + 1] = child2;
            } else {
                // 不交叉直接复制父代
                nextGeneration[i] = new Individual(parent1.getChromosome());
                nextGeneration[i + 1] = new Individual(parent2.getChromosome());
            }

        }

        return nextGeneration;
    }

    /**
     * 对下一代个体进行变异操作
     *
     * @param individuals 下一代个体
     */
    private static void performMutation(Individual[] individuals) {
        for (Individual individual : individuals) {
            if (Math.random() < GAConstants.MUTATION_RATE) {
                mutation(individual);
            }
        }
    }

    /**
     * 对个体进行基于次序的变异
     *
     * @param individual 要变异的个体
     */
    private static void mutation(Individual individual) {
        int length = individual.getChromosome().length;
        // 生成随机变异点
        Random random = new Random();
        int index1 = random.nextInt(length);
        int index2;
        do {
            index2 = random.nextInt(length);
        } while (index1 == index2);

        // 交换基因
        int temp = individual.getChromosome()[index1];
        individual.getChromosome()[index1] = individual.getChromosome()[index2];
        individual.getChromosome()[index2] = temp;
    }

    /**
     * 使用OX交叉法对两个父代进行交叉操作
     *
     * @param parent1 第一个父代
     * @param parent2 第二个父代
     * @return 子代个体
     */
    private static Individual crossoverByOX(Individual parent1, Individual parent2) {
        int length = parent1.getChromosome().length;

        // 生成随机交叉点，不需要 startIndex != endIndex，二者相等就是单点交叉
        Random random = new Random();
        int startIndex = random.nextInt(length);
        int endIndex = random.nextInt(length);

        // 确保startIndex始终小于endIndex
        if (startIndex > endIndex) {
            int temp = startIndex;
            startIndex = endIndex;
            endIndex = temp;
        }

        int[] childChromosome = new int[length];

        // 将parent1的基因在交叉范围内复制到childChromosome
    System.arraycopy(parent1.getChromosome(), startIndex, childChromosome, startIndex, endIndex - startIndex + 1);

        // 从parent2填充剩余的基因到childChromosome，如果它们尚未在交叉范围内出现
        for (int i = 0, index = 0; i < length; i++) {
            if (index == startIndex) {
                index = endIndex + 1;
            }


            if (!containsGene(childChromosome, parent2.getChromosome(), i, startIndex, endIndex)) {
                childChromosome[index] = parent2.getChromosome()[i];
                index++;
            }
        }

        return new Individual(childChromosome);
    }

    /**
     * 检查父代染色体中的某个基因是否已经存在于子代染色体的指定范围内。
     *
     * @param childChromosome 子代的染色体，表示为整数数组。
     * @param chromosome      父代的染色体，也表示为整数数组。
     * @param index           我们想要在子代染色体中查找的父代染色体中的基因的索引。
     * @param startIndex      子代染色体中我们想要查找基因的范围的起始索引。
     * @param endIndex        子代染色体中我们想要查找基因的范围的结束索引。
     * @return 如果在指定范围内找到了基因，则返回 true，否则返回 false。
     */
    private static boolean containsGene(int[] childChromosome, int[] chromosome, int index, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            if (childChromosome[i] == chromosome[index]) {
                return true;
            }
        }
        return false;
    }
}
