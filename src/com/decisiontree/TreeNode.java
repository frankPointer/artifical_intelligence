package com.decisiontree;

import java.util.ArrayList;

/**
 * @auther: pointer
 * @Date: 2024-03-29-11:22
 * @Description: 决策树的节点类
 */
public class TreeNode {
    private String name;

    public TreeNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 与子节点之间的属性值
    ArrayList<String> attributes = new ArrayList<>();

    // 子节点
    ArrayList<TreeNode> childNodes = new ArrayList<>();
}
