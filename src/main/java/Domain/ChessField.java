package Domain;

import ServerModels.Player;

public class ChessField {
   private Figure [][] chessBoard;

    ChessField(){
        chessBoard = new Figure[8][8];
        chessBoard[0][0] = new Figure(Pieces.Rook, PlayerColour.Black);
        chessBoard[0][1] = new Figure(Pieces.Knight, PlayerColour.Black);
        chessBoard[0][2] = new Figure(Pieces.Bishop, PlayerColour.Black);
        chessBoard[0][3] = new Figure(Pieces.Queen, PlayerColour.Black);
        chessBoard[0][4] = new Figure(Pieces.King, PlayerColour.Black);
        chessBoard[0][5] = new Figure(Pieces.Bishop, PlayerColour.Black);
        chessBoard[0][6] =  new Figure(Pieces.Knight, PlayerColour.Black);
        chessBoard[0][7] = new Figure(Pieces.Rook, PlayerColour.Black);

        for (int i = 1;i<2; i++){
            for (int j = 0; j < 8; j++)
                chessBoard[i][j] = new Figure(Pieces.Pawn, PlayerColour.Black);
        }

        for (int i = 6;i<7; i++){
            for (int j = 0; j < 8; j++)
                chessBoard[i][j] = new Figure(Pieces.Pawn, PlayerColour.White);
        }


        chessBoard[7][0] = new Figure(Pieces.Rook, PlayerColour.White);
        chessBoard[7][1] = new Figure(Pieces.Knight, PlayerColour.White);
        chessBoard[7][2] = new Figure(Pieces.Bishop, PlayerColour.White);
        chessBoard[7][3] = new Figure(Pieces.Queen, PlayerColour.White);
        chessBoard[7][4] = new Figure(Pieces.King, PlayerColour.White);
        chessBoard[7][5] = new Figure(Pieces.Bishop, PlayerColour.White);
        chessBoard[7][6] =  new Figure(Pieces.Knight, PlayerColour.White);
        chessBoard[7][7] = new Figure(Pieces.Rook, PlayerColour.White);
    }

    public Figure getFigure(Coords coords){
        return chessBoard[coords.X][coords.Y];
    }
}
