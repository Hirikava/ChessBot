package Service;

import Domain.Figure;
import Domain.PlayerColour;
import com.google.inject.Inject;
import org.javatuples.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ChessBordRenderer implements IChessBoardRenderer {

    @Inject
    IChessGameAssetProvider chessGameAssetProvider;


    public ByteArrayInputStream RenderChessBoard(Figure[][] chessBoard, PlayerColour colour) {

        Image chessBoardImage = chessGameAssetProvider.getBoardImage();
        if (chessBoardImage == null)
            return null;

        int boardWidth = chessGameAssetProvider.getBoardImage().getWidth(null);
        int boardHeight = chessGameAssetProvider.getBoardImage().getHeight(null);

        BufferedImage bufferedImage = new BufferedImage(boardWidth, boardHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.drawImage(chessGameAssetProvider.getBoardImage(), 0, 0, null);


        for (int i = 0; i < chessBoard.length; i++)
            for (int j = 0; j < chessBoard[i].length; j++)
                if (chessBoard[i][j] != null) {
                    Pair<Integer, Integer> drawingCords = GetDrawingCoordinates(boardWidth, boardHeight, colour, i, j);
                    Image asset = chessGameAssetProvider.getAsset(chessBoard[i][j].getFiguresName(), chessBoard[i][j].getColour());
                    if (asset == null)
                        return null;
                    graphics.drawImage(asset, drawingCords.getValue0(), drawingCords.getValue1(), null);
                }

        final ByteArrayOutputStream output = new ByteArrayOutputStream() {
            @Override
            public synchronized byte[] toByteArray() {
                return this.buf;
            }
        };

        try {
            ImageIO.write(bufferedImage, "png", output);
            ByteArrayInputStream bais = new ByteArrayInputStream(output.toByteArray(), 0, output.size());
            return bais;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public Pair GetDrawingCoordinates(int boardWidth, int boardHeight, PlayerColour playerColour, int row, int column) {
        switch (playerColour) {
            case Black:
                return new Pair(boardWidth - (boardWidth / 8) * (column + 1), (boardHeight / 8) * row);
            case White:
                return new Pair((boardWidth / 8) * column, boardHeight - (boardHeight / 8) * (row + 1));
        }

        return new Pair((boardWidth / 8) * column, (boardHeight / 8) * row);
    }
}
