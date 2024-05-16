package com.puzzle;

import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * @Date: 2024-05-16-21:20
 * @Description: 八数码问题的逻辑部分，包含启发式搜索算法，保存移动路径
 * Puzzle 命名的原因是八数码就是一个拼图游戏
 */
public class Puzzle {
    private int[] goal = {1, 2, 3, 8, 0, 4, 7, 6, 5};
    private PriorityQueue<State> queue;

    private HashSet<State> explored; // 保存已经探索过的状态
    private State solution; // 保存最终的解决方案

    public Puzzle(int[] initial) {
        queue = new PriorityQueue<>();
        explored = new HashSet<>();
        queue.add(new State(initial, goal));
    }
}
