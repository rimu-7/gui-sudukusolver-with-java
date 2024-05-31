import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SudokuGUI implements ActionListener {
    // 1st page attributes
    JFrame frame;
    JButton solveButton;
    JLabel timeLabel;
    static int[] arrayofvalues = {};
    static int[] arrayofvalues2 = {};
    private int[][] rand_board;
    private int[][] solved_board;

    // Timer attributes
    int elapsed = 0;
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    String seconds_string = String.format("%02d", seconds);
    String minutes_string = String.format("%02d", minutes);
    String hours_string = String.format("%02d", hours);

    Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            elapsed = elapsed + 1000;
            hours = (elapsed / 3600000);
            minutes = (elapsed / 60000);
            seconds = (elapsed / 1000) % 60;
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            hours_string = String.format("%02d", hours);
            timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
        }
    });

    static void createBoard() {
        // Reused board settup code here
    }

    static void reformatBoard() {
        // Reused reformating code
    }

    public void firstwindow(int difficulty) {
        // Creating Randomized Board
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
        SudokuGame createboard = new SudokuGame(board, difficulty);
        // SudokuGUI selfObj = new SudokuGUI();
        createboard.fillBoard();
        createboard.randomize();
        rand_board = createboard.getBoard();

        // Reordering board (by box of 3x3)
        for (int boxy = 0; boxy < 3; boxy++) {
            for (int boxx = 0; boxx < 3; boxx++) {
                for (int i = boxy * 3; i < boxy * 3 + 3; i++) {
                    for (int j = boxx * 3; j < boxx * 3 + 3; j++) {
                        arrayofvalues = Arrays.copyOf(arrayofvalues, arrayofvalues.length + 1);
                        arrayofvalues[arrayofvalues.length - 1] = rand_board[i][j];
                    }
                }
            }
        }

        // Solving Randomized Board
        createboard.solve();
        solved_board = createboard.getBoard(); // solved board

        // Creating the JFrame (establishing window)
        frame = new JFrame();
        frame.setTitle("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits out of application when 'x' is pressed
        frame.setSize(600, 700);
        JPanel main_container = new JPanel();
        main_container.setLayout(new BoxLayout(main_container, BoxLayout.Y_AXIS));

        // Sectioning the window (adding panels)
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(100, 600));
        container.setLayout(new GridLayout(3, 3));

        JPanel parray1[] = new JPanel[9];
        JPanel parray2[] = new JPanel[9];
        for (int i = 0; i < 9; i++) {
            parray1[i] = new JPanel();
            parray1[i].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            parray1[i].setLayout(new GridLayout(3, 3));

            for (int j = 0; j < 9; j++) {
                parray2[j] = new JPanel();
                parray2[j].setBorder(BorderFactory.createLineBorder(Color.black));

                // Replacing 0 with " "
                String check_val = Integer.toString(arrayofvalues[i * 9 + j]);
                if (check_val.equals("0")) {
                    check_val = " ";
                }

                JLabel innerText = new JLabel(check_val);
                innerText.setFont(new Font("American Typewriter", Font.BOLD, 25));
                parray2[j].add(innerText);
                parray1[i].add(parray2[j]);
            }

            container.add(parray1[i]);
        }
        main_container.add(container);

        // Create container to hold timer and button
        JPanel container2 = new JPanel();
        container2.setLayout(new BoxLayout(container2, BoxLayout.X_AXIS));

        // Add timer
        timeLabel = new JLabel();
        timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
        timeLabel.setFont(new Font("American Typewriter", Font.PLAIN, 18));
        container2.add(timeLabel);
        timer.start();

        // Add Solve button
        solveButton = new JButton("Solve Puzzle");
        solveButton.addActionListener(this);
        solveButton.setPreferredSize(new Dimension(40, 60));
        container2.add(solveButton);

        main_container.add(container2);
        frame.add(main_container);
        frame.setVisible(true); // makes frame visible
    }

    public void secondwindow(String ss, String ms, String hs) {
        // Creating the 2nd JFrame
        JFrame frame2 = new JFrame();
        frame2.setTitle("Sudoku Solution");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits out of application when 'x' is pressed
        frame2.setSize(600, 700);

        // Reordering board (by box of 3x3)
        for (int boxy = 0; boxy < 3; boxy++) {
            for (int boxx = 0; boxx < 3; boxx++) {
                for (int i = boxy * 3; i < boxy * 3 + 3; i++) {
                    for (int j = boxx * 3; j < boxx * 3 + 3; j++) {
                        arrayofvalues2 = Arrays.copyOf(arrayofvalues2, arrayofvalues2.length + 1);
                        arrayofvalues2[arrayofvalues2.length - 1] = solved_board[i][j];
                    }
                }
            }
        }
        JPanel main_container = new JPanel();
        main_container.setLayout(new BoxLayout(main_container, BoxLayout.Y_AXIS));

        // Sectioning the window (adding panels)
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(100, 600));
        container.setLayout(new GridLayout(3, 3));

        JPanel parray1[] = new JPanel[9];
        JPanel parray2[] = new JPanel[9];
        for (int i = 0; i < 9; i++) {
            parray1[i] = new JPanel();
            parray1[i].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            parray1[i].setLayout(new GridLayout(3, 3));

            for (int j = 0; j < 9; j++) {
                parray2[j] = new JPanel();
                parray2[j].setBorder(BorderFactory.createLineBorder(Color.black));

                // Replacing 0 with " "
                String check_val = Integer.toString(arrayofvalues2[i * 9 + j]);
                if (check_val.equals("0")) {
                    check_val = " ";
                }

                JLabel innerText = new JLabel(check_val);
                innerText.setFont(new Font("American Typewriter", Font.BOLD, 25));

                // mark as green if part of solution
                if (arrayofvalues[i * 9 + j] == 0) {
                    innerText.setForeground(Color.decode("#0bda51"));
                }

                parray2[j].add(innerText);
                parray1[i].add(parray2[j]);
            }
            container.add(parray1[i]);
        }
        main_container.add(container);

        // Adding final time
        JPanel container2 = new JPanel();
        JLabel final_time = new JLabel(hs + ":" + ms + ":" + ss);
        final_time.setFont(new Font("American Typewriter", Font.PLAIN, 30));
        container2.add(final_time);
        main_container.add(container2);
        frame2.add(main_container);

        frame2.setVisible(true); // make second frame visible
    }

    public static void main(String[] args) {
        SudokuGUI gui = new SudokuGUI();
        Scanner diff = new Scanner(System.in);
        System.out.print("Choose a level [Easy(1), Medium(2), Hard(3)]: ");
        int difficulty = diff.nextInt();
        if (difficulty == 1 || difficulty == 2 || difficulty == 3) {
            gui.firstwindow(difficulty);
        } else {
            System.out.println("\nPlease input 1 of the 3 available options!");
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == solveButton) {
                timer.stop();
                frame.dispose();
                secondwindow(seconds_string, minutes_string, hours_string);
            }
        } catch (Exception ex) {
            System.out.print("Error");
        }
    }

}

// SudokuGUI.java (main)
// SolutionGUI.java (second screen)
// SudokuGame.java
