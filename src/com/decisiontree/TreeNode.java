package com.decisiontree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @auther: pointer
 * @Date: 2024-03-29-11:22
 * @Description: 决策树的节点类
 */
public class TreeNode {
    private String name;

    ArrayList<String> label;
    ArrayList<TreeNode> childNodes;


    public TreeNode(String name) {
        this.name = name;
        this.label = null;
        this.childNodes = null;
    }

    public TreeNode(ID3 id3) {
        this.name = id3.getMaxInformationGain();
        this.label = new ArrayList<>();
        this.childNodes = new ArrayList<>();

        this.label.addAll(id3.data.get(name).keySet());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 创建决策树
     * @param id3 存储决策树数据的类
     * @return 决策树的根节点
     */
    public static TreeNode createDecisionTree(ID3 id3) {

        // 熵为0，就不需要再计算了
        if (id3.entropy == 0) {
            return new TreeNode(id3.originData.get(id3.originData.size() - 1)[id3.attributes.size() - 1]);
        }
        TreeNode treeNode = new TreeNode(id3);

        // 读取节点包含的标签，然后根据标签创建对应的子节点
        for (String label : treeNode.label) {
            ID3 newId3 = new ID3(id3.originData, id3.attributes, treeNode.getName(), label);
            treeNode.childNodes.add(createDecisionTree(newId3));
        }

        return treeNode;
    }

    // 层序遍历
   public void levelOrderTraversal() {

       Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            StringBuilder levelNodes = new StringBuilder();

            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                levelNodes.append(current.name).append(" ");

                if (current.childNodes != null) {
                    for (TreeNode child : current.childNodes) {
                        queue.offer(child);
                    }
                }
            }

            System.out.println(levelNodes.toString().trim());
        }
    }
    // 层序遍历输出属性
    public void levelOrderTraversalAttributes() {

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            StringBuilder levelAttributes = new StringBuilder();

            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();

                if (current.label != null) {
                    for (String attribute : current.label) {
                        levelAttributes.append(attribute).append(" ");
                    }
                }

                if (current.childNodes != null) {
                    for (TreeNode child : current.childNodes) {
                        queue.offer(child);
                    }
                }
            }

            System.out.println(levelAttributes.toString().trim());
        }
    }

}
