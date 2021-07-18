package tictactoe;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Koral Kulacoglu
 */

public class TicTacToe {
    public static void clear(int size) {
        for (int i=0; i<size; i++) {System.out.println();}
    }
    
    public static void show(char[][] board, String name, int depth, int round, int[] move) {
        System.out.println(name + " vs AI-" + depth + "\n");
        System.out.println("Round " + round);
        System.out.println("Last Played: " + Arrays.toString(move) + "\n");
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static boolean tie(char[][] board) {
        for (char[] row : board) {
            for (char col : row) {
                if (col == '*') {
                    return false;
                }
            }
        }
        return true;
    }    
    
    public static int predict(char[][] board, char player, int[] move, int depth, int rows, int cols) {
        int tempStatus = check(board, player, move[0], move[1]);
        if (tempStatus != 0 || tie(board)) {
            return tempStatus;
        }

        int result = (player=='X' ? -1 : 1);

        for (int i = 0; i < rows; i++) {
            for (int a = 0; a < cols; a++) {
                if (board[i][a] != '*')
                    continue;
                
                move[0] = i;
                move[1] = a;
                board[i][a] = player;
                
                int tempResult = predict(board, (player == 'X' ? 'O' : 'X'), move, depth, rows, cols);
                
                board[i][a] = '*';
                
                if (((player == 'X') == tempResult > result) || (!(player == 'X') && tempResult < result)) {
                    result = tempResult;
                }
            }
        return result;
        }
    }

    public static void play(char[][] board, int[] move, char player) {
        board[move[0]][move[1]] = player;
    }

    public static int check(char[][] board, char player, int x, int y) {
        int boardLen = board.length;
        int col = 0; int row = 0; int ldiag = 0; int rdiag = 0;
        for (int i=0; i<boardLen; i++) {
            col   += (board[x][i] == player) ? 1 : 0;
            row   += (board[i][y] == player) ? 1 : 0;
            ldiag += (board[i][i] == player) ? 1 : 0;
            rdiag += (board[i][boardLen-i-1] == player) ? 1 : 0;
        }
        return !((row==boardLen) || (col==boardLen) || (ldiag==boardLen) || (rdiag==boardLen)) ? 0 : (player=='X' ? 1 : -1);
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        clear(100);

        System.out.print("Name: ");
        String name = input.nextLine();
        clear(100);

        System.out.print("Board Dimensions - Rows: ");
        int rows = input.nextInt();

        System.out.print("Board Dimensions - Columns: ");
        int cols = input.nextInt();
        clear(100);
        
        System.out.println("The computer can see x moves ahead");
        System.out.print("AI Depth: ");
        int depth = input.nextInt();
        clear(100);
        
        char[][] board = new char[rows][cols];
        for (char[] row: board) {
            Arrays.fill(row, '*');
        }
        
        int status = 0;
        int round  = 0;
        int[] move = {-1, -1};
        while (true) {
            round += 1;
            
            clear(100);
            show(board, name, depth, round, move);
            
            System.out.print("\nRow: ");
            move[0] = Math.abs(input.nextInt() - 1)%rows;
            
            System.out.print("Column: ");
            move[1] = Math.abs(input.nextInt() - 1)%cols;

            play(board, move, 'X');
            status = check(board, 'X', move[0], move[1]);
            
            if (status != 0) {break;}
            
            predict(board, 'O', move, depth, rows, cols);
            play(board, move, 'O');
            status = check(board, 'O', move[0], move[1]);
            
            if (status != 0) {break;}
            
            if (tie(board)) {break;}
        }
        clear(100);
        String winner = (status==0 ? "Nobody" : (status==1 ? name : "Computer"));
        System.out.println(winner + " Wins!");
    }
}
