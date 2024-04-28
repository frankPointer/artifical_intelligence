import com.genetic.Individual;

import java.io.IOException;

/**
 * @Date: 2024-03-29-11:57
 * @Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] test = {0, 1, 2, 3, 4, 5, 6};
        Individual individual = new Individual(test);
        System.out.println(individual.getFitness());
    }
}
