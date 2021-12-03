package service.core;

import java.io.Serializable;

public class GameRequestMessage implements Serializable {
    public long id;
    public Item item;

    public GameRequestMessage(long id, Item item) {
        this.id = id;
        this.item = item;
    }
}
