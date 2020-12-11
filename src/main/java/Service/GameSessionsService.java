package Service;

import Domain.GameSession;
import Domain.PlayerColour;
import ServerModels.Player;
import com.google.inject.Inject;
import com.sun.javafx.iio.ImageStorage;
import org.checkerframework.checker.units.qual.C;
import org.javatuples.Triplet;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GameSessionsService {


    @Inject
    private ISendMessageService sendMessageService;

    private ConcurrentHashMap<Player, Triplet<List<Player>, GameSession, PlayerColour>> gameSessionsMap
            = new ConcurrentHashMap<Player, Triplet<List<Player>, GameSession, PlayerColour>>();


    public void startNewMatch(Player player1, Player player2) {
        GameSession gameSession = new GameSession();
        List<Player> players = Arrays.asList(player1, player2);

        gameSessionsMap.put(player1, new Triplet<List<Player>, GameSession, PlayerColour>(players, gameSession, PlayerColour.White));
        gameSessionsMap.put(player2, new Triplet<List<Player>, GameSession, PlayerColour>(players, gameSession, PlayerColour.Black));

        SendMessage message = new SendMessage(player1.getChatId(), String.format("Ваш соперник %s.", player2.getUserName()));
        SendMessage message2 = new SendMessage(player2.getChatId(), String.format("Ваш соперник %s.", player1.getUserName()));
        sendMessageService.Send(message);
        sendMessageService.Send(message2);

        try {
            BufferedImage finalImage = new BufferedImage(600,600, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = finalImage.getGraphics();
            graphics.setColor(new Color(255,0,0));
            graphics.fillRect(10,10,100,100);
            graphics.dispose();

            final ByteArrayOutputStream output = new ByteArrayOutputStream() {
                @Override
                public synchronized byte[] toByteArray() {
                    return this.buf;
                }
            };
            ImageIO.write(finalImage, "png", output);
            ByteArrayInputStream bais = new ByteArrayInputStream(output.toByteArray(), 0, output.size());
            InputFile inputFile = new InputFile().setMedia(bais, "board");
            SendPhoto sendPhoto = new SendPhoto(player1.getChatId(),inputFile);
            SendPhoto sendPhoto2 = new SendPhoto(player2.getChatId(),inputFile);
            sendMessageService.Send(sendPhoto);
            bais.reset();
            sendMessageService.Send(sendPhoto2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public Triplet<List<Player>, GameSession, PlayerColour> getGameSession(Player player) {
        return gameSessionsMap.get(player);
    }
}
