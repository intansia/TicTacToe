/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

/**
 *
 * @author zxcvbn
 */
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Random;

public class ToeTacTic extends JFrame {

    private final static int ROWS = 3;
    private final static int COLUMNS = 3;
    private JButton button[];
    private char board[][];
    private JPanel buttonsPanel;
    private JButton exitButton;

    public ToeTacTic() {
        this.setTitle("Tic-Tac-Toe Game");
        board = new char[ROWS][COLUMNS];
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(ROWS, COLUMNS, 1, 1));
        exitButton = new JButton("Exit");
        //Action Listener for exit button
        exitButton.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                }
        );
        this.add(exitButton, BorderLayout.SOUTH);

        //create an array of JBut
        button = new JButton[ROWS * COLUMNS];
        for (int i = 0; i < ROWS * COLUMNS; i++) {
            button[i] = new JButton();
            button[i].setFocusPainted(false);
            button[i].setActionCommand(Integer.toString(i));
            button[i].setFont(new Font("Arial", Font.BOLD, 18));
            button[i].setPreferredSize(new Dimension(50, 50));
            button[i].setToolTipText("Click to make you move");
            button[i].addActionListener(new ButtonClickHandler());
            buttonsPanel.add(button[i]);
        }

        this.add(buttonsPanel, BorderLayout.NORTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        reset();
    }

    private class ButtonClickHandler implements ActionListener {

        int i, j;

        public void actionPerformed(ActionEvent ev) {
            JButton but = (JButton) ev.getSource();
            int row = Integer.parseInt(but.getActionCommand()) / ROWS;
            int col = Integer.parseInt(but.getActionCommand()) % COLUMNS;
            //button clicked 2 times
            if (isOccupied(row, col) == true) {
                JOptionPane.showMessageDialog(null, "Already chosen.",
                        "Tic Tac Toe : Error", JOptionPane.ERROR_MESSAGE);

            } else {
                but.setText("X");
                board[row][col] = 'X';
                if (isWinner('X') == true) {
                    JOptionPane.showMessageDialog(null, "YOU WIN!!!!!", "Tic Tac Toe:Winner",
                            JOptionPane.INFORMATION_MESSAGE);
                    reset();
                    return;
                } else {
                    compMove();
                    if (isWinner('O') == true) {
                        JOptionPane.showMessageDialog(null, "COMPUTER WIN!!!!!", "Tic Tac Toe:Winner",
                                JOptionPane.INFORMATION_MESSAGE);
                        reset();
                        return;
                    }
                }

            }
            if (isTie() == true) {
                JOptionPane.showMessageDialog(null, "WE ARE SAME BRO", "Tic Tac Toe:Tie",
                        JOptionPane.INFORMATION_MESSAGE);
                reset();
            }

        }

        private boolean isTie() {
            boolean tie = true;
            //looping all array and check is there an empty element or not
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLUMNS; c++) {
                    //if not same, the game is not tie
                    if (board[r][c] != 'O' && board[r][c] != 'X') {
                        tie = false;
                    }
                }
            }
            return tie;
        }

        private void compMove() {

            //possibility user to win
            if (canWin('O', 'X', ROWS - 1) == true) {
                button[this.i * ROWS + this.j].setText("O");
                board[this.i][this.j] = 'O';
            } //possibility computer to win
            else if (canWin('X', 'O', ROWS - 1) == true) {
                button[this.i * ROWS + this.j].setText("O");
                board[this.i][this.j] = 'O';
            } else {
                for (int r = 0; r < (ROWS * COLUMNS); r++) {
                    if (!isOccupied(r / ROWS, r % COLUMNS) == true) {
                        button[r].setText("O");
                        board[r / ROWS][r & COLUMNS] = 'O';
                        break;
                    }
                }
            }
        }

        private boolean canWin(char player, char rival, int count) {
            byte r, c;
            byte c1, c2, c3, c4;
            byte i1, i2, i3, i4;
            byte j1, j2, j3, j4;

            i1 = i2 = i3 = i4 = -1;
            j1 = j2 = j3 = j4 = -1;
            c1 = c2 = c3 = c4 = 0;

            for (r = 0; r < ROWS; r++) {
                c1 = c2 = 0;
                i1 = i2 = i3 = i4 = -1;
                j1 = j2 = j3 = j4 = -1;
                for (c = 0; c < COLUMNS; c++) {
                    //check horizontal
                    if (board[r][c] == player) {
                        c1++;
                    } else {
                        if (board[r][c] != rival) {
                            i1 = r;
                            j1 = c;
                        }
                    }
                    //check vertical
                    if (board[c][r] == player) {
                        c2++;
                    } else {
                        if (board[c][r] != rival) {
                            i2 = c;
                            j2 = r;
                        }
                    }
                    //check diagonal
                    if (r == c) {
                        if (board[r][c] == player) {
                            c3++;
                        } else {
                            if (board[r][c] != rival) {
                                i3 = r;
                                j3 = c;
                            }
                        }
                    }
                    if ((r + c) == ROWS - 1) {
                        if (board[r][c] == player) {
                            c4++;
                        } else {
                            if (board[r][c] != rival) {
                                i4 = r;
                                j4 = c;
                            }
                        }
                    }

                    //check possibility horizontal to win
                    if (c1 == count && i1 != -1 && j1 != -1) {
                        this.i = i1;
                        this.j = j1;
                        return true;
                    }
                    //check possinbility vertical to win
                    if (c2 == count && i2 != -1 && j2 != -1) {
                        this.i = i2;
                        this.j = j2;
                        return true;
                    }
                    //check possinbility diagonal to win
                    if (c3 == count && i3 != -1 && j3 != -1) {
                        this.i = i3;
                        this.j = j3;
                        return true;
                    }
                    //check possinbility vertical to win
                    if (c4 == count && i4 != -1 && j4 != -1) {
                        this.i = i4;
                        this.j = j4;
                        return true;
                    }
                }

            }
            return false;
        }

        private boolean isOccupied(int r, int c) {
            if(board[r][c] == 'X' || board[r][c] == 'O') {
                return true;
            } else {
                return false;
            }
        }

        private boolean isWinner(char player) {
            byte r, c;
            byte c1, c2, c3, c4;
            c1 = c2 = c3 = c4 = 0;
            for (r = 0; r < ROWS; r++) {
                c1 = c2 = 0;
                for (c = 0; c < COLUMNS; c++) {
                    // check horizontally
                    if (board[r][c] == player) {
                        c1++;
                    }

                    // check vertically
                    if (board[c][r] == player) {
                        c2++;
                    }

                    // check diagonally
                    if ((r + c) == ROWS - 1) {
                        if (board[r][c] == player) {
                            c3++;
                        }
                    }

                    if (r == c) {
                        if (board[r][r] == player) {
                            c4++;
                        }
                    }

                }

                if (c1 == ROWS || c2 == COLUMNS || c3 == ROWS || c4 == ROWS) {
                    return true;
                }
            }
            return false;
        }

    }

    private void reset() {
        board = new char[ROWS][COLUMNS];
        for (int i = 0; i < ROWS * COLUMNS; i++) {
            button[i].setText("");
        }
        int text = JOptionPane.showConfirmDialog(null, "O = Computer\n X = You\n COMPUTER FIRST?", "tICtACtOE",
                JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (text == JOptionPane.YES_OPTION) {
            Random rnd = new Random();
            int rndnumb;
            rndnumb = rnd.nextInt(ROWS * COLUMNS);
            button[rndnumb].setText("O");
            board[rndnumb / ROWS][rndnumb % COLUMNS] = 'O';
        }
    }
}
