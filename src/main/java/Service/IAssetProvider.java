package Service;

import Domain.PlayerColour;

import java.awt.*;

public interface IAssetProvider<T> {
    Image getAsset(T value, PlayerColour colour);
    Image getBoardImage();
}
