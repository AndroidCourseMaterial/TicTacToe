package edu.rosehulman.tictactoe;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class TicTacToeGame {
    private enum GameState {
        X_TURN, O_TURN, X_WIN, O_WIN, TIE_GAME
    }

    private GameState gameState;

    private int[][] boardArray;
    private Context context;

    public static final int NUM_ROWS = 3;
    public static final int NUM_COLUMNS = 3;

    private static final int MARK_NONE = 0;
    private static final int MARK_X = 1;
    private static final int MARK_O = 2;

    public TicTacToeGame(Context context) {
        this.context = context;
        resetGame();
    }

    public void resetGame() {
        this.boardArray = new int[NUM_ROWS][NUM_COLUMNS];
        this.gameState = GameState.X_TURN;
    }

    public void pressedButtonAtLocation(int row, int column) {
        if (row < 0 || row >= NUM_ROWS || column < 0 || column >= NUM_COLUMNS)
            return; // Not a valid square location
        if (this.boardArray[row][column] != MARK_NONE)
            return; // Not empty

        if (this.gameState == GameState.X_TURN) {
            this.boardArray[row][column] = MARK_X;
            this.gameState = GameState.O_TURN;
        } else if (this.gameState == GameState.O_TURN) {
            this.boardArray[row][column] = MARK_O;
            this.gameState = GameState.X_TURN;
        }
        checkForWin();
    }

    private void checkForWin() {
        if (!(this.gameState == GameState.X_TURN || this.gameState == GameState.O_TURN))
            return;

        if (didPieceWin(MARK_X)) {
            this.gameState = GameState.X_WIN;
        } else if (didPieceWin(MARK_O)) {
            this.gameState = GameState.O_WIN;
        } else if (isBoardFull()) {

            Log.d("TicTacToeGame", "The pattern is full!");
            this.gameState = GameState.TIE_GAME;
        }
    }

    private boolean isBoardFull() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                if (this.boardArray[row][col] == MARK_NONE) {
                    Log.d("TicTacToeGame", "Empty at Row: " + row + " Col: " + col);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean didPieceWin(int markType) {
        boolean allMarksMatch = true;
        // Check all the columns for a win
        for (int col = 0; col < NUM_COLUMNS; col++) {
            allMarksMatch = true;
            for (int row = 0; row < NUM_ROWS; row++) {
                if (this.boardArray[row][col] != markType) {
                    allMarksMatch = false;
                    break;
                }
            }
            if (allMarksMatch)
                return true;
        }

        // Check all the rows for a win
        for (int row = 0; row < NUM_ROWS; row++) {
            allMarksMatch = true;
            for (int col = 0; col < NUM_COLUMNS; col++) {
                if (this.boardArray[row][col] != markType) {
                    allMarksMatch = false;
                    break;
                }
            }
            if (allMarksMatch)
                return true;
        }

        // Check down right diagonal
        if (this.boardArray[0][0] == markType && this.boardArray[1][1] == markType
                && this.boardArray[2][2] == markType)
            return true;

        // Check up right diagonal
        if (this.boardArray[2][0] == markType && this.boardArray[1][1] == markType
                && this.boardArray[0][2] == markType)
            return true;

        return false;
    }

    public String stringForButtonAtLocation(int row, int column) {
        String label = "";
        if (row >= 0 && row < NUM_ROWS && column >= 0 && column < NUM_COLUMNS) {
            if (this.boardArray[row][column] == MARK_X) {
                return "X";
            } else if (this.boardArray[row][column] == MARK_O) {
                return "O";
            }
        }
        return label;
    }

    public String stringForGameState() {
        String gameStateLabel = "";
        Resources r = this.context.getResources();
        switch (this.gameState) {
        case X_TURN:
            gameStateLabel = r.getString(R.string.x_turn);
            break;
        case O_TURN:
            gameStateLabel = r.getString(R.string.o_turn);
            break;
        case X_WIN:
            gameStateLabel = r.getString(R.string.x_win);
            break;
        case O_WIN:
            gameStateLabel = r.getString(R.string.o_win);
            break;
        default:
            gameStateLabel = r.getString(R.string.tie_game);
            break;
        }
        return gameStateLabel;
    }
}
