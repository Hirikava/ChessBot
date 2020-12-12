import Domain.Cords;
import Domain.Figure;
import Domain.GameSession;
import Domain.PlayerColour;
import Service.ChessBordRenderer;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import static org.junit.Assert.assertEquals;

public class RenderingTests {

    private ChessBordRenderer chessBordRenderer = TestEnvironment.CreateInjector().getInstance(ChessBordRenderer.class);

    @Test
    public void RenderChessBoard_ShouldRenderNormaly() {
        GameSession gameSession = new GameSession();
        Figure[][] board = gameSession.createGameState().getBoard();

        ByteArrayInputStream forWhite = chessBordRenderer.RenderChessBoard(board, PlayerColour.White);
        ByteArrayInputStream foкBlack = chessBordRenderer.RenderChessBoard(board, PlayerColour.Black);
        try {
            BufferedImage bufferedImage1 = ImageIO.read(forWhite);
            ImageIO.write(bufferedImage1, "png", new File("test_white.jpg"));
            BufferedImage bufferedImage2 = ImageIO.read(foкBlack);
            ImageIO.write(bufferedImage2, "png", new File("test_black.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals(false, true);
        }
    }

    @Test
    public void RenderChessBoard_ShouldRenderNormalyAfterFirstTurn() {
        GameSession gameSession = new GameSession();
        Figure[][] board = gameSession.createGameState().getBoard();


        gameSession.MakeTurn(PlayerColour.White, new Cords(4, 1), new Cords(4, 3));
        gameSession.MakeTurn(PlayerColour.Black, new Cords(4, 6), new Cords(4, 4));

        ByteArrayInputStream forWhite = chessBordRenderer.RenderChessBoard(board, PlayerColour.White);
        ByteArrayInputStream foкBlack = chessBordRenderer.RenderChessBoard(board, PlayerColour.Black);
        try {
            BufferedImage bufferedImage1 = ImageIO.read(forWhite);
            ImageIO.write(bufferedImage1, "png", new File("test1_white.jpg"));
            BufferedImage bufferedImage2 = ImageIO.read(foкBlack);
            ImageIO.write(bufferedImage2, "png", new File("test1_black.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals(false, true);
        }
    }
}
