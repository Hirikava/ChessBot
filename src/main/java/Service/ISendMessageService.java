package Service;

import java.io.ByteArrayInputStream;

public interface ISendMessageService {

    void SendMessage(String chatId, String message);

    void SendPhoto(String chatId, ByteArrayInputStream byteArrayInputStream, String mediaFileName);
}
