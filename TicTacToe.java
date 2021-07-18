package tictactoe;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Koral Kulacoglu
 */
public class TicTacToe {
    public static void clear(int size) {
        for (int i = 0; i < size; i++) {System.out.println();}
    }
    
    public static void show(char[][] board, String name, int depth, int round, int[] move) {
        System.out.println(name + " vs AI-" + depth + "\n");
        System.out.println("Round " + round);
        System.out.println("Last Played: " + Arrays.toString(move) + "\n");
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }
    
    public static void play(char[][] board, int[] move, char player) {
        board[move[0]][move[1]] = player;
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        clear(100);

        System.out.print("Name: ");
        String name = input.nextLine();
        clear(100);

        System.out.print("Board Dimensions - Rows: ");
        int rows = input.nextInt();
        clear(100);

        System.out.print("Board Dimensions - Columns: ");
        int cols = input.nextInt();
        clear(100);
        
        System.out.print("The computer can see n moves ahead:");
        System.out.print("AI Depth: ");
        int depth = input.nextInt();
        clear(100);
        
        char[][] board = new char[rows][cols];
        for (char[] row: board)
            Arrays.fill(row, ' ');
        
        int status = 0;
        int round = 0;
        int[] move = {-1, -1};
        while (status == 0) {
            round += 1;
            
            clear(100);
            show(board, name, depth, round, move);
            
            System.out.print("Row: ");
            move[0] = input.nextInt()%rows;
          
            System.out.println();
            
            System.out.print("Column: ");
            move[1] = input.nextInt()%cols;

            play(board, move, 'X');
        }
    }
}
