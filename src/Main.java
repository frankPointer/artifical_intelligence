import com.genetic.GA;
import com.genetic.GAConstants;
import com.genetic.Individual;
import com.genetic.Population;

import java.util.Arrays;

/**
 * @Date: 2024-03-29-11:57
 * @Description:
 */
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
