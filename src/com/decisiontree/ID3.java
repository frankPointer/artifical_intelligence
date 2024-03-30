package com.decisiontree;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ID3 {

    // 储存所有的属性名
    public ArrayList<String> attributes;
    // 储存不同属性对应的不同标签以及标签对应的 true 和 false 的数量
    public Map<String, Map<String, int[]>> data;

    // 读取的原始数据，以行存在这个数组中
    public ArrayList<String[]> originData;

    // 信息增益
    public Map<String, Double> informationGain;

    double entropy;

    // 真的数量，假的数量，所有的数量
    public double total;
    public double trueNum;
    public double falseNum;

    public ID3() {
        attributes = new ArrayList<>();
        informationGain = new HashMap<>();
        originData = new ArrayList<>();
        data = new HashMap<>();
        entropy = 0;
        total = 0;
        trueNum = 0;
        falseNum = 0;
    }


    public void getTestData(String filePath) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                String[] words = string.split(",");
                originData.add(words);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new IOException();
        }

        // 将测试数据的第一行也就是属性名，全部保存起来
        attributes.addAll(Arrays.asList(originData.get(0)));
        originData.remove(0);
        this.total = originData.size();
    }

    public void getData(String filePath) {
        int i = 0; // 用来标记读到了哪一行
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                String[] words = str.split(",");

                // 一行的数据数
                int size = words.length;

                // 存储标签以及其对应的结果
                Map<String, int[]> label;


                // 对第一行进行的操作
                if (i == 0) {
                    // 保存属性名
                    attributes.addAll(Arrays.asList(words));
                } else {
                    // 对第二行以及之后的数据进行操作

                    for (int k = 0; k < words.length; k++) {
                        String word = words[k];
                        int[] num = {0, 0};

                        // 判断这一行数据的最终结果是true or false
                        boolean flag = words[size - 1].equals(attributes.get(size - 1));

                        if (flag) {
                            trueNum++;
                        } else {
                            falseNum++;
                        }

                        //todo 先判断是否存在，然后再存进去

                        // 先判断这个属性之前是否存过
                        if (data.containsKey(attributes.get(k))) {

                            /*
                            属性存在，拿到属性下的标签map
                             */
                            label = data.get(attributes.get(k));

                            /*
                            因为之前存在，所以需要判断标签是否存在
                            存在的话先得到原来的数组进行加减
                             */
                            if (label.containsKey(word)) {
                                int[] ints = label.get(word);

                                if (flag) {
                                    ints[0]++;
                                } else {
                                    ints[1]++;
                                }

                                label.put(word, ints);
                            } else {
                                if (flag) {
                                    num[0] = 1;
                                } else {
                                    num[1] = 1;
                                }

                                label.put(word, num);
                            }

                            data.put(attributes.get(k), label);
                        } else {

                            label = new HashMap<>();

                            if (flag) {
                                num[0] = 1;
                            } else {
                                num[1] = 1;
                            }

                            label.put(word, num);
                            data.put(attributes.get(k), label);
                        }

                    }

                }

                i++;

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        total = i - 1;
    }

    public void getEntropy() {

        int size = attributes.size();
        trueNum /= size;
        falseNum /= size;
        // 计算该数据集的信息熵
        double[] data = {trueNum / total, falseNum / total};
        entropy = Entropy.getInformationEntropy(data);

        // 计算信息效益
        for (String attribute : this.data.keySet()) {
            Map<String, int[]> stringMap = this.data.get(attribute);

            double informationGain = Entropy.getInformationGain(entropy, this.total, stringMap);

            this.informationGain.put(attribute, informationGain);
        }
    }

    //todo 熵计算没有问题，就差构建决策树

    public static void main(String[] args) {
        ID3 id3 = new ID3();

        String filePath = "resources/test_data.txt";
        id3.getData(filePath);
        id3.getEntropy();
    }
}