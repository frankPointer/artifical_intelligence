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

    /**
     * 启发式搜索算法
     * 1. 从优先队列中取出一个状态
     * 2. 判断是否为目标状态，是则结束
     * 3. 否则生成下一个状态，如果没有探索过则加入队列
     */
    public void solve() {
        while (!queue.isEmpty()) {
            State current = queue.poll();
            explored.add(current);
            if (current.isGoal()) {
                solution = current;
                break;
            }
            for(State next : current.generateNextStates()) {
                if(!explored.contains(next)) {
                    queue.add(next);
                }
            }
        }
    }


}
