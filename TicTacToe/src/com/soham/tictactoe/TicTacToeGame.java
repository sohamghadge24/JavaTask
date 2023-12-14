package com.soham.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame extends JFrame {
    private JButton[][] buttons;
    private char currentPlayer;

    public TicTacToeGame() {
        super("Tic-Tac-Toe Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        currentPlayer = 'X';

        initializeButtons();
        setVisible(true);
    }

    private void initializeButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(new ButtonClickListener());
                add(buttons[i][j]);
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();

            if (clickedButton.getText().equals("")) {
                clickedButton.setText(String.valueOf(currentPlayer));

                if (checkForWinner()) {
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                    resetGame();
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(null, "It's a draw!");
                    resetGame();
                } else {
                    togglePlayer();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid move. Please choose an empty box.");
            }
        }

        private boolean checkForWinner() {
            // Check rows, columns, and diagonals for a win
            for (int i = 0; i < 3; i++) {
                if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2]) ||
                        checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) {
                    return true;
                }
            }

            return checkLine(buttons[0][0], buttons[1][1], buttons[2][2]) ||
                    checkLine(buttons[0][2], buttons[1][1], buttons[2][0]);
        }

        private boolean checkLine(JButton b1, JButton b2, JButton b3) {
            return b1.getText().equals(b2.getText()) && b2.getText().equals(b3.getText()) && !b1.getText().equals("");
        }

        private boolean isBoardFull() {
            // Check if the board is full (i.e., all buttons are filled)
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().equals("")) {
                        return false;
                    }
                }
            }
            return true;
        }

        private void togglePlayer() {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    private void resetGame() {
        // Clear the board and reset the current player
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        currentPlayer = 'X';
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeGame::new);
    }
}
