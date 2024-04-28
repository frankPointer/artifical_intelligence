package com.genetic;

/**
 * @Date: 2024-04-28-16:55
 * @Description: 种群类
 */
public class Population {
    private final Individual[] generation;

    private final int individualNum;


    private Individual bestIndividual;

    public Population(Individual[] generation) {

        this.generation = generation;

        this.individualNum = generation.length;

        // 计算最佳个体和适应度
        double bestFitness = 0.0;
        for (Individual individual : generation) {
            if (individual.getFitness() > bestFitness) {
                bestFitness = individual.getFitness();
                bestIndividual = individual;
            }
        }
    }

    public Individual getBestIndividual() {
        return bestIndividual;
    }

    public int getIndividualNum() {
        return individualNum;
    }

    public Individual[] getGeneration() {
        return generation;
    }
}

