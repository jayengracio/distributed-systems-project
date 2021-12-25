package service.core;

import java.io.Serializable;

public class GameResponseMessage implements  Serializable {
    public long id;
    public GameItem gameItem;

    public GameResponseMessage(long id, GameItem game) {
        this.id = id;
        this.gameItem = game;
    }
}
