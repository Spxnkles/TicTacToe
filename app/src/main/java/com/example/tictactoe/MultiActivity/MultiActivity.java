package com.example.tictactoe.MultiActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tictactoe.R;

import com.example.tictactoe.Player;

public class MultiActivity extends AppCompatActivity {
    public Player playerTurn;
    public Button[][] gameBoard = new Button[3][3];
    public int roundCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Initialization
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);


//        Multiplayer game initialization
        gameBoard[0][0] = findViewById(R.id.topLeft);
        gameBoard[0][1] = findViewById(R.id.top);
        gameBoard[0][2] = findViewById(R.id.topRight);
        gameBoard[1][0] = findViewById(R.id.left);
        gameBoard[1][1] = findViewById(R.id.middle);
        gameBoard[1][2] = findViewById(R.id.right);
        gameBoard[2][0] = findViewById(R.id.bottomLeft);
        gameBoard[2][1] = findViewById(R.id.bottom);
        gameBoard[2][2] = findViewById(R.id.bottomRight);

        playerTurn = Player.X;
        roundCount = 0;
    }

    //    button listener for click on game button
    public void gameBoardClick(View view){
        Button button = (Button) view;

        if (playerTurn == Player.NULL){
            return;
        }

        if (!button.getText().toString().isEmpty()){
            return;
        }

        roundCount = roundCount + 1;
        button.setText(playerTurn.toString());

        if (!checkWin()) switchTurns();
    }

    //    Function that switches the turns after someone clicked an empty spot
    public void switchTurns(){
        TextView label = findViewById(R.id.label);

        if (roundCount == 9){
            label.setText("Draw!");
            return;
        }

        if(playerTurn == Player.X){
            playerTurn = Player.O;
            label.setText("Player O Turn");
        }
        else {
            playerTurn = Player.X;
            label.setText("Player X Turn");
        }
    }

    //    Function for restarting the game
    public void restartGame(View view){
        for (Button[] buttons : gameBoard) {
            for (Button button : buttons) {
                button.setText("");
            }
        }

        TextView label = findViewById(R.id.label);

        playerTurn = Player.X;
        label.setText("Player X Turn");
        roundCount = 0;
    }

    //    Function that checks if someone has won
    public boolean checkWin(){
        if (roundCount < 5) return false;

//        ROW CHECKING
        for (Button[] row : gameBoard){
            String square1 = row[0].getText().toString();
            String square2 = row[1].getText().toString();
            String square3 = row[2].getText().toString();
            Log.d("RowCheck", square1 + square2 + square3);

            if (square1.isEmpty() || square2.isEmpty() || square3.isEmpty()) continue;

            if (square1.equals(square2) && square2.equals(square3)){
                Log.d("WinCheck", "Winner found!");
                winner(Player.valueOf(square1));
                return true;
            }
        }

//        COLUMN CHECKING
        for (int i = 0; i < 3; i++){
            String square1 = gameBoard[0][i].getText().toString();
            String square2 = gameBoard[1][i].getText().toString();
            String square3 = gameBoard[2][i].getText().toString();

            Log.d("ColumnCheck", square1);
            Log.d("ColumnCheck", square2);
            Log.d("ColumnCheck", square3);

            if (square1.isEmpty() || square2.isEmpty() || square3.isEmpty()) continue;

            if (square1.equals(square2) && square2.equals(square3)){
                Log.d("WinCheck", "Winner found!");
                winner(Player.valueOf(square1));
                return true;
            }
        }

//        DIAGONAL CHECKING
        String left1 = gameBoard[0][0].getText().toString();
        String left2 = gameBoard[1][1].getText().toString();
        String left3 = gameBoard[2][2].getText().toString();

        if (left1.equals(left2) && left2.equals(left3)){
            Log.d("WinCheck", "Winner found!");
            winner(Player.valueOf(left1));
            return true;
        }

        String right1 = gameBoard[0][2].getText().toString();
        String right2 = gameBoard[1][1].getText().toString();
        String right3 = gameBoard[2][0].getText().toString();

        if (right1.equals(right2) && right2.equals(right3)){
            Log.d("WinCheck", "Winner found!");
            winner(Player.valueOf(right1));
            return true;
        }

        return false;
    }

    //    Function that announces the winner
    public void winner(Player player){
        if (playerTurn == Player.NULL) return;
        playerTurn = Player.NULL;

        TextView label = findViewById(R.id.label);
        String winText = "Player " + player.toString() + " Wins!";
        label.setText(winText);
    }
    //    Function to return to menu
    public void menuReturn(View view){
        this.finish();
    }
}
