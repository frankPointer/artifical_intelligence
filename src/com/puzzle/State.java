package com.puzzle;

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
}
