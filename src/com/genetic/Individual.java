package com.genetic;


/**
 * @Date: 2024-04-28-14:26
 * @Description: 遗传算法中，种群中的个体
 */
public class Individual {
    private int[] chromosome;
    private double fitness;
    private double chooseRate;

    public Individual(int[] chromosome) {

        // 当传入的染色体长度不对时候，抛出异常
        if (chromosome.length != GAConstants.CHROMOSOME_LENGTH) {
            throw new IllegalArgumentException("chromosome length expected:" + GAConstants.CHROMOSOME_LENGTH);
        }
        this.chromosome = chromosome;
        this.fitness = GAConstants.getFitness(chromosome);
    }


    public Individual(int[] chromosome, int fitness) {
        this.chromosome = chromosome;
        this.fitness = fitness;
    }


    public int[] getChromosome() {
        return chromosome;
    }


    public void setChromosome(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void setChooseRate(double chooseRate) {
        this.chooseRate = chooseRate;
    }

    public double getChooseRate() {
        return chooseRate;
    }
}
