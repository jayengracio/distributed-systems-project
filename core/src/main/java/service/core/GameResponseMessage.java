package service.core;

import java.io.Serializable;

public class GameResponseMessage implements  Serializable {
    public long id;
    public Game game;

    public GameResponseMessage(long id, Game game) {
        this.id = id;
        this.game = game;
    }
}
