package Service;

import Domain.Figure;
import Domain.PlayerColour;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ChessBordRenderer {

    public ByteArrayInputStream RenderChessBoard(Figure[][] chessBoard, PlayerColour colour) {
        BufferedImage bufferedImage = new BufferedImage(480,480, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        try
        {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Image image = ImageIO.read(classLoader.getResourceAsStream("board.png"));
            graphics.drawImage(image, 0,0, image.getWidth(null),image.getHeight(null), null);

            final ByteArrayOutputStream output = new ByteArrayOutputStream() {
                @Override
                public synchronized byte[] toByteArray() {
                    return this.buf;
                }
            };
            ImageIO.write(bufferedImage, "png", output);
            ByteArrayInputStream bais = new ByteArrayInputStream(output.toByteArray(), 0, output.size());
            return bais;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
