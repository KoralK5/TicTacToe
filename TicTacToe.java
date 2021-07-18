package tictactoe;
import java.util.Scanner;
import java.util.Arrays;

/**
 *
 * @author Koral Kulacoglu
 */

public class TicTacToe {
    public static void clear(int size) {
        for (int i=0; i<size; i++) {System.out.println();}
    }
    
    public static void show(char[][] board, String name, int depth, int round, int[] move, int eval) {
        System.out.println(name + " vs AI-" + depth + "\n");
        System.out.println("Move " + round);
        System.out.println("Winning: " + (eval==2 ? " " : (eval==1 ? "X" : "O")));
        System.out.println("Last Played: " + Arrays.toString(new int[] {move[0]+1, move[1]+1}) + "\n");
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static int[] predict(char[][] board, char player, int diff) {
        int tempStatus = check(board);
        if (tempStatus != 0) {
            return new int[] {tempStatus, -1, -1};
        }
        
        int bestX = -1, bestY = -1;
        int result = (player=='X' ? -1 : 1);

        for (int i = 0; i < board.length; i++) {
            for (int a = 0; a < board[i].length; a++) {
                if (board[i][a] != '*')
                    continue;

                board[i][a] = player;
                int tempResult = predict(board, (player == 'X' ? 'O' : 'X'), diff)[0];
                board[i][a] = '*';

                if (((player == 'X') == tempResult > result) || (!(player == 'X') && tempResult < result)) {
                    result = tempResult;
                    bestX = i;
                    bestY = a;
                }
                else if (tempResult == result && Math.random() > diff/9) {
                    result = tempResult;
                    bestX = i;
                    bestY = a;
                }
            }
        }
        return new int[] {result, bestX, bestY};
    }

    public static void play(char[][] board, int[] move, char player) {
        board[move[0]][move[1]] = player;
    }

    public static int check(char[][] board) {
        boolean full = true;
        for (int i=0; i<board.length; i++) {
            for (int y=0; y<board[i].length; y++) {
                try{ if ((board[i][y] == board[i][y-1]) && (board[i][y] == board[i][y+1]) && (board[i][y] != '*')) {
                    return (board[i][y] == 'X' ? 1 : -1);
                }} catch (Exception e){}
                
                try{ if ((board[i][y] == board[i-1][y]) && (board[i][y] == board[i+1][y]) && (board[i][y] != '*')) {
                    return (board[i][y] == 'X' ? 1 : -1);
                }} catch (Exception e){}
                
                try{ if ((board[i][y] == board[i-1][y-1]) && (board[i][y] == board[i+1][y+1]) && (board[i][y] != '*')) {
                    return (board[i][y] == 'X' ? 1 : -1);
                }} catch (Exception e){}
                
                try{ if ((board[i][y] == board[i+1][y-1]) && (board[i][y] == board[i-1][y+1]) && (board[i][y] != '*')) {
                    return (board[i][y] == 'X' ? 1 : -1);
                }} catch (Exception e){}
                
                if (board[i][y] == '*') {
                    full = false;
                }
            } 
        }
        return (full ? 2 : 0);
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
        
        System.out.print("Difficuilty (0-9): ");
        int diff = input.nextInt()%10;
        clear(100);
        
        char[][] board = new char[rows][cols];
        for (char[] row: board) {
            Arrays.fill(row, '*');
        }
        
        int status = 0;
        int round  = 0;
        int eval   = 2;
        int[] move = {-1, -1};
        while (true) {
            round += 1;
            
            clear(100);
            show(board, name, diff, round, move, eval);
            
            System.out.print("\nRow: ");
            move[0] = Math.abs(input.nextInt() - 1)%rows;
            
            System.out.print("Column: ");
            move[1] = Math.abs(input.nextInt() - 1)%cols;

            play(board, move, 'X');
            status = check(board);
            
            if (status != 0) {break;}
            
            int[] preds = predict(board, 'O', diff);
            play(board, new int[] {preds[1], preds[2]}, 'O');
            status = check(board);
            
            eval = preds[0];
            
            if (status != 0) {break;}
        }
        clear(100);
        String winner = (status==2 ? "Nobody" : (status==1 ? name : "Computer"));
        System.out.println((status==2 ? "Tie..." : (status==1 ? "Congratulations!" : "You Lost...")) + "\n");
        System.out.println(winner + " Won!");
    }
}
