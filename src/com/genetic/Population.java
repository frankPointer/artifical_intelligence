package com.genetic;

/**
 * @Date: 2024-04-28-16:55
 * @Description:
 */
public class Population {
    private  Individual[] generation;

    private  double chromosomeLength;
    private   int individualNum;
    private  double crossoverRate;
    private  double mutationRate;

    private double bestFitness;
    private Individual bestIndividual;

    public Population(Individual[] generation) {
        if (generation.length != GAConstants.INDIVIDUAL_NUM) {
            throw new IllegalArgumentException("Individual num expected: " + GAConstants.INDIVIDUAL_NUM);
        }

        this.generation = generation;

        this.chromosomeLength = GAConstants.CHROMOSOME_LENGTH;
        this.crossoverRate = GAConstants.CROSSOVER_RATE;
        this.mutationRate = GAConstants.MUTATION_RATE;
        this.individualNum = GAConstants.INDIVIDUAL_NUM;

        // 计算最佳个体和适应度
        bestFitness = 0.0;
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

