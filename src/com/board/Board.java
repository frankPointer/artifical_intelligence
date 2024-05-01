package com.board;

/**
 * @Date: 2024-05-01-17:11
 * @Description: 棋盘类，用来存储八数码问题中棋盘的状态
 */
public class Board {
    private int[][] board; // 棋盘状态
    private int invOrdNum; // 逆序对的个数


    public Board(int[][] board) {
        this.board = board;
        this.invOrdNum = 0;
        for (int i = 0; i < 9; i++) {
            if (board[i / 3][i % 3] == 0) {
                continue;
            }
            for (int j = 0; j < i; j++) {
                if (board[j / 3][j % 3] > board[i / 3][i % 3]) {
                    invOrdNum++;
                }
            }
        }
    }
}
