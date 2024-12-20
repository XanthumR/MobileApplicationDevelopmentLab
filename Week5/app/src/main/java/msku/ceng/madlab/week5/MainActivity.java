package msku.ceng.madlab.week5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    static final String PLAYER_1="X";
    static final String PLAYER_2="O";
    boolean player1Turn=true;
    byte [][] board=new byte[3][3];
    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        table =findViewById(R.id.board);
        for(int i=0;i<3 ; i++){
            TableRow row=(TableRow) table.getChildAt(i);
            for (int j=0;j<3;j++)
            {
                Button button = (Button)row.getChildAt(j);
                button.setOnClickListener(new CellListener(i,j));
            }
        }

    }

    class CellListener implements View.OnClickListener{
        int col,row;
        public CellListener(int row,int col){
            this.col=col;
            this.row=row;
        }
        @Override
        public void onClick(View view) {

            if (isValisMove(row,col)){
                if (player1Turn){
                    ((Button)view).setText(PLAYER_1);
                    board[row][col]=1;

                }
                else {
                    ((Button)view).setText(PLAYER_2);
                    board[row][col]=2;

                }
            }
            else {
                Toast.makeText(MainActivity.this,"Cell is already occupied",
                        Toast.LENGTH_LONG).show();
            }
            if (gameEnded(row,col)==-1){
                player1Turn=! player1Turn;
            } else if (gameEnded(row,col)==0) {
                Toast.makeText(MainActivity.this,"It is a Draw",Toast.LENGTH_SHORT).show();
            }
            else if (gameEnded(row,col)==1){
                Toast.makeText(MainActivity.this,"Player 1 wins",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this,"Player 2 wins",Toast.LENGTH_SHORT).show();
            }
        }
        public int gameEnded(int row,int col){
            int symbol=board[row][col];
            boolean win = true;
            boolean win2 = true;
            boolean win3 = true;
            boolean win4 = true;
            for (int i=0;i<3;i++){
                if (board[i][col]!=symbol){
                    win=false;
                }
                if (board[row][i]!=symbol){
                    win2 =false;
                }
                if (board[i][i]!=symbol){
                    win3 =false;
                }
                if (board[2-i][i]!=symbol){
                    win4 =false;
                }
            }

            if (win||win2||win3||win4){
                return symbol;
            }
            return -1;
        }




        public boolean isValisMove(int row,int col){
            return board[row][col]==0;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("player1Turn",player1Turn);
        byte [] boardSingle=new byte[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardSingle[3*i+j]= board[i][j];
            }
        }

        outState.putByteArray("board",boardSingle);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        player1Turn=savedInstanceState.getBoolean("player1Turn");
        byte[] boardSingle=savedInstanceState.getByteArray("board");

        for (int i = 0; i < 9; i++) {
            board[i/3][i%3]=boardSingle[i];
        }
        TableLayout table=findViewById(R.id.board);
        for (int i = 0; i < 3; i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button button = (Button)row.getChildAt(j);
                if (board[i][j]==1){
                    button.setText("X");
                }
                else if(board[i][j]==2){
                    button.setText("O");
                }

            }
        }


    }
}