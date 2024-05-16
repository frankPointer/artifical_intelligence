package com.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date: 2024-05-16-21:18
 * @Description: 计算状态的启发式距离以及生成下一个状态
 */
public class State {
    private final int[] goal;
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

    public List<State> generateNextStates() {
        List<State> nextStates = new ArrayList<>();
        int[] directions = {-1, 1, -3, 3};  // 上，下，左，右
        int zeroPos = 0;

        // 寻找空白格子（0）的位置
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                zeroPos = i;
                break;
            }
        }

        // 尝试将空白格子上下左右移动
        for (int direction : directions) {
            int newZeroPos = zeroPos + direction;
            if (newZeroPos >= 0 && newZeroPos < 9 && !(zeroPos == 2 && newZeroPos == 3) && !(zeroPos == 3 && newZeroPos == 2) && !(zeroPos == 5 && newZeroPos == 6) && !(zeroPos == 6 && newZeroPos == 5)) {
                int[] newBoard = Arrays.copyOf(board, board.length);
                swap(newBoard, zeroPos, newZeroPos);
                State newState = new State(newBoard,goal);
                newState.previous = this;
                nextStates.add(newState);
            }
        }

        return nextStates;
    }

    private void swap(int[] board, int i, int j) {
        int temp = board[i];
        board[i] = board[j];
        board[j] = temp;
    }
}
