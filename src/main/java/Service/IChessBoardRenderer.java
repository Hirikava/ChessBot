package Service;

import Domain.Figure;
import Domain.PlayerColour;

import java.io.ByteArrayInputStream;

public interface IChessBoardRenderer {
    ByteArrayInputStream RenderChessBoard(Figure[][] chessBoard, PlayerColour colour);
}
