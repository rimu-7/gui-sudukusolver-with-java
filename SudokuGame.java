import java.lang.*;
import java.util.Scanner;
import java.util.Random;

public class SudokuGame {
    private int[][] board;
    private int numRemove;
    private int[] numList = new int[9];

    public SudokuGame(int[][] sudokuBoard, int level) {
        board = sudokuBoard;

        // determine how many values to remove
        if (level == 1) {
            numRemove = 25;
        } else if (level == 2) {
            numRemove = 40;
        } else if (level == 3) {
            numRemove = 81 - 17;
        } else {
            numRemove = 25;
        }

        for (int i = 0; i < 9; i++) {
            numList[i] = i + 1;
        }
    }

    // getter method for board
    public int[][] getBoard() {
        return board;
    }

    // shuffled set board
    public void shuffle() {
        for (int i = 0; i < 9; i++) {
            int rand = (int) ((Math.random() * (10 - 1)) + 1);
            numList[i] = rand;
        }
    }

    public boolean checkBoard() {
        // Check if board is full
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean fillBoard() {
        // populate entire random solved board
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == 0) {
                    shuffle();
                    for (int i : numList) {
                        board[row][column] = i;
                        if (isValid(i, row, column)) {
                            if (checkBoard()) {
                                return true;
                            } else if (fillBoard()) {
                                return true;
                            }
                        }
                        board[row][column] = 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public void randomize() {
        // remove random values in the board based on numRemove
        for (int i = 0; i < numRemove; i++) {
            boolean val = true;
            int rr = 0;
            int rc = 0;
            while (val) {
                rr = (int) (Math.random() * 9);
                rc = (int) (Math.random() * 9);

                if (board[rr][rc] != 0) {
                    val = false;
                }
            }
            board[rr][rc] = 0;
        }
    }

    public boolean solve() {
        // Backtracking Algorithm
        // 1. Choose Empty
        // 2. Try all number 1-9
        // 3. Check validity after all each # and if not backtrack
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == 0) {
                    for (int i = 1; i <= 9; i++) {
                        board[row][column] = i;
                        if (isValid(i, row, column) && solve()) {
                            return true;
                        }
                        board[row][column] = 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean solveFull() {
        // WRONG IDEA: Produces non-variable board that has a pattern
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == 0) {
                    for (int i : numList) {
                        board[row][column] = i;
                        if (isValid(i, row, column) && solve()) {
                            return true;
                        }
                        board[row][column] = 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValid(int num, int row, int column) {
        // check row
        for (int i = 0; i < board[0].length; i++) {
            if (board[row][i] == num && column != i) {
                return false;
            }
        }
        // check column
        for (int i = 0; i < board.length; i++) {
            if (board[i][column] == num && row != i) {
                return false;
            }
        }
        // check subsection (possible solutions: 0, 1, 2)
        int box_x = (int) Math.floor(column / 3);
        int box_y = (int) Math.floor(row / 3);

        for (int i = box_y * 3; i < box_y * 3 + 3; i++) {
            for (int j = box_x * 3; j < box_x * 3 + 3; j++) {
                if (board[i][j] == num && row != i && column != j) {
                    return false;
                }
            }
        }
        return true;

    }

    public void printBoard() {
        for (int row = 0; row < board.length; row++) {
            if ((row % 3) == 0 && row != 0) {
                System.out.println(" - - - - - - - - - - - - - - - -");
            }
            for (int column = 0; column < board.length; column++) {
                if ((column % 3) == 0 && column != 0) {
                    System.out.print(" | ");
                }
                if (board[row][column] != 0) {
                    System.out.print(" " + board[row][column] + " ");

                } else {
                    System.out.print(" X ");
                }

            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {
            int[][] board = {
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
            };

            Scanner diff = new Scanner(System.in);
            System.out.println("Welcome to Sudoku Puzzle Generator & Solver!");
            System.out.print("Choose a level [Easy(1), Medium(2), Hard(3)]: ");
            int difficulty = diff.nextInt();
            diff.nextLine();
            if (difficulty == 1 || difficulty == 2 || difficulty == 3) {
                System.out.println("___________Sudoku Board____________");
                SudokuGame sudokuObj = new SudokuGame(board, difficulty);
                sudokuObj.fillBoard();
                sudokuObj.randomize();
                sudokuObj.printBoard();
                sudokuObj.solve();

                System.out.println("Instructions: to view solution type \'yes\'");
                String response = diff.nextLine();

                if (response.toLowerCase().equals("yes")) {
                    System.out.println("___________Solution___________");
                    sudokuObj.printBoard();
                }
            } else {
                System.out.println("Please input valid values!");
            }
        } catch (Exception e) {
            System.out.println("Looks like there was an error. Please try again.");
            System.out.println(e);
        }

    }
}
