package com.genetic;

/**
 * @Date: 2024-04-28-14:29
 * @Description: 里面是遗传算法会用到的一些常量
 */
public class GAConstants {
    public final static int CHROMOSOME_LENGTH = 8;

    // 种群数量
    public final static int INDIVIDUAL_NUM = 100;
    // 交叉率
    public final static double CROSSOVER_RATE = 0.9;
    // 变异率
    public final static double MUTATION_RATE = 0.05;
    // 锦标赛大小
    public final static int TOURNAMENT_SIZE = 5;
    private final static int[][] DISTANCE_MATRIX = {
            {0, 49, 25, 19, 63, 74, 26, 39},
            {49, 0, 26, 48, 65, 36, 42, 55},
            {25, 26, 0, 26, 21, 24, 78, 49},
            {19, 48, 26, 0, 45, 44, 57, 62},
            {63, 65, 21, 45, 0, 47, 48, 54},
            {74, 36, 24, 44, 47, 0, 47, 65},
            {26, 42, 78, 57, 48, 47, 0, 47},
            {39, 55, 49, 62, 54, 65, 47, 0}
    };

    /**
     *
     * @param chromosome 路径序列
     * @return 距离值
     */
    public static int getDistance(int[] chromosome) {
        int distance = 0;

        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            if (i < CHROMOSOME_LENGTH - 1) {

                distance += DISTANCE_MATRIX[chromosome[i]][chromosome[i+1]];
            } else {

                distance += DISTANCE_MATRIX[chromosome[i]][chromosome[0]];
            }
        }


        return distance;
    }
}
