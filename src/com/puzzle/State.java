package com.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date: 2024-05-16-21:18
 * @Description:
 */
public class State {
    private final int[] goal  ;
    public int[] board;
    public int distance;

    // Puzzle 里的 explored 是乱序的，不知道移动顺序，需要 previous 来保存移动路径
    public State previous;

    public State(int[] board, int[] goal) {
        this.board = board;
        this.goal = goal;
        this.distance = manhattanDistance();
    }

    public int manhattanDistance() {
        int distance = 0;
        for (int i = 0; i < board.length; i++) {
            int value = board[i];
            if (value != 0) {
                int targetIndex = value - 1;
                distance += Math.abs(i / 3 - targetIndex / 3) + Math.abs(i % 3 - targetIndex % 3);
            }
        }
        return distance;
    }

    public boolean isGoal() {
        return Arrays.equals(board, goal);
    }

}
