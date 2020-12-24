package Service;

import Domain.Pieces;
import Domain.PlayerColour;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.util.logging.Logger;

public class ChessGameAssetProvider implements IChessGameAssetProvider {

    @Inject @Named("logger")
    private Logger logger;

    private Image whiteKingImage;
    private Image whitePawnImage;
    private Image whiteRookImage;
    private Image whiteKnightImage;
    private Image whiteBishopImage;
    private Image whiteQueenImage;

    private Image blackKingImage;
    private Image blackPawnImage;
    private Image blackRookImage;
    private Image blackKnightImage;
    private Image blackBishopImage;
    private Image blackQueenImage;

    private Image boardImage;

    public ChessGameAssetProvider() {
        Color transparentColor = new Color(255, 0, 0);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            boardImage = makeImageTransparent(ImageIO.read(classLoader.getResourceAsStream("board.png")), transparentColor);
            blackKingImage = makeImageTransparent(ImageIO.read(classLoader.getResourceAsStream("king_black.png")), transparentColor);
            blackQueenImage = makeImageTransparent(ImageIO.read(classLoader.getResourceAsStream("queen_black.png")), transparentColor);
            blackRookImage = makeImageTransparent(ImageIO.read(classLoader.getResourceAsStream("rook_black.png")), transparentColor);
            blackBishopImage = makeImageTransparent(ImageIO.read(classLoader.getResourceAsStream("bishop_black.png")), transparentColor);
            blackKnightImage = makeImageTransparent(ImageIO.read(classLoader.getResourceAsStream("knight_black.png")), transparentColor);
            blackPawnImage = makeImageTransparent(ImageIO.read(classLoader.getResourceAsStream("pawn_black.png")), transparentColor);

            whiteKingImage = makeImageTransparent(ImageIO.read(classLoader.getResourceAsStream("king_white.png")), transparentColor);
            whiteQueenImage = makeImageTransparent(ImageIO.read(classLoader.getResourceAsStream("queen_white.png")), transparentColor);
            whiteRookImage = makeImageTransparent(ImageIO.read(classLoader.getResourceAsStream("rook_white.png")), transparentColor);
            whiteBishopImage = makeImageTransparent(ImageIO.read(classLoader.getResourceAsStream("bishop_white.png")), transparentColor);
            whiteKnightImage = makeImageTransparent(ImageIO.read(classLoader.getResourceAsStream("knight_white.png")), transparentColor);
            whitePawnImage = makeImageTransparent(ImageIO.read(classLoader.getResourceAsStream("pawn_white.png")), transparentColor);
        } catch (Exception e) {
            logger.severe(String.format("Failed to load assets with following message:{%s}", e.getMessage()));
        }

    }

    private Image makeImageTransparent(Image im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            public int markerRGB = color.getRGB() | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    return 0x00FFFFFF & rgb;
                } else {
                    return rgb;
                }
            }
        };
        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }


    @Override
    public Image getAsset(Pieces value, PlayerColour colour) {
        switch (value) {
            case King:
                return colour == PlayerColour.Black ? blackKingImage : whiteKingImage;
            case Pawn:
                return colour == PlayerColour.Black ? blackPawnImage : whitePawnImage;
            case Rook:
                return colour == PlayerColour.Black ? blackRookImage : whiteRookImage;
            case Queen:
                return colour == PlayerColour.Black ? blackQueenImage : whiteQueenImage;
            case Bishop:
                return colour == PlayerColour.Black ? blackBishopImage : whiteBishopImage;
            case Knight:
                return colour == PlayerColour.Black ? blackKnightImage : whiteKnightImage;
        }
        return null;
    }

    @Override
    public Image getBoardImage() {
        return boardImage;
    }
}
