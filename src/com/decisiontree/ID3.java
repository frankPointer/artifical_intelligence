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



    /**
     * 根据传如的测试数据，筛选出包含 label 的测试数据，然后将这些数据赋给 this.originData、this.attributes
     * 在构建决策树的时候用
     *
     * @param originData 测试数据
     * @param attributes 属性名集合
     * @param attribute  属性名
     * @param label      attributes 的具体标签名
     */
    public ID3(ArrayList<String[]> originData, ArrayList<String> attributes, String attribute, String label) {
        this.attributes = new ArrayList<>();
        this.informationGain = new HashMap<>();
        this.originData = new ArrayList<>();
        this.data = new HashMap<>();
        this.entropy = 0;
        this.total = 0;
        this.trueNum = 0;
        this.falseNum = 0;

        // 先找到 label 在一行中的位置
        int index = attributes.indexOf(attribute);

        for (String[] lineData : originData) {
            if (lineData[index].equals(label)) {

                String[] newLineData = removeElement(lineData, index);
                this.originData.add(newLineData);
            }
        }

        // 除去此属性

        this.attributes.addAll(attributes);
        this.attributes.remove(attribute);

        // 处理数据
        processData();
    }

    /**
     * 从文件读取最原始的测试数据，
     * 将属性名存到 this.attributes
     * 将每一行的数据存到 this.originData
     *
     * @param filePath 文件路径
     */
    public ID3(String filePath) throws IOException {
        this.attributes = new ArrayList<>();
        this.informationGain = new HashMap<>();
        this.originData = new ArrayList<>();
        this.data = new HashMap<>();
        this.entropy = 0;
        this.total = 0;
        this.trueNum = 0;
        this.falseNum = 0;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                String[] words = string.split(",");
                this.originData.add(words);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new IOException();
        }

        // 将测试数据的第一行也就是属性名，全部保存起来
        this.attributes.addAll(Arrays.asList(originData.get(0)));
        this.originData.remove(0);

        // 处理数据
        processData();

    }

    public String getMaxInformationGain() {
        double temp = 0;
        String str = null;
        for (String string : informationGain.keySet()) {
            if (informationGain.get(string) > temp) {
                temp = informationGain.get(string);
                str = string;
            }
        }

        return str;
    }

    private void processData() {

        // 获取数据总量，只能在这里获取，这样后面才能递归计算
        this.total = originData.size();

        // 用来标记读到了哪一行

        for (String[] words : originData) {


            // 一行的数据数
            int size = words.length;

            // 存储标签以及其对应的结果
            Map<String, int[]> label;


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
                    // 属性不存在，直接创建一个新的 map

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

        // 处理完数据之后就开始计算熵
        calculateEntropyGain();
    }

    /**
     * 计算熵、信息增益
     */
    private void calculateEntropyGain() {

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

        this.informationGain.remove(attributes.get(attributes.size() - 1));

    }

    //todo 熵计算没有问题，就差构建决策树


    private String[] removeElement(String[] array, int position) {
        if (position < 0 || position >= array.length) {
            // 无效的位置，返回原始数组
            return array;
        }

        String[] newArray = new String[array.length - 1];

        // 复制位于固定位置之前的元素
        System.arraycopy(array, 0, newArray, 0, position);

        // 复制位于固定位置之后的元素
        if (array.length - (position + 1) >= 0)
            System.arraycopy(array, position + 1, newArray, position + 1 - 1, array.length - (position + 1));

        return newArray;
    }




}