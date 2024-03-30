package com.decisiontree;

import java.util.Map;

/**
 * @auther: pointer
 * @Date: 2024-03-29-11:22
 * @Description: 用于计算熵的工具类
 */
public class Entropy {
    // 以2为底的对数
    private static double logBase2(double num) {
        if (num == 0) {
            return 0;
        } else {
            return Math.log(num) / Math.log(2);
        }
    }

    //信息熵
    public static double getInformationEntropy(double[] data) {
        double result = 0;

        for (double pi : data) {
            result -= pi * logBase2(pi);
        }

        return result;
    }


    /**
     * @param total 样本总数
     * @param data  true 的值，false 的值
     * @return 条件熵
     */
    private static double getConditionalEntropy(double total, int[] data) {

        // 该属性占全部的概率
        double totalProportion = (double) (data[0] + data[1]) / total;

        // 该属性下，每个标签所占的概率
        double[] entropyData = {(double) data[0] / (data[0] + data[1]), (double) data[1] / (data[0] + data[1])};

        return totalProportion * getInformationEntropy(entropyData);


    }

    /**
     * @param entropy       总的信息熵
     * @param total         样本总数
     * @param data          该条件下的对应的 不同标签对应 true、false 的数量
     * @return 该条件下的信息增益
     */
    public static double getInformationGain(double entropy, double total, Map<String, int[]> data) {
        double conditionalEntropy = 0;
        for (int[] value : data.values()) {
            conditionalEntropy += getConditionalEntropy(total, value);
        }

        return entropy - conditionalEntropy;
    }
}
