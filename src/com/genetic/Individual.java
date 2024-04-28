package com.genetic;


/**
 * @Date: 2024-04-28-14:26
 * @Description: 遗传算法中，种群中的个体
 */
public class Individual {
    private final int[] chromosome;
    private final double fitness;
    private final int distance;

    public Individual(int[] chromosome) {

        // 当传入的染色体长度不对时候，抛出异常
        if (chromosome.length != GAConstants.CHROMOSOME_LENGTH) {
            throw new IllegalArgumentException("chromosome length expected:" + GAConstants.CHROMOSOME_LENGTH);
        }
        this.chromosome = chromosome;
        this.distance = GAConstants.getDistance(chromosome);
        this.fitness = 1 / (double) distance;
    }

    public int[] getChromosome() {
        return chromosome;
    }




    public double getFitness() {
        return fitness;
    }

    public int getDistance() {
        return distance;
    }
}
