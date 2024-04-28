package com.genetic;

import java.util.Random;

/**
 * @Date: 2024-04-28-18:49
 * @Description: 用来完成选择变异交叉
 */
public class GA {
    public static Population getNextPopulation(Population population) {
        Individual[] selectedIndividuals = selectParentsByTournament(population);
        Individual[] childIndividuals = performCrossover(selectedIndividuals);
        performMutation(childIndividuals);

        return new Population(childIndividuals);
    }



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

    private static Individual[] performCrossover(Individual[] parents) {
        int numParents = parents.length;

        Individual[] nextGeneration = new Individual[numParents * GAConstants.INDIVIDUAL_NUM];

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
    private static void performMutation(Individual[] individuals) {
        for (Individual individual : individuals) {
            if (Math.random() < GAConstants.MUTATION_RATE) {
                mutation(individual);
            }
        }
    }

    /**
     * 使用基于次序的变异来变异
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

    private static Individual crossoverByOX(Individual parent1, Individual parent2) {
        int length = parent1.getChromosome().length;

        // 生成随机交叉点
        Random random = new Random();
        int startIndex = random.nextInt(length);
        int endIndex;
        do {
            endIndex = random.nextInt(length);
        } while (startIndex == endIndex);

        if (startIndex > endIndex) {
            int temp = startIndex;
            startIndex = endIndex;
            endIndex = temp;
        }

        int[] childChromosome = new int[length];

        // 父代1的填充到子代基因中
        for (int i = startIndex; i <= endIndex; i++) {
            childChromosome[i] = parent1.getChromosome()[i];
        }

        // 填充剩下的子代基因位置
        for (int i = 0, index = 0; i < length; i++) {
            // index 用来遍历 childChromosome
            // i 用来遍历 parent2
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

    private static boolean containsGene(int[] childChromosome, int[] chromosome, int index, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            if (childChromosome[i] == chromosome[index]) {
                return false;
            }
        }
        return true;
    }
}
