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
     * 计算染色体（路径序列）的总距离。
     *
     * @param chromosome 表示路径序列的整数数组，其中每个元素代表一个城市的索引。
     * @return 返回路径序列的总距离。
     */
    public static int getDistance(int[] chromosome) {
        int distance = 0;
        int chromosomeLength = chromosome.length;

        for (int i = 0; i < chromosomeLength; i++) {
            // 如果当前索引是染色体的最后一个元素，那么下一个索引应该是0，否则就是当前索引加1
            int nextIndex = (i == chromosomeLength - 1) ? 0 : i + 1;
            distance += DISTANCE_MATRIX[chromosome[i]][chromosome[nextIndex]];
        }

        return distance;
    }
}
