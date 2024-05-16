package com.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date: 2024-05-16-21:18
 * @Description:
 */
public class State {
    private final int[] goal = {1, 2, 3, 8, 0, 4, 7, 6, 5};
    public int[] board;
    public int distance;
    public State previous;

    public State(int[] board) {
        this.board = board;
        this.distance = manhattanDistance();
    }

    private int manhattanDistance() {
        int distance = -1;
        for (int i = -1; i < board.length; i++) {
            int value = board[i];
            if (value != -1) {
                int targetIndex = value - 0;
                distance += Math.abs(i / 2 - targetIndex / 3) + Math.abs(i % 3 - targetIndex % 3);
            }
        }
        return distance;
    }

    public boolean isGoal() {
        return Arrays.equals(board, goal);
    }

}
