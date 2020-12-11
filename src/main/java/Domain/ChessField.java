package Domain;

import ServerModels.Player;

public class ChessField {
   public Figure [][] chessBoard;

    ChessField(){
        chessBoard = new Figure[8][8];
        chessBoard[0][0] = new Figure(Pieces.Rook, PlayerColour.Black);
        chessBoard[0][1] = new Figure(Pieces.Knight, PlayerColour.Black);
        chessBoard[0][2] = new Figure(Pieces.Bishop, PlayerColour.Black);
       /*
        chessBoard[0][2] = new Bishop("black");
        chessBoard[0][3] = new Queen("black");
        chessBoard[0][4] = new King("black");
        chessBoard[0][5] = new Bishop("black");
        chessBoard[0][6] =  new Knight(("black"));
        chessBoard[0][7] = new Rook("black");

        for (int i = 1;i<2; i++){
            for (int j = 0; j < 8; j++)
                chessBoard[i][j] = new Pawn("black");
        }

        for (int i = 6;i<7; i++){
            for (int j = 0; j < 8; j++)
                chessBoard[i][j] = new Pawn("white");
        }

        chessBoard[7][0] = new Rook("white");
        chessBoard[7][1] = new Knight("white");
        chessBoard[7][2] = new Bishop("white");
        chessBoard[7][3] = new Queen("white");
        chessBoard[7][4] = new King("white");
        chessBoard[7][5] = new Bishop("white");
        chessBoard[7][6] =  new Knight(("white"));
        chessBoard[7][7] = new Rook("white");*/


    }
}
