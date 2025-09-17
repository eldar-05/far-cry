import java.util.Scanner;
public class TickTackToe {
    public static void run() {
        System.out.println("Do you want to play TickTackToe? 1 - yes, 0 - no");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        while(choice.equals("1") || choice.equalsIgnoreCase("yes")) {
            char[][] board = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
            };
            char currentPlayer = 'X';
            boolean gameWon = false;
            for (int turn = 0; turn < 9 && !gameWon; turn++) {
                printBoard(board);
                System.out.println("Player " + currentPlayer + ", enter your move (row and column): ");
                int row = sc.nextInt() - 1;
                int col = sc.nextInt() - 1;
                if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != ' ') {
                    System.out.println("Invalid move. Try again.");
                    turn--;
                    continue;
                }
                board[row][col] = currentPlayer;
                gameWon = checkWin(board, currentPlayer);
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
            printBoard(board);    
            if (gameWon) {
                System.out.println("Player " + ((currentPlayer == 'X') ? 'O' : 'X') + " wins!");
            } else {
                System.out.println("It's a draw!");
            }
            System.out.println("Do you want to play again? 1 - yes, 0 - no");
            sc.nextLine();
            choice = sc.nextLine();
        }
        
    }

    private static void printBoard(char[][] board) {
        for(int i = 0; i < 20; i++) {
            System.out.println("                                                                                                ");
        }
        for(int i = 0; i < 3; i++) {
            System.out.println(" " + board[i][0] + " | " + board[i][1] + " | " + board[i][2]);
            if (i < 2) {
                System.out.println("---|---|---");
            }
        }
    }

    private static boolean checkWin(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }
}
